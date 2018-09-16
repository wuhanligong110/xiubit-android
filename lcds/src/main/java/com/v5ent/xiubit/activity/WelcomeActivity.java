package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopWelcomeActivity;
import com.v5ent.xiubit.R;

/**
 * Activity-引导页面
 */
public class WelcomeActivity extends TopWelcomeActivity {

    @Override
    protected void skipGestureActivity() {
        skipActivity(this, GestureActivity.class);
    }

    @Override
    protected void skipLoginActivity() {
        skipActivity(this, LoginActivity.class);
    }

    @Override
    protected int[] getImagesResources() {
        // 更换app引导介绍图片
        return new int[]{R.drawable.img_welcome_01, R.drawable.img_welcome_02, R.drawable.img_welcome_03
                ,R.drawable.img_welcome_04
        };
    }

    @Override
    protected void skipMainActivity() {
        skipActivity(this, MainActivity.class);
    }

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(this, GestureActivity.class);
    }

}
