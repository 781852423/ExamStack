package com.examstack.common.domain.exam;

import java.io.Serializable;

public class Paper2Part implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3845477450249271003L;
	private Integer id;
	private Integer paperId;
	private Integer paperPartId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public Integer getPaperPartId() {
		return paperPartId;
	}
	public void setPaperPartId(Integer paperPartId) {
		this.paperPartId = paperPartId;
	}
	
	
}
