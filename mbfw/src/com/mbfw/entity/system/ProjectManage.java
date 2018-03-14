package com.mbfw.entity.system;

import java.math.BigDecimal;

/**
 * 项目
 * @author:	   	LCL
 * @date: 	   	2017-9-14
 * @description:对应数据库表：project_manager
 */
public class ProjectManage{
	
	private Long apply_id;				//项目立项ID
	private String apply_name;			//项目立项名称
	private String apply_company;		
	private String apply_dept;
	private String apply_procedure;
	private String apply_time;
	private String apply_person;
	private String device_name;
	private BigDecimal device_price;
	private String device_model;
	private Integer device_number;
	private String device_purpose;
	private Integer device_use_years;
	private String apply_reason;
	private String apply_report_address;
	private String apply_status;
	private String apply_opinion;
	private String apply_remarks;
	
	public Long getApply_id() {
		return apply_id;
	}
	public String getApply_name() {
		return apply_name;
	}
	public String getApply_company() {
		return apply_company;
	}
	public String getApply_dept() {
		return apply_dept;
	}
	public String getApply_procedure() {
		return apply_procedure;
	}
	public String getApply_time() {
		return apply_time;
	}
	public String getApply_person() {
		return apply_person;
	}
	public String getDevice_name() {
		return device_name;
	}
	public BigDecimal getDevice_price() {
		return device_price;
	}
	public String getDevice_model() {
		return device_model;
	}
	public Integer getDevice_number() {
		return device_number;
	}
	public String getDevice_purpose() {
		return device_purpose;
	}
	public Integer getDevice_use_years() {
		return device_use_years;
	}
	public String getApply_reason() {
		return apply_reason;
	}
	public String getApply_report_address() {
		return apply_report_address;
	}
	public String getApply_status() {
		return apply_status;
	}
	public String getPply_opinion() {
		return apply_opinion;
	}
	public String getApply_remarks() {
		return apply_remarks;
	}
	public void setApply_id(Long apply_id) {
		this.apply_id = apply_id;
	}
	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}
	public void setApply_company(String apply_company) {
		this.apply_company = apply_company;
	}
	public void setApply_dept(String apply_dept) {
		this.apply_dept = apply_dept;
	}
	public void setApply_procedure(String apply_procedure) {
		this.apply_procedure = apply_procedure;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public void setApply_person(String apply_person) {
		this.apply_person = apply_person;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public void setDevice_price(BigDecimal device_price) {
		this.device_price = device_price;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public void setDevice_number(Integer device_number) {
		this.device_number = device_number;
	}
	public void setDevice_purpose(String device_purpose) {
		this.device_purpose = device_purpose;
	}
	public void setDevice_use_years(Integer device_use_years) {
		this.device_use_years = device_use_years;
	}
	public void setApply_reason(String apply_reason) {
		this.apply_reason = apply_reason;
	}
	public void setApply_report_address(String apply_report_address) {
		this.apply_report_address = apply_report_address;
	}
	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
	}
	public void setPply_opinion(String apply_opinion) {
		this.apply_opinion = apply_opinion;
	}
	public void setApply_remarks(String apply_remarks) {
		this.apply_remarks = apply_remarks;
	}

}
