package com.examstack.common.domain.question;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuestionRawContent {
	private int id;
	private String questionRawContent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestionRawContent() {
		return questionRawContent;
	}
	public void setQuestionRawContent(String questionRawContent) {
		this.questionRawContent = questionRawContent;
	}
	
}
