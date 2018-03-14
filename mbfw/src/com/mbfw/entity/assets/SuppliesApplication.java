package com.mbfw.entity.assets;

import java.io.Serializable;
import java.util.Date;


@SuppressWarnings("serial")
public class SuppliesApplication implements Serializable{
  
	
	private Integer id;
	private String  supplies_model;
	private String  supplies_name;
	private Integer  quantity;
	private String  brand;
	private String  supplies_type;
	private String  quote_basis;
	private String  sour_of_funds;
	private String  apply_procedure;
	private String  manager;
	private String  supplier;
	private String  supplies_use;
	private String  applicant;
	private String  applicant_sector;
	private String   company_apply;
	private String time_apply;
	private String state;
	private String  remarks;
	private String Market_quotes;
	private String purchase_order;
	
	
	
	public String getMarket_quotes() {
		return Market_quotes;
	}
	public void setMarket_quotes(String market_quotes) {
		Market_quotes = market_quotes;
	}
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSupplies_type() {
		return supplies_type;
	}
	public void setSupplies_type(String supplies_type) {
		this.supplies_type = supplies_type;
	}
	public String getQuote_basis() {
		return quote_basis;
	}
	public void setQuote_basis(String quote_basis) {
		this.quote_basis = quote_basis;
	}
	public String getSour_of_funds() {
		return sour_of_funds;
	}
	public void setSour_of_funds(String sour_of_funds) {
		this.sour_of_funds = sour_of_funds;
	}
	
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getSupplies_use() {
		return supplies_use;
	}
	public void setSupplies_use(String supplies_use) {
		this.supplies_use = supplies_use;
	}
	public String getTime_apply() {
		return time_apply;
	}
	public void setTime_apply(String time_apply) {
		this.time_apply = time_apply;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getApply_procedure() {
		return apply_procedure;
	}
	public void setApply_procedure(String apply_procedure) {
		this.apply_procedure = apply_procedure;
	}
	public String getPurchase_order() {
		return purchase_order;
	}
	public void setPurchase_order(String purchase_order) {
		this.purchase_order = purchase_order;
	}
}
