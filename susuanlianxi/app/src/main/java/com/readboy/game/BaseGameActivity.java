package com.readboy.game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readboy.HandWrite.DrawView;
import com.readboy.mentalcalculation.R;
import com.readboy.susuan.util.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGameActivity extends Activity {

    private Thread mythread;
    public static BaseGameActivity thisactivity;

    protected boolean fdv=false,fdv2=false;
    protected DrawView dv2=null;
    protected boolean is_restartgame = false;
    final protected int STARTNUM = 11;
    finishmiddle_dialog finishmiddle;
    protected ImageView back_bt;
    protected TextView type_view;
    protected TextView time_of_game;
    protected TextView time_of_game2;
    protected TextView content_of_game;
    protected TextView content_of_game2;
    protected TextView answer_below_line;
    protected TextView answer_below_line2;
    protected ImageView below;
    protected TextView number_of_game;
    protected String problem;
    protected int s = 0;
    protected int answer;
    protected int j = 1;
    protected boolean is_quit = false;
    protected String intent_type;
    protected String answer_float;
    protected boolean is_float = false;
    protected boolean is_middle = false;//wyd
    protected DrawView dv;      //手写板
    protected TextView grade_of_game;
    protected int student_grade;
    protected int student_office = 1;    //游戏的关数
    protected Object Alock;
    protected int type;  //题目类型
    protected int count_time = 181;  //每题的时间
    public static boolean homeKeyDown = false;  //判断home键是否被按下
    public boolean enterfinishDwon;     //就是当进行页面切换的时候设置一个标识
    public  CountDownThread count_down_thread;
    protected boolean stopThread = false;
    protected ImageView draft;
    protected ImageView ranking;
    protected RelativeLayout liner;
    protected static final int COMPLETED = 0;
    costomAnimation costom_animation;
    protected ImageView mImageViewFilling = null;
    protected ImageView mImageViewScore = null;
    protected Boolean correct;
    protected int time_temp;
    protected int[] grade_all = new int[3];
    protected boolean is_first_in_game = false;
    protected boolean is_over = true;
    public int time; //倒计时当前的时间
    protected boolean isUpNotTouchUpdateNotify = false;
    boolean isScreenOn = true;//判断屏幕亮暗
    public PlaySound playSound;
    int successOrFail = -1;
    protected boolean enterDraftJudge=false;
    protected int durationTime;
    protected int stayAnimationSuccessTime;
    protected int stayAnimationFailTime;
    protected int stayAnimationUpScore;
    protected List<Integer> resourceIdListSuccess = new ArrayList<Integer>();//存放成功图片id的list
    protected List<Integer> resourceIdListFail = new ArrayList<Integer>();//存放失败图片id的list
    protected List<Integer> resourceUpScores = new ArrayList<Integer>();//存放分数图片id的list
    final protected int[] drawOfGame = {10, 20, 30, 400, 500, 600, 700, 800, 900, 1000};
    protected BroadcastReceiver mHomeKeyEventReceiver = null;  //设置个广播
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    protected VolleyUtil volleyUtil;
    /**
     * 接受worthread反馈的数据，用来判断答案
     */
    private Handler handler;
    private void sethandler(boolean isdoublescreen) {
        if(isdoublescreen)
        {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (msg.what == COMPLETED) {
                        int count = dv.points.size();
                        int count2 = dv2.points.size();
                        if(dv.timer!=null)
                            dv.timeCancel();
                        if(dv2.timer!=null)
                            dv2.timeCancel();
                        short[] prolong = new short[count];

                        for (int i = 0; i < prolong.length; i++) {
                            prolong[i] = dv.points.get(i);
                        }

                        short[] prolong2 = new short[count2];
                        for (int j = 0; j < prolong2.length; j++) {
                            prolong2[j] = dv2.points.get(j);
                        }
                        try {
                            if(count>0&&count2>0&&(time!=0))
                            {
                                judgeHandPut("8", count/2 -1, count2/2 -1, prolong, prolong2);
                                dv.reSetPath();
                                dv2.reSetPath();
                                dv.clearScreen();
                                dv2.clearScreen();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
        else
        {
            /**
             * 接受worthread反馈的数据，用来判断答案
             */
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == COMPLETED) {
                        int count = dv.points.size();
                        short[] prolong = new short[count];
                        for (int i = 0; i < prolong.length; i++) {
                            prolong[i] = dv.points.get(i);
                        }
                        try {
                            if (time!=0){
                                judgeHandPut("8", count / 2 - 1, prolong);
                                dv.reSetPath();
                                dv.clearScreen();
                                if (dv.timer != null)
                                    dv.timeCancel();
                            }
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        IsRecoverFromLastActivity(savedInstanceState);
        volleyUtil = new VolleyUtil(this);
        registerHomeKey();
        sethandler(isdoublescreen());
        thisactivity=this;
    }

    abstract protected boolean isdoublescreen();

    /**
     * 异常情况下，activity被销毁的时候调用。
     */
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("HELLO", "HELLO：当Activity被销毁的时候，不是用户主动按back销毁，例如按了home键");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("is_over", false); //这里保存一个用户名
        Log.i("mentalcalculation", "game_activity_onSaveInstanceState");
    }

    protected void onPause() {
        super.onPause();
			/*将结果反馈给上一个界面*/
        Intent intent = new Intent();
        intent.putExtra("Type", type);
        setResult(RESULT_OK, intent);

	        /*将所得的成绩保存到SD卡中*/
        readFile();
        updateGradeContent();
        updateGrade(grade_all[0], grade_all[1]);

        //当进入设置的时间将时间进行保存
        if(enterfinishDwon==false) {
            if(enterDraftJudge==false){
                homeKeyDown = true;
                time_temp = time;              //将计时器的时间暂停不走动
                count_down_thread.setStop(true, time_temp);

            }
        }
        else{
            homeKeyDown=false;
        }
    }
    public void onResume() {
        super.onResume();
        enterDraftJudge=false;
        Log.e("onresume",time+"!!!!!!");
        if (homeKeyDown &&time==0) {  //表示是从home键返回的，并且做题时间到
            homeKeyDown = false;
            enterfinishDwon=true;
            showFinishDialog();  //则弹出dialog
            grade_of_game.setText("得分：0分");
            student_grade = 0;
        } else {
            if(homeKeyDown){        //表示之前Home键过，但是是在时间还没到的时候,则现在恢复时间
                count_down_thread.setStartTime(time);
                count_down_thread.setStop(false, 0);
                new Thread(new Runnable() {    //提前写了答案后，Home键退出又返回进行判断写的答案
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Message msg = new Message();
                            msg.what = COMPLETED;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            homeKeyDown=false;  //表示在home键切换后进来而时间还没到0
            if (ActivityCollector.isdestroy)   //退出当前的activity
            {
                this.finish();
                ActivityCollector.isdestroy=false;
                return;
            }
            if (is_first_in_game == false) {
	    	/*从草稿本和排行榜中返回执行的函数，将时间设置为之前保存的值*/
            } else {
	    	/*第一次进入游戏中，设置时间*/
                count_down_thread.setStartTime(STARTNUM);
                is_first_in_game = false;

                if (is_restartgame == true) {   //刷新当前的activity，也就是重新做题
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    overridePendingTransition(0, 0);
                    mythread.interrupt();
                    ActivityCollector.alreadyActivity.clear();
                    finish();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    enterfinishDwon=true;
                }
            }
        }
        //当isUpNotTouchUpdateNotify为true说明
        if (isUpNotTouchUpdateNotify == true) {
            isUpNotTouchUpdateNotify = false;
        }

    }

    protected void onDestroy() {
        //更新文件中数据
        is_first_in_game = true;
        is_quit = true;
        stopThread = true;
        ActivityCollector.isdestroy = false;
        homeKeyDown=false;
        count_down_thread.setTag(stopThread);
        count_down_thread.setStartTime(1);
        if(mHomeKeyEventReceiver!=null)
        {
            unregisterReceiver(mHomeKeyEventReceiver);
            mHomeKeyEventReceiver = null;
        }
        super.onDestroy();
    }
    /**
     * 判断是否需要从上一个状态恢复
     *
     * @param savedInstanceState
     */
    protected void IsRecoverFromLastActivity(Bundle savedInstanceState) {
        //状态 判断  ,从之前状态获得是否需要恢复
        boolean lastActivityIsNotExist = true;
        if (savedInstanceState != null) {
            Log.d("mentalcalculation", "HELLO：应用进程被回收后，状态恢复");
            lastActivityIsNotExist = savedInstanceState.getBoolean("is_over");
        }
    }
    /**
     * 获取到当前进程是否在后台运行
     *
     * @param context
     * @return true代表在后台运行，false表示前台运行
     */
    public boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        for (RunningTaskInfo amTask : tasks) {
            ComponentName topActivity = amTask.topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * 初始化界面
     */
    protected void initView() {
        //从上一个界面中获取activity的标题
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        type = intent.getIntExtra("type", -1);
        type_view.setText(content);
        student_grade = 0;

        playSound = new PlaySound(this);
        Alock = new Object();
        backToStartView();
        draftButton();
        addResource();
    }
    /**
     * 获取界面
     */
    abstract protected void findView();
    /**
     * 返回键响应事件
     */
    protected void backToStartView() {
        back_bt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog();
            }
        });
    }

    public void showDialog() {

        finishmiddle = new finishmiddle_dialog(this);
        finishmiddle.setClickQuitEvent(new OnClickListener() {

            public void onClick(View arg0) {
                stopThread = true;
                count_down_thread.setTag(stopThread);
                finishmiddle.dismiss();
                BaseGameActivity.this.finish();
                System.exit(0);
            }
        });

        finishmiddle.setClickContinueEvent(new OnClickListener() {
            public void onClick(View arg0) {
                finishmiddle.dismiss();
            }
        });
    }


    /**
     * 跳转到草稿本界面
     */
    protected void draftButton() {
        draft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                enterDraftJudge=true;
                Intent intent = new Intent();
//                intent.putExtra("enterDratime",time+"");
//                Log.e("onresume","!!!!!!!!!!"+"$$$$333333");
                Log.e("onresume",time+"$$$$333333");
                intent.setClass(BaseGameActivity.this, draftActivity_w.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 时间计时线程
     */
    @SuppressLint("HandlerLeak")
    protected void CountTimeThread() {
        Handler time_hander = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();//获取传递过来的Message中的数据集合 wyd
                time = b.getInt("time");//从数据集合中获取key为＂time＂的值，这个值是整型 wyd
                if (time <= 10) {
                    time_of_game.setText("时间：");
                    time_of_game2.setText(time + "s" + "");
                } else {
                    time_of_game.setText("时间：" + time + "s" + "");
                }
                if (time == 0) {
                    if(homeKeyDown){
                    }
                    else {
                        if (is_quit == false)
                            showFinishDialog();
                        grade_of_game.setText("得分：0分");
                        student_grade = 0;
                    }
                }
            }
        };
        count_down_thread = new CountDownThread(STARTNUM, time_hander);
        mythread = new Thread(count_down_thread);
        mythread.start();
    }


    /**
     * 从 “出题”（supply_project_thread）线程中获取题目
     *
     * @param type
     */
    protected abstract void GetProAndAns(int type);


    /**
     * 判断是否要进行答案的判断，若在屏幕暗的时候，不判断，亮则判断
     */
    @SuppressWarnings("deprecation")
    public void updateNotify(DrawView jdv) {
        if(dv2!=null)
        {
            if(jdv==dv)
                fdv=true;
            else if(jdv==dv2)
                fdv2=true;
            if(!(fdv&&fdv2))
                return;
            else
            {
                fdv=false;
                fdv2=false;
            }
        }
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        boolean isInBackground = isApplicationBroughtToBackground(this);
        if (isScreenOn == true && isInBackground == false) {
            WorkThread thread = null;//判断答案的线程
            thread = new WorkThread();
            thread.start();
        } else {
            isUpNotTouchUpdateNotify = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//		client.connect();
//		AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//		AppIndex.AppIndexApi.end(client, getIndexApiAction());
//		client.disconnect();
    }

    /**
     * 定时接收后返回处理
     *
     * @author nuanbing
     */
    public class WorkThread extends Thread {
        @Override
        public void run() {
            //......处理比较耗时的操作

            //处理完成后给handler发送消息
            Message msg = new Message();
            msg.what = COMPLETED;
            handler.sendMessage(msg);
        }

    }
    /**
     * 判断该手写的字是否正确，正确播放正确动画，错误播放错误动画
     *
     * @param word        正确的字
     * @param ncout       写了多少点
     * @param polongArray 点阵
     * @return 返回分数， 第一个字返回 5，第二个返回4，其他返回3，没找到返回0
     * @throws InterruptedException
     */
    abstract public int judgeHandPut(String word, int ncout, short[] polongArray) throws InterruptedException;
    abstract public int judgeHandPut(String word, int ncout,int ncout2, short[] polongArray,short[] polongArray2) throws InterruptedException;
    /**
     * 选择播放动画
     *
     * @param choosing 表示播放动画类型
     */
    @SuppressWarnings("deprecation")
    public void Animal(boolean choosing) {
        if (choosing == true) {
            mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
            mImageViewFilling.setVisibility(View.VISIBLE);
            successOrFail = 1;
            durationTime = 1000;
            costom_animation.setAnimation(mImageViewFilling, resourceIdListSuccess);
            UpSuccessAndGrade();
        } else {
            mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
            mImageViewFilling.setVisibility(View.VISIBLE);
            successOrFail = 2;
            durationTime = 1000;
            s = 0;
            costom_animation.setAnimation(mImageViewFilling, resourceIdListFail);

        }

        costom_animation.start(false, durationTime);   //启动动画
        if (time != 0) {
            playSound.PlaySoundSuccessOrFail(successOrFail);
        }

        dv.clearScreen();
        if(dv2!=null)
            dv2.clearScreen();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                costom_animation.stop();
                playSound.StopSoundSuccessOrFail(successOrFail);
                mImageViewFilling.setVisibility(View.INVISIBLE);
                synchronized (Alock) {
                    Alock.notifyAll();
                }
            }
        }, stayAnimationSuccessTime);
    }

    protected void addResource() {
        costom_animation = new costomAnimation();
        resourceIdListSuccess.add(R.drawable.correct);
        stayAnimationSuccessTime = 1000;// 6 * 300
        resourceIdListFail.add(R.drawable.wrong);
        stayAnimationFailTime = 1000;//17 * 100
        resourceUpScores.add(R.drawable.score1);
        resourceUpScores.add(R.drawable.score2);
        resourceUpScores.add(R.drawable.score3);
        resourceUpScores.add(R.drawable.score4);
        resourceUpScores.add(R.drawable.score5);
        resourceUpScores.add(R.drawable.score6);
        resourceUpScores.add(R.drawable.score7);
        resourceUpScores.add(R.drawable.score8);
        resourceUpScores.add(R.drawable.score9);
        resourceUpScores.add(R.drawable.score10);
        stayAnimationUpScore = 10 * 100;
    }
    /**
     * 播放正确动画，并且更新分数和局数
     */
    protected void UpSuccessAndGrade() {
        s = s + 1;
        if (s > 10) {
            s = 10;
        }
        mImageViewScore = (ImageView) findViewById(R.id.imageview_animation_list_score);
        mImageViewScore.setVisibility(View.VISIBLE);
        mImageViewScore.setImageResource(resourceUpScores.get(s - 1));
        durationTime = 1000;
        costom_animation.start(false, durationTime);   //启动动画
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                costom_animation.stop();
                mImageViewScore.setVisibility(View.INVISIBLE);
            }
        }, stayAnimationUpScore);
        student_grade = student_grade + s;
        grade_of_game.setText("得分：" + student_grade + "分" + "");
        Log.i("information", "正确");
        for (int i = 0; i < 10; i++) {
            if (student_grade >= drawOfGame[i]) {
                continue;
            } else {
                student_office = i + 1;
                break;
            }
        }
    }
    /**
     * 设置保存的数据 排名，将当前的最佳数据写入到sd卡中
     *
     * @param fist_grade 第一名分数
     *                   second_grade 第二名分数
     *                   third_grade 第三名分数
     */
    public void updateGrade(int fist_grade, int office) {
        String name = "test";
        SharedPreferences mySharedPreferences = getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("first_grade" + intent_type, fist_grade);
        editor.putInt("office_grade" + intent_type, office);
        editor.commit();
    }
    /**
     * 从sd卡中获取保存排名的数据
     */
    public void readFile() {
        String name = "test";
        SharedPreferences sharedPreferences = getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        grade_all[0] = sharedPreferences.getInt("first_grade" + intent_type, 0);
        grade_all[1] = sharedPreferences.getInt("office_grade" + intent_type, 1);

    }


    /**
     * 更新名次
     */
    public void updateGradeContent() {
        if (student_grade >= grade_all[0]) {
            grade_all[0] = student_grade;
        }
        if (student_office >= grade_all[1]) {
            grade_all[1] = student_office;
        }
    }


    /**
     * 显示结束的界面，统计回合的得分　退出挑战wyd
     */
    public void showFinishDialog() {
        is_first_in_game = true;
        is_restartgame = true;
        is_quit = true;
        Intent intent = new Intent();   //跳转到等待页面
        intent.setClass(BaseGameActivity.this, waitdialogActivity.class);
        intent.putExtra("guanka", student_office + "");
        intent.putExtra("current_grade", student_grade + "");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }
    /*
      home键监听，则退出应用
     */
    protected void registerHomeKey() {
        if (mHomeKeyEventReceiver == null) {
            mHomeKeyEventReceiver = new BroadcastReceiver() {
                String SYSTEM_REASON = "reason";
                String SYSTEM_HOME_KEY = "homekey";
                String SYSTEM_HOME_KEY_LONG = "recentapps";
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                        String reason = intent.getStringExtra(SYSTEM_REASON);
                        if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                            homeKeyDown=true;
                            time_temp = time;              //将计时器的时间暂停不走动
                            count_down_thread.setStop(true, time_temp);
                        } else if (TextUtils.equals(reason,
                                SYSTEM_HOME_KEY_LONG)) {
                            homeKeyDown=true;
                            time_temp = time;              //将计时器的时间暂停不走动
                            count_down_thread.setStop(true, time_temp);
                            // 表示长按home键,显示最近使用的程序列表
                        }
                    }

                }
            };
            registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                    Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }

}
