package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopFeedback;

/**
 * 公司: tophlc
 * 类说明：反馈界面
 * @date 2015-11-3
 */
public class Feedback extends TopFeedback {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
