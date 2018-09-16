package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MyRankWeeklyCommsionData extends BaseEntity {

    private static final long serialVersionUID = -340103891017578790L;


    /**
     * headImg : d11f514a2a514c670efc7ae16fbb6401
     * mobile : 152****3390
     * rank : 7
     * totalProfit : 1646.74
     */

    private String headImg;
    private String mobile;
    private String rank;
    private String totalProfit;
    private String levelName;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(String totalProfit) {
        this.totalProfit = totalProfit;
    }
}