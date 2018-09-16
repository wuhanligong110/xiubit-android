package com.toobei.common.view.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.TextFormatUtil;
import com.toobei.common.view.MyTextView;

import java.lang.reflect.Method;

public class PayPasswordPopupWindow extends PopupWindow implements OnClickListener {

	private Activity ctx;
	private View contentView;
	private View closedIv;
	private TextView forgetPayTv;
	private TextView errorTv;
	private GridPasswordView gridpwd_pay;
	private KeyboardView keyboard_view;
	private CallBack callBack;
	private int num = 0;
	private String money = "0.00";
	private float fee = 0f;
	private int errorTimes = 0;

	public PayPasswordPopupWindow(Activity ctx, String money, float fee,CallBack callBack) {
		super(ctx);
		this.ctx = ctx;
		this.callBack = callBack;
		this.money = money;
		this.fee = fee;
		initView();
	}

	private void initView() {
		contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_pay_password, null);
		// 设置SelectPicPopupWindow的View  
		this.setContentView(contentView);
		// 设置SelectPicPopupWindow弹出窗体的宽  
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高  
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击  
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态  
		this.update();
		// 实例化一个ColorDrawable颜色为半透明  
		ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
		// 设置SelectPicPopupWindow弹出窗体动画效果  
		this.setAnimationStyle(R.style.anim_popup_share);

		closedIv =  contentView.findViewById(R.id.closedIv);
		MyTextView moneyTv =  contentView.findViewById(R.id.moneyTv);
		errorTv =  contentView.findViewById(R.id.errorTv);
		TextView feeTv =  contentView.findViewById(R.id.feeTv);
		moneyTv.setText("¥"+ TextFormatUtil.formatFloat2zero(money));
		if (fee > 0) {
			feeTv.setText("额外扣除手续费¥"+TextFormatUtil.formatFloat2zero(""+fee));
			feeTv.setVisibility(View.VISIBLE);
		}else {
			feeTv.setVisibility(View.GONE);
		}
		forgetPayTv = (TextView) contentView.findViewById(R.id.forgetPayTv);
		closedIv.setOnClickListener(this);
		forgetPayTv.setOnClickListener(this);
		gridpwd_pay = (GridPasswordView) contentView.findViewById(R.id.gridpwd_pay);
		disableShowSoftInput(gridpwd_pay.getInputView());
		keyboard_view = (KeyboardView) contentView.findViewById(R.id.keyboard_view);
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
				num = gridpwd_pay.getPassWord().length();
				Editable editable = gridpwd_pay.getInputView().getText();
				int start = gridpwd_pay.getInputView().getSelectionStart();
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
						gridpwd_pay.getPasswordArr()[0] = null;
					} else if (primaryCode == -2) {
					} else {
						gridpwd_pay.setText(1, Character.toString((char) primaryCode));
					}
				} else if (num == 2) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridpwd_pay.setText(1, null);
					} else if (primaryCode == -2) {
					} else {
						gridpwd_pay.setText(2, Character.toString((char) primaryCode));
					}
				} else if (num == 3) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridpwd_pay.setText(2, null);
					} else if (primaryCode == -2) {
					} else {
						gridpwd_pay.setText(3, Character.toString((char) primaryCode));
					}
				} else if (num == 4) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridpwd_pay.setText(3, null);
					} else if (primaryCode == -2) {
					} else {
						gridpwd_pay.setText(4, Character.toString((char) primaryCode));
					}
				} else if (num == 5) {
					if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
						gridpwd_pay.setText(4, null);
					} else if (primaryCode == -2) {
					} else {
						gridpwd_pay.setText(5, Character.toString((char) primaryCode));
						userVerifyPayPwd(gridpwd_pay.getPassWord());
					}
				}
			}
		});
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

	/** 
	* 显示popupWindow 
	* @param parent 
	*/
	public void showPopupWindow(View parent) {
		backgroundAlpha(0.5f);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ctx.getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		ctx.getWindow().setAttributes(lp);
	}

	@Override
	public void dismiss() {
		backgroundAlpha(1f);
		gridpwd_pay.clearPassword();
		super.dismiss();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.closedIv) {
			dismiss();
		}
		else if (v.getId() == R.id.forgetPayTv) {
			callBack.onBtnForgeLoginPasswdClick();
			dismiss();
		}
	}

	public interface CallBack {
		void onBtnForgeLoginPasswdClick();

		void OnPayCompleted(boolean isPassed);
	}
	
	
	/*
	 * 验证支付密码
	 */
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
							if (callBack != null) {
								callBack.OnPayCompleted(true);
								errorTv.setVisibility(View.INVISIBLE);
							}
						} else {
							errorTv.setVisibility(View.VISIBLE);
							errorTimes ++;
							if (errorTimes >= 2) {
								forgetPayTv.setVisibility(View.VISIBLE);
							}else {
								forgetPayTv.setVisibility(View.INVISIBLE);
							}
						}
					} else {
						com.toobei.common.utils.ToastUtil.showCustomToast(response
								.getErrorsMsgStr());
					}
				} else {
					com.toobei.common.utils.ToastUtil.showCustomToast(ctx
							.getString(R.string.pleaseCheckNetwork));
				}
				gridpwd_pay.clearPassword();
				//btnNext.setEnabled(true);
			}
		}.execute();
	}
}
