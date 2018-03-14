package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * @author:	   	LCL
 * @date: 	   	2017-6-25
 * @description:存款模拟利润工资明细，对应数据库表:ERP_WAGE_CKMNLRGZMX_YYYYMM(年月)
 */
public class PerformanceCkmnlrgzMx {

	private String tjrq;			//统计日期
	private String jgdm;			//机构代码
	private String zzbz;			//组织标志
	private long gwbz;			//岗位标志 
	private String yggh;    		//员工工号
	private String ckzh;     		//存款账号
	private String zhmc;     		//账号名称
	private String zhlx;     		//账号类型
	private int yxlx;				//营销类型
	private String zjhm;     		//证件号码
	private String khrq;     		//开户日期
	private String dqrq;     		//到期日期
	private BigDecimal ckye;     	//存款余额
	private BigDecimal ll;       	//利率
	private BigDecimal  cbl;      //成本率
	private BigDecimal llc;      	//利率差
	private BigDecimal rllc;    	//日利率差
	private BigDecimal fcbl;    	//分成比率
	private BigDecimal  qmmnlr;   //期末模拟利润
	private BigDecimal qmljmnlr; 	//期末累积模拟利润
	private BigDecimal clye;    	//存量余额
	private BigDecimal clmnlr;   	//存量模拟利润
	private BigDecimal clljmnlr; 	//存量累计模拟利润
	private BigDecimal cldj;     	//存量单价
	private BigDecimal cldw;    	//存量单位
	private BigDecimal clgz;     	//存量工资
	private BigDecimal clljgz;   	//存量累计工资
	private BigDecimal zlmnlr;  	//增量模拟利润
	private BigDecimal zlljmnlr; 	//增量累计模拟利润
	private BigDecimal zldj;     	//增量单价
	private BigDecimal zldw;     	//增量单位
	private BigDecimal zlgz;     	//增量工资
	private BigDecimal zlljgz;   	//增量累计工资
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
	public String getCkzh() {
		return ckzh;
	}
	public String getZhmc() {
		return zhmc;
	}
	public String getZhlx() {
		return zhlx;
	}
	public int getYxlx() {
		return yxlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getKhrq() {
		return khrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public BigDecimal getCkye() {
		return ckye;
	}
	public BigDecimal getLl() {
		return ll;
	}
	public BigDecimal getCbl() {
		return cbl;
	}
	public BigDecimal getLlc() {
		return llc;
	}
	public BigDecimal getRllc() {
		return rllc;
	}
	public BigDecimal getFcbl() {
		return fcbl;
	}
	public BigDecimal getQmmnlr() {
		return qmmnlr;
	}
	public BigDecimal getQmljmnlr() {
		return qmljmnlr;
	}
	public BigDecimal getClye() {
		return clye;
	}
	public BigDecimal getClmnlr() {
		return clmnlr;
	}
	public BigDecimal getClljmnlr() {
		return clljmnlr;
	}
	public BigDecimal getCldj() {
		return cldj;
	}
	public BigDecimal getCldw() {
		return cldw;
	}
	public BigDecimal getClgz() {
		return clgz;
	}
	public BigDecimal getClljgz() {
		return clljgz;
	}
	public BigDecimal getZlmnlr() {
		return zlmnlr;
	}
	public BigDecimal getZlljmnlr() {
		return zlljmnlr;
	}
	public BigDecimal getZldj() {
		return zldj;
	}
	public BigDecimal getZldw() {
		return zldw;
	}
	public BigDecimal getZlgz() {
		return zlgz;
	}
	public BigDecimal getZlljgz() {
		return zlljgz;
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
	public void setCkzh(String ckzh) {
		this.ckzh = ckzh;
	}
	public void setZhmc(String zhmc) {
		this.zhmc = zhmc;
	}
	public void setZhlx(String zhlx) {
		this.zhlx = zhlx;
	}
	public void setYxlx(int yxlx) {
		this.yxlx = yxlx;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setKhrq(String khrq) {
		this.khrq = khrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public void setCkye(BigDecimal ckye) {
		this.ckye = ckye;
	}
	public void setLl(BigDecimal ll) {
		this.ll = ll;
	}
	public void setCbl(BigDecimal cbl) {
		this.cbl = cbl;
	}
	public void setLlc(BigDecimal llc) {
		this.llc = llc;
	}
	public void setRllc(BigDecimal rllc) {
		this.rllc = rllc;
	}
	public void setFcbl(BigDecimal fcbl) {
		this.fcbl = fcbl;
	}
	public void setQmmnlr(BigDecimal qmmnlr) {
		this.qmmnlr = qmmnlr;
	}
	public void setQmljmnlr(BigDecimal qmljmnlr) {
		this.qmljmnlr = qmljmnlr;
	}
	public void setClye(BigDecimal clye) {
		this.clye = clye;
	}
	public void setClmnlr(BigDecimal clmnlr) {
		this.clmnlr = clmnlr;
	}
	public void setClljmnlr(BigDecimal clljmnlr) {
		this.clljmnlr = clljmnlr;
	}
	public void setCldj(BigDecimal cldj) {
		this.cldj = cldj;
	}
	public void setCldw(BigDecimal cldw) {
		this.cldw = cldw;
	}
	public void setClgz(BigDecimal clgz) {
		this.clgz = clgz;
	}
	public void setClljgz(BigDecimal clljgz) {
		this.clljgz = clljgz;
	}
	public void setZlmnlr(BigDecimal zlmnlr) {
		this.zlmnlr = zlmnlr;
	}
	public void setZlljmnlr(BigDecimal zlljmnlr) {
		this.zlljmnlr = zlljmnlr;
	}
	public void setZldj(BigDecimal zldj) {
		this.zldj = zldj;
	}
	public void setZldw(BigDecimal zldw) {
		this.zldw = zldw;
	}
	public void setZlgz(BigDecimal zlgz) {
		this.zlgz = zlgz;
	}
	public void setZlljgz(BigDecimal zlljgz) {
		this.zlljgz = zlljgz;
	}

}
