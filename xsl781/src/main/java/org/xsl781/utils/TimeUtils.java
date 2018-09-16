package org.xsl781.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {
	/*public static PrettyTime prettyTime=new PrettyTime();

	public static String getDate(Date date) {
	  SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	  return format.format(date);
	}

	public static String millisecs2DateString(long timestamp) {
	  long gap=System.currentTimeMillis()-timestamp;
	  if(gap<1000*60*60*24){
	    return prettyTime.format(new Date(timestamp));
	  }else{
	    return getDate(new Date(timestamp));
	  }
	}*/
	//	private static SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");

	/**
	 * yyyy-MM-dd
	 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	/**
	 * yyyy-MM-dd hh:mm:ss
	 */
	public static final String FORMAT_DATE_TIME1 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String FORMAT_DATE_TIME2 = "yyyyMMddHHmmss";

	public static boolean haveTimeGap(long lastTime, long time) {
		int gap = 1000 * 60 * 10;//10 mins
		return time - lastTime > gap;
	}

	/**
	 * 功能：返回当前时间 格式 yyMMddHHmmss
	 * @return
	 */
	public static String getDateStr() {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
		return sf.format(new Date());
	}

	/**
	 * 功能：把字符串转为时间
	 * @param strDate
	 * @param fromat
	 * @return
	 */
	public static Date getDate(String strDate, String fromat) {
		SimpleDateFormat sf = new SimpleDateFormat(fromat);
		try {
			return sf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * 功能：获得当前日期的指定格式 字符串
	 * @param date
	 * @param formatPattern
	 * @return
	 */
	public static String getDateStr(Date date, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(date);
	}

	/** 
	* 获取当前日期 
	* @return 
	*/
	public static String getCurrentDate(String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		return format.format(new Date());
	}

	public static long getPassTime(long beforeTime) {
		return System.currentTimeMillis() - beforeTime;
	}

	public static int currentSecs() {
		int l;
		l = (int) (new Date().getTime() / 1000);
		return l;
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
}
