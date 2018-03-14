package com.hnran.perfmanagesys.activity.market;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.adapter.MarketChooseJgAdapter;
import com.hnran.perfmanagesys.adapter.MarketChooseJgAdapter.OnItemClickLitener;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.SimpleOrganization;

public class MarketChooseJigouActivity extends BaseActivity{
	
	private RecyclerView mRecyclerView;
	private List<SimpleOrganization> mDatas;
	private MarketChooseJgAdapter mAdapter;
	
	private ProgressBar progressBar;
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

//		mDatas = new ArrayList<String>();
//		for (int i = 'A'; i < 'Z'; i++)
//		{
//			mDatas.add("" + (char) i);
//		}

		mAdapter = new MarketChooseJgAdapter(this, mDatas);
//		mAdapter.setCurrent(2);
		mAdapter.setOnItemClickLitener(new OnItemClickLitener() {
			
			@Override
			public void onItemClick(View view, int position) {
				// TODO Auto-generated method stub
//				ToastUtil.showToast(MarketChooseJigouActivity.this, position+"");
				mAdapter.setCurrent(position);
				mAdapter.notifyDataSetChanged();
				
				Intent intent = new Intent();
				intent.putExtra("Extra_jigou_ywdm", mDatas.get(position).getYwjgdm());
				intent.putExtra("Extra_jigou_zzjc", mDatas.get(position).getZzjc());
				MarketChooseJigouActivity.this.setResult(0, intent);
				MarketChooseJigouActivity.this.finish();
			}
		});

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_market_choose_jg);
		
		progressBar = (ProgressBar) findViewById(R.id.market_choose_jg_progressbar);
		progressBar.setVisibility(View.VISIBLE);
		
		mRecyclerView = (RecyclerView) findViewById(R.id.market_choose_jg_recyclerview);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
		mRecyclerView.setAdapter(mAdapter);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
		String url = MakeUrl.makeURL(new String[]{"responseSimpleOrganizationJson.action"});

		StringRequest stringRequest = new StringRequest(url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				

				mDatas = JSON.parseArray(response, SimpleOrganization.class);
				Log.d("tt",mDatas.size() +"");
				
				notifyView();
				
				progressBar.setVisibility(View.GONE);
				
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				
			}  
		}); 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}
	
	
	public void onClickBack(View view) {
		// TODO Auto-generated method stub
		this.finish();
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub
		mAdapter.setData(mDatas);
		mAdapter.notifyDataSetChanged();
	}
	
}
