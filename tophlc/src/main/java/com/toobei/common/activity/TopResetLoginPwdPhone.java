package com.toobei.common.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：重设登录密码 填写手机号界面
 * @date 2015-11-11
 */
public abstract class TopResetLoginPwdPhone extends TopBaseActivity implements OnClickListener {

	public static TopResetLoginPwdPhone activity;
	private ClearEditText cedtPhone;
	private String strcedtPhone;
	private Button btnNext;
	private MySimpleTextWatcher textWatcher;
    protected View mTopLineView;
    protected TextView mTextRemind;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_reset_login_pwd_phone);
		initView();
	}

	public void initView() {

		String phoneStr = getIntent().getStringExtra("strcedtPhone");
		btnNext = (Button) findViewById(R.id.btn_next);
        mTopLineView = findViewById(R.id.top_line_view);
        mTextRemind = (TextView) findViewById(R.id.remind_text);
        btnNext.setOnClickListener(this);
		btnNext.setEnabled(false);
		cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
		//		if (TopApp.getInstance().getLoginService().curPhone != null) {
		//			cedtPhone.setText(TopApp.getInstance().getLoginService().curPhone);
		//			btnNext.setEnabled(true);
		//		}
		cedtPhone.setText(phoneStr);

		if (StringUtil.checkPhoneNum(phoneStr)) {
			btnNext.setEnabled(true);
			btnNext.setBackgroundResource(getBtnResId());
		} else {
			btnNext.setEnabled(false);
			btnNext.setBackgroundResource(R.drawable.btn_grey_big_default);
		}

		textWatcher = new MySimpleTextWatcher();
		cedtPhone.addTextChangedListener(textWatcher);
		initTopView();
	}

	private void initTopView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(R.string.reset_login_pwd);
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void sendVcode(final String phone) {
		new MyNetAsyncTask(ctx, true) {
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
						onSendVcodeSuccess(phone);
						//成功跳转
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

	protected abstract void onSendVcodeSuccess(String phone);

//	private void userCheckMobile(final String phone) {
//		new MyNetAsyncTask(ctx, true) {
//			CheckMobileRegisterEntity response;
//
//			@Override
//			protected void doInBack() throws Exception {
//				response = TopApp.getInstance().getHttpService().userCheckMobile(phone, "");
//			}
//
//			@Override
//			protected void onPost(Exception e) {
//				//登录成功
//				if (e == null && response != null) {
//					if (response.getCode().equals("0")) {
//						//成功跳转
//						if (response.getData().isRegisterCfg()) {
//							sendVcode(strcedtPhone);
//						} else {
//							com.toobei.common.utils.ToastUtil.showCustomToast("该号暂未注册");
//						}
//					} else {
//						com.toobei.common.utils.ToastUtil.showCustomToast(response
//								.getErrorsMsgStr());
//					}
//				} else {
//					com.toobei.common.utils.ToastUtil
//							.showCustomToast(getString(R.string.pleaseCheckNetwork));
//				}
//				btnNext.setEnabled(true);
//			}
//		}.execute();
//	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
			if (cedtPhone.getText().toString().length() == 11) {
				btnNext.setEnabled(true);
				btnNext.setBackgroundResource(getBtnResId());
			} else {
				btnNext.setEnabled(false);
				btnNext.setBackgroundResource(R.drawable.btn_grey_big_default);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_next) {
			btnNext.setEnabled(false);
			strcedtPhone = cedtPhone.getText().toString();
			if (!StringUtil.checkPhoneNum(strcedtPhone)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("手机号码错误，请重新输入！");
				return;
			}
			sendVcode(strcedtPhone);
			//userCheckMobile(strcedtPhone);
			/*//测试用
			Intent intent = new Intent(ResetLoginPwdPhone.this, ResetLoginPwdNew.class);
			intent.putExtra("phone", strcedtPhone);
			showActivity(ResetLoginPwdPhone.this,intent);*/
		}
	}

	protected abstract int getBtnResId();
}
