package com.mbfw.entity.assets;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AssetAlter implements Serializable{
	
	private Integer id;
	private String   asset_code;
	private String   chan_config;
	private String   deadline;
	private String   reason_change;
	private String   config_sour;
	private String    applicant;
	private String    config_cost;
	private String   applicant_sector;
	private String   company_apply;
	private String   time;
	private String   remarks;
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
	public String getChan_config() {
		return chan_config;
	}
	public void setChan_config(String chan_config) {
		this.chan_config = chan_config;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getReason_change() {
		return reason_change;
	}
	public void setReason_change(String reason_change) {
		this.reason_change = reason_change;
	}
	public String getConfig_sour() {
		return config_sour;
	}
	public void setConfig_sour(String config_sour) {
		this.config_sour = config_sour;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getApplicant_sector() {
		return applicant_sector;
	}
	public void setApplicant_sector(String applicant_sector) {
		this.applicant_sector = applicant_sector;
	}
	public String getCompany_apply() {
		return company_apply;
	}
	public void setCompany_apply(String company_apply) {
		this.company_apply = company_apply;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getConfig_cost() {
		return config_cost;
	}
	public void setConfig_cost(String config_cost) {
		this.config_cost = config_cost;
	}
	
	
	
}
