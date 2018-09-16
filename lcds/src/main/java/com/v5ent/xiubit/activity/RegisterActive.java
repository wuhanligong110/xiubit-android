package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.event.FinishActivityEvent;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.HeaderLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.SystemTool;

/**
 * 公司: tophlc
 * 类说明： Activity-注册 激活账号界面
 * @date 2015-12-29
 */
public class RegisterActive extends MyBaseActivity implements OnClickListener {

	public static RegisterActive activity;
	private ClearEditText cedtPasswd, cedtPhone;
	private String phone;
	private String strPasswd;
	private boolean isShowPassword = true;
	//private ImageView				showPassword;
	private Button btnComplete;
	private MySimpleTextWatcher textWatcher;
	private ImageView checkIV;
	private boolean isChecked = true;
	private String regSource = "第三方平台";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_register_active);
		phone = getIntent().getStringExtra("phone");
		regSource = getIntent().getStringExtra("regSource");
		initView();
		initData();
	}

	public void initView() {
		btnComplete = (Button) findViewById(R.id.btn_complete);
		btnComplete.setOnClickListener(this);
		btnComplete.setEnabled(false);
		cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
		if (phone != null) {
			cedtPhone.setText(phone);
		}
		cedtPasswd = (ClearEditText) findViewById(R.id.cedt_passwd);
		//showPassword = (ImageView) findViewById(R.id.password_show);
		textWatcher = new MySimpleTextWatcher();
		cedtPhone.addTextChangedListener(textWatcher);
		cedtPasswd.addTextChangedListener(textWatcher);
		//		showPassword.setOnClickListener(this);

		checkIV = (ImageView) findViewById(R.id.img_check);
		checkIV.setOnClickListener(this);
		findViewById(R.id.text_pay_agreement).setOnClickListener(this);
		TextView resetLoginPwdTitle = (TextView) findViewById(R.id.reset_login_pwd_title);
		resetLoginPwdTitle.setText("您是" + regSource + "用户，可直接输入密码登录（激活账户）");
		initTopView();
	}

	private void initTopView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(R.string.register);
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	public void initData() {

	}

	private void userRegister(final String phone, final String password) {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp
						.getInstance()
						.getHttpService()
						.userRegister(phone, password, null,
								PushManager.getInstance().getClientid(activity),"", SystemTool.getDeviceid(activity), SystemTool.getDeviceModel(), PixelUtil.getScreenH(activity)+"*"+PixelUtil.getScreenW(activity),SystemTool.getSystemVersion());

			}

			@Override
			protected void onPost(Exception e) {
				// 成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						LoginResponseData data = response.getData();

						// 保存token,设置缓存登录信息，更新用户数据
						MyApp.getInstance().getLoginService().curPhone = phone;
						MyApp.getInstance().getLoginService().isServerLogin = true;
						MyApp.getInstance().getLoginService().token = data.getToken();
						MyApp.getInstance().getCurUserSp().setToken(data.getToken());
						MyApp.getInstance().getDefaultSp().setCurUserPhone(phone);
//						if (RegisterPhone.activity != null) {
//							RegisterPhone.activity.finish();
//						}
//						if (LoginActivity.activity != null) {
//							LoginActivity.activity.finish();
//						}
						EventBus.getDefault().post(new FinishActivityEvent(RegisterPhone.class));
						EventBus.getDefault().post(new FinishActivityEvent(LoginActivity.class));
						skipActivity(RegisterActive.this, MainActivity.class);
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				}
				btnComplete.setEnabled(true);
			}
		}.execute();
	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtPasswd.getText().toString().length() >= 6
					&& cedtPhone.getText().toString().length() >= 11 && isChecked) {
				// btnComplete.setBackgroundResource(R.drawable.btn_complete);
				btnComplete.setEnabled(true);
			} else {
				// btnComplete.setBackgroundResource(R.drawable.btn_grey_big_default);
				btnComplete.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.img_check:

			isChecked = !isChecked;

			checkIV.setImageResource(isChecked ? R.drawable.img_checkbox_checked
					: R.drawable.img_checkbox_unchecked);

			if (isChecked && cedtPasswd.getText().toString().length() >= 6
					&& cedtPhone.getText().toString().length() >= 11) {
				btnComplete.setEnabled(true);
			} else {
				btnComplete.setEnabled(false);
			}

			// 不显示密码

			// case R.id.password_show:
			// if (isShowPassword) {
			// //显示密码
			// cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			// showPassword.setImageResource(R.drawable.img_login_pwd_eye_open);
			// } else {
			// //不显示密码
			// cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
			// showPassword.setImageResource(R.drawable.img_login_pwd_eye_close);
			// }
			// cedtPasswd.setSelection(cedtPasswd.getText().length());
			// isShowPassword = !isShowPassword;
			// break;
			break;

		case R.id.password_show:
			if (isShowPassword) {
				// 显示密码
				cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				//	showPassword.setImageResource(R.drawable.img_login_pwd_eye_open);
			} else {
				// 不显示密码
				cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				//showPassword.setImageResource(R.drawable.img_login_pwd_eye_close);
			}
			cedtPasswd.setSelection(cedtPasswd.getText().length());
			isShowPassword = !isShowPassword;
			break;

		case R.id.btn_complete:
			MobclickAgent.onEvent(ctx,"Q_2_3");
			btnComplete.setEnabled(false);
			phone = cedtPhone.getText().toString();
			// 检查密码
			strPasswd = cedtPasswd.getText().toString();
			if (!StringUtil.checkPasswd(strPasswd)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("密码不正确，请重新输入！");
				cedtPasswd.setText("");
				btnComplete.setEnabled(true);
				return;
			}
			userRegister(phone, strPasswd);
			break;

		case R.id.text_pay_agreement:// 点击协议
			// 跳转到 《貅比特用户服务协议》
			WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp()
					.getUserService(), "貅比特用户服务协议");
			break;
		default:
			break;
		}
	}

	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	// this.isChecked = isChecked;
	// if (isChecked && cedtPasswd.getText().toString().length() >= 6 && cedtPhone.getText().toString().length() >= 11)
	// {
	// btnComplete.setEnabled(true);
	// } else {
	// btnComplete.setEnabled(false);
	// }
	// }
}
