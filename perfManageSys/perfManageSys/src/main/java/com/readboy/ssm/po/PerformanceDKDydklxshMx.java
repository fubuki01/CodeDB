package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-9-2
 * @description:对应数据库表：v_wage_dndqdkshl
 */
public class PerformanceDKDydklxshMx{
  
    private String tjrq;
    private String jgdm;
    private String zzbz;
    private Long gwbz;
    private String yggh;
    private BigDecimal shlx;
    private BigDecimal zbdj;
    private BigDecimal zbdw;
    private BigDecimal zbgz;
    private String dkzh;
    private String hth;
    private String zhmc;
    private String zjhm;
    private BigDecimal dkje;
    private BigDecimal dkye;
    private String sxr;
    private BigDecimal fcbl;
    private String zzjc;		//
    
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
	public Long getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public BigDecimal getShlx() {
		return shlx;
	}
	public BigDecimal getZbdj() {
		return zbdj;
	}
	public BigDecimal getZbdw() {
		return zbdw;
	}
	public BigDecimal getZbgz() {
		return zbgz;
	}
	public String getDkzh() {
		return dkzh;
	}
	public String getHth() {
		return hth;
	}
	public String getZhmc() {
		return zhmc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public BigDecimal getDkje() {
		return dkje;
	}
	public BigDecimal getDkye() {
		return dkye;
	}
	public String getSxr() {
		if(sxr != null && sxr.length() > 10){
			sxr = sxr.substring(0, 10);
		}
		return sxr;
	}
	public BigDecimal getFcbl() {
		return fcbl;
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
	public void setGwbz(Long gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setShlx(BigDecimal shlx) {
		this.shlx = shlx;
	}
	public void setZbdj(BigDecimal zbdj) {
		this.zbdj = zbdj;
	}
	public void setZbdw(BigDecimal zbdw) {
		this.zbdw = zbdw;
	}
	public void setZbgz(BigDecimal zbgz) {
		this.zbgz = zbgz;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public void setZhmc(String zhmc) {
		this.zhmc = zhmc;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setDkje(BigDecimal dkje) {
		this.dkje = dkje;
	}
	public void setDkye(BigDecimal dkye) {
		this.dkye = dkye;
	}
	public void setSxr(String sxr) {
		this.sxr = sxr;
	}
	public void setFcbl(BigDecimal fcbl) {
		this.fcbl = fcbl;
	}    
}