package com.toobei.common.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：修改手势密码 验证登录密码
 * @date 2016-3-22
 */
public abstract class TopPwdManagerGestureVerify extends TopBaseActivity implements
		View.OnClickListener {
	private ClearEditText cedtPwd;
	private ImageView showPwd;
	private Button btnNext;
	private boolean isShow = false;
	private MySimpleTextWatcher textWatcher;
	public static TopPwdManagerGestureVerify activity;
	protected String whereFrom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_alter_gesture_pwd_loginpwd);
		whereFrom = getIntent().getStringExtra("whereFrom");
		findView();
		initTopTitle();
		showSoftInputView();
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(TopApp.getInstance().getString(R.string.alter_gesture_pwd));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void findView() {
		cedtPwd = (ClearEditText) findViewById(R.id.cedt_passwd);
		showPwd = (ImageView) findViewById(R.id.password_show);
		showPwd.setOnClickListener(this);
		textWatcher = new MySimpleTextWatcher();
		cedtPwd.addTextChangedListener(textWatcher);

		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		btnNext.setEnabled(false);
	}

	private void changedView() {
		if (isShow) {
			//显示密码
			cedtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			showPwd.setImageResource(R.drawable.img_login_pwd_eye_open);
		} else {
			//不显示密码
			cedtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
			showPwd.setImageResource(R.drawable.img_login_pwd_eye_close);
		}
		cedtPwd.setSelection(cedtPwd.getText().length());
		isShow = !isShow;
	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtPwd.getText().toString().length() >= 6) {
				btnNext.setEnabled(true);
				btnNext.setBackgroundResource(getBtnColor());
			} else {
				btnNext.setEnabled(false);
				btnNext.setBackgroundResource(R.drawable.btn_grey_big_default);
			}
		}
	}

	private void verifyLoginPwd(final String token, final String pwd) {
		new MyNetAsyncTask(ctx, true) {
			CheckResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService().userVerifyLoginPwd(token, pwd);

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						if (response.getData().getRlt().equals("true")) {
							onCheckLoginPwdValid();
						} else {
							com.toobei.common.utils.ToastUtil
									.showCustomToast(getString(R.string.passwd_error));
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
				btnNext.setEnabled(true);
			}
		}.execute();
	}

	/**
	 * 功能：登录密码有效，进行相应跳转
	 */
	public abstract void onCheckLoginPwdValid();
	
	public abstract int getBtnColor();
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_next) {
			btnNext.setEnabled(false);
			verifyLoginPwd(TopApp.getInstance().getLoginService().token, cedtPwd.getText()
					.toString());
		} else if (v.getId() == R.id.password_show) {
			changedView();
		}
	}
	
	
}
