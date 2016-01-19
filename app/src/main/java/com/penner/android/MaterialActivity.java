package com.penner.android;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.penner.android.base.BaseActivity;
import com.penner.android.model.material.RecyclerAdapter;

public class MaterialActivity extends BaseActivity {

    private RecyclerAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        mRecyclerView = (RecyclerView)findViewById(R.id.material_recyclerview);
        mAdapter = new RecyclerAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
