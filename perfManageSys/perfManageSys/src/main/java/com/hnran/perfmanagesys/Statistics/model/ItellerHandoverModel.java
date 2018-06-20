package com.hnran.perfmanagesys.Statistics.model;

import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;

import okhttp3.Callback;

/**
 * Created by Administrator on 2018/5/12.
 */

public interface ItellerHandoverModel {

    void loadPositionInNetWork(Callback callBack);

    void loadHandoverInfoInNetWork(Callback callBack, int start, String jjrq, String yggh, String jjgw);
}
