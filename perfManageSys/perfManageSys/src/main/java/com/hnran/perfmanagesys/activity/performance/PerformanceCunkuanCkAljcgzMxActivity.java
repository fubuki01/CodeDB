package com.hnran.perfmanagesys.activity.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request.Method;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.PerformanceCkAljcgzMxAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceCkAljcgzMx;

import android.os.Bundle;
import android.util.Log;

/**
 * 存量存款月日平余额业绩  和 综合
 * D90043 D90044
 * D90063 D90064
 */
public class PerformanceCunkuanCkAljcgzMxActivity extends PerformanceCommonActivity{

	private String zzbz;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		//设置显示搜索按钮
		isSearch = true;

		zzbz = getIntent().getStringExtra("Extra_zzbz");

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceCkAljcgzMx.action"});
		if(type == PerformanceCunkuanCommonActivity.CUNLIANG_ZONGHE || 
				type == PerformanceCunkuanCommonActivity.ZENGLIANG_ZONGHE){
			url = MakeUrl.makeURL(new String[]{"findZhhPerformanceCkAljcgzMx.action"});
		}
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				System.out.println(response);
				try{

					JSONObject jsonObj = JSON.parseObject(response);  
					JSONArray array = jsonObj.getJSONArray("list"); 

					mNum = jsonObj.getIntValue("num");

					final List<PerformanceCkAljcgzMx> list = JSON.parseArray(array.toString(), PerformanceCkAljcgzMx.class);

					if(adapter == null){
						adapter = new PerformanceCkAljcgzMxAdapter(lx);
						listView.setAdapter(adapter);
					}
					adapter.addData(list);

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

				params.put("tjrq", date);
				/**
				 * type区分综合和普通
				 */
				if(type == PerformanceCunkuanCommonActivity.CUNLIANG_ZONGHE ||
						type == PerformanceCunkuanCommonActivity.ZENGLIANG_ZONGHE)
					params.put("zzbz", zzbz);
				else {
					params.put("gyh", PMSApplication.gUser.getTellId());
				}
				/**
				 * lx区分存量、增量
				 */
				params.put("type", lx == 1 ? "cl":"zl");
				params.put("condition", condition);
				int size = adapter == null ? 0 : adapter.getCount();
				params.put("currentResult", size +"");
				return params;
			}
		}; 

		//2017-10-18 修改：设置网络请求时间
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				20000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}
}
