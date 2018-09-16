package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class MyProfitModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String profitTypeId;// 类型ID，见4.1收益类型定义
	private String profitType;// 收益类别名称
	private String profit;// 收益

	public String getProfitTypeId() {
		return profitTypeId;
	}

	public void setProfitTypeId(String profitTypeId) {
		this.profitTypeId = profitTypeId;
	}

	public String getProfitType() {
		return profitType;
	}

	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "MyProfitModel [profitTypeId=" + profitTypeId + ", profitType="
				+ profitType + ", profit=" + profit + "]";
	}

}