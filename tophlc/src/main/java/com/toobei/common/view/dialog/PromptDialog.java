package com.toobei.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.timeselector.Utils.TextUtil;

public class PromptDialog extends Dialog implements android.view.View.OnClickListener {
	private  String title="";
	private Context context;
	private String content;
	private String btnPositiveText;
	private String btnNegativeText;
	private DialogBtnOnClickListener listener;

	public PromptDialog(Context context, String content, String btnPositiveText,
			String btnNegativeText) {
		super(context, R.style.customDialog);
		this.context = context;
		this.content = content;
		this.btnPositiveText = btnPositiveText;
		this.btnNegativeText = btnNegativeText;
	}
	public PromptDialog(Context context, String title,String content, String btnPositiveText,
			String btnNegativeText) {
		super(context, R.style.customDialog);
		this.context = context;
		this.content = content;
		// TODO: 2016/12/19 0019  当使用 Dialog的最外层布局cardView 时 这里设置titile 会导致显示异常
		this.title=title;
		this.btnPositiveText = btnPositiveText;
		this.btnNegativeText = btnNegativeText;
	}

	public interface DialogBtnOnClickListener {
		void onClicked(PromptDialog dialog, boolean isCancel);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_prompt_dialog, null);
		this.setContentView(view);
		TextView textTitle = (TextView) findViewById(R.id.text_title);
		TextView textContent = (TextView) findViewById(R.id.text_content);
		Button btnConfirm = (Button) findViewById(R.id.positiveButton);
		Button btnNegative = (Button) findViewById(R.id.negativeButton);
		textTitle.setText(title);
		textTitle.setVisibility(TextUtil.isEmpty(title)?View.GONE:View.VISIBLE);
		textContent.setGravity(TextUtil.isEmpty(title)?Gravity.CENTER:Gravity.LEFT);
		textContent.setVisibility(TextUtil.isEmpty(content)?View.GONE:View.VISIBLE);
		if (TextUtil.isEmpty(btnNegativeText)) {
			btnNegative.setVisibility(View.GONE);
		} else {
			btnNegative.setText(btnNegativeText);
		}
		
		btnConfirm.setText(btnPositiveText);
		textContent.setText(content);

		btnConfirm.setOnClickListener(this);
		btnNegative.setOnClickListener(this);
	}

	public void setListener(DialogBtnOnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.positiveButton) {
			if (listener != null) {
				listener.onClicked(this, false);
			}
		} else if (v.getId() == R.id.negativeButton) {
			if (listener != null) {
				listener.onClicked(this, true);
			}
		}
		dismiss();
	}

}