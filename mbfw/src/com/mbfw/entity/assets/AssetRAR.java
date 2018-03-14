package com.mbfw.entity.assets;

import java.math.BigDecimal;


/**
 * 类名称：AssetRAR.java
 * 
 * @author 揭尹  创建时间：2017年9月19日
 * @version 1.0
 */


public class AssetRAR implements java.io.Serializable{
	private int id;
	private String asset_code;
	private String asset_name;
	private String bank_name;
	private String department;
	private String drep_department;
	private String repair_time;
	private String fault_phenomen;
	private String fault_reason;
	private String asset_person;
	private String defect_time;
	private String status;
	private BigDecimal cost;
	private String finishi_time;
	private String maintain_result;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMaintain_result() {
		return maintain_result;
	}
	public void setMaintain_result(String maintain_result) {
		this.maintain_result = maintain_result;
	}
	public String getFinishi_time() {
		return finishi_time;
	}
	public void setFinishi_time(String finishi_time) {
		this.finishi_time = finishi_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDrep_department() {
		return drep_department;
	}
	public void setDrep_department(String drep_department) {
		this.drep_department = drep_department;
	}
	public String getRepair_time() {
		return repair_time;
	}
	public void setRepair_time(String repair_time) {
		this.repair_time = repair_time;
	}
	public String getFault_phenomen() {
		return fault_phenomen;
	}
	public void setFault_phenomen(String fault_phenomen) {
		this.fault_phenomen = fault_phenomen;
	}
	public String getFault_reason() {
		return fault_reason;
	}
	public void setFault_reason(String fault_reason) {
		this.fault_reason = fault_reason;
	}
	public String getAsset_person() {
		return asset_person;
	}
	public void setAsset_person(String asset_person) {
		this.asset_person = asset_person;
	}
	public String getDefect_time() {
		return defect_time;
	}
	public void setDefect_time(String defect_time) {
		this.defect_time = defect_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}

	
	
}
