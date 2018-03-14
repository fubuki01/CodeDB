package com.readboy.ssm.po;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


public class Marketing{
	
	private String jgdm;			//机构代码
	private String zzjc;			//机构名称		//通过组织表查询
	private int zjlb; 				//证件类别
	private String zjhm; 			//证件号码
	private String khmc; 			//客户名称
	private String sjhm;			//手机号码
	private BigDecimal yxbl; 		//营销比例
	private int sbzt; 				//申报状态
	private String lrr;				//录入人
	private int lrbz; 				//录入标志
	private Timestamp lrsj; 		//录入时间
	private String yyrq;	 			//预约日期
	private long yybh; 				//预约编号,主键
	private String yggh; 			//员工工号
	private String ygxm; 			//员工姓名
	
	

	public String getJgdm() {
		return jgdm;
	}
	
	public String getZzjc() {
		return zzjc;
	}

	public int getZjlb() {
		return zjlb;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getSjhm() {
		return sjhm;
	}
	public BigDecimal getYxbl() {
		return yxbl;
	}
	public int getSbzt() {
		return sbzt;
	}
	public String getLrr() {
		return lrr;
	}
	public int getLrbz() {
		return lrbz;
	}
	public Timestamp getLrsj() {
		return lrsj;
	}
	public String getYyrq() {
		return yyrq;
	}
	public long getYybh() {
		return yybh;
	}
	public String getYggh() {
		return yggh;
	}
	public String getYgxm() {
		return ygxm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setZjlb(int zjlb) {
		this.zjlb = zjlb;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public void setYxbl(BigDecimal yxbl) {
		this.yxbl = yxbl;
	}
	public void setSbzt(int sbzt) {
		this.sbzt = sbzt;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public void setLrsj(Timestamp lrsj) {
		this.lrsj = lrsj;
	}
	public void setYyrq(String yyrq) {
		
		if(yyrq != null && yyrq.length() > 10){
			yyrq = yyrq.substring(0, 10);
		}
		
		this.yyrq = yyrq;
	}
	public void setYybh(long yybh) {
		this.yybh = yybh;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
	}
	
	
}
