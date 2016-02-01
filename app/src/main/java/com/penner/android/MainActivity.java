package com.penner.android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.newrelic.agent.android.NewRelic;
import com.penner.android.base.BaseActivity;
import com.penner.android.model.main.MainViewPagerAdapter;
import com.penner.android.view.main.MainFragment;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setElevation(0);

        NewRelic.withApplicationToken(
                "AA42071ed4924fbe1c589716303352c0aa7135b860"
        ).start(this.getApplication());

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener( v -> {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = (NavigationView)findViewById(R.id.main_nav_view);
        ViewPager viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Penner1");
        adapter.addFragment(new MainFragment(), "Penner2");
        adapter.addFragment(new MainFragment(), "Penner3");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
