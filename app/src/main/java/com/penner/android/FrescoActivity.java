package com.penner.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.penner.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrescoActivity extends BaseActivity {

    @BindView(R.id.fresco_palette)
    Button faletteBtn;
    @BindView(R.id.freso_palette_vibrant)
    View vibrantView;
    @BindView(R.id.freso_palette_vibrant_dark)
    View vibrantDrakView;
    @BindView(R.id.freso_palette_vibrant_light)
    View vibrantLightView;
    @BindView(R.id.fresco_palette_mute)
    View muteView;
    @BindView(R.id.fresco_palette_mute_dark)
    View muteDarkView;
    @BindView(R.id.fresco_palette_mute_light)
    View muteLightView;
    @BindView(R.id.freco_palette_singleview)
    View singleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);

        SimpleDraweeView draweeView = (SimpleDraweeView)findViewById(R.id.fresco_simpleView);
        draweeView.setImageURI(Uri.parse("http://img.vision.pptv.com/images/1c/f8/1cf8b6806829e5ffcd958f2da5f436ba7e936e55.jpeg"));

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        faletteBtn.setOnClickListener(v -> {
            Palette.from(bitmap).generate(palette -> {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch != null) {
                    vibrantView.setBackgroundColor(swatch.getRgb());
                }
                swatch = palette.getDarkVibrantSwatch();
                if (swatch != null) {
                    vibrantDrakView.setBackgroundColor(swatch.getRgb());
                }
                swatch = palette.getLightVibrantSwatch();
                if (swatch != null) {
                    vibrantLightView.setBackgroundColor(swatch.getRgb());
                }
                swatch = palette.getMutedSwatch();
                if (swatch != null) {
                    muteView.setBackgroundColor(swatch.getRgb());
                }
                swatch = palette.getDarkMutedSwatch();
                if (swatch != null) {
                    muteDarkView.setBackgroundColor(swatch.getRgb());
                }
                swatch = palette.getLightMutedSwatch();
                if (swatch != null) {
                    muteLightView.setBackgroundColor(swatch.getRgb());
                }
            });
            Palette.Builder builder = new Palette.Builder(bitmap);
            builder.maximumColorCount(1);
            builder.generate(palette -> {
                Palette.Swatch swatch = palette.getSwatches().get(0);
                if (swatch != null) {
                    singleView.setBackgroundColor(swatch.getRgb());
                } else {
                    Log.e("falette", "swatch is null");
                }
            });
        });
    }
}
