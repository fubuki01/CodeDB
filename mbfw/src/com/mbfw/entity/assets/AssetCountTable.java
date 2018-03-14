package com.mbfw.entity.assets;

import java.math.BigDecimal;

/**
 * 资产领用统计VO
 * @author:	   	LCL
 * @date: 	   	2017-10-18
 * @description:
 */
public class AssetCountTable {
	
	private AssetGetManage assetGet;
	private String asset_status;
	private String asset_class;
	private String asset_name;
	private BigDecimal asset_price;
	
	
	public String getAsset_status() {
		return asset_status;
	}
	public BigDecimal getAsset_price() {
		return asset_price;
	}
	public void setAsset_status(String asset_status) {
		this.asset_status = asset_status;
	}
	public void setAsset_price(BigDecimal asset_price) {
		this.asset_price = asset_price;
	}
	public AssetGetManage getAssetGet() {
		return assetGet;
	}
	public String getAsset_class() {
		return asset_class;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAssetGet(AssetGetManage assetGet) {
		this.assetGet = assetGet;
	}
	public void setAsset_class(String asset_class) {
		this.asset_class = asset_class;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

}	
