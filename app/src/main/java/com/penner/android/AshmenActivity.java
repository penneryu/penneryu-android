package com.penner.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.ashmen.IMemoryService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AshmenActivity extends BaseActivity {

    @Bind(R.id.ashmen_edit)
    EditText mEdiText;
    @Bind(R.id.ashmen_txt)
    TextView mTextView;
    @Bind(R.id.ashmen_btn)
    Button mButton;

    private IMemoryService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashmen);
        ButterKnife.bind(this);

        try {
            Intent intent = new Intent("com.penner.android.model.ashmen.action.SERVER");
            intent.setPackage("com.penner.android");
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mButton.setOnClickListener(v -> {
            String text = mEdiText.getText().toString();
            int val = Integer.parseInt(text);
            try {
                mService.setValue(val);
                mTextView.setText(mService.getContentValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMemoryService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };
}
