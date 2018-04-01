package com.examstack.common.domain.question;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Field {

	private Integer fieldId;
	private String fieldName;
	private String memo;
	private boolean state;
	private boolean removeable;
	private Date expiredTime;
	
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public boolean isRemoveable() {
		return removeable;
	}
	public void setRemoveable(boolean removeable) {
		this.removeable = removeable;
	}
	public Integer getFieldId() {
		return fieldId;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Field [fieldId=" + fieldId + ", fieldName=" + fieldName + ", memo=" + memo + "]";
	}
	
	
}
