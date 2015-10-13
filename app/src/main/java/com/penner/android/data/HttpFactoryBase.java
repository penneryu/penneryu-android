package com.penner.android.data;

import android.os.AsyncTask;
import android.os.Build;

import com.penner.android.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by PennerYu on 15/10/13.
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
        return 15000;
    }

    protected String getHttpType() {
        return "GET";
    }

    protected Map<String, String> getPostArgs() {
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

    protected T doNetworkRequest(Object... params) {
        try {
            String uri = CreateUri(params);
            LogUtils.i("httpfactorybase", uri);

            mHttpConection = (HttpURLConnection) new URL(uri).openConnection();
            mHttpConection.setReadTimeout(getConnectTimeout());
            mHttpConection.setConnectTimeout(getConnectTimeout());

            OutputStream os = null;
            if ("GET".equals(getHttpType())) {
                mHttpConection.setRequestMethod("GET");
                mHttpConection.connect();
            } else {
                byte[] data = getParamString(getPostArgs()).toString().getBytes();
                mHttpConection.setRequestMethod("POST");
                mHttpConection.setUseCaches(false);
                mHttpConection.setDoInput(true);
                mHttpConection.setDoOutput(true);
                mHttpConection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                mHttpConection.setRequestProperty("Content-Length", String.valueOf(data.length));
                os = mHttpConection.getOutputStream();
                os.write(data);
            }
            int code = mHttpConection.getResponseCode();
            InputStream stream;
            if (code == HttpURLConnection.HTTP_OK) {
                stream = mHttpConection.getInputStream();
            } else {
                stream = mHttpConection.getErrorStream();
            }
            try {
                return AnalysisContent(stream);
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
        } catch (MalformedURLException e) {
            LogUtils.e("httpfactorybase", e.toString());
        } catch (IOException e) {
            LogUtils.e("httpfactorybase", e.toString());
        } finally {
            if (mHttpConection != null) {
                mHttpConection.disconnect();
                mHttpConection = null;
            }
        }
        return null;
    }

    private StringBuffer getParamString(Map<String, String> params) {
        StringBuffer result = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            String key = param.getKey();
            String value = param.getValue();
            result.append(key).append('=').append(value);
            if (iterator.hasNext()) {
                result.append('&');
            }
        }
        return result;
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
                    mHttpEventHandler.HttpFailHandler();
                }
            } else {
                if (mHttpEventHandler != null) {
                    mHttpEventHandler.HttpSucessHandler(result);
                }
            }
        }
    }
}
