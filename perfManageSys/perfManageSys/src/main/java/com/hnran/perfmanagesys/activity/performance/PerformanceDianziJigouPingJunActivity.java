package com.hnran.perfmanagesys.activity.performance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.PerformanceJgpjMxAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceJgpjMx;

import android.util.Log;

/**
 * D90019  单条
 * @author Tower
 *
 */
public class PerformanceDianziJigouPingJunActivity extends PerformanceCommonActivity{

	private String zbid;
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		super.initVariables();
		
		zbid = getIntent().getStringExtra("Extra_zbid");
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceJgpjMx.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					final List<PerformanceJgpjMx> list = JSON.parseArray(response, PerformanceJgpjMx.class);
					if(adapter == null) {
						adapter = new PerformanceJgpjMxAdapter();
						listView.setAdapter(adapter);
					}
						
					else{
						adapter.clearData();
					}
					adapter.addData(list);
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							BigDecimal b = new BigDecimal("0");
							for(PerformanceJgpjMx l : list){
								b = b.add(l.getZbgz());
							}
							total = b + "";
							handler.sendEmptyMessage(ADD);
						}
					}).start();

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
				params.put("gzrq", date);
				params.put("zbid", zbid);
//				int size = adapter == null ? 0 : adapter.getCount();
//				params.put("currentResult", size +"");
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
}