package com.toobei.common.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toobei.common.R;

/**
 * 公司: tophlc
 * 类说明：弹出进度条，下载时用
 * @date 2015-11-2
 */
public class PromptDialogProgressBar extends Dialog implements android.view.View.OnClickListener {
	private Context context;
	private TextView textProgress;
	private ProgressBar bar;
	private boolean isForceUpdate = true;//是否强制

	public PromptDialogProgressBar(Context context) {
		super(context, R.style.customDialog);
		this.context = context;
	}

	public interface DialogBtnOnClickListener {
		void onClicked(PromptDialogProgressBar dialog, boolean isCancel);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(R.layout.layout_prompt_dialog_progressbar,
				null);
		ImageView imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
		imgCancel.setOnClickListener(this);
		textProgress = (TextView) view.findViewById(R.id.text_progress);
		bar = (ProgressBar) view.findViewById(R.id.download_progress_bar);
		if (!isForceUpdate) {
			imgCancel.setVisibility(View.VISIBLE);
		}else {
			imgCancel.setVisibility(View.INVISIBLE);
		}
				
		this.setContentView(view);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.img_cancel) {
		}
		dismiss();
	}

	public void setProgress(int progress) {
		if (bar != null) {
			bar.setProgress((int) (progress * 0.1));
			this.textProgress.setText(String.format("%.1f", progress * 0.1f) + "%");
		}
	}

	public void setForceUpdate(boolean isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

}