package com.readboy.game.Grade_6;

import com.readboy.game.GameActivityToG6D;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.readboy.game.Watcher;

public class Grade_6_down extends GameActivityToG6D implements Watcher{

    //protected Object Alock;

    Supply_Grade_6_down supply_project_thread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
        GetProAndAns(type);
        initTimer();
        IsRight();
        intent_type="61"+type;
//        rankingListen();
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
                content_of_game.setText(problem);
                answer_below_line.setText("");
//                if(Supply_Grade_6_down.pointChangeBaifenshu==true){  //是小数化百分数(则在后面加个%)
                    content_of_game2.setText("%");
//               }
//                else{
//                    content_of_game2.setText("");
//                }
            }
        };
        supply_project_thread=new Supply_Grade_6_down(problem_hander,Alock,type);
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


}


