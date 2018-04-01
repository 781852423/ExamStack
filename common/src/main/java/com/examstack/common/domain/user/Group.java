package com.examstack.common.domain.user;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Group implements Serializable {

	private static final long serialVersionUID = -166573023634513538L;
	private int groupId;
	private String groupName;
	private int userId;
	private Date expiredTime;
	private boolean defaultt;
	public boolean isDefaultt() {
		return defaultt;
	}
	public void setDefaultt(boolean defaultt) {
		this.defaultt = defaultt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", groupName=" + groupName + ", userId=" + userId + ", expiredTime="
				+ expiredTime + ", defaultt=" + defaultt + "]";
	}
	
}
