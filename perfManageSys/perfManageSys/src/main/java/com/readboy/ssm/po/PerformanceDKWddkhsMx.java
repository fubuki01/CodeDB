package com.readboy.ssm.po;

import java.math.BigDecimal;

public class PerformanceDKWddkhsMx{
 
	private String tjrq;
    private String zzbz;
    private String jgdm;
    private BigDecimal hs;
    private BigDecimal dj;
    private BigDecimal dw;
    private BigDecimal gz;
    private String zzjc;		//数据库无此字段，显示要用
    
	public String getZzjc() {
		return zzjc;
	}
	
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getTjrq() {
		return tjrq;
	}
	public String getZzbz() {
		return zzbz;
	}
	public String getJgdm() {
		return jgdm;
	}
	public BigDecimal getHs() {
		return hs;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public BigDecimal getDw() {
		return dw;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setHs(BigDecimal hs) {
		this.hs = hs;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setDw(BigDecimal dw) {
		this.dw = dw;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
}