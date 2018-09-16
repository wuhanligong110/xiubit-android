package com.toobei.tb.entity;

import java.util.List;

import com.toobei.common.entity.BaseEntity;

public class QuestionAnswerInfoModel extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private String content; // 您最偏好的投资期限是
	private String id;
	private String index;
	private List<QuestionAnswerInfo> questionAnswerInfos;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<QuestionAnswerInfo> getQuestionAnswerInfos() {
		return questionAnswerInfos;
	}

	public void setQuestionAnswerInfos(
			List<QuestionAnswerInfo> questionAnswerInfos) {
		this.questionAnswerInfos = questionAnswerInfos;
	}

}
