package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopResetLoginPwdNew;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：重置登录密码，输入新的密码
 * @date 2016-3-23
 */
public class ResetLoginPwdNew extends TopResetLoginPwdNew {

	@Override
	protected void skipToLoginActivity() {
		if (ResetLoginPwdPhone.activity != null) {
			ResetLoginPwdPhone.activity.finish();
		}
		skipActivity(this, LoginActivity.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int setTextColor() {
		return R.color.btn_common_orange;
	}

	@Override
	protected int setBtnColor() {
		return R.drawable.btn_common_round_selector;
	}
}
