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
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceDkXzbnbldkzb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PerformanceDkXzbnbldkzbActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceDkXzbnbldkzb performanceDkXzbnbldkzb;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceDkXzbnbldkzb.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					performanceDkXzbnbldkzb = JSON.parseObject(response, PerformanceDkXzbnbldkzb.class);
					total = performanceDkXzbnbldkzb.getZbgz().toString();
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
	
//	@Override
//	public Map<String, String> getStringMap() {
//		if(performanceDkXzbnbldkzb == null) 
//			return null;
//		Map<String, String> map = new LinkedHashMap<String, String>();
//		map.put("组织名称：", performanceDkXzbnbldkzb.getZzjc());
//		map.put("岗位名称：", performanceDkXzbnbldkzb.getGwmc());
//		map.put("员工姓名：", performanceDkXzbnbldkzb.getYgxm());
//		map.put("指标标识：", performanceDkXzbnbldkzb.getZzbz());
//		map.put("指标名称：", performanceDkXzbnbldkzb.getZbmc());
//		map.put("不良贷款余额：", performanceDkXzbnbldkzb.getBldkye()+"");
//		map.put("贷款期末余额：", performanceDkXzbnbldkzb.getDkqmye()+"");
//		map.put("不良贷款占比：", performanceDkXzbnbldkzb.getBldkzb()+"");
//		map.put("上月不良贷款占比：", performanceDkXzbnbldkzb.getSybldkzb()+"");
//		map.put("扣减工资基数：", performanceDkXzbnbldkzb.getKjgzjs()+"");
//		map.put("指标工资：", performanceDkXzbnbldkzb.getZbgz()+"");
//		return map;
//	}
	
	@SuppressLint("InflateParams")
	@Override
	public View addCustomerLayout() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.item_performance_daikuan_dndqdkshl, null);
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zzmc))).setText(performanceDkXzbnbldkzb.getZzjc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_gwmc))).setText(performanceDkXzbnbldkzb.getGwmc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_ygxm))).setText(performanceDkXzbnbldkzb.getYgxm());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbbs))).setText(performanceDkXzbnbldkzb.getZzbz());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbmc))).setText(performanceDkXzbnbldkzb.getZbmc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqwshdk))).setText(performanceDkXzbnbldkzb.getDkqmye()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqyshdk))).setText(performanceDkXzbnbldkzb.getBldkye()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqdk))).setText(performanceDkXzbnbldkzb.getBldkzb()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqdkshl))).setText(performanceDkXzbnbldkzb.getSybldkzb()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_kjgzjs))).setText(performanceDkXzbnbldkzb.getKjgzjs()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbgz))).setText(performanceDkXzbnbldkzb.getZbgz()+"");
		
		return view;
	}
	
}
