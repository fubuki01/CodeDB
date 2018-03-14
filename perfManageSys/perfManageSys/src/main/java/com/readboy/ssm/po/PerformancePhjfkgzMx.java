package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-7-25
 * @description:绩效_平衡积分卡工资明细，对应数据库表：ERP_WAGE_GW_PHJFK
 */
public class PerformancePhjfkgzMx {
	
	private String zzbz;			//组织标志
	private String khwd;			//考核维度
	private String gzyf;			//工资月份
	private String zbid;			//指标ID
	private BigDecimal zbrw;		//指标任务
	private BigDecimal zbfz;		//指标分值
	private BigDecimal zbjg;		//指标结果
	private BigDecimal zbdf;		//指标得分
	private BigDecimal zbgz;		//指标工资
	private int lrbz;				//录入标志
	private String lrsj;			//录入时间
	private String lrr;				//录入人
	private BigDecimal qhkhjxzgz;	//机构、部门考核绩效总工资
	private BigDecimal qhzrs;		//机构部门总人数
	private BigDecimal rjjxgz;		//人均绩效工资
	private BigDecimal qgwjxkhzgz;	//全岗位绩效考核总工资
	private BigDecimal gwrs;		//岗位人数
	private BigDecimal gwyfjxgz;	//岗位应发绩效工资工资
	private long gwbz;				//岗位标志
	private String yggh;			//员工工号
	private BigDecimal grxs;		//个人系数
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
	public BigDecimal getZbrw() {
		return zbrw;
	}
	public BigDecimal getZbfz() {
		return zbfz;
	}
	public BigDecimal getZbjg() {
		return zbjg;
	}
	public BigDecimal getZbdf() {
		return zbdf;
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
	public BigDecimal getQhkhjxzgz() {
		return qhkhjxzgz;
	}
	public BigDecimal getQhzrs() {
		return qhzrs;
	}
	public BigDecimal getRjjxgz() {
		return rjjxgz;
	}
	public BigDecimal getQgwjxkhzgz() {
		return qgwjxkhzgz;
	}
	public BigDecimal getGwrs() {
		return gwrs;
	}
	public BigDecimal getGwyfjxgz() {
		return gwyfjxgz;
	}
	public long getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public BigDecimal getGrxs() {
		return grxs;
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
	public void setZbrw(BigDecimal zbrw) {
		this.zbrw = zbrw;
	}
	public void setZbfz(BigDecimal zbfz) {
		this.zbfz = zbfz;
	}
	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}
	public void setZbdf(BigDecimal zbdf) {
		this.zbdf = zbdf;
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
	public void setQhkhjxzgz(BigDecimal qhkhjxzgz) {
		this.qhkhjxzgz = qhkhjxzgz;
	}
	public void setQhzrs(BigDecimal qhzrs) {
		this.qhzrs = qhzrs;
	}
	public void setRjjxgz(BigDecimal rjjxgz) {
		this.rjjxgz = rjjxgz;
	}
	public void setQgwjxkhzgz(BigDecimal qgwjxkhzgz) {
		this.qgwjxkhzgz = qgwjxkhzgz;
	}
	public void setGwrs(BigDecimal gwrs) {
		this.gwrs = gwrs;
	}
	public void setGwyfjxgz(BigDecimal gwyfjxgz) {
		this.gwyfjxgz = gwyfjxgz;
	}
	public void setGwbz(long gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setGrxs(BigDecimal grxs) {
		this.grxs = grxs;
	}
	
}
