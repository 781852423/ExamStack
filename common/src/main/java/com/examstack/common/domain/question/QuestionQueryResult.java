package com.examstack.common.domain.question;

public class QuestionQueryResult {

	private int questionId;
	private String content;
	private String answer;
	private String analysis;
	private int questionTypeId;
	private String referenceName;
	private String pointName;
	private String fieldName;
	private double questionPoint;
	private String examingPoint;
	private int knowledgePointId;
	
	private Integer parentId; // 添加题干的说明  on Sep 2, 2017
	private String parentContent;// 添加题干的说明  on Sep 2, 2017
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getParentContent() {
		return parentContent;
	}
	public void setParentContent(String parentContent) {
		this.parentContent = parentContent;
	}
	public int getKnowledgePointId() {
		return knowledgePointId;
	}
	public void setKnowledgePointId(int knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
	}
	public String getExamingPoint() {
		return examingPoint;
	}
	public void setExamingPoint(String examingPoint) {
		this.examingPoint = examingPoint;
	}
	public double getQuestionPoint() {
		return questionPoint;
	}
	public void setQuestionPoint(double questionPoint) {
		this.questionPoint = questionPoint;
	}
	public int getQuestionTypeId() {
		return questionTypeId;
	}
	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getReferenceName() {
		return referenceName;
	}
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
