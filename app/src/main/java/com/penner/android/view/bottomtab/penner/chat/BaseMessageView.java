package com.penner.android.view.bottomtab.penner.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.model.bottomtab.penner.ChatAdapter;
import com.penner.android.utils.DateUtils;

import java.util.Date;

/**
 * Created by PennerYu on 15/11/4.
 */
public abstract class BaseMessageView extends LinearLayout {

    protected Context mContext;

    public BaseMessageView(Context context) {
        this(context, null);
    }

    public BaseMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
    }

    public void bindViewHolder(ChatAdapter.ChatViewHodler holder, MessageInfo preMessageInfo, MessageInfo messageInfo, ConversationInfo conversationInfo, int position) {
        if (messageInfo.direct == MessageInfo.RECEIVED_DIRECT) {
            if (conversationInfo.type != ConversationInfo.PERSONAL_TYPE) {
                holder.userId.setText(messageInfo.fromId);
                holder.userId.setVisibility(VISIBLE);
            } else {
                holder.userId.setVisibility(GONE);
            }
        }
        if (preMessageInfo == null) {
            holder.timestamp.setText(DateUtils.getTimestampString(new Date(messageInfo.time)));
            holder.timestamp.setVisibility(View.VISIBLE);
        } else {
            if (DateUtils.isShowMessageTime(messageInfo.time, preMessageInfo.time)) {
                holder.timestamp.setText(DateUtils.getTimestampString(new Date(messageInfo.time)));
                holder.timestamp.setVisibility(View.VISIBLE);
            } else {
                holder.timestamp.setVisibility(View.GONE);
            }
        }
    }
}
