package com.penner.android.view.bottomtab.penner.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.model.bottomtab.penner.ChatAdapter;

/**
 * Created by PennerYu on 15/11/4.
 */
public class SystemMessageView extends BaseMessageView {

    public SystemMessageView(Context context) {
        super(context);
    }

    public SystemMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void bindViewHolder(ChatAdapter.ChatViewHodler holder, MessageInfo preMessageInfo, MessageInfo messageInfo, ConversationInfo conversationInfo, int position) {
        super.bindViewHolder(holder, preMessageInfo, messageInfo, conversationInfo, position);

        TextView content = (TextView)holder.content;
        content.setText(messageInfo.body);
    }
}
