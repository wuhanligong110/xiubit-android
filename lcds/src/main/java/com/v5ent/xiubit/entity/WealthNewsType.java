package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明：财富快报类型 实体
 * @date 2015-10-22
 */
public class WealthNewsType extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8402968867180760674L;
	private String typeCode;//类别code
	private String typeName;//类别名称

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}