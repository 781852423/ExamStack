package com.examstack.common.domain.personality;

import java.io.Serializable;

public class PersonalityQuestionItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4377613815043251928L;
	private Integer questionId;
	private Integer charactorId;
	private String answer; // 答案
	private Integer score; // 这个答案占多少分？
	private Integer xuePaiId;
	
	public Integer getXuePaiId() {
		return xuePaiId;
	}
	public void setXuePaiId(Integer xuePaiId) {
		this.xuePaiId = xuePaiId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getCharactorId() {
		return charactorId;
	}
	public void setCharactorId(Integer charactorId) {
		this.charactorId = charactorId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
