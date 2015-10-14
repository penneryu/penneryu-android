package com.penner.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.penner.android.base.BaseActivity;
import com.penner.android.utils.PennerUitls;

public class LoginActivity extends BaseActivity {

    private TextInputLayout mNameLyout;
    private TextInputLayout mPasswordLayout;
    private EditText mNameEdit;
    private EditText mPasswordEdit;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getToolbar().setTitle(getString(R.string.login));
        setupToolbar();

        mNameLyout = (TextInputLayout)findViewById(R.id.login_layout_name);
        mPasswordLayout = (TextInputLayout)findViewById(R.id.login_layout_pwd);
        mNameEdit = (EditText)findViewById(R.id.login_edit_name);
        mPasswordEdit = (EditText)findViewById(R.id.login_edit_pwd);
        mLoginBtn = (Button)findViewById(R.id.login_btn);

        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        mPasswordEdit.addTextChangedListener(new LoginTextWatcher(mPasswordLayout, getString(R.string.login_pwd_tips2)));
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEdit.getText().toString();
                String pwd = mPasswordEdit.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    mNameEdit.setError(getString(R.string.login_name_tips));
                    mNameEdit.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    mPasswordEdit.setError(getString(R.string.login_pwd_tips));
                    mPasswordEdit.requestFocus();
                    return;
                }
                mNameEdit.setError(null);
                mPasswordEdit.setError(null);
                PennerUitls.hideKeyboard(LoginActivity.this);

                dialog.setMessage(getString(R.string.logining));
                dialog.show();

                AVUser.logInInBackground(name, pwd, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        dialog.dismiss();
                        if (avUser != null) {
                            Snackbar.make(mLoginBtn, getString(R.string.login_sucess), Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(mLoginBtn, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private class LoginTextWatcher implements TextWatcher {

        private TextInputLayout mTextInputLayout;
        private String mErrorInfo;

        public LoginTextWatcher(TextInputLayout textInputLayout, String erroInfo) {
            mTextInputLayout = textInputLayout;
            mErrorInfo = erroInfo;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mTextInputLayout.getEditText().getText().toString().length() < 6) {
                mTextInputLayout.setErrorEnabled(true);
                mTextInputLayout.setError(mErrorInfo);
            } else {
                mTextInputLayout.setErrorEnabled(false);
            }
        }
    }
}
