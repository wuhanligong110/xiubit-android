package com.toobei.common.service;

import java.util.List;

import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMMessage;
import com.toobei.common.TopApp;

public class MyEMEventlistener implements EMEventListener {

	@SuppressWarnings("unchecked")
	@Override
	public void onEvent(EMNotifierEvent event) {
		switch (event.getEvent()) {
		case EventNewMessage:
			//接收到新消息
			EMMessage msg = (EMMessage) event.getData();
			TopApp.getInstance().getChatService().onMsgReceiver(msg, null);
			break;
		case EventDeliveryAck:
			break;
		case EventReadAck:
			break;
		case EventOfflineMessage:
			//接收离线消息event注册
			List<EMMessage> offlineMessages = (List<EMMessage>) event.getData();
			TopApp.getInstance().getChatService().onMsgReceiver(null, offlineMessages);
			break;
		default:
			break;
		}
	}
}
