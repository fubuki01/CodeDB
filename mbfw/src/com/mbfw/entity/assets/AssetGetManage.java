package com.mbfw.entity.assets;

public class AssetGetManage implements java.io.Serializable {
	
	private Integer id;
	private String asset_code;
	private String asset_name;
	private String asset_use_company;
	private String asset_use_dept;
	private String asset_user;
	private String orig_status;
	private String get_time;
	private String asset_use;
	private String asset_detail_config;
	private String asset_standard_model;
	private String get_manager;
	private String get_remark;
	private String is_valid;
	
	public String getGet_remark() {
		return get_remark;
	}
	public void setGet_remark(String get_remark) {
		this.get_remark = get_remark;
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
	public String getGet_time() {
		return get_time;
	}
	public void setGet_time(String get_time) {
		this.get_time = get_time;
	}
	public String getAsset_use() {
		return asset_use;
	}
	public void setAsset_use(String asset_use) {
		this.asset_use = asset_use;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getAsset_standard_model() {
		return asset_standard_model;
	}
	public void setAsset_standard_model(String asset_standard_model) {
		this.asset_standard_model = asset_standard_model;
	}
	public String getGet_manager() {
		return get_manager;
	}
	public void setGet_manager(String get_manager) {
		this.get_manager = get_manager;
	}
	public String getAsset_name() {
		return asset_name;
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
	public String getAsset_detail_config() {
		return asset_detail_config;
	}
	public void setAsset_detail_config(String asset_detail_config) {
		this.asset_detail_config = asset_detail_config;
	}
	public String getOrig_status() {
		return orig_status;
	}
	public void setOrig_status(String orig_status) {
		this.orig_status = orig_status;
	}

}
