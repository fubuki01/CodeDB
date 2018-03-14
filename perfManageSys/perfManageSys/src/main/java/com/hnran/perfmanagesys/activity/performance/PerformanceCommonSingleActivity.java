package com.hnran.perfmanagesys.activity.performance;

import java.util.Map;
import java.util.Map.Entry;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.SearchActivity;
import com.hnran.perfmanagesys.utils.ToastUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 绩效工资三级界面，查看单条记录页面（此类只含有一条数据）
 * 公用Activity父类，处理共同界面显示，加载数据放到子类中实现
 *
 */
public class PerformanceCommonSingleActivity extends BaseActivity{

	private String mTitle;

	private TextView tvText;
	private ProgressBar progressBar;

	private ImageView ivSearch;
	
	private TextView tvDate, tvTotalWages;
	protected String total;

	protected String date;
	
	protected String zbid;

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
	 * 结果为空
	 */
	protected boolean isNull = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		mTitle = intent.getStringExtra("Extra_title");
		lx = intent.getIntExtra("Extra_lx", 1);
		type = intent.getIntExtra("Extra_type", 0);
		date = intent.getStringExtra("Extra_date");
		zbid = intent.getStringExtra("Extra_zbid");

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

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
		total = "0";
		tvTotalWages.setText(total);
		
		progressBar.setVisibility(View.VISIBLE);
		tvText.setVisibility(View.GONE);
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

		progressBar.setVisibility(View.GONE);

		if(!isNull){
			View view = addDefaultLayout();
			if(view == null)
				view = addCustomerLayout();
			
			if(view != null){
				FrameLayout fl = (FrameLayout) findViewById(R.id.performance_framelayout);
				fl.addView(view);
			} 
		}else{
			tvText.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 添加默认的布局（每行显示两种类别），传入名称和内容，
	 * 若需换行可重写addCustomerLayout方法，实现加载新布局
	 */
	@SuppressLint({ "InflateParams", "NewApi" })
	private View addDefaultLayout(){
		Map<String, String> map = getStringMap();
		if(map == null) return null;
		LinearLayout parent = new LinearLayout(this);
		parent.setOrientation(LinearLayout.VERTICAL);
//		parent.setLayoutParams(new LinearLayout.LayoutParams((LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);  
		parent.setLayoutParams(params); 
		parent.setPadding(5, 2, 5, 2);
		parent.setBackground(getResources().getDrawable(R.drawable.selector_home_tips));
		
		int i = 1, size = map.size();
//		LinearLayout line = null;
		View view = null;
		for(Entry<String, String> entry : map.entrySet()){
			
			String title = entry.getKey();
			String content = entry.getValue();
			
			if(i % 2 == 1){
				view = LayoutInflater.from(this).inflate(R.layout.item_single_perf, null);
				((TextView)view.findViewById(R.id.item_single_perf_title_1)).setText(title);
				((TextView)view.findViewById(R.id.item_single_perf_content_1)).setText(content);
				if( i == size){
					parent.addView(view);
				}
			}else{
				((TextView)view.findViewById(R.id.item_single_perf_title_2)).setText(title);
				((TextView)view.findViewById(R.id.item_single_perf_content_2)).setText(content);
				parent.addView(view);
			}
			
			i++;
		}
//		parent.invalidate();
		
		return parent;
	}
	
	/**
	 * 创建显示信息，子类重写
	 * 若使用默认布局，则必须实现该方法
	 * 若使用addCustomerLayout方法，则不用实现该方法
	 * @return
	 */
	public Map<String, String> getStringMap() {
		return null;
	}
	
	/**
	 * 默认布局不满足显示要求，需要加载布局时，覆写该方法
	 */
	public View addCustomerLayout(){
		return null;
	}
	

	protected final static int FINISHED = 300004;
	protected final static int ISNULL = 300007;
	protected final static int ERROR = 300005;
	protected final static int ADD = 300006;
	
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case FINISHED:

				isNull = false;
				notifyView();

				break;
			case ISNULL:
				isNull = true;
				notifyView();
				break;

			case ERROR:

				notifyView();
				ToastUtil.showToast(PerformanceCommonSingleActivity.this, CommonContent.ERROR_NETWORK);
				
				break;
				
			case ADD:
				tvTotalWages.setText(total);
				break;
			}

		};		
	};
	
	public void gotoSearchActivity(){
		Intent intent = new Intent(PerformanceCommonSingleActivity.this, SearchActivity.class);
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
