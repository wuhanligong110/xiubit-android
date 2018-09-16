package com.toobei.common.entity;


/**
 * 公司: tophlc
 * 类说明：收益类别 实体
 * @date 2015-10-22
 */
public class AccountType extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1336805611961961262L;

	private String typeValue; //账户类型名称	string
	private String typeName;  //typeName	账户类型名称typeValue	账户类型(1=全部明细|2=提现|3=活动奖励|4=红包|5=其他)


	public AccountType(String typeValue, String typeName) {
		super();
		this.typeValue = typeValue;
		this.typeName = typeName;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}