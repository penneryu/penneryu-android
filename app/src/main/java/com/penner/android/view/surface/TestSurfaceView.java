package com.penner.android.view.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by penneryu on 16/6/23.
 */
public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mSurfaceHolder;

    public TestSurfaceView(Context context) {
        this(context, null, 0);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public Thread myThread = new Thread() {
        @Override
        public void run() {
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            Canvas canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                canvas.drawRect(new Rect(10, 10, 100, 50), paint);
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    };
}
