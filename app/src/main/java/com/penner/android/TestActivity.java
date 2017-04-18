package com.penner.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.penner.android.base.BaseActivity;
import com.penner.android.util.LogUtils;
import com.penner.lib.annotation.Test;

@Test("Penner")
public class TestActivity extends Activity {

    static {
        System.loadLibrary("anddown");
    }

    public native String markdownToHtml(String raw, boolean hasStrong);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Runtime runtime = Runtime.getRuntime();
        LogUtils.d("penner", runtime.maxMemory() + " " + runtime.totalMemory() + " " + runtime.freeMemory());

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        findViewById(R.id.test_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
                String deviceId = getPhoneDeviceId();
                Log.d("test", deviceId);
            }
        });

        String result = markdownToHtml("<a>Penner</a>", true);
        LogUtils.d("anddown", result);

        LogUtils.d("penner", "onCreate");

        String nonconfig = (String)getLastNonConfigurationInstance();
        LogUtils.d("nonconfig", nonconfig + " Peng");

        if (savedInstanceState != null) {
            String nonconfig2 = savedInstanceState.getString("nonconfig");
            LogUtils.d("nonconfig", nonconfig2 + " Peng");
        }
    }

    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public String getPhoneDeviceId() {
        return ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
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

    @Override
    public Object onRetainNonConfigurationInstance() {
        return "Penner Yuuu";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("nonconfig", "Penner Yuuuu");
    }
}
