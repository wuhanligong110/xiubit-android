package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;

import com.toobei.common.entity.Custom;
import com.toobei.common.utils.MyNetAsyncTask;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.service.ChatService;
import com.v5ent.xiubit.util.C;

import org.xsl781.utils.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：Activity-新建会话界面
 * @date 2015-12-17
 */
public class ConverNewActivity extends MyTitleBaseActivity implements OnClickListener,
		OnTouchListener {
	private TextView textCustomerNames;
	private EditText contentEdit;
	private List<Custom> checkedUsers;

	public void onCreate(Bundle savedInstanceState) {
		//isStatuBarTran = false;
		super.onCreate(savedInstanceState);
		initView();
		setSoftInputMode();
	}

	private void initTopTitle() {
		headerLayout.showTitle(R.string.new_conver);
		headerLayout.showLeftBackButton();
	}

	private void initView() {
		initTopTitle();
		textCustomerNames = (TextView) findViewById(R.id.text_customer_names);
		contentEdit = (EditText) findViewById(R.id.textEdit);
		findViewById(R.id.sendBtn).setOnClickListener(this);
		//findViewById(R.id.btn_send_img).setVisibility(View.GONE);
		findViewById(R.id.btn_add_customer).setOnClickListener(this);
		findViewById(R.id.rootview).setOnTouchListener(this);
		setCustomerNames(null);
	}

	private void setCustomerNames(List<Custom> customers) {
		StringBuffer sb = new StringBuffer("客户：");
		if (customers != null && customers.size() > 0) {
			for (int i = 0; i < customers.size(); i++) {
				Custom custom = customers.get(i);
				if (custom.getUserName() != null && custom.getUserName().length() > 0
						&& !custom.getUserName().equals("未认证")) {
					sb.append(custom.getUserName());
				} else {
					sb.append(custom.getMobile());
				}
				if (i < customers.size() - 1) {
					sb.append("、");
				}
			}
		}
		SpannableStringBuilder style = new SpannableStringBuilder(sb);
		style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_gray_common)),
				0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textCustomerNames.setText(style);
	}

	@SuppressWarnings({"unchecked","deprecation"})
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == C.REQUEST_CONVER_NEW_ADD_CUSTOMER && resultCode == RESULT_OK) {
			checkedUsers = (List<Custom>) data.getBundleExtra("data")
					.getSerializable("checkedUsers");
			setCustomerNames(checkedUsers);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Utils.hideSoftInputView(this);
			return true;
		}
		return false;

	}

	@Override
	protected int getContentLayout() {
		return R.layout.conver_new_layout;
	}

	private void sendMsgs() {
		final String contString = contentEdit.getText().toString();
		if (TextUtils.isEmpty(contString)) {
			return;
		}
		if (checkedUsers == null || checkedUsers.size() == 0) {
			com.toobei.common.utils.ToastUtil
					.showCustomToast(getString(R.string.prompt_select_custom));
			return;
		}

		new MyNetAsyncTask(ctx, true) {

			@Override
			protected void doInBack() throws Exception {
				for (int i = 0; i < checkedUsers.size(); i++) {
					ChatService.getInstance().sendTextAddConver(contString,
							checkedUsers.get(i).getEasemobAcct());
				}
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null) {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.prompt_sended));
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(e.getMessage());
				}
				finish();
			}
		}.execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_customer:
			Intent intent = new Intent(this, ConverNewAddCustom.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("checkedUsers", (Serializable) checkedUsers);
			intent.putExtra("data", bundle);
			startActivityForResult(intent, C.REQUEST_CONVER_NEW_ADD_CUSTOMER);
			break;
		case R.id.sendBtn://发送
			sendMsgs();
			break;

		default:
			break;
		}
	}
}
