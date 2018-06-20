package com.readboy.ssm.po;

import static android.R.attr.data;

public class VisitOtherInfo {
    private Integer id;

    private String attachType;

    private String attachName;

    private String fileName;

    private String date;

    private Integer visitorId;

    private Integer clientNum;


    private boolean downOver = false;//附件文件是否下载完成  默认是false

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType == null ? null : attachType.trim();
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName == null ? null : attachName.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
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

    public boolean isDownOver() {
        return downOver;
    }

    public void setDownOver(boolean downOver) {
        this.downOver = downOver;
    }
}