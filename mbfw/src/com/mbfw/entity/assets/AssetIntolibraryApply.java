package com.mbfw.entity.assets;

import java.io.Serializable;

public class AssetIntolibraryApply implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String  into_purchase_bill;
	private String  into_code;
	private String  into_time;
	private String  into_person;
	private String  into_device;
	private String  into_count;
	private String  into_house;
	private String  asset_total_money;
	private String into_note;
	private String into_status;
	private String device_code;
	private Integer product_count_flag;
	
	
	
	public Integer getProduct_count_flag() {
		return product_count_flag;
	}
	public void setProduct_count_flag(Integer product_count_flag) {
		this.product_count_flag = product_count_flag;
	}
	public String getInto_status() {
		return into_status;
	}
	public void setInto_status(String into_status) {
		this.into_status = into_status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInto_purchase_bill() {
		return into_purchase_bill;
	}
	public void setInto_purchase_bill(String into_purchase_bill) {
		this.into_purchase_bill = into_purchase_bill;
	}
	public String getInto_code() {
		return into_code;
	}
	public void setInto_code(String into_code) {
		this.into_code = into_code;
	}
	public String getInto_time() {
		return into_time;
	}
	public void setInto_time(String into_time) {
		this.into_time = into_time;
	}
	public String getInto_person() {
		return into_person;
	}
	public void setInto_person(String into_person) {
		this.into_person = into_person;
	}
	public String getInto_device() {
		return into_device;
	}
	public void setInto_device(String into_device) {
		this.into_device = into_device;
	}
	public String getInto_count() {
		return into_count;
	}
	public void setInto_count(String into_count) {
		this.into_count = into_count;
	}
	public String getInto_house() {
		return into_house;
	}
	public void setInto_house(String into_house) {
		this.into_house = into_house;
	}
	public String getAsset_total_money() {
		return asset_total_money;
	}
	public void setAsset_total_money(String asset_total_money) {
		this.asset_total_money = asset_total_money;
	}
	public String getInto_note() {
		return into_note;
	}
	public void setInto_note(String into_note) {
		this.into_note = into_note;
	}
	public String getDevice_code() {
		return device_code;
	}
	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}
	
}
