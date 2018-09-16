package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * Created by hasee-pc on 2017/2/22.
 */
public class HomepageNewActivityData extends BaseEntity {
    private static final long serialVersionUID = 3709674869804106053L;

    /**
     * appType : 1
     * id : 70
     * imgUrl : https://image.toobei.com/910c35ee5e2002d2e3bdf8b213bc27e2
     * linkUrl : SDF;JH'SF
     * pageIndex : product_opening
     * pageIndexDesc :
     * shareDesc : JDGKGH
     * shareIcon : 890128f55acf51c2c6cb458d8877703a
     * shareLink : http://toobei.tophlc.com/pages/activities/scratch.html
     * shareTitle : HDFJN
     * showIndex : 3
     * status : 0
     * validBeginDate : 2017-01-09 09:46:22
     * validEndDate : 2018-02-15 09:46:25
     */

    private String appType;
    private int id;
    private String imgUrl;
    private String linkUrl;
    private String pageIndex;
    private String pageIndexDesc;
    private String shareDesc;
    private String shareIcon;
    private String shareLink;
    private String shareTitle;
    private int showIndex;
    private int status;
    private String validBeginDate;
    private String validEndDate;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
