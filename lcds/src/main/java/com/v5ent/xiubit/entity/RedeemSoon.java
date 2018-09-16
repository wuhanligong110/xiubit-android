package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;
import com.v5ent.xiubit.MyApp;

/***
 * 公司: tophlc
 * 类说明：即将赎回
 * @date 2016-5-20
 */
public class RedeemSoon extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -8058846807604961114L;

//	amt	投资金额	string
//	endDate	到期时间	string
//	feeAmt	佣金	string
//	feeRate	佣金率	string
//	image	头像	string
//	mobile	手机号码	string
//	name	名称	string
//	productName	产品名称	string
//	profit	预期收益	string
//	rate	利率	string
//	startDate	开始时间	string

	private String customerId;//客户id
	private String image;//客户头像 [待确定，看图片保存的形式]
	private String mobile;//客户手机号码
	private String name;//昵称
	private String startDate;// 购买日期
	private String endDate;//赎回日期
	private String productName;///产品名称
	private String rate;//年化
	private String feeRate;//佣金率
	private String profit;//客户收益
	private String feeAmt;//我的佣金  [待确定，可能需要前端自己算]
	private String amt;//赎回额

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getImage() {
		return MyApp.getInstance().getHttpService().getImageServerBaseUrl() + image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFeeRate() {
		return feeRate;
	}

	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}

	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

}