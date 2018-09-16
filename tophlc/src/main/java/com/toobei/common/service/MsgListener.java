package com.toobei.common.service;

import java.util.List;

import com.easemob.chat.EMMessage;

public interface MsgListener {
	String getListenerId();

	void onMsgReceiver(EMMessage msg, List<EMMessage> offLineMsgs);

	void onMsgSendFailure(EMMessage msg);

	void onMsgSendSuccess(EMMessage msg);

}
