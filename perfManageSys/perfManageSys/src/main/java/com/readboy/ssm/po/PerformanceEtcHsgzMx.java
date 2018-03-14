package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:绩效_Etc户数工资明细，对应数据库表：ERP_WAGE_ETCHSGZMX
 */
public class PerformanceEtcHsgzMx {

	private String tjrq;		//统计日期
	private String jgdm;		//机构代码
	private String zzbz;		//组织标志
	private int gwbz;			//岗位标志
	private String yggh;		//员工工号
	private String khbh;		//客户编号
	private String khmc;		//客户名称
	private String xtkkh;		//湘通卡卡号
	private String zjhm;		//证件号码
	private String lxdh;		//联系电话
	private String kh;			//借记卡号
	private String zh;			//借记账号
	private BigDecimal dfje;	//垫付金额
	private int ghlx;			//管护类型
	private BigDecimal ghbl;	//管护比例
	private BigDecimal dj;		//单价
	private BigDecimal gz;		//工资
	private String zzjc;		//机构名称，该属性是显示要用，对应数据库表无

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
	public int getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getXtkkh() {
		return xtkkh;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getLxdh() {
		return lxdh;
	}
	public String getKh() {
		return kh;
	}
	public String getZh() {
		return zh;
	}
	public BigDecimal getDfje() {
		return dfje;
	}
	public int getGhlx() {
		return ghlx;
	}
	public BigDecimal getGhbl() {
		return ghbl;
	}
	public BigDecimal getDj() {
		return dj;
	}
	public BigDecimal getGz() {
		return gz;
	}
	public void setTjrq(String tjrq) {
		if(tjrq != null && tjrq.length() > 10){
			tjrq = tjrq.substring(0, 10);
		}
		this.tjrq = tjrq;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setGwbz(int gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setXtkkh(String xtkkh) {
		this.xtkkh = xtkkh;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public void setKh(String kh) {
		this.kh = kh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public void setDfje(BigDecimal dfje) {
		this.dfje = dfje;
	}
	public void setGhlx(int ghlx) {
		this.ghlx = ghlx;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}

}
