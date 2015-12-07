package com.penner.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.penner.android.base.BaseActivity;
import com.penner.android.utils.LogUtils;
import com.penner.android.view.bottomtab.BottomTabItemView;
import com.penner.android.view.bottomtab.contact.ContactFragment;
import com.penner.android.view.bottomtab.files.FilesFragment;
import com.penner.android.view.bottomtab.penner.PennerFragment;
import com.penner.android.view.bottomtab.profile.ProfileFragment;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

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

//    private Socket mSocket; {
//        try {
//            IO.Options opts = new IO.Options();
//            opts.path = "/io";
//            opts.forceNew = true;
//            opts.reconnection = true;
//
//            mSocket = IO.socket("https://s.pubu.im", opts);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);

        initTabs();
        initContents();

//        mSocket.connect();
//        mSocket.on("io:msg", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                LogUtils.d("io:msg", args[0].toString());
//            }
//        });
//
//        mSocket.emit("io:uid", "pubuim", "CKNW45Aypmfmv5/eRWv1DKnQ80YzMLLekqV5Orax218h1J1Ia+URB1VvHHRwSt/0fk/Pk3mqOUNIU7l1RDcZh5F9YytI7SPLEiVQqnRUceJxEGVYoVXpY+MfHlBABX8PAjdPOOtv6jGXkjqCl5J0aOQQnJvSd5FbS0nsk3DDoLI/n88f2ZtBBBekE/qB/MWu0AXVPetYcBcOcj15FtzQUg==");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/messages|child.create");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/messages|child.update");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/messages|child.delete");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/channels|child.create");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/channels|child.update");
//        mSocket.emit("io:sub", "pubuim/T54b8dbd43fc0cc701da0925c/channels|child.delete");
    }

    @Override
    protected String getToolbarTitle() {
        return "PennerYu";
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
        mTabItems[0].setSelected(true);
    }

    private void initContents() {
        mPennerFragment = new PennerFragment();
        mContactFragment = new ContactFragment();
        mFilesFragment = new FilesFragment();
        mProfileFragment = new ProfileFragment();

        mFragments = new Fragment[] {mPennerFragment, mContactFragment, mFilesFragment, mProfileFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.bottom_tab_fragment, mPennerFragment)
                .add(R.id.bottom_tab_fragment, mContactFragment)
                .add(R.id.bottom_tab_fragment, mFilesFragment)
                .add(R.id.bottom_tab_fragment, mProfileFragment)
                .show(mPennerFragment)
                .hide(mContactFragment).hide(mFilesFragment).hide(mProfileFragment)
                .commit();
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
        if (mTabCurrentIndex != index) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(mFragments[mTabCurrentIndex]);
            if (!mFragments[index].isAdded()) {
                fragmentTransaction.add(R.id.bottom_tab_fragment, mFragments[index]);
            }
            fragmentTransaction.show(mFragments[index]).commit();
        }
        mTabItems[mTabCurrentIndex].setSelected(false);
        mTabItems[index].setSelected(true);
        mTabCurrentIndex = index;
    }
}
