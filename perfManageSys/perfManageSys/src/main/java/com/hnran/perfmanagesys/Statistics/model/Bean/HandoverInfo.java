package com.hnran.perfmanagesys.Statistics.model.Bean;

/**
 * Created by Administrator on 2018/5/11.
 */

public class HandoverInfo {

    private String zzjc;		//机构名称，组织简称
    private String ygxm;  		//员工姓名
    private StaffJj staffJj;

    public String getZzjc() {
        return zzjc;
    }
    public void setZzjc(String zzjc) {
        this.zzjc = zzjc;
    }
    public String getYgxm() {
        return ygxm;
    }
    public void setYgxm(String ygxm) {
        this.ygxm = ygxm;
    }
    public StaffJj getStaffJj() {
        return staffJj;
    }
    public void setStaffJj(StaffJj staffJj) {
        this.staffJj = staffJj;
    }
}
