package com.hnran.perfmanagesys.Statistics.presenter;

/**
 * Created by Administrator on 2018/4/4.
 */

public interface IsalarySeekPresenter {

    void loadBankList();

    void loadAdapterData();

    void loadAdapterData(int start, int offset);

    void onDestroy();
}
