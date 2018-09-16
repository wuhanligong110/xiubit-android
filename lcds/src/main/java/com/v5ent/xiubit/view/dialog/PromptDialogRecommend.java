package com.v5ent.xiubit.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.v5ent.xiubit.R;

public class PromptDialogRecommend extends Dialog implements View.OnClickListener {
	private Context context;
	private CharSequence content;

	private String btnNegativeText;
	private DialogBtnOnClickListener listener;

	public PromptDialogRecommend(Context context,
								 String btnNegativeText) {
		super(context, R.style.customDialog);
		this.context = context;
		this.btnNegativeText = btnNegativeText;
	}

	public interface DialogBtnOnClickListener {
		void onClicked(PromptDialogRecommend dialog, boolean isCancel);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_recommend, null);
		this.setContentView(view);

		Button btnNegative = (Button) findViewById(R.id.negativeButton);
		if (btnNegativeText == null) {
			btnNegative.setVisibility(View.GONE);
		} else {
			btnNegative.setText(btnNegativeText);
		}
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