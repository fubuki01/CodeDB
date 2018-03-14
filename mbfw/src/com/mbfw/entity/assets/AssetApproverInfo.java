package com.mbfw.entity.assets;
/**
 * 
 * @author scw
 * 2017.09.13
 * function:审核人员实体类
 *
 */
public class AssetApproverInfo {
	public Integer approver_Id;    //审批表ID，自增
	public String user_Id;          //用户ID
	public String user_Name;            //用户真实姓名
	public String approver_Description;   //审批人描叙信息
	public String approver_Rights_Description; //审批人权限描述
	public Integer getApprover_Id() {
		return approver_Id;
	}
	public void setApprover_Id(Integer approver_Id) {
		this.approver_Id = approver_Id;
	}
	public String getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getApprover_Description() {
		return approver_Description;
	}
	public void setApprover_Description(String approver_Description) {
		this.approver_Description = approver_Description;
	}
	public String getApprover_Rights_Description() {
		return approver_Rights_Description;
	}
	public void setApprover_Rights_Description(String approver_Rights_Description) {
		this.approver_Rights_Description = approver_Rights_Description;
	}
	
	
	
}
