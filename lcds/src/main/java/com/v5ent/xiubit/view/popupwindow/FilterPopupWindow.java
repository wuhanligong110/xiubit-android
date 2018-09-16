package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.utils.TimeUtils;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.DateShowTimeType;
import com.v5ent.xiubit.entity.FilterDateData;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;
import org.xsl781.utils.PixelUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：时间 筛选 器
 * @date 2016-1-26
 */
public class FilterPopupWindow extends PopupWindow {
	private String minDate;
	private Context ctx;
	private FilterMainAdapter mainAdapter;
	private FilterSecondAdapter secondAdapter;
	private int minYear, curYear, minMonth, curMonth;
	private int mainSelectIndex = -1, secondSelectIndex = -1;
	private int selectYear;
	//, selectMonth;
	//	private DateShowTimeType selectTimeType;
	private OnFilterDateSelectedLintener onFilterDateSelectedLintener;

	public interface OnFilterDateSelectedLintener {
		void onFilterDateSelected(DateShowTimeType timeType, String time);
	}

	@SuppressWarnings("deprecation")
	public FilterPopupWindow(Context ctx, String minDate) {
		super(LayoutInflater.from(ctx).inflate(R.layout.layout_filter_popu, null),
				LayoutParams.MATCH_PARENT, PixelUtil.dip2px(ctx, 352), true);

		this.minDate = minDate;
		this.ctx = ctx;
		initDateParams();

		setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setTouchable(true);
		setOutsideTouchable(true);
		setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
					return true;
				}
				return false;
			}
		});
		initView();
	}

	private void initDateParams() {
		try {
			minYear = Integer.parseInt(TimeUtils.getYear(minDate));
			minMonth = Integer.parseInt(TimeUtils.getMonth(minDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		curYear = Calendar.getInstance().get(Calendar.YEAR);
		curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

	}

	public void initView() {
		View contextView = getContentView();
		ListView listView1 = (ListView) contextView.findViewById(R.id.listview1);
		mainAdapter = new FilterMainAdapter(ctx, getMainData());
		listView1.setAdapter(mainAdapter);
		listView1.setOnItemClickListener(new OnFilterItemClickListener(1));

		ListView listView2 = (ListView) contextView.findViewById(R.id.listview2);
		secondAdapter = new FilterSecondAdapter(ctx);
		listView2.setAdapter(secondAdapter);
		listView2.setOnItemClickListener(new OnFilterItemClickListener(2));
	}

	private List<String> getMainData() {
		List<String> list = new ArrayList<String>();
		list.add("今日");
		list.add("历史累计");
		for (int i = curYear; i >= minYear; i--) {
			list.add(i + "");
		}
		return list;
	}

	private List<FilterDateData> getSecondData() {
		List<FilterDateData> list = new ArrayList<FilterDateData>();
		switch (mainSelectIndex) {
		case 0://今日 较早的三十天
			for (int i = 0; i < 30; i++) {
				Date date = org.xsl781.utils.TimeUtils.getDateBefore(new Date(), i);
				if (date.after(org.xsl781.utils.TimeUtils.getDate(minDate,
						org.xsl781.utils.TimeUtils.FORMAT_DATE))) {
					list.add(new FilterDateData(DateShowTimeType.DAY, org.xsl781.utils.TimeUtils
							.getDateStr(date, org.xsl781.utils.TimeUtils.FORMAT_DATE)));
				}
			}
			break;
		case 1://历史累计，列表2为空

			break;
		default:
			list.add(new FilterDateData(DateShowTimeType.YREA, "全年", selectYear));
			if (selectYear == curYear) {
				if (selectYear > minYear) {//选择年份大于最小年，则只需要从1月1日到当前时间
					if (curMonth > 9) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "四季度", selectYear));
					}
					if (curMonth > 6) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "三季度", selectYear));
					}
					if (curMonth > 3) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "二季度", selectYear));
					}
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "一季度", selectYear));
					for (int i = curMonth; i >= 1; i--) {
						list.add(new FilterDateData(DateShowTimeType.MONTH, i + "月", selectYear, i));
					}

				} else if (selectYear == minYear) {//选择年份是当年，也是最小年，则需要从最小时间到当前月
					if (curMonth > 9) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "四季度", selectYear));
					}
					if (curMonth > 6 && minMonth <= 9) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "三季度", selectYear));
					}
					if (curMonth > 3 && minMonth <= 6) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "二季度", selectYear));
					}
					if (minMonth <= 3) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "一季度", selectYear));
					}
					for (int i = curMonth; i >= minMonth; i--) {
						list.add(new FilterDateData(DateShowTimeType.MONTH, i + "月", selectYear, i));
					}
				}

			} else if (selectYear < curYear) {
				if (selectYear > minYear) {//选择年份大于最小年，则数据为全部
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "四季度", selectYear));
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "三季度", selectYear));
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "二季度", selectYear));
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "一季度", selectYear));
					for (int i = 12; i >= 1; i--) {
						list.add(new FilterDateData(DateShowTimeType.MONTH, i + "月", selectYear, i));
					}
				} else if (selectYear == minYear) {//选择年份是当年，也是最小年，则需要从最小时间到12月
					list.add(new FilterDateData(DateShowTimeType.QUARTER, "四季度", selectYear));
					if (minMonth <= 9) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "三季度", selectYear));
					}
					if (minMonth <= 6) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "二季度", selectYear));
					}
					if (minMonth <= 3) {
						list.add(new FilterDateData(DateShowTimeType.QUARTER, "一季度", selectYear));
					}
					for (int i = 12; i >= minMonth; i--) {
						list.add(new FilterDateData(DateShowTimeType.MONTH, i + "月", selectYear, i));
					}
				}
			}
			break;

		}
		return list;
	}

	private class FilterMainAdapter extends BaseListAdapter<String> {

		public FilterMainAdapter(Context ctx) {
			super(ctx);
		}

		public FilterMainAdapter(Context ctx, List<String> datas) {
			super(ctx, datas);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_filter_list, null);
			}
			String content = getItem(position);
			TextView textItem = ViewHolder.findViewById(convertView, R.id.text_item);
			textItem.setText(content);
			textItem.getLayoutParams().height = PixelUtil.dip2px(ctx, 44);
			if (position == mainSelectIndex) {
				textItem.setBackgroundColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
				textItem.setTextColor(ContextCompat.getColor(ctx,R.color.WHITE));
			} else {
				textItem.setBackgroundColor(ContextCompat.getColor(ctx,R.color.Color_0));
				textItem.setTextColor(ContextCompat.getColor(ctx,R.color.BLACK));
			}
			return convertView;
		}
	}

	private class FilterSecondAdapter extends BaseListAdapter<FilterDateData> {

		public FilterSecondAdapter(Context ctx) {
			super(ctx);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_filter_list, null);
			}
			FilterDateData data = getItem(position);
			TextView textItem = ViewHolder.findViewById(convertView, R.id.text_item);
			textItem.setText(data.getContent());
			textItem.getLayoutParams().height = PixelUtil.dip2px(ctx, 38);
			if (position == secondSelectIndex) {
				textItem.setBackgroundColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
				textItem.setTextColor(ContextCompat.getColor(ctx,R.color.WHITE));
			} else {
				textItem.setBackgroundColor(ContextCompat.getColor(ctx,R.color.Color_0));
				textItem.setTextColor(ContextCompat.getColor(ctx,R.color.BLACK));
			}
			return convertView;
		}
	}

	private class OnFilterItemClickListener implements OnItemClickListener {
		private int listIndex;

		public OnFilterItemClickListener(int listIndex) {
			super();
			this.listIndex = listIndex;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			switch (listIndex) {
			case 1:
				if (position == 1) {//选择历史累计时
					dismiss();
					if (onFilterDateSelectedLintener != null) {
						onFilterDateSelectedLintener.onFilterDateSelected(DateShowTimeType.TOTAL,
								TimeUtils.getXnTodayTimeStr());
					}
				}
				if (position > 1) {//第三个开始为年份
					selectYear = Integer.parseInt(mainAdapter.getItem(position));
				}
				mainSelectIndex = position;
				secondSelectIndex = -1;
				mainAdapter.notifyDataSetInvalidated();
				secondAdapter.refresh(getSecondData());
				break;
			case 2:
				secondSelectIndex = position;
				secondAdapter.notifyDataSetInvalidated();
				dismiss();
				if (onFilterDateSelectedLintener != null) {
					FilterDateData data = secondAdapter.getItem(position);
					onFilterDateSelectedLintener.onFilterDateSelected(data.getTimeType(),
							data.getTime());
				}
				break;

			default:
				break;
			}
		}
	}

	public void setOnFilterDateSelectedLintener(
			OnFilterDateSelectedLintener onFilterDateSelectedLintener) {
		this.onFilterDateSelectedLintener = onFilterDateSelectedLintener;
	}

}
