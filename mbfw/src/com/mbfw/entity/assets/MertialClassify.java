package com.mbfw.entity.assets;
/**
 * 耗材分类
 * @author Wyd
 *
 */
public class MertialClassify {

	private String number;//资产编号
	private String name;//资产名称
	private String pId;//资产父编码
	private String isuse;//资产父编码
	private String open = "false";//是否展开树节点，默认不展开
	private String orderSort;//排列序号
	private String parentSort;//父类排列序号
	private String coding;//资产编码
	
	public String getOrderSort() {
		return orderSort;
	}
	public void setOrderSort(String orderSort) {
		this.orderSort = orderSort;
	}
	public String getParentSort() {
		return parentSort;
	}
	public void setParentSort(String parentSort) {
		this.parentSort = parentSort;
	}
	public String getCoding() {
		return coding;
	}
	public void setCoding(String coding) {
		this.coding = coding;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
}
