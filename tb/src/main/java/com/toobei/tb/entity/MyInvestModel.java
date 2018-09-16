package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class MyInvestModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String investAmount;// 投资金额(元)
	private String productType; // 类型id；固定收益产品2002
	private String productTypeName; // 收益类型名称
	private String profit; // 累计收益
	private String openH5LinkUrl;

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getOpenH5LinkUrl() {
		return openH5LinkUrl;
	}

	public void setOpenH5LinkUrl(String openH5LinkUrl) {
		this.openH5LinkUrl = openH5LinkUrl;
	}
}