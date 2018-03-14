package com.readboy.ssm.po;
import com.readboy.ssm.po.Marketing;

/*
 * 存款营销
 * */
public class DepositMarketing extends Marketing{
	
	private int ckzl;		//存款种类
	private int khzl;		//客户种类
	
	public int getCkzl() {
		return ckzl;
	}
	public int getKhzl() {
		return khzl;
	}
	public void setCkzl(int ckzl) {
		this.ckzl = ckzl;
	}
	public void setKhzl(int khzl) {
		this.khzl = khzl;
	}
	
	

}
