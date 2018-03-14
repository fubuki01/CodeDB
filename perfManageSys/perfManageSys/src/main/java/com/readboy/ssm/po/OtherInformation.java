package com.readboy.ssm.po;

/**
 * Created by Wyd on 2018/1/3.
 * 其他附件信息对应数据表
 */

public class OtherInformation {
    private Integer id;//附件ID
    private String attachType;//附件种类
    private String attachName;//附件名称
    private String fileUrl;//附件存放路径

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
        this.attachType = attachType;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
