package com.hnran.perfmanagesys.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter{  
	
	private List<Fragment> fragmentLists; 
	private List<String> titleLists;

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragmentLists = fragmentLists;
	}

	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentLists, List<String> titles) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragmentLists = fragmentLists;
		this.titleLists = titles;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentLists.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragmentLists.size();
	}  

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if(position < titleLists.size()){
			return titleLists.get(position);
		}else{
			return super.getPageTitle(position);
		}	
	}
	
}  
