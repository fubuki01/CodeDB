package com.readboy.game.Grade_5;

import com.readboy.game.GameActivityXiao;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.readboy.game.Watcher;
import com.readboy.mentalcalculation.R;

import static com.readboy.mentalcalculation.R.id.below;
import static com.readboy.mentalcalculation.R.id.below2;

public class Grade_5_top extends GameActivityXiao implements Watcher {

    //protected Object Alock;

    Supply_Grade_5_top supply_project_thread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
        GetProAndAns(type);
        initTimer();
        IsRight();
        intent_type = "50" + type;
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
                    Log.i("answer_this", answer + "");
                    is_float = false;
                } else {
                    answer_float = b.getString("answer");
                    Log.i("answer_this", answer_float);
                    is_float = true;
                }
                if (b.getBoolean("is_keep")){
                    below2.setBackgroundResource(0);
                    below2.setImageResource(R.drawable.decimalpsoitiontwonumber);
                }
                problem = b.getString("problem");
                content_of_game.setText(problem);
                answer_below_line.setText("");
                answer_below_line2.setText("");
            }
        };
        supply_project_thread = new Supply_Grade_5_top(problem_hander, Alock, type);
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


