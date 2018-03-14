package com.readboy.ssm.po;
import java.math.BigDecimal;

public class PerformanceDkKhhsMx{
	
	private String tjrq;
    private String zzbz;
    private Long gwbz;
    private String yggh;
    private String khbh;
    private String jgdm;
    private String khmc;
    private String zjhm;
    private BigDecimal dj;
    private BigDecimal gz;
    private BigDecimal zbdw;
    private String lx;
    private String zzkhr;
    private String zzjc;		//数据库无，显示要用到
	public String getTjrq() {
		if(tjrq != null  && tjrq.length() > 10){
			return tjrq.substring(0,10);
		}
		return tjrq;
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
	public String getKhbh() {
		return khbh;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public BigDecimal getZbdw() {
		return zbdw;
	}
	public String getLx() {
		return lx;
	}
	public String getZzkhr() {
		if(zzkhr != null  && zzkhr.length() > 10){
			return zzkhr.substring(0,10);
		}
		return zzkhr;
	}
	public String getZzjc() {
		return zzjc;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
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
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
	public void setZbdw(BigDecimal zbdw) {
		this.zbdw = zbdw;
	}
	public void setLx(String lx) {
		this.lx = lx;
	}
	public void setZzkhr(String zzkhr) {
		this.zzkhr = zzkhr;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
    
    
}