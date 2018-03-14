package com.readboy.game.Grade_6;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.FAO;
import com.readboy.game.GameActivityFen;
import com.readboy.game.Watcher;

public class Grade_6_top_fen extends GameActivityFen implements Watcher{

	//protected Object Alock;
	
	Supply_Grade_6_top supply_project_thread;
	 
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
		GetProAndAns(type);
		initTimer();
		IsRight();
		intent_type="60"+type;
//		rankingListen();
		count_time=STARTNUM;
		count_down_thread.setStartTime(count_time);
		is_first_in_game=true;
    }
	
	protected void initTimer(){
		//Watched watched = new DrawView();  
		dv.add(this);
		dv2.add(this);
	}
	
	protected void GetProAndAns(int type){
		 final Handler problem_hander=new Handler(){
			 public void handleMessage(Message msg) {
	             // process incoming messages here
				Log.i("information", "receive_problem");
				Bundle b = msg.getData();
				if(!b.getBoolean("is_float")){
					answer = b.getInt("answer");
					Log.i("answer_this",answer+"");
					is_float=false;
				}
				else{
					answer_float= b.getString("answer");
					Log.i("answer_this",answer_float);
					is_float=true;
				}
				problem=b.getString("problem");
				 String df = FAO.tostr(problem);
				 game_content.settext(df,2,true);
				 game_content.invalidate();
//				content_of_game.setText(problem);
	         }
		};
		supply_project_thread=new Supply_Grade_6_top(problem_hander,Alock,type);
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
//		             intent.setClass(Grade_6_top_fen.this, rankingList.class);
//
//		             intent.putExtra("game_type",intent_type);
//		             intent.putExtra("grade",student_grade);
//		             intent.putExtra("office", student_office);
//		             startActivity(intent);
//				}
//			});
	}
	
	
}


