package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MineSetting extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2204816178574094666L;

	private String authentication;//实名认证状态(true已认证)
	private String bundBankcard;//银行卡绑定状态(true已绑定

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getBundBankcard() {
		return bundBankcard;
	}

	public void setBundBankcard(String bundBankcard) {
		this.bundBankcard = bundBankcard;
	}

}