package com.toobei.tb.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.tb.R;

public class InvitePreviewDialog extends Dialog {
	private int layoutRes;//布局文件
	private Context context;
	private boolean hasName = false;
	private String content;
	private DialogOnClickListener listener;

	public interface DialogOnClickListener {
		void onClicked(boolean hasName);
	}

	public InvitePreviewDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义布局的构造方法
	 * @param context
	 * @param resLayout
	 */
	public InvitePreviewDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 自定义主题及布局的构造方法
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public InvitePreviewDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	public InvitePreviewDialog(Context context, int theme, int resLayout, boolean hasName,
			String content) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
		this.hasName = hasName;
		this.content = content;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(layoutRes, null);
		this.setContentView(view);
		TextView textTitle = (TextView) findViewById(R.id.text_title);
		TextView textContent = (TextView) findViewById(R.id.text_content);
		Button btnConfirm = (Button) findViewById(R.id.positiveButton);
		if (!hasName) {
			textTitle.setText(R.string.recommend_cfp_in_no_name);
			btnConfirm.setText(R.string.recommend_cfp_in_send_no_name);
		} else {
			textTitle.setText(R.string.recommend_cfp_in_has_name);
			btnConfirm.setText(R.string.recommend_cfp_in_send_has_name);
		}
		textContent.setText(content);
		findViewById(R.id.positiveButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				listener.onClicked(hasName);
			}
		});
	}

	public void setListener(DialogOnClickListener listener) {
		this.listener = listener;
	}

}