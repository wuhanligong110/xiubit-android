package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopMineMsgCenter;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-消息中心
 * @date 2015-10-26
 */
public class MineMsgCenter extends TopMineMsgCenter {

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected Intent getIntentWebActivityCommon(TopBaseActivity activity) {
		return new Intent(activity, WebActivityCommon.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected int setCursorColor() {
		return R.color.text_blue_common;
	}

	@Override
	protected int getDrawableId() {
		return R.drawable.checkbox;
	}
}
