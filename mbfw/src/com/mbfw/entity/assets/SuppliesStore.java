package com.mbfw.entity.assets;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class SuppliesStore implements Serializable{
  
	private Integer id;
	private String supplies_type;
	private String  supplies_model;
	private String  supplies_brand;
	private String  supplies_years;
	private String  supplies_name;
	private Integer  actual_amount;
	private BigDecimal  purchase_price;
	private String  bill;
	private String  location;
	private String  applicant;
	private String  applicant_sector;
	private String  company_apply;
	private String  remarks;
	private String  time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSupplies_model() {
		return supplies_model;
	}
	public void setSupplies_model(String supplies_model) {
		this.supplies_model = supplies_model;
	}
	public String getSupplies_name() {
		return supplies_name;
	}
	public void setSupplies_name(String supplies_name) {
		this.supplies_name = supplies_name;
	}
	public Integer getActual_amount() {
		return actual_amount;
	}
	public void setActual_amount(Integer actual_amount) {
		this.actual_amount = actual_amount;
	}
	
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getCompany_apply() {
		return company_apply;
	}
	public void setCompany_apply(String company_apply) {
		this.company_apply = company_apply;
	}
	public String getApplicant_sector() {
		return applicant_sector;
	}
	public void setApplicant_sector(String applicant_sector) {
		this.applicant_sector = applicant_sector;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigDecimal getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(BigDecimal purchase_price) {
		this.purchase_price = purchase_price;
	}
	public String getSupplies_type() {
		return supplies_type;
	}
	public void setSupplies_type(String supplies_type) {
		this.supplies_type = supplies_type;
	}
	
	public String getSupplies_brand() {
		return supplies_brand;
	}
	public void setSupplies_brand(String supplies_brand) {
		this.supplies_brand = supplies_brand;
	}
	public String getSupplies_years() {
		return supplies_years;
	}
	public void setSupplies_years(String supplies_years) {
		this.supplies_years = supplies_years;
	}
	
}
