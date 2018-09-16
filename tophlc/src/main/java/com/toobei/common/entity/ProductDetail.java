package com.toobei.common.entity;

import java.util.List;

/**
 * 公司: tophlc 类说明：理财产品
 *
 * @date 2015-10-28
 */
public class ProductDetail extends BaseEntity {


    private static final long serialVersionUID = 3942525023454121987L;


    private String detailOpenType;//	产品详情打开方式	number	0-原页打开 1-新页面打开
    private String buyTotalMoney;  //buyTotalMoney	产品总额度	number
    private String buyedTotalMoney; //buyedTotalMoney	产品被投资总额	number
    private String buyedTotalPeople; //buyedTotalPeople	产品已投资人数	number
    public  String couldbuyMoney; //剩余可买的份额
    private String cfpRecommend;//产品是否推荐   0 未推荐 其余数值 已推荐
    private String deadLine;  //deadLine	期限	number
    private String deadLineType; //deadLineType	期限类型：1天，2月	number
    private String deadLineValue;
    private String productDetailUrl; //productDetailUrl	产品详情打开URL	productDetailUrl=http://liecai.tophlc.com/pages/financing/product_detail.html
    private String feeRatio;//佣金率
    private String productRateText; //产品利率 productRateText	产品利率Text	string	产品利率 最终显示文本
    private String flowMaxRate; // flowMaxRate	浮动最大利率	number
    private String flowMinRate; //flowMinRate	浮动最小利率	number
    private String isFlow; //isFlow	1固定利率；2浮动利率	number
    private String isQuota;//isQuota	是否限额产品	number	1-限额、2-不限额
    private String orgName;
    private String orgNumber; //orgNumber	机构编码	string
    private String productId;
    private String productName;
    private String status; //saleStatus	产品状态	number	1-在售|2-售罄|3-募集失败
    private String thirdProductId; //thirdProductId	外部产品uuid	string
    private String isRedemption;  //是否可赎回可转让	number	0=不支持赎回和转让|1=可赎回|2=可转让;
    private String isHaveProgress;   //是否拥有产品进度	number	0=有|1没有
    private List<String> tagListRight;   // 右上角首投字段
    private String deadLineMinValue;
    private String minDeadLineType;
    private String deadLineMaxValue;  //deadLineMaxValue	产品最大期限天数	number
    private String maxDeadLineType;
    private String deadLineValueText;  //deadLineValueText=30天~50天 产品最大期限天数 自定义显示	string
    private List<String> tagList;

    //v1.1.0
    private String orgFeeType;   // 机构收费类型  1:cpa-按投资人数量进行收费/首投   2:cps-按投资金额进行收费/复投
    private String assignmentTime;   // 可转让天数	number
    private String redemptionTime;   // 可赎回天数	number
    //V1.2.1 是否是虚拟机构
    private String orgIsstaticproduct;   // 是否是虚拟机构
    //V2.0.2
    private List<String> tagListRightNewer;   // 新手专享标签	array<string>
    //
    private String saleStartTime;//产品销售开始时间	string
    private String timeNow;//系统当前时间	string
    //V4.0.0
    private String  productLogo;  //产品logo
    private String ifRookie; //是否是新手标(1=新手标|2=非新手标)
    //V4.5.0
    public String hasRedPacket; //否有可使用红包字段
    public String jfqzProductDetailUrl; //玖富的产品详情Url

    public String getIfRookie() {
        return ifRookie;
    }

    public void setIfRookie(String ifRookie) {
        this.ifRookie = ifRookie;
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getBuyTotalMoney() {
        return buyTotalMoney;
    }

    public void setBuyTotalMoney(String buyTotalMoney) {
        this.buyTotalMoney = buyTotalMoney;
    }

    public String getBuyedTotalMoney() {
        return buyedTotalMoney;
    }

    public void setBuyedTotalMoney(String buyedTotalMoney) {
        this.buyedTotalMoney = buyedTotalMoney;
    }

    public String getBuyedTotalPeople() {
        return buyedTotalPeople;
    }

    public void setBuyedTotalPeople(String buyedTotalPeople) {
        this.buyedTotalPeople = buyedTotalPeople;
    }

    public String getCfpRecommend() {
        return cfpRecommend;
    }

    public void setCfpRecommend(String cfpRecommend) {
        this.cfpRecommend = cfpRecommend;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getFeeRatio() {
        return feeRatio;
    }

    public void setFeeRatio(String feeRatio) {
        this.feeRatio = feeRatio;
    }

    public String getProductRateText() {
        return productRateText;
    }

    public void setProductRateText(String productRateText) {
        this.productRateText = productRateText;
    }

    public String getFlowMaxRate() {
        return flowMaxRate;
    }

    public void setFlowMaxRate(String flowMaxRate) {
        this.flowMaxRate = flowMaxRate;
    }

    public String getFlowMinRate() {
        return flowMinRate;
    }

    public void setFlowMinRate(String flowMinRate) {
        this.flowMinRate = flowMinRate;
    }

    public String getIsFlow() {
        return isFlow;
    }

    public void setIsFlow(String isFlow) {
        this.isFlow = isFlow;
    }

    public String getIsQuota() {
        return isQuota;
    }

    public void setIsQuota(String isQuota) {
        this.isQuota = isQuota;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThirdProductId() {
        return thirdProductId;
    }

    public void setThirdProductId(String thirdProductId) {
        this.thirdProductId = thirdProductId;
    }


    public String getDetailOpenType() {
        return detailOpenType;
    }

    public void setDetailOpenType(String detailOpenType) {
        this.detailOpenType = detailOpenType;
    }


    public String getProductDetailUrl() {
        return productDetailUrl;
    }

    public void setProductDetailUrl(String productDetailUrl) {
        this.productDetailUrl = productDetailUrl;
    }

    public String getDeadLineValueText() {
        return deadLineValueText;
    }

    public void setDeadLineValueText(String deadLineValueText) {
        this.deadLineValueText = deadLineValueText;
    }

    public String getIsRedemption() {
        return isRedemption;
    }

    public void setIsRedemption(String isRedemption) {
        this.isRedemption = isRedemption;
    }

    public String getDeadLineMinValue() {
        return deadLineMinValue;
    }

    public void setDeadLineMinValue(String deadLineMinValue) {
        this.deadLineMinValue = deadLineMinValue;
    }

    public String getMinDeadLineType() {
        return minDeadLineType;
    }

    public void setMinDeadLineType(String minDeadLineType) {
        this.minDeadLineType = minDeadLineType;
    }

    public String getDeadLineMaxValue() {
        return deadLineMaxValue;
    }

    public void setDeadLineMaxValue(String deadLineMaxValue) {
        this.deadLineMaxValue = deadLineMaxValue;
    }

    public String getMaxDeadLineType() {
        return maxDeadLineType;
    }

    public void setMaxDeadLineType(String maxDeadLineType) {
        this.maxDeadLineType = maxDeadLineType;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getIsHaveProgress() {
        return isHaveProgress;
    }

    public void setIsHaveProgress(String isHaveProgress) {
        this.isHaveProgress = isHaveProgress;
    }


    public List<String> getTagListRight() {
        return tagListRight;
    }

    public void setTagListRight(List<String> tagListRight) {
        this.tagListRight = tagListRight;
    }

    public String getOrgFeeType() {
        return orgFeeType;
    }

    public void setOrgFeeType(String orgFeeType) {
        this.orgFeeType = orgFeeType;
    }

    public String getAssignmentTime() {
        return assignmentTime;
    }

    public void setAssignmentTime(String assignmentTime) {
        this.assignmentTime = assignmentTime;
    }

    public String getRedemptionTime() {
        return redemptionTime;
    }

    public void setRedemptionTime(String redemptionTime) {
        this.redemptionTime = redemptionTime;
    }

    public String getOrgIsstaticproduct() {
        return orgIsstaticproduct;
    }

    public void setOrgIsstaticproduct(String orgIsstaticproduct) {
        this.orgIsstaticproduct = orgIsstaticproduct;
    }

    public List<String> getTagListRightNewer() {
        return tagListRightNewer;
    }

    public void setTagListRightNewer(List<String> tagListRightNewer) {
        this.tagListRightNewer = tagListRightNewer;
    }

    public String getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(String saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }
}