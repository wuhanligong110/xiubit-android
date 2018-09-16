package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class ProClassifyDetail extends BaseEntity {


    private static final long serialVersionUID = -1279508673874330760L;
    private String cateDeclare;                //   分类说明	string(投资端??)
    private String cateId;                     //   	分类ID	number
    private String cateLogoChannel;            //  	分类logo 猎才大师	string
    private String cateLogoInvestor;            //  	分类logo 投资者端	string
    private String cateName;                   //  	分类名称	string
    private String urlLink;                     //  	分类链接内容	string
    private String count;                       //  	产品数量	number
    private String flowMaxRateStatistics;       //  	最大浮动利率统计	number
    private String flowMinRateStatistics;       // 	最小浮动利率统计	number

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateLogoChannel() {
        return cateLogoChannel;
    }

    public void setCateLogoChannel(String cateLogoChannel) {
        this.cateLogoChannel = cateLogoChannel;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFlowMaxRateStatistics() {
        return flowMaxRateStatistics;
    }

    public void setFlowMaxRateStatistics(String flowMaxRateStatistics) {
        this.flowMaxRateStatistics = flowMaxRateStatistics;
    }

    public String getFlowMinRateStatistics() {
        return flowMinRateStatistics;
    }

    public void setFlowMinRateStatistics(String flowMinRateStatistics) {
        this.flowMinRateStatistics = flowMinRateStatistics;
    }

    public String getCateDeclare() {
        return cateDeclare;
    }

    public void setCateDeclare(String cateDeclare) {
        this.cateDeclare = cateDeclare;
    }

    public String getCateLogoInvestor() {
        return cateLogoInvestor;
    }

    public void setCateLogoInvestor(String cateLogoInvestor) {
        this.cateLogoInvestor = cateLogoInvestor;
    }
}