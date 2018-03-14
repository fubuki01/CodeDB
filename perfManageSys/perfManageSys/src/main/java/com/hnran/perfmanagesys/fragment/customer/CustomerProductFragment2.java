package com.hnran.perfmanagesys.fragment.customer;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.market.MarketCommonFragment;
import android.R.color;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class CustomerProductFragment2 extends Fragment{

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;

	private List<TextView> tvLists;

	private HorizontalScrollView horizontalScrollView;

	private int currentPosition;
	private int oldPosition;

	private int width;

	private Resources mResources;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_customer_product, null);

		initVariables();
		initViews(view);
		loadData();




		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		WindowManager wm = getActivity().getWindowManager();

		width = wm.getDefaultDisplay().getWidth();

		mResources = getResources();

		oldPosition = currentPosition = 0;

		fragmentLists = new ArrayList<Fragment>();

		vpAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentLists);

		tvLists = new ArrayList<TextView>();

	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub

		viewPager = (ViewPager) view.findViewById(R.id.customer_product_viewpager);
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

		TextView tv0 = (TextView)  view.findViewById(R.id.customer_product_tv_cunkuan);
		tv0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 0;
				changeTabState();
			}
		});

		TextView tv1 = (TextView) view.findViewById(R.id.customer_product_tv_daikuan);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 1;
				changeTabState();
			}
		});

		TextView tv2 = (TextView) view.findViewById(R.id.customer_product_tv_shouji);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 2;
				changeTabState();
			}
		});
		TextView tv3 = (TextView) view.findViewById(R.id.customer_product_tv_etc);
		tv3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oldPosition = currentPosition;
				currentPosition = 3;
				changeTabState();
			}
		});



		tvLists.add(tv0);
		tvLists.add(tv1);
		tvLists.add(tv2);
		tvLists.add(tv3);


	}


	protected void loadData() {
		// TODO Auto-generated method stub
		
//		fragmentLists.add(new MarketCommonFragment(1));
//		fragmentLists.add(new MarketCommonFragment(2));
//		fragmentLists.add(new MarketCommonFragment(3));
//		fragmentLists.add(new MarketCommonFragment(4));

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
