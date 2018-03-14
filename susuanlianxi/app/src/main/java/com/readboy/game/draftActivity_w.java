package com.readboy.game;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;


import com.readboy.HandWrite.DraftDrawable;
import com.readboy.HandWrite.PaintView;
import com.readboy.mentalcalculation.R;



public class draftActivity_w extends Activity{
	ImageButton draft_cancle;
	ImageButton draft_redo;
	ImageButton draft_clear;
	ImageButton close;
	private String enterTime;
	DraftDrawable drawView;
	PaintView drawView2;
	protected BroadcastReceiver mHomeKeyEventReceiver = null;  //设置个广播
	BaseGameActivity baseGameActivity;
//	String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/www";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.draft_layout_w);
		ActivityCollector.addActivity(this);
		ActivityCollector.putActivity(this);
		findView();
		StartButtonView();
		baseGameActivity=BaseGameActivity.thisactivity;
		registerHomeKey();

	}

	@Override
	public void onResume() {
		super.onResume();
		if(baseGameActivity.homeKeyDown) {
			baseGameActivity.count_down_thread.setStartTime(baseGameActivity.time);
			baseGameActivity.count_down_thread.setStop(false, 0);
			baseGameActivity.homeKeyDown=false;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		baseGameActivity.is_restartgame=false;   //防止点了草稿纸，重新开始游戏
	}

	private void findView()  {
		draft_cancle = (ImageButton) findViewById(R.id.draft_cancle);
		draft_redo = (ImageButton) findViewById(R.id.draft_redo);
		draft_clear = (ImageButton) findViewById(R.id.draft_clear);
		close = (ImageButton) findViewById(R.id.close);
		drawView2 = (PaintView) findViewById(R.id.drawView1);
	}


	private void StartButtonView() {
		draft_cancle.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				try {
					drawView2.undo();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		draft_redo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					drawView2.redo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		draft_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				drawView2.removeAllPaint();
			}
		});

		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				drawView2.removeAllPaint();
				ActivityCollector.removeActivity(draftActivity_w.this);
				draftActivity_w.this.finish();
			}
		});
	}
	/*
      home键监听，则退出应用
     */
	protected void registerHomeKey() {
		//baseGameActivity.registerHomeKey();
	}

	@Override
	protected void onPause() {
		super.onPause();
		baseGameActivity.homeKeyDown=true;
		baseGameActivity.time_temp = baseGameActivity.time;              //将计时器的时间暂停不走动
		baseGameActivity.count_down_thread.setStop(true, baseGameActivity.time_temp);
		Log.e("onresume",baseGameActivity.time+"pause");
	}
}
