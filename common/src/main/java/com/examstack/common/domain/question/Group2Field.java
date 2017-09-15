package com.examstack.common.domain.question;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// 主要映射表group_2_filed
public class Group2Field implements Serializable {

	private Integer groupId;
	private String groupName;
	
	private Integer fieldId;
	private String fieldName;
	
	private Integer id;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());

		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Group2Field))
			return false;
		Group2Field other = (Group2Field) obj;
		
        if(this.id == other.getId() || (this.groupId == other.getGroupId() && this.fieldId == other.getFieldId()))
        {
        	return true;
        }
		
        return false;
	}
	
	
}
