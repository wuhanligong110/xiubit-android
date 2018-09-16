package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class InvestmentRaiseDetailedModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String productId;//产品ID
	private String expectReturn;//预期年回报率
	private String invtmtName;// 投筹产品名称
	private String invtmtStatus;// 投筹状态 0-众筹中 1-众筹成功 2-分红中
	private String invtmtEndTime;// 投筹截止时间
	private String limitYear;// 投资期限年限
	private String renewalYear;// 投资续签年限
	private String surplusCopies;// 剩余份数
	private String buyCount;// 购买人数
	private String buyIncrsMoney;// 购买递增金额
	private String subscriptionAmount;// 认购额度
	private String dividendMethod;// 分红方式

	private String exitMethod;// 退出方式
	 private String availableBalance;// 可用余额
	private String buyableCopies;// 客户可买剩余份数
	private String openType;// 打开方式 0-原页打开 1-新页面打开
	private String openLinkUrl;// 打开链接地址页面url
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getExpectReturn() {
		return expectReturn;
	}
	public void setExpectReturn(String expectReturn) {
		this.expectReturn = expectReturn;
	}
	public String getInvtmtName() {
		return invtmtName;
	}
	public void setInvtmtName(String invtmtName) {
		this.invtmtName = invtmtName;
	}
	public String getInvtmtStatus() {
		return invtmtStatus;
	}
	public void setInvtmtStatus(String invtmtStatus) {
		this.invtmtStatus = invtmtStatus;
	}
	public String getInvtmtEndTime() {
		return invtmtEndTime;
	}
	public void setInvtmtEndTime(String invtmtEndTime) {
		this.invtmtEndTime = invtmtEndTime;
	}
	public String getLimitYear() {
		return limitYear;
	}
	public void setLimitYear(String limitYear) {
		this.limitYear = limitYear;
	}
	public String getRenewalYear() {
		return renewalYear;
	}
	public void setRenewalYear(String renewalYear) {
		this.renewalYear = renewalYear;
	}
	public String getSurplusCopies() {
		return surplusCopies;
	}
	public void setSurplusCopies(String surplusCopies) {
		this.surplusCopies = surplusCopies;
	}
	public String getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(String buyCount) {
		this.buyCount = buyCount;
	}
	public String getSubscriptionAmount() {
		return subscriptionAmount;
	}
	public void setSubscriptionAmount(String subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}
	public String getDividendMethod() {
		return dividendMethod;
	}
	public void setDividendMethod(String dividendMethod) {
		this.dividendMethod = dividendMethod;
	}
	public String getExitMethod() {
		return exitMethod;
	}
	public void setExitMethod(String exitMethod) {
		this.exitMethod = exitMethod;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getBuyableCopies() {
		return buyableCopies;
	}
	public void setBuyableCopies(String buyableCopies) {
		this.buyableCopies = buyableCopies;
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
	public String getBuyIncrsMoney() {
		return buyIncrsMoney;
	}
	public void setBuyIncrsMoney(String buyIncrsMoney) {
		this.buyIncrsMoney = buyIncrsMoney;
	}
}