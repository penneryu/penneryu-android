package com.penner.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.penner.android.base.BaseActivity;
import com.penner.android.view.bottomtab.BottomTabItemView;
import com.penner.android.view.bottomtab.contact.ContactFragment;
import com.penner.android.view.bottomtab.files.FilesFragment;
import com.penner.android.view.bottomtab.penner.PennerFragment;
import com.penner.android.view.bottomtab.profile.ProfileFragment;

public class BottomTabActivity extends BaseActivity {

    private int mTabCurrentIndex;

    private BottomTabItemView mPubuTabItem;
    private BottomTabItemView mContactTabItem;
    private BottomTabItemView mFilesTabItem;
    private BottomTabItemView mProfileTabItem;

    private PennerFragment mPennerFragment;
    private ContactFragment mContactFragment;
    private FilesFragment mFilesFragment;
    private ProfileFragment mProfileFragment;

    private BottomTabItemView[] mTabItems;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        if (savedInstanceState != null) {
            mTabCurrentIndex = savedInstanceState.getInt("tabindex", 0);
        }

        initTabs();
        initContents();
    }

    private void initTabs() {
        mPubuTabItem = (BottomTabItemView)findViewById(R.id.bottom_tab_penner);
        mContactTabItem = (BottomTabItemView)findViewById(R.id.bottom_tab_contact);
        mFilesTabItem = (BottomTabItemView)findViewById(R.id.bottom_tab_files);
        mProfileTabItem = (BottomTabItemView)findViewById(R.id.bottom_tab_profile);

        mPubuTabItem.initView(R.drawable.bottom_tab_penner, getResources().getString(R.string.bottom_tab_penner), null);
        mContactTabItem.initView(R.drawable.bottom_tab_penner, getResources().getString(R.string.bottom_tab_contract), null);
        mFilesTabItem.initView(R.drawable.bottom_tab_penner, getResources().getString(R.string.bottom_tab_file), null);
        mProfileTabItem.initView(R.drawable.bottom_tab_penner, getResources().getString(R.string.bottom_tab_profile), null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabClicked(v);
            }
        };
        mPubuTabItem.setOnClickListener(listener);
        mContactTabItem.setOnClickListener(listener);
        mFilesTabItem.setOnClickListener(listener);
        mProfileTabItem.setOnClickListener(listener);

        mTabItems = new BottomTabItemView[] {mPubuTabItem, mContactTabItem, mFilesTabItem, mProfileTabItem};
        mTabItems[mTabCurrentIndex].setSelected(true);
    }

    private void initContents() {
        mPennerFragment = new PennerFragment();
        mContactFragment = new ContactFragment();
        mFilesFragment = new FilesFragment();
        mProfileFragment = new ProfileFragment();

        mFragments = new Fragment[] {
                mPennerFragment,
                mContactFragment,
                mFilesFragment, mProfileFragment};
        showFragment(mTabCurrentIndex);
    }

    private void onTabClicked(View view) {
        int index = 0;
        switch (view.getId()) {
            case R.id.bottom_tab_penner:
                index = 0;
                break;
            case R.id.bottom_tab_contact:
                index = 1;
                break;
            case R.id.bottom_tab_files:
                index = 2;
                break;
            case R.id.bottom_tab_profile:
                index = 3;
                break;
        }
        showFragment(index);
    }

    private void showFragment(int index) {
        switch (index) {
            case 0:
                getToolbar().setTitle(getString(R.string.bottom_tab_penner));
                break;
            case 1:
                getToolbar().setTitle(getString(R.string.bottom_tab_contract));
                break;
            case 2:
                getToolbar().setTitle(getString(R.string.bottom_tab_file));
                break;
            case 3:
                getToolbar().setTitle(getString(R.string.bottom_tab_profile));
                break;
        }
        if (mTabCurrentIndex != index || !mFragments[index].isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(mFragments[mTabCurrentIndex]);
            if (!mFragments[index].isAdded()) {
                fragmentTransaction.add(R.id.bottom_tab_fragment, mFragments[index]);
            }
            fragmentTransaction.show(mFragments[index]).commitAllowingStateLoss();
        }
        mTabItems[mTabCurrentIndex].setSelected(false);
        mTabItems[index].setSelected(true);
        mTabCurrentIndex = index;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("tabindex", mTabCurrentIndex);
    }
}
