package com.toobei.common.utils;

import org.ocpsoft.prettytime.PrettyTime;
import org.xsl781.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static PrettyTime prettyTime = new PrettyTime();
    /**
     * yyMMddHHmmss
     */
    public static SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
    /**
     * yyyy-MM-dd
     */
    public static SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat commentSdf = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static SimpleDateFormat mesdf = new SimpleDateFormat("MM/dd HH:mm", Locale.getDefault());

    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_DATE_TIME2 = "yyyyMMddHHmmss";
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final String FORMAT_DATE_TIME3 = "yyyyMMddHHmmssSSS";

    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }

    private static SimpleDateFormat getDateFormat(String format) throws RuntimeException {
        return new SimpleDateFormat(format);
    }

    /**
     * 功能：获得当前日期的指定格式 字符串
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String getDateStr(Date date, String formatPattern) {
        SimpleDateFormat format = new SimpleDateFormat(formatPattern);
        return format.format(date);
    }

    /**
     * 功能：把字符串转为时间
     *
     * @param strDate
     * @param fromat
     * @return
     */
    public static Date getDate(String strDate, String fromat) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(fromat);
        return sf.parse(strDate);
    }

    public static String millisecs2Date(String time, String pattern) {
        String display = "";
        int tMin = 60 * 1000;
        int tHour = 60 * tMin;
        int tDay = 24 * tHour;

        if (time != null) {
            try {
                Date tDate = new SimpleDateFormat(pattern).parse(time);
                Date today = new Date();
                SimpleDateFormat todayDf = new SimpleDateFormat("yyyy-MM-dd");
                Date yesterday = new Date(todayDf.parse(todayDf.format(today)).getTime());
                Date beforeYes = new Date(yesterday.getTime() - tDay);
                if (tDate != null) {
                    long dTime = today.getTime() - tDate.getTime();
                    if (dTime < tDay && tDate.after(yesterday)) {
                        display = "今天    " + time.split(" ")[1];
                    } else if (tDate.after(beforeYes) && tDate.before(yesterday)) {
                        display = "昨天    " + time.split(" ")[1];
                    } else {
                        display = time;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return display;
    }

    public static String millisecs2DateString(long timestamp) {
        long gap = System.currentTimeMillis() - timestamp;
        if (gap < 1000 * 60 * 60 * 24 * 7) {
            return prettyTime.format(new Date(timestamp));
        } else {
            return getDate(new Date(timestamp));
        }
    }

    public static String millisecs2DateStringTwoDay(long timestamp) {
        long gap = System.currentTimeMillis() - timestamp;
        if (gap < 1000 * 60 * 60 * 24 * 2) {
            return prettyTime.format(new Date(timestamp));
        } else {
            return getDate(new Date(timestamp));
        }
    }

    public static String millisecs2DateStringMeParticip(long timestamp) {
        return mesdf.format(timestamp);
    }

    public static String millisecs2DateStringNew(long timestamp) {
        long gap = System.currentTimeMillis() - timestamp;
        if (gap < 1000 * 60 * 60 * 24) {
            if (gap < 60000) {
                return "片刻之前";
            } else if (gap < 3600000) {
                return gap / 60000 + "分钟前";
            } else {
                return gap / 3600000 + "小时前";
            }
        } else {
            return commentSdf.format(timestamp);
        }
    }

    public static boolean haveTimeGap(long lastTime, long time) {
        int gap = 1000 * 60 * 10;//10 mins
        return time - lastTime > gap;
    }

    public static String getDateStr() {
        return sf.format(new Date());
    }

    /**
     * 功能：得到年的第一天时间  年:2015-01-01
     *
     * @param year
     * @return
     */
    public static String getXnYearTimeStr(int year) {
        return year + "-01-01";
    }

    /**
     * 功能：得到季度的第一天时间  yyyy-01-01
     *
     * @param year
     * @param quarter
     * @return
     */
    public static String getXnQuarterTimeStr(int year, String quarter) {
        if (quarter == null || quarter.length() == 0) {
            return "";
        }
        String xnQuarter = "";
        if (quarter.equals("一季度")) {
            xnQuarter = year + "-01-01";
        } else if (quarter.equals("二季度")) {
            xnQuarter = year + "-04-01";
        } else if (quarter.equals("三季度")) {
            xnQuarter = year + "-07-01";
        } else if (quarter.equals("四季度")) {
            xnQuarter = year + "-10-01";
        }
        return xnQuarter;
    }

    /**
     * 功能：得到月份的第一天时间 yyyy-MM-01
     *
     * @param year
     * @param month
     * @return
     */
    public static String getXnMonthTimeStr(int year, int month) {
        return year + "-" + String.format("%02d", month) + "-01";
    }

    /**
     * 功能：得到日期 yyyy-MM-dd
     *
     * @return
     */
    public static String getXnTodayTimeStr() {
        return sf2.format(new Date());
    }

    /**
     * 功能：得到年份
     *
     * @param content yyyy-MM-dd
     * @return
     */
    public static String getYear(String content) {
        if (content != null && content.length() > 3) {
            return content.substring(0, 4);
        }
        return "";
    }

    /**
     * 功能：得到月份
     *
     * @param content yyyy-MM-dd
     * @return
     */
    public static String getMonth(String content) {
        if (content != null && content.length() > 6) {
            return content.substring(5, 7);
        }
        return "";
    }

    /**
     *  得到月份 小于10 显示一位
     * @param content
     * @return
     */
    public static String getMonth02(String content) {
        if (content != null && content.length() > 6) {
            String substring = content.substring(5, 7);
            int i = StringUtils.toInt(substring, 1);
            return i>9?substring:substring.substring(1);
        }
        return "";
    }

    /**
     * 功能：得到季度 二季度
     *
     * @param content yyyy-04-dd
     * @return
     */
    public static String getQuarter(String content) {
        int month = 1;
        if (content != null && content.length() > 6) {
            month = Integer.parseInt(content.substring(5, 7));
        }
        String quarter = "";
        switch (month) {
            case 1:
                quarter = "一季度";
                break;
            case 4:
                quarter = "二季度";
                break;
            case 7:
                quarter = "三季度";
                break;
            case 10:
                quarter = "四季度";
                break;

            default:
                break;
        }
        return quarter;
    }

    /**
     * 功能：得到天数dd
     *
     * @param content yyyy-MM-dd
     * @return
     */
    public static String getDay(String content) {
        if (content != null && content.length() > 9) {
            return content.substring(8, 10);
        }
        return "";
    }


    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到偏移的时间
     *
     * @param d
     * @param timeField Calendar.SECOND Calendar.DATE等等
     * @param time      往前算 time为负值 往后算时time为正数
     * @return
     */
    public static Date getOffsetDate(Date d, int timeField, int time) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(timeField, now.get(timeField) + time);
        return now.getTime();
    }

    /**
     * 得到date的当天 第一秒的时间  2016-08-29 00:00:00
     *
     * @return Date
     */
    public static String dayStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String startStr = getDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
        return startStr;
    }

    /**
     * 功能：得到天的格式日期yyyy年MM月dd日
     *
     * @param date
     * @return
     */
    public static String dayStr(Date date) {
        String startStr = getDateFormat("yyyy年MM月dd日").format(date);
        return startStr;
    }

    /**
     * 功能：得到小时的时间格式 yyyy-MM-dd HH时
     *
     * @param date
     * @return
     */
    public static String hourStr(Date date) {
        return getDateFormat("yyyy-MM-dd HH").format(date) + "时";
    }

    public static String hourStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getDateFormat("yyyy-MM-dd HH").format(date) + ":00:00";
    }

    public static String hourEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getDateFormat("yyyy-MM-dd HH").format(date) + ":59:59";
    }

    /**
     * 得到本天最后一秒的时间。 2016-08-29 23:59:59
     *
     * @return Date
     */
    public static String dayEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return getDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
    }

    /**
     * 功能：得到月份日期格式  yyyy年MM月
     *
     * @param date
     * @return
     */
    public static String monthStr(Date date) {
        String startStr = getDateFormat("yyyy年MM月").format(date);
        return startStr;
    }

    /**
     * 得到本月的第一天的开始,格式为长日期格式。例如：2016-08-01 00:00:00。
     *
     * @return Date
     */
    public static String monthStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String startStr = getDateFormat("yyyy-MM-").format(date)
                + c.getActualMinimum(Calendar.DATE) + " 00:00:00";
        return startStr;
    }

    /**
     * 功能：得到本月最后一天 2016-08-31 23:59:59
     *
     * @param date
     * @return
     */
    public static String monthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String startStr = getDateFormat("yyyy-MM-").format(date)
                + c.getActualMaximum(Calendar.DATE) + " 23:59:59";
        return startStr;
    }

    /**
     * 功能： 取年份的第几周 yyyy年X周
     *
     * @param date
     * @return
     */
    public static String weekStr(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String startStr = getDateFormat("yyyy年").format(date) + c.get(Calendar.WEEK_OF_YEAR) + "周";
        return startStr;
    }

    /**
     * 功能：得到本周第一天，周一为第一天
     *
     * @param date
     * @return
     */
    public static String weekStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date = c.getTime();
        String startStr = getDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
        return startStr;
    }

    /**
     * 功能：得到本周最后一天，周日为最后一天
     *
     * @param date
     * @return
     */
    public static String weekEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, 6);
        date = c.getTime();
        String startStr = getDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
        return startStr;
    }

    /**
     * 功能：取年份
     *
     * @param date
     * @return
     */
    public static String yearStr(Date date) {
        String startStr = getDateFormat("yyyy年").format(date);
        return startStr;
    }

    /**
     * 功能：得到当年的第一天  2016-01-01 00:00:00
     *
     * @param date
     * @return
     */
    public static String yearStart(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String startStr = getDateFormat("yyyy").format(date) + "-01-01 00:00:00";
        return startStr;
    }

    /**
     * 功能：得到当年的最后一天   2016-12-31 23:59:59
     *
     * @param date
     * @return
     */
    public static String yearEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        c.clear();
        c.set(Calendar.YEAR, year);
        c.roll(Calendar.DAY_OF_YEAR, -1);
        date = c.getTime();
        String startStr = getDateFormat("yyyy-MM-dd").format(date) + " 23:59:59";
        return startStr;
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);

        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的前一周最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfWeek(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.WEEK_OF_YEAR) - 1);
    }

    /**
     * 返回指定日期的月的第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的上个月的最后一天
     *
     * @return
     */
    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的季的第一天
     *
     * @return
     */
    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFirstDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的季的第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 1 - 1;
        } else if (quarter == 2) {
            month = 4 - 1;
        } else if (quarter == 3) {
            month = 7 - 1;
        } else if (quarter == 4) {
            month = 10 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季的最后一天
     *
     * @return
     */
    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 3 - 1;
        } else if (quarter == 2) {
            month = 6 - 1;
        } else if (quarter == 3) {
            month = 9 - 1;
        } else if (quarter == 4) {
            month = 12 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的上一季的最后一天
     *
     * @return
     */
    public static Date getLastDayOfLastQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的上一季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 12 - 1;
        } else if (quarter == 2) {
            month = 3 - 1;
        } else if (quarter == 3) {
            month = 6 - 1;
        } else if (quarter == 4) {
            month = 9 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季度
     *
     * @param date
     * @return
     */
    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }

}
