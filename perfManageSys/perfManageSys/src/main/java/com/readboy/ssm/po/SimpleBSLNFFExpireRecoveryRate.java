package com.readboy.ssm.po;

import java.math.BigDecimal;

/*移动端:包收历年发放当年到期回收率
 * */
public class SimpleBSLNFFExpireRecoveryRate {
	
	private String nd;			//年度
	private BigDecimal dqje;	//到期金额
	private BigDecimal qmje;	//期末金额
	private BigDecimal hsl;		//回收率
	
	public String getNd() {
		return nd;
	}
	public BigDecimal getDqje() {
		return dqje;
	}
	public BigDecimal getQmje() {
		return qmje;
	}
	public BigDecimal getHsl() {
		return hsl;
	}
	public void setNd(String nd) {
		if(nd != null && nd.length() > 4){
			nd = nd.substring(0, 4);
		}
		this.nd = nd;
	}
	public void setDqje(BigDecimal dqje) {
		this.dqje = dqje;
	}
	public void setQmje(BigDecimal qmje) {
		this.qmje = qmje;
	}
	public void setHsl(BigDecimal hsl) {
		this.hsl = hsl;
	}
	
}
