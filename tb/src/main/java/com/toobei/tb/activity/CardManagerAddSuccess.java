package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopCardManagerAddSuccess;

/**
 * 公司: tophlc
 * 类说明：添加银行卡 绑定成功
 * @date 2015-10-13
 */
public class CardManagerAddSuccess extends TopCardManagerAddSuccess {

	@Override
	protected void skipPwdManagerInitPay() {
		skipActivity(CardManagerAddSuccess.this, PwdManagerInitPay.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
