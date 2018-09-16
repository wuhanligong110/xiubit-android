package com.toobei.tb.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;

public class MsgManagerActivity extends MyBaseActivity implements OnClickListener {

	private ImageView infoIv;
	private ImageView pushIv;

	//	private boolean infoFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_msg_manager);
		initView();
	}

	private void initView() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle("消息免打扰");
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();

		infoIv = (ImageView) findViewById(R.id.infoIv);
		infoIv.setOnClickListener(this);
		pushIv = (ImageView) findViewById(R.id.pushIv);
		pushIv.setOnClickListener(this);
		//		infoFlag = MyApp.getInstance().getCurUserSp().isNotifyWhenNews();
		//		if (infoFlag) {
		//			infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
		//		} else {
		//			infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
		//		}
		String platformflag = MyApp.getInstance().getCurUserSp().getPlatformflag();
		String interactflag = MyApp.getInstance().getCurUserSp().getInteractflag();
		if (TextUtils.isEmpty(platformflag) || TextUtils.isEmpty(interactflag)) {
			queryMsgPushSet();
		} else {
			updateUI(platformflag);
		}
	}

	/*
	 * 查询消息设置
	 */
	private void queryMsgPushSet() {
		new MyNetAsyncTask(ctx, true) {
			MsgResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService().queryMsgPushSet(MyApp.getInstance().getLoginService().token);

			}

			@Override
			protected void onPost(Exception e) {
				// 登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {

						String platformflag = response.getData().getPlatformflag();

						updateUI(platformflag);
						MyApp.getInstance().getCurUserSp().setPlatformflag(platformflag);

					} else {
						ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	/*
	 * 更新UI
	 */
	private void updateUI(String platformflag) {
		if (!TextUtils.isEmpty(platformflag)) {
			if (platformflag.equals("0")) {
				pushIv.setBackgroundResource(R.drawable.btn_notification_unselected);
			} else {
				pushIv.setBackgroundResource(R.drawable.btn_notification_selected);
			}
		}

	}

	/*
	 * 设置消息
	 * 0关闭
	 * 1开启
	 */
	private void setMsgPush(final String token, final String platformflag,
			final String interactflag, final boolean typeFlag) {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = MyApp.getInstance().getHttpService()
						.setMsgPush(token, platformflag, interactflag);

			}

			@Override
			protected void onPost(Exception e) {
				// 登录成功
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						if (typeFlag) {
							if (platformflag.equals("0")) {
								pushIv.setBackgroundResource(R.drawable.btn_notification_unselected);
								MyApp.getInstance().getCurUserSp().setPlatformflag("0");
							} else {
								pushIv.setBackgroundResource(R.drawable.btn_notification_selected);
								MyApp.getInstance().getCurUserSp().setPlatformflag("1");
							}
						} else {
							if (interactflag.equals("0")) {
								infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
								MyApp.getInstance().getCurUserSp().setInteractflag("0");
								MyApp.getInstance().getCurUserSp().setNotifyWhenNews(true);
							} else {
								infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
								MyApp.getInstance().getCurUserSp().setInteractflag("1");
								MyApp.getInstance().getCurUserSp().setNotifyWhenNews(false);

							}
						}
					} else {
						ToastUtil.showCustomToast(response.getErrorsMsgStr());
					}
				} else {
					ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
			}
		}.execute();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.infoIv:
			//			infoFlag = MyApp.getInstance().getCurUserSp().isNotifyWhenNews();
			//			if (infoFlag) {
			//				infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
			//				MyApp.getInstance().getCurUserSp().setNotifyWhenNews(false);
			//			} else {
			//				infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
			//				MyApp.getInstance().getCurUserSp().setNotifyWhenNews(true);
			//			}
			//typeFlag为false
			String platformflag = MyApp.getInstance().getCurUserSp().getPlatformflag();
			String interactflag = MyApp.getInstance().getCurUserSp().getInteractflag();
			if (!TextUtils.isEmpty(platformflag) && !TextUtils.isEmpty(interactflag)) {
				if (interactflag.equals("0")) {
					setMsgPush(MyApp.getInstance().getLoginService().token, platformflag, "1", false);
				} else {
					setMsgPush(MyApp.getInstance().getLoginService().token, platformflag, "0", false);
				}
			}
			break;
		case R.id.pushIv:
			//typeFlag为true
			String platformflag1 = MyApp.getInstance().getCurUserSp().getPlatformflag();
			String interactflag1 = MyApp.getInstance().getCurUserSp().getInteractflag();
			if (!TextUtils.isEmpty(platformflag1) && !TextUtils.isEmpty(interactflag1)) {
				if (platformflag1.equals("0")) {
					setMsgPush(MyApp.getInstance().getLoginService().token, "1", interactflag1, true);
				} else {
					setMsgPush(MyApp.getInstance().getLoginService().token, "0", interactflag1, true);
				}
			}
			break;
		}
	}
}
