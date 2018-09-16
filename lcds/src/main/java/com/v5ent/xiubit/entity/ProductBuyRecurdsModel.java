package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc 类说明：投资活动 实体
 * 
 * @date 2015-10-20
 */
public class ProductBuyRecurdsModel extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;
	private String createDate; //购买时间
	private String investAmount; //投资金额
	private String mobile; //购买人手机
	private String productId; //产品id
	private String userId; //用户id
	private String userName; //用户姓名

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(String investAmount) {
		this.investAmount = investAmount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}