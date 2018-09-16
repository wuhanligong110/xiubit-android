package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWithdrawSuccess;

/**
 * 公司: tophlc
 * 类说明： 提现成功界面
 * @date 2016-4-8
 */
public class WithdrawSuccess extends TopWithdrawSuccess {

	@Override
	protected void skipWithdrawList(TopBaseActivity activity) {
		skipActivity(this, WithdrawList.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}
}
