package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class MyProfitDatasData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -2849906265970315380L;
	private String totalProfit;// 总收益
	private List<MyProfitModel> datas;

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	public List<MyProfitModel> getDatas() {
		return datas;
	}

	public void setDatas(List<MyProfitModel> datas) {
		this.datas = datas;
	}

}