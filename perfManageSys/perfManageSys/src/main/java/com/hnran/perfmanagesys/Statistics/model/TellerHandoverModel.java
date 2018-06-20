package com.hnran.perfmanagesys.Statistics.model;

import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;
import com.hnran.perfmanagesys.utils.MakeUrl;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/12.
 */

public class TellerHandoverModel implements ItellerHandoverModel {

    private OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

    @Override
    public void loadPositionInNetWork(Callback callBack) {
        String url = MakeUrl.makeURL(new String[]{"staffJj/findStaffJjGw"});
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("wyd","gaydong");//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callBack);
    }

    @Override
    public void loadHandoverInfoInNetWork(Callback callBack, int start, String jjrq, String yggh, String jjgw) {
        String url = MakeUrl.makeURL(new String[]{"staffJj/findStaffJjCustom"});
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("jjrq",jjrq);
        formBody.add("yggh",yggh);
        formBody.add("jjgw",jjgw);
        formBody.add("currentResult",start+"");
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        client.newCall(request).enqueue(callBack);
    }
}
