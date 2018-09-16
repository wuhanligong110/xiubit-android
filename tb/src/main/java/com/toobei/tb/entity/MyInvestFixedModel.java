package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class MyInvestFixedModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String deadLine;// 产品期限
	private String deadLineType;// 产品期限类型 1天2月
	private String endDate;// 结息日期
	private String inTransferAmount;// 转让中的金额
	private String investAmount;// 投资本金
	private String investId;// 投资记录id
	private String investTime;// 投资时间
	private String productName;// 产品名称
	private String profit;// 预计收益
	private String startDate;// 起息日期
	private String transferable;// 是否可转让
	private String transferableAmount;// 还可以转让金额
	private String transferableTime;// 可转让时间
	private String protocalName;//协议名称
	private String protocalUrl;//协议url


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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInTransferAmount() {
		return inTransferAmount;
	}

	public void setInTransferAmount(String inTransferAmount) {
		this.inTransferAmount = inTransferAmount;
	}

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTransferable() {
		return transferable;
	}

	public void setTransferable(String transferable) {
		this.transferable = transferable;
	}

	public String getTransferableAmount() {
		return transferableAmount;
	}

	public void setTransferableAmount(String transferableAmount) {
		this.transferableAmount = transferableAmount;
	}

	public String getTransferableTime() {
		return transferableTime;
	}

	public void setTransferableTime(String transferableTime) {
		this.transferableTime = transferableTime;
	}

	public String getProtocalName() {
		return protocalName;
	}

	public void setProtocalName(String protocalName) {
		this.protocalName = protocalName;
	}

	public String getProtocalUrl() {
		return protocalUrl;
	}

	public void setProtocalUrl(String protocalUrl) {
		this.protocalUrl = protocalUrl;
	}
}