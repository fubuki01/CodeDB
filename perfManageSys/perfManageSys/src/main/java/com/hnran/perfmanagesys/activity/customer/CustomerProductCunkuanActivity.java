package com.hnran.perfmanagesys.activity.customer;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.adapter.CustomerProductCunkuanAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.DepositCustomer;

public class CustomerProductCunkuanActivity extends BaseActivity{

	private RecyclerView mRecyclerView;

	private CustomerProductCunkuanAdapter customerProductCunkuanAdapter;

	private DepositCustomer depositCustomer;

	private ProgressBar progressBar;

	private String khbh, jgdm;

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		khbh = getIntent().getStringExtra("Extra_khbh");
		jgdm = getIntent().getStringExtra("Extra_jgdm");

		customerProductCunkuanAdapter = new CustomerProductCunkuanAdapter(depositCustomer);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_customer_product_shouji);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("存款");

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		progressBar = (ProgressBar) findViewById(R.id.customer_product_shouji_progressbar);

		mRecyclerView = (RecyclerView) findViewById(R.id.costomer_product_shouji);

		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(customerProductCunkuanAdapter);

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

		String url = MakeUrl.makeURL(new String[]{"responseDepositCustomerJson.action"});

		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					depositCustomer = JSON.parseObject(response, DepositCustomer.class);
				}catch(JSONException e){
					e.printStackTrace();
				}
				Log.d("tt", depositCustomer.toString());

				progressBar.setVisibility(View.GONE);
				mRecyclerView.setVisibility(View.VISIBLE);

				notifyView();
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数

				Map<String, String> params = new HashMap<String, String>();
				params.put("khbh", khbh);
				params.put("jgdm", jgdm);

				return params;
			}
		}; 

		//2017-10-18 修改：设置网络请求时间
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				CommonContent.NETWORK_TIME_OUT_2, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub
		customerProductCunkuanAdapter.setData(depositCustomer);
		customerProductCunkuanAdapter.notifyDataSetChanged();
	}


}
