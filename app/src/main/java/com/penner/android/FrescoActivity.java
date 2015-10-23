package com.penner.android;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.penner.android.base.BaseActivity;

public class FrescoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);

        SimpleDraweeView draweeView = (SimpleDraweeView)findViewById(R.id.fresco_simpleView);
        draweeView.setImageURI(Uri.parse("http://img.vision.pptv.com/images/1c/f8/1cf8b6806829e5ffcd958f2da5f436ba7e936e55.jpeg"));
    }

    @Override
    protected String getToolbarTitle() {
        return "Fresco";
    }
}
