package com.hnran.perfmanagesys.Statistics.model;

import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;

import okhttp3.Callback;

/**
 * Created by Administrator on 2018/5/14.
 */

public interface IhandoverSaveModel {

    void saveHandoverInfoInNetWork(Callback callBack, String jjrq, String zzbz, String yggh, String jjgw);

    void loadOrganizationNameInNetWork(Callback callBack, String staffNo);

    void loadPositionListInNetWork(Callback callBack);
}
