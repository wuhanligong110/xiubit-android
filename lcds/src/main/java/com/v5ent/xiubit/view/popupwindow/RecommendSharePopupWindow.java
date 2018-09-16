package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.ShareService;
import com.v5ent.xiubit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/7
 */

public class RecommendSharePopupWindow extends PopupWindow{

    private int recommendType;
    public final static  int RECOMMENDTYPE_PRODUCT = 0;
    public final static  int RECOMMENDTYPE_ORG = 1;
    private RecommendShareListener listener;
    @BindView(R.id.btn_wechat_cricle)
    ImageView btnWechatCricle;
    @BindView(R.id.btn_wechat_friend)
    ImageView btnWechatFriend;
    @BindView(R.id.recommendCfgBtn)
    ImageView recommendCfgBtn;
    @BindView(R.id.recommendCustomerBtn)
    ImageView recommendCustomerBtn;
    @BindView(R.id.text_share_name)
    TextView text_share_name;
    private ShareContent shareContent;
    private View contentView;
    protected TopBaseActivity ctx;
    private ShareService shareService;


    public RecommendSharePopupWindow(Context ctx,ShareContent shareContent,int recommendType,RecommendShareListener listener) {
        super(ctx);
        this.ctx = (TopBaseActivity) ctx;
        this.shareContent = shareContent;
        this.listener = listener;
        this.recommendType = recommendType;
        shareService = new ShareService();
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_recommend_share_popu_window, null);
        ButterKnife.bind(this,contentView);
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx, R.color.Color_0));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_popup_share);
        //	contentView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        if (recommendType == RECOMMENDTYPE_PRODUCT) text_share_name.setText("选择推荐产品方式");
        else text_share_name.setText("选择推荐平台方式");
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




    @OnClick({R.id.btn_wechat_cricle, R.id.btn_wechat_friend, R.id.recommendCfgBtn, R.id.recommendCustomerBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wechat_cricle:
                btnWechatCricle.setEnabled(false);
                shareService.share(ctx, shareContent, ShareService.SharePlatform.WechatMoments,R.drawable.ic_launcher);
                btnWechatCricle.setEnabled(true);
                break;
            case R.id.btn_wechat_friend:
                btnWechatFriend.setEnabled(false);
                shareService.share(ctx, shareContent, ShareService.SharePlatform.Wechat, R.drawable.ic_launcher);
                btnWechatFriend.setEnabled(true);
                break;
            case R.id.recommendCfgBtn:
                listener.onRecommendCfg();
                break;
            case R.id.recommendCustomerBtn:
                listener.onRecommendCustomer();
                break;
        }
        dismiss();
    }

    public interface RecommendShareListener{
        void onRecommendCfg();
        void onRecommendCustomer();
    }

}
