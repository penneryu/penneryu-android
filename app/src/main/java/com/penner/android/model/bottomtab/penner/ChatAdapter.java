package com.penner.android.model.bottomtab.penner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.penner.android.R;
import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.data.bottomtab.MessageInfo;
import com.penner.android.view.bottomtab.penner.chat.BaseMessageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by PennerYu on 15/11/4.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHodler> {

    private static final int MESSAGE_TYPE_RECV_TXT = 0;
    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 3;
    private static final int MESSAEG_TYPE_SYSTEM = 4;

    private Context mContext;
    private ConversationInfo mConversationInfo;
    private List<MessageInfo> mMessageInfos;

    public ChatAdapter(Context context, ConversationInfo conversationInfo, List<MessageInfo> messageInfos) {
        mContext = context;
        mConversationInfo = conversationInfo;
        mMessageInfos = messageInfos;
    }

    @Override
    public ChatViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case MESSAGE_TYPE_RECV_TXT:
                view = inflater.inflate(R.layout.chat_msg_txt_received, parent, false);
                break;
            case MESSAGE_TYPE_SENT_TXT:
                view = inflater.inflate(R.layout.chat_msg_txt_sent, parent, false);
                break;
            case MESSAGE_TYPE_RECV_IMAGE:
                view = inflater.inflate(R.layout.chat_msg_img_received, parent, false);
                break;
            case MESSAGE_TYPE_SENT_IMAGE:
                view = inflater.inflate(R.layout.chat_msg_img_sent, parent, false);
                break;
            case MESSAEG_TYPE_SYSTEM:
                view = inflater.inflate(R.layout.chat_msg_system, parent, false);
                break;
        }
        if (view != null) {
            ChatViewHodler viewHodler = new ChatViewHodler(view);
            return viewHodler;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ChatViewHodler holder, int position) {
        MessageInfo message = mMessageInfos.get(position);
        BaseMessageView messageView = (BaseMessageView) holder.itemView;
        messageView.bindViewHolder(holder, position > 0 ? mMessageInfos.get(position - 1) : null, message, mConversationInfo, position);
    }

    @Override
    public int getItemCount() {
        return mMessageInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageInfo message = mMessageInfos.get(position);
        if (message.type == MessageInfo.TEXT_TYPE) {
            return message.direct == MessageInfo.RECEIVED_DIRECT ? MESSAGE_TYPE_RECV_TXT
                    : MESSAGE_TYPE_SENT_TXT;
        } else if (message.type == MessageInfo.FILE_TYPE) {
            return message.direct == MessageInfo.RECEIVED_DIRECT ? MESSAGE_TYPE_RECV_IMAGE
                    : MESSAGE_TYPE_SENT_IMAGE;
        } else if (message.type == MessageInfo.SYSTEM_TYPE) {
            return  MESSAEG_TYPE_SYSTEM;
        }
        return super.getItemViewType(position);
    }

    public static class ChatViewHodler extends RecyclerView.ViewHolder {
        @Optional
        @InjectView(R.id.chat_msg_sending)
        public ProgressBar pb;
        @Optional
        @InjectView(R.id.chat_msg_status)
        public ImageView staus;
        @Optional
        @InjectView(R.id.chat_msg_userid)
        public TextView userId;

        @InjectView(R.id.chat_msg_userhead)
        public ImageView head;
        @InjectView(R.id.chat_msg_timestamp)
        public TextView timestamp;
        @InjectView(R.id.chat_msg_content)
        public View content;

        public ChatViewHodler(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
