package com.readboy.ssm.po;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/12.
 */

public class VisitBaseInfo implements Serializable{

    private Integer id;
    private String type;
    private String gift;
    private String summery;
    private String location;
    private Date date;
    private Integer visitorId;
    private Integer clientNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift == null ? null : gift.trim();
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery == null ? null : summery.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getDate() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public void setClientNum(Integer clientNum) {
        this.clientNum = clientNum;
    }

    @Override
    public String toString() {
        return "VisitBasicInfo [type=" + type + ", gift=" + gift + ", summery=" + summery + ", location="
                + location + ", date=" + date + ", visitorId=" + visitorId + ", clientNum=" + clientNum + "]";
    }
}
