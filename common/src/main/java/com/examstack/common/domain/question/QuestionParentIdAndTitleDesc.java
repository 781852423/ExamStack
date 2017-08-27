package com.examstack.common.domain.question;

import java.io.Serializable;

public class QuestionParentIdAndTitleDesc implements Serializable{

	private Integer parentId;
	private String titleDesc;
	
	public QuestionParentIdAndTitleDesc(){}

	@Override
	public String toString() {
		return "QuestionParentIdAndTitleDesc [parentId=" + parentId + ", titleDesc=" + titleDesc + "]";
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	
	
}
