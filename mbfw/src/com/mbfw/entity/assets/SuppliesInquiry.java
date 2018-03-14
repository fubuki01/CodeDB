package com.mbfw.entity.assets;

import java.io.Serializable;
import java.math.BigDecimal;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;

@SuppressWarnings("serial")
public class SuppliesInquiry implements Serializable{
    
	private Integer id;
	private String  supplies_model;
	private String  supplies_name;
	private String  supplies_type;
	private String  supplies_brand;
	private String  supplies_use;
	private Integer  inventory_quantity;
	private String applicant_sector; 
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
	private String company_apply;
	private Integer  market_quotes;
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
	public String getSupplies_use() {
		return supplies_use;
	}
	public void setSupplies_use(String supplies_use) {
		this.supplies_use = supplies_use;
	}
	public Integer getInventory_quantity() {
		return inventory_quantity;
	}
	public void setInventory_quantity(Integer inventory_quantity) {
		this.inventory_quantity = inventory_quantity;
	}
	public Integer getMarket_quotes() {
		return market_quotes;
	}
	public void setMarket_quotes(Integer market_quotes) {
		this.market_quotes = market_quotes;
	}
	public String getQuote_basis() {
		return quote_basis;
	}
	public void setQuote_basis(String quote_basis) {
		this.quote_basis = quote_basis;
	}
	public BigDecimal getAmount_money() {
		return amount_money;
	}
	public void setAmount_money(BigDecimal amount_money) {
		this.amount_money = amount_money;
	}
	public Integer getSupplies_years() {
		return supplies_years;
	}
	public void setSupplies_years(Integer supplies_years) {
		this.supplies_years = supplies_years;
	}
	public String getStorage_location() {
		return storage_location;
	}
	public void setStorage_location(String storage_location) {
		this.storage_location = storage_location;
	}
	public String getAdministrator() {
		return administrator;
	}
	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}
	public String getPurchase_time() {
		return purchase_time;
	}
	public void setPurchase_time(String purchase_time) {
		this.purchase_time = purchase_time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	private String  quote_basis;
	private BigDecimal  amount_money;
	private Integer  supplies_years;
	private String  storage_location;
	private String  administrator;
	private String  purchase_time;
	private String  remarks;
}
