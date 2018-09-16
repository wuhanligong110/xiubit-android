package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

public class MineHome extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8370039548993217785L;

    //	accountBalance	账户余额	string
//	cfgLevelName	理财师等级	string
//	todayProfit	本月销售佣金	string
//	headImage	头像图片地址	string
//	historyProfit	累计收益	string
//	hongbaoCount	可用红包数量	string
//	isBindBankCard	是否已绑定银行卡	boolean
//	levelExperience	职级经验值	string
//	mobile	手机号码	string
//	monthProfit	本月收益	string
//	msgCount	信息数量	string
//	newPartnerCount	新增团队成员数量	string
//	historyProfit	本月推荐收益	string
//	todayProfit	今日收益	string
//	userName	姓名	string
//	withdrawAmount	提现中的金额	string
    private String accountBalance;//账户余额
    private String isBindBankCard;//是否实名验证  是否绑卡 isBindBankCard=True
    private String cfgLevelName;//理财师等级
    private String todayProfit;// 今日收益
    private String headImage;//头像图片地址
    private String hongbaoCount;//红包数量
    private String levelExperience;//职级经验值
    private String mobile;//手机号码
    private String monthProfit;//本月收益(元)
    private String msgCount;//信息数量
    private String newPartnerCount;//新增理财师数量
    private String userName;//用户名称
    private String historyProfit;//累计收益(元)
    private String withdrawAmount;//提现中金额
    private String cfgAllowance;//理财师津贴(元)


    private String invitationDesc;//邀请客户描述
    private String partnerCount;//团队人数
    private String cfpDesc;//推荐理财师描述
    private String unRecordInvestCount="0";//报单条数	string	2.0.2新增
    private String unFinishNewcomerTaskCount="0";//未完成新手任务数量	string	2.0.3新增
    private String investmentRecord;  //0没有新的投资记录 1有新的投资记录
    //4.0
    private String investNum; //红包投资数量
    private String sendNum;  //红包派发数量
    private String onInvestNotReadMsg; //在投未读投资记录数目
    private String overdueInvestNotReadMsg;//已过期未读投资记录数目

    public String getOnInvestNotReadMsg() {
        return onInvestNotReadMsg;
    }

    public void setOnInvestNotReadMsg(String onInvestNotReadMsg) {
        this.onInvestNotReadMsg = onInvestNotReadMsg;
    }

    public String getOverdueInvestNotReadMsg() {
        return overdueInvestNotReadMsg;
    }

    public void setOverdueInvestNotReadMsg(String overdueInvestNotReadMsg) {
        this.overdueInvestNotReadMsg = overdueInvestNotReadMsg;
    }

    public String getInvestNum() {
        return investNum;
    }

    public void setInvestNum(String investNum) {
        this.investNum = investNum;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public String getInvestmentRecord() {
        return investmentRecord;
    }

    public void setInvestmentRecord(String investmentRecord) {
        this.investmentRecord = investmentRecord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIsBindBankCard() {
        return isBindBankCard;
    }

    public void setIsBindBankCard(String isBindBankCard) {
        this.isBindBankCard = isBindBankCard;
    }

    public String getCfgLevelName() {
        return cfgLevelName;
    }

    public void setCfgLevelName(String cfgLevelName) {
        this.cfgLevelName = cfgLevelName;
    }

    public String getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(String monthProfit) {
        this.monthProfit = monthProfit;
    }

    public String getCfgAllowance() {
        return cfgAllowance;
    }

    public void setCfgAllowance(String cfgAllowance) {
        this.cfgAllowance = cfgAllowance;
    }

    public String getTodayProfit() {
        return todayProfit;
    }

    public void setTodayProfit(String todayProfit) {
        this.todayProfit = todayProfit;
    }

    public String getHistoryProfit() {
        return historyProfit;
    }

    public void setHistoryProfit(String historyProfit) {
        this.historyProfit = historyProfit;
    }

    public String getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(String partnerCount) {
        this.partnerCount = partnerCount;
    }

    public String getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(String msgCount) {
        this.msgCount = msgCount;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getInvitationDesc() {
        return invitationDesc;
    }

    public void setInvitationDesc(String invitationDesc) {
        this.invitationDesc = invitationDesc;
    }

    public String getCfpDesc() {
        return cfpDesc;
    }

    public void setCfpDesc(String cfpDesc) {
        this.cfpDesc = cfpDesc;
    }

    public String getNewPartnerCount() {
        return newPartnerCount;
    }

    public void setNewPartnerCount(String newPartnerCount) {
        this.newPartnerCount = newPartnerCount;
    }

    public String getLevelExperience() {
        return levelExperience;
    }

    public void setLevelExperience(String levelExperience) {
        this.levelExperience = levelExperience;
    }

    public String getHongbaoCount() {
        return hongbaoCount;
    }

    public void setHongbaoCount(String hongbaoCount) {
        this.hongbaoCount = hongbaoCount;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUnRecordInvestCount() {
        return unRecordInvestCount;
    }

    public void setUnRecordInvestCount(String unRecordInvestCount) {
        this.unRecordInvestCount = unRecordInvestCount;
    }

    public String getUnFinishNewcomerTaskCount() {
        return unFinishNewcomerTaskCount;
    }

    public void setUnFinishNewcomerTaskCount(String unFinishNewcomerTaskCount) {
        this.unFinishNewcomerTaskCount = unFinishNewcomerTaskCount;
    }
}