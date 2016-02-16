package com.penner.android;

import android.os.Bundle;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NdkActivity extends BaseActivity {

    @Bind(R.id.ndk_txt)
    TextView mNdkTextView;

    static {
        System.loadLibrary("pennerNDK");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);
        ButterKnife.bind(this);

        mNdkTextView.setText(penner("penner"));
    }

    private native String penner(String source);
}
