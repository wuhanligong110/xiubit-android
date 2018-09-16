package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPwdManagerLogin;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-修改登录密码界面
 * @date 2016-3-23
 */
public class PwdManagerLogin extends TopPwdManagerLogin {

	@Override
	protected void onBtnForgeLoginPasswdClick() {
		showActivity(ctx, ResetLoginPwdPhone.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int getBtnColor() {
		return R.drawable.btn_login_selector;
	}

}
