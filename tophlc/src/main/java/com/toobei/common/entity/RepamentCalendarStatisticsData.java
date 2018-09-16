package com.toobei.common.entity;

import java.util.List;

/**
 * Created by hasee-pc on 2017/11/29.
 */

public class RepamentCalendarStatisticsData extends BaseEntity {
    private static final long serialVersionUID = -8200827576917807784L;


    public String havaRepaymentAmtTotal;
    public String waitRepaymentAmtTotal;
    public List<CalendarStatisticsResponseListBean> calendarStatisticsResponseList;

    public static class CalendarStatisticsResponseListBean {
        /**
         * calendarNumber : 1
         * calendarTime : 2017-01-02
         */

        public int calendarNumber;
        public String calendarTime;
    }
}
