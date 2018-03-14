package com.mbfw.entity.assets;


/**
 * 
* @ClassName: Department 
* @Description: 部门bean
* @author scw 
* @date 2017-09-21
*
 */
public class AssetDepartmentManage {
	 private Integer department_Id ; //部门表的ID
	 private String department_Name ; // 部门名称
	 private String department_Code; //部门代号
	 private String department_Pro ; //部门上级
	 private Integer department_Grade ; //部门等级
	 private String department_Style  ; //部门类型
	 private String department_PrincipalName ; //部门负责人名称
	 private String department_PrincipalId ; //部门负责人userId
	 
	 
	public Integer getDepartment_Id() {
		return department_Id;
	}
	public void setDepartment_Id(Integer department_Id) {
		this.department_Id = department_Id;
	}
	public String getDepartment_Name() {
		return department_Name;
	}
	public void setDepartment_Name(String department_Name) {
		this.department_Name = department_Name;
	}
	public String getDepartment_Code() {
		return department_Code;
	}
	public void setDepartment_Code(String department_Code) {
		this.department_Code = department_Code;
	}
	public String getDepartment_Pro() {
		return department_Pro;
	}
	public void setDepartment_Pro(String department_Pro) {
		this.department_Pro = department_Pro;
	}
	public Integer getDepartment_Grade() {
		return department_Grade;
	}
	public void setDepartment_Grade(Integer department_Grade) {
		this.department_Grade = department_Grade;
	}
	public String getDepartment_Style() {
		return department_Style;
	}
	public void setDepartment_Style(String department_Style) {
		this.department_Style = department_Style;
	}
	public String getDepartment_PrincipalName() {
		return department_PrincipalName;
	}
	public void setDepartment_PrincipalName(String department_PrincipalName) {
		this.department_PrincipalName = department_PrincipalName;
	}
	public String getDepartment_PrincipalId() {
		return department_PrincipalId;
	}
	public void setDepartment_PrincipalId(String department_PrincipalId) {
		this.department_PrincipalId = department_PrincipalId;
	}
                         	
}
