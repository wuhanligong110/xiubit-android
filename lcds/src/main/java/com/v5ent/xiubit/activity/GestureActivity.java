package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.view.View.OnClickListener;

import com.toobei.common.activity.TopGestureActivity;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-手势登录界面
 *
 * @date 2015-9-28
 */
public class GestureActivity extends TopGestureActivity implements OnClickListener {


	@Override
	protected void skipLoginActivity() {
		Intent intent = new Intent(ctx,LoginActivity.class);
		intent.putExtra("is2LoginOtherAccount",true);
		startActivity(intent);
	}

	@Override
	protected void skipMainActivity() {
		skipActivity(this, MainActivity.class);
	}

	@Override
	protected void showPwdManagerGestureVerify() {
//		showActivity(this, PwdManagerGestureVerify.class);
//		Intent intent = new Intent(activity, GestureSetActivity.class);
//		intent.putExtra("whereFrom", "PwdManagerGestureVerify");
		Intent intent = new Intent(this, PwdManagerGestureVerify.class);
		intent.putExtra("whereFrom", "GestureActivityForgetPWD");
		startActivity(intent);
	}

	@Override
	protected int[] setLocusPassWordViewImage() {
		return new int[]{R.drawable.img_locus_round_click, R.drawable.img_locus_line};
	}

	@Override
	protected void skipTopGestureSetActivity() {

	}

	@Override
	protected int setBackgroundColor() {
//		return R.color.top_title_text;
		return R.drawable.gesture_bg;
	}
}