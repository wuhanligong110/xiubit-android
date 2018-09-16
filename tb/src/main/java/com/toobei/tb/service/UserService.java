package com.toobei.tb.service;

import com.toobei.common.entity.UserInfo;
import com.toobei.common.entity.UserInfoEntity;
import com.toobei.common.service.TopUserService;
import com.toobei.tb.MyApp;

public class UserService extends TopUserService {

	private static UserService service = null;

	protected UserService() {
		super();
		service = this;
	}

	public static synchronized UserService getInstance() {
		if (service == null) {
			service = new UserService();
		}
		return service;
	}

	/**
	 * 功能：用于获取当前用户的信息
	 * @return
	 */
	@Override
	public UserInfo getCurUserByTokenFromMyServer() {
		UserInfoEntity entity = null;
		try {
			entity = MyApp.getInstance().getHttpService()
					.userLevelInfo(MyApp.getInstance().getLoginService().token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (entity != null && entity.getData() != null) {
			return entity.getData();
		}
		return null;
	}

}
