package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class MyInvestDatasData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -2849906265970315380L;
	private String totalInvestAmount;// 总收益
	private List<MyInvestModel> datas;

	public String getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(String totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	public List<MyInvestModel> getDatas() {
		return datas;
	}

	public void setDatas(List<MyInvestModel> datas) {
		this.datas = datas;
	}

}