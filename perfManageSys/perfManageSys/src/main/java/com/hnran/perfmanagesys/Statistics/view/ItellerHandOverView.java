package com.hnran.perfmanagesys.Statistics.view;

import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/11.
 */

public interface ItellerHandOverView {

    void showToast(String str);

    String getPosition();

    String getDate();

    void setPositionList(List<String> PositionList);

    void addAdapterData(List<HandoverInfo> handoverInfos,int start);

    void clearAdapterAndReload();
}
