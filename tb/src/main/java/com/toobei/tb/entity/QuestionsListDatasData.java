package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class QuestionsListDatasData extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private List<QuestionAnswerInfoModel> datas;

	public List<QuestionAnswerInfoModel> getDatas() {
		return datas;
	}

	public void setDatas(List<QuestionAnswerInfoModel> datas) {
		this.datas = datas;
	}

}