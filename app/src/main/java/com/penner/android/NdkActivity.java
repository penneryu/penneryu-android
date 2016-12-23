package com.penner.android;

import android.os.Bundle;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;

public class NdkActivity extends BaseActivity {

//    @BindView(R.id.ndk_txt)
    TextView mNdkTextView;

    static {
        System.loadLibrary("anddown");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);
//        ButterKnife.bind(this);

        mNdkTextView = (TextView)findViewById(R.id.ndk_txt);
        mNdkTextView.setText(markdownToHtml("penner*aaa*yu", true));
    }

    public native String markdownToHtml(String raw, boolean hasStrong);
}
