package com.v5ent.xiubit.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
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

public class DoubleElevenDialog extends Dialog {

    private final Activity ctx;
    private BtnClickListener mBtnClickListener;
    @BindView(R.id.closedIv)
    ImageView mClosedIv;
    @BindView(R.id.btn)
    ImageView mBtn;
    @BindView(R.id.contentTv)
    TextView mContentTv;


    public DoubleElevenDialog(@NonNull Activity context,BtnClickListener listener ) {
        super(context, com.toobei.common.R.style.customDialog);
        this.ctx = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(ctx).inflate(R.layout.dialog_double_eleven, null);
        setContentView(view);
        ButterKnife.bind(this);
        setCancelable(false);
        initView();
    }

    private void initView() {
        mContentTv.setText("您的真爱伴侣已在等你！\n" +
                "最近老板老生气？最近女神不理你？\n" +
                "关注公众号“貅比特”\n" +
                "回复“真爱”");
    }

    public DoubleElevenDialog setOnBtnClickListener(BtnClickListener listener) {
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
                //跳转微信
                Intent intent = new Intent();
                ComponentName cmp= new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);
                ctx.startActivity(intent);

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
