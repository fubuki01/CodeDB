package com.hnran.perfmanagesys.activity.performance;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.SearchActivity;
import com.hnran.perfmanagesys.adapter.ADataLoadAdapter;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.ToastUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 绩效工资三级界面，查看多条记录页面
 * 公用Activity父类，处理共同界面显示，加载数据绑定适配器放到子类中实现
 *
 */
public class PerformanceCommonActivity extends BaseActivity{

	private String mTitle;

	protected PullToRefreshListView listView;
	//	private List<PerformanceDepositHushuMx> mList;
	protected ADataLoadAdapter adapter = null;

	private TextView tvText;
	private ProgressBar progressBar;


	private ImageView ivSearch;

	private TextView tvDate, tvTotalWages, tvBumen, tvGangwei;
	protected String total;

	protected String date;

	protected int lx;

	protected int type;

	/**
	 * 搜索关键字
	 */
	protected String condition = ""; 
	/**
	 * 是否显示搜索
	 */
	protected boolean isSearch = false;

	/**
	 * 2017-10-27 
	 * 修改
	 * 显示条目总数
	 */
	protected Integer mNum;
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
		lx = getIntent().getIntExtra("Extra_lx", 1);
		type = getIntent().getIntExtra("Extra_type", 0);
		date = getIntent().getStringExtra("Extra_date");
		total = getIntent().getStringExtra("Extra_total");
		
		mNum = 1;
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_performance_common);

		ivSearch = (ImageView) findViewById(R.id.common_back_iv_search);
		if(isSearch) ivSearch.setVisibility(View.VISIBLE);
		ivSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoSearchActivity();
			}
		});

		tvText = (TextView) findViewById(R.id.performance_cunkuan_hushu_tv_text);

		progressBar = (ProgressBar) findViewById(R.id.performance_cunkuan_hushu_progressbar);

		tvDate = (TextView) findViewById(R.id.performance_cunkuan_hushu_tv_date);
		tvDate.setText(date);

		tvTotalWages = (TextView) findViewById(R.id.performance_cunkuan_hushu_tv_total);
		tvTotalWages.setText(total);

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

		listView = (PullToRefreshListView) findViewById(R.id.performance_cunkuan_hushu_listview);
		listView.setAdapter(adapter);

		listView.setMode(Mode.BOTH);		//下拉刷新，上拉加载
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
				
				condition = "";
				reLoadData();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullUpToRefresh");

				loadData();

			}
		});
		
		//
		tvNumberLable = (TextView) findViewById(R.id.performance_tv_lable);

	}

	protected void reLoadData(){
		if(adapter != null){
			adapter.clearData();
			adapter.notifyDataSetChanged();
			adapter = null;
			
		}
		
		loadData();
	}

	@Override
	protected void loadData(){
		// TODO Auto-generated method stub

		/**
		 * 修改：显示全部记录的合计，不需单独计算，只需在开始设置一次
		 */
		//		total = "0";
		//		tvTotalWages.setText(total);

		progressBar.setVisibility(View.VISIBLE);
		tvText.setVisibility(View.GONE);
		tvNumberLable.setVisibility(View.GONE);
		//		listView.setVisibility(View.GONE);

	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

		listView.onRefreshComplete();
		progressBar.setVisibility(View.GONE);

		if(adapter != null && adapter.getCount() > 0){
//			listView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			listView.setVisibility(View.VISIBLE);
			tvNumberLable.setVisibility(View.VISIBLE);
			tvNumberLable.setText(mNum+"");

		}else{
			//			ToastUtil.showToast(this, "没有数据");
			tvText.setVisibility(View.VISIBLE);
		}
	}


	protected final static int FINISHED = 300004;
	protected final static int ERROR = 300005;
	protected final static int ADD = 300006;

	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case FINISHED:

				notifyView();

				break;

			case ERROR:

				notifyView();
				ToastUtil.showToast(PerformanceCommonActivity.this, CommonContent.ERROR_NETWORK);

				break;

			case ADD:
				/**
				 * 修改：直接使用所有合计工资
				 */
				//				tvTotalWages.setText(total);
				break;
			}

		};		
	};

	public void gotoSearchActivity(){
		Intent intent = new Intent(PerformanceCommonActivity.this, SearchActivity.class);
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
				reLoadData();
			}
		}
	}

}
