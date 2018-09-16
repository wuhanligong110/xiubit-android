package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class RedPaperByProductDatasData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private List<RedPacketModel> datas;

	public List<RedPacketModel> getDatas() {
		return datas;
	}

	public void setDatas(List<RedPacketModel> datas) {
		this.datas = datas;
	}

}