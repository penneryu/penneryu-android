package com.penner.android;

import android.os.Bundle;
import android.webkit.WebView;

import com.penner.android.base.BaseActivity;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        WebView webView = (WebView)findViewById(R.id.webview_webview);
        String data = "<a href=\"pubuim://123\">test to launch myapp</a> <br /><br />";
        webView.loadData(data, "text/html", "utf-8");
    }
}
