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
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.StringUtils;

/**
 * 公司: tophlc
 * 类说明：修改登录密码界面 基类
 * 
 * @date 2016-3-23
 */
public abstract class TopPwdManagerLogin extends TopBaseActivity implements View.OnClickListener {
	private ClearEditText cedtOld, cedtNew, cedtNewAgain;
	private ImageView showOld, showNew,showNewAgain;
	private Button btnComplete;
	private boolean isOldShow = false, isNewShow = false,isNewShowAgain = false;
	private MySimpleTextWatcher textWatcher;
	public static TopPwdManagerLogin activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_alter_login_pwd);
		findView();
		initTopTitle();
		//	cedtOld.setFocusable(true);
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(TopApp.getInstance().getString(R.string.alter_login_pwd));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void findView() {
		findViewById(R.id.forget_passwd_bt).setOnClickListener(this);
		cedtOld = (ClearEditText) findViewById(R.id.cedt_old_passwd);
		cedtNew = (ClearEditText) findViewById(R.id.cedt_new_passwd);
		cedtNewAgain = (ClearEditText) findViewById(R.id.cedt_new_passwd_again);
		showOld = (ImageView) findViewById(R.id.old_password_show);
		showNew = (ImageView) findViewById(R.id.new_password_show);
		showNewAgain = (ImageView) findViewById(R.id.new_password_again_show);
		showOld.setOnClickListener(this);
		showNew.setOnClickListener(this);
		showNewAgain.setOnClickListener(this);
		textWatcher = new MySimpleTextWatcher();
		cedtOld.addTextChangedListener(textWatcher);
		cedtNew.addTextChangedListener(textWatcher);
		cedtNewAgain.addTextChangedListener(textWatcher);

		btnComplete = (Button) findViewById(R.id.btn_next);
		btnComplete.setOnClickListener(this);
		btnComplete.setEnabled(false);
	}

	private void changedView(int flag) {
		if (flag==1) {
			if (isOldShow) {
				//显示密码
				cedtOld.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				showOld.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				//不显示密码
				cedtOld.setTransformationMethod(PasswordTransformationMethod.getInstance());
				showOld.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtOld.setSelection(cedtOld.getText().length());
			isOldShow = !isOldShow;
		} else if(flag==2) {
			if (isNewShow) {
				//显示密码
				cedtNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				showNew.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				//不显示密码
				cedtNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
				showNew.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtNew.setSelection(cedtNew.getText().length());
			isNewShow = !isNewShow;
		}else if(flag==3){
			if (isNewShowAgain) {
				//显示密码
				cedtNewAgain.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				showNewAgain.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				//不显示密码
				cedtNewAgain.setTransformationMethod(PasswordTransformationMethod.getInstance());
				showNewAgain.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtNewAgain.setSelection(cedtNewAgain.getText().length());
			isNewShowAgain = !isNewShowAgain;
		}

	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtOld.getText().toString().length() >= 6
					&& cedtNew.getText().toString().length() >= 6
					&& cedtNewAgain.getText().toString().length() >= 6) {
				btnComplete.setEnabled(true);
				btnComplete.setBackgroundResource(getBtnColor());
			} else {
				btnComplete.setEnabled(false);
				btnComplete.setBackgroundResource(R.drawable.btn_grey_big_default);
			}
		}
	}

	private void modifyLoginPwd(final String token, final String oldPwd, final String newPwd) {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.userModifyLoginPwd(token, oldPwd, newPwd);

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						com.toobei.common.utils.ToastUtil.showCustomToast("修改成功,请重新登录");
						TopApp.getInstance().logOut();
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_next) {
			btnComplete.setEnabled(false);
			String strPasswd = cedtNew.getText().toString();
			String strPasswd2 = cedtNewAgain.getText().toString();
			if (!StringUtils.isTopicPassword(strPasswd)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("新密码不符合规则，请重新输入！");
				cedtNew.setText("");
				btnComplete.setEnabled(true);
				return;
			}
			if (!StringUtils.isTopicPassword(strPasswd2)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("确认密码不符合规则，请重新输入！");
				cedtNewAgain.setText("");
				btnComplete.setEnabled(true);
				return;
			}
			if (!strPasswd.equals(strPasswd2)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("亲，两次密码不一致哦！");
				btnComplete.setEnabled(true);
				return;
			}
			modifyLoginPwd(TopApp.getInstance().getLoginService().token, cedtOld.getText()
					.toString(), cedtNew.getText().toString());
		} else if (v.getId() == R.id.old_password_show) {
			changedView(1);
		} else if (v.getId() == R.id.new_password_show) {
			changedView(2);
		}else if(v.getId() == R.id.new_password_again_show){
			changedView(3);
		}else if (v.getId() == R.id.forget_passwd_bt) {
			onBtnForgeLoginPasswdClick();
			//	showActivity(ctx, ResetLoginPwdPhone.class);
		}
	}

	/**
	 * 功能：点忘记密码后，跳到重置登录密码界面
	 */
	protected abstract void onBtnForgeLoginPasswdClick();
	protected abstract int getBtnColor();
}
