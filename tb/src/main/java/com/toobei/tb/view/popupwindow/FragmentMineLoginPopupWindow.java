package com.toobei.tb.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.toobei.common.TopBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.RegisterPhone;

public class FragmentMineLoginPopupWindow extends PopupWindow implements View.OnClickListener {

    private View contentView;
    protected TopBaseActivity ctx;


    public FragmentMineLoginPopupWindow(Context ctx) {
        super(ctx);
        this.ctx = (TopBaseActivity) ctx;
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.popup_window_fragment_mine_login, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));

        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_fade);

        contentView.findViewById(R.id.btn_register).setOnClickListener(this);
        contentView.findViewById(R.id.btn_login).setOnClickListener(this);

    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
      //  backgroundAlpha(0.5f);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ctx.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ctx.getWindow().setAttributes(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                ctx.showActivity(ctx, RegisterPhone.class);
                break;
            case R.id.btn_login:
                ctx.showActivity(ctx, LoginActivity.class);
                break;
        }


    }


}
