package com.readboy.ssm.po;

/**
 * 地区表：对于数据库表area
 * @author:	   	LCL
 * @date: 	   	2017-10-17
 * @description:
 */
public class Area {
	private Integer id;
	private String dqdh;
	private Integer dqcs;
	private String bz;
	
	public Integer getId() {
		return id;
	}
	public String getDqdh() {
		return dqdh;
	}
	public Integer getDqcs() {
		return dqcs;
	}
	public String getBz() {
		return bz;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setDqdh(String dqdh) {
		this.dqdh = dqdh;
	}
	public void setDqcs(Integer dqcs) {
		this.dqcs = dqcs;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

}
