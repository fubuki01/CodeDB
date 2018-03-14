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
import com.readboy.ssm.po.PerformanceJymb;

import android.os.Bundle;
import android.util.Log;

public class PerformanceJymbActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceJymb mPerformanceJymb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceJymb.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					mPerformanceJymb = JSON.parseObject(response, PerformanceJymb.class);
					
					total = mPerformanceJymb.getZbgz().toString();
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
		if(mPerformanceJymb == null) 
			return null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("组织名称：", mPerformanceJymb.getZzjc());
		map.put("岗位名称：", mPerformanceJymb.getGwmc());
		map.put("员工姓名：", mPerformanceJymb.getYgxm());
		map.put("指标标识：", mPerformanceJymb.getZbid());
		map.put("指标名称：", mPerformanceJymb.getZbmc());
		map.put("经营得分：", mPerformanceJymb.getZhjydf()+"");
		map.put("全行人均工资：", mPerformanceJymb.getQhrjgz()+"");
		map.put("指标权重：", mPerformanceJymb.getZbqz()+"");
		map.put("指标工资：", mPerformanceJymb.getZbgz()+"");
		return map;
	}
}
