package com.examstack.common.domain.personality;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class PersonalityScore implements Serializable,Comparable<PersonalityScore>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -679391215492956874L;
	private Integer Id; // 表示et_charactor中的ID，主键
	private String name;
	private Integer xuepaiId;
	private Integer danxiangScore;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
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
	public Integer getDanxiangScore() {
		return danxiangScore;
	}
	public void setDanxiangScore(Integer danxiangScore) {
		this.danxiangScore = danxiangScore;
	}
	/*
	 * 采用倒序排序，分数高的排前面
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PersonalityScore o) {
		// TODO Auto-generated method stub
		if(danxiangScore < o.danxiangScore)
		{
			return 1;
		}else if(danxiangScore > o.danxiangScore)
		{
			return -1;
		}
		
		return 0;
	}
	
	
}
