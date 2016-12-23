package com.penner.android.model.bottomtab.penner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.penner.android.R;
import com.penner.android.data.bottomtab.ConversationInfo;
import com.penner.android.util.Constants;
import com.penner.android.util.DateUtils;
import com.penner.android.util.SmileUtils;
import com.penner.android.view.bottomtab.ChatActivity;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PennerYu on 15/10/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecylerViewHodler> {

    private Context mContext;
    private List<ConversationInfo> mConversationInfos;

    public RecyclerAdapter(Context context, List<ConversationInfo> conversationInfos) {
        mContext = context;
        mConversationInfos = conversationInfos;
    }

    @Override
    public RecylerViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.bottom_conversation_item_common, parent, false);
        final RecylerViewHodler viewHodler = new RecylerViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecylerViewHodler holder, final int position) {
        final ConversationInfo info = mConversationInfos.get(position);
        holder.name.setText(info.userName);
        if (info.unreadCount > 0) {
            holder.unread.setText(String.valueOf(info.unreadCount));
            holder.unread.setVisibility(View.VISIBLE);
        } else {
            holder.unread.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(info.messageTips)) {
            holder.content.setText(SmileUtils.getSmiledText(mContext, info.messageTips), TextView.BufferType.SPANNABLE);
            holder.time.setText(DateUtils.getTimestampString(new Date(info.time)));
//            if (messageInfo.direct == PubuMessageInfo.Direct.SEND
//                    && messageInfo.status == PubuMessageInfo.Status.FAIL) {
//                holder.state.setVisibility(View.VISIBLE);
//            } else {
            holder.state.setVisibility(View.GONE);
//            }
        }
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(Constants.CONVINFO_EXTRA, info);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mConversationInfos.size();
    }

    class RecylerViewHodler extends RecyclerView.ViewHolder {

        @BindView(R.id.home_cov_common_root)
        View root;
        @BindView(R.id.home_cov_common_name)
        TextView name;
        @BindView(R.id.home_cov_common_unread)
        TextView unread;
        @BindView(R.id.home_cov_common_content)
        TextView content;
        @BindView(R.id.home_cov_common_time)
        TextView time;
        @BindView(R.id.home_cov_common_msgstate)
        ImageView state;
        @BindView(R.id.home_cov_common_avatar)
        ImageView avatar;

        public RecylerViewHodler(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
