package com.mbfw.entity.assets;

import java.io.Serializable;
import java.math.BigDecimal;

public class AssetInfo implements Serializable {
	private static final long serialVersionUID = 2L;
	private Integer id;
	private String asset_name;
	private String asset_code;
	private String asset_class;
	private String asset_standard_model;
	private String asset_sn;
	private String asset_detail_config;
	private String asset_unit;
	private BigDecimal asset_price;
	private String asset_usecompany_code;
	private String asset_use_company;
	private String asset_use_dept;
	private String asset_usedept_code;
	private String asset_user;
	private String asset_storehouse;
	private String asset_manager;
	private String asset_purchase_time;
	private String asset_provider;
	private String asset_max_years;
	private String asset_qr_code;
	private String asset_brand;
	private String asset_status;
	private String asset_into_bill;
	private String asset_use;
	private String asset_nod;
	private Integer register_flag;
	private Integer delete_flag;
	private String asset_image;
	
	private String asset_name_select;
	private String product_code;
	private String product_total;
	
	
	
	public String getProduct_total() {
		return product_total;
	}
	public void setProduct_total(String product_total) {
		this.product_total = product_total;
	}
	public String getAsset_image() {
		return asset_image;
	}
	public void setAsset_image(String asset_image) {
		this.asset_image = asset_image;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getAsset_name_select() {
		return asset_name_select;
	}
	public void setAsset_name_select(String asset_name_select) {
		this.asset_name_select = asset_name_select;
	}
	public String getAsset_use() {
		return asset_use;
	}
	public void setAsset_use(String asset_use) {
		this.asset_use = asset_use;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}
	public void setAsset_name(String asset_name){
		this.asset_name = asset_name;
	}
	public String getAsset_name(){
		return asset_name;
	}
	public void setAsset_code(String asset_code){
		this.asset_code = asset_code;
	}
	public String getAsset_code(){
		return asset_code;
	}
	public void setAsset_class(String asset_class){
		this.asset_class = asset_class;
	}
	public String getAsset_class(){
		return asset_class;
	}
	public void setAsset_standard_model(String asset_standard_model){
		this.asset_standard_model = asset_standard_model;
	}
	public String getAsset_standard_model(){
		return asset_standard_model;
	}
	public void setAsset_sn(String asset_sn){
		this.asset_sn = asset_sn;
	}
	public String getAsset_sn(){
		return asset_sn;
	}
	public void setAsset_detail_config(String asset_detail_config){
		this.asset_detail_config = asset_detail_config;
	}
	public String getAsset_detail_config(){
		return asset_detail_config;
	}
	public void setAsset_unit(String asset_unit){
		this.asset_unit = asset_unit;
	}
	public String getAsset_unit(){
		return asset_unit;
	}
	public void setAsset_price(BigDecimal asset_price){
		this.asset_price = asset_price;
	}
	public BigDecimal getAsset_price(){
		if(asset_price == null)
			return new BigDecimal(0);
		return asset_price;
	}
	public void setAsset_usecompany_code(String asset_usecompany_code){
		this.asset_usecompany_code = asset_usecompany_code;
	}
	public String getAsset_usecompany_code(){
		return asset_usecompany_code;
	}
	public void setAsset_use_company(String asset_use_company){
		this.asset_use_company = asset_use_company;
	}
	public String getAsset_use_company(){
		return asset_use_company;
	}
	public void setAsset_use_dept(String asset_use_dept){
		this.asset_use_dept = asset_use_dept;
	}
	public String getAsset_use_dept(){
		return asset_use_dept;
	}
	public void setAsset_usedept_code(String asset_usedept_code){
		this.asset_usedept_code = asset_usedept_code;
	}
	public String getAsset_usedept_code(){
		return asset_usedept_code;
	}
	public void setAsset_user(String asset_user){
		this.asset_user = asset_user;
	}
	public String getAsset_user(){
		return asset_user;
	}
	public void setAsset_storehouse(String asset_storehouse){
		this.asset_storehouse = asset_storehouse;
	}
	public String getAsset_storehouse(){
		return asset_storehouse;
	}
	public void setAsset_manager(String asset_manager){
		this.asset_manager = asset_manager;
	}
	public String getAsset_manager(){
		return asset_manager;
	}
	public void setAsset_purchase_time(String asset_purchase_time){
		this.asset_purchase_time = asset_purchase_time;
	}
	public String getAsset_purchase_time(){
		return asset_purchase_time;
	}
	public void setAsset_provider(String asset_provider){
		this.asset_provider = asset_provider;
	}
	public String getAsset_provider(){
		return asset_provider;
	}
	public void setAsset_max_years(String asset_max_years){
		this.asset_max_years = asset_max_years;
	}
	public String getAsset_max_years(){
		return asset_max_years;
	}
	public void setAsset_qr_code(String asset_qr_code){
		this.asset_qr_code = asset_qr_code;
	}
	public String getAsset_qr_code(){
		return asset_qr_code;
	}
	public void setAsset_brand(String asset_brand){
		this.asset_brand = asset_brand;
	}
	public String getAsset_brand(){
		return asset_brand;
	}
	public void setAsset_status(String asset_status){
		this.asset_status = asset_status;
	}
	public String getAsset_status(){
		return asset_status;
	}
	public void setAsset_into_bill(String asset_into_bill){
		this.asset_into_bill = asset_into_bill;
	}
	public String getAsset_into_bill(){
		return asset_into_bill;
	}
	public void setAsset_nod(String asset_nod){
		this.asset_nod = asset_nod;
	}
	public String getAsset_nod(){
		return asset_nod;
	}
	public Integer getRegister_flag() {
		return register_flag;
	}
	public void setRegister_flag(Integer register_flag) {
		this.register_flag = register_flag;
	}
	public Integer getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	
}
