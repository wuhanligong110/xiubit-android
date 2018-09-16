package com.toobei.common.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：反馈界面  基类
 * @date 2016-4-6
 */
public abstract class TopFeedback extends TopBaseActivity {

	private EditText editContent;
	private TextView textCount, completeBtn,errorTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		initView();
	}

	private void initView() {
		initTopTitle();
		editContent = (EditText) findViewById(R.id.feedback_edit);
		textCount = (TextView) findViewById(R.id.text_feedback_remain_count);
		completeBtn = findViewById(R.id.completeBtn);
		errorTv = findViewById(R.id.errorTv);
		editContent.addTextChangedListener(new SimpleTextWatcher() {

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				int count = editContent.getText().toString().length();
				textCount.setText("" + count + "/200");
				completeBtn.setEnabled((count >= 10 && count <= 200));
				if (count >0 && count< 10) {
					errorTv.setText("输入字符过少");
				}else if (count > 200) {
					errorTv.setText("请输入小于200字的描述");
				}else {
					errorTv.setText("");
				}
			}
		});

		completeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (editContent.getText().toString().length() == 0) {
					errorTv.setText("请输入意见或建议");
					return;
				}else if (editContent.getText().toString().length() < 10){
					errorTv.setText("输入字符过少");
				}else {
					commit();
				}


			}
		});
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle("问题反馈");
		headerLayout.showLeftBackButton();

		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void commit() {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp
						.getInstance()
						.getHttpService()
						.personcenterFeedback(TopApp.getInstance().getLoginService().token,
								editContent.getText().toString());

			}

			@Override
			protected void onPost(Exception e) {
				completeBtn.setEnabled(true);
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						com.toobei.common.utils.ToastUtil.showCustomToast("已提交");
						completeBtn.postDelayed(new Runnable() {
							@Override
							public void run() {
								finish();
							}
						},1000);

					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}


}
