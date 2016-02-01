package com.penner.android.data;

import android.os.AsyncTask;
import android.os.Build;

import com.penner.android.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by penneryu on 15/8/22.
 */
public abstract class HttpFactoryBase<T> {

    private HttpDownloadTask mTask;
    private HttpEventHandler<T> mHttpEventHandler;
    private HttpURLConnection mHttpConection;

    public void setHttpEventHandler(HttpEventHandler<T> httpEventHandler) {
        mHttpEventHandler = httpEventHandler;
    }

    /**
     * 异步下载数据
     */
    public void downloaDatas(Object... args) {
        cancel();
        mTask = new HttpDownloadTask();
        if (Build.VERSION.SDK_INT < 11) {
            mTask.execute(args);
        } else {
            mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
        }
    }

    /**
     * 同步下载数据
     */
    public T syncDownloaDatas(Object... args) {
        return doNetworkRequest(args);
    }

    /**
     * 取消下载
     */
    public void cancel() {
        try {
            if (mTask != null) {
                mTask.cancel(true);
                mTask = null;
            }
            if (mHttpConection != null) {
                mHttpConection.disconnect();
                mHttpConection = null;
            }
        } catch (Exception e) {
            LogUtils.e("httpfactorybase", e.toString());
        }
    }

    protected int getConnectTimeout() {
        return 60000;
    }

    protected String getHttpType() {
        return "GET";
    }

    protected Map<String, Object> getPostArgs() {
        return null;
    }

    protected Map<String, String> getHttpHeads() {
        return null;
    }

    /**
     * Http请求地址
     *
     * @param args
     * @return
     */
    protected abstract String CreateUri(Object... args);

    /**
     * 子类解析内容
     *
     * @param stream
     * @return
     * @throws java.io.IOException
     */
    protected abstract T AnalysisContent(InputStream stream) throws IOException;

    /**
     * 服务器返回非200得到错误原因
     * @param stream
     * @throws IOException
     */
    protected void AnalysisError(InputStream stream) throws IOException {
    }

    protected T doNetworkRequest(Object... params) {
        try {
            String uri = CreateUri(params);
            LogUtils.i("httpfactorybase", uri);

            mHttpConection = (HttpURLConnection) new URL(uri).openConnection();
            mHttpConection.setReadTimeout(getConnectTimeout());
            mHttpConection.setConnectTimeout(getConnectTimeout());

            OutputStream os = null;
            if (getHttpHeads() != null) {
                Iterator<Map.Entry<String, String>> iterator = getHttpHeads().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> param = iterator.next();
                    String key = param.getKey();
                    String value = param.getValue();
                    mHttpConection.setRequestProperty(key, value);
                }
            }
            mHttpConection.setRequestMethod(getHttpType());
            if ("GET".equals(getHttpType()) || "DELETE".equals(getHttpType())) {
                mHttpConection.connect();
            } else {
                byte[] data = getParamString(getPostArgs()).getBytes();
                mHttpConection.setUseCaches(false);
                mHttpConection.setDoInput(true);
                mHttpConection.setDoOutput(true);
                mHttpConection.setRequestProperty("Content-Length", String.valueOf(data.length));
                os = mHttpConection.getOutputStream();
                os.write(data);
            }
            int code = mHttpConection.getResponseCode();
            InputStream stream = null;
            try {
                if (code == HttpURLConnection.HTTP_OK) {
                    stream = mHttpConection.getInputStream();
                    return AnalysisContent(stream);
                } else {
                    stream = mHttpConection.getErrorStream();
                    AnalysisError(stream);
                    return null;
                }
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }  catch (Exception e) {
            LogUtils.e("httpfactorybase", e.toString());
        } finally {
            if (mHttpConection != null) {
                mHttpConection.disconnect();
                mHttpConection = null;
            }
        }
        return null;
    }

    protected String getParamString(Map<String, Object> params) {
        StringBuffer result = new StringBuffer();
        if (params != null) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> param = iterator.next();
                String key = param.getKey();
                String value = param.getValue().toString();
                result.append(key).append('=').append(value);
                if (iterator.hasNext()) {
                    result.append('&');
                }
            }
        }
        return result.toString();
    }

    /**
     * 异步下载任务
     *
     * @author PennerYu
     */
    private class HttpDownloadTask extends AsyncTask<Object, Integer, T> {

        @Override
        protected T doInBackground(Object... params) {
            if (isCancelled()) {
                return null;
            }
            return doNetworkRequest(params);
        }

        @Override
        protected void onPostExecute(T result) {
            if (isCancelled()) {
                return;
            }
            if (result == null) {
                if (mHttpEventHandler != null) {
                    mHttpEventHandler.HttpFailHandler(null);
                }
            } else {
                if (mHttpEventHandler != null) {
                    mHttpEventHandler.HttpSucessHandler(result);
                }
            }
        }
    }
}
