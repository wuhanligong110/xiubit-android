package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：收益类别 实体
 * @date 2015-10-22
 */
public class IncomeType extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 7981969641841305502L;

	private String profitTypeId;//收益类别id
	private String profitType;//收益类别

	public IncomeType(String profitTypeId, String profitType) {
		super();
		this.profitTypeId = profitTypeId;
		this.profitType = profitType;
	}

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

}