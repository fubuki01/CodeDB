package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 业绩工资首页，对应数据库表erp_wage_ygjx_mx
 * 2017-06-23
 */
public class PerformanceMx {
	
	private String zzbz;			//组织标识
	private String gzrq;			//工资日期
	private String zbid;			//指标ID
	private String zbmc;			//指标名称，从表erp_bas_zbk中获取
	private BigDecimal zbdj;		//指标单价
	private BigDecimal zbjg;		//指标结果
	private BigDecimal zbgz;		//指标工资
	private int gwbz;				//岗位标志
	private String yggh;			//员工工号
	private int zblb;				//指标类别
	private String khwd;			//考核维度
	private int lrbz;
	private String lrr;
	private String lrsj;
	public String getZzbz() {
		return zzbz;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public String getGzrq() {
		return gzrq;
	}
	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}
	public String getZbid() {
		return zbid;
	}
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	
	public String getZbmc() {
		return zbmc;
	}
	public void setZbmc(String zbmc) {
		this.zbmc = zbmc;
	}
	public BigDecimal getZbdj() {
		return zbdj;
	}
	public void setZbdj(BigDecimal zbdj) {
		this.zbdj = zbdj;
	}
	public BigDecimal getZbjg() {
		return zbjg;
	}
	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}
	public BigDecimal getZbgz() {
		return zbgz;
	}
	public void setZbgz(BigDecimal zbgz) {
		this.zbgz = zbgz;
	}
	public int getGwbz() {
		return gwbz;
	}
	public void setGwbz(int gwbz) {
		this.gwbz = gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public int getZblb() {
		return zblb;
	}
	public void setZblb(int zblb) {
		this.zblb = zblb;
	}
	public String getKhwd() {
		return khwd;
	}
	public void setKhwd(String khwd) {
		this.khwd = khwd;
	}
	public int getLrbz() {
		return lrbz;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getLrsj() {
		return lrsj;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	
}
