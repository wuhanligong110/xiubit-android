package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class CustomerHomeData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 103546626826787498L;

	private String minTime;//交易记录最小时间
	private String dayInvestAmt;//当日投资金额
	private String monthInvestAmt;//本月投资金额
	private String totalInvestAmt;//累计投资总额
	private String monthInvestCount;//本月投资笔数
	private String totalInvestCount;//累计投资笔数
	private String monthInvestPersonCount;//本月投资人数
	private String totalInvestPersonCount;//累计投资人数
	private String tradeCount;//交易动态

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getDayInvestAmt() {
		return dayInvestAmt;
	}

	public void setDayInvestAmt(String dayInvestAmt) {
		this.dayInvestAmt = dayInvestAmt;
	}

	public String getMonthInvestAmt() {
		return monthInvestAmt;
	}

	public void setMonthInvestAmt(String monthInvestAmt) {
		this.monthInvestAmt = monthInvestAmt;
	}

	public String getTotalInvestAmt() {
		return totalInvestAmt;
	}

	public void setTotalInvestAmt(String totalInvestAmt) {
		this.totalInvestAmt = totalInvestAmt;
	}

	public String getMonthInvestCount() {
		return monthInvestCount;
	}

	public void setMonthInvestCount(String monthInvestCount) {
		this.monthInvestCount = monthInvestCount;
	}

	public String getTotalInvestCount() {
		return totalInvestCount;
	}

	public void setTotalInvestCount(String totalInvestCount) {
		this.totalInvestCount = totalInvestCount;
	}

	public String getMonthInvestPersonCount() {
		return monthInvestPersonCount;
	}

	public void setMonthInvestPersonCount(String monthInvestPersonCount) {
		this.monthInvestPersonCount = monthInvestPersonCount;
	}

	public String getTotalInvestPersonCount() {
		return totalInvestPersonCount;
	}

	public void setTotalInvestPersonCount(String totalInvestPersonCount) {
		this.totalInvestPersonCount = totalInvestPersonCount;
	}

	public String getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}

}