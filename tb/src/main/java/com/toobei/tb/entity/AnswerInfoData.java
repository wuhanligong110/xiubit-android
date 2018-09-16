package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class AnswerInfoData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 4320889048811653573L;

	private String name;
	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}