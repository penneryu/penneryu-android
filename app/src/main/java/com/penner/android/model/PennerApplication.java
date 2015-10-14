package com.penner.android.model;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by PennerYu on 15/10/14.
 */
public class PennerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this, "zxm3yzVUdbppiv1l1BSXredl", "KN5vk3uPcWqHLBTG71Cdzhk7");
    }
}
