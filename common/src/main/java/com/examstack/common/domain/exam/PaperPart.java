package com.examstack.common.domain.exam;

import java.io.Serializable;
import java.util.List;

import com.examstack.common.domain.question.Question;

//对应表paperPart
public class PaperPart implements Serializable{
 /**
	 * 
	 */
private static final long serialVersionUID = 3962795212120753703L;

 private Integer id;
 private Integer questionCount;
 private Integer paperId; // 添加对paperId的引用
 private String name;
 private double pointPerQuestion;
 private String summary;
 private List<Question> questions;
 
public Integer getPaperId() {
	return paperId;
}
public void setPaperId(Integer paperId) {
	this.paperId = paperId;
}
public Integer getId() {
	return id;
}
public List<Question> getQuestions() {
	return questions;
}
public void setQuestions(List<Question> questions) {
	this.questions = questions;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getPointPerQuestion() {
	return pointPerQuestion;
}
public void setPointPerQuestion(double pointPerQuestion) {
	this.pointPerQuestion = pointPerQuestion;
}
public String getSummary() {
	return summary;
}
public void setSummary(String summary) {
	this.summary = summary;
}
 
public Integer getQuestionCount() {
	return questionCount;
}
public void setQuestionCount(Integer questionCount) {
	this.questionCount = questionCount;
}
@Override
public String toString() {
	return "PaperPart [id=" + id + ", questionCount=" + questionCount
			+ ", paperId=" + paperId + ", name=" + name + ", pointPerQuestion="
			+ pointPerQuestion + ", summary=" + summary + ", questions="
			+ questions + "]";
}


}
