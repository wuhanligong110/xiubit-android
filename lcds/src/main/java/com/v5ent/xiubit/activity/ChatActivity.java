package com.v5ent.xiubit.activity;

import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopChatActivity;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-客服聊天
 * @date 2015-12-16
 */
public class ChatActivity extends TopChatActivity {
	
	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	protected Intent getImageSelectActivityIntent() {
		return new Intent(this, ImageSelectActivity.class);
	}

	@Override
	protected Intent getPopuImgViewActivityIntent() {
		return new Intent(this, PopuImgViewActivity.class);
	}

	@Override
	protected int getBtnSendImgResId() {
		return R.drawable.btn_chat_img;
	}

	@Override
	protected int getSendBtnResId() {
		return R.drawable.btn_chart_send_selector;
	}

	@Override
	protected int getChatBgResId() {
		return R.drawable.chat_left_qp;
	}
}
