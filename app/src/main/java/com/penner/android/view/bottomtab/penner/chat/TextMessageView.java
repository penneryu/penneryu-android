package com.penner.android.view.bottomtab.penner.chat;

import android.content.Context;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.model.bottomtab.penner.ChatAdapter;
import com.penner.android.util.SmileUtils;

/**
 * Created by PennerYu on 15/11/4.
 */
public class TextMessageView extends BaseMessageView {

    public TextMessageView(Context context) {
        super(context);
    }

    public TextMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void bindViewHolder(ChatAdapter.ChatViewHodler holder, MessageInfo preMessageInfo, MessageInfo messageInfo, ConversationInfo conversationInfo, int position) {
        super.bindViewHolder(holder, preMessageInfo, messageInfo, conversationInfo, position);

        Spannable span = SmileUtils.getSmiledText(mContext, messageInfo.body);
        TextView content = (TextView)holder.content;
        content.setText(span, TextView.BufferType.SPANNABLE);
        if (messageInfo.direct == MessageInfo.SEND_DIRECT) {
            switch (messageInfo.state) {
                case MessageInfo.SUCESS_STATE:    // 发送成功
                    holder.pb.setVisibility(View.GONE);
                    holder.staus.setVisibility(View.GONE);
                    break;
                case MessageInfo.FAIL_STATE:       // 发送失败
                    holder.pb.setVisibility(View.GONE);
                    holder.staus.setVisibility(View.VISIBLE);
                    break;
                case MessageInfo.INPROGRESS_STATE: // 发送中
                    holder.pb.setVisibility(View.VISIBLE);
                    holder.staus.setVisibility(View.GONE);
                    break;
                default:         // 发送消息
                    holder.staus.setVisibility(View.GONE);
                    holder.pb.setVisibility(View.VISIBLE);
            }
        }
    }
}
