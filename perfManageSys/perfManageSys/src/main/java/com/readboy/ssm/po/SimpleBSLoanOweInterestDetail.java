package com.readboy.ssm.po;

import java.math.BigDecimal;

/*移动端包收贷款欠息明细
 * */
public class SimpleBSLoanOweInterestDetail {
	
    private String zzjc;		//组织简称
	private String khmc;		//客户名称
	private String qxr;			//起息日
	private String jxr;			//结息日
	private BigDecimal qx;		//欠息
	public String getZzjc() {
		return zzjc;
	}
	public String getKhmc() {
		return khmc;
	}
	public String getQxr() {
		return qxr;
	}
	public String getJxr() {
		return jxr;
	}
	public BigDecimal getQx() {
		return qx;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public void setQxr(String qxr) {
		if(qxr != null && qxr.length() > 10){
			qxr = qxr.substring(0, 10);
		}
		this.qxr = qxr;
	}
	public void setJxr(String jxr) {
		if(jxr != null && jxr.length() > 10){
			jxr = jxr.substring(0, 10);
		}
		this.jxr = jxr;
	}
	public void setQx(BigDecimal qx) {
		this.qx = qx;
	}
	
}
