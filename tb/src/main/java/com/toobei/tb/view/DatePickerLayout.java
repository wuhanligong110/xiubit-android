package com.toobei.tb.view;

import java.util.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.view.dialog.DoubleDatePickerDialog;

/**
 * 公司: tophlc
 * 类说明：时间日期 选择视图
 * @date 2015-11-4
 */
public class DatePickerLayout extends LinearLayout implements View.OnClickListener {
	private TextView textDateYear, textDateMonth;
	private int dateYear, dateMonth;
	private int dateYearMin, dateMonthMin;//交易的最小时间
	private String strDateMin, curSelectDate = null;
	private ViewGroup vgDate;
	private MyBaseActivity activity;

	private OnDatePickerSetListener listener;

	public interface OnDatePickerSetListener {
		/**
		 * @param curSelectDate 2015-10
		 */
		void onDatePickerSet(String curSelectDate);
	}

	public DatePickerLayout(Context context) {
		super(context);
		activity = (MyBaseActivity) context;
		init();
	}

	private void init() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_date_picker, null,
				false);
		vgDate = (ViewGroup) view.findViewById(R.id.date_root_ll);
		vgDate.setOnClickListener(this);
		textDateYear = (TextView) view.findViewById(R.id.customer_date_year);
		textDateMonth = (TextView) view.findViewById(R.id.customer_date_month);

		initDateView();
		addView(view);
	}

	private void setDateView() {
		textDateYear.setText(dateYear + "年");
		textDateMonth.setText(String.format("%02d", dateMonth + 1));
	}

	private void showDateDailog() {
		// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
		new DoubleDatePickerDialog(activity, 0, new DoubleDatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
					int startDayOfMonth) {
				System.out.println("startYear = "  + startYear + " startMonthOfYear" + startMonthOfYear);
				if (startYear < dateYearMin
						|| (startYear == dateYearMin && startMonthOfYear + 1< dateMonthMin)) {
					com.toobei.common.utils.ToastUtil.showCustomToast("最早交易时间为 " + strDateMin + ",请重新选择月份");
					return;
				}
				if (startYear > Calendar.getInstance().get(Calendar.YEAR)
						|| (startYear == Calendar.getInstance().get(Calendar.YEAR) && startMonthOfYear > Calendar
								.getInstance().get(Calendar.MONTH))) {
					com.toobei.common.utils.ToastUtil.showCustomToast("请选择正确的年月");
					return;
				}
				dateYear = startYear;
				dateMonth = startMonthOfYear;
				setDateView();
				curSelectDate = String.format("%d-%02d", startYear, startMonthOfYear + 1);
				if (listener != null) {
					listener.onDatePickerSet(curSelectDate);
				}
			}
		}, dateYear, dateMonth, 1, false).show();
		vgDate.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		vgDate.setEnabled(false);
		showDateDailog();
	}

	/**
	 * 功能：设置最小时间，如2014-12
	 * @param strDateMin
	 */
	public void setStrDateMin(String strDateMin) {
		this.strDateMin = strDateMin;
		if (strDateMin != null && strDateMin.length() > 6) {
			dateYearMin = Integer.parseInt(strDateMin.substring(0, 4));
			dateMonthMin = Integer.parseInt(strDateMin.substring(5, 7));
			System.out.println("========= dateYearMin" + dateYearMin + "=== dateMonthMin"
					+ dateMonthMin);
		}
	}

	/**
	 * 功能：初始化日期选择视图，显示当前时间
	 */
	public void initDateView() {
		dateYear = Calendar.getInstance().get(Calendar.YEAR);
		dateMonth = Calendar.getInstance().get(Calendar.MONTH);
		setDateView();
	}

	public void setListener(OnDatePickerSetListener listener) {
		this.listener = listener;
	}

}
