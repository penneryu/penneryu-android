package com.penner.android.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by PennerYu on 15/10/10.
 */
public final class PennerUitls {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int sp2px(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(value * scale + 0.5F);
    }
}
