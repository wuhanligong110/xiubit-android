package com.toobei.common.activity;

import java.lang.reflect.Method;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.jungly.gridpasswordview.imebugfixer.ImeDelBugFixedEditText;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

/**
 * 公司: tophlc
 * 类说明：修改交易密码
 * @date 2015-10-14
 */
public abstract class TopPwdManagerPay extends TopBaseActivity implements View.OnClickListener {
	private GridPasswordView gridPwd;
	private String oldPwd, firstPwd;
	private TextView textTitle, textForget;
	private Type type = Type.VERITY;
	private ViewGroup vgWarmPrompt;
	private KeyboardView keyboard_view;
	private int num = 0;

	enum Type {
		/**
		 * 验证交易密码
		 */
		VERITY,
		/**
		 * 第一次输入 
		 */
		FIRST,
		/**
		 * 第二次输入
		 */
		SECOND
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alter_pay_pwd);
		findView();
		initTopTitle();
		showSoftInputView();
	}

	private void initTopTitle() {
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
		headerLayout.showTitle(TopApp.getInstance().getString(R.string.alter_pay_pwd));
		headerLayout.showLeftBackButton();
		setTranslucentStatus(true);
		headerLayout.setHeadViewCoverStateBar();
		changeView();
	}

	/**
	   * 禁止Edittext弹出软件盘，光标依然正常显示。
	   */
	public void disableShowSoftInput(TextView editText) {
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			editText.setInputType(InputType.TYPE_NULL);
		} else {
			Class<EditText> cls = EditText.class;
			Method method;
			try {
				method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				method.setAccessible(true);
				method.invoke(editText, false);
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
				method.setAccessible(true);
				method.invoke(editText, false);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void findView() {
		findViewById(R.id.forget_passwd_bt).setOnClickListener(this);
		vgWarmPrompt = (ViewGroup) findViewById(R.id.pay_pwd_warm_prompt);
		textTitle = (TextView) findViewById(R.id.alter_pay_pwd_title);
		textForget = (TextView) findViewById(R.id.forget_passwd_bt);
		gridPwd = (GridPasswordView) findViewById(R.id.gridpwdPay);
		ImeDelBugFixedEditText inputView = gridPwd.getInputView();
		disableShowSoftInput(inputView);

		//		gridPwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
		//			@Override
		//			public void onChanged(String psw) {
		//				if (psw.length() == 6 && type == Type.VERITY) {
		//					oldPwd = psw;
		//					userVerifyPayPwd(psw);
		//				} else if (psw.length() == 6 && type == Type.FIRST) {
		//					firstPwd = psw;
		//					com.tophlc.common.utils.ToastUtil
		//							.showCustomToast(getString(R.string.input_pay_pwd_init_prompt2));
		//					type = Type.SECOND;
		//					gridPwd.clearPassword();
		//					changeView();
		//				} else if (psw.length() == 6 && type == Type.SECOND) {
		//					if (psw.equals(firstPwd)) {
		//						modifyPayPwd(oldPwd, firstPwd);
		//					} else {
		//						type = Type.FIRST;
		//						com.tophlc.common.utils.ToastUtil
		//								.showCustomToast(getString(R.string.twice_pwd_different));
		//						gridPwd.clearPassword();
		//						changeView();
		//					}
		//				}
		//			}
		//
		//			@Override
		//			public void onMaxLength(String psw) {
		//			}
		//		});

		keyboard_view = (KeyboardView) findViewById(R.id.keyboard_view);
		Keyboard k2 = new Keyboard(ctx, R.xml.symbols);
		keyboard_view.setKeyboard(k2);
		keyboard_view.setEnabled(true);
		keyboard_view.setPreviewEnabled(false);
		keyboard_view.setOnKeyboardActionListener(new OnKeyboardActionListener() {

			@Override
			public void swipeUp() {

			}

			@Override
			public void swipeRight() {

			}

			@Override
			public void swipeLeft() {

			}

			@Override
			public void swipeDown() {

			}

			@Override
			public void onText(CharSequence arg0) {

			}

			@Override
			public void onRelease(int arg0) {

			}

			@Override
			public void onPress(int arg0) {

			}

			@Override
			public void onKey(int primaryCode, int[] arg1) {
				num = gridPwd.getPassWord().length();
				Editable editable = gridPwd.getInputView().getText();
				int start = gridPwd.getInputView().getSelectionStart();
				if (num == 0) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						if (editable != null && editable.length() > 0) {
							if (editable.length() > 0) {
								editable.delete(start - 1, start);
							}
						}
					} else if (primaryCode == -2) {
						if (editable != null && editable.length() > 0) {
							if (start > 0) {
								editable.append(".");
							}
						}
					} else {
						editable.insert(start, Character.toString((char) primaryCode));
					}
				} else if (num == 1) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						if (editable != null && editable.length() > 0) {
							if (editable.length() > 0) {
								editable.delete(start - 1, start);
							}
						}
						gridPwd.getPasswordArr()[0] = null;
					} else if (primaryCode == -2) {
					} else {
						gridPwd.setText(1, Character.toString((char) primaryCode));
					}
				} else if (num == 2) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridPwd.setText(1, null);
					} else if (primaryCode == -2) {
					} else {
						gridPwd.setText(2, Character.toString((char) primaryCode));
					}
				} else if (num == 3) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridPwd.setText(2, null);
					} else if (primaryCode == -2) {
					} else {
						gridPwd.setText(3, Character.toString((char) primaryCode));
					}
				} else if (num == 4) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridPwd.setText(3, null);
					} else if (primaryCode == -2) {
					} else {
						gridPwd.setText(4, Character.toString((char) primaryCode));
					}
				} else if (num == 5) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridPwd.setText(4, null);
					} else if (primaryCode == -2) {
					} else {
						gridPwd.setText(5, Character.toString((char) primaryCode));

						if (type == Type.VERITY) {
							oldPwd = gridPwd.getPassWord();
							userVerifyPayPwd(gridPwd.getPassWord());
						} else if (type == Type.FIRST) {
							firstPwd = gridPwd.getPassWord();
							com.toobei.common.utils.ToastUtil
									.showCustomToast(getString(R.string.input_pay_pwd_init_prompt2));
							type = Type.SECOND;
							gridPwd.clearPassword();
							changeView();
						} else if (type == Type.SECOND) {
							if (gridPwd.getPassWord().equals(firstPwd)) {
								modifyPayPwd(oldPwd, firstPwd);
							} else {
								type = Type.FIRST;
								com.toobei.common.utils.ToastUtil
										.showCustomToast(getString(R.string.twice_pwd_different));
								gridPwd.clearPassword();
								changeView();
							}
						}
					}
				}
			}
		});
	}

	private void changeView() {
		switch (type) {
		case VERITY:
			textTitle.setText(R.string.input_pay_pwd_verity);
			textForget.setVisibility(View.VISIBLE);
			vgWarmPrompt.setVisibility(View.GONE);
			break;
		case FIRST:
			textTitle.setText(R.string.input_new_pay_first);
			textForget.setVisibility(View.INVISIBLE);
			vgWarmPrompt.setVisibility(View.GONE);
			break;
		case SECOND:
			textTitle.setText(R.string.input_new_pay_second);
			textForget.setVisibility(View.INVISIBLE);
			vgWarmPrompt.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	private void userVerifyPayPwd(final String pwd) {
		new MyNetAsyncTask(ctx, true) {
			CheckResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp.getInstance().getHttpService()
						.userVerifyPayPwd(TopApp.getInstance().getLoginService().token, pwd);

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						if (response.getData().getRlt().equals("true")) {
							type = Type.FIRST;
							gridPwd.clearPassword();
							changeView();
						} else {
							com.toobei.common.utils.ToastUtil.showCustomToast(ctx
									.getString(R.string.passwd_error));
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil.showCustomToast(ctx
							.getString(R.string.pleaseCheckNetwork));
				}
				gridPwd.clearPassword();
			}
		}.execute();
	}

	private void modifyPayPwd(final String oldPwd, final String newPwd) {
		new MyNetAsyncTask(ctx, true) {
			LoginResponseEntity response;

			@Override
			protected void doInBack() throws Exception {
				response = TopApp
						.getInstance()
						.getHttpService()
						.userModifyPayPwd(TopApp.getInstance().getLoginService().token, oldPwd,
								newPwd);

			}

			@Override
			protected void onPost(Exception e) {
				if (e == null && response != null) {
					if (response.getCode().equals("0")) {
						//修改支付 密码成功
						finish();
						com.toobei.common.utils.ToastUtil
								.showCustomToast(getString(R.string.set_success));
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil
							.showCustomToast(getString(R.string.pleaseCheckNetwork));
				}
				gridPwd.clearPassword();
				type = Type.VERITY;
				changeView();
			}
		}.execute();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.forget_passwd_bt) {
			onBtnForgePayPasswdClick();
		}
	}

	public abstract void onBtnForgePayPasswdClick();
}
