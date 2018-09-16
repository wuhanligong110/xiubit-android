package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：收益实体
 * @date 2015-10-22
 */
public class IncomeDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -3745359143666133243L;

	private String profitType;//收益类别
	private String time;//发生时间
	private String amt;//金额(单位元)
	private String content;//内容

	public String getProfitType() {
		return profitType;
	}

	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}