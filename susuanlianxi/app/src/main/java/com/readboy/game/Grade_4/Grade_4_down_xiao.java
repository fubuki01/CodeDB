package com.readboy.game.Grade_4
        ;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.readboy.game.GameActivity;
import com.readboy.game.GameActivityXiao;
import com.readboy.game.Watcher;

import static com.readboy.mentalcalculation.R.id.answer_below_line;
import static com.readboy.mentalcalculation.R.id.answer_below_line2;

public class Grade_4_down_xiao extends GameActivityXiao implements Watcher {

    //protected Object Alock;

    Supply_Grade_4_down supply_project_thread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
        GetProAndAns(type);
        initTimer();
        IsRight();
        intent_type = "41" + type;
//		rankingListen();
        count_time=STARTNUM;
        count_down_thread.setStartTime(count_time);
        is_first_in_game=true;
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
                if (!b.getBoolean("is_float")) {
                    answer = b.getInt("answer");
                    Log.e("333333333333", answer + "");
                    is_float = false;
                } else {
                    answer_float = b.getString("answer");
                    Log.e("44444444444", answer_float);
                    is_float = true;
                }
                problem = b.getString("problem");
                content_of_game.setText(problem);
                answer_below_line.setText("");
                answer_below_line2.setText("");
            }
        };
        supply_project_thread = new Supply_Grade_4_down(problem_hander, Alock, type);
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




}


