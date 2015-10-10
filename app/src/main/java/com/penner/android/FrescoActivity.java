package com.penner.android;

import android.os.Bundle;

import com.penner.android.base.BaseActivity;

public class FrescoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        getToolbar().setTitle("Fresco");
        setupToolbar();
    }
}
