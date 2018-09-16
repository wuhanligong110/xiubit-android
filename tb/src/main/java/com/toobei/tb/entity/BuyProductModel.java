package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class BuyProductModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private String endTime;// 回款时间
	private String investAmount;// 购买金额
	private String investId;// 投资记录ID
	private String startTime;// 计息时间
	private String transferTime; // 可转让时间（暂时为空）
	private String returnUrl;//购买成功返回URl（可以为空）
	private String productInvestType;//产品类型（活期产品、固定产品……）
	private String openH5LinkUrl;

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getInvestId() {
		return investId;
	}

	public void setInvestId(String investId) {
		this.investId = investId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getProductInvestType() {
		return productInvestType;
	}

	public void setProductInvestType(String productInvestType) {
		this.productInvestType = productInvestType;
	}

	public String getOpenH5LinkUrl() {
		return openH5LinkUrl;
	}

	public void setOpenH5LinkUrl(String openH5LinkUrl) {
		this.openH5LinkUrl = openH5LinkUrl;
	}
}