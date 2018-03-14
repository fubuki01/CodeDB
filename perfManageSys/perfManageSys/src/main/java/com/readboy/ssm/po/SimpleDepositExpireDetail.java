package com.readboy.ssm.po;

import java.math.BigDecimal;

public class SimpleDepositExpireDetail {
	
	private String zzjc;	//机构名称,在organization表中
	private String khmc;	//客户名称
	private String khrq;	//开户日期
	private String dqrq;	//到期日期
	private BigDecimal ll;	//利率
	private BigDecimal cq;	//存期
	private BigDecimal ye;	//余额
	
	public String getZzjc() {
		return zzjc;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getKhrq() {
		return khrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public BigDecimal getLl() {
		return ll;
	}
	public BigDecimal getCq() {
		return cq;
	}
	public BigDecimal getYe() {
		return ye;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setKhrq(String khrq) {
		if(khrq != null && khrq.length() > 10){
			khrq = khrq.substring(0, 10);
		}
		this.khrq = khrq;
	}
	public void setDqrq(String dqrq) {
		if(dqrq != null && dqrq.length() > 10){
			dqrq = dqrq.substring(0, 10);
		}
		this.dqrq = dqrq;
	}
	public void setLl(BigDecimal ll) {
		this.ll = ll;
	}
	public void setCq(BigDecimal cq) {
		this.cq = cq;
	}
	public void setYe(BigDecimal ye) {
		this.ye = ye;
	}
	
	
}
