package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.ShareContent;

public class MyQRCodeDatasData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -2849906265970315380L;
	private String url; // 二维码图片地址
	private ShareContent shareContent;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ShareContent getShareContent() {
		return shareContent;
	}

	public void setShareContent(ShareContent shareContent) {
		this.shareContent = shareContent;
	}

}