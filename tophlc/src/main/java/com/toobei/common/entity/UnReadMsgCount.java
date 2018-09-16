package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class UnReadMsgCount extends BaseEntity {


	private static final long serialVersionUID = -8793670333135625496L;


	/**
	 * bulletinMsgCount : 0
	 * personMsgCount : 0
	 */

	private String bulletinMsgCount;
	private String personMsgCount;

	public String getBulletinMsgCount() {
		return bulletinMsgCount;
	}

	public void setBulletinMsgCount(String bulletinMsgCount) {
		this.bulletinMsgCount = bulletinMsgCount;
	}

	public String getPersonMsgCount() {
		return personMsgCount;
	}

	public void setPersonMsgCount(String personMsgCount) {
		this.personMsgCount = personMsgCount;
	}
}