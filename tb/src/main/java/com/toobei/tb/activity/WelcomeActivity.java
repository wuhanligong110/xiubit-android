package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWelcomeActivity;
import com.toobei.tb.R;

public class WelcomeActivity extends TopWelcomeActivity {

	@Override
	protected void skipGestureActivity() {
		skipActivity(this, MainActivity.class);
	}

	@Override
	protected void skipLoginActivity() {
		skipActivity(this, LoginActivity.class);
	} //不跳转登录

	@Override
	protected int[] getImagesResources() {
		return new int[] { R.drawable.img_welcome_01, R.drawable.img_welcome_02,
				R.drawable.img_welcome_03,R.drawable.img_welcome_04 };
	}

	@Override
	protected void skipMainActivity() {
		skipActivity(this, MainActivity.class);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(this, MainActivity.class);
	}

}
