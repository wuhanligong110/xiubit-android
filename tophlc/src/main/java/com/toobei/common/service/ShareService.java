package com.toobei.common.service;

import android.content.Context;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.ToastUtil;

import org.xsl781.utils.Logger;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * 公司:Tophlc
 * 类说明：分享服务
 * @date 2015-5-15
 */
public class ShareService implements PlatformActionListener, Callback {

	//	private static ShareService service = null;
	private static final int MSG_ACTION_CCALLBACK = 2;

	private SharePlatform curSharePlatform;
	private OnShareSuccessListener onShareSuccessListener;

	public enum SharePlatform {
		Wechat, QQ, WechatMoments, Copy
	}

	public interface OnShareSuccessListener {
		void onShareSuccessed(SharePlatform sharePlatform);
		void onShareCancelOrFail(SharePlatform sharePlatform);
	}

	public ShareService() {
		ShareSDK.initSDK(TopApp.getInstance());
	}

	/*
		public static synchronized ShareService getInstance() {
			if (service == null) {
				service = new ShareService();
			}
			return service;
		}*/

	/**
	 * 功能：实现一键分享
	 * @param ctx
	 */
	public void showOneKeyShare(Context ctx) {
		//	ShareSDK.initSDK(ctx);
		OnekeyShare oks = new OnekeyShare();
		//关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字
		//	oks.setNotification(R.drawable.ic_launcher, ctx.getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("forward.getShareTitle()");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("forward.getLink()");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("forward.getDescript()");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//	oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		//oks.setImageUrl("http://www.wozhuzhe.com/images/img1.png");
		oks.setImageUrl("forward.getThumb()");
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("forward.getLink()");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("forward.getDescript()");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(ctx.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("forward.getSite()");

		// 启动分享GUI
		oks.show(ctx);
	}

	/**
	 * 功能：分享到qq，可用于邀请好友
	 * @param ctx
	 */
	public void share(Context ctx, ShareContent shareContent, SharePlatform platform,
			int ic_launcher) {
		if (shareContent == null) shareContent = new ShareContent();
		//ShareSDK.initSDK(ctx);
		Platform plat = null;
		ShareParams oks = new ShareParams();
		curSharePlatform = platform;
		/*	HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("BypassApproval", false);
			ShareSDK.setPlatformDevInfo("Wechat", map);
			ShareSDK.setPlatformDevInfo("WechatMoments", map);*/
		if (platform == SharePlatform.QQ) {
			//	System.out.println("=======ToQQShare");
			plat = ShareSDK.getPlatform(QQ.NAME);
			oks.setTitle(shareContent.getShareTitle());
		} else if (platform == SharePlatform.Wechat) {
			//System.out.println("=======ToWechat");
			plat = ShareSDK.getPlatform("Wechat");
			oks.setShareType(Platform.SHARE_WEBPAGE);
			oks.setTitle(shareContent.getShareTitle());
		} else if (platform == SharePlatform.WechatMoments) {
			//	System.out.println("=======ToWechatMoments");
			plat = ShareSDK.getPlatform("WechatMoments");
			oks.setShareType(Platform.SHARE_WEBPAGE);
			oks.setTitle(shareContent.getShareDesc());//显示内容
		}
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(shareContent.getLink());
		// text是分享文本，所有平台都需要这个字段
		oks.setText(shareContent.getShareDesc());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		//	oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		//oks.setImageUrl("http://www.wozhuzhe.com/images/img1.png");
        // TODO: 2017/1/13 0013 qq分享图片错误链接错误时会调用平台配置的默认图片 而微信会报错  ;可以不设置分享图片 为null时微信和qq都会调用开发者平台配置的默认图片
		String imgUrl = shareContent.getShareImgurl();
		if (TextUtils.isEmpty(imgUrl)) {
			//oks.setImageData(BitmapFactory.decodeResource(ctx.getResources(), ic_launcher));
//			oks.setImageUrl(TopApp.getInstance().getDefaultSp().getWechatShareLogo());
			oks.setImageUrl(null);
		} else {
			oks.setImageUrl(shareContent.getShareImgurl());//todo 保证是图片地址，否则无法弹出界面
		}
		//	oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(shareContent.getLink());
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(shareContent.getShareDesc());
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(shareContent.getShareTitle());
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(shareContent.getLink());
		plat.setPlatformActionListener(this);
		plat.share(oks);
		Logger.d("SharePlatform==="+curSharePlatform,shareContent.toString());
	}

	/**
	 * 分享本地图片到微信朋友
	 */
	public void shareImageToWeixinFriend(String imagePath){
		Platform plat = ShareSDK.getPlatform("Wechat");
		ShareParams oks = new ShareParams();
		oks.setShareType(Platform.SHARE_IMAGE);
		oks.setImagePath(imagePath);
		plat.setPlatformActionListener(this);
		plat.share(oks);

	}

	/**
	 * 分享本地图片到微信朋友
	 */
	public void shareImageToWeixinCircle(String imagePath){
		Platform plat = ShareSDK.getPlatform("WechatMoments");
		ShareParams oks = new ShareParams();
		oks.setShareType(Platform.SHARE_IMAGE);
		oks.setImagePath(imagePath);
		plat.setPlatformActionListener(this);
		plat.share(oks);

	}
	
	
	public void shareImageToQQ(String imagePath){
		Platform plat = ShareSDK.getPlatform(QQ.NAME);
		ShareParams oks = new ShareParams();
		oks.setShareType(Platform.SHARE_IMAGE);
		oks.setImagePath(imagePath);
		plat.setPlatformActionListener(this);
		plat.share(oks);
	}

	@Override
	public void onCancel(Platform platform, int action) {
		// 取消
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
		// 成功
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);

	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		// 失敗
		//打印错误信息,print the error msg
		t.printStackTrace();
		//错误监听,handle the error msg
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);

	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
		case 1: {
			// 成功
			if (onShareSuccessListener != null) {
				onShareSuccessListener.onShareSuccessed(curSharePlatform);
			}
			ToastUtil.showCustomToast("分享成功");
			System.out.println("分享回调成功------------");
		}
			break;
		case 2: {
			// 失败
            if (onShareSuccessListener != null) {
                onShareSuccessListener.onShareCancelOrFail(curSharePlatform);
            }
			ToastUtil.showCustomToast("分享失败");
		}
			break;
		case 3: {
			// 取消
            if (onShareSuccessListener != null) {
                onShareSuccessListener.onShareCancelOrFail(curSharePlatform);
            }
			ToastUtil.showCustomToast("分享取消");
		}
			break;
		}

		return false;

	}

	public void setOnShareSuccessListener(OnShareSuccessListener onShareSuccessListener) {
		this.onShareSuccessListener = onShareSuccessListener;
	}

}
