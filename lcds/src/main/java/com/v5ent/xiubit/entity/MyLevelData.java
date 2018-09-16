package com.v5ent.xiubit.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class MyLevelData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2292751873011975728L;

	private MyLevelDetail myCfpLevel;////理财师等级
	private List<MyCfpLevelData> cfpLevels;

	//	private MyRankDetail partnerLevel;

	public void setMyCfpLevel(MyLevelDetail myCfpLevel) {
		this.myCfpLevel = myCfpLevel;
	}

	public List<MyCfpLevelData> getCfpLevels() {
		return cfpLevels;
	}

	public void setCfpLevels(List<MyCfpLevelData> cfpLevels) {
		this.cfpLevels = cfpLevels;
	}

	public MyLevelDetail getMyCfpLevel() {
		return myCfpLevel;
	}

}