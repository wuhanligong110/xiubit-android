package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopResetLoginPwdPhone;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-重设登录密码 填写手机号界面
 * @date 2016-3-23
 */
public class ResetLoginPwdPhone extends TopResetLoginPwdPhone {

	@Override
	protected void onSendVcodeSuccess(String phone) {
		Intent intent = new Intent(this, ResetLoginPwdNew.class);
		intent.putExtra("phone", phone);
		showActivity(this, intent);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int getBtnResId() {
		return R.drawable.btn_login_selector;
	}

}
