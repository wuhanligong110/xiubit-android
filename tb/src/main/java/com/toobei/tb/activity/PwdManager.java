package com.toobei.tb.activity;

import android.os.Bundle;
import android.view.View;

import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.umeng.analytics.MobclickAgent;

/**
 * 公司: tophlc
 * 类说明：密码管理界面
 * @date 2015-10-13
 */
public class PwdManager extends MyBaseActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pwd_manager);
		findView();
		initTopTitle();
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(MyApp.getInstance().getString(R.string.password_manage));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onEvent(PwdManager.this,"manager_password");
	}

	private void findView() {
		findViewById(R.id.alter_login_pwd_rl).setOnClickListener(this);
		findViewById(R.id.alter_gesture_pwd_rl).setOnClickListener(this);
		findViewById(R.id.alter_pay_pwd_rl).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_login_pwd_rl:
			showActivity(this, PwdManagerLogin.class);
			break;
		case R.id.alter_gesture_pwd_rl:
			showActivity(this, PwdManagerGestureVerify.class);
			break;
		case R.id.alter_pay_pwd_rl:
			MyApp.getInstance().getAccountService()
					.checkBoundedCardAndInitPayAndSkip(ctx, new UpdateViewCallBack<Boolean>() {

						@Override
						public void updateView(Exception e, Boolean object) {
							if (object != null && object) {
								PwdManager.this.showActivity(PwdManager.this, PwdManagerPay.class);
							}
						}
					});
			break;
		default:
			break;
		}

	}
}
