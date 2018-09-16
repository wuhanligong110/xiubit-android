package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class InviteCfpRecordStatistic extends BaseEntity {

	private static final long serialVersionUID = 7089554979505364009L;
	private String regiteredCount; //	邀请理财师	number

	public String getRegiteredCount() {
		return regiteredCount;
	}

	public void setRegiteredCount(String regiteredCount) {
		this.regiteredCount = regiteredCount;
	}
}