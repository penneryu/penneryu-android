package com.penner.android;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.penner.android.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView)findViewById(R.id.webview_webview);
//        String data = "<a href=\"pubuim://123\">test to launch myapp</a> <br /><br />";
//        webView.loadData(data, "text/html", "utf-8");

        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsProviderOK = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkProviderOK = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean geolocationOK = gpsProviderOK && networkProviderOK;
        Log.i("webview", "gpsProviderOK = " + gpsProviderOK + "; networkProviderOK = " + networkProviderOK + "; geoLocationOK=" + geolocationOK);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String uri) {
                view.loadUrl(uri);
                return true;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                      GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

//        String url = "http://192.168.30.83:32769/geolocation.html";
        String url = "http://www.zhihu.com";
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
