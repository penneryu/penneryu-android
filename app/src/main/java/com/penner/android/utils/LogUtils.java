package com.penner.android.utils;

import android.util.Log;

/**
 * Created by PennerYu on 15/10/13.
 */
public final class LogUtils {

    /**
     * 0 : develop
     * 6 : release
     * */
    public static final int LOG_LEVEL = 0;

    public static void v(String tag, String msg) {
        if (Log.VERBOSE >= LOG_LEVEL) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (Log.DEBUG >= LOG_LEVEL) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (Log.INFO >= LOG_LEVEL) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (Log.WARN >= LOG_LEVEL) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (Log.ERROR >= LOG_LEVEL) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable ex) {
        if (Log.ERROR >= LOG_LEVEL) {
            Log.e(tag, msg);
        }
    }
}
