package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

import java.util.List;

public class MyTeamSalesRecord extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -5297009764503193027L;

	private String allowance;//收益
	private String allowanceType; //收益类别名称
	private String bizDate;//销售时间
	private String feeRate;//收益率
	private String productName;//产品名称
	private String purAmount;//销售额
	private String leaderReward; //leader奖励
	//V3.0.0
	private String userName; //名字
	private String mobile; //手机号
	private String gradeDesc; //级别描述；例：下级理财师
	private List<AllowanceDatas> allowanceList; //	津贴明细

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public List<AllowanceDatas> getAllowanceList() {
		return allowanceList;
	}

	public void setAllowanceList(List<AllowanceDatas> allowanceList) {
		this.allowanceList = allowanceList;
	}

	public String getBizDate() {
		return bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}



	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPurAmount() {
		return purAmount;
	}

	public void setPurAmount(String purAmount) {
		this.purAmount = purAmount;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getAllowanceType() {
		return allowanceType;
	}

	public void setAllowanceType(String allowanceType) {
		this.allowanceType = allowanceType;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getLeaderReward() {
		return leaderReward;
	}

	public void setLeaderReward(String leaderReward) {
		this.leaderReward = leaderReward;
	}
}
