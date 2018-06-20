package com.hnran.perfmanagesys.Statistics.view;

import java.util.List;

/**
 * Created by Administrator on 2018/5/12.
 */

public interface IhandoverSaveView {
    void showToast(String str);

    String getPosition();

    String getDate();

    String getStaffName();

    String getStaffNo();

    String getOrganizationName();

    void setOrganizationName(String organizationName);

    void setPositionList(List<String> PositionList);
}
