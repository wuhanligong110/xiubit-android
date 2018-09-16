package com.toobei.common.view;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author Frederico Silva (fredericojssilva@gmail.com)
 * @date Oct 31, 2014
 * <p>
 * 1 myShowTipsView = new MyShowTipsView(ctx, ivInfo, R.drawable.img_guide_service);//new对象
 * 构造传入参数
 * <p>
 * 2 设置点击监听
 * <p>
 * 3 myShowTipsView.setDisplayOneTime(true);// 设置是否只显示一次
 * <p>
 * 4 myShowTipsView.show(ctx);
 */
public class MyShowTipsView extends RelativeLayout {
    private static final String TAG = "MyShowTipsView";
    private Point showhintPoints;
    private int radius = 0;
    private boolean custom, displayOneTime = true;
    private String displayOneTimeID = "";  // 控件是否只显示一次的显示状态存储SP的ID
    private int delay = 0;   // 默认显示延时
    private View targetView;
    private int screenX, screenY;    // 屏幕尺寸
    private int background_color, circleColor;
    private int guideImageID; //指引的图片图片资源id
    private int background_alpha = 90; // 默认背景透明 private int background_alpha = 220;
    private ImageView guideImage;   // 指引图片
    private Bitmap bitmap;
    private Canvas temp;
    private Paint paint;
    private Paint bitmapPaint;
 //   private Paint circleline;
    private Paint transparentPaint;
    private PorterDuffXfermode porterDuffXfermode;
    boolean isMeasured;
    // 获取的目标控件的位置
    private int locationX;
    private int locationY;
    private Context context;
    // 是否已经显示过了
    private boolean hasShow;
    // 屏幕尺寸
    private int screenHeightPixels;
    private int screenWidthPixels;
    private int diffX = 0;
    private int diffY = 0;
    private MyShowTipsView.onShowTipHideListener onShowTipHideListener;
    private int mTargetViewHeight;
    private int mTargetViewWidth;
    private int expandWith;
    private int expandHeight;
    private int offsetX;
    private int offsetY;

    public MyShowTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyShowTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context          上下文
     * @param targetView       目标控件
     * @param ImageResID       设置的引导图片
     * @param displayOneTimeID 引导图片存sp时的id
     */
    public MyShowTipsView(Context context, View targetView, int ImageResID, String displayOneTimeID) {

        super(context);
        this.context = context;
        this.targetView = targetView;
        this.guideImageID = ImageResID;
        this.displayOneTimeID = displayOneTimeID;
        init();
    }

    /**
     * @param context          上下文
     * @param targetView       设置的目标控件
     * @param ImageResID       引导图片的资源id
     * @param diffX            引导图片 相对于目标 左上 的位置 X方向的距离
     * @param diffY            引导图片 相对于目标 左上 的位置 Y方向的距离
     * @param displayOneTimeID 引导图片存sp时的id
     */
    public MyShowTipsView(Context context, View targetView, int ImageResID, int diffX, int diffY, String displayOneTimeID) {

        super(context);
        this.context = context;
        this.targetView = targetView;
        this.guideImageID = ImageResID;
        this.displayOneTimeID = displayOneTimeID;
        this.diffX = diffX;
        this.diffY = diffY;
        init();
    }

    /**
     * 在原控件的宽高基础上延伸宽度和高度
     * 必须在show方法前调用
     * @param expandWith
     * @param expandHeight
     */
    public void  setExpandSize(int expandWith,int expandHeight){
        this.expandWith = expandWith;
        this.expandHeight = expandHeight;
    }

    /**
     * 在原控件的起始位置xy方向的偏移量
     * 必须在show方法前调用
     * @param offsetX
     * @param offsetY
     */
    public void  setLocationOffset(int offsetX,int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    private void init() {
        this.setVisibility(View.GONE);
        this.setBackgroundColor(Color.TRANSPARENT);
        paint = new Paint();
        bitmapPaint = new Paint();
      //  circleline = new Paint();
        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Get screen dimensions
        screenX = w;
        screenY = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 设置透明背景
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
            temp = new Canvas(bitmap);
        }

        if (background_color != 0) paint.setColor(background_color);
        else paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(background_alpha);
        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);
        transparentPaint.setColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
        transparentPaint.setXfermode(porterDuffXfermode);

        int x = showhintPoints.x;
        int y = showhintPoints.y;
        // temp.drawCircle(x, y, radius, transparentPaint);
        // 绘制高亮区域
        if (radius < 150) { //当控件尺寸小于150时
            RectF rect = new RectF(x - radius, y - mTargetViewHeight / 2, x + radius, y + mTargetViewHeight / 2);
            temp.drawRoundRect(rect, 15.0f, 15.0f, transparentPaint);
        } else {
            RectF rect = new RectF(x - radius + 8, y - mTargetViewHeight / 2 + 5, x + radius - 8, y + mTargetViewHeight / 2 - 5);
            temp.drawRoundRect(rect, 15.0f, 15.0f, transparentPaint);

        }

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        //circleline.setStyle(Paint.Style.STROKE);
//        if (circleColor != 0) circleline.setColor(circleColor);
//        else circleline.setColor(Color.RED);
//        circleline.setAntiAlias(true);
//        circleline.setStrokeWidth(3);
        //绘制高亮区域外边的点画线
        if (radius < 150) {  //当控件小于150时
            RectF rect2 = new RectF(x - radius - 4, y - mTargetViewHeight / 2 - 4, x + radius + 4, y + mTargetViewHeight / 2 + 4);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStyle(Paint.Style.STROKE); //空心线框
            p.setColor(Color.WHITE); //白色
            p.setStrokeWidth(3);//线条宽度
            PathEffect effects = new DashPathEffect(new float[]{10, 6}, 1); //线条样式
            p.setPathEffect(effects); //给画笔设置线条样式
            canvas.drawRoundRect(rect2, 15.0f, 15.0f, p); //画空心圆角矩形
        } else {
            RectF rect2 = new RectF(x - radius + 3, y - mTargetViewHeight / 2, x + radius - 3, y + mTargetViewHeight / 2);
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setStyle(Paint.Style.STROKE);
            p.setColor(Color.WHITE);
            p.setStrokeWidth(3);
            PathEffect effects = new DashPathEffect(new float[]{10, 6}, 1);
            p.setPathEffect(effects);
            canvas.drawRoundRect(rect2, 15.0f, 15.0f, p);
        }


        // canvas.drawCircle(x, y, radius, circleline);
        // canvas.drawLine(x-radius, y-radius, x+radius, y+radius, circleline);
    }

    public void show(final Activity activity) {
        // 判断是否已经显示过
        hasShow = context.getSharedPreferences("showtips", Context.MODE_PRIVATE).getBoolean("ShowTipsViewDisplayOneTimeID" + displayOneTimeID, false);
        //	Log.e(TAG, "是否不是第一次显示guideView=======  " + hasShow);
        if (isDisplayOneTime() && hasShow) {
            setVisibility(View.GONE);
            ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(MyShowTipsView.this);
            return;
        } else {
            // if (isDisplayOneTime())
            // showTipsStore.storeShownId(getDisplayOneTimeID());
            // 存储是否显示一次状态
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) activity.getWindow().getDecorView()).removeView(MyShowTipsView.this);
                ((ViewGroup) activity.getWindow().getDecorView()).addView(MyShowTipsView.this);

                MyShowTipsView.this.setVisibility(View.VISIBLE);
                Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                MyShowTipsView.this.startAnimation(fadeInAnimation);

                final ViewTreeObserver observer = targetView.getViewTreeObserver();
                observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        if (isMeasured) return;
                        mTargetViewHeight = targetView.getHeight() + expandHeight;
                        mTargetViewWidth = targetView.getWidth() + expandWith;

                        if (mTargetViewHeight > 0 && mTargetViewWidth > 0) {
                            isMeasured = true;
                            
                        }

                        if (custom == false) {
                            int[] location = new int[2];
                            targetView.getLocationInWindow(location);
                            int x = location[0] + offsetX + mTargetViewWidth / 2;
                            int y = location[1] + offsetY + mTargetViewHeight / 2;
                            // Log.d("FRED", "X:" + x + " Y: " + y);

                            Point p = new Point(x, y);

                            showhintPoints = p;
                            radius = mTargetViewWidth / 2;
                        } else {
                            int[] location = new int[2];
                            targetView.getLocationInWindow(location);
                            // int x = location[0] + showhintPoints.x;
                            int x = location[0] + offsetX + 30;
                            int y = location[1] + offsetY + 30;
                            // int y = location[1] + showhintPoints.y;
                            // Log.d("FRED", "X:" + x + " Y: " + y);
                            Point p = new Point(x, y);
                            showhintPoints = p;

                        }

                        invalidate();
                        createViews();

                    }
                });
            }
        }, getDelay());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 点击后存储显示与否的状态
                context.getSharedPreferences("showtips", Context.MODE_PRIVATE).edit().putBoolean("ShowTipsViewDisplayOneTimeID" + displayOneTimeID, true).commit();
                // 点击后取消显示
                MyShowTipsView.this.setVisibility(View.GONE);
                if (onShowTipHideListener != null) {
                    onShowTipHideListener.onShowTipHide();
                }
                //判断点击区域是否在高亮区域
                boolean isWidthOk = event.getX() < (targetView.getX() + mTargetViewWidth) && event.getX() > targetView.getX();
                boolean isHeightOk = event.getY() < (targetView.getY() + mTargetViewHeight) && event.getY() > targetView.getY();
                if (!isWidthOk || !isHeightOk) {
                    // return true; //用于设置是否只有高亮区域才可以点击
                }
        }
        //		return super.onTouchEvent(event);
        return true; // 事件消费  不向下传递
    }


    /*
     * Create text views and close button
     */
    private void createViews() {
        this.removeAllViews();

        // 获取目标控件的位置
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        locationX = location[0];
        locationY = location[1];
        // 获取屏幕的尺寸
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenHeightPixels = displayMetrics.heightPixels;
        screenWidthPixels = displayMetrics.widthPixels;
        initGuideImage();// 初始化指引图片

    }

    /**
     * 初始化指引图片
     */
    private void initGuideImage() {
        LayoutParams params;
        guideImage = new ImageView(getContext());
        guideImage.setImageDrawable(ContextCompat.getDrawable(getContext(),guideImageID));
        Options opts = new BitmapFactory.Options();
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), guideImageID, opts);
        int guideImageWidth = decodeResource.getWidth();
        params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.topMargin = locationY + (diffY == 0 ? (mTargetViewHeight + 50) : diffY);
        //Log.e(TAG, "params.topMargin===" + params.topMargin);
        // 判断设置图片在左边还是右边显示

        if (locationX < screenWidthPixels / 4) {// 在目标右边设置guideView

            params.leftMargin = locationX + (diffX == 0 ? (2 * mTargetViewWidth / 3) : diffX);
            //	Log.e(TAG, "params.leftMargin===" + params.leftMargin);
        } else {// 在目标左边设置guideView
            params.leftMargin = locationX + (diffX == 0 ? (-guideImageWidth + mTargetViewWidth / 3) : diffX);
            //	Log.e(TAG, "在目标左边设置guideView------params.leftMargin===" + params.leftMargin);

        }

        guideImage.setLayoutParams(params);
        this.removeAllViews();
        this.addView(guideImage);
    }

    /**
     * 暴露给外界设置指引图片
     */
    public void addGuideImage(int srcId) {

        this.guideImageID = srcId;

    }

    /**
     * 设置 高亮的targetView
     */
    public void setTarget(View v) {
        targetView = v;
    }

    public void setTarget(View v, int x, int y, int radius) {
        custom = true;
        targetView = v;
        Point p = new Point(x, y);
        showhintPoints = p;
        this.radius = radius;
    }

    static Point getShowcasePointFromView(View view) {
        Point result = new Point();
        result.x = view.getLeft() + view.getWidth() / 2;
        result.y = view.getTop() + view.getHeight() / 2;
        return result;
    }

    public boolean isDisplayOneTime() {
        return displayOneTime;
    }

    /**
     * 设置引导是否只显示一次  false 显示多次 ; 默认true只显示一次
     *
     * @param displayOneTime
     */
    public void setDisplayOneTime(boolean displayOneTime) {
        this.displayOneTime = displayOneTime;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getDisplayOneTimeID() {
        return displayOneTimeID;
    }

    public void setDisplayOneTimeID(String displayOneTimeID) {
        this.displayOneTimeID = displayOneTimeID;
    }

    public int getBackground_color() {
        return background_color;
    }

    public void setBackground_color(int background_color) {
        this.background_color = background_color;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getBackground_alpha() {
        return background_alpha;
    }

    public void setBackground_alpha(int background_alpha) {
        if (background_alpha > 255) this.background_alpha = 255;
        else if (background_alpha < 0) this.background_alpha = 0;
        else this.background_alpha = background_alpha;

    }

    public interface onShowTipHideListener {
        public void onShowTipHide();
    }

    public void setOnShowTipHideListener(onShowTipHideListener listener) {
        onShowTipHideListener = listener;
    }

}
