package com.hnran.perfmanagesys.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.hnran.perfmanagesys.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * 启动页面
 * @author tan
 * 2017-05-25
 *
 */
public class LaunchActivity extends BaseActivity {

	private Timer timer;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_launch);
		 
		 
		 timer = new Timer();
		 
		 timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
				startActivity(intent);
				LaunchActivity.this.finish();
			}
		}, 1200);
		  
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer != null){
			timer.cancel();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(timer != null){
				timer.cancel();
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
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
