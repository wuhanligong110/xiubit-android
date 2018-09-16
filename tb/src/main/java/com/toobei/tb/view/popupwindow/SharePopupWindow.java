package com.toobei.tb.view.popupwindow;

import android.content.Context;
import android.content.Intent;

import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.ShareService;
import com.toobei.common.view.popupwindow.TopSharePopupWindow;
import com.toobei.tb.R;
import com.toobei.tb.activity.InviteRecommendContacts;

public class SharePopupWindow extends TopSharePopupWindow {

	public SharePopupWindow(Context ctx, String phone, ShareContent shareContent) {
		super(ctx, phone, shareContent);
	}

	@Override
	protected int getIcLauncherDrawable() {
		return R.drawable.ic_launcher;
	}

	@Override
	protected Intent getIntentInviteContacts() {
		return new Intent(ctx, InviteRecommendContacts.class);
	}
	/**
	 * 配置友盟埋点
	 */
	@Override
	protected void setUmengEvent(String sharePurpose, int shareWay) {

	}

	@Override
	public void onShareCancelOrFail(ShareService.SharePlatform sharePlatform) {

	}
}
