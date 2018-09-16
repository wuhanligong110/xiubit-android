package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 类说明: 课程列表
 * 请求类型 get
 * 请求Url classroom/queryClassroomList
 *
 * @author Administrator
 * @time 2016/11/4 0004 下午 5:39
 */
public class ClassRoomDetail extends BaseEntity {


    private static final long serialVersionUID = -909703582609622390L;

    /**
     * createTime : 2016-11-03 17:10:36
     * creator :
     * id : 1
     * img :
     * label :
     * linkUrl :
     * title :
     */

    private String createTime;  //  createTime	创建时间	string
    private String creator;     // creator	创建者	string
    private String id;          // id	id	number
    private String img;         // img	图片	string
    private String label;       // label	标签	string
    private String linkUrl;     // linkUrl	跳转地址	string

    private String shareDesc;//分享描述	string
    private String shareIcon;//	分享图标地址	string
    private String shareLink;//	分享图片链接	string
    private String shareTitle;//	分享标题	string

    private String summary;     //	简介	string
    private String title;       // title	标题	string

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
}