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

import com.toobei.common.TopBaseActivity;
import com.toobei.common.service.ShareService;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/7
 */

public class OneImageSharePopupWindow extends PopupWindow implements View.OnClickListener{

    private String imagePath;
    private View contentView;
    protected TopBaseActivity ctx;
    private ImageView btnWechatFriend, btnWechatCricle,btnQQshare, btnCopyLink;
    private ShareService shareService;


    public OneImageSharePopupWindow(Context ctx,String imagePath) {
        super(ctx);
        this.ctx = (TopBaseActivity) ctx;
        this.imagePath = imagePath;
        shareService = new ShareService();
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_one_image_share_popu_window, null);
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));
        this.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.anim_popup_share);
        btnWechatFriend = (ImageView) contentView.findViewById(R.id.btn_wechat_friend);
        btnWechatFriend.setOnClickListener(this);
        btnWechatCricle = (ImageView) contentView.findViewById(R.id.btn_wechat_cricle);
        btnWechatCricle.setOnClickListener(this);
        btnQQshare = (ImageView) contentView.findViewById(R.id.btn_share_qq);
        btnQQshare.setOnClickListener(this);
        btnCopyLink = (ImageView) contentView.findViewById(R.id.btn_copy_link);
        btnCopyLink.setOnClickListener(this);

    }

    /**
     * 显示popupWindow
     * @param parent
     */
    public void showPopupWindow(View parent) {
        backgroundAlpha(0.5f);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
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
       if (v.getId() == R.id.btn_wechat_friend) {
            btnWechatFriend.setEnabled(false);
            shareService.shareImageToWeixinFriend(imagePath);
            btnWechatFriend.setEnabled(true);
        }
        else if (v.getId() == R.id.btn_share_qq) {
            btnQQshare.setEnabled(false);
            shareService.shareImageToQQ(imagePath);
            btnQQshare.setEnabled(true);
        }

        else if (v.getId() == R.id.btn_wechat_cricle) {
            btnWechatCricle.setEnabled(false);
            shareService.shareImageToWeixinCircle(imagePath);
			btnWechatCricle.setEnabled(true);
        }
        else if (v.getId() == R.id.btn_copy_link) {
//            if (isShareQr) {
//                setUmengEvent(sharePurpose,C.SHARE_CONTACTS);
//                //	Intent intent = new Intent(ctx, InviteRecommendContacts.class);
//                Intent intent = getIntentInviteContacts();  //通讯录
//                if (isInviteCfp) {
//                    intent.putExtra("isRecommendCfp", true);
//                } else {
//                    intent.putExtra("isRecommendCfp", false);
//                }
//                ctx.showActivity(ctx, intent);
//            } else {
//                SystemTool.copy(shareContent.getLink(), ctx);
//                com.toobei.common.utils.ToastUtil.showCustomToast("复制成功");
//                if (phone != null) {
//                    //updateShareDate();
//                }
//            }
        }

        dismiss();
    }



}
