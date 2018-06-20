package com.hnran.perfmanagesys.Statistics.model;

import okhttp3.Callback;

/**
 * Created by Administrator on 2018/4/4.
 */

public interface IsalarySeekModel {

    void loadBankListInNetWork(Callback callBack);

    void loadStaffRankDataInNetWork(Callback callBack, int start, String date, String bankName, String staffNo, String staffName);
}
