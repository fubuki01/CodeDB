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
import com.hnran.perfmanagesys.adapter.PerfCunkuanHushuAdapter;
import com.hnran.perfmanagesys.adapter.PerformanceCkmnlrgzMxAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceCkmnlrgzMx;
import com.readboy.ssm.po.PerformanceDepositHushuMx;

import android.os.Bundle;
import android.util.Log;

/**
 * 存款公用Activity
 * 包含存款三种大类型：存量模拟利润、增量模拟利润和存款户数
 */
public class PerformanceCunkuanCommonActivity extends PerformanceCommonActivity{

	public final static int CUNLIAN_MONI = 0;
	public final static int ZENGLIAN_MONI = 1;
	public final static int CUNKUAN_HUSHU = 2;
	public final static int CUNLIANG_ZONGHE = 3;
	public final static int ZENGLIANG_ZONGHE = 4;

	private String zzbz;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//设置显示搜索按钮
		isSearch = true;

		super.onCreate(savedInstanceState);

		zzbz = getIntent().getStringExtra("Extra_zzbz");
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
		super.loadData();
		
		String url = "";
		switch(type){
		case CUNLIAN_MONI:
			url = "findPerformanceClCkmnlrgzMx.action";
			break;
		case ZENGLIAN_MONI:
			url = "findPerformanceZlCkmnlrgzMx.action";
			break;
		case CUNKUAN_HUSHU:
			url = "responsePerformanceCkhsMxJson.action";
			break;

		case CUNLIANG_ZONGHE:
			url = "findZhhPerformanceClCkmnlrgzMx.action";
			break;

		case ZENGLIANG_ZONGHE:
			url = "findZhhPerformanceZlCkmnlrgzMx.action";
			break;
		}


		url = MakeUrl.makeURL(new String[]{url});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					switch(type){
					case CUNLIAN_MONI:
					case ZENGLIAN_MONI:
					case CUNLIANG_ZONGHE:
					case ZENGLIANG_ZONGHE:
						if(adapter == null){
							adapter = new PerformanceCkmnlrgzMxAdapter(type);
							listView.setAdapter(adapter);
						}
						
//						final List<PerformanceCkmnlrgzMx> list_zl = JSON.parseArray(response, PerformanceCkmnlrgzMx.class);
//						adapter.addData(list_zl);
						
						//
						JSONObject jsonObj = JSON.parseObject(response);  
						JSONArray array = jsonObj.getJSONArray("list"); 
						
						mNum = jsonObj.getIntValue("num");
						
						final List<PerformanceCkmnlrgzMx> list_zl = JSON.parseArray(array.toString(), PerformanceCkmnlrgzMx.class);
						adapter.addData(list_zl);
						
//						new Thread(new Runnable() {
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								BigDecimal b = new BigDecimal("0");
//								for(PerformanceCkmnlrgzMx l : list_zl){
//									if(type == CUNLIAN_MONI || type == CUNLIANG_ZONGHE)
//										b = b.add(l.getClgz());
//									else if(type == ZENGLIAN_MONI || type == ZENGLIANG_ZONGHE)
//										b = b.add(l.getZlgz());
//								}
//								total = b + "";
//								handler.sendEmptyMessage(ADD);
//							}
//						}).start();

						break;
					case CUNKUAN_HUSHU:
						if(adapter == null) adapter = new PerfCunkuanHushuAdapter();
						
						JSONObject jsonObj2 = JSON.parseObject(response);  
						JSONArray array2 = jsonObj2.getJSONArray("list"); 
						
						mNum = jsonObj2.getIntValue("num");
						
						final List<PerformanceDepositHushuMx> list = JSON.parseArray(array2.toString(), PerformanceDepositHushuMx.class);
						adapter.addData(list);

//						new Thread(new Runnable() {
//							@Override
//							public void run() {
//								// TODO Auto-generated method stub
//								BigDecimal b = new BigDecimal("0");
//								for(PerformanceDepositHushuMx l : list){
//									b = b.add(l.getGz());
//								}
//								total = b + "";
//								handler.sendEmptyMessage(ADD);
//							}
//						}).start();

						break;
					}

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
				params.put("zzbz", zzbz+"");
				//				params.put("zzbz", "2");
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
