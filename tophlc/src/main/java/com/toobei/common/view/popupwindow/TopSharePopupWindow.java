package com.toobei.common.view.popupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.ShareService;
import com.toobei.common.service.ShareService.OnShareSuccessListener;
import com.toobei.common.service.ShareService.SharePlatform;
import com.toobei.common.utils.C;

import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

public abstract class TopSharePopupWindow extends PopupWindow implements OnClickListener,
		OnShareSuccessListener {

	private View contentView;
	protected TopBaseActivity ctx;
	private ShareContent shareContent;
	private ImageView btnWechatFriend, btnWechatCricle,btnQQshare, btnCopyLink;
	private ShareService shareService;
	private String phone;
	private TextView textCopyLink;

	private boolean isShareQr = false;
	private boolean isInviteCfp = false;
	public TextView	textShareName;
	//分享目的
	private String sharePurpose;
	public static final String SHAREPURPOSE_RECOMMENDCFPQR = "sharePurpose_recommendCfpQr";
	public static final String SHAREPURPOSE_INVITECUSTOMERQR = "sharePurpose_inviteCustomerQr";


	public TopSharePopupWindow(Context ctx, String phone, ShareContent shareContent) {
		super(ctx);
		this.ctx = (TopBaseActivity) ctx;
		this.shareContent = shareContent;
		this.phone = phone;
		shareService = new ShareService();
		shareService.setOnShareSuccessListener(this);
		initView();
	}

	private void initView() {
		contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_share_popu_window, null);
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
		textCopyLink = (TextView) contentView.findViewById(R.id.text_copy_link);
		textShareName = (TextView) contentView.findViewById(R.id.text_share_name);
		if (isShareQr && btnCopyLink != null) {
			btnCopyLink.setImageResource(R.drawable.img_share_contacts);
			textShareName.setText("选择邀请方式");
			textCopyLink.setText("通讯录");
		}

	}

	/** 
	* 显示popupWindow 
	* @param parent 
	*/
	public void showPopupWindow(View parent) {
		backgroundAlpha(0.5f);
		Logger.d("shareContent==="+shareContent.toString());
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

	protected void updateShareDate(String phone) {

	}

	protected abstract int getIcLauncherDrawable();

	protected abstract Intent getIntentInviteContacts();

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_wechat_friend) {
			setUmengEvent(sharePurpose, C.SHARE_WECHAT);
			btnWechatFriend.setEnabled(false);
 			shareService.share(ctx, shareContent, SharePlatform.Wechat, getIcLauncherDrawable());
			btnWechatFriend.setEnabled(true);
		}
		else if (v.getId() == R.id.btn_share_qq) {
			setUmengEvent(sharePurpose,C.SHARE_QQ);
			btnQQshare.setEnabled(false);
 			shareService.share(ctx, shareContent, SharePlatform.QQ, getIcLauncherDrawable());
			btnQQshare.setEnabled(true);
		}

		else if (v.getId() == R.id.btn_wechat_cricle) {
			setUmengEvent(sharePurpose,C.SHARE_WECHATCIRCLE);
			btnWechatCricle.setEnabled(false);
			shareService.share(ctx, shareContent, SharePlatform.WechatMoments,
					getIcLauncherDrawable());
			/*			shareService.share(ctx, shareContent, SharePlatform.WechatMoments,
								R.drawable.ic_launcher);
			*/btnWechatCricle.setEnabled(true);
		} else if (v.getId() == R.id.btn_copy_link) {
			if (isShareQr) {
				setUmengEvent(sharePurpose,C.SHARE_CONTACTS);
				//	Intent intent = new Intent(ctx, InviteRecommendContacts.class);
				Intent intent = getIntentInviteContacts();  //通讯录
				if (isInviteCfp) {
					intent.putExtra("isRecommendCfp", true);
				} else {
					intent.putExtra("isRecommendCfp", false);
				}
				ctx.showActivity(ctx, intent);
			} else {
				SystemTool.copy(shareContent.getLink(), ctx);
				com.toobei.common.utils.ToastUtil.showCustomToast("复制成功");
				if (phone != null) {
					//updateShareDate();
				}
			}
		}

		dismiss();
	}

	@Override
	public void onShareSuccessed(SharePlatform sharePlatform) {
		if (phone != null) {
			updateShareDate(phone);
		}
	}

	public void setShareQr(boolean isShareQr) {
		this.isShareQr = isShareQr;
		if (isShareQr && btnCopyLink != null) {
			btnCopyLink.setImageResource(R.drawable.img_share_contacts);
			textCopyLink.setText("通讯录");
		}
	}

	public void setInviteCfp(boolean isInviteCfp) {
		this.isInviteCfp = isInviteCfp;
	}

	public void setSharePurpose(String sharePurpose){
		this.sharePurpose = sharePurpose;
	}
	/**
	 * 配置友盟埋点
	 */
	protected abstract void setUmengEvent(String sharePurpose, int shareWay);

}
