package com.hnran.perfmanagesys.fragment.visit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.adapter.VisitBasicInfoRecodeAdapter;
import com.hnran.perfmanagesys.adapter.VisitMenuAadapter;
import com.hnran.perfmanagesys.utils.DensityUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.visit.VisitLocationActivity;
import com.readboy.ssm.po.VisitBaseInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拜访基本信息界面逻辑
 */
public class BasicInformationFragment extends BaseFragment implements VisitJudgeAndSaveCallBack {
    //拜访类型弹窗相关组件
    private LinearLayout bar_visit_type;
    private VisitMenuAadapter adapter = null;
    private ListPopupWindow listPopupWindow = null;
    private ImageView bt_arrow;
    private TextView tv_visit_type;
    //礼品和总结相关组件
    private EditText et_gift_info;
    private EditText et_person_summary;
    //走访地址相关组件
    private ImageView mLocation;
    private TextView mAddress;
    //走访日志相关组件
    private ListView lv_visit_log;
    private VisitBasicInfoRecodeAdapter visitBasicInfoRecodeAdapter;
    //msg码
    private final static int LOADFINISHED = 100000;
    private final static int SAVEFINISHED = 100001;
    private final static int ERROR = 100002;
    //客户编号
    private String khbh;
    private String visitorId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khbh = getActivity().getIntent().getStringExtra("Extra_khbh");
        visitorId = getActivity().getIntent().getStringExtra("ghrgh");
        View view = inflater.inflate(R.layout.fragment_visit_basic_information, null);
        initViews(view);
        loadData();
        return view;
    }

    private void initVariables() {

    }

    private void initViews(View view) {
        tv_visit_type = (TextView)view.findViewById(R.id.tv_visit_type);
        bt_arrow = (ImageView)view.findViewById(R.id.bt_arrow);
        bar_visit_type = (LinearLayout)view.findViewById(R.id.bar_visit_type);
        lv_visit_log = (ListView)view.findViewById(R.id.lv_visit_log);
        et_gift_info = (EditText)view.findViewById(R.id.et_gift_info);
        et_person_summary = (EditText)view.findViewById(R.id.et_person_summary);
        mLocation = (ImageView) view.findViewById(R.id.bt_location);
        mAddress = (TextView) view.findViewById(R.id.tv_address);
        /**
         * 拜访类型栏绑定弹框监听
         */
        bar_visit_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListPopupWindow(bar_visit_type);
            }
        });
        /**
         * 走访记录的ListView设置配适器
         */
        visitBasicInfoRecodeAdapter = new VisitBasicInfoRecodeAdapter(new ArrayList<VisitBaseInfo>(),getActivity());
        lv_visit_log.setAdapter(visitBasicInfoRecodeAdapter);
        /**
         * 点击位置图标获取地理位置
         */
        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VisitLocationActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void loadData() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/getVisitInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            Log.i("onResponse", response);
                            ArrayList<VisitBaseInfo> list = (ArrayList<VisitBaseInfo>)JSON.parseArray(response, VisitBaseInfo.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("list",list);
                            message = handler.obtainMessage(LOADFINISHED,bundle);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        if(message!=null)
                            handler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG", error.getMessage(), error);
                            handler.sendEmptyMessage(ERROR);
                            ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
                        }
        }){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("visitorId",visitorId);
                map.put("clientNum",khbh);
                return map;
            }
        };

        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }

    private Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case LOADFINISHED:
                    visitBasicInfoRecodeAdapter.clearData();
                    List<VisitBaseInfo> tmplist = (ArrayList<VisitBaseInfo>)((Bundle)msg.obj).get("list");
                    for(VisitBaseInfo info:tmplist)
                        visitBasicInfoRecodeAdapter.setData(info);
                    visitBasicInfoRecodeAdapter.notifyDataSetChanged();
                    break;
                case SAVEFINISHED:
                    String issuccess = (String)msg.obj;
                    if(issuccess.equals("success")){
                        ToastUtil.showToast(getActivity(), CommonContent.OPERATION_SUCCESS);
                        loadData();
                    }
                    else
                        ToastUtil.showToast(getActivity(), CommonContent.OPERATION_FAILED);
                    break;
                case ERROR:
                    break;
            }
        };
    };
    /**
     * 选择拜访类型的弹框函数
     */
    private void showListPopupWindow(View view) {
        if (listPopupWindow == null)
            listPopupWindow = new ListPopupWindow(getActivity());

        if (adapter == null) {
            adapter = new VisitMenuAadapter(getActivity(), android.R.layout.simple_list_item_1,
                    new String[]{"上门","单位"});
            // ListView适配器
            listPopupWindow.setAdapter(adapter);
            // 选择item的监听事件
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    tv_visit_type.setText(adapter.getItem(pos));
                    listPopupWindow.dismiss();
                }
            });
            listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //旋转0度是复位ImageView
                    bt_arrow.animate().setDuration(500).rotation(0).start();
                }
            });
        }
        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        int viewWidth = view.getWidth();
        listPopupWindow.setAnchorView(view);
        listPopupWindow.setVerticalOffset(DensityUtil.dip2px(getActivity(),5));
        listPopupWindow.setHorizontalOffset(viewWidth*2/3);
        // 对话框的宽高
        listPopupWindow.setWidth(viewWidth/3);
        listPopupWindow.setModal(true);
        listPopupWindow.show();
        bt_arrow.animate().setDuration(500).rotation(180).start();
    }
    /**
     * 获取返回的地理位置
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            String locationResult = data.getStringExtra("locationResult");
            mAddress.setText(locationResult);
        }
    }


    /**
     * 判断条件是否符合
     * @return
     */
    @Override
    public boolean judgeCallBack() {
        return true;
    }

    /**
     * 对应拜访主Activity保存按键的回调
     */
    @Override
    public void saveCallBack() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/saveVisitInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            JSONObject jsonObj = JSON.parseObject(response);
                            message = handler.obtainMessage(SAVEFINISHED,jsonObj.getString("result"));
                        }catch(JSONException e){
                            e.printStackTrace();
                        }catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        if(message!=null)
                            handler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                handler.sendEmptyMessage(ERROR);
                ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String,String> map = new HashMap<String,String>();
                map.put("type",tv_visit_type.getText().toString());
                map.put("gift",et_gift_info.getText().toString());
                map.put("summery",et_person_summary.getText().toString());
                map.put("location",mAddress.getText().toString());
                map.put("visitorId",visitorId);
                map.put("clientNum",khbh);
                map.put("date",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }
}
