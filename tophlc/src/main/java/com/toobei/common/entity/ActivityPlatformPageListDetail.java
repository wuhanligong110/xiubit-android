package com.toobei.common.entity;

/**
 * 接口名称 平台活动列表（分页）
 * 请求类型 get
 * 请求Url /activity/platform/pageList
 *
 * @author Administrator
 * @time 2016/11/4 0004 下午 1:50
 */
public class ActivityPlatformPageListDetail extends BaseEntity {


    private static final long serialVersionUID = 5891927160460443806L;


    /**
     * activityCode :
     * activityDesc : 点评一下
     * activityImg : be8ce9f75d8549f93d8fa2626b972077
     * activityName : 修改后的活动新测试
     * activityPlatform : 貅比特
     * activityStatus : 0
     * activityType :
     * appType : 1
     * endDate : 2016-11-18 14:21:36
     * id : 7
     * isCover : 0
     * linkUrl : www.baidu.com?token=eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NzgyNDg4Mzk4NTcsInN1YiI6ImFmNTU3MTIzNDVmZTRhOGFhOTdjMzQ5Yjc1M2Q2ZDVjIiwiaXNzIjoiaHR0cHM6XC9cL3d3dy5saW5rd2VlLmNvbSJ9.BLI3uW6NWRxzpgbb4CZR59ArLo8go_icg1jEEpbo16Q
     * platformImg : 890128f55acf51c2c6cb458d8877703a
     * shareDesc : wwwwwwwwwwwww
     * shareIcon : 890128f55acf51c2c6cb458d8877703a
     * shareLink :
     * shareTitle : wwwwww
     * startDate : 2016-09-12 14:21:33
     */

    private String activityCode;        //  activityCode		string
    private String activityDesc;        //	        activityDesc	活动描述	string
    private String activityImg;         //	        activityImg	活动图片	string
    private String activityName;        //	        activityName		string
    private String activityPlatform;    //	        activityPlatform		string
    private String activityStatus;      //	        activityStatus	活动状态（0：进行中；1：已结束）	number
    private String activityType;        //	        activityType		string
    private String appType;             //	        appType		number
    private String endDate;             //	        endDate		string
    private String id;                  //	        id		number
    private String isCover;             //	        isCover		number
    private String linkUrl;             //	        linkUrl	链接	string
    private String platformImg;         //	        platformImg		string
    private String shareDesc;           //	        shareDesc		string
    private String shareIcon;           //	        shareIcon		string
    private String shareLink;           //	        shareLink		string
    private String shareTitle;          //	        shareTitle		string
    private String startDate;           //	        startDate		string

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityPlatform() {
        return activityPlatform;
    }

    public void setActivityPlatform(String activityPlatform) {
        this.activityPlatform = activityPlatform;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsCover() {
        return isCover;
    }

    public void setIsCover(String isCover) {
        this.isCover = isCover;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPlatformImg() {
        return platformImg;
    }

    public void setPlatformImg(String platformImg) {
        this.platformImg = platformImg;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}