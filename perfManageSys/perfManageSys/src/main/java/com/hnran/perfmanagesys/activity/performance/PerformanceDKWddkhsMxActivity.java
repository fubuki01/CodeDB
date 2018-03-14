package com.hnran.perfmanagesys.activity.performance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceDKWddkhsMx;

import android.os.Bundle;
import android.util.Log;

public class PerformanceDKWddkhsMxActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceDKWddkhsMx mPerformanceDKWddkhsMx;
	
	private String zzbz;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		zzbz = getIntent().getStringExtra("Extra_zzbz");
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceDkWddkhsMx.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					mPerformanceDKWddkhsMx = JSON.parseObject(response, PerformanceDKWddkhsMx.class);
					total = mPerformanceDKWddkhsMx.getGz().toString();
					handler.sendEmptyMessage(ADD);
					
				}catch(JSONException e){
					e.printStackTrace();
					if("".equals(response)){
						handler.sendEmptyMessage(ISNULL);
					}
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
				params.put("zzbz", zzbz);
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
	
	@Override
	public Map<String, String> getStringMap() {
		if(mPerformanceDKWddkhsMx == null) 
			return null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("组织名称：", mPerformanceDKWddkhsMx.getZzjc());
		map.put("贷款户数：", mPerformanceDKWddkhsMx.getHs()+"");
		map.put("单价：", mPerformanceDKWddkhsMx.getDj()+"");
		map.put("工资：", mPerformanceDKWddkhsMx.getGz()+"");
		return map;
	}
}
