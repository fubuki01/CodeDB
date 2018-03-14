package com.mbfw.entity.assets;

import java.io.Serializable;

/**
 * 
 * @author scw
 * 2017-10-11
 *function:用户的部门权限管理，对应数据库中的user_departmentauthority
 */

public class UserDepartmentAuthority implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id ; //表的自增序号
	private Integer authority_Code ; //部门权限编号
	private String authority_Name ; //部门权限名称
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuthority_Code() {
		return authority_Code;
	}
	public void setAuthority_Code(Integer authority_Code) {
		this.authority_Code = authority_Code;
	}
	public String getAuthority_Name() {
		return authority_Name;
	}
	public void setAuthority_Name(String authority_Name) {
		this.authority_Name = authority_Name;
	}
	
}
