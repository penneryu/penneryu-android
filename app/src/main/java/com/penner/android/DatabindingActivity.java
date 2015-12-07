package com.penner.android;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.penner.android.base.BaseActivity;
import com.penner.android.databinding.ActivityDatabindingBinding;
import com.penner.android.model.databinding.DatabindingUser;

public class DatabindingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDatabindingBinding binding = ActivityDatabindingBinding.inflate(LayoutInflater.from(this));
        DatabindingUser user = new DatabindingUser("Penenr", "Yu");
        binding.setUser(user);

        setupToolbar();
    }

    @Override
    protected String getToolbarTitle() {
        return "Data Binding";
    }
}
