package com.mbfw.entity.assets;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SuppliesUse implements Serializable{
    /**
	 * 
	 */
	
	private Integer id;
    private String supplies_model;
    private String supplies_name;
    private String depaerment;
    private String quantity;
    private String leader;
    private String release_people;
    private String company;
    private String time;
    private String remarks;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSupplies_name() {
		return supplies_name;
	}
	public void setSupplies_name(String supplies_name) {
		this.supplies_name = supplies_name;
	}
	public String getDepaerment() {
		return depaerment;
	}
	public void setDepaerment(String depaerment) {
		this.depaerment = depaerment;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getRelease_people() {
		return release_people;
	}
	public void setRelease_people(String release_people) {
		this.release_people = release_people;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public String getSupplies_model() {
		return supplies_model;
	}
	public void setSupplies_model(String supplies_model) {
		this.supplies_model = supplies_model;
	}
	
}
