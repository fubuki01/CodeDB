package com.hnran.perfmanagesys.Statistics.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffJjGw;
import com.hnran.perfmanagesys.Statistics.model.HandoverSaveModel;
import com.hnran.perfmanagesys.Statistics.model.IhandoverSaveModel;
import com.hnran.perfmanagesys.Statistics.view.IhandoverSaveView;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.readboy.ssm.po.Organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/14.
 */

public class HandoverSavePresenter implements IhandoverSavePresenter{
    IhandoverSaveView handoverSaveView;
    IhandoverSaveModel handoverSaveModel = new HandoverSaveModel();

    private final int SHOWTOAST = 0;
    private final int LOADPOSITION = 1;
    private final int LOADORGANIZATIONNAME = 2;

    private boolean isloading = false;
    private HashMap<String, String> positionmap = new HashMap<>();
    private Organization org = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOWTOAST : handoverSaveView.showToast((String)msg.obj); break;
                case LOADPOSITION : handoverSaveView.setPositionList((List<String>)msg.obj); break;
                case LOADORGANIZATIONNAME : handoverSaveView.setOrganizationName((String)msg.obj);break;
                default:break;
            }
        }
    };

    public HandoverSavePresenter(IhandoverSaveView handoverSaveView){
        this.handoverSaveView = handoverSaveView;
        init();
    }

    private void init(){
        positionmap.put("","");
        positionmap.put("无","");
    }

    @Override
    public void onDestroy() {
        handoverSaveView=null;
    }

    @Override
    public void save() {
        if(isloading)
            return;
        isloading = true;
        if(handoverSaveView.getDate().equals("")){
            handoverSaveView.showToast("请选择交接日期");
            return;
        }
        if(handoverSaveView.getPosition().equals("")){
            handoverSaveView.showToast("请选择交接岗位");
            return;
        }
        if(org==null){
            handoverSaveView.showToast("支行信息获取中，请稍后保存");
            loadOrganizationName();
            return;
        }
        if(handoverSaveView.getStaffName().equals("")
                ||handoverSaveView.getStaffNo().equals("")
                ||handoverSaveView.getOrganizationName().equals("")){
            handoverSaveView.showToast("用户信息异常,请重新登陆");
            return;
        }
        if(handoverSaveView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");

        handoverSaveModel.saveHandoverInfoInNetWork(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isloading = false;
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                msg.obj = "保存超时，请检查网络连接。";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                isloading = false;
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                if(response.body().string().equals("OK"))
                    msg.obj = "保存成功";
                else
                    msg.obj = "保存失败";
                handler.sendMessage(msg);
            }
        },handoverSaveView.getDate(), org.getZzbz(), handoverSaveView.getStaffNo(),positionmap.get(handoverSaveView.getPosition()));
    }

    @Override
    public void loadPositionList() {
        if(handoverSaveView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        handoverSaveModel.loadPositionListInNetWork(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                msg.obj = "获取岗位列表失败，请检查网络连接。";
                handler.sendMessage(msg);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<String> positionNames = new ArrayList<>();
                try {
                    String json = response.body().string();
                    if(!json.equals("[]")){
                        JSONArray jsonArray = JSON.parseArray(json);
                        for(int i=0; i<jsonArray.size(); i++){
                            StaffJjGw gw = jsonArray.getObject(i,StaffJjGw.class);
                            positionmap.put(gw.getJjgwmc(), gw.getJjgw().intValue()+"");
                            positionNames.add(gw.getJjgwmc());
                        }
                    }else{
                        Message msg = Message.obtain();
                        msg.what = SHOWTOAST;
                        msg.obj = "未查到岗位信息。";
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = SHOWTOAST;
                    msg.obj = "数据异常。";
                    handler.sendMessage(msg);
                    return;
                }
                Message msg = Message.obtain();
                msg.what = LOADPOSITION;
                msg.obj = positionNames;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void loadOrganizationName() {
        if(handoverSaveView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        handoverSaveModel.loadOrganizationNameInNetWork(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = SHOWTOAST;
                msg.obj = "获取支行信息，请检查网络连接。";
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String json = response.body().string();
                    org = JSON.parseObject(json,Organization.class);
                    Message msg = Message.obtain();
                    msg.what = LOADORGANIZATIONNAME;
                    msg.obj = org.getZzmc();
                    handler.sendMessage(msg);
                }catch (Exception e){
                    Message msg = Message.obtain();
                    msg.what = SHOWTOAST;
                    msg.obj = "获取支行信息异常。";
                    handler.sendMessage(msg);
                }
            }
        }, PMSApplication.gUser.getTellId());
    }
}
