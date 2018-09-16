package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopFeedback;

public class Feedback extends TopFeedback {

	@Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}