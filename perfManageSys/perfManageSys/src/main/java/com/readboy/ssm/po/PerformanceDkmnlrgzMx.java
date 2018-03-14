package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * @author:	   	LCL
 * @date: 	   	2017-6-25
 * @description:绩效_贷款客户模拟利润工资明细，对应数据库表：ERP_WAGE_DKMNLRGZMX_YYYYMM(年月)。
 */
public class PerformanceDkmnlrgzMx {
	
	 private String tjrq;			//统计日期
	 private String jgdm;			//机构代码
	 private String zzbz;			//组织标志
	 private long gwbz;    			//岗位标志
	 private String yggh;    		//员工工号
	 private String dkzh;     		//贷款账号
	 private String hth;    		//合同号
	 private String zhmc;    		//账号名称
	 private String zjhm;			//证件号码
	 private BigDecimal dkje; 		//贷款金额
	 private BigDecimal dkye; 		//贷款余额
	 private String ffrq;			//发放日期
	 private String dqrq;			//到期日期
	 private int  wjflbz;			//五级分类标志
	 private int  ncwjflbz;			//年初五级分类标志
	 private String ywzl;			//业务种类
	 private String  zzqxrq;		//最早欠息日期
	 private BigDecimal ll;			//利率
	 private BigDecimal cbl;		//成本率
	 private BigDecimal llc;		//利率差
	 private BigDecimal rllc;  		//日利率差
	 private BigDecimal fcbl;		//分成比率
	 private BigDecimal qmmnlr;		//期末模拟利润
	 private BigDecimal qmljmnlr;	//期末累计模拟利润
	 private BigDecimal  clye;		//存量余额
	 private BigDecimal  clmnlr;	//存量模拟利润
	 private BigDecimal clljmnlr;	//存量累计模拟利润
	 private BigDecimal cldj;		//存量单价
	 private BigDecimal cldw;		//存量单位
	 private BigDecimal  clgz;		//存量工资
	 private BigDecimal clljgz;		//存量累计工资
	 private BigDecimal zlmnlr;		//增量模拟利润
	 private BigDecimal zlljmnlr;	//增量累计模拟利润
	 private BigDecimal zldj;		//增量单价
	 private BigDecimal zldw;		//增量单位
	 private BigDecimal zlgz;		//增量工资
	 private BigDecimal zlljgz;		//增量累计工资 
	 private BigDecimal yqdfmnlr;	//延期兑付模拟利润
	 private BigDecimal yqdfljmnlr;	//延期兑付累积模拟利润
	 private BigDecimal yqdfgz;		//延期兑付工资
	 private BigDecimal yqdfljgz;	//延期兑付累积工资
	 private BigDecimal yqdffcgz;	//延期兑付分成工资
	 private Integer dfbz;			//兑付标志
	 private String dfrq;			//到付日期
	 private Integer czlbz;			//存增量标志
	 private String zzjc;			//组织简称，该属性是显示要用，对应数据库表无
		
	 
	public BigDecimal getYqdfmnlr() {
		return yqdfmnlr;
	}
	public BigDecimal getYqdfljmnlr() {
		return yqdfljmnlr;
	}
	public BigDecimal getYqdfgz() {
		return yqdfgz;
	}
	public BigDecimal getYqdfljgz() {
		return yqdfljgz;
	}
	public BigDecimal getYqdffcgz() {
		return yqdffcgz;
	}
	public Integer getDfbz() {
		return dfbz;
	}
	public String getDfrq(){
		if(dfrq != null && dfrq.length() > 10){
			return dfrq.substring(0,10);
		}
		return dfrq;
	}
	public Integer getCzlbz() {
		return czlbz;
	}
	public void setYqdfmnlr(BigDecimal yqdfmnlr) {
		this.yqdfmnlr = yqdfmnlr;
	}
	public void setYqdfljmnlr(BigDecimal yqdfljmnlr) {
		this.yqdfljmnlr = yqdfljmnlr;
	}
	public void setYqdfgz(BigDecimal yqdfgz) {
		this.yqdfgz = yqdfgz;
	}
	public void setYqdfljgz(BigDecimal yqdfljgz) {
		this.yqdfljgz = yqdfljgz;
	}
	public void setYqdffcgz(BigDecimal yqdffcgz) {
		this.yqdffcgz = yqdffcgz;
	}
	public void setDfbz(Integer dfbz) {
		this.dfbz = dfbz;
	}
	public void setDfrq(String dfrq) {
		this.dfrq = dfrq;
	}
	public void setCzlbz(Integer czlbz) {
		this.czlbz = czlbz;
	}
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getTjrq() {
		if(tjrq != null && tjrq.length() > 10){
			return tjrq.substring(0,10);
		}
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
	public String getDkzh() {
		return dkzh;
	}
	public String getHth() {
		return hth;
	}
	public String getZhmc() {
		return zhmc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public BigDecimal getDkje() {
		return dkje;
	}
	public BigDecimal getDkye() {
		return dkye;
	}
	public String getFfrq() {
		if(ffrq != null && ffrq.length() > 10){
			return ffrq.substring(0,10);
		}
		return ffrq;
	}
	public String getDqrq() {
		if(dqrq != null && dqrq.length() > 10){
			return dqrq.substring(0,10);
		}
		return dqrq;
	}
	public int getWjflbz() {
		return wjflbz;
	}
	public int getNcwjflbz() {
		return ncwjflbz;
	}
	public String getYwzl() {
		return ywzl;
	}
	public String getZzqxrq() {
		if(zzqxrq != null && zzqxrq.length() > 10){
			return zzqxrq.substring(0,10);
		}
		return zzqxrq;
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
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public void setZhmc(String zhmc) {
		this.zhmc = zhmc;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setDkje(BigDecimal dkje) {
		this.dkje = dkje;
	}
	public void setDkye(BigDecimal dkye) {
		this.dkye = dkye;
	}
	public void setFfrq(String ffrq) {
		this.ffrq = ffrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public void setWjflbz(int wjflbz) {
		this.wjflbz = wjflbz;
	}
	public void setNcwjflbz(int ncwjflbz) {
		this.ncwjflbz = ncwjflbz;
	}
	public void setYwzl(String ywzl) {
		this.ywzl = ywzl;
	}
	public void setZzqxrq(String zzqxrq) {
		this.zzqxrq = zzqxrq;
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
