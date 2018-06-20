package com.hnran.perfmanagesys.fragment.home;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.performancebymonth.PerformanceMonthAljcActivity;
import com.hnran.perfmanagesys.activity.performancebymonth.PerformanceMonthGxlActivity;
import com.hnran.perfmanagesys.activity.performancebymonth.PerformanceMonthPhjfkActivity;
import com.hnran.perfmanagesys.adapter.PerformanceByMonthAdapter;
import com.hnran.perfmanagesys.adapter.PerformanceByMonthAdapter.LayoutClickListener;
import com.hnran.perfmanagesys.utils.DatePickerUtil;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceFindByMonthHome;
import com.readboy.ssm.po.PerformanceGwjxgzMxCustom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 绩效工资（按月）
 * @author Tower
 *
 */
public class PerformanceMonthFragment extends BaseFragment{

	private final static int FINISHED = 3101;
	private final static int ERROR = 3102;

	private TextView tvText;

	private TextView tvDate;

	private ProgressBar progressBar;

	private RecyclerView mRecyclerView;
	private PerformanceByMonthAdapter adapter;
	private PerformanceFindByMonthHome performanceFindByMonthHome; 
	
	private LayoutClickListener layoutClickListener;
	
	private String strDate;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DatePickerUtil.CHANGE_YEAR_MONTH:

				progressBar.setVisibility(View.VISIBLE);
				tvText.setVisibility(View.GONE);
				
				strDate = tvDate.getText().toString().trim();

				loadData();

				break;

			case FINISHED:

				notifyView();

				break;

			case ERROR:

				ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);

				notifyView();
				
				break;
			}

		};		
	};


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_performance_month, null);

		initVariables();
		initViews(view, savedInstanceState);
		loadData();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		layoutClickListener = new LayoutClickListener() {
			@Override
			public void onClick(int positionId, int index) {
				// TODO Auto-generated method stub
				
//				ToastUtil.showToast(PerformanceMonthFragment.this.getActivity(), positionId + "");
				
				Intent intent = null;
				String title = "";
				switch(positionId){
				case R.id.performance_month_rl_phjfk_title:
					intent = new Intent(PerformanceMonthFragment.this.getActivity(), PerformanceMonthPhjfkActivity.class);
					title = "平衡积分卡工资";
					
					break;
				case R.id.performance_month_rl_gxl_title:
					intent = new Intent(PerformanceMonthFragment.this.getActivity(), PerformanceMonthGxlActivity.class);
					title = "贡献率工资";
					break;
				case R.id.performance_month_rl_aljc_title:
					intent = new Intent(PerformanceMonthFragment.this.getActivity(), PerformanceMonthAljcActivity.class);
					title = "按量计酬工资";
					break;
				}
				
				if(intent != null){
					
					PerformanceGwjxgzMxCustom performanceGwjxgzMxCustom = performanceFindByMonthHome.getPerformanceGwjxgzMxCustomList().get(index);
					
					intent.putExtra("Extra_title", title);
					intent.putExtra("Extra_gwbz", performanceGwjxgzMxCustom.getGwbz()+"");
					intent.putExtra("Extra_zzbz", performanceGwjxgzMxCustom.getZzbz());
					intent.putExtra("Extra_gangwei", performanceGwjxgzMxCustom.getGwmc());
					intent.putExtra("Extra_bumen", performanceGwjxgzMxCustom.getZzjc());
					intent.putExtra("Extra_date", strDate);
					startActivity(intent);
				}
			}
		};

		adapter = new PerformanceByMonthAdapter(performanceFindByMonthHome);
		adapter.setLayoutClickListener(layoutClickListener);
		
	}


	protected void initViews(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
//		tv_title.setText(title);

		tvText = (TextView) view.findViewById(R.id.perf_tv_text);

		progressBar = (ProgressBar) view.findViewById(R.id.perf_progressbar);

		tvDate = (TextView) view.findViewById(R.id.perf_tv_date);
		if(savedInstanceState == null )	{	//只有Activity第一次进来时才设置默认当前日期
			strDate = DateUtil.getCurrentTime("yyyy-MM");
		}
		else{
			strDate = savedInstanceState.getString("Extra_date");
		}
		tvDate.setText(strDate);

		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.perf_rl_date);
		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerUtil.showYearMonthPicker(getActivity(), tvDate, handler);
			}
		});

		mRecyclerView = (RecyclerView) view.findViewById(R.id.perf_recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setAdapter(adapter);

	}


	protected void loadData() {
		// TODO Auto-generated method stub

		performanceFindByMonthHome = null;
		
		String url = MakeUrl.makeURL(new String[]{"findByMonthHome.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", "response：" + response);  

				try{
					performanceFindByMonthHome = JSON.parseObject(response, PerformanceFindByMonthHome.class);
					
				}catch(JSONException e){
					e.printStackTrace();
				}

				handler.sendEmptyMessage(FINISHED);

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 
				handler.sendEmptyMessage(ERROR);
//				ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();

				map.put("gyh", PMSApplication.gUser.getTellId());
				map.put("ksrq", strDate + "-01");
				map.put("jsrq", DateUtil.getCurrentMonthLastDay(strDate + "-01") );

				return map;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}

	public void notifyView(){
		
		progressBar.setVisibility(View.GONE);
		
		adapter.setData(performanceFindByMonthHome);
		adapter.notifyDataSetChanged();
		
		if(adapter.getItemCount() <= 1){
			tvText.setVisibility(View.VISIBLE);
		}

	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("Extra_date", strDate);
	};

}
