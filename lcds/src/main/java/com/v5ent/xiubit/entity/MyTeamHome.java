package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MyTeamHome extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8445820819231789262L;

	private String quarterPartnerFee;//本季团队佣金
	private String partnerCount;//团队人数
	private String myRcCount;//直接推荐
	private String childrenRcCount;//间接推荐
	private String grandChildrenRcCount;//直接下级理财师的下下级

	public String getGrandChildrenRcCount() {
		return grandChildrenRcCount;
	}

	public void setGrandChildrenRcCount(String grandChildrenRcCount) {
		this.grandChildrenRcCount = grandChildrenRcCount;
	}

	public String getQuarterPartnerFee() {
		return quarterPartnerFee;
	}

	public void setQuarterPartnerFee(String quarterPartnerFee) {
		this.quarterPartnerFee = quarterPartnerFee;
	}

	public String getPartnerCount() {
		return partnerCount;
	}

	public void setPartnerCount(String partnerCount) {
		this.partnerCount = partnerCount;
	}

	public String getMyRcCount() {
		return myRcCount;
	}

	public void setMyRcCount(String myRcCount) {
		this.myRcCount = myRcCount;
	}

	public String getChildrenRcCount() {
		return childrenRcCount;
	}

	public void setChildrenRcCount(String childrenRcCount) {
		this.childrenRcCount = childrenRcCount;
	}

}