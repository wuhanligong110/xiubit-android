package com.toobei.common.entity;

/**
 * 公司: tophlc
 * 类说明：产品和机构banner
 * <p>
 * 接口名称 pc_广告查询-陈春燕-已实现
 * 请求类型 post
 * 请求Url /api/homepage/advs
 * 接口描述 show_index ASC
 *
 * @date 2015-10-22
 */
public class HomePagerBanners extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6012534780177206381L;

   // private ShareContent shareContent; //旧字段 V2.0版本前首页使用
    /**
     * appType : 2
     * id : 51
     * pageIndex : pc_idx_middle
     * pageIndexDesc :
     * shareDesc :
     * shareIcon :
     * shareLink :
     * shareTitle :
     * showIndex : 4
     * status : 0
     * validBeginDate : 2016-09-05 10:44:14
     * validEndDate : 2017-02-15 10:44:17
     */

    private String appType;
    private String id;
    private String imgUrl;// 图片URL
    private String linkUrl; // 跳转url
    private String pageIndex;
    private String pageIndexDesc;
    //分享使用字段
    private String shareDesc;
    private String shareIcon;
    private String shareLink;
    private String shareTitle;

    private String showIndex;
    private String status;
    private String validBeginDate;
    private String validEndDate;


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

//    public ShareContent getShareContent() {
//        return shareContent;
//    }
//
//    public void setShareContent(ShareContent shareContent) {
//        this.shareContent = shareContent;
//    }


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(String validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public String getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }
}