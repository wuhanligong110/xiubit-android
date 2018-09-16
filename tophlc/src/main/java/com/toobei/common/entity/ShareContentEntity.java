package com.toobei.common.entity;

public class ShareContentEntity  extends BaseEntity {


	private static final long serialVersionUID = 5402348909407085142L;
	/**
	 * code : 0
	 * msg : success
	 * data : {"shareContent":{"shareDesc":"分享描述","shareImgurl":"imagurl","shareLink":"www.baidu.com?recommendCode=18782985332&productId=b73bb12e-c4e2-4065-8328-2a49b8c80a56","shareTitle":"分享标题"}}
	 */

	private String code;
	private String msg;
	/**
	 * shareContent : {"shareDesc":"分享描述","shareImgurl":"imagurl","shareLink":"www.baidu.com?recommendCode=18782985332&productId=b73bb12e-c4e2-4065-8328-2a49b8c80a56","shareTitle":"分享标题"}
	 */

	private DataBean data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public  class DataBean extends BaseEntity {

		private static final long serialVersionUID = 29434226307385569L;
		/**
		 * shareDesc : 分享描述
		 * shareImgurl : imagurl
		 * shareLink : www.baidu.com?recommendCode=18782985332&productId=b73bb12e-c4e2-4065-8328-2a49b8c80a56
		 * shareTitle : 分享标题
		 */

		private ShareContent shareContent;


		public ShareContent getShareContent() {
			return shareContent;
		}

		public void setShareContent(ShareContent shareContent) {
			this.shareContent = shareContent;
		}
	}
}