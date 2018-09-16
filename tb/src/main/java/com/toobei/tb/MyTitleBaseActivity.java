package com.toobei.tb;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.TopTitleBaseActivity;
import com.toobei.tb.activity.GestureActivity;

public abstract class MyTitleBaseActivity extends TopTitleBaseActivity {
	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(ctx, GestureActivity.class);
	}
}
