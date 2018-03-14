package com.readboy.ssm.po;

public class LoanMarketing extends Marketing{
	private String dkzl; 	//贷款种类
	private int khzl;	//客户种类
	
	public String getDkzl() {
		return dkzl;
	}
	public int getKhzl() {
		return khzl;
	}
	public void setDkzl(String dkzl) {
		this.dkzl = dkzl;
	}
	public void setKhzl(int khzl) {
		this.khzl = khzl;
	}
	
}
