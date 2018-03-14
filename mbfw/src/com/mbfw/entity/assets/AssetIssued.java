package com.mbfw.entity.assets;

public class AssetIssued {

	private Integer id;
	private Integer idd;
	private String receive;
	private String asset_code;
	private String issued_branch;
	private String issued_department;
	private String issue_person;
	private String issue_time;
	private String issued_status;
	private String receive_branch;
	private String receive_department;
	private String receiver;
	private String receive_time;
	private String receive_proof;
	private String del_issue_flag;
	private String  instruction;
	
	public Integer getIdd() {
		return idd;
	}
	public void setIdd(Integer idd) {
		this.idd = idd;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public String getIssued_department() {
		return issued_department;
	}
	public void setIssued_department(String issued_department) {
		this.issued_department = issued_department;
	}
	public String getDel_issue_flag() {
		return del_issue_flag;
	}
	public void setDel_issue_flag(String del_issue_flag) {
		this.del_issue_flag = del_issue_flag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public String getAsset_code() {
		return asset_code;
	}
	public void setAsset_code(String asset_code) {
		this.asset_code = asset_code;
	}
	public String getIssued_branch() {
		return issued_branch;
	}
	public void setIssued_branch(String issued_branch) {
		this.issued_branch = issued_branch;
	}
	public String getIssue_person() {
		return issue_person;
	}
	public void setIssue_person(String issue_person) {
		this.issue_person = issue_person;
	}
	public String getIssue_time() {
		return issue_time;
	}
	public void setIssue_time(String issue_time) {
		this.issue_time = issue_time;
	}
	public String getIssued_status() {
		return issued_status;
	}
	public void setIssued_status(String issued_status) {
		this.issued_status = issued_status;
	}
	public String getReceive_branch() {
		return receive_branch;
	}
	public void setReceive_branch(String receive_branch) {
		this.receive_branch = receive_branch;
	}
	public String getReceive_department() {
		return receive_department;
	}
	public void setReceive_department(String receive_department) {
		this.receive_department = receive_department;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}
	public String getReceive_proof() {
		return receive_proof;
	}
	public void setReceive_proof(String receive_proof) {
		this.receive_proof = receive_proof;
	}
	
	
}
