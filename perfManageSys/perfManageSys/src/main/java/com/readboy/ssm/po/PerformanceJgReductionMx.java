package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:绩效_机构扣减明细,对应数据库表:v_wage_jgkj
 */
public class PerformanceJgReductionMx {
	
	private String gzrq;		//统计日期
	private String zzbz;		//组织标志
	private String zzjc;		//组织简称
	private int gwbz;			//岗位标志
	private String gwmc;		//岗位名称
	private String yggh;		//员工工号
	private String ygxm;		//员工姓名
	private String zbid;		//指标ID
	private String zbmc;		//指标名称
	private BigDecimal zbgz;	//指标工资
	private BigDecimal zbjg;	//指标结果
	
	public String getGzrq() {
		return gzrq;
	}
	public String getZzbz() {
		return zzbz;
	}
	public String getZzjc() {
		return zzjc;
	}
	public int getGwbz() {
		return gwbz;
	}
	public String getGwmc() {
		return gwmc;
	}
	public String getYggh() {
		return yggh;
	}
	public String getYgxm() {
		return ygxm;
	}
	public String getZbid() {
		return zbid;
	}
	public String getZbmc() {
		return zbmc;
	}
	public BigDecimal getZbgz() {
		return zbgz;
	}
	public BigDecimal getZbjg() {
		return zbjg;
	}
	public void setGzrq(String gzrq) {
		if(gzrq != null && gzrq.length() > 10){
			gzrq = gzrq.substring(0, 10);
		}
		this.gzrq = gzrq;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setGwbz(int gwbz) {
		this.gwbz = gwbz;
	}
	public void setGwmc(String gwmc) {
		this.gwmc = gwmc;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
	}
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	public void setZbmc(String zbmc) {
		this.zbmc = zbmc;
	}
	public void setZbgz(BigDecimal zbgz) {
		this.zbgz = zbgz;
	}
	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}
}
