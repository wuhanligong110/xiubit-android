package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/22
 */

public class HomeHotNewsDetial extends BaseEntity {
    private static final long serialVersionUID = 1938181816174249044L;
    /**
     * crtTime : 2016-07-20 10:58
     * img : http://image.tophlc.com/ffddaa3bf3b376f9156a69a6f0a3e127
     * linkUrl :
     * name : 财经
     * newsId : 65
     * shareIcon :
     * summary : 中困中困中困中困
     * title : 中困中困中困中困
     * typeName : 财经
     */

    private String crtTime;
    private String img;
    private String linkUrl;
    private String name;
    private String newsId;
    private String shareIcon;
    private String summary;
    private String title;
    private String typeName;

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
