package com.penner.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.service.LocalService;
import com.penner.android.utils.LogUtils;

public class ServiceActivity extends BaseActivity {

    private LocalService mLocalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button btnBind = (Button)findViewById(R.id.service_btn_bind);
        Button btnAction = (Button)findViewById(R.id.service_btn_action);
        Button btnUnBind = (Button)findViewById(R.id.service_btn_unbind);

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ServiceActivity.this, LocalService.class);
                    bindService(intent, mConnecton, Context.BIND_AUTO_CREATE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLocalService != null) {
                    mLocalService.showNotification();
                }
            }
        });
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    unbindService(mConnecton);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private ServiceConnection mConnecton = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocalService = ((LocalService.LocalBinder)service).getService();
            LogUtils.i("LocalService", "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLocalService = null;
            LogUtils.i("LocalService", "onServiceDisconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            unbindService(mConnecton);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
