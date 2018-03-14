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
import com.readboy.ssm.po.PerformanceCbSjyhdhl;

import android.os.Bundle;
import android.util.Log;

public class PerformanceCbSjyhdhlActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceCbSjyhdhl mPerformanceCbSjyhdhl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceCbSjyhdhl.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					mPerformanceCbSjyhdhl = JSON.parseObject(response, PerformanceCbSjyhdhl.class);
					
					total = mPerformanceCbSjyhdhl.getZbgz().toString();
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
				params.put("gzrq", date);
				params.put("zbid", zbid);
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
	
	@Override
	public Map<String, String> getStringMap() {
		if(mPerformanceCbSjyhdhl == null) 
			return null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("组织名称：", mPerformanceCbSjyhdhl.getZzjc());
		map.put("岗位名称：", mPerformanceCbSjyhdhl.getGwmc());
		map.put("员工姓名：", mPerformanceCbSjyhdhl.getYgxm());
		map.put("指标标识：", mPerformanceCbSjyhdhl.getZbid());
		map.put("指标名称：", mPerformanceCbSjyhdhl.getZbmc());
		map.put("指标结果：", mPerformanceCbSjyhdhl.getZbjg()+"");
		map.put("指标任务：", mPerformanceCbSjyhdhl.getZbrw()+"");
		map.put("全行人均：", mPerformanceCbSjyhdhl.getQhrjgz()+"");
		map.put("指标工资：", mPerformanceCbSjyhdhl.getZbgz()+"");
		return map;
	}
}
