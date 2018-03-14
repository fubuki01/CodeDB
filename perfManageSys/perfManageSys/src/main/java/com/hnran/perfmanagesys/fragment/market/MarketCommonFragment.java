package com.hnran.perfmanagesys.fragment.market;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.market.MarketCheckActivity;
import com.hnran.perfmanagesys.adapter.MarketCommonAdapter;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.readboy.ssm.po.Marketing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * 营销申报父类，
 * @author tan
 *
 */
public abstract class MarketCommonFragment extends Fragment{

	public final static int CUNKUAN = 0;
//	public final static int DAIKUAN = 1;
	public final static int SHOUJI = 1;
	public final static int ETC = 2;
	public final static int POS = 3;

	public final static int OTHER = -1;

	public final static String[] TITILS = {"存款营销","手机银行营销","ETC营销","POS机营销"};

	public final int REFRESH_DATA = 10001;
	public final int NOTIFY_LISTVIEW = 10002;
	public final int REQUEST_ERROR = 10003;
	
	private TextView tvText;

//	private ProgressBar progressBar;
	
	private PullToRefreshListView lvMarket;

	private MarketCommonAdapter mMarketCommonAdapter;
	protected List<? extends Marketing> mMarketings;

	//2017-10-27 增加显示条目总数
	private TextView tvNumberLable;
	protected Integer mNum;
	

	/**
	 * 类型，根据不同类型选择更新类型 存款营销申请、贷款、手机银行
	 */
	protected int mType;
	
	public MarketCommonFragment(){
	}

	public MarketCommonFragment(int type){
		this.mType = type;
	}

	
	
	protected Handler UIHandler = new Handler(){
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch(msg.what){
				case REFRESH_DATA:
					refreshDataUpdateView();
					break;
					
				case NOTIFY_LISTVIEW:
					notifyListView();
					break;
					
				case REQUEST_ERROR:
					notifyListView();
					ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
					break;
			}
			
			
		};
	};
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_market_cunkuan, null);

		initVariables();
		initViews(view);
		loadData("");
//		refreshDataUpdateView();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

//		mMarketings = new ArrayList<Marketing>();

//		mMarketings.add(new Marketing(1, "张三","12312312312","营业部"));
//		mMarketings.add(new Marketing(2, "张三","12312312312","营业部"));
//		mMarketings.add(new Marketing(3, "张三","12312312312","营业部"));

		mMarketCommonAdapter = new MarketCommonAdapter(getActivity(), mMarketings);
	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub

		lvMarket = (PullToRefreshListView) view.findViewById(R.id.market_lv_customer);
		lvMarket.setAdapter(mMarketCommonAdapter);
		lvMarket.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
				
				gotoMarketCheckActivity(position - 1);

			}});

		lvMarket.getRefreshableView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("tt","onItemLongClick");
				showPopupWindow(view, position - 1);	//这里注意下标要减1
				return true;
			}
		});


		lvMarket.setMode(Mode.BOTH);
		lvMarket.getLoadingLayoutProxy(true,false).setRefreshingLabel("正在刷新");
		lvMarket.getLoadingLayoutProxy(true,false).setPullLabel("下拉刷新");
		lvMarket.getLoadingLayoutProxy(true,false).setReleaseLabel("释放开始刷新");
		lvMarket.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载");
		lvMarket.getLoadingLayoutProxy(false,true).setPullLabel("上拉加载更多");
		lvMarket.getLoadingLayoutProxy(false,true).setReleaseLabel("释放开始加载");

		String label = "上次更新：" + DateUtil.getCurrentTime();  

		// Update the LastUpdatedLabel  
		lvMarket.getLoadingLayoutProxy()  
		.setLastUpdatedLabel(label); 


		lvMarket.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullDownToRefresh");

				String label = "上次更新：" + DateUtil.getCurrentTime();  

				// Update the LastUpdatedLabel  
				refreshView.getLoadingLayoutProxy()  
				.setLastUpdatedLabel(label); 

				clearMarketings();
				
				loadData("");

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullUpToRefresh");

				loadData("");

			}
		});

//		progressBar = (ProgressBar) view.findViewById(R.id.market_progressbar);

		tvText = (TextView) view.findViewById(R.id.market_tv_text);

		tvNumberLable = (TextView) view.findViewById(R.id.performance_tv_lable);
	}

//	protected void loadData() {
//		// TODO Auto-generated method stub
//		loadData("");
//	}
	
	public void loadData(String data) {
		// TODO Auto-generated method stub
		
//		if(mMarketings != null) mMarketings.clear();
		
//		progressBar.setVisibility(View.VISIBLE);
		tvText.setVisibility(View.GONE);
		tvNumberLable.setVisibility(View.GONE);
	}
	
	protected boolean deleteData(int yybh) {
		// TODO Auto-generated method stub
		return true;
	}	
	
	protected void refreshDataUpdateView(){
		mMarketCommonAdapter.setData(mMarketings);
		notifyListView();
	}
	
	
	private void notifyListView(){
		
//		progressBar.setVisibility(View.GONE);
		
		lvMarket.onRefreshComplete();
		
		mMarketCommonAdapter.notifyDataSetChanged();
		
		if(mMarketCommonAdapter.getCount() == 0){
			tvText.setVisibility(View.VISIBLE);
		}else{
			tvNumberLable.setVisibility(View.VISIBLE);
			tvNumberLable.setText(mNum+"");
		}
		
//		if(mMarketings != null && mMarketings.size() == 0){
//			tvText.setVisibility(View.VISIBLE);
//		}
	}
	
	public void gotoMarketCheckActivity(int position){
		
		Intent intent = new Intent(getActivity(), MarketCheckActivity.class);
		intent.putExtra("Extra_type", mType);	

		startActivity(intent);
	}

	private PopupWindow mPopWindow;
	public void showPopupWindow(View view, final int position){
		//设置contentView  
		View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_delete, null);  
		//		contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		mPopWindow = new PopupWindow(contentView,  
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);  
		mPopWindow.setContentView(contentView);  
		/**
		 * 解决mPopWindow获取不到宽高
		 */
		mPopWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);  
		int width = mPopWindow.getContentView().getMeasuredHeight() / 2;

		/**
		 * 这行必须加上，实现点击其他区域，弹框消失
		 */
		mPopWindow.setBackgroundDrawable(new ColorDrawable(0));  

		Button bt0 = (Button) contentView.findViewById(R.id.popwindow_bt_delete);
		bt0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(deleteData(position)){
					mMarketings.remove(position);

					UIHandler.sendEmptyMessage(NOTIFY_LISTVIEW);
				}

				mPopWindow.dismiss();  
			}
		});


		//显示PopupWindow  
		//		mPopWindow.showAtLocation(lvMarket, Gravity.CENTER, 0, 60);

		int yOff = view.getHeight();
		int xOff = getActivity().getResources().getDisplayMetrics().widthPixels / 2 - width;

		mPopWindow.showAsDropDown(view, xOff , -yOff);

	}
	
	
	public void updateInMarketHome() {
		// TODO Auto-generated method stub
		clearMarketings();
		loadData("");
//		refreshDataUpdateView();
	}
	
	public void clearMarketings(){
		if(mMarketings != null){
			mMarketings.clear();
		}
	}
	
}
