package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWithdrawList;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：提现记录
 * @date 2016-4-8
 */
public class WithdrawList extends TopWithdrawList {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int getTextColor() {
        return R.color.text_red_common;
    }

	@Override
	protected int getImageResId() {
		return R.drawable.iv_mine_questions;
	}


}
