package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class CustomerTrade extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 6657490484192144895L;

	private String time;//发生时间
	private String customerName;//客户名称
	private String customerMobile;//客户手机号码
	private String amt;//金额(投资、赎回)
	private String productName;// 产品名称
	private String yearRate;//年化
	private String feeRate;//佣金
	private String startDate;//起息日期
	private String endDate;//到期日期
	private String profit;//客户预收益
	private String feeProfit;//我的佣金

	private String tradeType;//交易类别 

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getFeeProfit() {
		return feeProfit;
	}

	public void setFeeProfit(String feeProfit) {
		this.feeProfit = feeProfit;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}