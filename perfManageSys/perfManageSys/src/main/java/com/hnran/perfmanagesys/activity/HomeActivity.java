package com.hnran.perfmanagesys.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.home.CustomerFragment;
import com.hnran.perfmanagesys.fragment.home.MarketFragment;
import com.hnran.perfmanagesys.fragment.home.PerformanceFragment;
import com.hnran.perfmanagesys.fragment.home.PerformanceListFragment;
import com.hnran.perfmanagesys.fragment.home.PerformanceMonthFragment;
import com.hnran.perfmanagesys.fragment.home.StatisticsFragment;
import com.hnran.perfmanagesys.fragment.home.WarningFragment;
import com.hnran.perfmanagesys.service.WebSocketService;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.view.NoScrollViewPager;
import com.readboy.ssm.po.Menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 主页面
 * @author tan
 *
 */
public class HomeActivity extends FragmentActivity {

	/**
	 * 从服务器获取菜单选项
	 */
	private List<Menu> menus;

	private List<LinearLayout> llTabs;
	private List<TextView> tvTabs;
	private List<ImageView> ivTabs;
	
	private ProgressBar progressBar;

	private int[] ivIds = {R.drawable.customer, R.drawable.market, R.drawable.performance, R.drawable.performance, R.drawable.warning};
	private int[] ivSltIds = {R.drawable.customer_selected, R.drawable.market_selected, R.drawable.performance_selected, R.drawable.performance_selected, R.drawable.warning_selected};

	private NoScrollViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;
	private int currentPosition;
	private int oldPosition;

	private Resources mRresources;


	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = this.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(this.getResources().getColor(R.color.bg_title));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		initVariables();
		initViews(savedInstanceState);
		loadData();

		Intent service = new Intent(HomeActivity.this, WebSocketService.class);
		startService(service);

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		mRresources = this.getResources();

		currentPosition = oldPosition = 0;

		fragmentLists = new ArrayList<Fragment>();

		vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentLists);

		llTabs = new ArrayList<LinearLayout>();

		tvTabs = new ArrayList<TextView>();

		ivTabs = new ArrayList<ImageView>();

	}

	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_home);

		LinearLayout kehu = (LinearLayout) findViewById(R.id.home_ll_1);
		llTabs.add(kehu);
		kehu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 0;
				changeTabState();
			}
		});

		LinearLayout yingxiao = (LinearLayout) findViewById(R.id.home_ll_2);
		llTabs.add(yingxiao);
		yingxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 1;
				changeTabState();
			}
		});

		LinearLayout jixiao = (LinearLayout) findViewById(R.id.home_ll_3);
		llTabs.add(jixiao);
		jixiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 2;
				changeTabState();
			}
		});

		LinearLayout jixiao2 = (LinearLayout) findViewById(R.id.home_ll_4);
		llTabs.add(jixiao2);
		jixiao2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 3;
				changeTabState();
			}
		});

		LinearLayout jiankong = (LinearLayout) findViewById(R.id.home_ll_5);
		llTabs.add(jiankong);
		jiankong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 4;
				changeTabState();
			}
		});

		tvTabs.add((TextView) findViewById(R.id.home_tv_kehu));
		tvTabs.add((TextView) findViewById(R.id.home_tv_yingxiao));
		tvTabs.add((TextView) findViewById(R.id.home_tv_jixiao));
		tvTabs.add((TextView) findViewById(R.id.home_tv_jixiao_2));
		tvTabs.add((TextView) findViewById(R.id.home_tv_jiankong));

		ivTabs.add((ImageView) findViewById(R.id.home_iv_1));
		ivTabs.add((ImageView) findViewById(R.id.home_iv_2));
		ivTabs.add((ImageView) findViewById(R.id.home_iv_3));
		ivTabs.add((ImageView) findViewById(R.id.home_iv_4));
		ivTabs.add((ImageView) findViewById(R.id.home_iv_5));

		viewPager = (NoScrollViewPager) findViewById(R.id.home_viewpager);
		viewPager.setNoScroll(true);
		viewPager.setAdapter(vpAdapter);
		viewPager.setOffscreenPageLimit(4); 
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = arg0;
				changeTabState();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});

		progressBar = (ProgressBar) findViewById(R.id.home_progressbar);

	}

	protected void loadData() {
		// TODO Auto-generated method stub
		
//		fragmentLists.clear();
//		fragmentLists.add(new CustomerFragment());
//		fragmentLists.add(new MarketFragment());
//		fragmentLists.add(new PerformanceFragment());
//		fragmentLists.add(new PerformanceMonthFragment());
//		fragmentLists.add(new WarningFragment());
//		
//		vpAdapter.notifyDataSetChanged();
		
		requestMenu();
	}


	public void changeTabState(){


		tvTabs.get(oldPosition).setTextColor(mRresources.getColor(R.color.font_black_2));

		tvTabs.get(currentPosition).setTextColor(mRresources.getColor(R.color.font_tab_selected));

		ivTabs.get(oldPosition).setImageResource(ivIds[oldPosition]);

		ivTabs.get(currentPosition).setImageResource(ivSltIds[currentPosition]);


		if(viewPager.getCurrentItem() != currentPosition){
			viewPager.setCurrentItem(currentPosition);
		}
	}

	/**
	 * 连续按两次返回键退出应用，防止误触
	 */
	private long lastBackKeyTouchTime = 0;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - lastBackKeyTouchTime < 3000) {
				finish();
			} else {
				ToastUtil.showToast(this, "再按一次返回退出应用");
				lastBackKeyTouchTime = System.currentTimeMillis();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	public void requestMenu(){
		StringRequest stringRequest = new StringRequest(MakeUrl.makeURL(new String[]{"responseMenuJson.action"}),  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					menus = JSON.parseArray(response, Menu.class);
				}catch(JSONException e){
					e.printStackTrace();
				}
				if(menus != null && menus.size() != 0 ) 
					handler.sendEmptyMessage(1);
				else{
					progressBar.setVisibility(View.GONE);
					ToastUtil.showToast(HomeActivity.this, CommonContent.ERROR_HOME_RESULT);
				}
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  

			}  
		}); 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
			if(msg.what == 1) updateMenus();
		}
	};

	private void updateMenus(){
		
		progressBar.setVisibility(View.GONE);
		
		BaseFragment[] fragments = {new CustomerFragment(), new MarketFragment(), new PerformanceListFragment(), new StatisticsFragment(), new WarningFragment() };

		fragmentLists.clear();
		
		int first = -1;
		
		for(int i = 0, size = tvTabs.size(); i < size; i++){
			Menu menu = menus.get(i);
//			int id = menu.getId();
			
			String title = menu.getName();
			fragments[i].setTitle(title);
			fragmentLists.add(fragments[i]);		//同步修改二级界面标题
			
			try{
				if(menu.getShow() == 1){
					
					if(first == -1) 
						first = i;
					
					llTabs.get(i).setVisibility(View.VISIBLE);
					if(title != null){
						tvTabs.get(i).setText(title);
					}
				}
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			
		}

		vpAdapter.notifyDataSetChanged();
		
		currentPosition = oldPosition = first;
		viewPager.setCurrentItem(currentPosition);
		changeTabState();
		
	}

}
