package com.mbfw.entity.assets;

import java.math.BigDecimal;

/**
 * 资产统计类，用于生产资产统计的表格
 * @author:	   	LCL
 * @date: 	   	2017-10-10
 * @description:
 */
public class AssetCount {
	
	private String asset_use_dept;
	private BigDecimal zzc;
	private BigDecimal ndxz;
	
	public AssetCount(String asset_use_dept,BigDecimal zzc,BigDecimal ndxz){
		this.asset_use_dept = asset_use_dept;
		this.zzc = zzc;
		this.ndxz = ndxz;
	}
	
	public String getAsset_use_dept() {
		return asset_use_dept;
	}

	public void setAsset_use_dept(String asset_use_dept) {
		this.asset_use_dept = asset_use_dept;
	}

	public BigDecimal getZzc() {
		return zzc;
	}

	public BigDecimal getNdxz() {
		return ndxz;
	}

	public void setZzc(BigDecimal zzc) {
		this.zzc = zzc;
	}

	public void setNdxz(BigDecimal ndxz) {
		this.ndxz = ndxz;
	}
}
