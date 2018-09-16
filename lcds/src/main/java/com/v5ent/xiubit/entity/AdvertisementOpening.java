package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class AdvertisementOpening extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 9212733212904537272L;
	private String imgUrl; //            "imgUrl": "http://10.16.0.202:4869/fafa5efeaf3cbe3b23b2748d13e629a1", 
	private String linkUrl; //            "linkUrl": "http://www.bytter.com", 
	private String pageIndex; //            "pageIndex": "app首页", 
	private String pageIndexDesc; //            "pageIndexDesc": "开屏广告", 
	private String validEndDate; //            "validEndDate": "2016-05-31 00:00:00"

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

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageIndexDesc() {
		return pageIndexDesc;
	}

	public void setPageIndexDesc(String pageIndexDesc) {
		this.pageIndexDesc = pageIndexDesc;
	}

	public String getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(String validEndDate) {
		this.validEndDate = validEndDate;
	}

	@Override
	public String toString() {
		return "AdvertisementOpeningData [imgUrl=" + imgUrl + ", linkUrl=" + linkUrl
				+ ", pageIndex=" + pageIndex + ", pageIndexDesc=" + pageIndexDesc
				+ ", validEndDate=" + validEndDate + "]";
	}
}
