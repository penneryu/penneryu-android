package com.penner.android.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PennerYu on 15/10/13.
 */
public abstract class SharedPreferencesFactoryBase {

    private Context mContext;
    private int mMode;
    private SharedPreferences mSharedPreferences;

    public SharedPreferencesFactoryBase(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    public SharedPreferencesFactoryBase(Context context, int mode) {
        mContext = context;
        mMode = mode;
    }

    protected abstract String getKey();

    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = mContext.getSharedPreferences(getKey(), mMode);
        }
        return mSharedPreferences;
    }
}
