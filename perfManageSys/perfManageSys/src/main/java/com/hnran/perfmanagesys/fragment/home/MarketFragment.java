package com.hnran.perfmanagesys.fragment.home;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.view.PagerSlidingTabStrip;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.SearchActivity;
import com.hnran.perfmanagesys.activity.market.MarketAddActivity;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.broadcastreceiver.UpdateViewReceiver;
import com.hnran.perfmanagesys.fragment.market.MarketCommonFragment;
import com.hnran.perfmanagesys.fragment.market.MarketCunkuanFragment;
import com.hnran.perfmanagesys.fragment.market.MarketEtcFragment;
import com.hnran.perfmanagesys.fragment.market.MarketPosFragment;
import com.hnran.perfmanagesys.fragment.market.MarketShoujiFragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MarketFragment extends BaseFragment{

	private final static int GOTO_ADD_MARKET_CODE = 2001;
	private final static int GOTO_SEARCH_CODE = 2002;

	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;
	private List<Fragment> fragmentLists;

	private PagerSlidingTabStrip mTabs;
	private List<String> titleLists;

	private ImageView ivAdd, ivSearch;

	//更新数据广播
	protected UpdateViewReceiver updateViewReceiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_market, null);

		initVariables();
		initViews(view);
		loadData();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		titleLists = new ArrayList<String>();

		fragmentLists = new ArrayList<Fragment>();

		fragmentLists.add(new MarketCunkuanFragment());
		//		fragmentLists.add(new MarketDaikuanFragment());
		fragmentLists.add(new MarketShoujiFragment());
		fragmentLists.add(new MarketEtcFragment());
		fragmentLists.add(new MarketPosFragment());

		titleLists.add("存款营销");
		//		titleLists.add("贷款营销");
		titleLists.add("手机银行营销");
		titleLists.add("ETC营销");
		titleLists.add("POS机营销");

		vpAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentLists, titleLists);


		updateViewReceiver = new UpdateViewReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
//				super.onReceive(context, intent);
				if(UpdateViewReceiver.UPDATE_VIEW.equals(intent.getAction())){
					int index = intent.getIntExtra("Extra_update_index", 0);
					updateView(index);
				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(UpdateViewReceiver.UPDATE_VIEW);
		getActivity().registerReceiver(updateViewReceiver, filter);

	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub
		TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
		tv_title.setText(title);

		ivAdd = (ImageView) view.findViewById(R.id.common_top_iv_add);
		ivAdd.setVisibility(View.VISIBLE);

		ivSearch = (ImageView) view.findViewById(R.id.common_top_iv_search);
		ivSearch.setVisibility(View.VISIBLE);

		ivAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoMarketAddActivity(MarketAddActivity.class);
			}
		});

		ivSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoSearchActivity();
			}
		});

		viewPager = (ViewPager) view.findViewById(R.id.market_viewpager);
		viewPager.setAdapter(vpAdapter);
		viewPager.setOffscreenPageLimit(4);

		mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.market_tabs);
		mTabs.setViewPager(viewPager);

	}


	protected void loadData() {
		// TODO Auto-generated method stub

	}


	public void gotoMarketAddActivity(Class<?> c){
		Intent intent = new Intent(getActivity(), c);
		intent.putExtra("Extra_type", MarketCommonFragment.OTHER);
		intent.putExtra("Extra_index", viewPager.getCurrentItem());
		startActivityForResult(intent, GOTO_ADD_MARKET_CODE);
	}

	public void gotoSearchActivity(){
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		startActivityForResult(intent, GOTO_SEARCH_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode == GOTO_SEARCH_CODE && resultCode == SearchActivity.SEARCH_RESULT_CODE)
		{
			if(data != null){
				String result = data.getStringExtra("Extra_result");
				Log.d("reslut",result);
//				ToastUtil.showToast(getActivity(), result);
				
				((MarketCommonFragment) fragmentLists.get(viewPager.getCurrentItem())).loadData(result);
				
			}

		}
//		else if(resultCode == MarketAddActivity.OPTION_MARKET){
//			//修改或添加营销申报后更新
//			//			ToastUtil.showToast(getActivity(), "添加成功");
//
//			int index = viewPager.getCurrentItem();
//
//			MarketCommonFragment mcf =  (MarketCommonFragment) fragmentLists.get(index);
//
//			switch(index){
//			case 0:
//				((MarketCunkuanFragment)mcf).updateInMarketHome();
//				break;
//				//			case 1:
//				//				((MarketDaikuanFragment)mcf).updateInMarketHome();
//				//				break;
//				//			case 2:
//				//				((MarketShoujiFragment)mcf).updateInMarketHome();
//				//				break;
//				//			case 3:
//				//				((MarketEtcFragment)mcf).updateInMarketHome();
//				//				break;
//			case 4:
//				//				((MarketPosFragment2)mcf).updateInMarketHome();
//				break;
//
//			}
//
//		}

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(updateViewReceiver);
	}

	private void updateView(int index){

		if(index != viewPager.getCurrentItem()){
			viewPager.setCurrentItem(index);
		}

		MarketCommonFragment mcf =  (MarketCommonFragment) fragmentLists.get(index);

		switch(index){
		case 0:
			((MarketCunkuanFragment)mcf).updateInMarketHome();
			break;
		case 1:
			((MarketShoujiFragment)mcf).updateInMarketHome();
			break;
		case 2:
			((MarketEtcFragment)mcf).updateInMarketHome();
			break;
		case 3:
			((MarketPosFragment)mcf).updateInMarketHome();
			break;

		}


	}

}
