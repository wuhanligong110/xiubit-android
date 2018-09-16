package com.v5ent.xiubit;

import android.content.Intent;
import android.os.Bundle;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.TopTitleBaseActivity;
import com.v5ent.xiubit.activity.GestureActivity;

public abstract class MyTitleBaseActivity extends TopTitleBaseActivity {
	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(ctx, GestureActivity.class);
	}
}
