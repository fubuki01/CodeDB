package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:绩效_手机银行户数明细，对应数据库表:erp_wage_sjyhhsmx
 */
public class PerformanceCellBankHsMx{
	
	private String tjrq;			//统计日期
	private String jgdm;			//机构代码
	private String zzbz;			//组织标志
	private long gwbz;				//岗位标志
	private String yggh;			//员工工号
	private String khbh;			//客户编号
	private String khname;			//客户姓名
	private String ctftype;			//证件类型
	private String ctfno;			//证件号码
	private String mobile;			//手机号码
	private String opentime;		//开户时间
	private String stk_serial;		//贴片卡号
	private String stk_active_time;	//贴片卡激活时间
	private String stk_opentime;	//贴片卡开户时间
	private String stk_mobile;		//绑定手机号码
	private int yxlx;				//营销类型
	private BigDecimal dj;			//单价
	private BigDecimal gz;			//工资
	private String zzjc;			//机构名称，该属性是显示要用，对应数据库表无
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getTjrq() {
		return tjrq;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getZzbz() {
		return zzbz;
	}
	public long getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getKhname() {
		return khname;
	}
	public String getCtftype() {
		return ctftype;
	}
	public String getCtfno() {
		return ctfno;
	}
	public String getMobile() {
		return mobile;
	}
	public String getOpentime() {
		return opentime;
	}
	public String getStk_serial() {
		return stk_serial;
	}
	public String getStk_active_time() {
		return stk_active_time;
	}
	public String getStk_opentime() {
		return stk_opentime;
	}
	public String getStk_mobile() {
		return stk_mobile;
	}
	public int getYxlx() {
		return yxlx;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setGwbz(long gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setKhname(String khname) {
		this.khname = khname;
	}
	public void setCtftype(String ctftype) {
		this.ctftype = ctftype;
	}
	public void setCtfno(String ctfno) {
		this.ctfno = ctfno;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setOpentime(String opentime) {
		if(opentime != null && opentime.length() > 10){
			opentime = opentime.substring(0, 10);
		}
		this.opentime = opentime;
	}
	public void setStk_serial(String stk_serial) {
		this.stk_serial = stk_serial;
	}
	public void setStk_active_time(String stk_active_time) {
		if(stk_active_time != null && stk_active_time.length() > 10){
			stk_active_time = stk_active_time.substring(0, 10);
		}
		this.stk_active_time = stk_active_time;
	}
	public void setStk_opentime(String stk_opentime) {
		if(stk_opentime != null && stk_opentime.length() > 10){
			stk_opentime = stk_opentime.substring(0, 10);
		}
		this.stk_opentime = stk_opentime;
	}
	public void setStk_mobile(String stk_mobile) {
		this.stk_mobile = stk_mobile;
	}
	public void setYxlx(int yxlx) {
		this.yxlx = yxlx;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
}
