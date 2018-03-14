package com.readboy.game.Grade_5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.readboy.game.FAO;
import com.readboy.game.GameActivityFenToXiao;
import com.readboy.game.Watcher;


public class Grade_5_down_fen_xiao extends GameActivityFenToXiao implements Watcher {

    //protected Object Alock;

    Supply_Grade_5_down supply_project_thread;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        correct = false;
        GetProAndAns(type);
        initTimer();
        IsRight();
        intent_type = "51" + type;
        count_time=STARTNUM;
        count_down_thread.setStartTime(count_time);
        is_first_in_game=true;
    }

    protected void initTimer() {
        dv.add(this);
        dv2.add(this);
    }

    protected void GetProAndAns(int type) {
        final Handler problem_hander = new Handler() {
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
                problem = b.getString("problem");
                String [] a = problem.split("\\/");
                content_of_game.setText(a[0]);
                content_of_game2.setText(a[1]);
                answer_below_line.setText("");
                answer_below_line2.setText("");
//                String wt = FAO.tostr(problem);
//                game_content.settext(wt, (is_float == true ? answer_float : Integer.toString(answer)).length(), false);
//                game_content.invalidate();
//				 game_content.setText();
//				 String a[] = problem.split(",");
//				content_of_game.setText(problem);
//				 v_up1.setText(a[0]);
//				 v_down1.setText(a[2]);
//				 fuhao1.setText(a[3]);
//				 v_up2.setText(a[4]);
//				 v_down1.setText(a[6]);
//				 fuhao2.setText(a[7]);

            }
        };
        supply_project_thread = new Supply_Grade_5_down(problem_hander, Alock, type);
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


