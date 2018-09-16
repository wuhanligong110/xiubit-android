package com.v5ent.xiubit.entity;

import com.toobei.common.entity.BaseEntity;

/**
 * 公司: tophlc
 * 类说明： 佣金计算
 * @date 2015-10-20
 */
public class CaclulatorDetail extends BaseEntity {

	/** serialVersionUID*/
	private static final long serialVersionUID = 1274273290916744863L;
	private String profiltType;//产品id
	private String profiltName;//产品name
	private String profiltValue;//
	private String isEdit;// 是否可编辑



	public String getProfiltType() {
		return profiltType;
	}

	public void setProfiltType(String profiltType) {
		this.profiltType = profiltType;
	}

	public String getProfiltName() {
		return profiltName;
	}

	public void setProfiltName(String profiltName) {
		this.profiltName = profiltName;
	}

	public String getProfiltValue() {
		return profiltValue;
	}

	public void setProfiltValue(String profiltValue) {
		this.profiltValue = profiltValue;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}
}