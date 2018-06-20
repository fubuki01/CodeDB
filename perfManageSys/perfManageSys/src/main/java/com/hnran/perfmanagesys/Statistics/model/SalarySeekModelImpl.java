package com.hnran.perfmanagesys.Statistics.model;

import com.hnran.perfmanagesys.utils.MakeUrl;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SalarySeekModelImpl implements IsalarySeekModel{

    private OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

    @Override
    public void loadBankListInNetWork(Callback callBack) {
        String url = MakeUrl.makeURL(new String[]{"employeeStatistics/findOrganization.action"});
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("wyd","gaydong");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callBack);
    }

    @Override
    public void loadStaffRankDataInNetWork(Callback callBack, int start, String date, String bankName, String staffNo, String staffName) {
        String url = MakeUrl.makeURL(new String[]{"employeeStatistics/findEmployeeWagesInfo.action"});
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("offset","10");
        formBody.add("currentResult",start+"");
        formBody.add("gzrq",date+" 00:00:00");//传递键值对参数
        formBody.add("zzmc",bankName);
        formBody.add("yggh",staffNo);
        formBody.add("ygxm",staffName);
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callBack);
    }
}
