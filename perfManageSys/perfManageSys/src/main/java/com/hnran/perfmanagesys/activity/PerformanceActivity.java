package com.hnran.perfmanagesys.activity;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.performance.PerfCommonFragment;
import com.hnran.perfmanagesys.view.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 绩效工资二级界面
 * @author tan
 *
 */
public class PerformanceActivity extends FragmentActivity{

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;
	
	private PagerSlidingTabStrip mTabs;
	private List<String> titleLists;
	
	private LinearLayout llBack;
	
	private String mDate;
	
	//修改：区分地区
	private String area;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		initVariables();
        initViews(savedInstanceState);
        loadData();

	}




	protected void initVariables() {
		// TODO Auto-generated method stub

		area = getIntent().getStringExtra("Extra_area");
		
		mDate = getIntent().getStringExtra("Extra_date");
		
		titleLists = new ArrayList<String>();
		
		fragmentLists = new ArrayList<Fragment>();
		
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_CUNKUAN));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_DAIKUAN));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_DIANZI, area));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_YEWU));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_GUANLI));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_DIQU));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_QITA));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_JINGYING));
		fragmentLists.add(new PerfCommonFragment(mDate, PerfCommonFragment.ZBLB_YANQI));
		
		titleLists.add("存款业绩");
		titleLists.add("贷款业绩");
		titleLists.add("电子银行业绩");
		titleLists.add("业务量");
		titleLists.add("管理绩效");
		titleLists.add("地区补差");
		titleLists.add("其它");
		titleLists.add("经营目标");
		titleLists.add("延期兑付");
		
		vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentLists, titleLists);
	
		
	}

	
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_performance);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("绩效工资明细");
		
		llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		viewPager = (ViewPager) findViewById(R.id.perf_viewpager);
		viewPager.setAdapter(vpAdapter);
		
		mTabs = (PagerSlidingTabStrip) findViewById(R.id.perf_tabs);
		mTabs.setViewPager(viewPager);
		
	}


	protected void loadData() {
		// TODO Auto-generated method stub
		
		int position = getIntent().getIntExtra("Extra_position",0);
		viewPager.setCurrentItem(position);
		
	}
	

}
