package com.toobei.tb;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.tb.activity.GestureActivity;

public abstract class MyBaseActivity extends TopBaseActivity {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(ctx, GestureActivity.class);
	}

}
