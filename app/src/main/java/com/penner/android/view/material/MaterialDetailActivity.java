package com.penner.android.view.material;

import android.os.Bundle;

import com.penner.android.R;
import com.penner.android.base.BaseActivity;

public class MaterialDetailActivity extends BaseActivity {

    @Override
    protected String getToolbarTitle() {
        return "MaterialDetail";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_detail);
    }
}
