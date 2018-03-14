package com.readboy.ssm.po;

import java.math.BigDecimal;

/*绩效地区补差，对应数据库表：erp_wage_dqbcgzmx
 * */
public class PerformanceAreaMakeUp {
	
	private String tjrq;		//统计日期
	private String zzbz;		//组织标志
	private String yggh;		//员工工号
	private int zgts;			//在岗天数
	private int xydc;			//补差档次，数据库中这个可能命名写错了
	private BigDecimal btbz;	//标准补贴
	private BigDecimal gz;		//工资
	private String zzjc;		//数据库中无，显示要用
	
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
	public String getYggh() {
		return yggh;
	}
	public int getZgts() {
		return zgts;
	}
	public int getXydc() {
		return xydc;
	}
	public BigDecimal getBtbz() {
		return btbz;
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
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setZgts(int zgts) {
		this.zgts = zgts;
	}
	public void setXydc(int xydc) {
		this.xydc = xydc;
	}
	public void setBtbz(BigDecimal btbz) {
		this.btbz = btbz;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}
	
}
