package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:表外不良清收明细，对应数据库表：v_wage_blqsmx
 */
public class PerformanceBlqsMx {
	
	private String gzrq;		//工资日期
	private String jgdm;		//机构代码
	private String jgmc;		//机构名称
	private String dkzh;		//贷款账号
	private String khmc;		//客户名称
	private int shlx;			//收回类型
	private String shrq;		//收回日期
	private BigDecimal shje;	//收回金额
	private String shrgh;		//收回人工号
	private BigDecimal zbgz;	//指标工资
	private BigDecimal zbdj;	//指标单价
	private BigDecimal zbdw;	//指标单位
	public String getGzrq() {
		return gzrq;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getJgmc() {
		return jgmc;
	}
	public String getDkzh() {
		return dkzh;
	}
	public String getKhmc() {
		return khmc;
	}
	public int getShlx() {
		return shlx;
	}
	public String getShrq() {
		return shrq;
	}
	public BigDecimal getShje() {
		return shje;
	}
	public String getShrgh() {
		return shrgh;
	}
	public BigDecimal getZbgz() {
		return zbgz;
	}
	public BigDecimal getZbdj() {
		return zbdj;
	}
	public BigDecimal getZbdw() {
		return zbdw;
	}
	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setShlx(int shlx) {
		this.shlx = shlx;
	}
	public void setShrq(String shrq) {
		if(shrq != null && shrq.length() > 10){
			shrq = shrq.substring(0, 10);
		}
		this.shrq = shrq;
	}
	public void setShje(BigDecimal shje) {
		this.shje = shje;
	}
	public void setShrgh(String shrgh) {
		this.shrgh = shrgh;
	}
	public void setZbgz(BigDecimal zbgz) {
		this.zbgz = zbgz;
	}
	public void setZbdj(BigDecimal zbdj) {
		this.zbdj = zbdj;
	}
	public void setZbdw(BigDecimal zbdw) {
		this.zbdw = zbdw;
	}
}
