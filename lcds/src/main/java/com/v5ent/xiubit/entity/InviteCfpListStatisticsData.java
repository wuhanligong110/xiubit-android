package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class InviteCfpListStatisticsData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 1579680917284373094L;
	
	private String rcPersons; //累计邀请人数
	private String regPersons;//已注册人数

	public String getRcPersons() {
		return rcPersons;
	}

	public void setRcPersons(String rcPersons) {
		this.rcPersons = rcPersons;
	}

	public String getRegPersons() {
		return regPersons;
	}

	public void setRegPersons(String regPersons) {
		this.regPersons = regPersons;
	}

}