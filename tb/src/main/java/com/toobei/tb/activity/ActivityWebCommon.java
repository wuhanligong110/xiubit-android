package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.entity.ActivityDetail;
import com.toobei.tb.view.popupwindow.SharePopupWindow;

/**
 * 公司: tophlc
 * 类说明：活动详情web页面
 * @date 2016-4-15
 */
public class ActivityWebCommon extends WebActivityCommon implements OnClickListener {
	private ActivityDetail activityDetail;

	public static void showThisActivity(TopBaseActivity ctx, ActivityDetail activityDetail) {
		Intent intent = new Intent(ctx, ActivityWebCommon.class);
		intent.putExtra("url", activityDetail.getLinkUrl());
		intent.putExtra("title", activityDetail.getActivityName());
		intent.putExtra("activityDetail", activityDetail);
		ctx.showActivity(ctx, intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		headerLayout.showRightImageButton(R.drawable.img_share, this);
		activityDetail = (ActivityDetail) getIntent().getSerializableExtra("activityDetail");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.rightContainer) {
			String desc = "";
			String imgUrl = null;
			if (activityDetail != null) {
				title = activityDetail.getActivityName();
				url = activityDetail.getLinkUrl();
				desc = activityDetail.getActivityName();
				imgUrl = activityDetail.getActivityImg();
			}
			shareContent = new ShareContent(title, desc, url, imgUrl);
			if (shareContent != null) {
				if (popuWindow == null) {
					//因为后台返回的是一个md5 拼接上图片服务器地址
					String shareImgurl = shareContent.getShareImgurl();
					if(!shareImgurl.startsWith("http")){
						shareImgurl = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + shareImgurl;
						shareContent.setShareImgurl(shareImgurl);
					}
					popuWindow = new SharePopupWindow(ctx, null, shareContent);
				}
				popuWindow.showPopupWindow(headerLayout);
			}
		}
	}
}
