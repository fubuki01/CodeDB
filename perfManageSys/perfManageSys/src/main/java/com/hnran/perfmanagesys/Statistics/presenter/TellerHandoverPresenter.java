package com.hnran.perfmanagesys.Statistics.presenter;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffJjGw;
import com.hnran.perfmanagesys.Statistics.model.ItellerHandoverModel;
import com.hnran.perfmanagesys.Statistics.model.TellerHandoverModel;
import com.hnran.perfmanagesys.Statistics.view.ItellerHandOverView;
import com.hnran.perfmanagesys.activity.PMSApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.hnran.perfmanagesys.utils.DatePickerUtil.CHANGE_YEAR_MONTH_DAY;

/**
 * Created by Administrator on 2018/5/11.
 */

public class TellerHandoverPresenter implements ItellerHandoverPresenter {

    ItellerHandOverView tellerHandOverView;
    ItellerHandoverModel tellerHandoverModel = new TellerHandoverModel();
    private final int SHOWTOAST = 0;
    private final int LOADPOSITION = 1;
    private final int LOADADAPTERDATA = 2;
    private boolean isloading = false;
    private HashMap<String, String> positionmap = new HashMap<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SHOWTOAST : tellerHandOverView.showToast((String)msg.obj); break;
                case LOADPOSITION : tellerHandOverView.setPositionList((List<String>)msg.obj); break;
                case LOADADAPTERDATA : tellerHandOverView.addAdapterData((List<HandoverInfo>)msg.obj,msg.arg1);break;
                case CHANGE_YEAR_MONTH_DAY : tellerHandOverView.clearAdapterAndReload(); break;
                default:break;
            }
        }
    };

    public TellerHandoverPresenter(ItellerHandOverView tellerHandOverView){
        this.tellerHandOverView = tellerHandOverView;
        init();
    }

    private void init(){
        positionmap.put("","");
        positionmap.put("无","");
    }

    @Override
    public void onDestroy() {
        tellerHandOverView = null;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void loadPositionList() {
        if(tellerHandOverView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        tellerHandoverModel.loadPositionInNetWork(new Callback() {
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
                positionNames.add("无");
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
    public void loadAdapterData() {
        loadAdapterData(0,10);
    }

    @Override
    public void loadAdapterData(final int start, int offset) {
        if(isloading)
            return;
        isloading = true;
        if(tellerHandOverView==null)
            throw new RuntimeException("Presenter异常：该Presenter没有绑定View");
        tellerHandoverModel.loadHandoverInfoInNetWork(
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
                        List<HandoverInfo> list = new ArrayList();
                        try {
                            String json = response.body().string();
                            if(!json.equals("[]")){
                                JSONArray jsonArray = JSON.parseArray(json);
                                for(int i=0; i<jsonArray.size(); i++){
                                    list.add(jsonArray.getObject(i,HandoverInfo.class));
                                }
                            }else{
                                isloading = false;
                                Message msg = Message.obtain();
                                msg.what = SHOWTOAST;
                                msg.obj = "未查到相关数据。";
                                handler.sendMessage(msg);
                            }
                        } catch (Exception e) {
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
                tellerHandOverView.getDate().equals("")?"":tellerHandOverView.getDate()+" 00:00:00",
                PMSApplication.gUser.getTellId(),
                positionmap.get(tellerHandOverView.getPosition())
        );
    }

}
