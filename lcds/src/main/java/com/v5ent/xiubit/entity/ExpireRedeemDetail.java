package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：客户---即将赎回
 * @date 2015-10-20
 */
public class ExpireRedeemDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -5687104820231903730L;
	private String customerId;//         555252312sdf”,//客户id
	private String customerImg;//        "com/aa/bb.jpg”,//客户头像 [待确定，看图片保存的形式]
	private String phone;//              "13312345611"  //电话    [待确定，需求文档不明确]
	private String customerName;//       ”张三”,//昵称
	private String buyDate;//            ”2015-09-30 9:30”,//购买日期
	private String nearEndDate;//        ”2015-09-30”, //赎回日期1已结束
	private String productName;//        ”天天牛90”, //产品名称
	private String fixRate;//            ”8.5”, //年化
	private String commissionRate;//     ”1.5”, //佣金率
	private String earnings;//          ”800”, //客户端收益
	private String commission;//      ”500”, //我的佣金  [待确定，可能需要前端自己算]
	private String expiRedeemMoney;//      ”8000”, //赎回额

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerImg() {
		return customerImg;
	}

	public void setCustomerImg(String customerImg) {
		this.customerImg = customerImg;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getNearEndDate() {
		return nearEndDate;
	}

	public void setNearEndDate(String nearEndDate) {
		this.nearEndDate = nearEndDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFixRate() {
		return fixRate;
	}

	public void setFixRate(String fixRate) {
		this.fixRate = fixRate;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getExpiRedeemMoney() {
		return expiRedeemMoney;
	}

	public void setExpiRedeemMoney(String expiRedeemMoney) {
		this.expiRedeemMoney = expiRedeemMoney;
	}

}