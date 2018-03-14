package com.hnran.perfmanagesys.activity;

import com.hnran.perfmanagesys.R;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * 登录页面
 * @author tan
 * 2017-06-28
 *
 */
public class ReLoginActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		PMSApplication.isLogin = false;
		
		popupDialog();
	
	}

	

	private AlertDialog mAlertDialog = null;
	private void popupDialog() {

		mAlertDialog = new AlertDialog.Builder(this).create();
		mAlertDialog.setCanceledOnTouchOutside(false);

		mAlertDialog.show();
		mAlertDialog.getWindow().setContentView(R.layout.layotu_pop_dialog);
		mAlertDialog.getWindow().findViewById(R.id.alarm_btn_left)
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//退出应用
				if (mAlertDialog != null) {
					mAlertDialog.dismiss();
					mAlertDialog = null;
				}
				
//				android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
//				System.exit(0);
				
				Intent intent = new Intent(ReLoginActivity.this, ExitActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity(intent);
			}
		});

		mAlertDialog.getWindow().findViewById(R.id.alarm_btn_right)
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//重新登录
				
				if (mAlertDialog != null) {
					mAlertDialog.dismiss();
					mAlertDialog = null;
				}
				
				Intent intent = new Intent(ReLoginActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
				startActivity(intent);
				
			}
		});

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
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// 按下键盘上返回按钮
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			return true;
//		} else {
//			return super.onKeyDown(keyCode, event);
//		}
//	}

}
