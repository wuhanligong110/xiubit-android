package com.v5ent.xiubit.activity;

import android.content.Intent;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWithdraw;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明： Activity-提现 界面
 * @date 2015-10-23
 */
public class Withdraw extends TopWithdraw {

	@Override
	protected void skipCardManagerAdd(TopBaseActivity activity) {
		activity.skipActivity(activity, CardManagerAdd.class);
	}

	@Override
	protected Intent getIntentWithdrawSuccess(TopBaseActivity activity) {
		return new Intent(activity, WithdrawSuccess.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected void showWithdrawList(TopBaseActivity activity) {
		showActivity(this, WithdrawList.class);
	}
	
	@Override
	public void forgetPayPwd() {
		showActivity(Withdraw.this, ResetPayPwdIdentity.class);
	}


	@Override
	protected int setTextColor() {
		return R.color.text_blue_common;
	}

	@Override
	protected int setButtonDrawable() {
		return R.drawable.btn_blue_selector;
	}
}
