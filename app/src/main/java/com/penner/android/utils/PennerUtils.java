package com.penner.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

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

    public static void showSnackbar(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
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
                boolean canMake = cache.mkdir();
                if (!canMake) {
                    return context.getCacheDir();
                }
            }
            return cache;
        } else {
            return context.getCacheDir();
        }
    }

    public static File getCameraFile() {
        File directory = new File(Environment.getExternalStorageDirectory() + "/penner_android/cameras/");
        if (!directory.exists()) {
            directory.mkdir();
        }
        return new File(directory, System.currentTimeMillis() + ".jpg");
    }

    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }
}
