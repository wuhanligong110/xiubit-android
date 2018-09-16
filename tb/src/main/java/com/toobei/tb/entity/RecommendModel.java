package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class RecommendModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String productId; // 产品id
	private String productName; // 产品名称
	private String isFlow;// 1=固定利率|2=浮动利率
	private String fixRate; // 固定利率
	private String flowMaxRate; // 浮动最小利率
	private String flowMinRate; // 浮动最大利率
	private String deadLine; // 产品期限
	private String deadLineType;// 期限类型(1=天数|2=自然月)
	private String buyMaxMoney; // 产品单笔购买最大额度
	private String buyMinMoney; // 产品单笔购买最小额度
	private String remaMoney; // 剩余额度
	private String buyedTotalPeople; // 购买总人数
	private String type;//推荐产品类别，1：新手专享 | 2：今日推荐
	private String saleStatus; //销售状态(产品销售状态:1-预售、2-在售、3-售罄、4-下架)
	private String isQuota;//是否限额产品。1-限额、2-不限额
	private String beginSaleTimeText;//产品开售时间
	private String openType;//打开类型
	private String openLinkUrl;//URL地址

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

	public String getIsFlow() {
		return isFlow;
	}

	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}

	public String getFixRate() {
		return fixRate;
	}

	public void setFixRate(String fixRate) {
		this.fixRate = fixRate;
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

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public String getBuyMaxMoney() {
		return buyMaxMoney;
	}

	public void setBuyMaxMoney(String buyMaxMoney) {
		this.buyMaxMoney = buyMaxMoney;
	}

	public String getBuyMinMoney() {
		return buyMinMoney;
	}

	public void setBuyMinMoney(String buyMinMoney) {
		this.buyMinMoney = buyMinMoney;
	}

	public String getRemaMoney() {
		return remaMoney;
	}

	public void setRemaMoney(String remaMoney) {
		this.remaMoney = remaMoney;
	}

	public String getBuyedTotalPeople() {
		return buyedTotalPeople;
	}

	public void setBuyedTotalPeople(String buyedTotalPeople) {
		this.buyedTotalPeople = buyedTotalPeople;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeadLineType() {
		return deadLineType;
	}

	public void setDeadLineType(String deadLineType) {
		this.deadLineType = deadLineType;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}

	public String getBeginSaleTimeText() {
		return beginSaleTimeText;
	}

	public void setBeginSaleTimeText(String beginSaleTimeText) {
		this.beginSaleTimeText = beginSaleTimeText;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getOpenLinkUrl() {
		return openLinkUrl;
	}

	public void setOpenLinkUrl(String openLinkUrl) {
		this.openLinkUrl = openLinkUrl;
	}

}