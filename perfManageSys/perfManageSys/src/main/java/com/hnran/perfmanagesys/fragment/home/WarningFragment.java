package com.hnran.perfmanagesys.fragment.home;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.warning.WarningCommonActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WarningFragment extends BaseFragment{
		
	public ListView listview;
	private ArrayAdapter<String> adapter;
	
	private List<String> contents;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_warning, null);
		
		initVariables();
		initViews(view);
		loadData();
		
		return view;
		
	}
	
	protected void initVariables() {
		// TODO Auto-generated method stub
		
		contents = new ArrayList<String>();
		contents.add("定期存款到期明细");
		contents.add("管户贷款到期提醒明细");
		contents.add("管户新增不良贷款明细");
		contents.add("管户贷款欠息明细");
		contents.add("管户历年发放当年到期贷款收回率");
		contents.add("管户当年发放当年到期贷款收回率");
		contents.add("逾期贷款管户明细");
		
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_warning_main, R.id.warning_tv_text, contents);
		
	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub
		TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
		tv_title.setText(title);
		
		listview = (ListView) view.findViewById(R.id.warning_lv_customer);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("cick", position+"");
				gotoWarningActivity(position);
			}
		});
		
	}


	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void gotoWarningActivity(int position){
		
		Intent intent = new Intent(getActivity(), WarningCommonActivity.class);
		intent.putExtra("Extra_title", contents.get(position));
		intent.putExtra("Extra_type", position);
		startActivity(intent);
		
	}
	
	
}
