package com.toobei.common.entity;


/**
 * 公司: tophlc
 * 类说明：账户 实体
 * @date 2015-10-22
 */
public class AccountDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1854143167229240600L;

	private String typeName;//类别 	账户类型(1=全部明细|2=提现|3=活动奖励|4=红包|5=其他)
	private String transDate;//发生时间
	private String transAmount;//金额(单位元)
	private String remark;//内容
	private String fee;//提现手续费

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
}