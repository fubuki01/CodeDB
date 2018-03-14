package com.mbfw.entity.assets;

/**
 * 机构信息bean
 * @author Wyd
 *
 */
public class InstitutionalInfo {
	private Integer organizational_identification;//组织
	private String business_organization_code;//业务机构代码
	private String organization_name;//组织名称
	private String organization_abbreviation;//组织简称
	private Integer superior_organizational_identification;//上级组织标识
	private String superior_organization_name;//上级组织名称
	private String business_organization_nature;//业务机构性质
	private String organization_class;//组织类别
	private String organizational_level;//组织级别 
	private String enable_identification;//启用标识
	private String report_authority_organization_code;//报表权限机构代码
	private String business_organization_identifier;//业务机构标识
	private String business_organization_type;//业务机构类型
	private Integer sort_ordinal;//序号
	private String location;//所在区域
	private String key;//查询的关键字
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getOrganizational_identification() {
		return organizational_identification;
	}
	public void setOrganizational_identification(Integer organizational_identification) {
		this.organizational_identification = organizational_identification;
	}
	public String getBusiness_organization_code() {
		return business_organization_code;
	}
	public void setBusiness_organization_code(String business_organization_code) {
		this.business_organization_code = business_organization_code;
	}
	public String getOrganization_name() {
		return organization_name;
	}
	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}
	public String getOrganization_abbreviation() {
		return organization_abbreviation;
	}
	public void setOrganization_abbreviation(String organization_abbreviation) {
		this.organization_abbreviation = organization_abbreviation;
	}
	public Integer getSuperior_organizational_identification() {
		return superior_organizational_identification;
	}
	public void setSuperior_organizational_identification(Integer superior_organizational_identification) {
		this.superior_organizational_identification = superior_organizational_identification;
	}
	public String getSuperior_organization_name() {
		return superior_organization_name;
	}
	public void setSuperior_organization_name(String superior_organization_name) {
		this.superior_organization_name = superior_organization_name;
	}
	public String getBusiness_organization_nature() {
		return business_organization_nature;
	}
	public void setBusiness_organization_nature(String business_organization_nature) {
		this.business_organization_nature = business_organization_nature;
	}
	public String getOrganization_class() {
		return organization_class;
	}
	public void setOrganization_class(String organization_class) {
		this.organization_class = organization_class;
	}
	public String getOrganizational_level() {
		return organizational_level;
	}
	public void setOrganizational_level(String organizational_level) {
		this.organizational_level = organizational_level;
	}
	public String getEnable_identification() {
		return enable_identification;
	}
	public void setEnable_identification(String enable_identification) {
		this.enable_identification = enable_identification;
	}
	public String getReport_authority_organization_code() {
		return report_authority_organization_code;
	}
	public void setReport_authority_organization_code(String report_authority_organization_code) {
		this.report_authority_organization_code = report_authority_organization_code;
	}
	public String getBusiness_organization_identifier() {
		return business_organization_identifier;
	}
	public void setBusiness_organization_identifier(String business_organization_identifier) {
		this.business_organization_identifier = business_organization_identifier;
	}
	public String getBusiness_organization_type() {
		return business_organization_type;
	}
	public void setBusiness_organization_type(String business_organization_type) {
		this.business_organization_type = business_organization_type;
	}
	public Integer getSort_ordinal() {
		return sort_ordinal;
	}
	public void setSort_ordinal(Integer sort_ordinal) {
		this.sort_ordinal = sort_ordinal;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
