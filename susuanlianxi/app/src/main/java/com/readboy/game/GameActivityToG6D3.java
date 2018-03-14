package com.readboy.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.readboy.HandWrite.DraftView;
import com.readboy.HandWrite.DrawView;
import com.readboy.HandWrite.HandWrite;
import com.readboy.mentalcalculation.R;

import java.util.ArrayList;
import java.util.List;

public  abstract class GameActivityToG6D3 extends BaseGameActivity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_view);
		findView();//获取游戏界面
		initView();
		CountTimeThread();
		ActivityCollector.putActivity(this);
	}

	@Override
	protected boolean isdoublescreen() {
		return false;
	}
	/**
	 * 获取界面
	 */
	protected void findView(){
		back_bt=(ImageView)findViewById(R.id.back_bt);
		type_view=(TextView) findViewById(R.id.type_view);
		time_of_game=(TextView) findViewById(R.id.time_of_game);
		time_of_game2 = (TextView) findViewById(R.id.time_of_game2);
		content_of_game=(TextView) findViewById(R.id.content_of_game);
		content_of_game2 = (TextView) findViewById(R.id.content_of_game2);
		answer_below_line = (TextView) findViewById(R.id.answer_below_line);
		answer_below_line2 = (TextView) findViewById(R.id.answer_below_line2);
		number_of_game=(TextView) findViewById(R.id.number_of_game);
		//answer_of_game=(EditText) findViewById(R.id.answer_of_game);
		grade_of_game=(TextView) findViewById(R.id.grade_of_game);
		draft=(ImageView) findViewById(R.id.draft);
//		ranking=(ImageView) findViewById(R.id.ranking);
		//draft_view=(DraftView) findViewById(R.id.draft_layout);
		liner=(RelativeLayout) findViewById(R.id.relative);
		dv = (DrawView) findViewById(R.id.draw);
		dv.paint.setColor(Color.argb(255,0,149,255));//设置画笔颜色 wyd
		dv.paint.setStrokeWidth(8);//设置空心线宽 即设置线的宽度 wyd

		time_of_game.setText("时间："+(STARTNUM-1)+"s"+"");
	}

	/**
	 * 判断该手写的字是否正确，正确播放正确动画，错误播放错误动画
	 * @param word	正确的字
	 * @param ncout	写了多少点
	 * @param polongArray	点阵
	 * @return	返回分数， 第一个字返回 5，第二个返回4，其他返回3，没找到返回0
	 * @throws InterruptedException
	 */
	@Override
	public int judgeHandPut(String word, int ncout,int ncout2, short[] polongArray,short[] polongArray2) throws InterruptedException {
		return 0;
	}
	@Override
	public  int judgeHandPut(String word, int ncout, short[] polongArray) throws InterruptedException
	{
		short[] rgResultBuff = new short[1024];
		char[]  tempArray;
		int score = 0;
		int err = HandWrite.initHandWrite(Environment.getExternalStorageDirectory()+"/Aiwrite.dat");
		HandWrite.setReconizeEngilish();
		HandWrite.iHCR_SetParam(HandWrite.iHCR_PARAM.iHCR_PARAM_RECOGMANNER.ordinal(), HandWrite.iHCR_RECOGNITION_SENT_LINE);
		if(err == 0){
			return 0;
		}else{
			if(ncout<=256){
				err = HandWrite.reconizePoint(ncout, polongArray,rgResultBuff);
			}else {
				HandWrite.exitHandWriteInit();
				return 0;
			}
			if(err <= 0){
				HandWrite.exitHandWriteInit();
				return 0;
			}else{
				tempArray=new char[err];
				for(int i=0;i<err;i++){
					if((int)rgResultBuff[i] == 0){
						tempArray[i]= '-';
					}else{
						tempArray[i]=(char)rgResultBuff[i];
					}
				}
				String str=String.valueOf(tempArray);
				String[] result = str.split("-");//分割字符串
				boolean f = false;
				try
				{
					Integer.valueOf(result[0]);
				}
				catch(NumberFormatException e1)
				{
					try
					{
						Double.valueOf(result[0]);
					}
					catch(NumberFormatException e2)
					{
						f = true;
					}
				}
				if(f)
				{
					HandWrite.exitHandWriteInit();
					findViewById(R.id.draw).invalidate();
					return -1;
				}

				String answerStr;
				if(is_float==false)
					answerStr = String.valueOf(answer);
				else{
					answerStr = answer_float;
				}

				for (int i = 0; i < result.length; i++)
				{
					if(answerStr.equalsIgnoreCase(result[i])){
						correct = true;
						answer_below_line.setText(answerStr);
        				 /*播放正确动画*/
						Animal(true);
						number_of_game.setText("题数：第"+(++j)+"题"+"");
						break;
					}

				}
				if(!correct){
					if (result[0].length()>5) {
						Toast.makeText(this, "答案长度超过限制", Toast.LENGTH_SHORT).show();
						HandWrite.exitHandWriteInit();
						findViewById(R.id.draw).invalidate();
						return -1;
					}else{
						answer_below_line.setText(result[0]);
						Animal(false);
						number_of_game.setText("题数：第"+(++j)+"题"+"");
					}

				}

			}
			correct = false;
			HandWrite.exitHandWriteInit();
		}
		return score;
	}

}
