package com.hnran.perfmanagesys.activity;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.customer.CustomerInfoFragment;
import com.hnran.perfmanagesys.fragment.customer.CustomerProductFragment2;
import com.hnran.perfmanagesys.fragment.customer.CustomerRelationFragment;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerActivity extends FragmentActivity{

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;
	
	private List<TextView> tvLists;

	private LinearLayout llBack;
	
	private HorizontalScrollView horizontalScrollView;
	
	private int currentPosition;
	private int oldPosition;
	
	private int width;
	
	private Resources mResources;
	
	private String mTitle;
	
	
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
		
		WindowManager wm = this.getWindowManager();

	    width = wm.getDefaultDisplay().getWidth();
	    
	    mTitle = getIntent().getStringExtra("Extra_title");
	    
	    mResources = getResources();
		
		oldPosition = currentPosition = 0;

		fragmentLists = new ArrayList<Fragment>();

		vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentLists);
	
		tvLists = new ArrayList<TextView>();
		
		
	}

	
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_customer); 
		
		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText(mTitle);
		
		llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		viewPager = (ViewPager) findViewById(R.id.customer_viewpager);
		viewPager.setAdapter(vpAdapter);
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
		
		TextView tv0 = (TextView) findViewById(R.id.customer_tv_jiben);
		tv0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 0;
				changeTabState();
			}
		});
		
		TextView tv1 = (TextView) findViewById(R.id.customer_tv_guanlianren);
		tv1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 1;
				changeTabState();
			}
		});
		
		TextView tv2 = (TextView) findViewById(R.id.customer_tv_chanpin);
		tv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 2;
				changeTabState();
			}
		});
		
		
		tvLists.add(tv0);
		tvLists.add(tv1);
		tvLists.add(tv2);
		
	}


	protected void loadData() {
		// TODO Auto-generated method stub
		
		fragmentLists.add(new CustomerInfoFragment());
		fragmentLists.add(new CustomerRelationFragment());
		fragmentLists.add(new CustomerProductFragment2());

		vpAdapter.notifyDataSetChanged();
		
		viewPager.setCurrentItem(currentPosition);
		
		changeTabState();
	}
	
	@SuppressLint("NewApi")
	public void changeTabState(){
		
		TextView old = tvLists.get(oldPosition);
		TextView current = tvLists.get(currentPosition);
		
		old.setBackgroundColor(color.transparent);
		old.setTextColor(mResources.getColor(R.color.font_black_1));
		
		current.setBackgroundColor(mResources.getColor(R.color.bg_tab_selected));
		current.setTextColor(mResources.getColor(R.color.font_tab_selected));
		
		if(viewPager.getCurrentItem() != currentPosition){
			viewPager.setCurrentItem(currentPosition);
		}
		
			
	}
	

}
