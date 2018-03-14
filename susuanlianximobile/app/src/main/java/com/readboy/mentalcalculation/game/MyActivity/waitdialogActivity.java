package com.readboy.mentalcalculation.game.MyActivity;

/*
   等待跳转到成绩的Activity
 */


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.readboy.mentalcalculation.R;
import com.readboy.mentalcalculation.other.CurrentAndUsersUtils;
import com.readboy.susuan.bean.ChapterScore;
import com.readboy.susuan.bean.TotalInfo;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2017/3/9.
 */

public class waitdialogActivity extends Activity {

    private BroadcastReceiver mHomeKeyEventReceiver = null;  //设置个广播
    public static UserInfos current = new UserInfos();
    private List<UserInfos> users = new ArrayList<UserInfos>();
    private TotalInfo totalInfo = new TotalInfo();
    private List<ChapterScore> chapterScores = new ArrayList<ChapterScore>();
    private VolleyUtil volleyUtil;
    private  boolean visitNextWork=true;  //当前网络状态
    public int current_grade;  //当前的分数
    public int current_office;  //当前的关卡数
    private String charptername=null;
    private boolean repeatDown;
    TextView tvshowtext;
    ProgressBar waittimepb;
    public static String charpternamecash;
    public static boolean notShowAgain=false;   //当在查看得分的回来的时候，不需要重复访问数据库信息
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitdialog);  //显示等待的Dialog
        visitNextWork=isNetworkAvailable(this);   //判断用户当前是否有网络状态
        volleyUtil=new VolleyUtil(this);
        Intent intent = getIntent();
        current_office = intent.getExtras().getInt("guanka");  //获取题目数
        current_grade = intent.getExtras().getInt("current_grade"); //获取分数
        tvshowtext= (TextView) findViewById(R.id.tv_waitshowtext);
        waittimepb=(ProgressBar)findViewById(R.id.progressBar1);
        registerHomeKey(); //注册home键的监听
        charpternamecash = BaseGameActivity.chapterName;
       //charpternamecash = GetUserChapterName.getChapterName(this);
    }
    protected void onStart() {
        super.onStart();
        if(notShowAgain){
            waitdialogActivity.this.finish();
            notShowAgain=false;
        }
        else {
            if (repeatDown == false) {
                if (visitNextWork) {   //有网的状态
                    Log.e("1234",current_grade+"@@"+ waitdialogActivity.current.toString()+"$$"+users.toString());
                    volleyUtil.getRankInfo(current_grade, charpternamecash, "", current, users, new VolleyUtil.ResultCodeLisenter() {
                        public void onResult(int code) {
                            if (code == 0) {
                                Log.e("rank",current_grade+charpternamecash);
                                tvshowtext.setVisibility(View.INVISIBLE);  //控件消失
                                waittimepb.setVisibility(View.INVISIBLE);
                                if(!waitdialogActivity.this.isFinishing()) {
                                    //跳转到成绩的Activity
                                    final Intent intent=new Intent();
                                    intent.setClass(waitdialogActivity.this,FinishActivity.class);
                                    intent.putExtra("current_grade",current.getScore());   //当前分数
                                    intent.putExtra("current_office",current_office);   //当前题目数
                                    intent.putExtra("current_rank",judgeCurrentRank(current.getRank()));   //当前的排名
                                    intent.putExtra("history_rank",current.getMaxRank());  //最高的排名
                                    intent.putExtra("history_grade",current.getMaxScore());  //最高的分数
                                    CurrentAndUsersUtils.setCurrent(current);   //设置当前用户和所有用户的信息引用
                                    CurrentAndUsersUtils.setUsers(users);
                                    startActivity(intent);
                                    waitdialogActivity.this.finish();  //关闭当前的页面Activity
                                    TimerTask tt = new TimerTask() {
                                        @Override
                                        public void run() {

                                        }
                                    };
                                    Timer time = new Timer();
                                    time.schedule(tt,2000);
                                }
                            } else if (code == -1) {
                                Toast.makeText(waitdialogActivity.this, "网络异常，请重新开始", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {       //当前用户没有网络
                    Toast.makeText(waitdialogActivity.this, "当前没有可访问网络，程序将在5秒钟之后退出重新开始", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {       //延时5秒退出程序
                        public void run() {
                            ActivityCollector.isdestroy = true;
                            ActivityCollector.finishAll();
                            finish();
                        }
                    }, 5000);
                }
            }
        }
    }
    /*
    判断当前用户排名是否是在前十名
     */
    private int judgeCurrentRank(int rank) {
        int currentUid = current.getUid(); //拿到当前用户的UId
        for(UserInfos u : users){
            if(u.getUid() == currentUid){
                return u.getRank();
            }
        }
        return rank;
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(ActivityCollector.isdestroy)
            this.finish();
    }

    @Override
    protected void onDestroy() {
        if(mHomeKeyEventReceiver!=null)   //判断是否有广播，有则关闭
        {
            unregisterReceiver(mHomeKeyEventReceiver);
            mHomeKeyEventReceiver = null;
        }
        volleyUtil.cancelAllRequest();
        super.onDestroy();
    }

    /*
          home键监听，则退出应用
         */
    private void registerHomeKey() {
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
                            repeatDown=true;
//                            finish();
                        } else if (TextUtils.equals(reason,
                                SYSTEM_HOME_KEY_LONG)) {
                            // 表示长按home键,显示最近使用的程序列表
                        }
                    }

                }
            };
            registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                    Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        repeatDown=true;
    }
}

