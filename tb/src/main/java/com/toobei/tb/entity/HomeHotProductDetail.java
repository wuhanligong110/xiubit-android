package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

/**
 * 公司: tophlc 类说明：理财产品
 *
 * @date 2015-10-28
 */
public class HomeHotProductDetail extends BaseEntity {


    private static final long serialVersionUID = 3942525023454121987L;


    /**
     * buyTotalMoney	产品总额度	number
     * buyedTotalMoney	产品被投资总额	number
     * buyedTotalPeople	产品已投资人数	number
     * cfpRecommend	是否理财师推荐	string	0-未推荐 1-已推荐
     * deadLine	期限	number
     * deadLineMaxValue	产品最大期限天数	number
     * deadLineMaxValueText	产品最大期限天数 自定义显示	string
     * deadLineMinValue	产品最小期限天数	number
     * deadLineMinValueText	产品最小期限天数 自定义显示	string
     * deadLineType	期限类型：1天，2月	number
     * deadLineValueText	产品期限Text	string	产品期限 最终显示文本
     * detailOpenType	产品详情打开方式	number	0-原页打开 1-新页面打开
     * productDetailUrl	产品详情打开URL	string
     * feeRatio	佣金率	number
     * fixRate	固定利率	number
     * flowMaxRate	浮动最大利率	number
     * flowMinRate	浮动最小利率	number
     * isFixedDeadline	是否固定期限	number	1=固定期限|2=浮动期限
     * isFlow	1固定利率；2浮动利率	number
     * isQuota	是否限额产品	number	1-限额、2-不限额
     * isRedemption	是否可赎回可转让	number	0=不支持赎回和转让|1=可赎回|2=可转让
     * orgName	机构名称	string
     * orgNumber	机构编码	string
     * productId	产品id	string
     * productName	产品名称	string
     * productRateText	产品利率Text	string	产品利率 最终显示文本
     * saleStatus	产品状态	number	1-在售|2-售罄|3-募集失败
     * thirdProductId	外部产品uuid	string
     */


    private String buyTotalMoney;
    private String buyedTotalMoney;
    private String buyedTotalPeople;
    private String cfpRecommend;
    private String deadLine;
    private String deadLineType;
    private String deadLineValue;
    private String productDetailUrl; //productDetailUrl=http://liecai.tophlc.com/pages/financing/product_detail.html
    private String feeRatio;
    private String productRateText; //产品利率
    private String flowMaxRate;
    private String flowMinRate;
    private String isFlow;
    private String isQuota;
    private String orgName;
    private String orgNumber;
    private String productId;
    private String productName;
    private String status;
    private String thirdProductId;
    private String isRedemption;  //是否可赎回可转让	number	0=不支持赎回和转让|1=可赎回|2=可转让;
    private String isHaveProgress ;   //是否拥有产品进度	number	0=有|1没有
   private  List<String>  tagListRight   ;   // 右上角首投字段

    private String deadLineMinValue;
    private String minDeadLineType;
    private String deadLineMaxValue;
    private String maxDeadLineType;
    //  deadLineMaxValue=50
    private String deadLineValueText;  //deadLineValueText=30天~50天
    private List<String> tagList;

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

    private String detailOpenType;

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
}