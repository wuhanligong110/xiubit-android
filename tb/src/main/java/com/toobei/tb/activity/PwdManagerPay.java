package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPwdManagerPay;

/**
 * 公司: tophlc
 * 类说明：修改交易密码
 * @date 2016-3-23
 */
public class PwdManagerPay extends TopPwdManagerPay {

	@Override
	public void onBtnForgePayPasswdClick() {
		skipActivity(ctx, ResetPayPwdIdentity.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
