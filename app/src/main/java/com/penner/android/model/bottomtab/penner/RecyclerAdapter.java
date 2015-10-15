package com.penner.android.model.bottomtab.penner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.penner.android.R;
import com.penner.android.view.bottomtab.penner.DatabindingActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by PennerYu on 15/10/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecylerViewHodler> {

    private Context mContext;
    private List<String> mList;

    public RecyclerAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecylerViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.penner_recycler_item, parent, false);
        final RecylerViewHodler viewHodler = new RecylerViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecylerViewHodler holder, final int position) {
        String value = mList.get(position);
        holder.title.setText(value);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    mContext.startActivity(new Intent(mContext, DatabindingActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class RecylerViewHodler extends RecyclerView.ViewHolder {

        @InjectView(R.id.penner_recycler_txt_tile)
        TextView title;

        public RecylerViewHodler(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }
}
