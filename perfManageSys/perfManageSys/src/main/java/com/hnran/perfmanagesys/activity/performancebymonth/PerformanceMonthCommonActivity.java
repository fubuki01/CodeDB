package com.hnran.perfmanagesys.activity.performancebymonth;

import com.hnran.perfmanagesys.R;

import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.SearchActivity;
import com.hnran.perfmanagesys.utils.ToastUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *
 */
public class PerformanceMonthCommonActivity extends BaseActivity{

	private String mTitle;

	protected ListView listView;
	//	private List<PerformanceDepositHushuMx> mList;
	protected BaseAdapter adapter = null;

	private TextView tvText;
	private ProgressBar progressBar;

	
	private ImageView ivSearch;
	
	private TextView tvDate, tvTotalWages, tvBumen, tvGangwei;
	protected String total;

	protected String date, zzbz, gwbz, bumen, gangwei;

	/**
	 * 搜索关键字
	 */
	protected String condition = ""; 
	/**
	 * 是否显示搜索
	 */
	protected boolean isSearch = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		mTitle = getIntent().getStringExtra("Extra_title");
		date = getIntent().getStringExtra("Extra_date");
		zzbz = getIntent().getStringExtra("Extra_zzbz");
		gwbz = getIntent().getStringExtra("Extra_gwbz");
		bumen = getIntent().getStringExtra("Extra_bumen");
		gangwei = getIntent().getStringExtra("Extra_gangwei");

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_performance_month_common);

//		ivSearch = (ImageView) findViewById(R.id.common_back_iv_search);
//		if(isSearch) ivSearch.setVisibility(View.VISIBLE);
//		ivSearch.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				gotoSearchActivity();
//			}
//		});
		
		tvText = (TextView) findViewById(R.id.perfe_month_check_tv_text);
		
		progressBar = (ProgressBar) findViewById(R.id.perfe_month_check_progressbar);

		tvDate = (TextView) findViewById(R.id.perfe_month_check_tv_yuefen);
		tvDate.setText(date);

		tvTotalWages = (TextView) findViewById(R.id.perfe_month_check_tv_heji);
		
		tvBumen = (TextView) findViewById(R.id.perfe_month_check_tv_bumen);
		tvBumen.setText(bumen);
		
		tvGangwei = (TextView) findViewById(R.id.perfe_month_check_tv_gangwei);
		tvGangwei.setText(gangwei);
		
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

		listView = (ListView) findViewById(R.id.perfe_month_check_listview);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
		adapter = null;
		
		total = "0";
		tvTotalWages.setText(total);
		
		progressBar.setVisibility(View.VISIBLE);
		tvText.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

		progressBar.setVisibility(View.GONE);

		if(adapter != null && adapter.getCount() > 0){
			listView.setAdapter(adapter);
			listView.setVisibility(View.VISIBLE);
			
		}else{
//			ToastUtil.showToast(this, "没有数据");
			tvText.setVisibility(View.VISIBLE);
		}
	}


	protected final static int FINISHED = 310004;
	protected final static int ERROR = 310005;
	protected final static int ADD = 310006;
	
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case FINISHED:

				notifyView();

				break;

			case ERROR:

				notifyView();
				ToastUtil.showToast(PerformanceMonthCommonActivity.this, CommonContent.ERROR_NETWORK);
				
				break;
				
			case ADD:
				tvTotalWages.setText(total);
				break;
			}

		};		
	};
	
	public void gotoSearchActivity(){
		Intent intent = new Intent(PerformanceMonthCommonActivity.this, SearchActivity.class);
		startActivityForResult(intent, 111);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(data != null){

			if(resultCode == SearchActivity.SEARCH_RESULT_CODE)
			{
				condition = data.getStringExtra("Extra_result");
				loadData();
			}
		}
	}

}
