package com.hnran.perfmanagesys.activity.market;

import com.hnran.perfmanagesys.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MarketAddCunkuanActivity extends Activity{


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	protected void initVariables() {
		// TODO Auto-generated method stub



	}

	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_market_add);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("存款营销申报");

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		

		

		
	}

	protected void loadData() {
		// TODO Auto-generated method stub

	}
}
