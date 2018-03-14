package com.readboy.ssm.po;

import java.math.BigDecimal;
/**
 * 业绩工资首页，对应数据库表erp_wage_ygjx
 * 2017-06-21
 */
public class Performance {
	
	private final static double DEFUALT = 0.00;
	
	private String zzbz;			//组织标识
	private String gzrq;			//工资日期
	private BigDecimal ckgz;		//存款工资
	private BigDecimal dkgz;		//贷款工资
	private BigDecimal dzyhgz;		//电子银行工资
	private BigDecimal ywlgz;		//业务量工资
	private BigDecimal gljxgz;		//管理绩效工资
	private BigDecimal dqbcgz;		//地区补差工资
	private BigDecimal gzhj;		//工资合计
	private int gwbz;				//岗位标志
	private String yggh;			//员工工号
	private int zhgzpm;				//支行工资排名
	private int qhgzpm;				//全行工资排名
	private String khwd;			//考核维度
	private int lrbz;				//录入标志
	private String lrr;				//录入人
	private String lrsj;			//录入时间
	private BigDecimal qt;			//其他
	private BigDecimal jymbgz;		//经营目标工资
	private BigDecimal yqdfgz;		//延期兑付工资
	
	public String getZzbz() {
		return zzbz;
	}
	
	public String getGzrq() {
		if(gzrq !=null  && gzrq.length() > 10){
			return gzrq.substring(0,10);
		}
		return gzrq;
	}

	public BigDecimal getCkgz() {
		if(ckgz ==null){
			return new BigDecimal(0);
		}
		return ckgz;
	}

	public BigDecimal getDkgz() {
		if(dkgz ==null){
			return new BigDecimal(0);
		}
		return dkgz;
	}

	public BigDecimal getDzyhgz() {
		if(dzyhgz ==null){
			return new BigDecimal(0);
		}
		return dzyhgz;
	}

	public BigDecimal getYwlgz() {
		if(ywlgz ==null){
			return new BigDecimal(0);
		}
		return ywlgz;
	}

	public BigDecimal getGljxgz() {
		if(gljxgz ==null){
			return new BigDecimal(0);
		}
		return gljxgz;
	}

	public BigDecimal getDqbcgz() {
		if(dqbcgz ==null){
			return new BigDecimal(0);
		}
		return dqbcgz;
	}

	public BigDecimal getGzhj() {
		if(gzhj ==null){
			return new BigDecimal(0);
		}
		return gzhj;
	}

	public int getGwbz() {
		return gwbz;
	}

	public String getYggh() {
		return yggh;
	}

	public int getZhgzpm() {
		return zhgzpm;
	}

	public int getQhgzpm() {
		return qhgzpm;
	}

	public String getKhwd() {
		return khwd;
	}

	public int getLrbz() {
		return lrbz;
	}

	public String getLrr() {
		return lrr;
	}

	public String getLrsj() {
		return lrsj;
	}

	public BigDecimal getQt() {
		return qt;
	}

	public BigDecimal getJymbgz() {
		return jymbgz;
	}

	public BigDecimal getYqdfgz() {
		if(yqdfgz ==null){
			return new BigDecimal(0);
		}
		return yqdfgz;
	}

	public void setZzbz(String zzbz) {
		this.zzbz = zzbz;
	}

	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}

	public void setCkgz(BigDecimal ckgz) {
		if(ckgz == null)
			this.ckgz = BigDecimal.valueOf(DEFUALT);
		else
			this.ckgz = ckgz;
	}

	public void setDkgz(BigDecimal dkgz) {
		if(dkgz == null)
			this.dkgz = BigDecimal.valueOf(DEFUALT);
		else
			this.dkgz = dkgz;
	}

	public void setDzyhgz(BigDecimal dzyhgz) {
		if(dzyhgz == null)
			this.dzyhgz = BigDecimal.valueOf(DEFUALT);
		else
			this.dzyhgz = dzyhgz;
	}

	public void setYwlgz(BigDecimal ywlgz) {
		if(ywlgz == null)
			this.ywlgz = BigDecimal.valueOf(DEFUALT);
		else
			this.ywlgz = ywlgz;
	}

	public void setGljxgz(BigDecimal gljxgz) {
		if(gljxgz == null)
			this.gljxgz = BigDecimal.valueOf(DEFUALT);
		else
			this.gljxgz = gljxgz;
	}

	public void setDqbcgz(BigDecimal dqbcgz) {
		if(dqbcgz == null)
			this.gljxgz = BigDecimal.valueOf(DEFUALT);
		else
			this.dqbcgz = dqbcgz;
	}

	public void setGzhj(BigDecimal gzhj) {
		if(gzhj == null)
			this.gzhj = BigDecimal.valueOf(DEFUALT);
		else
			this.gzhj = gzhj;
	}

	public void setGwbz(int gwbz) {
		this.gwbz = gwbz;
	}

	public void setYggh(String yggh) {
		this.yggh = yggh;
	}

	public void setZhgzpm(int zhgzpm) {
		this.zhgzpm = zhgzpm;
	}

	public void setQhgzpm(int qhgzpm) {
		this.qhgzpm = qhgzpm;
	}

	public void setKhwd(String khwd) {
		this.khwd = khwd;
	}

	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}

	public void setQt(BigDecimal qt) {
		if(qt == null)
			this.qt = BigDecimal.valueOf(DEFUALT);
		else
			this.qt = qt;
	}

	public void setJymbgz(BigDecimal jymbgz) {
		if(jymbgz == null)
			this.jymbgz = BigDecimal.valueOf(DEFUALT);
		else
			this.jymbgz = jymbgz;
	}

	public void setYqdfgz(BigDecimal yqdfgz) {
		if(yqdfgz == null)
			this.yqdfgz = BigDecimal.valueOf(DEFUALT);
		else
			this.yqdfgz = yqdfgz;
	}
	
}
