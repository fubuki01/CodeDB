package com.mbfw.entity.system;

import com.mbfw.entity.Page;

/**
 * 类名称：User.java
 * 
 * @author 研发中心 作者单位： 创建时间：2014年6月28日
 * @version 1.0
 */
public class User {
	private String USER_ID; // 用户id
	private String USERNAME; // 用户名
	private String PASSWORD; // 密码
	private String NAME; // 姓名
	private String RIGHTS; // 权限
	private String ROLE_ID; // 角色id
	private String LAST_LOGIN; // 最后登录时间
	private String IP; // 用户登录ip地址
	private String STATUS; // 状态
	private Role role; // 角色对象
	private Page page; // 分页对象
	private String SKIN; // 皮肤
	private String superior_organization_name;  //上级部门信息
	private String organization_name ; //当前部门信息
	private Integer user_Permission ; //当前用户所拥有的权限
	private String creatuser_Time;//创建该用户的时间
	
	
	

	public String getCreatuser_Time() {
		return creatuser_Time;
	}

	public void setCreatuser_Time(String creatuser_Time) {
		this.creatuser_Time = creatuser_Time;
	}

	public Integer getUser_Permission() {
		return user_Permission;
	}

	public void setUser_Permission(Integer user_Permission) {
		this.user_Permission = user_Permission;
	}

	public String getSuperior_organization_name() {
		return superior_organization_name;
	}

	public void setSuperior_organization_name(String superior_organization_name) {
		this.superior_organization_name = superior_organization_name;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getSKIN() {
		return SKIN;
	}

	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getRIGHTS() {
		return RIGHTS;
	}

	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}

	public String getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}

	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
