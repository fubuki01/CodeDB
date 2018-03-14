package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-7-25
 * @description:绩效_按量计酬工资明细，对应数据库表:ERP_WAGE_GW_ALJC
 */
public class PerformanceAljcgzMx {
	
	private String zzbz;			//组织标志
	private String khwd;			//考核维度
	private String gzyf;			//工资月份
	private String zbid;			//指标ID
	private BigDecimal zbdj;		//指标单价
	private BigDecimal zbjg;		//指标结果
	private BigDecimal zbgz;		//指标工资
	private int lrbz;				//录入标志
	private String lrsj;			//录入时间
	private String lrr;				//录入人
	private long gwbz;				//岗位标志
	private String yggh;			//员工工号
	private BigDecimal zbrw;		//指标任务
	private BigDecimal zbdw;		//指标单位
	private BigDecimal tjxs;		//调节系数
	private BigDecimal rwwdj;		//任务外单价
	
	public String getZzbz() {
		return zzbz;
	}
	public String getKhwd() {
		return khwd;
	}
	public String getGzyf() {
		return gzyf;
	}
	public String getZbid() {
		return zbid;
	}
	public BigDecimal getZbdj() {
		return zbdj;
	}
	public BigDecimal getZbjg() {
		return zbjg;
	}
	public BigDecimal getZbgz() {
		return zbgz;
	}
	public int getLrbz() {
		return lrbz;
	}
	public String getLrsj() {
		return lrsj;
	}
	public String getLrr() {
		return lrr;
	}
	public long getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public BigDecimal getZbrw() {
		return zbrw;
	}
	public BigDecimal getZbdw() {
		return zbdw;
	}
	public BigDecimal getTjxs() {
		return tjxs;
	}
	public BigDecimal getRwwdj() {
		return rwwdj;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setKhwd(String khwd) {
		this.khwd = khwd;
	}
	public void setGzyf(String gzyf) {
		this.gzyf = gzyf;
	}
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	public void setZbdj(BigDecimal zbdj) {
		this.zbdj = zbdj;
	}
	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}
	public void setZbgz(BigDecimal zbgz) {
		this.zbgz = zbgz;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public void setGwbz(long gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setZbrw(BigDecimal zbrw) {
		this.zbrw = zbrw;
	}
	public void setZbdw(BigDecimal zbdw) {
		this.zbdw = zbdw;
	}
	public void setTjxs(BigDecimal tjxs) {
		this.tjxs = tjxs;
	}
	public void setRwwdj(BigDecimal rwwdj) {
		this.rwwdj = rwwdj;
	}
	
}
