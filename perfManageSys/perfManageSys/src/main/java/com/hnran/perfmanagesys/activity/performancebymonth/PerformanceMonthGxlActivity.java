package com.hnran.perfmanagesys.activity.performancebymonth;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.PerformanceMonthGxlAdapter;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceGxlgzMxCustom;

import android.os.Bundle;
import android.util.Log;

public class PerformanceMonthGxlActivity extends PerformanceMonthCommonActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();

		String url = MakeUrl.makeURL(new String[]{"findPerformanceGxlgzMxCustom.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					final List<PerformanceGxlgzMxCustom> list = JSON.parseArray(response, PerformanceGxlgzMxCustom.class);
					adapter = new PerformanceMonthGxlAdapter(list);

					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							BigDecimal b = new BigDecimal("0");
							for(PerformanceGxlgzMxCustom l : list){
								b = b.add(l.getZbgz());
							}
							total = b + "";
							handler.sendEmptyMessage(ADD);
						}
					}).start();

				}catch(JSONException e){
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
				params.put("zzbz", zzbz);
				params.put("gwbz", gwbz);
				params.put("ksrq", date + "-01");
				params.put("jsrq", DateUtil.getCurrentMonthLastDay(date + "-01") );
//				params.put("condition", condition);
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}

}
