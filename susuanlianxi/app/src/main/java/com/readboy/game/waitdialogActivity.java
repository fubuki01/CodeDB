package com.readboy.game;

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

import com.readboy.mentalcalculation.GetUserChapterName;
import com.readboy.susuan.bean.ChapterScore;
import com.readboy.susuan.bean.TotalInfo;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;
import com.readboy.mentalcalculation.R;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/9.
 */

public class waitdialogActivity extends Activity {

    finishDialog_w finish_dialog;
    private BroadcastReceiver mHomeKeyEventReceiver = null;  //设置个广播
    private UserInfos current = new UserInfos();
    private List<UserInfos> users = new ArrayList<UserInfos>();
    private TotalInfo totalInfo = new TotalInfo();
    private List<ChapterScore> chapterScores = new ArrayList<ChapterScore>();
    private VolleyUtil volleyUtil;
    private  boolean visitNextWork=true;  //当前网络状态
    public int current_grade;  //当前的分数
    public int current_office;  //当前的关卡数
    protected CountDownThread count_down_thread;
    private String charptername=null;
    private boolean repeatDown;
    TextView tvshowtext;
    ProgressBar waittimepb;
    private String charpternamecash;
    public static boolean notShowAgain=false;   //当在查看得分的回来的时候，不需要重复访问数据库信息
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitdialog);  //显示等待的Dialog
        visitNextWork=isNetworkAvailable(this);   //判断用户当前是否有网络状态
        volleyUtil=new VolleyUtil(this);
        Intent getIntent = getIntent();
        current_office = Integer.parseInt(getIntent.getStringExtra("guanka"));  //获取关卡
        current_grade = Integer.parseInt(getIntent.getStringExtra("current_grade")); //获取分数
        tvshowtext= (TextView) findViewById(R.id.tv_waitshowtext);
         waittimepb=(ProgressBar)findViewById(R.id.progressBar1);
         registerHomeKey(); //注册home键的监听
        charpternamecash = GetUserChapterName.getChapterName(this);
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
                    volleyUtil.getRankInfo(current_grade, charpternamecash, "", current, users, new VolleyUtil.ResultCodeLisenter() {
                        public void onResult(int code) {
                            if (code == 0) {
                                tvshowtext.setVisibility(View.INVISIBLE);  //控件消失
                                waittimepb.setVisibility(View.INVISIBLE);
                                if(!waitdialogActivity.this.isFinishing()) {
                                finish_dialog = new finishDialog_w(waitdialogActivity.this, current_office, current_grade, "qwe", current, users);
                                finish_dialog.setDestort(new finishDialog_w.Destort() {
                                    @Override
                                    public void onClick() {
                                        //             finish();
                                        waitdialogActivity.this.finish();
                                    }
                                });
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
