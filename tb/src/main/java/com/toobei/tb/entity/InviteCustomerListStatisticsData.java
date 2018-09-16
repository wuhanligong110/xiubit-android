package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class InviteCustomerListStatisticsData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 5281863863781892682L;

	private String regPersons; //已注册人数
	private String investPersons;//已投资人数

	public String getRegPersons() {
		return regPersons;
	}

	public void setRegPersons(String regPersons) {
		this.regPersons = regPersons;
	}

	public String getInvestPersons() {
		return investPersons;
	}

	public void setInvestPersons(String investPersons) {
		this.investPersons = investPersons;
	}

}