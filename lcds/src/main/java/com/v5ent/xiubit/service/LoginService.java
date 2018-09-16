package com.v5ent.xiubit.service;

import com.toobei.common.service.TopLoginService;

public class LoginService extends TopLoginService {

	private static LoginService service = null;

	private LoginService() {
		super();
	}

	public static LoginService getInstance() {
		if (service == null) {
			service = new LoginService();
		}
		return service;
	}

}
