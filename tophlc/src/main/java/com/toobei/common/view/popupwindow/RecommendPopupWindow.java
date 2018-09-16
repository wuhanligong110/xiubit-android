package com.toobei.common.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopBaseActivity;

/**
 * 公司: tophlc
 * 类说明:  我的界面->邀请理财师和推荐客户
 *
 * @author qingyechen
 * @time 2017/2/8 0008 下午 2:37
 */
public class RecommendPopupWindow extends PopupWindow implements OnClickListener {

    private String strCancel;
    private String str02;
    private String str01;
    private View contentView;
    protected TopBaseActivity ctx;

    private TextView text01;
    private TextView text02;
    private TextView textCancel;
    private OnClickListener listener;

    /**
     * @param ctx       context
     * @param str01     第一个按钮显示的文字
     * @param str02     第二个按钮显示的文字
     * @param strCancel 取消按钮显示的文字
     */
    public RecommendPopupWindow(Context ctx, String str01, String str02, String strCancel) {
        super(ctx);
        this.str01 = str01;
        this.str02 = str02;
        this.strCancel = strCancel;
        this.ctx = (TopBaseActivity) ctx;
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_recommend_popu_window, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_60));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_popup_share);
        text01 = (TextView) contentView.findViewById(R.id.text_01);
        text01.setOnClickListener(this);
        text02 = (TextView) contentView.findViewById(R.id.text_02);
        text02.setOnClickListener(this);
        textCancel = (TextView) contentView.findViewById(R.id.text_cancel);
        textCancel.setOnClickListener(this);
        text01.setText(str01);
        text02.setText(str02);
        textCancel.setText(strCancel);


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        backgroundAlpha(0.6f);
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
        ctx.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        ctx.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        backgroundAlpha(1f);
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_01) {
            listener.onclickText(1);//点击第一个
        } else if (v.getId() == R.id.text_02) {
            listener.onclickText(2); //点击第二个按钮
        } else {
            dismiss();
        }


        dismiss();
    }

    public void setOnclickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onclickText(int position);


    }


}
