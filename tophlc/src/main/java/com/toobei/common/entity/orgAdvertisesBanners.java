package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明：首页 轮播图实体
 * @date 2015-10-22
 */
public class orgAdvertisesBanners extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -6012534780177206381L;
	private String imgUrl;// 图片URL
	private String linkUrl; // 跳转url
	private  ShareContent shareContent;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public ShareContent getShareContent() {
		return shareContent;
	}

	public void setShareContent(ShareContent shareContent) {
		this.shareContent = shareContent;
	}
}