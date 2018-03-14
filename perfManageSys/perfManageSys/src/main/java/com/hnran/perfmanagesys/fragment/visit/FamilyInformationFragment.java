package com.hnran.perfmanagesys.fragment.visit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.VisitFamilyMemberListAdapter;
import com.hnran.perfmanagesys.adapter.VisitMenuAadapter;
import com.hnran.perfmanagesys.utils.DensityUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.VisitFamilyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拜访家庭成员信息界面逻辑
 */

public class FamilyInformationFragment extends BaseFragment implements VisitJudgeAndSaveCallBack, VisitDeleteCallBack{
    //成员名称和证件号的输入框
    private EditText et_member_name;
    private EditText et_credentials_number;
    //证件类型和成员关系的弹窗与适配器
    private ListPopupWindow credentialsPopupWindow = null;
    private ListPopupWindow relationshipPopupWindow = null;
    private VisitMenuAadapter credentialsadapter = null;
    private VisitMenuAadapter relationshipadapter = null;
    //证件类型栏的相关View
    private LinearLayout bar_credentials_type;
    private TextView tv_credentials_type;
    private ImageView bt_credentials_arrow;
    //成员关系栏的相关View
    private LinearLayout bar_relationship_type;
    private TextView tv_relationship_type;
    private ImageView bt_relationship_arrow;
    //家庭成员列表相关组件
    private ListView lv_family_member_list;
    private VisitFamilyMemberListAdapter visitFamilyMemberListAdapter;
    //msg码
    private final static int LOADFINISHED = 200000;
    private final static int OPERATIONFINISHED = 200001;
    private final static int ERROR = 200002;
    //客户编号
    private String khbh;
    //删除弹窗
    private AlertDialog mAlertDialogRecord = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khbh = getActivity().getIntent().getStringExtra("Extra_khbh");
        View view = inflater.inflate(R.layout.fragment_visit_family_member, null);
        initViews(view);
        loadData();
        return view;
    }

    public void initVariables() {

    }

    private void initViews(View view) {
        et_member_name = (EditText)view.findViewById(R.id.et_member_name);
        et_credentials_number = (EditText)view.findViewById(R.id.et_credentials_number);

        bar_credentials_type = (LinearLayout)view.findViewById(R.id.bar_credentials_type);
        tv_credentials_type = (TextView) view.findViewById(R.id.tv_credentials_type);
        bt_credentials_arrow = (ImageView) view.findViewById(R.id.bt_credentials_arrow);

        bar_relationship_type = (LinearLayout)view.findViewById(R.id.bar_relationship_type);
        tv_relationship_type = (TextView) view.findViewById(R.id.tv_relationship_type);
        bt_relationship_arrow = (ImageView) view.findViewById(R.id.bt_relationship_arrow);

        lv_family_member_list = (ListView)view.findViewById(R.id.lv_family_member_list);
        /**
         * 证件类型栏绑定弹框监听
         */
        bar_credentials_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCredentialsPopupWindow(bar_credentials_type);
            }
        });
        /**
         * 成员关系栏绑定弹框监听
         */
        bar_relationship_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRelationshipPopupWindow(bar_relationship_type);
            }
        });
        /**
         * 家庭成员列表的ListView设置配适器
         */
        visitFamilyMemberListAdapter = new VisitFamilyMemberListAdapter(new ArrayList<VisitFamilyInfo>(),getActivity(),this);
        lv_family_member_list.setAdapter(visitFamilyMemberListAdapter);
    }

    public void loadData() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/getFamilyInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            Log.i("onResponse", response);
                            ArrayList<VisitFamilyInfo> list = (ArrayList<VisitFamilyInfo>) JSON.parseArray(response, VisitFamilyInfo.class);
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
                map.put("visitorId", PMSApplication.gUser.getTellId());
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
                    visitFamilyMemberListAdapter.clearData();
                    List<VisitFamilyInfo> tmplist = (ArrayList<VisitFamilyInfo>)((Bundle)msg.obj).get("list");
                    for(VisitFamilyInfo info:tmplist)
                        visitFamilyMemberListAdapter.setData(info);
                    visitFamilyMemberListAdapter.notifyDataSetChanged();
                    break;
                case OPERATIONFINISHED:
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
     * 选择证件类型的弹框函数
     */
    private void showCredentialsPopupWindow(View bar) {
        if (credentialsPopupWindow == null)
            credentialsPopupWindow = new ListPopupWindow(getActivity());

        if (credentialsadapter == null) {
            credentialsadapter = new VisitMenuAadapter(getActivity(), android.R.layout.simple_list_item_1,
                    new String[]{"身份证","老年证"});
            // ListView适配器
            credentialsPopupWindow.setAdapter(credentialsadapter);
            // 选择item的监听事件
            credentialsPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    tv_credentials_type.setText(credentialsadapter.getItem(pos));
                    credentialsPopupWindow.dismiss();
                }
            });
            credentialsPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //旋转0度是复位ImageView
                    bt_credentials_arrow.animate().setDuration(500).rotation(0).start();
                }
            });
        }
        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        int viewWidth = bar.getWidth();
        credentialsPopupWindow.setAnchorView(bar);
        credentialsPopupWindow.setVerticalOffset(DensityUtil.dip2px(getActivity(),5));
        credentialsPopupWindow.setHorizontalOffset(viewWidth*2/3);
        // 对话框的宽高
        credentialsPopupWindow.setWidth(viewWidth/3);
        credentialsPopupWindow.setModal(true);
        credentialsPopupWindow.show();
        bt_credentials_arrow.animate().setDuration(500).rotation(180).start();
    }
    /**
     * 选择成员关系的弹框函数
     */
    private void showRelationshipPopupWindow(View bar) {
        if (relationshipPopupWindow == null)
            relationshipPopupWindow = new ListPopupWindow(getActivity());

        if (relationshipadapter == null) {
            relationshipadapter = new VisitMenuAadapter(getActivity(), android.R.layout.simple_list_item_1,
                    new String[]{"父母","子女","兄弟","姐妹","其他"});
            // ListView适配器
            relationshipPopupWindow.setAdapter(relationshipadapter);
            // 选择item的监听事件
            relationshipPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    tv_relationship_type.setText(relationshipadapter.getItem(pos));
                    relationshipPopupWindow.dismiss();
                }
            });
            relationshipPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //旋转0度是复位ImageView
                    bt_relationship_arrow.animate().setDuration(500).rotation(0).start();
                }
            });
        }
        // ListPopupWindow的锚,弹出框的位置是相对当前View的位置
        int viewWidth = bar.getWidth();
        relationshipPopupWindow.setAnchorView(bar);
        relationshipPopupWindow.setVerticalOffset(DensityUtil.dip2px(getActivity(),5));
        relationshipPopupWindow.setHorizontalOffset(viewWidth*2/3);
        // 对话框的宽高
        relationshipPopupWindow.setWidth(viewWidth/3);
        relationshipPopupWindow.setModal(true);
        relationshipPopupWindow.show();
        bt_relationship_arrow.animate().setDuration(500).rotation(180).start();
    }

    @Override
    public boolean judgeCallBack() {
        String name = et_member_name.getText().toString();
        if(name==null||name.equals("")){
            ToastUtil.showToast(getActivity(), "请输入成员名称");
            return false;
        }
        else
            return true;
    }

    /**
     * 保存按键回调
     */
    @Override
    public void saveCallBack() {
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/saveFamilyInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            JSONObject jsonObj = JSON.parseObject(response);
                            message = handler.obtainMessage(OPERATIONFINISHED,jsonObj.getString("result"));
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
                map.put("memberName",et_member_name.getText().toString());
                map.put("credentialNum",et_credentials_number.getText().toString());
                map.put("credentialType",tv_credentials_type.getText().toString());
                map.put("relationship",tv_relationship_type.getText().toString());
                map.put("clientNum",khbh);
                map.put("visitorId",PMSApplication.gUser.getTellId());
                map.put("memberType","1");
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }

    /**
     * 删除家庭成员回调
     */
    @Override
    public void deleteCallBack(int id) {
        /**
         * 删除家庭成员提示对话框
         */
        final int fid = id;

        if(mAlertDialogRecord!=null){
            mAlertDialogRecord.show();
            return;
        }
        mAlertDialogRecord = new AlertDialog.Builder(getActivity()).create();
        mAlertDialogRecord.setCanceledOnTouchOutside(false);
        mAlertDialogRecord.show();
        mAlertDialogRecord.getWindow().setContentView(R.layout.dialog_pop);
        TextView msg = (TextView) mAlertDialogRecord.getWindow().findViewById(R.id.tv_sjd_id);
        msg.setText("是否删除此家庭成员？");
        Button cancle = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_right);
        Button confirm = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_left);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogRecord.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember(fid);
                mAlertDialogRecord.dismiss();
            }
        });
    }
    /**
     * 删除家庭成员函数
     */
    private void deleteMember(int id){
        final int fid = id;
        String url = MakeUrl.makeURL(new String[]{"visitor/BaseInfo/deleteFamilyInfo"});
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Message message = null;
                        try{
                            JSONObject jsonObj = JSON.parseObject(response);
                            message = handler.obtainMessage(OPERATIONFINISHED,jsonObj.getString("result"));
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
                map.put("id",""+fid);
                return map;
            }
        };
        VolleyUtil.getInstanceRequestQueue().add(stringRequest);
    }
}
