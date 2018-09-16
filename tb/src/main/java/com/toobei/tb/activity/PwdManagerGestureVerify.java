package com.toobei.tb.activity;

import android.content.Intent;
import android.view.View;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPwdManagerGestureVerify;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：修改手势密码 验证登录密码
 * @date 2016-3-22
 */
public class PwdManagerGestureVerify extends TopPwdManagerGestureVerify implements
		View.OnClickListener {

	@Override
	public void onCheckLoginPwdValid() {
		if (GestureActivity.activity != null) {
			GestureActivity.activity.finish();
		}

		Intent intent = new Intent(activity, GestureSetActivity.class);
		intent.putExtra("whereFrom", TextUtil.isEmpty(whereFrom)?"PwdManagerGestureVerify":whereFrom);
		skipActivity(activity, intent);
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	public int getBtnColor() {
		return R.drawable.btn_common_round_selector;
	}
}
