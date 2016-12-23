package com.penner.android;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.penner.android.base.BaseActivity;
import com.penner.android.util.LogUtils;
import com.penner.lib.annotation.Test;

@Test("Penner")
public class TestActivity extends BaseActivity {

    static {
        System.loadLibrary("pubuanddown");
    }

    public native String markdownToHtml(String raw, boolean hasStrong);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Runtime runtime = Runtime.getRuntime();
        LogUtils.d("penner", runtime.maxMemory() + " " + runtime.totalMemory() + " " + runtime.freeMemory());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        String result = markdownToHtml("<a>Penner</a>", true);
        LogUtils.d("anddown", result);

        LogUtils.d("penner", "onCreate");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LogUtils.d("penner", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        LogUtils.d("penner", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        LogUtils.d("penner", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        LogUtils.d("penner", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        LogUtils.d("penner", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        LogUtils.d("penner", "onRestart");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        LogUtils.d("penner", "onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        LogUtils.d("penner", "onDetachedFromWindow");
    }
}
