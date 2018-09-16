
package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class MyLevelData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2292751873011975728L;

	private MyLevelDetail cfgLevel;
	private MyRankDetail partnerLevel;

	public MyLevelDetail getCfgLevel() {
		return cfgLevel;
	}

	public void setCfgLevel(MyLevelDetail cfgLevel) {
		this.cfgLevel = cfgLevel;
	}

	public MyRankDetail getPartnerLevel() {
		return partnerLevel;
	}

	public void setPartnerLevel(MyRankDetail partnerLevel) {
		this.partnerLevel = partnerLevel;
	}

}