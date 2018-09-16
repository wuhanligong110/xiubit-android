package com.toobei.tb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.entity.CheckMobileRegisterEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc 类说明：注册界面 填写手机号界面
 * 
 * @date 2015-12-25
 */
public class RegisterPhone extends MyBaseActivity implements OnClickListener {

	public static RegisterPhone activity;
	private ClearEditText cedtPhone;
	private String strcedtPhone;
	private Button btnNext;
	private MySimpleTextWatcher textWatcher;
	private TextView loginTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		setContentView(R.layout.activity_register_phone);
		initView();
	}

	public void initView() {

		loginTv = (TextView) findViewById(R.id.loginTv);
		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
		loginTv.setOnClickListener(this);
		btnNext.setEnabled(false);
		cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
		textWatcher = new MySimpleTextWatcher();
		cedtPhone.addTextChangedListener(textWatcher);
		initTopView();
	}

	private void initTopView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(R.string.register);
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void sendVcode(final String phone) {
        new MyNetAsyncTask(ctx, false) {
            LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService()
						.userSendVcode(null, phone, "1");
			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						Intent intent = new Intent(RegisterPhone.this,
								RegisterPwdNew.class);
						intent.putExtra("phone", phone);
						showActivity(RegisterPhone.this, intent);
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

	private void userCheckMobile(final String phone) {
        new MyNetAsyncTask(ctx, false) {
            CheckMobileRegisterEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService()
						.userCheckMobile(phone, "");
			}

			@Override
			protected void onPost(Exception e) {
				// 登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						// 有限制注册，则对话框提示
						if (response.getData().getRegFlag().equals("0")) {
							sendVcode(strcedtPhone);
                        } else if (response.getData().getRegFlag().equals("2")) {
                            // 已注册用户，直接登录
							com.toobei.common.utils.ToastUtil.showCustomToast("亲，此账号已经注册，请直接去登录吧~");
                            showActivity((Activity) ctx, LoginActivity.class);
                        }
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
                btnNext.setEnabled(false);
            }
        }.execute();
	}

	private class MySimpleTextWatcher extends SimpleTextWatcher {
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2,
				int i3) {
			if (cedtPhone.getText().toString().length() == 11) {
                btnNext.setBackgroundResource(R.drawable.btn_big_blue_selector);
                btnNext.setEnabled(true);
			} else {
                btnNext.setBackgroundResource(R.drawable.btn_big_blue_selector);
                btnNext.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			btnNext.setEnabled(false);
			strcedtPhone = cedtPhone.getText().toString();
			if (!StringUtil.checkPhoneNum(strcedtPhone)) {
				com.toobei.common.utils.ToastUtil.showCustomToast("亲，请确定你输入的是手机号码");
				return;
			}
			userCheckMobile(strcedtPhone);
			/*
			 * //测试用 Intent intent = new Intent(ResetLoginPwdPhone.this,
			 * ResetLoginPwdNew.class); intent.putExtra("phone", strcedtPhone);
			 * showActivity(ResetLoginPwdPhone.this,intent);
			 */
			break;
		case R.id.loginTv:
            showActivity(ctx, LoginActivity.class);
            break;
		default:
			break;
		}
	}
}
