package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.ShareContent;

/**
 * 公司: tophlc
 * 类说明：投资活动 实体
 * @date 2015-10-20
 */
public class ActivityDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -5250494118283024986L;
	private String activityId;//活动id
	private String activityName;//活动名称
	private String activityImg;//活动图片
	private String startDate;//开始日期
	private String endDate;
	private String linkUrl;
	private String status;//结束标识: 0未结束，1已结束
	private  ShareContent shareContent;

//	activityId	活动id	string
//	activityImg	活动图片	string
//	activityName	活动名称	string
//	endDate	活动结束时间	string
//	linkUrl	活动链接	string
//	shareContent	活动分享	object
//	startDate	活动开始时间	string
//	status	活动状态:0:进行中，1:已结束，-1:即将开始	string



	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityImg() {
		return activityImg;
	}

	public void setActivityImg(String activityImg) {
		this.activityImg = activityImg;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ShareContent getShareContent() {
		return shareContent;
	}

	public void setShareContent(ShareContent shareContent) {
		this.shareContent = shareContent;
	}
}