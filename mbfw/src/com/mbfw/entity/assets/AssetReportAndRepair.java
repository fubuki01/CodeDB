package com.mbfw.entity.assets;

/**
 * 类名称：AssetEquipment.java
 * 
 * @author 揭尹  创建时间：2017年9月11日
 * @version 1.0
 */
public class AssetReportAndRepair implements java.io.Serializable {
	private int id;
	private String asset_code;
	private String bank_name;
	private String rep_department;
	private String drep_department;
	private String repair_time;
	private String fault_phenomen;
	private String fault_reason;
	private String asset_person;
	private String defect_period;
	private String maintain_result;
	private int maintain_cost;
	

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
	public String getRep_department() {
		return rep_department;
	}
	public void setRep_department(String rep_department) {
		this.rep_department = rep_department;
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
	public String getDefect_period() {
		return defect_period;
	}
	public void setDefect_period(String defect_period) {
		this.defect_period = defect_period;
	}
	public String getMaintain_result() {
		return maintain_result;
	}
	public void setMaintain_result(String maintain_result) {
		this.maintain_result = maintain_result;
	}
	public int getMaintain_cost() {
		return maintain_cost;
	}
	public void setMaintain_cost(int maintain_cost) {
		this.maintain_cost = maintain_cost;
	}
	
	
}
