package com.penner.android.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.penner.android.R;

/**
 * Created by PennerYu on 15/10/9.
 */
public class BaseActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setupToolbar();
    }

    public void setContentViewWithoutToolbar(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar)findViewById(R.id.penner_toolbar);
    }

    protected void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.penner_toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getToolbarTitle());
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setupToolbarWithoutTitle() {
        mToolbar = (Toolbar) findViewById(R.id.penner_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    protected String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
