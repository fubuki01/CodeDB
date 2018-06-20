package com.hnran.perfmanagesys.Statistics.model;

import com.hnran.perfmanagesys.utils.MakeUrl;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/21.
 */

public class SalaryRankModel implements IsalarySeekModel {

    private OkHttpClient client = new OkHttpClient();

    @Override
    public void loadBankListInNetWork(Callback callBack) {
        String url = MakeUrl.makeURL(new String[]{"employeeStatistics/findOrganization.action"});
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("wyd","gaydong");
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        client.newCall(request).enqueue(callBack);
    }

    @Override
    public void loadStaffRankDataInNetWork(Callback callBack, int type, String date, String bankName, String staffNo, String staffName) {
        String url = "";
        switch (type){
            case 1:url = MakeUrl.makeURL(new String[]{"allStatistic/findEmployeesByTime.action"});break;
            case 2:url = MakeUrl.makeURL(new String[]{"allStatistic/findLastTenByTime.action"});break;
            case 3:url = MakeUrl.makeURL(new String[]{"branchStatistics/findEmployeesByOraganization.action"});break;
            case 4:url = MakeUrl.makeURL(new String[]{"branchStatistics/findLastTenEmployees.action"});break;
        }
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("gzrq",date+" 00:00:00");//传递键值对参数
        formBody.add("zzmc",bankName);
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callBack);
    }
}
