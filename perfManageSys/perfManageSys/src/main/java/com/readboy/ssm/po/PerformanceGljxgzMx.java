package com.readboy.ssm.po;
/**
 * 
 * @author:	   	LCL
 * @date: 	   	2017-7-7
 * @description:管理绩效工资明细多表查询类
 */
public class PerformanceGljxgzMx{
	private PerformanceManageDetail performanceManageDetail;
	private String zzjc;
	private String gwmc;
	public PerformanceManageDetail getPerformanceManageDetail() {
		return performanceManageDetail;
	}
	public String getZzjc() {
		return zzjc;
	}
	public String getGwmc() {
		return gwmc;
	}
	public void setPerformanceManageDetail(
			PerformanceManageDetail performanceManageDetail) {
		this.performanceManageDetail = performanceManageDetail;
	}
	public void setZzjc(String zzjc) {
		this.zzjc = zzjc;
	}
	public void setGwmc(String gwmc) {
		this.gwmc = gwmc;
	}
}
