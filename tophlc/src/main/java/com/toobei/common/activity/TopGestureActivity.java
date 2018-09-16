package com.toobei.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.locus.LocusPassWordViewWhite;
import com.toobei.common.view.locus.LocusPassWordViewWhite.OnCompleteListener;

import org.xsl781.ui.ActivityStack;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 公司: tophlc
 * 类说明：手势登录界面
 * @date 2015-9-28
 */
public abstract class TopGestureActivity extends TopBaseActivity implements OnClickListener {
	protected LocusPassWordViewWhite lpwv;
	// private SharedPreferences sp;
	// private SharedPreferences sp1;
	//	public static boolean isopen = false;
	//	private SharedPreferences sps;
	private int pwdcount = 5;
	protected TextView tv_phone, toastTv;
	private boolean isReVerify = false;//是否为重新检验，为重新检验的话，不跳转到首页，//暂时不启用，直接跳转到首页
	private FrameLayout gestureFl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gesture_login);
		initView();
		setTranslucentStatus(true);

		if (!TopApp.getInstance().getLoginService().isCachPhoneExist()) {
			finish();
			return;
		}
		isReVerify = getIntent().getBooleanExtra("reVerify", false);
		if (TopApp.getInstance().getLoginService().curPhone != null
				&& TopApp.getInstance().getLoginService().curPhone.length() == 11) {

			tv_phone.setText(com.toobei.common.utils.StringUtil.formatPhone(TopApp.getInstance()
					.getLoginService().curPhone));
			tv_phone.setVisibility(View.VISIBLE);
			if (!TopApp.getInstance().getLoginService().isCurUserGesturePasswdExist()) {
				//手势不存在，进入设置手势环节
				skipTopGestureSetActivity();
				//	skipActivity(this, TopGestureSetActivity.class);
			}
		} else {
			//用户账号为空，清空缓存信息，重新登录
			TopApp.getInstance().logOut();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		TopApp.systime = System.currentTimeMillis();
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected abstract void skipLoginActivity();

	protected abstract void skipTopGestureSetActivity();

	protected abstract void skipMainActivity();

	protected abstract void showPwdManagerGestureVerify();

	private void initView() {
		gestureFl = (FrameLayout) findViewById(R.id.gestureFl);
		tv_phone = (TextView) findViewById(R.id.login_phone);
		toastTv = (TextView) findViewById(R.id.login_toast);
		findViewById(R.id.login_forget).setOnClickListener(this);
		findViewById(R.id.login_other).setOnClickListener(this);
		lpwv = (LocusPassWordViewWhite) this.findViewById(R.id.mLocusPassWordView);

		lpwv.setImgResourse(setLocusPassWordViewImage()[0],setLocusPassWordViewImage()[1]);
		lpwv.setOnCompleteListener(new OnCompleteListener() {
			@Override
			public void onComplete(String password) {
				checkPasswd(password);
			}
		});
		gestureFl.setBackgroundResource(setBackgroundColor());
	}

	protected abstract int[] setLocusPassWordViewImage();

	private void checkPasswd(String password) {
		// 如果密码正确,则进入主页面。
		if (TopApp.getInstance().getLoginService().isGesturePasswdValid(password)) {

			/*	if (MyApp.getInstance().getLoginService().curUser != null
						&& !MyApp.getInstance().getLoginService().curUser.isPayPwdExist()) {
					//如果不存在支付密码，要求设置
						Intent intent = new Intent(GesturePasswdActivity.this,
								SettingTransPasswordActivity.class);
						intent.putExtra("is_register_or_login", true);
						startActivity(intent);
						MobclickAgent.onEvent(GesturePasswdActivity.this, "paypwd_start_login");
						SharedPreferenceUtil.saveString(GesturePasswdActivity.this, "setPayPwdFrom",
								"login");
					finish();
					return;
				}*/
		//	System.out.println("==== ActivityStackCount" + ActivityStack.getInstance().getCount());
			if (isReVerify && ActivityStack.getInstance().getCount() > 1) {
				finish();
			} else {
				skipMainActivity();
				//	skipActivity(this, getMainActivity());
			}
		} else {
			pwdcount--;
			if (pwdcount > 0) {
				toastTv.setText("图案密码绘制错误，还可尝试" + pwdcount + "次");
				lpwv.markError();
				lpwv.clearPassword();
			} else {
				lpwv.markError();
				ToastUtil.showCustomToast("图案密码绘制错误次数过多，请重新登录");
				lpwv.clearPassword();
				// 五次错误后，注销，要求重新登录
				TopApp.getInstance().logOut();
			}

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private static boolean isExit = false;

	/**
	 * 双击退出函数
	 */
	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			ToastUtil.showCustomToast("再按一次退出程序");
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			TopApp.getInstance().exitApp();
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login_forget) {
			showPwdManagerGestureVerify();
			//	showActivity(ctx, getPwdManagerGestureVerify());
		} else if (v.getId() == R.id.login_other) {
			skipLoginActivity();
		}
	}

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return null;
	}

	protected abstract int setBackgroundColor();
}