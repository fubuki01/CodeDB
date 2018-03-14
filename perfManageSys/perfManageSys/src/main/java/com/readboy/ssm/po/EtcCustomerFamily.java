package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * khgxgl_etckhgl 
 * @author Administrator
 *
 */
public class EtcCustomerFamily {
	private String khbh;
	private String khmc;
	private String zjlx;
	private String zjhm;
	private String lxdh;
	private String khdh;
	private String dljgm;
	private String zzjc;
	private String jgdm;
	private long kzbz;
	private String kh;
	private String zh;
	private long zhxh;
	private String dfzh;
	private long dfzhxh;
	private BigDecimal dfje;
	private String xtkkh;
	private long gy;
	private long sqr;
	private String rq;
	private String yjbrq;
	private int bz;
	private String lrr;
	private int lrbz;
	private String lrsj;
	private String txdz;
	private int yxlx;
	private String tzr;
	private BigDecimal tzbl;
	private String azr;
	private BigDecimal azbl;
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	} 
	public String getKhbh() {
		return khbh;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getZjlx() {
		return zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getLxdh() {
		return lxdh;
	}
	public String getKhdh() {
		return khdh;
	}
	public String getDljgm() {
		return dljgm;
	}
	public String getJgdm() {
		return jgdm;
	}
	public long getKzbz() {
		return kzbz;
	}
	public String getKh() {
		return kh;
	}
	public String getZh() {
		return zh;
	}
	public long getZhxh() {
		return zhxh;
	}
	public String getDfzh() {
		return dfzh;
	}
	public long getDfzhxh() {
		return dfzhxh;
	}
	public BigDecimal getDfje() {
		return dfje;
	}
	public String getXtkkh() {
		return xtkkh;
	}
	public long getGy() {
		return gy;
	}
	public long getSqr() {
		return sqr;
	}
	public String getRq() {
		return rq;
	}
	public String getYjbrq() {
		return yjbrq;
	}
	public int getBz() {
		return bz;
	}
	public String getLrr() {
		return lrr;
	}
	public int getLrbz() {
		return lrbz;
	}
	public String getLrsj() {
		return lrsj;
	}
	public String getTxdz() {
		return txdz;
	}
	public int getYxlx() {
		return yxlx;
	}
	public String getTzr() {
		return tzr;
	}
	public BigDecimal getTzbl() {
		return tzbl;
	}
	public String getAzr() {
		return azr;
	}
	public BigDecimal getAzbl() {
		return azbl;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public void setKhdh(String khdh) {
		this.khdh = khdh;
	}
	public void setDljgm(String dljgm) {
		this.dljgm = dljgm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKzbz(long kzbz) {
		this.kzbz = kzbz;
	}
	public void setKh(String kh) {
		this.kh = kh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public void setZhxh(long zhxh) {
		this.zhxh = zhxh;
	}
	public void setDfzh(String dfzh) {
		this.dfzh = dfzh;
	}
	public void setDfzhxh(long dfzhxh) {
		this.dfzhxh = dfzhxh;
	}
	public void setDfje(BigDecimal dfje) {
		this.dfje = dfje;
	}
	public void setXtkkh(String xtkkh) {
		this.xtkkh = xtkkh;
	}
	public void setGy(long gy) {
		this.gy = gy;
	}
	public void setSqr(long sqr) {
		this.sqr = sqr;
	}
	public void setRq(String rq) {
		if(rq != null && rq.length() > 10){
			rq = rq.substring(0, 10);
		}
		this.rq = rq;
	}
	public void setYjbrq(String yjbrq) {
		this.yjbrq = yjbrq;
	}
	public void setBz(int bz) {
		this.bz = bz;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}
	public void setYxlx(int yxlx) {
		this.yxlx = yxlx;
	}
	public void setTzr(String tzr) {
		this.tzr = tzr;
	}
	public void setTzbl(BigDecimal tzbl) {
		this.tzbl = tzbl;
	}
	public void setAzr(String azr) {
		this.azr = azr;
	}
	public void setAzbl(BigDecimal azbl) {
		this.azbl = azbl;
	}
}
