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
import com.readboy.ssm.po.PerformanceGljxBmkh;

import android.os.Bundle;
import android.util.Log;

public class PerformanceGljxBmkhActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceGljxBmkh mPerformanceGljxBmkh;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceGljxBmkh.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					mPerformanceGljxBmkh = JSON.parseObject(response, PerformanceGljxBmkh.class);
					
					total = mPerformanceGljxBmkh.getZbgz().toString();
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
		if(mPerformanceGljxBmkh == null) 
			return null;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("组织名称：", mPerformanceGljxBmkh.getZzjc());
		map.put("岗位名称：", mPerformanceGljxBmkh.getGwmc());
		map.put("员工姓名：", mPerformanceGljxBmkh.getYgxm());
		map.put("指标标识：", mPerformanceGljxBmkh.getZzbz());
		map.put("指标名称：", mPerformanceGljxBmkh.getZbmc());
		map.put("指标结果：", mPerformanceGljxBmkh.getZbjg()+"");
		map.put("全行人均工资：", mPerformanceGljxBmkh.getQhrjgz()+"");
		map.put("指标权重：", mPerformanceGljxBmkh.getZbqz()+"");
		map.put("指标工资：", mPerformanceGljxBmkh.getZbgz()+"");
		
		return map;
	}
}
