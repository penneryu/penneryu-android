package com.penner.android.model.material;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.penner.android.R;
import com.penner.android.view.material.MaterialDetailActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by PennerYu on 15/10/27.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CellViewHodler> {

    private Context mContext;

    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CellViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext).inflate(R.layout.material_item, parent, false);
        final CellViewHodler viewHodler = new CellViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(CellViewHodler holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MaterialDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CellViewHodler extends RecyclerView.ViewHolder {

        @InjectView(R.id.material_avatar)
        ImageView imageView;

        public CellViewHodler(View itemView) {
            super(itemView);

            ButterKnife.inject(itemView);
        }
    }
}
