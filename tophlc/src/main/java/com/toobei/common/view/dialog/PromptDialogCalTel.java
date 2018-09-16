package com.toobei.common.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;

import org.xsl781.utils.SystemTool;

/**
 * 公司: tophlc
 * 类说明：拨打电话的提示框
 * @date 2015-10-27
 */
public class PromptDialogCalTel extends Dialog implements android.view.View.OnClickListener {
	private Activity context;
	private boolean isCallServiceTel = true;
	private String title;
	private String phone;
	private Button btnPositive, btnNegative;
	private int btnTextColor = 0;
	private OnEventCountlistener onEventCountlistener;

	public PromptDialogCalTel(Activity context, boolean isCallServiceTel, String title, String phone) {
		super(context, R.style.customDialog);
		this.context = context;
		this.isCallServiceTel = isCallServiceTel;
		this.title = title;
		this.phone = phone;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_prompt_dialog_call_service_tel, null);
		this.setContentView(view);
		TextView textTitle = (TextView) findViewById(R.id.text_title);
		TextView textServicePhone = (TextView) findViewById(R.id.text_service_phone_content);
		TextView textServiceWorkTime = (TextView) findViewById(R.id.text_service_work_time);

		btnPositive = (Button) findViewById(R.id.positiveButton);
		btnPositive.setOnClickListener(this);
		btnNegative = (Button) findViewById(R.id.negativeButton);
		btnNegative.setOnClickListener(this);

		textTitle.setText(title);
		textServicePhone.setText(phone);
		if (isCallServiceTel) {
			textTitle.setVisibility(View.VISIBLE);
			textServiceWorkTime.setVisibility(View.VISIBLE);
			btnPositive.setText("确定");
		} else {
			textTitle.setVisibility(View.GONE);
			textServiceWorkTime.setVisibility(View.GONE);
			btnPositive.setText("呼叫");
		}

		if (btnTextColor > 0) {
			btnPositive.setTextColor(ContextCompat.getColor(context,btnTextColor));
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.positiveButton) {
			String phone2 = this.phone.replaceAll("-", "");
			System.out.println("phone = " + phone2);
			SystemTool.goPhone(context, phone2);
	//		onEventCountlistener.eventCount();
		} else if (v.getId() == R.id.negativeButton) {

		}
		dismiss();
	}

	/**
	 *
	 * 功能： 用于友盟统计
	 * @param onEventCountlistener
	 */
	public void setOnEventCountListener(OnEventCountlistener onEventCountlistener) {
		this.onEventCountlistener = onEventCountlistener;

	}

	public interface OnEventCountlistener {

		void eventCount();
	}

	/**
	 * 功能：设置是否拨打客服电话
	 * @param isCallServiceTel
	 */
	public void setCallServiceTel(boolean isCallServiceTel) {
		this.isCallServiceTel = isCallServiceTel;
	}

	/**
	 * 功能：设置按键字体颜色 在show前調用
	 * @param btnTextColorResource
	 */
	public void setBtnPositiveColor(int btnTextColorResource) {
		btnTextColor = btnTextColorResource;

	}

}