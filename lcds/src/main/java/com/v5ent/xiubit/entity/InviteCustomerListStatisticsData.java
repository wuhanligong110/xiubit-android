package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class InviteCustomerListStatisticsData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 3076607387599300924L;

	private String regCustomer; //注册客户
	private String investCustomer;//投资客户

	public String getRegCustomer() {
		return regCustomer;
	}

	public void setRegCustomer(String regCustomer) {
		this.regCustomer = regCustomer;
	}

	public String getInvestCustomer() {
		return investCustomer;
	}

	public void setInvestCustomer(String investCustomer) {
		this.investCustomer = investCustomer;
	}

}