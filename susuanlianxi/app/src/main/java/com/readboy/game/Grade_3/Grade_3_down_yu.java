package com.readboy.game.Grade_3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.GameActivityToG2DYu;
import com.readboy.game.Grade_3.Supply_Grade_3_down;
import com.readboy.game.Watcher;

public class Grade_3_down_yu extends GameActivityToG2DYu implements Watcher {

    //protected Object Alock;

    Supply_Grade_3_down supply_project_thread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
        GetProAndAns(type);
        initTimer();
        IsRight();
        intent_type = "31" + type;
        count_time = STARTNUM;
        count_down_thread.setStartTime(count_time);
        is_first_in_game = true;
    }

    protected void initTimer() {
        //Watched watched = new DrawView();
        dv.add(this);
        dv2.add(this);
    }

    protected void GetProAndAns(int type) {
        Handler problem_hander = new Handler() {
            public void handleMessage(Message msg) {
                // process incoming messages here
                Log.i("information", "receive_problem");
                Bundle b = msg.getData();
                answer = b.getInt("answer");
                problem = b.getString("problem");
                content_of_game.setText(problem);
                answer_below_line.setText("");
                answer_below_line2.setText("");
            }
        };
        supply_project_thread = new Supply_Grade_3_down(problem_hander, Alock, type);
        new Thread(supply_project_thread).start();
    }


    protected void IsRight() {
    }

    protected void onDestroy() {
        System.out.println("-----------onDestroy------");
        stopThread = true;
        count_down_thread.setTag(stopThread);
        supply_project_thread.setTag(stopThread);
        super.onDestroy();
    }


    //不同页面的传递rankingList的数据不相同,传递数据：1.储存数据的xml名字  2.当前的分数


}


