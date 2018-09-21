package com.toobei.common.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.ResetPayPwdTokenEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.SystemFunctionUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：重置交易密码 填写验证码
 * @date 2015-10-14
 */
public abstract class TopResetPayPwdVcode extends TopBaseActivity implements OnClickListener {
	private int sendNum = 0;
	private ClearEditText cedtVcode;
	private Button btnComplete, btnResend;
	private MySimpleTextWatcher textWatcher;
	private TextView textTitlePrompt;
	protected String forWhat = "";

	protected static final int GET_RESET_SMS_CODE = 0x001;

	private int seconds = 60;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_RESET_SMS_CODE:
				seconds--;
				if (seconds <= 0) { // 恢复验证码输入
					seconds = 60;
					btnResend.setEnabled(true);
					btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
				} else {
					btnResend.setEnabled(false);
					btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common_title));
					mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
				}
				btnResend.setText("重新发送(" + seconds + "s)");
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		forWhat = getIntent().getStringExtra("forWhat");
		setContentView(R.layout.activity_reset_pay_pwd_vcode);
		initView();
		sendVcode();
	}

	public void initView() {

		btnComplete = (Button) findViewById(R.id.btn_next);
		btnComplete.setOnClickListener(this);
		btnComplete.setEnabled(false);
		btnResend = (Button) findViewById(R.id.btn_resend_sms_vcode);
		TextView questionTv =  findViewById(R.id.questionTv);
		btnResend.setOnClickListener(this);
		btnResend.setEnabled(false);
		btnResend.setText("重新获取(60s)");
		btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
		cedtVcode = (ClearEditText) findViewById(R.id.cedt_vcode);
		textWatcher = new MySimpleTextWatcher();
		cedtVcode.addTextChangedListener(textWatcher);

		textTitlePrompt = (TextView) findViewById(R.id.reset_login_pwd_title);
		textTitlePrompt.setText(getString(R.string.sended_sms_vcode)
				+ com.toobei.common.utils.StringUtil.formatPhone(TopApp.getInstance()
						.getLoginService().curPhone));

		questionTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SystemFunctionUtil.INSTANCE.CallServicePhone(ctx,"0755-86725461");
			}
		});
		initTopView();
	}

	private void initTopView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle("验证手机号");
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void sendVcode() {
		if (sendNum > 10 ) {
			ToastUtil.showCustomToast("当天发送已超过10次，请明日再试");
			return;
		}
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.userSendVcode(TopApp.getInstance().getLoginService().token, null, "3");
			}

			@Override
			protected void onPost(Exception e) {
				//登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						mHandler.sendEmptyMessage(GET_RESET_SMS_CODE);
					} else {
						btnResend.setEnabled(true);
						btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
						btnResend.setText("发 送");
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

	private void resetPayPwd(final String vcode) {
		new MyNetAsyncTask(ctx, true) {
			ResetPayPwdTokenEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp
						.getInstance()
						.getHttpService()
						.userResetPayPwdVerifyVcode(TopApp.getInstance().getLoginService().token,
								vcode);
			}

			@Override
			protected void onPost(Exception e) {
				//登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						onResetPayPwdSuccess(response.getData().getResetPayPwdToken());
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
				btnComplete.setEnabled(true);
			}
		}.execute();
	}

	protected abstract void onResetPayPwdSuccess(String resetPayPwdToken);

	class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtVcode.getText().toString().length() >= 4) {
				btnComplete.setEnabled(true);
			} else {
				btnComplete.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_next) {
			btnComplete.setEnabled(false);
			resetPayPwd(cedtVcode.getText().toString());
		} else if (v.getId() == R.id.btn_resend_sms_vcode) {
			sendVcode();
		}
	}
}
