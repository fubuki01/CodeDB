package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-9-2
 * @description:随意数据库表:v_wage_wdrjgz
 */
public class PerformanceGljxWdrjgz{
    private String gzrq;
    private String zzbz;
    private String zzjc;
    private Long gwbz;
    private String gwmc;
    private String yggh;
    private String ygxm;
    private String zbid;
    private String zbmc;
    private BigDecimal zbgz;
    private BigDecimal wdrjgz;
    private BigDecimal khxs;
    
	public String getGzrq() {
		if(gzrq != null && gzrq.length() > 10){
			return gzrq.substring(0,10);
		}
		return gzrq;
	}
	public String getZzbz() {
		return zzbz;
	}
	public String getZzjc() {
		return zzjc;
	}
	public Long getGwbz() {
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
	public BigDecimal getWdrjgz() {
		return wdrjgz;
	}
	public BigDecimal getKhxs() {
		return khxs;
	}
	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setGwbz(Long gwbz) {
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
	public void setWdrjgz(BigDecimal wdrjgz) {
		this.wdrjgz = wdrjgz;
	}
	public void setKhxs(BigDecimal khxs) {
		this.khxs = khxs;
	}
    
    
}