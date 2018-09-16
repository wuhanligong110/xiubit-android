package com.toobei.common.service;

import com.easemob.EMCallBack;
import com.easemob.chat.EMMessage;
import com.toobei.common.TopApp;

public class MyEMCallBack implements EMCallBack {
	private EMMessage msg;

	public MyEMCallBack(EMMessage msg) {
		super();
		this.msg = msg;
	}

	@Override
	public void onError(int arg0, String arg1) {
		TopApp.getInstance().getChatService().onMsgSendFailure(msg);
	}

	@Override
	public void onProgress(int arg0, String arg1) {

	}

	@Override
	public void onSuccess() {
		TopApp.getInstance().getChatService().onMsgSendSuccess(msg);
	}

}
