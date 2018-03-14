package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-7-25
 * @description:员工岗位绩效工资明细，对应数据库表：ERP_WAGE_GW
 */
public class PerformanceGwjxgzMx {
	
	private String zzbz;			//组织标志
	private String khwd;			//考核维度
	private String gzyf;			//工资月份
	private BigDecimal phjfkjg;		//平衡计分考核结构
	private BigDecimal phjfkgz;		//平衡计分考核工资
	private BigDecimal gxljg;		//贡献率结果
	private BigDecimal gxlgz;		//贡献率工资
	private BigDecimal aljcjg;		//按量计酬考核结果
	private BigDecimal aljcgz;		//按量计酬考核工资
	private BigDecimal gzhj;		//工资合计
	private int lrbz;				//录入标志
	private String lrsj;			//录入时间
	private String lrr;				//录入人
	private BigDecimal qhkhjxzgz;	//机构、部门考核绩效总工资
	private BigDecimal qhzrs;		//机构部门总人数
	private BigDecimal rjjxgz;		//人均绩效工资
	private BigDecimal gwrs;		//岗位人数
	private BigDecimal gwyfjxgz;	//岗位应发绩效工资工资
	private long gwbz;				//岗位标志
	private String yggh;			//员工工号
	private BigDecimal jbjxgz;		//基本绩效工资
	private BigDecimal grxs;		//个人系数
	private BigDecimal kjgz;		//扣减工资
	private BigDecimal tzgz;		//调增工资
	
	public String getZzbz() {
		return zzbz;
	}
	public String getKhwd() {
		return khwd;
	}
	public String getGzyf() {
		return gzyf;
	}
	public BigDecimal getPhjfkjg() {
		return phjfkjg;
	}
	public BigDecimal getPhjfkgz() {
		return phjfkgz;
	}
	public BigDecimal getGxljg() {
		return gxljg;
	}
	public BigDecimal getGxlgz() {
		return gxlgz;
	}
	public BigDecimal getAljcjg() {
		return aljcjg;
	}
	public BigDecimal getAljcgz() {
		return aljcgz;
	}
	public BigDecimal getGzhj() {
		return gzhj;
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
	public BigDecimal getJbjxgz() {
		return jbjxgz;
	}
	public BigDecimal getGrxs() {
		return grxs;
	}
	public BigDecimal getKjgz() {
		return kjgz;
	}
	public BigDecimal getTzgz() {
		return tzgz;
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
	public void setPhjfkjg(BigDecimal phjfkjg) {
		this.phjfkjg = phjfkjg;
	}
	public void setPhjfkgz(BigDecimal phjfkgz) {
		this.phjfkgz = phjfkgz;
	}
	public void setGxljg(BigDecimal gxljg) {
		this.gxljg = gxljg;
	}
	public void setGxlgz(BigDecimal gxlgz) {
		this.gxlgz = gxlgz;
	}
	public void setAljcjg(BigDecimal aljcjg) {
		this.aljcjg = aljcjg;
	}
	public void setAljcgz(BigDecimal aljcgz) {
		this.aljcgz = aljcgz;
	}
	public void setGzhj(BigDecimal gzhj) {
		this.gzhj = gzhj;
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
	public void setJbjxgz(BigDecimal jbjxgz) {
		this.jbjxgz = jbjxgz;
	}
	public void setGrxs(BigDecimal grxs) {
		this.grxs = grxs;
	}
	public void setKjgz(BigDecimal kjgz) {
		this.kjgz = kjgz;
	}
	public void setTzgz(BigDecimal tzgz) {
		this.tzgz = tzgz;
	}
	
}
