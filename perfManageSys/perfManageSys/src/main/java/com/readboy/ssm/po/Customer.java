package com.readboy.ssm.po;

import java.io.Serializable;

/**
 * 客户信息，对应表 app_mycust
 * @author Administrator
 *
 */
public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jgdm;
	private String zzjc;
	private String khbh;
	private String khmc;
	private String zjhm;
	private String zz;
	private String cpxx;
	private String ssdj;
	private String sjhm;
	private String ghrgh;
	private String ghrxm;
	private int khlx;
	
	public String getZzjc() {
		return zzjc;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public String getJgdm() {
		return jgdm;
	}
	public String getKhbh() {
		return khbh;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getZz() {
		return zz;
	}
	public String getCpxx() {
		return cpxx;
	}
	public String getSsdj() {
		return ssdj;
	}
	public String getSjhm() {
		return sjhm;
	}
	public String getGhrgh() {
		return ghrgh;
	}
	public String getGhrxm() {
		return ghrxm;
	}
	public int getKhlx() {
		return khlx;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setZz(String zz) {
		this.zz = zz;
	}
	public void setCpxx(String cpxx) {
		this.cpxx = cpxx;
	}
	public void setSsdj(String ssdj) {
		this.ssdj = ssdj;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public void setGhrgh(String ghrgh) {
		this.ghrgh = ghrgh;
	}
	public void setGhrxm(String ghrxm) {
		this.ghrxm = ghrxm;
	}
	public void setKhlx(int khlx) {
		this.khlx = khlx;
	}
	
}
