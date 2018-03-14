package com.hnran.perfmanagesys.activity.warning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CopyOfWarningCommonActivity2 extends BaseActivity{

	private String mTitle;
	
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<? extends Map<String, ?>> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		mTitle = getIntent().getStringExtra("Extra_title");
		
		data = new ArrayList<Map<String, Object>>();
		
		simpleAdapter = new SimpleAdapter(this, null, 0, null, null);
		
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_warning_common);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText(mTitle+"");
		if(mTitle.length() > 10){
			tv.setTextSize(16);
		}

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		listView = (ListView) findViewById(R.id.warning_listview);
//		listView.setAdapter(simpleAdapter);

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub
		
	}
}
