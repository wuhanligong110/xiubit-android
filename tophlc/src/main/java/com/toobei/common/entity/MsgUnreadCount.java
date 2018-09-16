package com.toobei.common.entity;

public class MsgUnreadCount extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -4802152484150349469L;

	private String bulletinMsgCount;//未读公告消息数量 
	private String personMsgCount;//未读个人消息数量

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