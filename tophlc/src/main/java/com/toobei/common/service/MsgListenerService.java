package com.toobei.common.service;

import java.util.HashMap;
import java.util.List;

import com.easemob.chat.EMMessage;

/**
 * 公司: tophlc
 * 类说明：消息监听界面管理服务类
 * @date 2015-12-10
 */
public class MsgListenerService {

	private static MsgListenerService service = null;
	private HashMap<String, MsgListener> msgListenerMap = null;

	private MsgListenerService() {
		msgListenerMap = new HashMap<String, MsgListener>();
	}

	public static synchronized MsgListenerService getInstance() {
		if (service == null) {
			service = new MsgListenerService();
		}
		return service;
	}

	/**
	 * 功能：注册监听器，在界面启动时添加，停止后取消
	 * @param key
	 * @param listener
	 */
	public void registerMsgListener(String key, MsgListener listener) {
		msgListenerMap.put(key, listener);
	}

	/**
	 * 功能：取消监听器，在界面停止后取消
	 * @param key
	 */
	public void unregisterMsgListener(String key) {
		if (msgListenerMap.containsKey(key)) {
			msgListenerMap.remove(key);
		}
	}

	public void listenersOnMsgReceiver(EMMessage msg, List<EMMessage> offLineMsgs) {
		for (String string : msgListenerMap.keySet()) {
			MsgListener listener = msgListenerMap.get(string);
			System.out.println("listenersOnMsgReceiver = key " + string);
			if (listener != null)
				msgListenerMap.get(string).onMsgReceiver(msg, offLineMsgs);
		}
	}

	/*
		public void listenersOnMsgSendStart(EMMessage msg) {
			for (String string : msgListenerMap.keySet()) {
				MsgListener listener = msgListenerMap.get(string);
				if (listener != null)
					msgListenerMap.get(string).onMsgSendStart(msg);
			}
		}*/

	public void listenersOnMessageFailure(EMMessage msg) {
		for (String string : msgListenerMap.keySet()) {
			MsgListener listener = msgListenerMap.get(string);
			if (listener != null)
				msgListenerMap.get(string).onMsgSendFailure(msg);
		}
	}

	public void listenersOnMsgSendSuccess(EMMessage msg) {
		for (String string : msgListenerMap.keySet()) {
			MsgListener listener = msgListenerMap.get(string);
			if (listener != null)
				msgListenerMap.get(string).onMsgSendSuccess(msg);
		}
	}

	/**
	 * 功能：得到当前msgListener的数量
	 * @return
	 */
	public int getMsgListenerCount() {
		if (msgListenerMap != null) {
			return msgListenerMap.size();
		}
		return 0;
	}
}
