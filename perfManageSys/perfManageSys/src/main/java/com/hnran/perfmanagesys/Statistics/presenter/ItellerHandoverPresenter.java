package com.hnran.perfmanagesys.Statistics.presenter;

import android.os.Handler;

/**
 * Created by Administrator on 2018/5/11.
 */

public interface ItellerHandoverPresenter {

    void loadPositionList();

    void loadAdapterData();

    void loadAdapterData(int start, int offset);

    void onDestroy();

    Handler getHandler();
}
