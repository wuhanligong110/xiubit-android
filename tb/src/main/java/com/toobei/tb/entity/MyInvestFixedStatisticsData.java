package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class MyInvestFixedStatisticsData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String totalProfit; // 累计收益(元)
	private String balance; // 当前投资(元)

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

}