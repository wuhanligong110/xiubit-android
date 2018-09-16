package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MyCfpLevelData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -5383860101501911612L;

	private String name;
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}