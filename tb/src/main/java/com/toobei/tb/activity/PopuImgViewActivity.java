package com.toobei.tb.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopPopuImgViewActivity;

/**
 * 公司: tophlc
 * 类说明：此类用于点击图片消失，双击图片放大
 * @date 2016-5-13
 */
public class PopuImgViewActivity extends TopPopuImgViewActivity {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

}
