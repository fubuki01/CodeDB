package com.hnran.perfmanagesys.fragment.customer;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.PerformanceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerRelationFragment extends Fragment{
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_customer_relation, null);
		
		initVariables();
		initViews(view);
		loadData();
		
		return view;
		
	}
	
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub	
		
		
	}


	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void gotoPerformanceActivity(int position){
	
	}

	
	
	
}
