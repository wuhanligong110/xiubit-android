package com.toobei.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.R;

/**
 * Created by hasee-pc on 2017/1/9.
 */

public class JumpProgressDialog extends Dialog {

    private Animation mLoadingAnimation;
    private ImageView mProgress;
    private String text;
    private TextView mText;
    private Context ctx;

    public JumpProgressDialog(Context context, String text) {
        super(context, R.style.customDialog);
        ctx = context;
        this.text = text;
    }

    public JumpProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jump_progress_dialog);
        initView();
        initLoadingAnimation();

        mProgress.setAnimation(mLoadingAnimation);
    }

    private void initView() {
        mProgress = (ImageView) findViewById(R.id.loadingIv);
        mText = (TextView) findViewById(R.id.text);
        mText.setText(text);
    }

    /**
     * 进度条旋转动画
     */
    private void initLoadingAnimation() {
        mLoadingAnimation = new RotateAnimation(0.0f, 720.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mLoadingAnimation.setFillAfter(true);
        mLoadingAnimation.setInterpolator(new LinearInterpolator());
        mLoadingAnimation.setDuration(3600);
        mLoadingAnimation.setRepeatCount(Animation.INFINITE);
        mLoadingAnimation.setRepeatMode(Animation.RESTART);
    }
}
