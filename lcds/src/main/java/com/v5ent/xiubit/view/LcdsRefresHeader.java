package com.v5ent.xiubit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.toobei.common.TopApp;
import com.toobei.common.view.FrameAnimDrawable;
import com.v5ent.xiubit.R;

import org.xsl781.utils.Logger;

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

public class LcdsRefresHeader extends RelativeLayout implements RefreshHeader {


    private ImageView progressIv;
    private TextView headerTitle;
    FrameAnimDrawable frameAnimDrawable;
    private final int ROTATE_ANIM_DURATION = 180;
    private int arrowSrc;
    private SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private Context mContext;
    private long freshTime;
    private DateFormat mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
    private int mBackgroundColor = 0;
    private RefreshKernel mRefreshKernel;
    private ViewAnimator rotationAnimal;
    private boolean pullDownAnimalRuning;

    public LcdsRefresHeader(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public LcdsRefresHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public LcdsRefresHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);

    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        frameAnimDrawable = new FrameAnimDrawable(10,getRes(R.array.lcds_pullrefresh_downing_resArrayId), context.getResources(),false);
        
        View view = LayoutInflater.from(context).inflate(R.layout.layout_lcds_refresh_header, this, true);
        progressIv = (ImageView) view.findViewById(R.id.progressIv);
        headerTitle = view.findViewById(R.id.default_header_title);

        if (freshTime==0){
            freshTime = System.currentTimeMillis();
        }

        freshTime = System.currentTimeMillis();
    }

    private void pullDownAnimalStart(){
        progressIv.setImageDrawable(frameAnimDrawable);
        progressIv.setVisibility(View.VISIBLE);
        frameAnimDrawable.start();
        pullDownAnimalRuning = true;
    }

    private void loadingAnimalStart(){
        try {
            progressIv.setImageResource(R.drawable.lcds_pull_refresh_loading);
            rotationAnimal = ViewAnimator.animate(progressIv).rotation(0,360).duration(1000).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int[] getRes(int resArrayId){
        TypedArray typedArray = TopApp.getInstance().getResources().obtainTypedArray(resArrayId);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i >= 0 && i <= len-1 ; i++) {
            resId[i] = typedArray.getResourceId(i, -1);
        }
        typedArray.recycle();
        return resId;
    }


    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        Logger.d("refresh--onPullingDown-:"+ percent+";"+offset+";"+headerHeight+";"+extendHeight);
        if (percent > 0.8 && ! pullDownAnimalRuning){
            pullDownAnimalStart();
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
        Logger.d("refresh--onReleasing-:"+ percent+";"+offset+";"+headerHeight+";"+extendHeight);
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
            } else {
                headerTitle.setTextColor(0xffffffff);
            }
        }
    }

    public LcdsRefresHeader setAccentColor(int accentColor) {
        headerTitle.setTextColor(accentColor);
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
        

        freshTime = System.currentTimeMillis();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 200;//延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                Logger.d("refresh--PullDownToRefresh");
                pullDownAnimalRuning = false;
                progressIv.setImageDrawable(null);
                break;
            case Refreshing:
                Logger.d("refresh--Refreshing");
                loadingAnimalStart();
                break;
            case ReleaseToRefresh:
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
