package com.penner.android;

import android.os.Bundle;

import com.penner.android.base.BaseActivity;
import com.penner.android.view.largeimage.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageActivity extends BaseActivity {

    @Override
    protected String getToolbarTitle() {
        return "LargeImage";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        LargeImageView imageView = (LargeImageView)findViewById(R.id.large_img);
        try {
            InputStream stream = getAssets().open("world_map.jpg");
            imageView.setInputStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
