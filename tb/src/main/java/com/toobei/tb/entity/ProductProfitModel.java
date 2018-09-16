package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class ProductProfitModel extends BaseEntity {
	/** serialVersionUID*/
	private static final long serialVersionUID = 4320889048811653573L;
	private String profit;
	private String investAmount;
	private String productId;

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
