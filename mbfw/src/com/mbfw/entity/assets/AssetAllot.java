package com.mbfw.entity.assets;


/**
 * 类名称：AssetAllot.java
 * 
 * @author 揭尹  创建时间：2017年9月11日
 * @version 1.0
 */

public class AssetAllot implements java.io.Serializable{

	private int id;
	private String asset_code;
	private String bank_name;
	private String it_department;
	private String ot_department;
	private String allot_time;
	private String allot_reason;
	private String asset_user;
	private String asset_receiver;
	private String rt_agency;
	private String idea;
	private String status;
	private String remark;
	private String allot_name;
	private String apply_procedure;
	private String user_id;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getApply_procedure() {
		return apply_procedure;
	}
	public void setApply_procedure(String apply_procedure) {
		this.apply_procedure = apply_procedure;
	}
	public String getAllot_name() {
		return allot_name;
	}
	public void setAllot_name(String allot_name) {
		this.allot_name = allot_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getIt_department() {
		return it_department;
	}
	public void setIt_department(String it_department) {
		this.it_department = it_department;
	}
	public String getOt_department() {
		return ot_department;
	}
	public void setOt_department(String ot_department) {
		this.ot_department = ot_department;
	}
	public String getAllot_time() {
		return allot_time;
	}
	public void setAllot_time(String allot_time) {
		this.allot_time = allot_time;
	}
	public String getAllot_reason() {
		return allot_reason;
	}
	public void setAllot_reason(String allot_reason) {
		this.allot_reason = allot_reason;
	}
	public String getAsset_user() {
		return asset_user;
	}
	public void setAsset_user(String asset_user) {
		this.asset_user = asset_user;
	}
	public String getAsset_receiver() {
		return asset_receiver;
	}
	public void setAsset_receiver(String asset_receiver) {
		this.asset_receiver = asset_receiver;
	}
	public String getRt_agency() {
		return rt_agency;
	}
	public void setRt_agency(String rt_agency) {
		this.rt_agency = rt_agency;
	}
	public String getIdea() {
		return idea;
	}
	public void setIdea(String idea) {
		this.idea = idea;
	}
	
	
}
