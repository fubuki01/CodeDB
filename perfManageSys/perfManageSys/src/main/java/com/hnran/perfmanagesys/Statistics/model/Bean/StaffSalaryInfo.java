package com.hnran.perfmanagesys.Statistics.model.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/4/4.
 */

public class StaffSalaryInfo implements Parcelable {

    private String ZZMC;//组织部门
    private String GZRQ;//工资日期
    private double CKGZ;//存款工资
    private double  DKGZ;//贷款工资
    private double DZYHGZ;//电子银行工资
    private double YWLGZ;//业务量工资
    private double GLJXGZ;//管理绩效工资
    private double DQBCGZ;//地区补差工资
    private double GZHJ;//工资合计
    private double GWBZ;//岗位标识
    private String YGGH;//员工工号
    private String REALNAME;//员工姓名
    private int ZHGZPM;//支行工资排名
    private int QHGZPM;//全行工资排名
    private String KHWD;//考核维度
    private double QT;//其他
    private double JYMBGZ;//经营目标工资
    private double YQDFGZ;//延期兑付工资
    private String LRR;
    private String LRSJ;
    private double LRBZ;

    public String getREALNAME() {
        return REALNAME;
    }

    public void setREALNAME(String REALNAME) {
        this.REALNAME = REALNAME;
    }

    public String getZZMC() {
        return ZZMC;
    }

    public void setZZMC(String ZZMC) {
        this.ZZMC = ZZMC;
    }

    public String getGZRQ() {
        return GZRQ;
    }

    public void setGZRQ(String GZRQ) {
        this.GZRQ = GZRQ;
    }

    public double getCKGZ() {
        return CKGZ;
    }

    public void setCKGZ(double CKGZ) {
        this.CKGZ = CKGZ;
    }

    public double getDKGZ() {
        return DKGZ;
    }

    public void setDKGZ(double DKGZ) {
        this.DKGZ = DKGZ;
    }

    public double getDZYHGZ() {
        return DZYHGZ;
    }

    public void setDZYHGZ(double DZYHGZ) {
        this.DZYHGZ = DZYHGZ;
    }

    public double getYWLGZ() {
        return YWLGZ;
    }

    public void setYWLGZ(double YWLGZ) {
        this.YWLGZ = YWLGZ;
    }

    public double getGLJXGZ() {
        return GLJXGZ;
    }

    public void setGLJXGZ(double GLJXGZ) {
        this.GLJXGZ = GLJXGZ;
    }

    public double getDQBCGZ() {
        return DQBCGZ;
    }

    public void setDQBCGZ(double DQBCGZ) {
        this.DQBCGZ = DQBCGZ;
    }

    public double getGZHJ() {
        return GZHJ;
    }

    public void setGZHJ(double GZHJ) {
        this.GZHJ = GZHJ;
    }

    public double getGWBZ() {
        return GWBZ;
    }

    public void setGWBZ(double GWBZ) {
        this.GWBZ = GWBZ;
    }

    public String getYGGH() {
        return YGGH;
    }

    public void setYGGH(String YGGH) {
        this.YGGH = YGGH;
    }

    public int getZHGZPM() {
        return ZHGZPM;
    }

    public void setZHGZPM(int ZHGZPM) {
        this.ZHGZPM = ZHGZPM;
    }

    public int getQHGZPM() {
        return QHGZPM;
    }

    public void setQHGZPM(int QHGZPM) {
        this.QHGZPM = QHGZPM;
    }

    public String getKHWD() {
        return KHWD;
    }

    public void setKHWD(String KHWD) {
        this.KHWD = KHWD;
    }

    public double getQT() {
        return QT;
    }

    public void setQT(double QT) {
        this.QT = QT;
    }

    public double getJYMBGZ() {
        return JYMBGZ;
    }

    public void setJYMBGZ(double JYMBGZ) {
        this.JYMBGZ = JYMBGZ;
    }

    public double getYQDFGZ() {
        return YQDFGZ;
    }

    public void setYQDFGZ(double YQDFGZ) {
        this.YQDFGZ = YQDFGZ;
    }

    public String getLRR() {
        return LRR;
    }

    public void setLRR(String LRR) {
        this.LRR = LRR;
    }

    public String getLRSJ() {
        return LRSJ;
    }

    public void setLRSJ(String LRSJ) {
        this.LRSJ = LRSJ;
    }

    public double getLRBZ() {
        return LRBZ;
    }

    public void setLRBZ(double LRBZ) {
        this.LRBZ = LRBZ;
    }

    public StaffSalaryInfo TextSet(String ZZMC, String GZRQ, String YGGH, String REALNAME,
                        int ZHGZPM, int QHGZPM){
        this.ZZMC = ZZMC;
        this.GZRQ = GZRQ;
        this.YGGH = YGGH;
        this.REALNAME = REALNAME;
        this.ZHGZPM = ZHGZPM;
        this.QHGZPM = QHGZPM;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ZZMC);
        dest.writeString(this.GZRQ);
        dest.writeDouble(this.CKGZ);
        dest.writeDouble(this.DKGZ);
        dest.writeDouble(this.DZYHGZ);
        dest.writeDouble(this.YWLGZ);
        dest.writeDouble(this.GLJXGZ);
        dest.writeDouble(this.DQBCGZ);
        dest.writeDouble(this.GZHJ);
        dest.writeDouble(this.GWBZ);
        dest.writeString(this.YGGH);
        dest.writeString(this.REALNAME);
        dest.writeInt(this.ZHGZPM);
        dest.writeInt(this.QHGZPM);
        dest.writeString(this.KHWD);
        dest.writeDouble(this.QT);
        dest.writeDouble(this.JYMBGZ);
        dest.writeDouble(this.YQDFGZ);
        dest.writeString(this.LRR);
        dest.writeString(this.LRSJ);
        dest.writeDouble(this.LRBZ);
    }

    public StaffSalaryInfo() {
    }

    protected StaffSalaryInfo(Parcel in) {
        this.ZZMC = in.readString();
        this.GZRQ = in.readString();
        this.CKGZ = in.readDouble();
        this.DKGZ = in.readDouble();
        this.DZYHGZ = in.readDouble();
        this.YWLGZ = in.readDouble();
        this.GLJXGZ = in.readDouble();
        this.DQBCGZ = in.readDouble();
        this.GZHJ = in.readDouble();
        this.GWBZ = in.readDouble();
        this.YGGH = in.readString();
        this.REALNAME = in.readString();
        this.ZHGZPM = in.readInt();
        this.QHGZPM = in.readInt();
        this.KHWD = in.readString();
        this.QT = in.readDouble();
        this.JYMBGZ = in.readDouble();
        this.YQDFGZ = in.readDouble();
        this.LRR = in.readString();
        this.LRSJ = in.readString();
        this.LRBZ = in.readDouble();
    }

    public static final Parcelable.Creator<StaffSalaryInfo> CREATOR = new Parcelable.Creator<StaffSalaryInfo>() {
        @Override
        public StaffSalaryInfo createFromParcel(Parcel source) {
            return new StaffSalaryInfo(source);
        }

        @Override
        public StaffSalaryInfo[] newArray(int size) {
            return new StaffSalaryInfo[size];
        }
    };
}
