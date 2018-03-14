package com.mbfw.entity.assets;

public class AssetAbandonManage implements java.io.Serializable{

	private Integer id;
	private String asset_code;
	private String asset_name;
	private String asset_use_company;
	private String asset_use_dept;
	private String asset_user;
	private String asset_detail_config;
	private String asset_use;
	private String orig_status;
	private String abandon_reason;
	private String abandon_time;
	private String abandon_idea;
	private String abandon_approve;
	private String approve_status;
	private String abandon_manager;
	private String abandon_remark;
	private String is_valid;
	
	public String getAbandon_remark() {
		return abandon_remark;
	}
	public void setAbandon_remark(String abandon_remark) {
		this.abandon_remark = abandon_remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public String getAsset_use_dept() {
		return asset_use_dept;
	}
	public void setAsset_use_dept(String asset_use_dept) {
		this.asset_use_dept = asset_use_dept;
	}
	public String getAsset_user() {
		return asset_user;
	}
	public void setAsset_user(String asset_user) {
		this.asset_user = asset_user;
	}
	public String getAsset_detail_config() {
		return asset_detail_config;
	}
	public void setAsset_detail_config(String asset_detail_config) {
		this.asset_detail_config = asset_detail_config;
	}
	public String getAsset_use() {
		return asset_use;
	}
	public void setAsset_use(String asset_use) {
		this.asset_use = asset_use;
	}
	public String getAbandon_reason() {
		return abandon_reason;
	}
	public void setAbandon_reason(String abandon_reason) {
		this.abandon_reason = abandon_reason;
	}
	public String getAbandon_time() {
		return abandon_time;
	}
	public void setAbandon_time(String abandon_time) {
		this.abandon_time = abandon_time;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getAbandon_idea() {
		return abandon_idea;
	}
	public void setAbandon_idea(String abandon_idea) {
		this.abandon_idea = abandon_idea;
	}
	public String getAbandon_manager() {
		return abandon_manager;
	}
	public void setAbandon_manager(String abandon_manager) {
		this.abandon_manager = abandon_manager;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public String getAbandon_approve() {
		return abandon_approve;
	}
	public void setAbandon_approve(String abandon_approve) {
		this.abandon_approve = abandon_approve;
	}
	public String getApprove_status() {
		return approve_status;
	}
	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public String getAsset_use_company() {
		return asset_use_company;
	}
	public void setAsset_use_company(String asset_use_company) {
		this.asset_use_company = asset_use_company;
	}
	public String getOrig_status() {
		return orig_status;
	}
	public void setOrig_status(String orig_status) {
		this.orig_status = orig_status;
	}
	
}
