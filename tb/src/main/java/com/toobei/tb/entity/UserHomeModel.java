package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class UserHomeModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String investAmount;// 投资总额
	private String accountBalance;// 账户余额
	private String headImage;// 头像
	private String hongbaoCount;// 可用红包数
	private boolean isBindBankCard;// 是否已绑定银行卡
	private String mobile;// 电话号码
	private String msgCount; // 消息数
	private String orgAccountCount;// 机构账户数量
	private String totalProfit;// 总收益(元)
	private String unBindOrgAccountCount;// 未绑定机构数量
	private String userName;// 用户名称
	private String withdrawAmount;// 提现中金额




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

	public boolean getBindBankCard() {
		return isBindBankCard;
	}

	public void setBindBankCard(boolean bindBankCard) {
		this.isBindBankCard = bindBankCard;
	}

	public String getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(String msgCount) {
		this.msgCount = msgCount;
	}

//	public String getTotalAmount() {
//		return totalAmount;
//	}
//
//	public void setTotalAmount(String totalAmount) {
//		this.totalAmount = totalAmount;
//	}

	public String getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(String totalProfit) {
		this.totalProfit = totalProfit;
	}

	public String getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
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

	public String getOrgAccountCount() {
		return orgAccountCount;
	}

	public void setOrgAccountCount(String orgAccountCount) {
		this.orgAccountCount = orgAccountCount;
	}

	public String getUnBindOrgAccountCount() {
		return unBindOrgAccountCount;
	}

	public void setUnBindOrgAccountCount(String unBindOrgAccountCount) {
		this.unBindOrgAccountCount = unBindOrgAccountCount;
	}
}