package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class ProductDetailProductInfo extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 8731736703077631425L;
	private String type;
	private String typeName;
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}