package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class MyAssetModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String totalAmount; // 总资产(元)
	private String accountBalance; // 账户余额
	private String currentAmount; // 活期产品总额
	private String fixedAmount;// 固定收益产品总额
	private String floatAmount;// 浮动收益产品总额

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getFixedAmount() {
		return fixedAmount;
	}

	public void setFixedAmount(String fixedAmount) {
		this.fixedAmount = fixedAmount;
	}

	public String getFloatAmount() {
		return floatAmount;
	}

	public void setFloatAmount(String floatAmount) {
		this.floatAmount = floatAmount;
	}

}