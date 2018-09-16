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

import com.toobei.common.R;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.view.MyWebView;
import com.toobei.common.view.OnMyWebViewListener;

import org.xsl781.utils.PixelUtil;

public class WebPromptPopupWindow extends PopupWindow implements OnClickListener, OnMyWebViewListener {

    private View contentView;
    protected TopBaseActivity ctx;
    private String url;
    protected MyWebView mWebView;
    private OnClickListener listener;

    /**
     * @param ctx context
     * @param url
     */
    public WebPromptPopupWindow(Context ctx, String url) {
        super(ctx);
        this.ctx = (TopBaseActivity) ctx;
        this.url = url;
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_web_prompt_popu_window, null);
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
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_popup_web);

        initWebView();
    }

    private void initWebView() {
        mWebView = (MyWebView) contentView.findViewById(R.id.myWebView);
        contentView.findViewById(R.id.text_cancel).setOnClickListener(this);
        mWebView.setOnMyWebViewListener(this);
        mWebView.setAnimationCacheEnabled(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setRedirectUsable(false);
        mWebView.loadUrl(url);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        backgroundAlpha(0.5f);
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
    public void dismiss() {
        backgroundAlpha(1f);
        super.dismiss();
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }


    public void setWebHeight(int heightDp) {
        mWebView.getLayoutParams().height = PixelUtil.dip2px(ctx, (float) heightDp);
    }

    @Override
    public void onReceivedTitle(String title) {

    }

    @Override
    public void onUrlRedirectCallBack(boolean isRedirectUsable, String url) {
        
    }

    @Override
    public void onUrlLoading(boolean isRedirectUsable, String url) {
    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageStart(String url) {

    }

    @Override
    public void onLoadError(String url) {

    }
}
