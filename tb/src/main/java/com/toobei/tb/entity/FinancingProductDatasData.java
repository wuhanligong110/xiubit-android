package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class FinancingProductDatasData extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = -1565102793291849552L;

	private String cateId; //分类id
	private String cateName; //热门产品"
	private String identifier;//产品标示 hot:热门产品  fund:基金产品
	private List<FinancingProductModel> datas;

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public List<FinancingProductModel> getDatas() {
		return datas;
	}

	public void setDatas(List<FinancingProductModel> datas) {
		this.datas = datas;
	}
}