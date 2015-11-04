package com.penner.android.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by PennerYu on 15/11/4.
 */
public class ChatRecyclerView extends RecyclerView {

    private int mOldPosition;

    public ChatRecyclerView(Context context) {
        super(context);
    }

    public ChatRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        mOldPosition = position;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (h < oldh) {
            scrollToPosition(mOldPosition);
        }
    }
}
