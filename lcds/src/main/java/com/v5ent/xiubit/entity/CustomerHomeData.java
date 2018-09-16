package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class CustomerHomeData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 103546626826787498L;


	/**
	 * advertisementImageUrl : 测试内容95t6
	 * advertisementLinkUrl : 测试内容219n
	 * dayInvestAmt : 0.00
	 * hasAdvertisement : 测试内容cc78
	 * hasCustomer : 测试内容i1u4
	 * hasTeamMembers : 测试内容6md6
	 * level : 测试内容3960
	 * minTime : 测试内容f8g8
	 * monthInvestAmt : 0.00
	 * newBacktradeCount : 0
	 * newBuytradeCount : 0
	 * newCustomerCount : 5
	 * newMsgCount : 0
	 * teamCount : 测试内容067b
	 * thisMonthAllowance : 测试内容f5w3
	 * thisMonthFee : 测试内容4y4u
	 * thisMonthTeamSaleAmount : 测试内容9djh
	 * totalInvestAmt : 0.00
	 */

	private String advertisementImageUrl;      //advertisementImageUrl	广告图片	string
	private String advertisementLinkUrl;       //	advertisementLinkUrl	广告	string
	private String dayInvestAmt;               //	dayInvestAmt	今日投资总额	string
	private String hasAdvertisement;           //	hasAdvertisement	是否有广告：1有，0无	string
	private String hasCustomer;                //	hasCustomer	是否有客户：1有，0无	string	2.0.2新增
	private String hasTeamMembers;             //	hasTeamMembers	是否有团队成员：1有，0无	string	2.0.2新增
	private String level;                      //	level	职级	string	2.0.2新增
	private String minTime;                    //	minTime	客户投资最早时间	string
	private String monthInvestAmt="0.00";             //	monthInvestAmt	本月投资总额	string
	private String newBacktradeCount;          //	newBacktradeCount	未读赎回动态数	string
	private String newBuytradeCount;           //	newBuytradeCount	未读申购动态数	string
	private String newCustomerCount;           //	newCustomerCount	未读客户态数	string
	private String newMsgCount;                //	newMsgCount	未读消息态数	string
	private String teamCount;                  //	teamCount	团队人数	string	2.0.2新增
	private String thisMonthAllowance="0.00";         //	thisMonthAllowance	本月团队推荐奖励	string	2.0.2新增
	private String thisMonthFee="0.00";               //	thisMonthFee	本月佣金	string	2.0.2新增
	private String thisMonthTeamSaleAmount="0.00";    //	thisMonthTeamSaleAmount	本月团队销售额	string	2.0.2新增
	private String totalInvestAmt="0.00";             //	totalInvestAmt	累计投资总额	string
	private String leaderProfit;				//  leader奖励

	//V3.2.0
	private String feeMonth;   //佣金月份

	public String getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(String feeMonth) {
		this.feeMonth = feeMonth;
	}

	public void setAdvertisementImageUrl(String advertisementImageUrl) {
		this.advertisementImageUrl = advertisementImageUrl;
	}

	public void setAdvertisementLinkUrl(String advertisementLinkUrl) {
		this.advertisementLinkUrl = advertisementLinkUrl;
	}

	public void setDayInvestAmt(String dayInvestAmt) {
		this.dayInvestAmt = dayInvestAmt;
	}

	public void setHasAdvertisement(String hasAdvertisement) {
		this.hasAdvertisement = hasAdvertisement;
	}

	public void setHasCustomer(String hasCustomer) {
		this.hasCustomer = hasCustomer;
	}

	public void setHasTeamMembers(String hasTeamMembers) {
		this.hasTeamMembers = hasTeamMembers;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public void setMonthInvestAmt(String monthInvestAmt) {
		this.monthInvestAmt = monthInvestAmt;
	}

	public void setNewBacktradeCount(String newBacktradeCount) {
		this.newBacktradeCount = newBacktradeCount;
	}

	public void setNewBuytradeCount(String newBuytradeCount) {
		this.newBuytradeCount = newBuytradeCount;
	}

	public void setNewCustomerCount(String newCustomerCount) {
		this.newCustomerCount = newCustomerCount;
	}

	public void setNewMsgCount(String newMsgCount) {
		this.newMsgCount = newMsgCount;
	}

	public void setTeamCount(String teamCount) {
		this.teamCount = teamCount;
	}

	public void setThisMonthAllowance(String thisMonthAllowance) {
		this.thisMonthAllowance = thisMonthAllowance;
	}

	public void setThisMonthFee(String thisMonthFee) {
		this.thisMonthFee = thisMonthFee;
	}

	public void setThisMonthTeamSaleAmount(String thisMonthTeamSaleAmount) {
		this.thisMonthTeamSaleAmount = thisMonthTeamSaleAmount;
	}

	public void setTotalInvestAmt(String totalInvestAmt) {
		this.totalInvestAmt = totalInvestAmt;
	}

	public String getAdvertisementImageUrl() {
		return advertisementImageUrl;
	}

	public String getAdvertisementLinkUrl() {
		return advertisementLinkUrl;
	}

	public String getDayInvestAmt() {
		return dayInvestAmt;
	}

	public String getHasAdvertisement() {
		return hasAdvertisement;
	}

	public String getHasCustomer() {
		return hasCustomer;
	}

	public String getHasTeamMembers() {
		return hasTeamMembers;
	}

	public String getLevel() {
		return level;
	}

	public String getMinTime() {
		return minTime;
	}

	public String getMonthInvestAmt() {
		return monthInvestAmt;
	}

	public String getNewBacktradeCount() {
		return newBacktradeCount;
	}

	public String getNewBuytradeCount() {
		return newBuytradeCount;
	}

	public String getNewCustomerCount() {
		return newCustomerCount;
	}

	public String getNewMsgCount() {
		return newMsgCount;
	}

	public String getTeamCount() {
		return teamCount;
	}

	public String getThisMonthAllowance() {
		return thisMonthAllowance;
	}

	public String getThisMonthFee() {
		return thisMonthFee;
	}

	public String getThisMonthTeamSaleAmount() {
		return thisMonthTeamSaleAmount;
	}

	public String getTotalInvestAmt() {
		return totalInvestAmt;
	}

	public String getLeaderProfit() {
		return leaderProfit;
	}

	public void setLeaderProfit(String leaderProfit) {
		this.leaderProfit = leaderProfit;
	}
}