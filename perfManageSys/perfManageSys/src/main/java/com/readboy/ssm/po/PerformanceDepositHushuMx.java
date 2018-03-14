package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 业绩工资存款户数工资明细，对应数据库表erp_wage_ckkhhsmx
 * 2017-06-23
 */
public class PerformanceDepositHushuMx {
	
	private String tjrq;			//统计日期
	private String jgdm;			//机构代码
	private String zzbs;			//组织标识
	private int gwbz;				//岗位标志
	private String yggh;			//员工工号
	private String khbh;			//客户编号，存款账号
	private String khmc;			//开户名称，账户名称
	private String zzkhrq;			//最早开户日期
	private String zjhm;			//证件号码
	private String lx;				//类型（存量，增量）
	private BigDecimal dj;			//单价
	private BigDecimal gz;			//工资
	private BigDecimal ljgz;		//累计工资
	private String ghksrq;			//管户开始日期
	private String ghjsrq;			//管户结束日期
	private String zzjc;			//机构名称，该属性是显示要用，对应数据库表无
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getTjrq() {
		return tjrq;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public String getJgdm() {
		return jgdm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public String getZzbs() {
		return zzbs;
	}
	public void setZzbs(String zzbs) {
		this.zzbs = zzbs;
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
	public String getKhbh() {
		return khbh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getZzkhrq() {
		return zzkhrq;
	}
	public void setZzkhrq(String zzkhrq) {
		if(zzkhrq != null && zzkhrq.length() > 10){
			zzkhrq = zzkhrq.substring(0, 10);
		}
		this.zzkhrq = zzkhrq;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getLx() {
		return lx;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
	public BigDecimal getLjgz() {
		return ljgz;
	}
	public void setLjgz(BigDecimal ljgz) {
		this.ljgz = ljgz;
	}
	public String getGhksrq() {
		return ghksrq;
	}
	public void setGhksrq(String ghksrq) {
		if(ghksrq != null && ghksrq.length() > 10){
			ghksrq = ghksrq.substring(0, 10);
		}
		this.ghksrq = ghksrq;
	}
	public String getGhjsrq() {
		return ghjsrq;
	}
	public void setGhjsrq(String ghjsrq) {
		if(ghjsrq != null && ghjsrq.length() > 10){
			ghjsrq = ghjsrq.substring(0, 10);
		}
		this.ghjsrq = ghjsrq;
	}
	
}
