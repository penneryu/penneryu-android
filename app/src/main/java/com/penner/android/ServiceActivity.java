package com.penner.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.ashmen.IRemoteService;
import com.penner.android.model.service.LocalService;
import com.penner.android.util.LogUtils;

public class ServiceActivity extends BaseActivity {

    private LocalService mLocalService;
    private IRemoteService mRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Button btnBind = (Button)findViewById(R.id.service_btn_bind);
        Button btnAction = (Button)findViewById(R.id.service_btn_action);
        Button btnUnBind = (Button)findViewById(R.id.service_btn_unbind);
        TextView textView = (TextView)findViewById(R.id.service_txt);

        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ServiceActivity.this, LocalService.class);
//                    bindService(intent, mConnecton, Context.BIND_AUTO_CREATE);
                    startService(intent);

                    Intent intent1 = new Intent("com.penner.android.model.ashmen.action.Remote");
                    intent1.setPackage("com.penner.android");
                    bindService(intent1, mConnecton1, BIND_AUTO_CREATE);
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
                if (mRemoteService != null) {
                    try {
                        textView.setText(String.valueOf(mRemoteService.getPid()));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btnUnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    unbindService(mConnecton);
                    unbindService(mConnecton1);
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

    private ServiceConnection mConnecton1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
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
