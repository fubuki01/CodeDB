package com.readboy.ssm.po;

/**
 * Created by Wyd on 2018/1/8.
 * 通话记录
 */

public class CallRecord {
    private String callTime;
    private String callPhoneNumber;

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallPhoneNumber() {
        return callPhoneNumber;
    }

    public void setCallPhoneNumber(String callPhoneNumber) {
        this.callPhoneNumber = callPhoneNumber;
    }

    public CallRecord(String callTime, String callPhoneNumber) {
        this.callTime = callTime;
        this.callPhoneNumber = callPhoneNumber;
    }
}
