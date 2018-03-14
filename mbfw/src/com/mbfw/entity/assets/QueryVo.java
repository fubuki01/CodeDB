package com.mbfw.entity.assets;

import java.util.List;

/**
 * 
 * @author scw
 *2017-09-17
 *function：自定义一个封装对象javabean，主要是为了将jsp传送过来的相同类的数据进行封装处理
 */
public class QueryVo {
	
	private AssetApprovalProcess aap;
	
	private List<AssetApprovalProcessDetail> itemList;
	
	


	public AssetApprovalProcess getAap() {
		return aap;
	}

	public void setAap(AssetApprovalProcess aap) {
		this.aap = aap;
	}

	public List<AssetApprovalProcessDetail> getItemList() {
		return itemList;
	}

	public void setItemList(List<AssetApprovalProcessDetail> itemList) {
		this.itemList = itemList;
	}

	public QueryVo(AssetApprovalProcess aap, List<AssetApprovalProcessDetail> itemList) {
		super();
		this.aap = aap;
		this.itemList = itemList;
	}

	public QueryVo(){
		super();
	}

	
}
