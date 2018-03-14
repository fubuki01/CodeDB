package com.hnran.perfmanagesys.fragment.home;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.SearchActivity;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.customer.CustomerCommonFragment;
import com.hnran.perfmanagesys.fragment.customer.CustomerPersonalFragment;
import com.hnran.perfmanagesys.fragment.customer.CustomerPublicFragment;
import com.hnran.perfmanagesys.view.PagerSlidingTabStrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

//import static com.baidu.location.d.j.R;

public class CustomerFragment extends BaseFragment{

	private LinearLayout llMore;

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;

	private PagerSlidingTabStrip mTabs;
	private List<String> titleLists;

	private ImageView ivSearch;
	
//	private Fragment mCustomerPersonalFragment, mCustomerPublicFragment;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_customer, null);

		initVariables();
		initViews(view);
		loadData();


		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		titleLists = new ArrayList<String>();

		fragmentLists = new ArrayList<Fragment>();
		
		fragmentLists.add(new CustomerPersonalFragment());
		fragmentLists.add(new CustomerPublicFragment());

		titleLists.add("个人客户");
		titleLists.add("对公客户");

		vpAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentLists, titleLists);

	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub

		TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
		tv_title.setText(title);
		
		ivSearch = (ImageView) view.findViewById(R.id.common_top_iv_search);
		ivSearch.setVisibility(View.VISIBLE);
		ivSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				gotoSearchActivity();

			}
		});


		viewPager = (ViewPager) view.findViewById(R.id.customer_viewpager);
		viewPager.setAdapter(vpAdapter);

		mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.customer_tabs);
		mTabs.setViewPager(viewPager);

	}


	protected void loadData() {
		// TODO Auto-generated method stub

	}


	private PopupWindow mPopWindow;
	public void showPopupWindow(){
		//设置contentView  
		View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_customer_more, null);  
		mPopWindow = new PopupWindow(contentView,  
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);  
		/**
		 * 解决mPopWindow获取不到宽高
		 */
		mPopWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);  

		int popWidth=mPopWindow.getContentView().getMeasuredWidth();  



		TextView tv0 = (TextView) contentView.findViewById(R.id.pop_tv_0);
		tv0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopWindow.dismiss();  
			}
		});

		TextView tv1 = (TextView) contentView.findViewById(R.id.pop_tv_1);
		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopWindow.dismiss();  
			}
		});


		//显示PopupWindow  
		mPopWindow.showAsDropDown(llMore, -popWidth/3*2, 10); 
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if(mPopWindow != null) mPopWindow.dismiss();  
	}


	public void gotoSearchActivity(){
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		startActivityForResult(intent, 111);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(data != null){

			if(requestCode == 111 && resultCode == SearchActivity.SEARCH_RESULT_CODE)
			{
				String result = data.getStringExtra("Extra_result");
				Log.d("reslut",result);
//				ToastUtil.showToast(getActivity(), result);
				
				((CustomerCommonFragment) fragmentLists.get(viewPager.getCurrentItem())).clearCustomer();
				((CustomerCommonFragment) fragmentLists.get(viewPager.getCurrentItem())).loadDataByCondition(result);
				
			}
		}
	}
}




