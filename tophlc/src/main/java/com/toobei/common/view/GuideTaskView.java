package com.toobei.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.toobei.common.TopApp;

/**
 * 公司: tophlc
 * 类说明：引导任务视图
 *
 * @date 2016-12-7
 */
public class GuideTaskView extends View {
    private final String TAG = GuideTaskView.class.getName();

    /**
     * 以基准尺寸 1920*1080的标准
     * 指定的能触摸范围  int left, int top, int right, int bottom
     * {0, 0, 1080, 1920}
     */
    private int[] touchRect = null;
    /**
     * 手指位置左上角 x,y 坐标
     */
    private int[] fingerLocation = null;
    private float touchX = 0;
    private float touchY = 0;
    private Bitmap[] fingerBitmaps = null;
    private int curDrawFingerIndex = 0;//当前绘制手指图片的序号 0 - 4
    private Canvas canvas;
    private Paint mBitPaint;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            drawFinger();
        }
    };

    private OnRectViewTouchedListener onRectViewTouchedListener;

    public interface OnRectViewTouchedListener {
        /**
         * 设置的矩形区域被触摸
         */
        public void onRectViewTouched();
    }

    public GuideTaskView(Context context) {
        super(context);
        initPaint();
    }

    public GuideTaskView(Context context, int[] touchRect, int[] fingerLocation, Bitmap[] fingerBitmaps) {
        super(context);
        if (touchRect != null && touchRect.length == 4) {
            this.touchRect = touchRect;
        }
        if (fingerLocation != null && fingerLocation.length == 2) {
            this.fingerLocation = fingerLocation;
        }
        if (fingerBitmaps != null) {
            this.fingerBitmaps = fingerBitmaps;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        //   drawFinger();
    }

    private void initPaint() {
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    /**
     * 绘制手指
     */
    private void drawFinger() {
        if (fingerLocation == null || fingerBitmaps == null) {
            return;
        }
        if (curDrawFingerIndex >= fingerBitmaps.length) {
            curDrawFingerIndex = 0;
        }
        Log.d(TAG, "======drawFinger: ");
        canvas.drawBitmap(fingerBitmaps[curDrawFingerIndex], fingerLocation[0], fingerLocation[1], mBitPaint);
        postInvalidate();

        curDrawFingerIndex++;
        mHandler.sendEmptyMessageDelayed(0x123, 250);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        if (touchRect != null && touchX >= touchRect[0] * TopApp.widthRate && touchX <= touchRect[2] * TopApp.widthRate
                && touchY >= touchRect[1] * TopApp.heightRate && touchY <= touchRect[3] * TopApp.heightRate) {
            Log.d(TAG, "onTouchEvent: ");
            //在设置的触摸矩形内
            if (onRectViewTouchedListener != null) {
                onRectViewTouchedListener.onRectViewTouched();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setOnRectViewTouchedListener(OnRectViewTouchedListener onRectViewTouchedListener) {
        this.onRectViewTouchedListener = onRectViewTouchedListener;
    }

    public int[] getFingerLocation() {
        return fingerLocation;
    }

    public void setFingerLocation(int[] fingerLocation) {
        if (fingerLocation != null && fingerLocation.length == 2) {
            this.fingerLocation = fingerLocation;
        }
    }

    public int[] getTouchRect() {
        return touchRect;
    }

    public void setTouchRect(int[] touchRect) {
        if (touchRect != null && touchRect.length == 4) {
            this.touchRect = touchRect;
        }
    }

    public Bitmap[] getFingerBitmaps() {
        return fingerBitmaps;
    }

    public void setFingerBitmaps(Bitmap[] fingerBitmaps) {
        this.fingerBitmaps = fingerBitmaps;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //当view从窗体移除的时候的回调函数
        fingerBitmaps = null;
        mHandler.removeMessages(0x123);
    }
}
