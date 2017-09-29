package com.examstack.common.domain.personality;

import java.io.Serializable;

public class PersonalityCharactor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -343846741106770581L;
	private Integer id;
	private String name;
	private Integer xuepaiId;
	private String summary;
	public Integer getId() {
		return id;
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
	public Integer getXuepaiId() {
		return xuepaiId;
	}
	public void setXuepaiId(Integer xuepaiId) {
		this.xuepaiId = xuepaiId;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
