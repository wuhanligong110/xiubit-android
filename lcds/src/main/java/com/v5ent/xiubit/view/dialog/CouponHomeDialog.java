package com.v5ent.xiubit.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.v5ent.xiubit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/5/5
 */

public class CouponHomeDialog extends Dialog {

    public static final int TYPE_REDPACKET = 1;  //红包
    public static final int TYPE_JOBGRADE = 2;  //职级体验
    public static final int TYPE_ADDFEE = 3;  //加佣金
    public static final int TYPE_ADDFEE_INCOME = 4;  //加佣金
    @BindView(R.id.bannerIv)
    ImageView mBannerIv;
    private int type;
    private final Activity ctx;
    private BtnClickListener mBtnClickListener;
    private  String content;
    @BindView(R.id.closedIv)
    ImageView mClosedIv;
    @BindView(R.id.btn)
    TextView mBtn;
    @BindView(R.id.contentTv)
    TextView mContentTv;
    @BindView(R.id.remindTv)
    TextView mRemindTv;


    public CouponHomeDialog(@NonNull Activity context, int type, String content) {
        super(context, com.toobei.common.R.style.customDialog);
        this.ctx = context;
        this.type = type;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_home_coupon, null);
        setContentView(view);
        ButterKnife.bind(this);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mContentTv.setText(content);
        switch (type) {
            case TYPE_REDPACKET:
                mBannerIv.setImageResource(R.drawable.redpacket_home_dialog_bg);
                mBtn.setText("查看详情");
                break;
            case TYPE_JOBGRADE:
                    if (content.contains("经理")) {
                        mBannerIv.setImageResource(R.drawable.job_grade_home_dialog_bg2);
                    }else if(content.contains("总监")){
                        mBannerIv.setImageResource(R.drawable.job_grade_home_dialog_bg1);
                    }
                    mBtn.setText("查看详情");
                break;
            case TYPE_ADDFEE:
                if (content.contains("加佣")) {
                    mBannerIv.setImageResource(R.drawable.add_fee_home_dialog_bg1);
                }else if(content.contains("奖励")){
                    mBannerIv.setImageResource(R.drawable.add_fee_home_dialog_bg2);
                }
                mBtn.setText("查看详情");
                break;
            case TYPE_ADDFEE_INCOME:
                if (content.contains("加佣")) {
                    mBannerIv.setImageResource(R.drawable.add_fee_home_dialog_bg1);
                    mRemindTv.setText("加佣总收益将于次月15日统一发放");
                }else if(content.contains("奖励")){
                    mBannerIv.setImageResource(R.drawable.add_fee_home_dialog_bg2);
                    mRemindTv.setText("奖励总收益将于次月15日统一发放");
                }
                mRemindTv.setVisibility(View.VISIBLE);
                mBtn.setText("知道了");
                break;
        }


    }

    public CouponHomeDialog setOnBtnClickListener(BtnClickListener listener) {
        mBtnClickListener = listener;
        return this;
    }

    public interface BtnClickListener {

        void onConfirmClick();

        void onCloseClick();

    }


    @OnClick({R.id.btn, R.id.closedIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                if (mBtnClickListener != null) mBtnClickListener.onConfirmClick();

                break;
            case R.id.closedIv:
                if (mBtnClickListener != null) mBtnClickListener.onCloseClick();
                break;
        }
        dismiss();

    }

    @Override
    public void dismiss() {
        if (ctx.isFinishing()) return;
        super.dismiss();
    }
}
