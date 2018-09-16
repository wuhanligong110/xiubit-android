package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.view.View;

import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.HeaderLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-密码管理界面
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

	@Override
	protected void onResume() {

		super.onResume();
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(MyApp.getInstance().getString(R.string.password_manage));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
	}

	private void findView() {
		findViewById(R.id.alter_login_pwd_rl).setOnClickListener(this);
		findViewById(R.id.alter_gesture_pwd_rl).setOnClickListener(this);
		findViewById(R.id.alter_pay_pwd_rl).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alter_login_pwd_rl: //修改登录密码
			showActivity(this, PwdManagerLogin.class);
			break;
		case R.id.alter_gesture_pwd_rl: //修改手势密码
			showActivity(this, PwdManagerGestureVerify.class);
			break;
		case R.id.alter_pay_pwd_rl: // 修改支付密码
			MyApp.getInstance().getAccountService()
					.checkBoundedCard(ctx, new UpdateViewCallBack<String>() {

						@Override
						public void updateView(Exception e, String bindFlag) {
							if (bindFlag != null && bindFlag.equals("true")) {

								MyApp.getInstance().getAccountService()
										.checkInitPayPwd(ctx, new UpdateViewCallBack<String>() {

											@Override
											public void updateView(Exception e, String isHasPayPwd) {
												if (isHasPayPwd == null) {
													//异常情况，无反应
												} else if (isHasPayPwd.equals("true")) {
													PwdManager.this.showActivity(PwdManager.this,
															PwdManagerPay.class);
												} else if (isHasPayPwd.equals("false")) {
													PwdManager.this.showActivity(PwdManager.this,
															ResetPayPwdIdentity.class);
												}
											}
										});

							} else {
								ToastUtil
										.showCustomToast("亲，还没有绑定银行卡，先绑定银行卡吧~");
								PwdManager.this.showActivity(PwdManager.this, CardManagerAdd.class);
							}
						}
					});

			break;
		default:
			break;
		}

	}

}
