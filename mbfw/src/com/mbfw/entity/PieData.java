package com.mbfw.entity;

import java.math.BigDecimal;

/**
 * 饼状图数据封装类
 * @author:	   	LCL
 * @date: 	   	2017-10-10
 * @description:
 */
public class PieData{
	
	private String name;
	private BigDecimal share;
	
	public PieData(String name,BigDecimal share){
		this.name = name;
		this.share = share;
	}
	public String getName() {
		return name;
	}
	public BigDecimal getShare() {
		return share;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setShare(BigDecimal share) {
		this.share = share;
	}
}