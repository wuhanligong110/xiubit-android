package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：首页 公告实体
 * @date 2015-10-22
 */
public class HomePagerBulletin extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 9097651647471724205L;
	private String wealthNewsName;//财富快报
	private String todayProfit;//今日收益
	private String tradeCount; // 交易动态

	public String getWealthNewsName() {
		return wealthNewsName;
	}

	public void setWealthNewsName(String wealthNewsName) {
		this.wealthNewsName = wealthNewsName;
	}

	public String getTodayProfit() {
		return todayProfit;
	}

	public void setTodayProfit(String todayProfit) {
		this.todayProfit = todayProfit;
	}

	public String getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}

}