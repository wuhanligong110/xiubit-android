package com.v5ent.xiubit.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：首页 公告实体
 * @date 2015-10-22
 */
public class HomePagerBulletin extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 9097651647471724205L;
	private String activityCount;//精彩活动
	private String todayProfit;//今日收益
	private String interlocutionCount; // 最新问答
	private String newEventCount; // 最新动态
	private List<HomePagerDynamic> events;//动态(没有动态时，为空数组)

	public String getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(String activityCount) {
		this.activityCount = activityCount;
	}

	public String getTodayProfit() {
		return todayProfit;
	}

	public void setTodayProfit(String todayProfit) {
		this.todayProfit = todayProfit;
	}

	public String getInterlocutionCount() {
		return interlocutionCount;
	}

	public void setInterlocutionCount(String interlocutionCount) {
		this.interlocutionCount = interlocutionCount;
	}

	public String getNewEventCount() {
		return newEventCount;
	}

	public void setNewEventCount(String newEventCount) {
		this.newEventCount = newEventCount;
	}

	public List<HomePagerDynamic> getEvents() {
		return events;
	}

	public void setEvents(List<HomePagerDynamic> events) {
		this.events = events;
	}

}