package com.v5ent.xiubit.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.CfpLevelWarningData;

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

public class CfpLevelWarningDialog extends Dialog {

    private final Context ctx;
    private final CfpLevelWarningData data;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.cfpLevelContentTv)
    TextView mCfpLevelContentTv;
    @BindView(R.id.cfpLevelDetail1Tv)
    TextView mCfpLevelDetail1Tv;
    @BindView(R.id.cfpLevelDetail2Tv)
    TextView mCfpLevelDetail2Tv;
    @BindView(R.id.lookTv)
    TextView mLookTv;
    @BindView(R.id.closedIv)
    ImageView mClosedIv;

    public CfpLevelWarningDialog(@NonNull Context context, CfpLevelWarningData data) {
        super(context, com.toobei.common.R.style.customDialog);
        this.ctx = context;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_cfp_level_warning, null);
        setContentView(view);
        ButterKnife.bind(this);
        setCancelable(false);
        initData();
    }


    private void initData() {
        try {
            String cfpLevelTitle = data.getCfpLevelTitle();
            mTitle.setText(cfpLevelTitle.replace("|","\n"));

            String cfpLevelDetail = data.getCfpLevelDetail().replace(" ","");
            if (cfpLevelDetail.endsWith("|")){
            cfpLevelDetail = cfpLevelDetail.substring(0, cfpLevelDetail.length()-1);
            }

            //当|左或右为空的话，只会分割一个字符串
            setText(mCfpLevelDetail1Tv,cfpLevelDetail.replace("|", "\n"));


            String cfpLevelContent = data.getCfpLevelContent();
//        cfpLevelContent = "您本月职级为《见习》，距离升级《总监》";
            if (TextUtils.isEmpty(cfpLevelContent)){
                mCfpLevelContentTv.setVisibility(View.GONE);
                return;
            }

            SpannableString contentSpan = null;
            if(cfpLevelContent.contains("本月职级")){
                String[] split = cfpLevelContent.split("<|>");
                contentSpan = new SpannableString(cfpLevelContent.replace("<","").replace(">",""));
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_black_common)),0,split[0].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_blue_common)),split[0].length(),split[0].length()+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_black_common)),split[0].length()+2,split[0].length()+2+split[2].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_blue_common)),split[0].length()+2+split[2].length(),split[0].length()+split[2].length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else if (cfpLevelContent.contains("已完成总监")){
                int index = cfpLevelContent.indexOf("<");
                String string = cfpLevelContent.replace("<", "").replace(">", "");
                contentSpan = new SpannableString(string);
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_black_common6c)),0,index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                contentSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_black_common)),index,string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            mCfpLevelContentTv.setText(contentSpan);
            mCfpLevelContentTv.setVisibility(View.VISIBLE);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }


    private void setText(TextView tv, String str) {
        if (TextUtils.isEmpty(str)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setText(str);
            tv.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.lookTv, R.id.closedIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lookTv:
                WebActivityCommon.showThisActivity((TopBaseActivity) ctx, MyApp.getInstance().getDefaultSp().getURLMyLevelIntroduce(),"");
                dismiss();
                break;
            case R.id.closedIv:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        if (((Activity)ctx).isFinishing()) return;
            super.show();
    }
}
