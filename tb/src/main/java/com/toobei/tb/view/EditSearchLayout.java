package com.toobei.tb.view;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：输入姓名或手机号 搜索视图控件
 * @date 2015-11-10
 */
public class EditSearchLayout extends LinearLayout implements View.OnClickListener {
	private MyBaseActivity ctx;
	private ClearEditText clearEditText;
	private String strSearch;
	private OnEditSearchListener onEditSearchListener;

	public interface OnEditSearchListener {
		void onEditTextChanged(String strSearch);

		void onSearchBtnClicked();
	}

	public EditSearchLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.ctx = (MyBaseActivity) context;
		init();
	}

	public EditSearchLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = (MyBaseActivity) context;
		init();
	}

	private void init() {
		View view = LayoutInflater.from(ctx).inflate(R.layout.edit_search_layout, null, false);
		clearEditText = (ClearEditText) view.findViewById(R.id.cedt_search);
		clearEditText.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
				strSearch = clearEditText.getText().toString();
				if (onEditSearchListener != null) {
					onEditSearchListener.onEditTextChanged(strSearch);
				}
			}
		});
		view.findViewById(R.id.btn_search).setOnClickListener(this);
		addView(view);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			if (onEditSearchListener != null) {
				onEditSearchListener.onSearchBtnClicked();
			}
			break;

		default:
			break;
		}
	}

	public void setOnEditSearchListener(OnEditSearchListener onEditSearchListener) {
		this.onEditSearchListener = onEditSearchListener;
	}

}
