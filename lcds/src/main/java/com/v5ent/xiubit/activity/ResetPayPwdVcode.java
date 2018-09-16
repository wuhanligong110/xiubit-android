package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopResetPayPwdVcode;

/**
 * 公司: tophlc
 * 类说明：Activity-重置交易密码 填写验证码
 * @date 2016-3-23
 */
public class ResetPayPwdVcode extends TopResetPayPwdVcode {

	@Override
	protected void onResetPayPwdSuccess(String resetPayPwdToken) {
		//成功跳转
		Intent intent = new Intent(ResetPayPwdVcode.this, PwdManagerInitPay.class);
		//带token
		intent.putExtra("resetPayPwdToken", resetPayPwdToken);
		skipActivity(ResetPayPwdVcode.this, intent);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
