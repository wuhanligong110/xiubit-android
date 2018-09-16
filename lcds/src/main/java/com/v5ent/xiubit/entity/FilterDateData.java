package com.v5ent.xiubit.entity;

import com.toobei.common.utils.TimeUtils;
import com.v5ent.xiubit.data.DateShowTimeType;

/**
 * 公司: tophlc
 * 类说明：筛选时间用的实体
 * @date 2016-1-28
 */
public class FilterDateData {

	private DateShowTimeType timeType;
	private String content;
	private String time;
	private int year;
	private int month;
	private int day;

	public FilterDateData() {
		super();
	}

	public FilterDateData(DateShowTimeType timeType, String content) {
		super();
		this.timeType = timeType;
		this.content = content;
	}

	public FilterDateData(DateShowTimeType timeType, String content, int year) {
		super();
		this.timeType = timeType;
		this.content = content;
		this.year = year;
	}

	public FilterDateData(DateShowTimeType timeType, String content, int year, int month) {
		super();
		this.timeType = timeType;
		this.content = content;
		this.year = year;
		this.month = month;
	}

	public FilterDateData(DateShowTimeType timeType, String content, int year, int month, int day) {
		super();
		this.timeType = timeType;
		this.content = content;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public DateShowTimeType getTimeType() {
		return timeType;
	}

	public void setTimeType(DateShowTimeType timeType) {
		this.timeType = timeType;
	}

	public String getTime() {
		if (time == null) {
			switch (timeType) {
			case YREA:
				time = TimeUtils.getXnYearTimeStr(year);
				break;
			case QUARTER:
				time = TimeUtils.getXnQuarterTimeStr(year, content);
				break;
			case MONTH:
				time = TimeUtils.getXnMonthTimeStr(year, month);
				break;
			case DAY:
				time = content;
				break;

			default:
				break;
			}
		}
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}