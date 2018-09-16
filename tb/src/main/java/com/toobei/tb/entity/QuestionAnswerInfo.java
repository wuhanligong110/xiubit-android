package com.toobei.tb.entity;

import com.toobei.common.entity.BaseEntity;

public class QuestionAnswerInfo extends BaseEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = -8428663685395492424L;

	private String answerContent; // 活期产品，灵活性第一
	private String answerIndex;
	private String id;

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public String getAnswerIndex() {
		return answerIndex;
	}

	public void setAnswerIndex(String answerIndex) {
		this.answerIndex = answerIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
