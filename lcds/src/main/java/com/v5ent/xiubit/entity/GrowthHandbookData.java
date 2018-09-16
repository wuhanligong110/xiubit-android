package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class GrowthHandbookData extends BaseEntity {
    private static final long serialVersionUID = -6350513751408068812L;

    /**
     * source : 测试内容0h11
     * appType : 1
     * description : 从0开始，教你如何理财
     * extends1 :
     * extends2 :
     * extends3 :
     * icon : fb405a2cb4190f8ea725e88a7cadf90f
     * id : 1
     * img : fb405a2cb4190f8ea725e88a7cadf90f
     * name : 新手课堂
     * showIndex : 1
     */

    private String source;
    private String appType;
    private String description;
    private String extends1;
    private String extends2;
    private String extends3;
    private String icon;
    private String id;
    private String img;
    private String name;
    private String showIndex;

    public String getShowIndex() {
        return showIndex;
    }

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

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtends1() {
        return extends1;
    }

    public void setExtends1(String extends1) {
        this.extends1 = extends1;
    }

    public String getExtends2() {
        return extends2;
    }

    public void setExtends2(String extends2) {
        this.extends2 = extends2;
    }

    public String getExtends3() {
        return extends3;
    }

    public void setExtends3(String extends3) {
        this.extends3 = extends3;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
