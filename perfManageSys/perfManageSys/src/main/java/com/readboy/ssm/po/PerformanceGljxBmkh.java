package com.readboy.ssm.po;

import java.math.BigDecimal;

public class PerformanceGljxBmkh{
   
    private String gzrq;
    private String zzbz;
    private String zzjc;
    private Long gwbz;
    private String gwmc;
    private String yggh;
    private String ygxm;
    private String zbid;
    private String zbmc;			//指标名称
    private BigDecimal zbgz;		//指标工资
    private BigDecimal zbjg;			//指标结果
    private BigDecimal zbqz;		//指标权重
    private BigDecimal qhrjgz;			//全行人均工资
    
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
	public BigDecimal getZbjg() {
		return zbjg;
	}
	public BigDecimal getZbqz() {
		return zbqz;
	}
	public BigDecimal getQhrjgz() {
		return qhrjgz;
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
	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}
	public void setZbqz(BigDecimal zbqz) {
		this.zbqz = zbqz;
	}
	public void setQhrjgz(BigDecimal qhrjgz) {
		this.qhrjgz = qhrjgz;
	}

}