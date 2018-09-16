package com.toobei.common.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.StringUtils;

public abstract class TopResetLoginPwdNew extends TopBaseActivity implements OnClickListener {

	public static TopResetLoginPwdNew activity;
	private ClearEditText cedtVcode, cedtPasswd, cedtPasswd2;
	private String strcedtVcode, phone;
	private String strPasswd;
	private String strPasswd2;
	private boolean isShowPassword = true;
	private boolean isShowPassword2 = true;
	private ImageView showPassword, showPassword2;
	private Button btnComplete, btnResend;
	private MySimpleTextWatcher textWatcher;
	private TextView textTitlePrompt;

	protected static final int GET_RESET_SMS_CODE = 0x001;

	private int seconds = 60;
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_RESET_SMS_CODE:
				seconds--;
				if (seconds <= 0) { // 恢复验证码输入
					seconds = 60;
					btnResend.setTextColor(ContextCompat.getColor(ctx,setTextColor()));
					btnResend.setEnabled(true);
					btnResend.setTextColor(ContextCompat.getColor(ctx,setTextColor()));
				} else {
					btnResend.setEnabled(false);
					mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
					btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_commonDC));
				}
				btnResend.setText("重新获取(" + seconds + "s)");
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_reset_login_pwd_new);
		mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
		phone = getIntent().getStringExtra("phone");
		initView();
		initData();
	}

	public void initView() {
		btnComplete = (Button) findViewById(R.id.btn_next);
		btnComplete.setOnClickListener(this);
		btnComplete.setEnabled(false);
		btnResend = (Button) findViewById(R.id.btn_resend_sms_vcode);
		btnResend.setOnClickListener(this);
		btnResend.setEnabled(false);
		btnResend.setText("重新获取(60s)");
		btnResend.setTextColor(ContextCompat.getColor(ctx,setTextColor()));

		cedtVcode = (ClearEditText) findViewById(R.id.cedt_vcode);
		cedtPasswd = (ClearEditText) findViewById(R.id.cedt_passwd);
		showPassword = (ImageView) findViewById(R.id.password_show);
		cedtPasswd2 = (ClearEditText) findViewById(R.id.cedt_passwd2);
		showPassword2 = (ImageView) findViewById(R.id.password_show2);
		textWatcher = new MySimpleTextWatcher();
		cedtVcode.addTextChangedListener(textWatcher);
		cedtPasswd.addTextChangedListener(textWatcher);
		cedtPasswd2.addTextChangedListener(textWatcher);
		showPassword.setOnClickListener(this);
		showPassword2.setOnClickListener(this);

		textTitlePrompt = (TextView) findViewById(R.id.reset_login_pwd_title);
		textTitlePrompt.setText(getString(R.string.sended_sms_vcode)
				+ com.toobei.common.utils.StringUtil.formatPhone(phone));
		initTopView();
	}

	private void initTopView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(R.string.set_new_login_pwd);
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	public void initData() {

	}

	private void resetLoginPwd(final String phone, final String vcode, final String newPwd) {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.userResetLoginPwd(phone, vcode, newPwd);

			}

			@Override
			protected void onPost(Exception e) {
				//成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						//LoginResponseData data = response.getData();
						ToastUtil.showCustomToast("密码找回成功!");
						if (TopApp.getInstance().getLoginService().isServerLogin) {
							TopApp.getInstance().logOut(false);
						} else {
							skipToLoginActivity();
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				}
				btnComplete.setEnabled(true);
			}
		}.execute();
	}

	protected abstract void skipToLoginActivity();

	private void sendVcode(final String phone) {
		new MyNetAsyncTask(ctx, false) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService().userSendVcode(null, phone, "2");
			}

			@Override
			protected void onPost(Exception e) {
				//登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						//成功
						mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
						btnResend.setEnabled(true);
						btnResend.setTextColor(ContextCompat.getColor(ctx,setTextColor()));
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
					btnResend.setEnabled(true);
					btnResend.setTextColor(ContextCompat.getColor(ctx,setTextColor()));
				}
			}
		}.execute();
	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtPasswd.getText().toString().length() >= 6
					&& cedtPasswd2.getText().toString().length() >= 6
					&& cedtVcode.getText().toString().length() >= 4) {
				btnComplete.setBackgroundResource(setBtnColor());
				btnComplete.setEnabled(true);
			} else {
				btnComplete.setBackgroundResource(R.drawable.btn_grey_big_default);
				btnComplete.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.password_show) {
			if (isShowPassword) {
				//显示密码
				cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				showPassword.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				//不显示密码
				cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				showPassword.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtPasswd.setSelection(cedtPasswd.getText().length());
			isShowPassword = !isShowPassword;
		} else if (v.getId() == R.id.password_show2) {
			if (isShowPassword2) {
				//显示密码
				cedtPasswd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				showPassword2.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				//不显示密码
				cedtPasswd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
				showPassword2.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtPasswd2.setSelection(cedtPasswd2.getText().length());
			isShowPassword2 = !isShowPassword2;
		} else if (v.getId() == R.id.btn_next) {
			btnComplete.setEnabled(false);
			strcedtVcode = cedtVcode.getText().toString();
			if (strcedtVcode.length() < 4) {
				com.toobei.common.utils.ToastUtil.showCustomToast("验证码错误，请重新输入！");
				btnComplete.setEnabled(true);
				return;
			}
			// 检查密码
			strPasswd = cedtPasswd.getText().toString();
			if (!StringUtils.isTopicPassword(strPasswd)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("密码不符合规则，请重新输入！");
				cedtPasswd.setText("");
				cedtPasswd2.setText("");
				btnComplete.setEnabled(true);
				return;
			}

			strPasswd2 = cedtPasswd2.getText().toString();
			if (!StringUtils.isTopicPassword(strPasswd2)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("确认密码不符合规则，请重新输入！");
				cedtPasswd2.setText("");
				btnComplete.setEnabled(true);
				return;
			}

			if (!strPasswd.equals(strPasswd2)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("两次密码不一致，请重新输入！");
				btnComplete.setEnabled(true);
				return;
			}

			resetLoginPwd(phone, strcedtVcode, strPasswd);
		} else if (v.getId() == R.id.btn_resend_sms_vcode) {
			btnResend.setEnabled(false);
			btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_commonDC));
			cedtVcode.setText("");
			sendVcode(phone);
		}
	}

	protected abstract int setTextColor();

	protected abstract int setBtnColor();
}
