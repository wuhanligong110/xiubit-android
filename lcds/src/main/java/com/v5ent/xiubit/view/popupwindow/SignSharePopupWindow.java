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
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.ShareService;
import com.v5ent.xiubit.R;

public class SignSharePopupWindow  extends PopupWindow implements View.OnClickListener {

	private ShareContent shareContent;
	private View contentView;
	protected TopBaseActivity ctx;
	private ImageView btnWechatFriend, btnWechatCricle,btnQQshare, btnCopyLink;
	private ShareService shareService;
	public ShareListener shareListener;


	public SignSharePopupWindow(Context ctx,ShareContent shareContent,ShareService.OnShareSuccessListener shareListener) {
		super(ctx);
		this.ctx = (TopBaseActivity) ctx;
		this.shareContent = shareContent;
		shareService = new ShareService();
		shareService.setOnShareSuccessListener(shareListener);
		initView();
	}

	private void initView() {
		contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_sign_share_popu_window, null);
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
		ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.anim_popup_share);
		//	contentView.findViewById(R.id.btn_cancel).setOnClickListener(this);
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
		if (shareListener != null) shareListener.onShareHasClick();
		if (v.getId() == com.toobei.common.R.id.btn_wechat_friend) {
			btnWechatFriend.setEnabled(false);
			shareService.share(ctx, shareContent, ShareService.SharePlatform.Wechat, R.drawable.ic_launcher);
			btnWechatFriend.setEnabled(true);
		}
		else if (v.getId() == com.toobei.common.R.id.btn_share_qq) {
			btnQQshare.setEnabled(false);
			shareService.share(ctx, shareContent, ShareService.SharePlatform.QQ, R.drawable.ic_launcher);
			btnQQshare.setEnabled(true);
		}

		else if (v.getId() == com.toobei.common.R.id.btn_wechat_cricle) {
			btnWechatCricle.setEnabled(false);
			shareService.share(ctx, shareContent, ShareService.SharePlatform.WechatMoments,
					R.drawable.ic_launcher);
			/*			shareService.share(ctx, shareContent, SharePlatform.WechatMoments,
								R.drawable.ic_launcher);
			*/btnWechatCricle.setEnabled(true);
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


	public SignSharePopupWindow setOnShareListener(ShareListener shareListener){
		this.shareListener = shareListener;
		return this;
	}

	public interface  ShareListener{
		void onShareHasClick();
	}

}

