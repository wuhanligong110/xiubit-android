package com.v5ent.xiubit;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.TopNetworkBaseActivity;
import com.toobei.common.entity.BaseResponseEntity;
import com.v5ent.xiubit.activity.GestureActivity;

@SuppressWarnings("rawtypes")
public abstract class MyNetworkBaseActivity<T extends BaseResponseEntity> extends
		TopNetworkBaseActivity<T> {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(ctx, GestureActivity.class);
	}

}
