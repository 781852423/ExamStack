package com.examstack.common.domain.user;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.star.util.DateTime;

/*
 * 用@XMLRootElement注释，表示该类可以支持于xml元素映射，且为xml元素的根元素
 * 例如：
 *  @XmlRootElement // 必须要标明这个元素  
	public class Boy {     
	    String name = "CY";  
	    ---getter and setter function
	}  
   
   ===>可以经过输出成 <?xml version="1.0" encoding="UTF-8" standalone="yes"?><boy><name>CY</name></boy> 
    反之，这样的字符串：<boy><name>David</name></boy> 也可以被转换成上述对象
 */
@XmlRootElement
public class User implements Serializable {
	private static final long serialVersionUID = 2866441053387084227L;
	private int userId;
	private String userName;
	private String password;
	private String trueName;
	private String nationalId;
	private String email;
	private String phoneNum;
	private Date createTime;
	private int createBy;
	private int fieldId;
	private String fieldName;
	private Date lastLoginTime;
	private Date loginTime;
	private String company;
	private boolean enabled;
	private String roles;
	private String groups;
	
	private int depId;
	private String department;
	private Date expiredTime;
	
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public int getDepId() {
		return depId;
	}
	public void setDepId(int depId) {
		this.depId = depId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCreateBy() {
		return createBy;
	}
	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", trueName=" + trueName
				+ ", nationalId=" + nationalId + ", email=" + email + ", phoneNum=" + phoneNum + ", createTime="
				+ createTime + ", createBy=" + createBy + ", fieldId=" + fieldId + ", fieldName=" + fieldName
				+ ", lastLoginTime=" + lastLoginTime + ", loginTime=" + loginTime + ", company=" + company
				+ ", enabled=" + enabled + ", roles=" + roles + ", groups=" + groups + ", depId=" + depId
				+ ", department=" + department + ", expiredTime=" + expiredTime + "]";
	}
	
	
}
