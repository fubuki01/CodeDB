package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * @author:	   	LCL
 * @date: 	   	2017-6-24
 * @description:绩效_手机银行交易明细，对应数据库表:erp_wage_sjyhjymx
 */
public class PerformanceCellBankTradeMx {
	
	private String tjrq;		//统计日期
	private String jgdm;		//机构代码
	private String zzbz;		//组织标志
	private long gwbz;			//岗位标志
	private String yggh;		//员工工号
	private String khbh;		//客户编号
	private String khname;		//客户姓名
	private String ctftype;		//证件类型
	private String ctfno;		//证件号码
	private String mobile;		//手机号码
	private String stk_serial;	//贴片卡号
	private String payacc;		//付款方账号
	private String payname;		//付款方户名
	private String rcvacc;		//收款方账号
	private String rcvname;		//收款方姓名
	private String rcvbank;		//收款方开户行
	private String tranamt;		//交易金额
	private String stt;			//状态
	private String flowno;		//渠道流水号
	private String bsncode;		//交易代码
	private String subtime;		//交易时间
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
	public String getStk_serial() {
		return stk_serial;
	}
	public String getPayacc() {
		return payacc;
	}
	public String getPayname() {
		return payname;
	}
	public String getRcvacc() {
		return rcvacc;
	}
	public String getRcvname() {
		return rcvname;
	}
	public String getRcvbank() {
		return rcvbank;
	}
	public String getTranamt() {
		return tranamt;
	}
	public String getStt() {
		return stt;
	}
	public String getFlowno() {
		return flowno;
	}
	public String getBsncode() {
		return bsncode;
	}
	public String getSubtime() {
		return subtime;
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
	public void setStk_serial(String stk_serial) {
		this.stk_serial = stk_serial;
	}
	public void setPayacc(String payacc) {
		this.payacc = payacc;
	}
	public void setPayname(String payname) {
		this.payname = payname;
	}
	public void setRcvacc(String rcvacc) {
		this.rcvacc = rcvacc;
	}
	public void setRcvname(String rcvname) {
		this.rcvname = rcvname;
	}
	public void setRcvbank(String rcvbank) {
		this.rcvbank = rcvbank;
	}
	public void setTranamt(String tranamt) {
		this.tranamt = tranamt;
	}
	public void setStt(String stt) {
		this.stt = stt;
	}
	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}
	public void setBsncode(String bsncode) {
		this.bsncode = bsncode;
	}
	public void setSubtime(String subtime) {
		if(subtime != null && subtime.length() > 10){
			subtime = subtime.substring(0, 10);
		}
		this.subtime = subtime;
	}
	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}
	public void setGz(BigDecimal gz) {
		this.gz = gz;
	}

}
