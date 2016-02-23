package com.penner.android.model.ashmen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.penner.android.utils.LogUtils;

/**
 * Created by penneryu on 16/2/23.
 */
public class Server extends Service {

    private final static String LOG_TAG = "com.penner.android.model.ashmen.Server";


    @Override
    public void onCreate() {
        LogUtils.i(LOG_TAG, "Create Memory Service...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MemoryService();
    }
}
