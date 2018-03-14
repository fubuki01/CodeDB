package com.mbfw.entity.assets;

public class AssetRecycleManage implements java.io.Serializable{

	private Integer id;
	private String asset_code;
	private String asset_name;
	private String orig_company;
	private String orig_department;
	private String orig_user;
	private String orig_status;
	private String recycle_reason;
	private String recycle_time;
	private String recycleman;
	private String recycle_manager;
	private String recycle_remark;
	private String is_valid;
	
	
	public String getRecycle_remark() {
		return recycle_remark;
	}
	public void setRecycle_remark(String recycle_remark) {
		this.recycle_remark = recycle_remark;
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
	public String getOrig_department() {
		return orig_department;
	}
	public void setOrig_department(String orig_department) {
		this.orig_department = orig_department;
	}
	public String getOrig_user() {
		return orig_user;
	}
	public void setOrig_user(String orig_user) {
		this.orig_user = orig_user;
	}
	public String getRecycle_reason() {
		return recycle_reason;
	}
	public void setRecycle_reason(String recycle_reason) {
		this.recycle_reason = recycle_reason;
	}
	public String getRecycle_time() {
		return recycle_time;
	}
	public void setRecycle_time(String recycle_time) {
		this.recycle_time = recycle_time;
	}
	public String getRecycleman() {
		return recycleman;
	}
	public void setRecycleman(String recycleman) {
		this.recycleman = recycleman;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getRecycle_manager() {
		return recycle_manager;
	}
	public void setRecycle_manager(String recycle_manager) {
		this.recycle_manager = recycle_manager;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public String getOrig_company() {
		return orig_company;
	}
	public void setOrig_company(String orig_company) {
		this.orig_company = orig_company;
	}
	public String getOrig_status() {
		return orig_status;
	}
	public void setOrig_status(String orig_status) {
		this.orig_status = orig_status;
	}
}
