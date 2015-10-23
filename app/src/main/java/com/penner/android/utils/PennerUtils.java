package com.penner.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;

/**
 * Created by PennerYu on 15/10/10.
 */
public final class PennerUtils {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int sp2px(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(value * scale + 0.5F);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null) {
                InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static File getImageCacheDirectory(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File cache = context.getExternalCacheDir();
            if (cache == null) {
                StringBuilder builder = new StringBuilder();
                String path = builder.append(Environment.getExternalStorageDirectory().getPath()).append("/Android/data/").append(context.getPackageName())
                        .append("/cache/").append(File.separator).toString();
                cache = new File(path);
            }
            if (!cache.exists()) {
                cache.mkdir();
            }
            return cache;
        } else {
            return context.getCacheDir();
        }
    }
}
