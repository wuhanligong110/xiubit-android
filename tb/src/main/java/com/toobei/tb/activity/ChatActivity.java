package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopChatActivity;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：对话界面
 * @date 2015-12-16
 */
public class ChatActivity extends TopChatActivity {

	private String productName;
	private String productId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(isCallCenter){
			headerLayout.showTitle("客服");
		}else{
			headerLayout.showTitle("与理财师的互动");
		}
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	public void initData(Intent intent) {
		super.initData(intent);
		productName = intent.getStringExtra("productName");
		productId = intent.getStringExtra("productId");
		if (!TextUtils.isEmpty(productName)) {
			String defaultText = "#" + productName + "# ";
			contentEdit.setText(defaultText);
			contentEdit.setSelection(defaultText.length());
		}
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
		return R.drawable.btn_chart_send;
	}

	@Override
	protected int getChatBgResId() {
		return R.drawable.chat_left_qp_toobei;
	}
}
