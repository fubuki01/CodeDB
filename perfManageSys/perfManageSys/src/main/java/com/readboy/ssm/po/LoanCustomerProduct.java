package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * app_dkkhcpxx 贷款客户产品信息
 * @author Administrator
 *
 */
public class LoanCustomerProduct {
	private String cpmc;
	private String jgdm;
	private String zzjc;
	private String khbh;
	private String hth;
	private String dkzh;
	private String ffrq;
	private String dqrq;
	private BigDecimal dkje;
	private BigDecimal dkye;
	private BigDecimal ll;
	private String khjlmc;
	private int five_class_type;
	private String yxr;
	private BigDecimal yxbl;
	private String ghr;
	private BigDecimal ghbl;
	private String bsr;
	private BigDecimal bsbl;
	private String spr;
	private BigDecimal spbl;
	private String dcr;
	private BigDecimal dcbl;
	private String lrr;
	private int lrbz;
	private String lrsj;
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getCpmc() {
		return cpmc;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getHth() {
		return hth;
	}
	public String getDkzh() {
		return dkzh;
	}
	public String getFfrq() {
		return ffrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public BigDecimal getDkje() {
		if(dkje == null){
			return new BigDecimal(0.00);
		}
		return dkje;
	}
	public BigDecimal getDkye() {
		if(dkye == null){
			return new BigDecimal(0.00);
		}
		return dkye;
	}
	public BigDecimal getLl() {
		if(ll == null){
			return new BigDecimal(0.00);
		}
		return ll;
	}
	public String getKhjlmc() {
		return khjlmc;
	}
	public int getFive_class_type() {
		return five_class_type;
	}
	public String getYxr() {
		return yxr;
	}
	public BigDecimal getYxbl() {
		if(yxbl == null){
			return new BigDecimal(0.00);
		}
		return yxbl;
	}
	public String getGhr() {
		return ghr;
	}
	public BigDecimal getGhbl() {
		if(ghbl == null){
			return new BigDecimal(0.00);
		}
		return ghbl;
	}
	public String getBsr() {
		return bsr;
	}
	public BigDecimal getBsbl() {
		if(bsbl == null){
			return new BigDecimal(0.00);
		}
		return bsbl;
	}
	public String getSpr() {
		return spr;
	}
	public BigDecimal getSpbl() {
		if(spbl == null){
			return new BigDecimal(0.00);
		}
		return spbl;
	}
	public String getDcr() {
		return dcr;
	}
	public BigDecimal getDcbl() {
		if(dcbl == null){
			return new BigDecimal(0.00);
		}
		return dcbl;
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
	public void setCpmc(String cpmc) {
		this.cpmc = cpmc;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
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
	public void setDkje(BigDecimal dkje) {
		this.dkje = dkje;
	}
	public void setDkye(BigDecimal dkye) {
		this.dkye = dkye;
	}
	public void setLl(BigDecimal ll) {
		this.ll = ll;
	}
	public void setKhjlmc(String khjlmc) {
		this.khjlmc = khjlmc;
	}
	public void setFive_class_type(int five_class_type) {
		this.five_class_type = five_class_type;
	}
	public void setYxr(String yxr) {
		this.yxr = yxr;
	}
	public void setYxbl(BigDecimal yxbl) {
		this.yxbl = yxbl;
	}
	public void setGhr(String ghr) {
		this.ghr = ghr;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public void setBsr(String bsr) {
		this.bsr = bsr;
	}
	public void setBsbl(BigDecimal bsbl) {
		this.bsbl = bsbl;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public void setSpbl(BigDecimal spbl) {
		this.spbl = spbl;
	}
	public void setDcr(String dcr) {
		this.dcr = dcr;
	}
	public void setDcbl(BigDecimal dcbl) {
		this.dcbl = dcbl;
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
