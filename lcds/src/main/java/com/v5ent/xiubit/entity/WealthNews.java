package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：财富快报  实体
 *
 * @date 2015-10-22
 */
public class WealthNews extends BaseEntity {

    /**
     * serialVersionUID
     */
//
//	变量名	含义	类型	备注
//	crtTime	开始有效时间	object	2017-07-04 10:39:12
//	img	图片地址	string	http://image.tophlc.com/53890cfdd25e905428e0106b3e562b58
//	linkUrl	详情地址	string	www.baidu.com
//	name	名称	string	投资
//	newsId	资讯Id	number	12
//	summary	简介	string	这个风口，吹起了多少猪？
//	title	标题	string	这个风口，吹起了多少猪？

    private static final long serialVersionUID = -6715796841244472657L;
    private String newsId;//id
    private String name;//资讯名称
    private String crtTime;//创建时间
    private String img;//配图
    private String linkUrl;//详细地址
    private String summary;//总结
    private String title;//标题
    private String typeName;//资讯标签
    private String shareIcon;//


    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }
}