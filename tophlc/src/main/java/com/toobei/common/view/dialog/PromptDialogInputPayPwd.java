//package com.toobei.common.view.dialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//
//import com.jungly.gridpasswordview.GridPasswordView;
//import com.toobei.common.R;
//import com.toobei.common.TopApp;
//import com.toobei.common.TopBaseActivity;
//import com.toobei.common.entity.CheckResponseEntity;
//import com.toobei.common.utils.MyNetAsyncTask;
//
///**
// * 公司: tophlc
// * 类说明：支付密码输入弹出框
// * @date 2015-10-27
// */
//public class PromptDialogInputPayPwd extends Dialog implements android.view.View.OnClickListener {
//	private TopBaseActivity activity;
//	private String moneyCount;
//	private GridPasswordView gridPwd;
//	private OnPayCompletedListener listener;
//
//	public interface OnPayCompletedListener {
//		void OnPayCompleted(boolean isPassed);
//
//		void forgetPayPwd();
//	}
//
//	public PromptDialogInputPayPwd(Context context, String moneyCount,
//			OnPayCompletedListener listener) {
//		super(context, R.style.customDialog);
//		this.activity = (TopBaseActivity) context;
//		this.moneyCount = moneyCount;
//		this.listener = listener;
//	}
//
//	public void setListener(OnPayCompletedListener listener) {
//		this.listener = listener;
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		View view = LayoutInflater.from(activity).inflate(
//				R.layout.layout_prompt_dialog_input_pay_pwd, null);
//		this.setContentView(view);
//		findViewById(R.id.img_cancel).setOnClickListener(this);
//		findViewById(R.id.text_forgetPayPwdTv).setOnClickListener(this);
//		TextView textMoneyCount = (TextView) findViewById(R.id.text_money_count);
//		textMoneyCount.setText("¥" + moneyCount);
//		initPwdView();
//	}
//
//	private void initPwdView() {
//		gridPwd = (GridPasswordView) findViewById(R.id.gridpwd_pay);
//		gridPwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
//			@Override
//			public void onChanged(String psw) {
//				if (psw.length() == 6) {
//					userVerifyPayPwd(psw);
//				}
//			}
//
//			@Override
//			public void onMaxLength(String psw) {
//			}
//		});
//		//	gridPwd.performClick();
//		activity.showSoftInputView();
//	}
//
//	private void userVerifyPayPwd(final String pwd) {
//		new MyNetAsyncTask(activity, true) {
//			CheckResponseEntity response;
//
//			@Override
//			protected void doInBack() throws Exception {
//				response = TopApp.getInstance().getHttpService()
//						.userVerifyPayPwd(TopApp.getInstance().getLoginService().token, pwd);
//
//			}
//
//			@Override
//			protected void onPost(Exception e) {
//				if (e == null && response != null) {
//					if (response.getCode().equals("0")) {
//						if (response.getData().getRlt().equals("true")) {
//							if (listener != null) {
//								listener.OnPayCompleted(true);
//							}
//						} else {
//							com.toobei.common.utils.ToastUtil.showCustomToast(ctx
//									.getString(R.string.passwd_error));
//						}
//					} else {
//						com.toobei.common.utils.ToastUtil.showCustomToast(response
//								.getErrorsMsgStr());
//					}
//				} else {
//					com.toobei.common.utils.ToastUtil.showCustomToast(ctx
//							.getString(R.string.pleaseCheckNetwork));
//				}
//				gridPwd.clearPassword();
//				//btnNext.setEnabled(true);
//			}
//		}.execute();
//	}
//
//	@Override
//	public void onClick(View v) {
//		if (v.getId() == R.id.img_cancel) {
//			if (listener != null) {
//				listener.OnPayCompleted(false);
//			}
//			dismiss();
//		} else if (v.getId() == R.id.text_forgetPayPwdTv) {
//			listener.forgetPayPwd();
//		}
//	}
//}