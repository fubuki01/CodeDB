package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 持有产品中存款信息 管户人，对应表 khgxgl_ckkhghlsb
 * @author tan	
 *
 */
public class DepositAccountPerson {
	private String jgdm;	//机构代码
	private String khbh;	//客户编号
	private String ghlx;	//管户类型
	private String ghr;		//管户人
	private String ygxm;		//管户人姓名，通过hr_bas_staff查询
	private BigDecimal ghbl;//管户比例
	private String ksrq;	//开始日期
	private String jsrq;	//结束日期
	private BigDecimal lrbs;//录入标识
	private String lrr;		//录入人
	private String lrsj;	//录入时间
	
	public String getJgdm() {
		return jgdm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public String getKhbh() {
		return khbh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public String getGhlx() {
		return ghlx;
	}
	public void setGhlx(String ghlx) {
		this.ghlx = ghlx;
	}
	public String getGhr() {
		return ghr;
	}
	public void setGhr(String ghr) {
		this.ghr = ghr;
	}
	public String getYgxm() {
		return ygxm;
	}
	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
	}
	public BigDecimal getGhbl() {
		return ghbl;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public String getKsrq() {
		return ksrq;
	}
	public void setKsrq(String ksrq) {
		
		if(ksrq != null && ksrq.length() > 10){
			ksrq = ksrq.substring(0, 10);
		}
		
		this.ksrq = ksrq;
	}
	public String getJsrq() {
		return jsrq;
	}
	public void setJsrq(String jsrq) {
		if(jsrq != null && jsrq.length() > 10){
			jsrq = jsrq.substring(0, 10);
		}
		this.jsrq = jsrq;
	}
	public BigDecimal getLrbs() {
		return lrbs;
	}
	public void setLrbs(BigDecimal lrbs) {
		this.lrbs = lrbs;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getLrsj() {
		return lrsj;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	
	
}
