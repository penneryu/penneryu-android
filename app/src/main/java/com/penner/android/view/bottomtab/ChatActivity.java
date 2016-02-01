package com.penner.android.view.bottomtab;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.penner.android.R;
import com.penner.android.base.BaseActivity;
import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.LocalConversationFactory;
import com.penner.android.data.bottomtab.LocalMessageFactory;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.model.bottomtab.penner.ChatAdapter;
import com.penner.android.utils.Constants;
import com.penner.android.utils.PennerUtils;

import java.io.File;
import java.util.List;

public class ChatActivity extends BaseActivity {

    public static final int REQUEST_CODE_CAMERA = 1;
    public static final int REQUEST_CODE_LOCAL = 2;

    private int mDelta;
    private int mCurrentPage = 1;
    private int mMaxPage = 1;
    private int mPageSize = 12;

    private RecyclerView mChatList;
    private LinearLayoutManager mLayoutManager;
    private View mMore;
    private View mBtnTakePicture;
    private View mBtnPicture;
    private Button mBtnMore;
    private Button mBtnSend;
    private EditText mSendEditText;
    private LinearLayout mFaceContainer;
    private LinearLayout mBtnContainer;
    private SwipeRefreshLayout mSwipeLayout;

    private File mCameraFile;
    private ChatAdapter mChatAdapter;
    private ConversationInfo mConversationInfo;
    private LocalConversationFactory mConversationFactory;
    private LocalMessageFactory mMessageFactory;
    private List<MessageInfo> mMessageInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithoutToolbar(R.layout.activity_chat);

        Intent intent = getIntent();
        if (intent == null) return;

        mConversationInfo = intent.getParcelableExtra(Constants.CONVINFO_EXTRA);
        mMessageFactory = new LocalMessageFactory(this);
        mConversationFactory = new LocalConversationFactory(this);

        String[] args = new String[] {String.valueOf(mConversationInfo.id)};
        int size = (int)mMessageFactory.getRecordCount("convId=?", args);
        mDelta = size % mPageSize;
        mMaxPage = (size / mPageSize) + (mDelta > 0 ? 1 : 0);
        mMessageInfos = mMessageFactory.findRecords(getLastPageIndex(), mPageSize, "convId=?", args);

        mChatList = (RecyclerView) findViewById(R.id.chat_list);
        mLayoutManager = new LinearLayoutManager(this);
        mChatList.setLayoutManager(mLayoutManager);
        mChatAdapter = new ChatAdapter(this, mConversationInfo, mMessageInfos);
        mChatList.setAdapter(mChatAdapter);
        mChatList.scrollToPosition(mMessageInfos.size() - 1);

        mMore = findViewById(R.id.chat_more);
        mBtnTakePicture = findViewById(R.id.chat_btn_takepicture);
        mBtnPicture = findViewById(R.id.chat_btn_picture);
        mBtnMore = (Button)findViewById(R.id.chat_btn_more);
        mBtnSend = (Button)findViewById(R.id.chat_btn_send);
        mSendEditText = (EditText)findViewById(R.id.chat_edit_sendmsg);
        mFaceContainer = (LinearLayout)findViewById(R.id.chat_face_container);
        mBtnContainer = (LinearLayout)findViewById(R.id.chat_btn_container);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.chat_swipelayout);

        getToolbar().setTitle(mConversationInfo.userName);
        setupToolbarWithoutTitle();

        mBtnSend.setOnClickListener(v -> {
            String content = mSendEditText.getText().toString();
            saveMessageInfo(content, MessageInfo.TEXT_TYPE);
        });
        mSendEditText.setOnClickListener(v1 -> {
            mChatList.scrollToPosition(mMessageInfos.size() - 1);
            if (mMore.getVisibility() == View.VISIBLE) {
                mMore.setVisibility(View.GONE);
            }
        });
        mSendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mBtnMore.setVisibility(View.GONE);
                    mBtnSend.setVisibility(View.VISIBLE);
                } else {
                    mBtnMore.setVisibility(View.VISIBLE);
                    mBtnSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mChatList.setOnTouchListener((v, event) -> {
            PennerUtils.hideKeyboard(ChatActivity.this);
            mMore.setVisibility(View.GONE);
            return false;
        });
        mBtnMore.setOnClickListener(v -> {
            if (mMore.getVisibility() == View.GONE) {
                PennerUtils.hideKeyboard(ChatActivity.this);
                mMore.setVisibility(View.VISIBLE);
                mFaceContainer.setVisibility(View.GONE);
                mBtnContainer.setVisibility(View.VISIBLE);
            } else {
                mMore.setVisibility(View.GONE);
            }
        });
        mBtnTakePicture.setOnClickListener(v -> {
            if (!PennerUtils.isExitsSdcard()) {
                PennerUtils.showSnackbar(mBtnTakePicture, R.string.chat_not_takepic);
            } else {
                mCameraFile = PennerUtils.getCameraFile();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
                startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
            }
        });
        mBtnPicture.setOnClickListener(v -> {
            Intent picIntent;
            if (Build.VERSION.SDK_INT < 19) {
                picIntent = new Intent(Intent.ACTION_GET_CONTENT);
                picIntent.setType("image/*");
            } else {
                picIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(picIntent, REQUEST_CODE_LOCAL);
        });
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeLayout.setOnRefreshListener(() -> {
            if (++mCurrentPage <= mMaxPage) {
                MessageInfo messageInfo = mMessageInfos.get(0);
                List<MessageInfo> messageInfos = mMessageFactory.findRecords(getLastPageIndex(), mPageSize,
                        "convId=? and time<?", new String[]{String.valueOf(mConversationInfo.id), String.valueOf(messageInfo.time)});
                int position = mLayoutManager.findLastVisibleItemPosition();
                mMessageInfos.addAll(0, messageInfos);
                mChatAdapter.notifyDataSetChanged();
                mChatList.scrollToPosition(position + messageInfos.size() - 1);
                mSwipeLayout.setRefreshing(false);
            } else {
                mSwipeLayout.setRefreshing(false);
            }
        });
    }

    private int getLastPageIndex() {
        int pageIndex = (mMaxPage - mCurrentPage - (mDelta > 0 ? 1 : 0)) * mPageSize + mDelta;
        return pageIndex > 0 ? pageIndex : 0;
    }

    private void saveMessageInfo(String content, int type) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.type = type;
        messageInfo.convId = mConversationInfo.id;
        messageInfo.time = System.currentTimeMillis();
        messageInfo.fromId = "PenenrYu";
        messageInfo.toId = mConversationInfo.userId;
        messageInfo.body = content;

        mConversationInfo.time = messageInfo.time;
        mConversationInfo.messageTips = content;

        mMessageFactory.insertRecord(messageInfo);
        mConversationFactory.updateRecord(mConversationInfo);

        mMessageInfos.add(messageInfo);
        mChatAdapter.notifyDataSetChanged();
        mSendEditText.setText("");
        mChatList.scrollToPosition(mMessageInfos.size() - 1);
    }

    private void sendPicture(Uri selectedImage) {
        Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String picturePath = cursor.getString(cursor.getColumnIndex("_data"));
            cursor.close();
            if (picturePath == null || picturePath.equals("null")) {
                PennerUtils.showSnackbar(mBtnPicture, R.string.chat_not_picture);
            } else {
                sendPicture(String.format("file:///%s", picturePath));
            }
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                PennerUtils.showSnackbar(mBtnPicture, R.string.chat_not_picture);
            } else {
                sendPicture(String.format("file:///%s", file.getAbsolutePath()));
            }
        }
    }

    private void sendPicture(final String filePath) {
        saveMessageInfo(filePath, MessageInfo.FILE_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_LOCAL) {
                if (mCameraFile != null && mCameraFile.exists()) {
                    sendPicture(String.format("file:///%s", mCameraFile.getAbsolutePath()));
                }
            } else if (requestCode == REQUEST_CODE_CAMERA) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicture(selectedImage);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mConversationInfo.type == ConversationInfo.PERSONAL_TYPE) {
            menu.add(0, 0, 0, R.string.chat_personal).setIcon(R.mipmap.chat_single_setting).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(0, 1, 0, R.string.channelinfo).setIcon(R.mipmap.chat_group_setting).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == 0) {
//            Intent intent = new Intent(this, UserInfoActivity.class);
//            intent.putExtra(Constants.USERID_EXTRA, mConversationInfo.userId);
//            intent.putExtra(Constants.USERINFO_FROM_EXTRA, UserInfoActivity.CHAT_FROM);
//            startActivity(intent);
//            return true;
//        } else if (id == 1) {
//            Intent intent = new Intent(this, ChannelInfoActivity.class);
//            intent.putExtra(Constants.CONVINFO_EXTRA, mConversationInfo);
//            startActivity(intent);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
