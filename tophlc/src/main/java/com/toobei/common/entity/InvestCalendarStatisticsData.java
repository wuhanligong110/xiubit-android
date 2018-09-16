package com.toobei.common.entity;

import java.util.List;

/**
 * Created by hasee-pc on 2017/11/29.
 */

public class InvestCalendarStatisticsData extends BaseEntity {
    private static final long serialVersionUID = -7303828053839808015L;


        public String feeAmountSumTotal;
        public String investAmtTotal;
        public List<CalendarStatisticsResponseListBean> calendarStatisticsResponseList;

        public static class CalendarStatisticsResponseListBean {
            /**
             * calendarNumber : 2
             * calendarTime : 2017-02-06
             */

            public String calendarNumber;
            public String calendarTime;
        }
    }
