package com.v5ent.xiubit.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.v5ent.xiubit.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/18
 */

public class MyDefaultRefresHeader extends RelativeLayout implements RefreshHeader {

    public static String REFRESH_HEADER_PULLDOWN = "下拉刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新";
    public static String REFRESH_HEADER_RELEASE = "松开刷新数据";
    public static String REFRESH_HEADER_FINISH = "刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";
    
    private TextView headerTitle;
    private TextView headerTime;
    private ImageView headerArrow;
    private ProgressBar headerProgressbar;
    private RotateAnimation mRotateUpAnim;
    private RotateAnimation mRotateDownAnim;
    private final int ROTATE_ANIM_DURATION = 180;
    private int arrowSrc;
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private Context mContext;
    private long freshTime;
    private DateFormat mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
    private int mBackgroundColor = 0;
    private RefreshKernel mRefreshKernel;
    
    public MyDefaultRefresHeader(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public MyDefaultRefresHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public MyDefaultRefresHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);

    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        
        View view = LayoutInflater.from(context).inflate(R.layout.layout_my_refresh_header, this, true);
        headerTitle = (TextView) view.findViewById(R.id.default_header_title);
        headerTime = (TextView) view.findViewById(R.id.default_header_time);
        headerArrow = (ImageView) view.findViewById(R.id.default_header_arrow);
        headerProgressbar = (ProgressBar) view.findViewById(R.id.default_header_progressbar);
        headerArrow.setImageResource(R.drawable.ic_pulltorefresh_arrow);

        if (freshTime==0){
            freshTime = System.currentTimeMillis();
        }
        headerTime.setText(mFormat.format(freshTime));
        freshTime = System.currentTimeMillis();
    }


    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRefreshKernel = null;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void setPrimaryColors(int... colors) {
        if (colors.length > 1) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setBackgroundColor(colors[0]);
            }
            headerTitle.setTextColor(colors[1]);
            headerTime.setTextColor(colors[1]&0x00ffffff|0x99000000);
        } else if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setBackgroundColor(colors[0]);
            }
            mBackgroundColor = colors[0];
            if (mRefreshKernel != null) {
                mRefreshKernel.requestDrawBackgoundForHeader(colors[0]);
            }
            if (colors[0] == 0xffffffff) {
                headerTitle.setTextColor(0xff666666);
                headerTime.setTextColor(0xff666666&0x00ffffff|0x99000000);
            } else {
                headerTitle.setTextColor(0xffffffff);
                headerTime.setTextColor(0xaaffffff);
            }
        }
    }

    public MyDefaultRefresHeader setAccentColor(int accentColor) {
        headerTitle.setTextColor(accentColor);
        headerTime.setTextColor(accentColor&0x00ffffff|0x99000000);
        return this;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        if (mBackgroundColor != 0) {
            kernel.requestDrawBackgoundForHeader(mBackgroundColor);
        }
        mRefreshKernel = kernel;
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        if (freshTime==0){
            freshTime = System.currentTimeMillis();
        }
        
        headerTime.setText(mFormat.format(freshTime));
       
        freshTime = System.currentTimeMillis();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        headerArrow.setVisibility(View.VISIBLE);
        headerProgressbar.setVisibility(View.INVISIBLE);
        if (success) {
            headerTitle.setText(REFRESH_HEADER_FINISH);
        } else {
            headerTitle.setText(REFRESH_HEADER_FAILED);
        }
        return 200;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                headerTitle.setText(REFRESH_HEADER_PULLDOWN);
                headerArrow.setVisibility(VISIBLE);
                headerProgressbar.setVisibility(GONE);
                headerArrow.animate().rotation(0);
                break;
            case Refreshing:
                headerTitle.setText(REFRESH_HEADER_REFRESHING);
                headerProgressbar.setVisibility(VISIBLE);
                headerArrow.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                headerTitle.setText(REFRESH_HEADER_RELEASE);
                headerArrow.animate().rotation(180);
                break;
        }
    }

    private Runnable restoreRunable;
    private void restoreRefreshLayoutBackground() {
        if (restoreRunable != null) {
            restoreRunable.run();
            restoreRunable = null;
        }
    }

    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
        if (restoreRunable == null && mSpinnerStyle == SpinnerStyle.FixedBehind) {
            restoreRunable = new Runnable() {
                Drawable drawable = refreshLayout.getLayout().getBackground();
                @Override
                public void run() {
                    refreshLayout.getLayout().setBackground(drawable);
                }
            };
            refreshLayout.getLayout().setBackground(getBackground());
        }
    }
}
