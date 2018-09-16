package com.toobei.common.utils;

public class C {

	/**
	 * 系统剪切图片请求
	 */
	public static final int REQUEST_CROP_SYSTEM = 0X111;

	/**
	 * 相机请求
	 */
	public static final int REQUEST_CAMARA = 0X112;

	public static final int REQUEST_PIC_CHANGED = 0X113;

	public static final int REQUEST_PIC_FEEDBACK = 0X11A;

	public static final String TAG = "tag";

	public static final String RESULT = "result";
	/**
	 * 用户头像更改标识
	 */
	public static final String TAG_USER_FACE_CHANGED = "userFaceChanged";

	public static final String TAG_FEEDBACK = "Feedback";

	public static final String TAG_CHAT_PIC_SELECT = "chatPicSelect";
	public static final String UPLOAD_DECLARATION_PIC = "upload_declaration_pic";
	public static final String FOR_CARDSCAN = "FOR_CARDSCAN";  //用于银行卡和身份证扫描

	/**
	 *  // TODO: 2017/2/18   android6.0 获取权限
	 *
	 */
	public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
	public static final int MY_PERMISSIONS_REQUEST_DIAL = 10086;

	/**
	 * 分享方式
	 */
	public static final int SHARE_QQ = 1;  //QQ
	public static final int SHARE_WECHAT = 2;  //微信好友
	public static final int SHARE_WECHATCIRCLE = 3;  //朋友圈
	public static final int SHARE_CONTACTS = 4; //联系人
	public static final int SHARE_COPYINK = 5; //复制链接
}
