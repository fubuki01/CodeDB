package com.readboy.game.Grade_2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.GameActivity;
import com.readboy.game.Watcher;

public class Grade_2_top extends GameActivity implements Watcher{

	//protected Object Alock;
	
	Supply_Grade_2_top supply_project_thread;
	 
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
		GetProAndAns(type);
		initTimer();
		IsRight();
		intent_type="20"+type;
		rankingListen();
		
		
//		enter_dialog.dismiss();
		count_time=STARTNUM;
		count_down_thread.setStartTime(count_time);
		is_first_in_game=true;
    }
	
	protected void initTimer(){
		//Watched watched = new DrawView();  
		dv.add(this);
	}
	
	protected void GetProAndAns(int type){
		 Handler problem_hander=new Handler(){
			 public void handleMessage(Message msg) {
	             // process incoming messages here
				Log.i("information", "receive_problem");
				Bundle b = msg.getData();
				answer = b.getInt("answer");
				problem=b.getString("problem");
				content_of_game.setText(problem);
				 answer_below_line.setText("");
	         }
		};
		supply_project_thread=new Supply_Grade_2_top(problem_hander,Alock,type);
		new Thread(supply_project_thread).start();
	 }
	
	 
	protected void IsRight(){
	 }
	
	protected void onDestroy() {
		System.out.println("-----------onDestroy------");
        stopThread=true;
        count_down_thread.setTag(stopThread);
        supply_project_thread.setTag(stopThread);
        super.onDestroy();
	}
	
	
	//不同页面的传递rankingList的数据不相同,传递数据：1.储存数据的xml名字  2.当前的分数
	protected void rankingListen(){
//		 ranking.setOnClickListener(new OnClickListener() {
//				public void onClick(View arg0) {
//					 is_over=false;
//					 Intent intent = new Intent();
//		             intent.setClass(Grade_2_top.this, rankingList.class);
//
//		             intent.putExtra("game_type",intent_type);
//		             intent.putExtra("grade",student_grade);
//		             intent.putExtra("office", student_office);
//		             startActivity(intent);
//				}
//			});
	}
	
	
}


