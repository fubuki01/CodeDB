package com.readboy.ssm.po;

import java.math.BigDecimal;

/*绩效管理明细，对应数据库表：erp_wage_gljxgzmx
 * */
public class PerformanceManageDetail {
	
	private String tjrq;		//统计日期
	private String zzbz;		//组织标志
	private int gwbz;			//岗位标志
	private String yggh;		//员工工号
	private int zgts;			//在岗天数
	private BigDecimal bzjx;	//标准绩效
	private BigDecimal zhjydf;	//支行经营得分
	private BigDecimal gz;		//工资
	
	public String getTjrq() {
		return tjrq;
	}
	public String getZzbz() {
		return zzbz;
	}
	public int getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public int getZgts() {
		return zgts;
	}
	public BigDecimal getBzjx() {
		return bzjx;
	}
	public BigDecimal getZhjydf() {
		return zhjydf;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setGwbz(int gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setZgts(int zgts) {
		this.zgts = zgts;
	}
	public void setBzjx(BigDecimal bzjx) {
		this.bzjx = bzjx;
	}
	public void setZhjydf(BigDecimal zhjydf) {
		this.zhjydf = zhjydf;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
	
}
