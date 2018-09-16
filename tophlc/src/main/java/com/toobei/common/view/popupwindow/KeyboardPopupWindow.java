package com.toobei.common.view.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.toobei.common.R;

public class KeyboardPopupWindow extends PopupWindow {

	private Activity ctx;
	private View contentView;
	private EditText et;
	private KeyboardView keyboard_view;

	public KeyboardPopupWindow(Activity ctx, EditText et) {
		super(ctx);
		this.ctx = ctx;
		this.et = et;
		initView();
	}

	private void initView() {
		contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_keyboard, null);
		// 设置SelectPicPopupWindow的View  
		this.setContentView(contentView);
		// 设置SelectPicPopupWindow弹出窗体的宽  
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高  
		this.setHeight(LayoutParams.WRAP_CONTENT);
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
				Editable editable = et.getText();
				int start = et.getSelectionStart();
				if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
					if (editable != null && editable.length() > 0) {
						if (start > 0) {
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
			}
		});
	}

	/** 
	* 显示popupWindow 
	* @param parent 
	*/
	public void showPopupWindow(View parent) {
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
		super.dismiss();
	}
}
