package com.penner.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.penner.android.base.BaseActivity;
import com.penner.android.view.surface.PennerSurfaceView;
import com.penner.android.view.surface.TestSurfaceView;

public class ScaleGestureActivity extends BaseActivity {

    private Bitmap mBitmap;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ScaleGestureDetector mScaleDetetor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_gesture);

//        mSurfaceView = (PennerSurfaceView)findViewById(R.id.scale_view);
//        mSurfaceHolder = mSurfaceView.getHolder();
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.chat_show_1);
//        mScaleDetetor = new ScaleGestureDetector(this, new ScaleGestureListener());



//        Canvas canvas = mSurfaceHolder.lockCanvas();
//        canvas.drawBitmap(mBitmap, 0f, 0f, null);
//        mSurfaceHolder.unlockCanvasAndPost(canvas);
//        mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
//        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return mScaleDetetor.onTouchEvent(event);
//    }

    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        public ScaleGestureListener() {}

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Matrix matrix = new Matrix();
            float scale = detector.getScaleFactor() / 3;
            matrix.setScale(scale, scale);

            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(mBitmap, matrix, null);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
            mSurfaceHolder.unlockCanvasAndPost(canvas);

            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

}
