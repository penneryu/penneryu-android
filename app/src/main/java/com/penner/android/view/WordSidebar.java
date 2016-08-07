package com.penner.android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.penner.android.util.PennerUtils;

/**
 * Created by PennerYu on 15/10/15.
 */
public class WordSidebar extends View {

    private float mHeight;

    private Context mContext;
    private Paint mPaint;
    private TextView mHeader;

    private OnSlideListener mSlideListener;

    private String[] sSections = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public WordSidebar(Context context) {
        this(context, null, 0);
    }

    public WordSidebar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WordSidebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setRecyclerView(TextView headView) {
        mHeader = headView;
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(PennerUtils.sp2px(mContext, 10));
    }

    private int sectionForPoint(float y) {
        int index = (int) (y / mHeight);
        if (index < 0) {
            index = 0;
        }
        if (index > sSections.length - 1) {
            index = sSections.length - 1;
        }
        return index;
    }

    private void setHeaderTextAndscroll(MotionEvent event) {
        String headerString = sSections[sectionForPoint(event.getY())];
        mHeader.setText(headerString);

        if (mSlideListener != null) {
            mSlideListener.slideTo(headerString);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float center = getWidth() / 2;
        mHeight = getHeight() / sSections.length;
        for (int i = sSections.length - 1; i > -1; i--) {
            canvas.drawText(sSections[i], center, mHeight * (i+1), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setHeaderTextAndscroll(event);
                mHeader.setVisibility(View.VISIBLE);
                return true;
            case MotionEvent.ACTION_MOVE:
                setHeaderTextAndscroll(event);
                return true;
            case MotionEvent.ACTION_UP:
                mHeader.setVisibility(View.INVISIBLE);
                return true;
            case MotionEvent.ACTION_CANCEL:
                mHeader.setVisibility(View.INVISIBLE);
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void setOnslideListener(OnSlideListener listener) {
        mSlideListener = listener;
    }

    public interface OnSlideListener {
        void slideTo(String header);
    }
}
