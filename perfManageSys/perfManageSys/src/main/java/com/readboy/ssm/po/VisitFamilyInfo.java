package com.readboy.ssm.po;

import java.io.Serializable;

public class VisitFamilyInfo implements Serializable {

    private Integer id;
    private String memberName;
    private String credentialType;
    private String credentialNum;
    private String relationship;
    private Integer clientNum;
    private Integer visitorId;
    private String phone;
    private String memberType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType == null ? null : credentialType.trim();
    }

    public String getCredentialNum() {
        return credentialNum;
    }

    public void setCredentialNum(String credentialNum) {
        this.credentialNum = credentialNum;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    @Override
    public String toString() {
        return "VisitFamilyInfo [id=" + id + ", memberName=" + memberName + ", credentialType=" + credentialType
                + ", credentialNum=" + credentialNum + ", relationship=" + relationship + ", clientNum=" + clientNum
                + ", visitorId=" + visitorId + ", phone=" + phone + ", memberType=" + memberType + "]";
    }
}