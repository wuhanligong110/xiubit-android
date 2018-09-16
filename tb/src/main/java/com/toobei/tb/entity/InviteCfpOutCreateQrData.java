package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.ShareContent;

public class InviteCfpOutCreateQrData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -7560857764507620663L;

	private String mobile;

	private String url;

	private ShareContent shareContent;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

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