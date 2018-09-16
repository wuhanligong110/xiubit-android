package org.xsl781.ui.view;

import org.xsl781.ui.view.wheel.NumericWheelAdapter;
import org.xsl781.ui.view.wheel.OnWheelChangedListener;
import org.xsl781.ui.view.wheel.WheelView;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SelectWheel extends PopupWindow {

	private Context mContext;
	private DateNumericAdapter adapter;
	private WheelView wheel;
	private TextView mTextview;
	private String strValue;
	private Handler handler;
	private int mHeight = 0;
	private int mTextSize = 24;

	public SelectWheel(Context context, TextView textview, final int minValue, int maxValue,
			int current) {
		super(context);
		mContext = context;
		this.mTextview = textview;
		wheel = new WheelView(mContext);
		strValue = current + "";
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				strValue = (minValue + newValue) + "";
			}
		};
		adapter = new DateNumericAdapter(context, minValue, maxValue, current);
		wheel.setViewAdapter(adapter);
		int curValue = minValue - 1;
		if (textview.getText().toString().length() > 0) {
			try {
				curValue = Integer.parseInt(textview.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
				curValue = minValue - 1;
			}
		}
		if (curValue > minValue) {
			wheel.setCurrentItem(curValue - minValue);
		} else {
			wheel.setCurrentItem(current - minValue);
		}
		wheel.addChangingListener(listener);
		this.setContentView(wheel);
		this.setWidth(100);

		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
		this.update();
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	@SuppressWarnings("unused")
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTypeface(Typeface.SANS_SERIF);
			view.setTextSize(mTextSize);
		}

		@Override
		public CharSequence getItemText(int index) {
			currentItem = index;
			return super.getItemText(index);
		}

	}

	/*
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.submit:
				//	mHandler.sendMessage(mHandler.obtainMessage(Constent.SETTING_USER_BIRTHDAY, age));
				mTextview.setText(strValue);
				break;

			default:
				break;
			}
			this.dismiss();
		}*/

	@Override
	public void dismiss() {
		super.dismiss();
		mTextview.setText(strValue);
		//		if(handler != null){
		//			handler.sendMessage(handler.obtainMessage(Constent.GET_GOAL_NUMBER, Integer.parseInt(strValue), 0));
		//		}
	}

	public void setmHeight(int height) {
		this.mHeight = height;
		if (height > 0) {
			this.setHeight(height);
		}
	}

	public void setmTextSize(int textSize) {
		this.mTextSize = textSize;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
