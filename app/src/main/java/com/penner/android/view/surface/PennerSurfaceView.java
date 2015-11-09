package com.penner.android.view.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.penner.android.utils.PennerUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PennerYu on 15/11/9.
 */
public class PennerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private SurfaceHolder mSurfaceHolder;

    public PennerSurfaceView(Context context) {
        this(context, null, 0);
    }

    public PennerSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PennerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mTimer = new Timer();
        mTimerTask = new PennerTimerTask();
        mTimer.schedule(mTimerTask, 500, 1000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimerTask = null;
    }


    public class PennerTimerTask extends TimerTask {

        private int count;
        private int size;
        private Paint paint;

        public PennerTimerTask() {
            size = PennerUtils.sp2px(mContext, 16);
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(size);
        }

        @Override
        public void run() {
            if (count > 40)
                return;

            switch(count % 5) {
                case 0:
                    paint.setColor(Color.BLUE);
                    break;
                case 1:
                    paint.setColor(Color.WHITE);
                    break;
                case 2:
                    paint.setColor(Color.YELLOW);
                    break;
                case 3:
                    paint.setColor(Color.RED);
                    break;
                case 4:
                    paint.setColor(Color.GREEN);
                    break;
            }

            Canvas canvas = null;
            try {
                if (count % 2 == 0) {
                    int x = 50;
                    int y = count * size;
                    canvas = mSurfaceHolder.lockCanvas(new Rect(x, y, x + size, y + size));
//                    canvas = mSurfaceHolder.lockCanvas();   //如果锁定全部surface，则surfaceview两个缓存交替更新。
                    canvas.drawRect(new Rect(x, y, x + size, y + size), paint);
                } else {
                    int x = 150;
                    int y = count * size;
                    canvas = mSurfaceHolder.lockCanvas(new Rect(x, y, x + size, y + size));
                    canvas.drawRect(new Rect(x, y, x + size, y + size), paint);
                }
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
//                    mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
//                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            count++;
        }
    }
}
