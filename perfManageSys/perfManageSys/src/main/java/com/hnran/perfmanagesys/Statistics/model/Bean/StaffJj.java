package com.hnran.perfmanagesys.Statistics.model.Bean;

/**
 * Created by Administrator on 2018/5/15.
 */

public class StaffJj {
    private String jjrq;		// 接交日期
    private String  zzbz;		// 组织标识
    private String yggh;		//员工工号
    private Integer jjgw;  	//交接岗位（1：代班会计 2：出纳 3：复核）
    private String lrsj; 		//录入时间
    private String jjgwmc;		//交接岗位名称

    public String getJjrq() {
        return jjrq;
    }
    public void setJjrq(String jjrq) {
        this.jjrq = jjrq;
    }
    public String getZzbz() {
        return zzbz;
    }
    public void setZzbz(String zzbz) {
        this.zzbz = zzbz;
    }
    public String getYggh() {
        return yggh;
    }
    public void setYggh(String yggh) {
        this.yggh = yggh;
    }
    public Integer getJjgw() {
        return jjgw;
    }
    public void setJjgw(Integer jjgw) {
        this.jjgw = jjgw;
    }
    public String getLrsj() {
        return lrsj;
    }
    public void setLrsj(String lrsj) {
        this.lrsj = lrsj;
    }
    public String getJjgwmc() {
        return jjgwmc;
    }
    public void setJjgwmc(String jjgwmc) {
        this.jjgwmc = jjgwmc;
    }
}
