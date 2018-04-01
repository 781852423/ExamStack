package com.examstack.common.domain.exam;

import java.io.Serializable;
import java.util.Date;
import com.sun.star.lib.uno.environments.java.java_environment;


public class User2ExamMap implements Serializable{

	@Override
	public String toString() {
		return "User2ExamMap [userId=" + userId + ", groupId=" + groupId + ", expiredTime=" + expiredTime + ", examId="
				+ examId + ", examName=" + examName + ", userName=" + userName + "]";
	}
	private Integer userId;
	private Integer groupId;
	private  Date expiredTime;
	private Integer examId;
	private String examName;
	public String getExamName() {
		return examName;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	private String userName;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getExamId() {
		return examId;
	}
	public void setExamId(Integer examId) {
		this.examId = examId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
