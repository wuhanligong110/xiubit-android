package com.toobei.common.service;

import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.toobei.common.TopApp;

import org.xsl781.ui.ActivityStack;

/**
 * 公司: tophlc
 * 类说明：实现ConnectionListener接口
 * @date 2016-1-5
 */
public class MyConnectionListener implements EMConnectionListener {
	@Override
	public void onConnected() {
		//已连接到服务器
		System.out.println("通信帐号登录成功");
		TopApp.getInstance().getLoginService().isEMServerLogin = true;
	}

	@Override
	public void onDisconnected(final int error) {
		ActivityStack.getInstance().topActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {

				if (error == EMError.USER_REMOVED) {
					// 显示帐号已经被移除
				} else if (error == EMError.CONNECTION_CONFLICT) {
					// 显示帐号在其他设备登录
				//	ToastUtil.showCustomToast("通信帐号在其他设备登录");
					System.out.println("通信帐号在其他设备登录");
				} else {

				}
				TopApp.getInstance().getLoginService().isEMServerLogin = false;
				System.out.println("MyConnectionListener  onDisconnected error = " + error);
			}
		});
	}
}