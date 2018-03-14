package com.readboy.game.Grade_3;

import com.readboy.mentalcalculation.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.GameActivity;
import com.readboy.game.Watcher;

public class Grade_3_down extends GameActivity implements Watcher{

	//protected Object Alock;
	
	Supply_Grade_3_down supply_project_thread;
	 
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
		GetProAndAns(type);
		initTimer();
		IsRight();
		intent_type="31"+type;
//		rankingListen();
		count_time=STARTNUM;
		count_down_thread.setStartTime(count_time);
		is_first_in_game=true;
    }

	protected void initTimer(){
		//Watched watched = new DrawView();  
		dv.add(this);
	}
	
	
	protected void GetProAndAns(int type){
		 final Handler problem_hander=new Handler(){
			 public void handleMessage(Message msg) {
	             // process incoming messages here
				Log.i("information", "receive_problem");
				Bundle b = msg.getData();
				if((!b.getBoolean("is_float"))&&(!b.getBoolean("is_middle"))){
					answer = b.getInt("answer");
					Log.i("answer_this",answer+"");
					is_float=false;
					is_middle=false;
					problem=b.getString("problem");
					content_of_game.setText(problem);
					Log.e("000000001111", problem+"");
					answer_below_line.setText("");
					content_of_game2.setText("");
				}
				if ((!b.getBoolean("is_float"))&&(b.getBoolean("is_middle"))){
					answer = b.getInt("answer");
					Log.i("answer_this",answer+"");
					is_float=false;
					is_middle=true;
					problem=b.getString("problem");
					String[] r = problem.split("  ");
				    content_of_game.setText(r[0]);
					Log.e("00000000000", r[0]+"");
					answer_below_line.setText("");
					content_of_game2.setText(r[1]);
					Log.e("00000000000", r[1]+"");
				}
				if ((b.getBoolean("is_float"))&&(!b.getBoolean("is_middle"))){
					answer_float= b.getString("answer");
					Log.i("answer_this",answer_float);
					is_float=true;
					is_middle=false;
					problem=b.getString("problem");
					Log.e("33333333333", answer_float+"");
					Log.e("22222222222", problem+"");
//					below.invalidateDrawable(getResources().getDrawable(R.drawable.keep_two_number));
					below.setBackgroundResource(0);
					below.setImageResource(R.drawable.keep_two_number);
					content_of_game.setText(problem);
					answer_below_line.setText("");
				}
//				if ((is_float)&&(is_middle)){
//					answer_float= b.getString("answer");
//					Log.i("answer_this",answer_float);
//					is_float=true;
//					is_middle=true;
//					problem=b.getString("problem");
//					Log.e("88888888", answer_float+"");
//					Log.e("88888888", problem+"");
//					content_of_game.setText(problem);
//					answer_below_line.setText("");
//				}
	         }
		};
		supply_project_thread=new Supply_Grade_3_down(problem_hander,Alock,type);
		new Thread(supply_project_thread).start();
	 }
	
	 
	protected void IsRight(){
	 }
	
//	protected void onDestroy() {
//		System.out.println("-----------onDestroy------");
//        stopThread=true;
//        count_down_thread.setTag(stopThread);
//        supply_project_thread.setTag(stopThread);
//        super.onDestroy();
//	}
	
	
	//不同页面的传递rankingList的数据不相同,传递数据：1.储存数据的xml名字  2.当前的分数
	protected void rankingListen(){
//		 ranking.setOnClickListener(new OnClickListener() {
//				public void onClick(View arg0) {
//					 is_over=false;
//					 Intent intent = new Intent();
//		             intent.setClass(Grade_3_down.this, rankingList.class);
//
//		             intent.putExtra("game_type",intent_type);
//		             intent.putExtra("grade",student_grade);
//		             intent.putExtra("office", student_office);
//		             startActivity(intent);
//				}
//			});
	}
	
	
}


