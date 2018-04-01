package com.examstack.common.domain.practice;

public class TypeAnalysis {
	private int questionTypeId;
	private String questionTypeName;
	private int restAmount;
	private int rightAmount;
	private int wrongAmount;
	private int favoriteAmount;
    private int amount;
    
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public int getFavoriteAmount() {
		return favoriteAmount;
	}

	public void setFavoriteAmount(int favoriteAmount) {
		this.favoriteAmount = favoriteAmount;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public String getQuestionTypeName() {
		return questionTypeName;
	}

	public void setQuestionTypeName(String questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public int getRestAmount() {
		return restAmount;
	}

	public void setRestAmount(int restAmount) {
		this.restAmount = restAmount;
	}

	public int getRightAmount() {
		return rightAmount;
	}

	public void setRightAmount(int rightAmount) {
		this.rightAmount = rightAmount;
	}

	public int getWrongAmount() {
		return wrongAmount;
	}

	public void setWrongAmount(int wrongAmount) {
		this.wrongAmount = wrongAmount;
	}
}
