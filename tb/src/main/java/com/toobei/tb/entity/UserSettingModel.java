package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class UserSettingModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String authentication;// 实名认证状态(true已认证)
	private String bundBankcard;// 银行卡绑定状态(true已绑定)

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