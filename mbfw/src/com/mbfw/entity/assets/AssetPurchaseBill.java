package com.mbfw.entity.assets;

import java.math.BigDecimal;

public class AssetPurchaseBill implements java.io.Serializable{
	private Integer id;
	private Integer apply_id;
	private String purchase_code;
	private String project_name;
	private String purchase_asset_name;
	private String purchase_asset_class;
	private Integer purchase_asset_count;
	private String provider_name;
	private String provider_code;
	private BigDecimal purchase_price;
	private String purchase_person;
	private String check_person;
	private String purchase_way;
	private String delivery_date;
	private String dispatch_way;
	private String flag;
	private String provider;
	private String money_from;
	private String puchase_payway;
	private String purchase_upload;
	private String purchase_bill_status;
	private String device_code;
	
	private String file_hidden;
	private String file_address;
	private String superior_organization_name;
	
	
	
	
	public String getSuperior_organization_name() {
		return superior_organization_name;
	}
	public void setSuperior_organization_name(String superior_organization_name) {
		this.superior_organization_name = superior_organization_name;
	}
	public Integer getApply_id() {
		return apply_id;
	}
	public void setApply_id(Integer apply_id) {
		this.apply_id = apply_id;
	}
	public String getFile_hidden() {
		return file_hidden;
	}
	public void setFile_hidden(String file_hidden) {
		this.file_hidden = file_hidden;
	}
	public String getFile_address() {
		return file_address;
	}
	public void setFile_address(String file_address) {
		this.file_address = file_address;
	}
	public String getPurchase_bill_status() {
		return purchase_bill_status;
	}
	public void setPurchase_bill_status(String purchase_bill_status) {
		this.purchase_bill_status = purchase_bill_status;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPurchase_code() {
		return purchase_code;
	}
	public void setPurchase_code(String purchase_code) {
		this.purchase_code = purchase_code;
	}
	
	
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getPurchase_asset_name() {
		return purchase_asset_name;
	}
	public void setPurchase_asset_name(String purchase_asset_name) {
		this.purchase_asset_name = purchase_asset_name;
	}
	public String getPurchase_asset_class() {
		return purchase_asset_class;
	}
	public void setPurchase_asset_class(String purchase_asset_class) {
		this.purchase_asset_class = purchase_asset_class;
	}
	public Integer getPurchase_asset_count() {
		return purchase_asset_count;
	}
	public void setPurchase_asset_count(Integer purchase_asset_count) {
		this.purchase_asset_count = purchase_asset_count;
	}
	
	public String getProvider_name() {
		return provider_name;
	}
	public void setProvider_name(String provider_name) {
		this.provider_name = provider_name;
	}
	public String getProvider_code() {
		return provider_code;
	}
	public void setProvider_code(String provider_code) {
		this.provider_code = provider_code;
	}
	public BigDecimal getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(BigDecimal purchase_price) {
		this.purchase_price = purchase_price;
	}
	public String getPurchase_person() {
		return purchase_person;
	}
	public void setPurchase_person(String purchase_person) {
		this.purchase_person = purchase_person;
	}
	public String getCheck_person() {
		return check_person;
	}
	public void setCheck_person(String check_person) {
		this.check_person = check_person;
	}
	public String getPurchase_way() {
		return purchase_way;
	}
	public void setPurchase_way(String purchase_way) {
		this.purchase_way = purchase_way;
	}
	
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getDispatch_way() {
		return dispatch_way;
	}
	public void setDispatch_way(String dispatch_way) {
		this.dispatch_way = dispatch_way;
	}
	public String getMoney_from() {
		return money_from;
	}
	public void setMoney_from(String money_from) {
		this.money_from = money_from;
	}
	public String getPuchase_payway() {
		return puchase_payway;
	}
	public void setPuchase_payway(String puchase_payway) {
		this.puchase_payway = puchase_payway;
	}
	public String getPurchase_upload() {
		return purchase_upload;
	}
	public void setPurchase_upload(String purchase_upload) {
		this.purchase_upload = purchase_upload;
	}
	public String getDevice_code() {
		return device_code;
	}
	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}
	
}
