package com.penner.android.model.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.penner.android.R;
import com.penner.android.ServiceActivity;
import com.penner.android.util.LogUtils;

/**
 * Created by PennerYu on 15/10/26.
 */
public class LocalService extends Service {

    private NotificationManager mNotifactionManager;
    private IBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        mNotifactionManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        LogUtils.i("LocalService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i("LocalService", "onStartCommand");
//        Intent activityIntent = new Intent(this, FrescoActivity.class);
//        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(activityIntent);

        if (Build.VERSION.SDK_INT < 18) {
            startForeground(1001, new Notification());
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(1001, new Notification());
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i("LocalService", "onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mNotifactionManager.cancel(R.string.service_local_start);
        LogUtils.i("LocalService", "onDestroy");
    }

    public void showNotification() {
        CharSequence text = getText(R.string.service_local_start);
        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, ServiceActivity.class), 0);
        Notification notification;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification(R.mipmap.ic_launcher, text, System.currentTimeMillis());
            notification.contentIntent = intent;
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(text)
                    .setContentIntent(intent)
                    .build();
        }
        mNotifactionManager.notify(R.string.service_local_start, notification);
    }

    public String getTestValue(String name) {
        return name.substring(3);
    }

    public class LocalBinder extends Binder {

        public LocalService getService() {
            return LocalService.this;
        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(1001, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
