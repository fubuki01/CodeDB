package com.readboy.ssm.po;
import java.math.BigDecimal;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-9-1
 * @description:存款业绩，对应数据库表：erp_wage_ckaljcgzmx
 */
public class PerformanceCkAljcgzMx{
	private String tjrq;			//统计日期
    private String zzbz;			//组织标志
    private Long gwbz;				//岗位标志
    private String yggh;			//员工工号
    private String ckzh;			//存款账号
    private String jgdm;			//机构代码
    private String khbh;			//客户编号
    private String zjhm;			//证件号码
    private String zhlx;			//账号类型
    private BigDecimal clye;		//存量余额
    private BigDecimal qmye;		//期末余额
    private BigDecimal zlye;		//增量余额
    private BigDecimal clyrp;		//存量月日平
    private BigDecimal clnrp;		//存量年日平
    private BigDecimal qmyrp;		//期末月日平
    private BigDecimal qmnrp;		//期末年日平
    private BigDecimal zlyrp;		//增量月日平
    private BigDecimal zlnrp;		//增量年日平
    private BigDecimal clyrpdj;		//存量年日平单价
    private BigDecimal clyrpgz;		//存量月日平工资
    private BigDecimal qmyrpdj;		//期末月日平单价
    private BigDecimal qmyrpgz;		//期末月日平工资
    private BigDecimal zlyrpdj;		//增量月日平单价
    private BigDecimal zlyrpgz;		//增量月日平工资
    private BigDecimal clyrpdw;		//存量月日平单位
    private BigDecimal zlyrpdw;		//增量月日平单位
    private BigDecimal qmyrpdw;		//期末月日平单位
    private String zhmc;			//账号名称
    private BigDecimal ghbl;		//管护比例
    private BigDecimal clyrpljgz;	//存量月日平累计工资
    private BigDecimal qmyrpljgz;	//期末月日平累计工资
    private BigDecimal zlyrpljgz;	//增量月日平累计工资
    private String zzjc;			//组织简称，对应数据库无这个字段
    
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
	public Long getGwbz() {
		return gwbz;
	}
	public String getYggh() {
		return yggh;
	}
	public String getCkzh() {
		return ckzh;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getZhlx() {
		return zhlx;
	}
	public BigDecimal getClye() {
		return clye;
	}
	public BigDecimal getQmye() {
		return qmye;
	}
	public BigDecimal getZlye() {
		return zlye;
	}
	public BigDecimal getClyrp() {
		return clyrp;
	}
	public BigDecimal getClnrp() {
		return clnrp;
	}
	public BigDecimal getQmyrp() {
		return qmyrp;
	}
	public BigDecimal getQmnrp() {
		return qmnrp;
	}
	public BigDecimal getZlyrp() {
		return zlyrp;
	}
	public BigDecimal getZlnrp() {
		return zlnrp;
	}
	public BigDecimal getClyrpdj() {
		return clyrpdj;
	}
	public BigDecimal getClyrpgz() {
		return clyrpgz;
	}
	public BigDecimal getQmyrpdj() {
		return qmyrpdj;
	}
	public BigDecimal getQmyrpgz() {
		return qmyrpgz;
	}
	public BigDecimal getZlyrpdj() {
		return zlyrpdj;
	}
	public BigDecimal getZlyrpgz() {
		return zlyrpgz;
	}
	public BigDecimal getClyrpdw() {
		return clyrpdw;
	}
	public BigDecimal getZlyrpdw() {
		return zlyrpdw;
	}
	public BigDecimal getQmyrpdw() {
		return qmyrpdw;
	}
	public String getZhmc() {
		return zhmc;
	}
	public BigDecimal getGhbl() {
		return ghbl;
	}
	public BigDecimal getClyrpljgz() {
		return clyrpljgz;
	}
	public BigDecimal getQmyrpljgz() {
		return qmyrpljgz;
	}
	public BigDecimal getZlyrpljgz() {
		return zlyrpljgz;
	}
	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}
	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}
	public void setGwbz(Long gwbz) {
		this.gwbz = gwbz;
	}
	public void setYggh(String yggh) {
		this.yggh = yggh;
	}
	public void setCkzh(String ckzh) {
		this.ckzh = ckzh;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setZhlx(String zhlx) {
		this.zhlx = zhlx;
	}
	public void setClye(BigDecimal clye) {
		this.clye = clye;
	}
	public void setQmye(BigDecimal qmye) {
		this.qmye = qmye;
	}
	public void setZlye(BigDecimal zlye) {
		this.zlye = zlye;
	}
	public void setClyrp(BigDecimal clyrp) {
		this.clyrp = clyrp;
	}
	public void setClnrp(BigDecimal clnrp) {
		this.clnrp = clnrp;
	}
	public void setQmyrp(BigDecimal qmyrp) {
		this.qmyrp = qmyrp;
	}
	public void setQmnrp(BigDecimal qmnrp) {
		this.qmnrp = qmnrp;
	}
	public void setZlyrp(BigDecimal zlyrp) {
		this.zlyrp = zlyrp;
	}
	public void setZlnrp(BigDecimal zlnrp) {
		this.zlnrp = zlnrp;
	}
	public void setClyrpdj(BigDecimal clyrpdj) {
		this.clyrpdj = clyrpdj;
	}
	public void setClyrpgz(BigDecimal clyrpgz) {
		this.clyrpgz = clyrpgz;
	}
	public void setQmyrpdj(BigDecimal qmyrpdj) {
		this.qmyrpdj = qmyrpdj;
	}
	public void setQmyrpgz(BigDecimal qmyrpgz) {
		this.qmyrpgz = qmyrpgz;
	}
	public void setZlyrpdj(BigDecimal zlyrpdj) {
		this.zlyrpdj = zlyrpdj;
	}
	public void setZlyrpgz(BigDecimal zlyrpgz) {
		this.zlyrpgz = zlyrpgz;
	}
	public void setClyrpdw(BigDecimal clyrpdw) {
		this.clyrpdw = clyrpdw;
	}
	public void setZlyrpdw(BigDecimal zlyrpdw) {
		this.zlyrpdw = zlyrpdw;
	}
	public void setQmyrpdw(BigDecimal qmyrpdw) {
		this.qmyrpdw = qmyrpdw;
	}
	public void setZhmc(String zhmc) {
		this.zhmc = zhmc;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public void setClyrpljgz(BigDecimal clyrpljgz) {
		this.clyrpljgz = clyrpljgz;
	}
	public void setQmyrpljgz(BigDecimal qmyrpljgz) {
		this.qmyrpljgz = qmyrpljgz;
	}
	public void setZlyrpljgz(BigDecimal zlyrpljgz) {
		this.zlyrpljgz = zlyrpljgz;
	}

}