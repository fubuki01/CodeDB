package com.readboy.ssm.po;

import java.math.BigDecimal;

/*
 * 移动端，包收逾期贷款明细
 */
public class SimpleBSOverdueLoanDetail {
	
	private String zzjc;		//组织简称，也叫业务机构
	private String  khmc ;		//客户名称
	private String ffrq;		//发放日期
	private String  dqrq;		//到期日期
	private BigDecimal dkje;	//贷款金额
	private BigDecimal dkye;	//贷款余额
	private BigDecimal ll;		//利率
	private String  wjfl;		//五级分类
	
	public String getZzjc() {
		return zzjc;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getFfrq() {
		return ffrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public BigDecimal getDkje() {
		return dkje;
	}
	public BigDecimal getDkye() {
		return dkye;
	}
	public BigDecimal getLl() {
		return ll;
	}
	public String getWjfl() {
		return wjfl;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
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
	public void setWjfl(String wjfl) {
		this.wjfl = wjfl;
	}
}
