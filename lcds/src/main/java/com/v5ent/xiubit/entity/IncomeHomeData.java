package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

public class IncomeHomeData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2569307813990315312L;
	private String minDate;//收益记录最小时间
	private String dayProfit;//今日收益
	private String sumProfit;//全部收益
	private String monthProfit;//本月收益
	private String totalProfit;//累计收益
	private List<IncomeType> items;//收益项

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
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

	public String getSumProfit() {
		return sumProfit;
	}

	public void setSumProfit(String sumProfit) {
		this.sumProfit = sumProfit;
	}

	public List<IncomeType> getItems() {
		return items;
	}

	public void setItems(List<IncomeType> items) {
		this.items = items;
	}

}