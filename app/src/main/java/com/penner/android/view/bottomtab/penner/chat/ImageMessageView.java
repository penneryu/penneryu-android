package com.penner.android.view.bottomtab.penner.chat;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.model.bottomtab.penner.ChatAdapter;

/**
 * Created by PennerYu on 15/11/4.
 */
public class ImageMessageView extends BaseMessageView {

    private Context mContext;

    public ImageMessageView(Context context) {
        this(context, null);
    }

    public ImageMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }

    @Override
    public void bindViewHolder(ChatAdapter.ChatViewHodler holder, MessageInfo preMessageInfo, MessageInfo messageInfo, ConversationInfo conversationInfo, int position) {
        super.bindViewHolder(holder, preMessageInfo, messageInfo, conversationInfo, position);

//        AsyncImageView content = (AsyncImageView)holder.content;
//        content.setImageUrl(messageInfo.body, R.mipmap.chat_default_image);
//        content.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ShowImageActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
    }
}
