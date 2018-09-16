package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.v5ent.xiubit.R;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWithdrawList;

/**
 * 公司: tophlc
 * 类说明：Activity-提现记录
 * @date 2016-4-8
 */
public class WithdrawList extends TopWithdrawList {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int getTextColor() {
		return R.color.text_blue_common;
	}

	@Override
	protected int getImageResId() {
		return R.drawable.img_question;
	}

}
