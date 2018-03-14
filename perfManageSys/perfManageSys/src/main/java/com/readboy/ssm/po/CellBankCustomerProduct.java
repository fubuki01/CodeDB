package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * app_sjyhcpxx 手机银行客户产品信息		手机银行信息查询
 * @author Administrator
 *
 */
public class CellBankCustomerProduct {
	private String jgdm;
	private String zzjc;
	private String khmc;
	private String khbh;
	private String zjhm;
	private String sjhm;
	private String khrq;
	private String tpjgdm;
	private String tpkh;
	private String tprq;
	private String jhsj;
	private String zxrq;
	private int yxlx;
	private String tzr;
	private BigDecimal tzbl;
	private String ghr;
	private BigDecimal ghbl;
	private String lrr;
	private int lrbz;
	private String lrsj;
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getSjhm() {
		return sjhm;
	}
	public String getKhrq() {
		return khrq;
	}
	public String getTpjgdm() {
		return tpjgdm;
	}
	public String getTpkh() {
		return tpkh;
	}
	public String getTprq() {
		return tprq;
	}
	public String getJhsj() {
		return jhsj;
	}
	public String getZxrq() {
		return zxrq;
	}
	public int getYxlx() {
		return yxlx;
	}
	public String getTzr() {
		return tzr;
	}
	public BigDecimal getTzbl() {
		return tzbl;
	}
	public String getGhr() {
		return ghr;
	}
	public BigDecimal getGhbl() {
		return ghbl;
	}
	public String getLrr() {
		return lrr;
	}
	public int getLrbz() {
		return lrbz;
	}
	public String getLrsj() {
		return lrsj;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public void setKhrq(String khrq) {
		if(khrq != null && khrq.length() > 10){
			khrq = khrq.substring(0, 10);
		}
		this.khrq = khrq;
	}
	public void setTpjgdm(String tpjgdm) {
		this.tpjgdm = tpjgdm;
	}
	public void setTpkh(String tpkh) {
		this.tpkh = tpkh;
	}
	public void setTprq(String tprq) {
		if(tprq != null && tprq.length() > 10){
			tprq = tprq.substring(0, 10);
		}
		this.tprq = tprq;
	}
	public void setJhsj(String jhsj) {
		this.jhsj = jhsj;
	}
	public void setZxrq(String zxrq) {
		if(zxrq != null && zxrq.length() > 10){
			zxrq = zxrq.substring(0, 10);
		}
		this.zxrq = zxrq;
	}
	public void setYxlx(int yxlx) {
		this.yxlx = yxlx;
	}
	public void setTzr(String tzr) {
		this.tzr = tzr;
	}
	public void setTzbl(BigDecimal tzbl) {
		this.tzbl = tzbl;
	}
	public void setGhr(String ghr) {
		this.ghr = ghr;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
}
