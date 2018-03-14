package com.hnran.perfmanagesys.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment{
	protected String title;
	
	public BaseFragment(){
		this.title = "";
	}
	
	@SuppressLint("ValidFragment")
	public BaseFragment(String title){
		this.title = title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
}
