package com.examstack.common.domain.training;

import java.io.Serializable;

public class User2TrainingAuthMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6479906620173386734L;
	private Integer id;
	private Integer userId;
	private Integer trainingId;
	private Boolean isAllowWatch;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}
	public Boolean getIsAllowWatch() {
		return isAllowWatch;
	}
	public void setIsAllowWatch(Boolean isAllowWatch) {
		this.isAllowWatch = isAllowWatch;
	}
	
}
