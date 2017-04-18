package com.penner.android.view.bottomtab;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.penner.android.R;

/**
 * Created by PennerYu on 15/10/14.
 */
public class BottomTabItemView extends RelativeLayout {

    private ImageView mTabImage;
    private TextView mTabText;
    private TextView mUnreadText;

    public BottomTabItemView(Context context) {
        this(context, null, 0);
    }

    public BottomTabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(int tabImgResid, String tabName) {
        mTabImage.setImageDrawable(ContextCompat.getDrawable(getContext(), tabImgResid));
        mTabText.setText(tabName);
    }

    public void setUnreadText(int count) {
        if (count > 0) {
            String tips;
            if (count > 99) {
                tips = "...";
            } else {
                tips = String.valueOf(count);
            }
            mUnreadText.setText(tips);
            mUnreadText.setVisibility(View.VISIBLE);
        } else {
            mUnreadText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        mTabImage.setSelected(selected);
        mTabText.setSelected(selected);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTabImage = (ImageView)findViewById(R.id.bottom_tab_item_img);
        mTabText = (TextView)findViewById(R.id.bottom_tab_item_txt);
        mUnreadText = (TextView)findViewById(R.id.bottom_tab_item_unreadtxt);
    }
}
