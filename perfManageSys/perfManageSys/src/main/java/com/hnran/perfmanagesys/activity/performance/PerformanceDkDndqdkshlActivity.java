package com.hnran.perfmanagesys.activity.performance;

import java.util.HashMap;
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
import com.readboy.ssm.po.PerformanceDkDndqdkshl;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PerformanceDkDndqdkshlActivity extends PerformanceCommonSingleActivity{
	
	private PerformanceDkDndqdkshl mPerformanceDkDndqdkshl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		super.loadData();
		String url = MakeUrl.makeURL(new String[]{"findPerformanceDkDndqdkshl.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{

					mPerformanceDkDndqdkshl = JSON.parseObject(response, PerformanceDkDndqdkshl.class);
					
					total = mPerformanceDkDndqdkshl.getZbgz().toString();
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
	
	@SuppressLint("InflateParams")
	@Override
	public View addCustomerLayout() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.item_performance_daikuan_dndqdkshl, null);
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zzmc))).setText(mPerformanceDkDndqdkshl.getZzjc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_gwmc))).setText(mPerformanceDkDndqdkshl.getGwmc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_ygxm))).setText(mPerformanceDkDndqdkshl.getYgxm());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbbs))).setText(mPerformanceDkDndqdkshl.getZzbz());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbmc))).setText(mPerformanceDkDndqdkshl.getZbmc());
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqwshdk))).setText(mPerformanceDkDndqdkshl.getDndqwsh()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqyshdk))).setText(mPerformanceDkDndqdkshl.getDndqysh()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqdk))).setText(mPerformanceDkDndqdkshl.getDndqdk()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_dndqdkshl))).setText(mPerformanceDkDndqdkshl.getDndqshl()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_kjgzjs))).setText(mPerformanceDkDndqdkshl.getKjgzjs()+"");
		((TextView)(view.findViewById(R.id.item_performance_daikuan_dndqdkshl_zbgz))).setText(mPerformanceDkDndqdkshl.getZbgz()+"");
		
		return view;
	}
	
}
