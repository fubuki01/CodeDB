package com.hnran.perfmanagesys.activity.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.PerformanceCellBankHsMxAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceCellBankHsMx;

import android.os.Bundle;
import android.util.Log;

public class PerformanceDianziHushuActivity extends PerformanceCommonActivity{
	
	public final static int ZHUDONG = 1;
	public final static int ZIRAN = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		//设置显示搜索按钮
		isSearch = true;

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceCellBankHsMx.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					JSONObject jsonObj = JSON.parseObject(response); 
					JSONArray array = jsonObj.getJSONArray("list"); 					
					mNum = jsonObj.getIntValue("num");
					final List<PerformanceCellBankHsMx> list = JSON.parseArray(array.toString(), PerformanceCellBankHsMx.class);
					if(adapter == null){
						adapter = new PerformanceCellBankHsMxAdapter();
						listView.setAdapter(adapter);
					}
					adapter.addData(list);
					
//					new Thread(new Runnable() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							BigDecimal b = new BigDecimal("0");
//							for(PerformanceCellBankHsMx l : list){
//								b = b.add(l.getGz());
//							}
//							total = b + "";
//							handler.sendEmptyMessage(ADD);
//						}
//					}).start();
					
				}catch(JSONException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}

				handler.sendEmptyMessage(FINISHED);


			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 
				handler.sendEmptyMessage(ERROR);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("gyh", PMSApplication.gUser.getTellId());
				params.put("tjrq", date);
				params.put("yxlx", lx+"");
				params.put("condition", condition);
				int size = adapter == null ? 0 : adapter.getCount();
				params.put("currentResult", size +"");
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
}
