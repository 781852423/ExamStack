package com.examstack.common.domain.question;

import java.io.Serializable;

public class charactorType implements Serializable{

	private Integer id; // charactor2question table column id
	private Integer charactorTypeId;
	private String  name;
	private Integer xuepai;
	private Integer questionId; // 主要是为了映射到合适的question
	
	
	public Integer getCharactorTypeId() {
		return charactorTypeId;
	}
	public void setCharactorTypeId(Integer charactorTypeId) {
		this.charactorTypeId = charactorTypeId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

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
	public Integer getXuepai() {
		return xuepai;
	}
	public void setXuepai(Integer xuepai) {
		this.xuepai = xuepai;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((charactorTypeId == null) ? 0 : charactorTypeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((xuepai == null) ? 0 : xuepai.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof charactorType))
			return false;
		charactorType other = (charactorType) obj;
		if (charactorTypeId == null) {
			if (other.charactorTypeId != null)
				return false;
		} else if (!charactorTypeId.equals(other.charactorTypeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (xuepai == null) {
			if (other.xuepai != null)
				return false;
		} else if (!xuepai.equals(other.xuepai))
			return false;
		return true;
	}
	
}
