package com.hnran.perfmanagesys.Statistics.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;
import com.hnran.perfmanagesys.Statistics.model.IsalarySeekModel;
import com.hnran.perfmanagesys.Statistics.model.SalarySeekModelImpl;
import com.hnran.perfmanagesys.Statistics.view.IsalarySeekView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/4.
 */

public class SalarySeekPresenter implements IsalarySeekPresenter{
    private IsalarySeekView salarySeekView;
    private IsalarySeekModel salarySeekModel = new SalarySeekModelImpl();
    private final int SHOWTOAST = 0;
    private final int LOADBANKLIST = 1;
    private final int LOADADAPTERDATA = 2;
    private boolean isloading = false;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOWTOAST : salarySeekView.showToast((String)msg.obj); break;
                case LOADBANKLIST : salarySeekView.setBankList((List<String>)msg.obj); break;
                case LOADADAPTERDATA : salarySeekView.addAdapterData((List<StaffSalaryInfo>)msg.obj,msg.arg1);break;
                default:break;
            }
        }
    };

    public SalarySeekPresenter(IsalarySeekView salarySeekView){
        this.salarySeekView = salarySeekView;
    }

    @Override
    public void loadBankList() {
        if(salarySeekView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        salarySeekModel.loadBankListInNetWork(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                msg.obj = "获取支行列表失败，请检查网络连接。";
                handler.sendMessage(msg);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONArray bankList = JSON.parseArray(json);
                List<String> banknames = new ArrayList<>();
                banknames.add("全行");
                for(Object i : bankList)
                    banknames.add(i.toString());
                Message msg = Message.obtain();
                msg.what = LOADBANKLIST;
                msg.obj = banknames;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void loadAdapterData(final int start, final int offset) {
        if(isloading)
            return;
        isloading = true;
        if(salarySeekView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        salarySeekModel.loadStaffRankDataInNetWork(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        isloading = false;
                        Message msg = Message.obtain();
                        msg.what = SHOWTOAST;
                        msg.obj = "获取数据失败，请检查网络连接。";
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        List<StaffSalaryInfo> list = new ArrayList();
                        try {
                            String json = new JSONObject(response.body().string()).getString("result");
                            if(!json.equals("[]")){
                                JSONArray jsonArray = JSON.parseArray(json);
                                for(int i=0; i<jsonArray.size(); i++){
                                    list.add(jsonArray.getObject(i,StaffSalaryInfo.class));
                                }
                            }else{
                                isloading = false;
                                Message msg = Message.obtain();
                                msg.what = SHOWTOAST;
                                msg.obj = "未查到相关数据。";
                                handler.sendMessage(msg);
                            }
                        } catch (JSONException e) {
                            isloading = false;
                            Message msg = Message.obtain();
                            msg.what = SHOWTOAST;
                            msg.obj = "数据异常。";
                            handler.sendMessage(msg);
                            return;
                        }
                        isloading = false;
                        Message msg = Message.obtain();
                        msg.what = LOADADAPTERDATA;
                        msg.obj = list;
                        msg.arg1 = start;
                        handler.sendMessage(msg);
                    }
                },
                start,
                salarySeekView.getDate(),
                salarySeekView.getBankName(),
                salarySeekView.getStaffNo(),
                salarySeekView.getStaffName()
        );
    }

    @Override
    public void loadAdapterData() {
        loadAdapterData(0,10);
    }

    @Override
    public void onDestroy(){
        salarySeekView = null;
    }
}
