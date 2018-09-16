package com.toobei.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.xsl781.utils.Logger;

/**
 * 可拖动并自动吸附ImageView
 * Created by hasee-pc on 2017/1/11.
 */

public class FloatImageView extends ImageView {
    private int rootViewWidth;
    private int rootViewHeight;
    private int lastX, lastY; // 记录移动的最后的位置
    private int btnHeight, btnWidth;
    private int downx, downy; //记录点下去的位置
    private long downTime;
    private long upTime;
    private int upX, upY;
    private OnSingleTouchListener singleTouchListener;
    private RelativeLayout.LayoutParams lp;
    private View v;
    private ScaleAnimation enterAnim;
    private boolean hasStartFreshWelfareAnimation;
    private boolean hasInitFreshWelfare;

    public FloatImageView(Context context) {
        super(context);
        Logger.e("FloatImageView(Context context)");
    }

    public FloatImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        v = this;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (v == null) {
            v = this;
        }

        // 获取Action
        int ea = event.getAction();
        switch (ea) {
            case MotionEvent.ACTION_DOWN: // 按下
                downTime = System.currentTimeMillis();
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                downx = (int) event.getRawX();
                downy = (int) event.getRawY();
//                Logger.e("downX==" + lastX);
//                Logger.e("downY==" + lastY);

                /*
                 *这里获取的宽高可能会因为父容器的布局参数产生偏差，
                 * 如果发现有误，最好在父容器布局完成后，
                 * 再用setViewHeight()，setViewHeight()方法传入真是的宽高
                 *
                 */
                if (rootViewWidth == 0) {
                    rootViewWidth = v.getRootView().getWidth();
                }

                if (rootViewHeight == 0) {
                    rootViewHeight = v.getRootView().getHeight();
                }
                btnHeight = v.getHeight();
                btnWidth = v.getWidth();

                break;
            case MotionEvent.ACTION_MOVE: // 移动
                // 移动中动态设置位置
//                Logger.e("rootViewHeight=="+ rootViewHeight);
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > rootViewWidth) {
                    right = rootViewWidth;
                    left = right - v.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if (bottom > rootViewHeight) {
                    bottom = rootViewHeight;
                    top = bottom - v.getHeight();
                }
                setLayoutMargins(left, top, right, bottom);
                // 将当前的位置再次设置
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
//                Logger.e("moveLastx=="+ lastX);
//                Logger.e("moveLasty=="+ lastY);

                break;
            case MotionEvent.ACTION_UP: // 抬起
                upTime = System.currentTimeMillis();
                upX = (int) event.getRawX();
                upY = (int) event.getRawY();
//                        // 向四周吸附
                int dx1 = upX - lastX;
                int dy1 = upY - lastY;
                int left1 = v.getLeft() + dx1;
                int top1 = v.getTop() + dy1;
                int right1 = v.getRight() + dx1;
                int bottom1 = v.getBottom() + dy1;
                if (top1 < 50) {  //向上吸附
//                        v.layout(left1, 0, right1, btnHeight);
                    setLayoutMargins(left1, 0, 0, 0);
                } else if (bottom1 > (rootViewHeight - 50)) {   //向下吸附
//                        v.layout(left1, (rootViewHeight - btnHeight), right1, rootViewHeight);
                    setLayoutMargins(left1, (rootViewHeight - btnHeight), 0, 0);
                } else {
                    if (left1 < (rootViewWidth / 2)) {  //在父容器的左半边
                        //向左吸附
//                        v.layout(0, top1, btnWidth, bottom1);
                        setLayoutMargins(0, top1, 0, 0);
                    } else {//在父容器的右半边
                        //向右吸附
//                        v.layout((rootViewWidth - btnWidth), top1, rootViewWidth, bottom1);
                        setLayoutMargins((rootViewWidth - btnWidth), top1, 0, 0);
                    }
                }
//判断是否是点击
                int MoveX = Math.abs(upX - downx);
                int MoveY = Math.abs(upY - downy);
                if ((upTime - downTime) < 200 && (MoveX < 20 || MoveY < 20)) {
                    if (singleTouchListener != null) {
                        singleTouchListener.onSingleTouch(v);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置控件的Margins值，
     * 这样可以永久保存控件的位置，防止页面内其他控件渲染引起父容器重新布局而导致的回到原位的影响
     */
    private void setLayoutMargins(int left, int top, int right, int bottom) {
        if (lp == null) {
            lp = new RelativeLayout.LayoutParams(btnWidth, btnHeight);
        }
        lp.setMargins(left, top, right, bottom);
        setLayoutParams(lp);
    }

    /**
     * 开启呼吸动画
     */
    public void startFreshWelfare() {
        if (!hasInitFreshWelfare){
            initFreshWelfare();
        }

        if (!hasStartFreshWelfareAnimation) {
            if (v == null) {
                v = this;
            }
            v.startAnimation(enterAnim);
            hasStartFreshWelfareAnimation = true;
        }
    }

    /**
     * 关闭呼吸动画
     */
    public void stopFreshWelfare() {
        if (v == null) {
            v = this;
        }
        v.clearAnimation();
        hasStartFreshWelfareAnimation = false;
    }

    /**
     * 初始化呼吸效果触摸事件和动画
     */
    public void initFreshWelfare() {
        if (v == null) {
            v = this;
        }
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 触摸时取消动画，并缩小，有按下的感觉
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setScaleX(0.8f);
                    v.setScaleY(0.8f);
                    if (v.getAnimation() != null) {
                        v.getAnimation().cancel();
                    }
                } else { // 松手后，恢复大小，并继续呼吸效果
                    v.setScaleX(1.0f);
                    v.setScaleY(1.0f);
                    freshWelfareAni();
                }

                return false;
            }
        });
        // 放大小时view，完全显示后开始呼吸效果
        if (enterAnim == null) {
            enterAnim = new ScaleAnimation(0f, 0.9f, 0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        enterAnim.setDuration(1000);
        // enterAnim.setInterpolator(new AccelerateInterpolator(2f));
        enterAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                freshWelfareAni();
            }
        });
        hasInitFreshWelfare = true;
    }

    private void freshWelfareAni() {
        if (v == null) {
            v = this;
        }
        ScaleAnimation anim = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(850);
        anim.setRepeatMode(Animation.REVERSE); //重复模式 反转
        anim.setRepeatCount(Animation.INFINITE); // 无限循环
        v.setAnimation(anim);
        v.startAnimation(v.getAnimation());
    }


    public void setOnSingleTouch(OnSingleTouchListener listener) {
        this.singleTouchListener = listener;
    }

    public void setViewHeight(int height) {
        rootViewHeight = height;
    }

    public void setViewWitdh(int width) {
        rootViewWidth = width;
    }

    public interface OnSingleTouchListener {

        void onSingleTouch(View v);
    }

}


