package com.v5ent.xiubit;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.v5ent.xiubit.activity.GestureActivity;

public abstract class MyBaseActivity extends TopBaseActivity {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(ctx, GestureActivity.class);
	}

}
