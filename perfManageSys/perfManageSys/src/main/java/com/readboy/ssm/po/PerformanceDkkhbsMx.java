package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:绩效_贷款客户包收明细，对应数据库表：erp_wage_dkkhbsmx
 */
public class PerformanceDkkhbsMx{
	
	private String tjrq;		//统计日期
	private String jgdm;		//机构代码
	private String zzbz;		//组织标志
	private String gwbz;		//岗位标志
	private String yggh;		//员工工号
	private String khbh;		//客户编号
	private String khmc;		//客户名称
	private String zjhm;		//证件号码
	private String hth;			//合同号
	private BigDecimal dkje;	//贷款金额
	private String dkzh;		//贷款账号
	private String dkfl;		//贷款分类
	private BigDecimal dj;		//单价
	private String ffrq;		//发放日期
	private String dqrq;		//到期日期
	private BigDecimal zrbl;	//责任比例
	private BigDecimal bsdj;	//笔数单价
	private BigDecimal bsdw;	//笔数单位
	private BigDecimal bsgz;	//笔数工资
	private BigDecimal eddj;	//额度单价
	private BigDecimal eddw;	//额度单位
	private BigDecimal edgz;	//额度工资
	private String zzjc;		//机构名称，该属性是显示要用，对应数据库表无

	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getTjrq() {
		return tjrq;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getZzbz() {
		return zzbz;
	}
	public String getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getHth() {
		return hth;
	}
	public BigDecimal getDkje() {
		return dkje;
	}
	public String getDkzh() {
		return dkzh;
	}
	public String getDkfl() {
		return dkfl;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public String getFfrq() {
		return ffrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public BigDecimal getZrbl() {
		return zrbl;
	}
	public BigDecimal getBsdj() {
		return bsdj;
	}
	public BigDecimal getBsdw() {
		return bsdw;
	}
	public BigDecimal getBsgz() {
		return bsgz;
	}
	public BigDecimal getEddj() {
		return eddj;
	}
	public BigDecimal getEddw() {
		return eddw;
	}
	public BigDecimal getEdgz() {
		return edgz;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setGwbz(String gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public void setDkje(BigDecimal dkje) {
		this.dkje = dkje;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public void setDkfl(String dkfl) {
		this.dkfl = dkfl;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setFfrq(String ffrq) {
		if(ffrq != null && ffrq.length() > 10){
			ffrq = ffrq.substring(0, 10);
		}
		this.ffrq = ffrq;
	}
	public void setDqrq(String dqrq) {
		if(dqrq != null && dqrq.length() > 10){
			dqrq = dqrq.substring(0, 10);
		}
		this.dqrq = dqrq;
	}
	public void setZrbl(BigDecimal zrbl) {
		this.zrbl = zrbl;
	}
	public void setBsdj(BigDecimal bsdj) {
		this.bsdj = bsdj;
	}
	public void setBsdw(BigDecimal bsdw) {
		this.bsdw = bsdw;
	}
	public void setBsgz(BigDecimal bsgz) {
		this.bsgz = bsgz;
	}
	public void setEddj(BigDecimal eddj) {
		this.eddj = eddj;
	}
	public void setEddw(BigDecimal eddw) {
		this.eddw = eddw;
	}
	public void setEdgz(BigDecimal edgz) {
		this.edgz = edgz;
	}
	
}
