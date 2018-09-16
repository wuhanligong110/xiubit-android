package com.toobei.common.entity;


public class WithdrawSummary extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 2246844049577700865L;

	private String outTotalAmount;// 累计提现金额
	private String outTotalFee;// 累计提现手续费
	private String outingAmount;// 提现中金额
	private String outingFee;// 提现中手续费

	public String getOutTotalAmount() {
		return outTotalAmount;
	}

	public void setOutTotalAmount(String outTotalAmount) {
		this.outTotalAmount = outTotalAmount;
	}

	public String getOutTotalFee() {
		return outTotalFee;
	}

	public void setOutTotalFee(String outTotalFee) {
		this.outTotalFee = outTotalFee;
	}

	public String getOutingAmount() {
		return outingAmount;
	}

	public void setOutingAmount(String outingAmount) {
		this.outingAmount = outingAmount;
	}

	public String getOutingFee() {
		return outingFee;
	}

	public void setOutingFee(String outingFee) {
		this.outingFee = outingFee;
	}

}
