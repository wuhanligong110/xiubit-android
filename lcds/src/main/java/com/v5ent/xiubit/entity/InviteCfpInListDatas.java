package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.toobei.common.entity.UserInfo;

public class InviteCfpInListDatas extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 1844396349677252490L;

	private String customerId;//客户id 
	private String customerName;//"客户名称", 
	private String customerMobile;//客户手机号
	private String nearInvest;//最近投资
	private String currInvestAmt;//在投总额
	private String totalInvestCount;//投资笔数

	private String emId;
	private String emPwd;
	private String faceUrl;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getNearInvest() {
		return nearInvest;
	}

	public void setNearInvest(String nearInvest) {
		this.nearInvest = nearInvest;
	}

	public String getCurrInvestAmt() {
		return currInvestAmt;
	}

	public void setCurrInvestAmt(String currInvestAmt) {
		this.currInvestAmt = currInvestAmt;
	}

	public String getTotalInvestCount() {
		return totalInvestCount;
	}

	public void setTotalInvestCount(String totalInvestCount) {
		this.totalInvestCount = totalInvestCount;
	}

	public String getEmId() {
		return emId;
	}

	public void setEmId(String emId) {
		this.emId = emId;
	}

	public String getEmPwd() {
		return emPwd;
	}

	public void setEmPwd(String emPwd) {
		this.emPwd = emPwd;
	}

	public UserInfo toUserInfo() {
		return new UserInfo(emId, customerName, customerMobile, customerId, emPwd, faceUrl, "", "","false");
	}

}