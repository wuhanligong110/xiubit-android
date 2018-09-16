package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPwdManagerLogin;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：修改登录密码界面
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
		return R.drawable.btn_common_round_selector;
	}

}
