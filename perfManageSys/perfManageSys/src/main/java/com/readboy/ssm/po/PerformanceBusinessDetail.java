package com.readboy.ssm.po;

import java.math.BigDecimal;

/*
 * 工资业务量明细,对应数据库表：v_wage_ywlmx
 * */
public class PerformanceBusinessDetail {
	private String tjrq;		//统计日期
	private String ywjgdm;		//业务机构代码
	private String ywjgmc;		//业务机构名称
	private String ghgh;		//员工工号
	private BigDecimal jybs;	//交易笔数
	private BigDecimal sjjybs;	//实际交易笔数
	private BigDecimal xjjyje;	//现金交易金额
	private BigDecimal sjxjjyje;//实际现金交易金额
	private String ywlx;		//业务类型 
	private BigDecimal ywlgz;	//业务量工资
	private BigDecimal xjllgz;	//现金流量工资
	public String getTjrq() {
		return tjrq;
	}
	public String getYwjgdm() {
		return ywjgdm;
	}
	public String getYwjgmc() {
		return ywjgmc;
	}
	public String getGhgh() {
		return ghgh;
	}
	public BigDecimal getJybs() {
		return jybs;
	}
	public BigDecimal getSjjybs() {
		return sjjybs;
	}
	public BigDecimal getXjjyje() {
		return xjjyje;
	}
	public BigDecimal getSjxjjyje() {
		return sjxjjyje;
	}
	public String getYwlx() {
		return ywlx;
	}
	public BigDecimal getYwlgz() {
		return ywlgz;
	}
	public BigDecimal getXjllgz() {
		return xjllgz;
	}
	public void setTjrq(String tjrq) {
		if(tjrq != null && tjrq.length() > 10){
			tjrq = tjrq.substring(0, 10);
		}
		this.tjrq = tjrq;
	}
	public void setYwjgdm(String ywjgdm) {
		this.ywjgdm = ywjgdm;
	}
	public void setYwjgmc(String ywjgmc) {
		this.ywjgmc = ywjgmc;
	}
	public void setGhgh(String ghgh) {
		this.ghgh = ghgh;
	}
	public void setJybs(BigDecimal jybs) {
		this.jybs = jybs;
	}
	public void setSjjybs(BigDecimal sjjybs) {
		this.sjjybs = sjjybs;
	}
	public void setXjjyje(BigDecimal xjjyje) {
		this.xjjyje = xjjyje;
	}
	public void setSjxjjyje(BigDecimal sjxjjyje) {
		this.sjxjjyje = sjxjjyje;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public void setYwlgz(BigDecimal ywlgz) {
		this.ywlgz = ywlgz;
	}
	public void setXjllgz(BigDecimal xjllgz) {
		this.xjllgz = xjllgz;
	}
}
