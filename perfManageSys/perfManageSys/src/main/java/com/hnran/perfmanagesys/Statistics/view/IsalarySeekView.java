package com.hnran.perfmanagesys.Statistics.view;

import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public interface IsalarySeekView {

    void showToast(String str);

    String getStaffName();

    String getStaffNo();

    String getBankName();

    String getDate();

    void setBankList(List<String> bankList);

    void addAdapterData(List<StaffSalaryInfo> staffSalaryInfos, int start);
}
