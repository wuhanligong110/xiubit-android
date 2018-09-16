package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class MineHome extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8370039548993217785L;
	
	private String userName;//用户名称
	private String mobile;//手机号码
	private String authentication;//是否实名验证()
	private String cfgLevelName;//理财师等级
	private String patternLevelName;//合伙人等级
	private String quarterFee;//本季度佣金
	private String currCfgLevel;//已达成理财师级别
	private String distCfgLevel;//目标理财师级别
	private String shortUpgrade;//离升级还差多少佣金
	private String monthProfit;//本月收益(元)
	private String cfgAllowance;//理财师津贴(元)
	private String feeProfit;//佣金收益(元)
	private String partnerAllowance;//合伙人津贴(元)
	private String recommendProfit;//推荐收益(元)
	private String partnerCount;//团队人数
	private String msgCount;//消息

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

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	public String getCfgLevelName() {
		return cfgLevelName;
	}

	public void setCfgLevelName(String cfgLevelName) {
		this.cfgLevelName = cfgLevelName;
	}

	public String getPatternLevelName() {
		return patternLevelName;
	}

	public void setPatternLevelName(String patternLevelName) {
		this.patternLevelName = patternLevelName;
	}

	public String getQuarterFee() {
		return quarterFee;
	}

	public void setQuarterFee(String quarterFee) {
		this.quarterFee = quarterFee;
	}

	public String getCurrCfgLevel() {
		return currCfgLevel;
	}

	public void setCurrCfgLevel(String currCfgLevel) {
		this.currCfgLevel = currCfgLevel;
	}

	public String getDistCfgLevel() {
		return distCfgLevel;
	}

	public void setDistCfgLevel(String distCfgLevel) {
		this.distCfgLevel = distCfgLevel;
	}

	public String getShortUpgrade() {
		return shortUpgrade;
	}

	public void setShortUpgrade(String shortUpgrade) {
		this.shortUpgrade = shortUpgrade;
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

	public String getFeeProfit() {
		return feeProfit;
	}

	public void setFeeProfit(String feeProfit) {
		this.feeProfit = feeProfit;
	}

	public String getPartnerAllowance() {
		return partnerAllowance;
	}

	public void setPartnerAllowance(String partnerAllowance) {
		this.partnerAllowance = partnerAllowance;
	}

	public String getRecommendProfit() {
		return recommendProfit;
	}

	public void setRecommendProfit(String recommendProfit) {
		this.recommendProfit = recommendProfit;
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

}