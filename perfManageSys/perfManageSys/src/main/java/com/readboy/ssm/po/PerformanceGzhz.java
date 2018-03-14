package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * @author:	   	LCL
 * @date: 	   	2017-7-25
 * @description:绩效_工资汇总表，对应数据库表 :V_WAGE_HZ
 */
public class PerformanceGzhz {
	
	private String gzyf;			//工资月份
	private String yggh;			//员工工号
	private BigDecimal gzhj;		//工资合计
	private int qhpm;				//全行排名
	public String getGzyf() {
		return gzyf;
	}
	public String getYggh() {
		return yggh;
	}
	public BigDecimal getGzhj() {
		return gzhj;
	}
	public int getQhpm() {
		return qhpm;
	}
	public void setGzyf(String gzyf) {
		this.gzyf = gzyf;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setGzhj(BigDecimal gzhj) {
		this.gzhj = gzhj;
	}
	public void setQhpm(int qhpm) {
		this.qhpm = qhpm;
	}
	
}
