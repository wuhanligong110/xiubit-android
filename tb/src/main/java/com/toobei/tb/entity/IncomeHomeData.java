package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class IncomeHomeData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2569307813990315312L;
	private String minTime;//收益记录最小时间
	private String dayProfit;//今日收益
	private String monthProfit;//本月收益
	private String totalProfit;//累计收益

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getDayProfit() {
		return dayProfit;
	}

	public void setDayProfit(String dayProfit) {
		this.dayProfit = dayProfit;
	}

	public String getMonthProfit() {
		return monthProfit;
	}

	public void setMonthProfit(String monthProfit) {
		this.monthProfit = monthProfit;
	}

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

}