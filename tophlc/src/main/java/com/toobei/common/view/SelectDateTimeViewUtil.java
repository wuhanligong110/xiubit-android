/*
package com.toobei.common.view;

import android.app.Activity;
import android.view.View;

import com.toobei.common.utils.TimeUtils;
import com.v5ent.xiubit.data.DateShowTimeType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

*/
/**
 * 日期时间选择弹出界面 工具，如：2016  09  10 15：30
 *//*

public class SelectDateTimeViewUtil {

    private Activity activity;
    private View rootView;
    private List<MonthViewDate> datas = new ArrayList<MonthViewDate>();
    private int minYear, minMonth;
    private SelectWheelPopup monthSelectWheelPopup;
    private OnSelectDateTimeLintener onSelectDateTimeLintener;
    private boolean isFirstShow=true;

    public SelectDateTimeViewUtil(Activity activity, View rootView, String minDate) {
        this.activity = activity;
        this.rootView = rootView;
        initDateParams(minDate);
    }

    public interface OnSelectDateTimeLintener {
        void onDateTimeSelected(String time);
    }

    private void initDateParams(String minDate) {

        try {
            minYear = Integer.parseInt(TimeUtils.getYear(minDate));
            minMonth = Integer.parseInt(TimeUtils.getMonth(minDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //    curYear = Calendar.getInstance().get(Calendar.YEAR);
        //   curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        Date nowDate = new Date();
        int count = 0;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年 MM月");
        while (count > -24) {
            Date date = TimeUtils.getOffsetDate(nowDate, Calendar.MONTH, count);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            MonthViewDate monthViewDate = new MonthViewDate(sf.format(date), DateShowTimeType.MONTH, TimeUtils.getXnMonthTimeStr(year, month));
            datas.add(0, monthViewDate);
            if (year == minYear && month == minMonth) {
                break;
            }
            count--;
        }
    }


    public void showView() {
        if (monthSelectWheelPopup == null) {
            SelectWheelAdapter adapter = new SelectWheelAdapter(activity, datas) {
                @Override
                public CharSequence getItemText(int index) {
                    return datas.get(index).dateStr;
                }
            };
            monthSelectWheelPopup = new SelectWheelPopup(activity, datas, adapter);
            monthSelectWheelPopup.setOnSelectWheelPopupCompletedListener(new SelectWheelPopup.OnSelectWheelPopupCompletedListener() {
                @Override
                public void onSelectWheelPopupCompleted(SelectWheelPopup selectWheelPopup, Object object) {

                    if (onFilterMonthDateSelectedLintener != null) {
                        MonthViewDate data = (MonthViewDate) object;
                        onFilterMonthDateSelectedLintener.onFilterMonthDateSelected(data.timeType, data.time);
                    }
                }
            });
        }

        if(isFirstShow){
            monthSelectWheelPopup.setCurrentItem(datas.size()-1); //
            isFirstShow=false;
        }
        monthSelectWheelPopup.showAtLocation(rootView);
    }

    */
/**
     * dateStr 显示用
     * timeType 时间类别: 1:年；2:季度；3:月；4:日
     * time 时间格式:2015-12-24 年:2015-01-01 季度:一季度2015-01-01 二季度 2015-04-01
     * …月: 2015-09-01 日:2015-09-06
     *//*

    private class MonthViewDate {
        public String dateStr;
        public DateShowTimeType timeType;
        public String time;

        public MonthViewDate() {
        }

        public MonthViewDate(String dateStr, DateShowTimeType timeType, String time) {
            this.dateStr = dateStr;
            this.timeType = timeType;
            this.time = time;
        }
    }

    public void setOnFilterMonthDateSelectedLintener(OnFilterMonthDateSelectedLintener onFilterMonthDateSelectedLintener) {
        this.onFilterMonthDateSelectedLintener = onFilterMonthDateSelectedLintener;
    }

    */
/**
     * 设置选中的月
     *
     * @param time 选中的月
     *//*

    public void setCurrentMonth(String time) {
        if(monthSelectWheelPopup != null) {
            for (int i = 0; i < datas.size(); i++) {
                if (datas.get(i).time.equals(time)) {
                    monthSelectWheelPopup.setCurrentItem(i);
                    break;
                }
            }

        }
    }
}
*/
