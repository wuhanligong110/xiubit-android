package com.toobei.common.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.R;

/**
 * 公司: tophlc
 * 类说明：拨打电话的提示框
 * @date 2015-10-27
 */
public class PromptDialogEmail extends Dialog implements android.view.View.OnClickListener {
	private Activity context;

	private String title;
	private String serviceMail;

	public PromptDialogEmail(Activity context, String title, String serviceMail) {
		super(context, R.style.customDialog);
		this.context = context;

		this.title = title;
		this.serviceMail = serviceMail;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_prompt_dialog_send_mail, null);
		this.setContentView(view);
		TextView textTitle = (TextView) findViewById(R.id.text_title);
		TextView textServiceMail = (TextView) findViewById(R.id.text_content);

		textTitle.setText(title);
		textServiceMail.setText(serviceMail);

		findViewById(R.id.positiveButton).setOnClickListener(this);
		findViewById(R.id.negativeButton).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.positiveButton) {

			Uri uri = Uri.parse("mailto:" + serviceMail);

			String[] email = { serviceMail };

			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

			//intent.putExtra(Intent.EXTRA_CC, email); // 抄送人  

			intent.putExtra(Intent.EXTRA_SUBJECT, " "); // 主题  

			intent.putExtra(Intent.EXTRA_TEXT, " "); // 正文  

			context.startActivity(intent);

		} else if (v.getId() == R.id.negativeButton) {

		}
		dismiss();
	}

}