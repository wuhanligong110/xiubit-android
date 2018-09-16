package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.content.Intent;

import com.toobei.common.TopApp;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.service.ShareService;
import com.toobei.common.utils.C;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.popupwindow.TopSharePopupWindow;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.InviteRecommendContacts;
import com.umeng.analytics.MobclickAgent;

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
		//推荐理财师
		if(SHAREPURPOSE_RECOMMENDCFPQR.equals(sharePurpose)){
			switch (shareWay){
				case C.SHARE_QQ:
					MobclickAgent.onEvent(ctx,"S_2_2_1_3");
					break;
				case C.SHARE_WECHAT:
					MobclickAgent.onEvent(ctx,"S_2_2_1_2");
					break;
				case C.SHARE_WECHATCIRCLE:
					MobclickAgent.onEvent(ctx,"S_2_2_1_1");
					break;
				case C.SHARE_CONTACTS:
					MobclickAgent.onEvent(ctx,"S_2_2_1_4");
					break;
			}
		}
		//邀请客户
		if (SHAREPURPOSE_INVITECUSTOMERQR.equals(sharePurpose)){
			switch (shareWay){
				case C.SHARE_QQ:
					break;
				case C.SHARE_WECHAT:
					break;
				case C.SHARE_WECHATCIRCLE:
					break;
				case C.SHARE_CONTACTS:
					break;
			}
		}
	}

	protected void updateShareDate(final String phone) {
		new MyNetAsyncTask(ctx, false) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService()
						.inviteCfpListOutResend(TopApp.getInstance().getLoginService().token, phone);
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {

					}
				}
			}
		}.execute();
	}

	@Override
	public void onShareCancelOrFail(ShareService.SharePlatform sharePlatform) {

	}
}
