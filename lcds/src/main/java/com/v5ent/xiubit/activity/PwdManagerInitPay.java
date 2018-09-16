package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPwdManagerInitPay;

/**
 * 公司: tophlc
 * 类说明：Activity-初始化交易密码
 * @date 2016-3-22
 */
public class PwdManagerInitPay extends TopPwdManagerInitPay {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
