package com.hnran.perfmanagesys.activity.warning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.adapter.ADataLoadAdapter;
import com.hnran.perfmanagesys.adapter.WarningBaoshouDaikuanAdapter;
import com.hnran.perfmanagesys.adapter.WarningBaoshouQianxiAdapter;
import com.hnran.perfmanagesys.adapter.WarningBaoshouXinzengDaikuanAdapter;
import com.hnran.perfmanagesys.adapter.WarningDingqiAdapter;
import com.hnran.perfmanagesys.adapter.WarningNianShouhuilvAdapter;
import com.hnran.perfmanagesys.adapter.WarningYuqiDaikuanAdapter;
import com.hnran.perfmanagesys.utils.DatePickerUtil;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.SimpleBSLNFFExpireRecoveryRate;
import com.readboy.ssm.po.SimpleBSLoanExpireDetail;
import com.readboy.ssm.po.SimpleBSLoanOweInterestDetail;
import com.readboy.ssm.po.SimpleBSNewAddBadLoanDetail;
import com.readboy.ssm.po.SimpleBSOverdueLoanDetail;
import com.readboy.ssm.po.SimpleDepositExpireDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WarningCommonActivity extends BaseActivity{
	
	private final int FINISHED = 4000;
	private final int ERROR = 4001;

	private String mTitle;

	private PullToRefreshListView listView;
	private ADataLoadAdapter adapter = null;

	private ProgressBar progressBar;

	private TextView tvDate, tvText;
	
	private String date;

	private int type;
	
	/**
	 * 2017-10-27 
	 * 修改
	 * 显示条目总数
	 */
	private TextView tvNumberLable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		mTitle = getIntent().getStringExtra("Extra_title");
		type = getIntent().getIntExtra("Extra_type", 0);

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_warning_common);

		progressBar = (ProgressBar) findViewById(R.id.warning_progressbar);

		tvText = (TextView) findViewById(R.id.warning_tv_text);
		
		tvDate = (TextView) findViewById(R.id.warning_common_tv_date);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.warning_common_rl_date);
		rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

//				DatePickUtil.showDatePickDlg(WarningCommonActivity.this, tvDate, true, handler);
				DatePickerUtil.showYearMonthPicker(WarningCommonActivity.this, tvDate, handler);
//				switch(type){
//				case 0:
//				case 1:
//				case 2:
//				case 3:
//				case 6:
//					DatePickerUtil.showYearMonthPicker(WarningCommonActivity.this, tvDate, handler);
//					break;
//				case 4:
//				case 5:
//					DatePickerUtil.showYearPicker(WarningCommonActivity.this, tvDate, handler);
//					break;
//				}
			}
		});
		
		
		date = DateUtil.getCurrentTime("yyyy-MM-dd");
		
		tvDate.setText(date.substring(0, 7));
		
//		switch(type){
//		case 0:
//		case 1:
//		case 2:
//		case 3:
//		case 6:
//			tvDate.setText(date.substring(0, 7));
//			break;
//		
//		case 5:
//			rl.setFocusable(false);
//		case 4:
//			tvDate.setText(date.substring(0, 4));
//			break;
//		}

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText(mTitle+"");
		if(mTitle.length() > 10){
			tv.setTextSize(16);
		}

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		listView = (PullToRefreshListView) findViewById(R.id.warning_listview);
		
		listView.setMode(Mode.DISABLED);		//下拉刷新，上拉加载
		listView.getLoadingLayoutProxy(true,false).setRefreshingLabel("正在刷新");
		listView.getLoadingLayoutProxy(true,false).setPullLabel("下拉刷新");
		listView.getLoadingLayoutProxy(true,false).setReleaseLabel("释放开始刷新");
		listView.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载");
		listView.getLoadingLayoutProxy(false,true).setPullLabel("上拉加载更多");
		listView.getLoadingLayoutProxy(false,true).setReleaseLabel("释放开始加载");

		String label = "上次更新：" + DateUtil.getCurrentTime();  

		// Update the LastUpdatedLabel  
		listView.getLoadingLayoutProxy()  
		.setLastUpdatedLabel(label); 

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullDownToRefresh");

				String label = "上次更新：" + DateUtil.getCurrentTime();  

				// Update the LastUpdatedLabel  
				refreshView.getLoadingLayoutProxy()  
				.setLastUpdatedLabel(label); 

				reLoadData();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullUpToRefresh");

				//				new GetDataTask().execute();

				loadData();

			}
		});

		tvNumberLable = (TextView) findViewById(R.id.performance_tv_lable);
		
	}

	public void reLoadData(){
		if(adapter != null){
			adapter.clearData();
			adapter.notifyDataSetChanged();
		}
		loadData();
	}
	
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

		progressBar.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
		tvText.setVisibility(View.GONE);
		tvNumberLable.setVisibility(View.GONE);
		
		StringRequest stringRequest = null;

		String url = "";
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("gyh", PMSApplication.gUser.getTellId());
		int size = adapter == null ? 0 : adapter.getCount();
		paras.put("currentResult", size +"");

		switch(type){
		case 0:
			if(adapter == null) adapter = new WarningDingqiAdapter();
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleDepositExpireDetail.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleDepositExpireDetail> simpleDepositExpireDetail = JSON.parseArray(response, SimpleDepositExpireDetail.class);
					((WarningDingqiAdapter)adapter).addData(simpleDepositExpireDetail);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		case 1:
			if(adapter == null) adapter = new WarningBaoshouDaikuanAdapter();
	
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSLoanExpireDetail.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleBSLoanExpireDetail> simpleBSLoanExpireDetail = JSON.parseArray(response, SimpleBSLoanExpireDetail.class);
					((WarningBaoshouDaikuanAdapter)adapter).addData(simpleBSLoanExpireDetail);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		case 2:
			if(adapter == null) adapter = new WarningBaoshouXinzengDaikuanAdapter();
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSNewAddBadLoanDetail.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleBSNewAddBadLoanDetail> t = JSON.parseArray(response, SimpleBSNewAddBadLoanDetail.class);
					((WarningBaoshouXinzengDaikuanAdapter)adapter).addData(t);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		case 3:
			if(adapter == null) adapter = new WarningBaoshouQianxiAdapter();
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSLoanOweInterestDetail.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  

					List<SimpleBSLoanOweInterestDetail> simpleBSNewAddBadLoanDetail = JSON.parseArray(response, SimpleBSLoanOweInterestDetail.class);
					((WarningBaoshouQianxiAdapter)adapter).addData(simpleBSNewAddBadLoanDetail);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		case 4:
			if(adapter == null) adapter = new WarningNianShouhuilvAdapter();
			
//			paras.put("ksrq", DateUtil.getCurrentYearFirstDay(date));
//			paras.put("jsrq", DateUtil.getCurrentYearLastDay(date));
			
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthFirstDay(date));
			
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSLNFFExpireRecoveryRate.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleBSLNFFExpireRecoveryRate> simpleBSLNFFExpireRecoveryRate = JSON.parseArray(response, SimpleBSLNFFExpireRecoveryRate.class);
					((WarningNianShouhuilvAdapter)adapter).addData(simpleBSLNFFExpireRecoveryRate);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;
		case 5:
			if(adapter == null) adapter = new WarningNianShouhuilvAdapter();
			
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthFirstDay(date));
			
//			paras.put("ksrq", DateUtil.getCurrentYearFirstDay(date));
//			paras.put("jsrq", DateUtil.getCurrentYearLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSDNFFExpireRecoveryRate.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleBSLNFFExpireRecoveryRate> simpleBSLNFFExpireRecoveryRate = JSON.parseArray(response, SimpleBSLNFFExpireRecoveryRate.class);
					((WarningNianShouhuilvAdapter)adapter).addData(simpleBSLNFFExpireRecoveryRate);
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		case 6:
			if(adapter == null) adapter = new WarningYuqiDaikuanAdapter();
			
			paras.put("ksrq", DateUtil.getCurrentMonthFirstDay(date));
			paras.put("jsrq", DateUtil.getCurrentMonthLastDay(date));
			url = MakeUrl.makeURL(new String[]{"mobileGetSimpleBSOverdueLoanDetail.action"}, paras);
			stringRequest = new StringRequest(Method.POST, url,  
					new Response.Listener<String>() {  
				@Override  
				public void onResponse(String response) {  
					Log.d("TAG", response);  
					List<SimpleBSOverdueLoanDetail> simpleBSOverdueLoanDetail = JSON.parseArray(response, SimpleBSOverdueLoanDetail.class);
					((WarningYuqiDaikuanAdapter)adapter).addData(simpleBSOverdueLoanDetail);
					
					handler.sendEmptyMessage(FINISHED);
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error); 
					ToastUtil.showToast(WarningCommonActivity.this, CommonContent.ERROR_NETWORK);
					handler.sendEmptyMessage(ERROR);
				}  
			}); 
			break;

		}

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

		progressBar.setVisibility(View.GONE);
		
		if(adapter.getCount() <= 0){
//			ToastUtil.showToast(this, "没有数据");
			tvText.setVisibility(View.VISIBLE);
			return ;
		}else{
			listView.setAdapter(adapter);
//			adapter.notifyDataSetChanged();
			listView.setVisibility(View.VISIBLE);
			tvNumberLable.setVisibility(View.VISIBLE);
			tvNumberLable.setText(adapter.getCount()+"");
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ToastUtil.cancelToast(this);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DatePickerUtil.CHANGE_YEAR_MONTH:

//				progressBar.setVisibility(View.VISIBLE);
//				listView.setVisibility(View.GONE);
//				tvText.setVisibility(View.GONE);

				date = tvDate.getText().toString().trim() + "-01";
				
				reLoadData();
				break;
				
			case DatePickerUtil.CHANGE_YEAR:

//				progressBar.setVisibility(View.VISIBLE);
//				listView.setVisibility(View.GONE);
//				tvText.setVisibility(View.GONE);

				date = tvDate.getText().toString().trim() + "-01-01";;
				
				reLoadData();
				break;
			
			case FINISHED:
				notifyView();
				break;
				
			case ERROR:
				progressBar.setVisibility(View.GONE);
				tvText.setVisibility(View.VISIBLE);
				break;
				
			}

		};		
	};

	
}
