package com.readboy.ssm.po;

/**
 * 创建日期:2017/6/23 on  11:06.
 * 作者:hewenyu
 * 公司:杭州思弘科技有限公司
 */

public class ContactsPhone {

    private String name;
    private String phoneNo;
    private String fistPinyin;
    private String pinyin;

    public ContactsPhone() {
    }

    public ContactsPhone(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public ContactsPhone(String name, String phoneNo, String fistPinyin, String pinyin) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.fistPinyin = fistPinyin;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFistPinyin() {
        return fistPinyin;
    }

    public void setFistPinyin(String fistPinyin) {
        this.fistPinyin = fistPinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "ContactsPhone{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", fistPinyin='" + fistPinyin + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
