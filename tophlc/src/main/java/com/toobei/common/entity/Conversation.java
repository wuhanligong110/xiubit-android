package com.toobei.common.entity;

import com.easemob.chat.EMConversation;

/**
 * 公司: tophlc
 * 类说明：会话实体
 * @date 2015-12-16
 */
public class Conversation extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1123245438302096323L;

	private UserInfo userInfo;

	private EMConversation emConversation;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public EMConversation getEmConversation() {
		return emConversation;
	}

	public void setEmConversation(EMConversation emConversation) {
		this.emConversation = emConversation;
	}

}