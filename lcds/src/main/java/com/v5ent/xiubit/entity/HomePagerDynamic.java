package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：动态  最新消息
 * @date 2015-10-22
 */
public class HomePagerDynamic extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -7891235642921721388L;

	private String time;//发生时间
	private String content;//内容
	private String customertype;//客户类别：1-客户  2-团队
	private String optype;//操作类别：1-注册  2-投资  3-销售  4-赎回
	private String userId;//用户id，仅 团队  销售 情况下该字段对应理财师id,其他情况均为客户id
	private String readFlag;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCustomertype() {
		return customertype;
	}

	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public boolean isReaded() {
		return !(readFlag != null && readFlag.equals("false"));
	}

}