package com.readboy.mentalcalculation.game.MyDialog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.readboy.mentalcalculation.R;
import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;
import com.readboy.mentalcalculation.other.CountDownThread;
import com.readboy.susuan.bean.TotalInfo;
import com.readboy.susuan.util.VolleyUtil;

import java.util.Timer;
import java.util.TimerTask;


public class Start_dialog {

    public boolean isLive = false;
    public boolean isScreenAndHomeKeydown = false;
    private int time_temp = 5;
    private Context context;
    private android.app.AlertDialog ad;
    private Button startbutton;
    private Button closebutton;
    private TextView numtext;
    private TextView timetext;
    private Window window;
    private int time = 5;

    private TimerTask mTimerTask;
    private Timer mTimer;
    private int mCount;
    private boolean mCountFlag;
    private Handler mHandler;
    private CountDownThread time_thread_control;
    private Thread timethread;

    private boolean is_close;
    private TotalInfo totalInfo = new TotalInfo();
    private VolleyUtil volleyUtil;
    private boolean is_checkNetwork;

    public interface ICloseDialogListener{
        public void onClose();
    }
    private ICloseDialogListener mCloseDialogListener = null;
    public void setCloseDialogListener(ICloseDialogListener closeDialogListener){
        mCloseDialogListener = closeDialogListener;
    }

    public Start_dialog(Context context) {
        this.context = context;
        ad=new android.app.AlertDialog.Builder(context, R.style.startdialog).create();
        ad.show();
        WindowManager.LayoutParams lp=ad.getWindow().getAttributes();
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        window = ad.getWindow();
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_start);
        ad.setCancelable(false);
        findView();
        volleyUtil = new VolleyUtil(context);
        startTime ();
        isLive = true;
    }

    private void findView() {

        numtext = (TextView)window.findViewById(R.id.startdialog_num);
        timetext = (TextView)window.findViewById(R.id.startdialog_time);
        startbutton = (Button)window.findViewById(R.id.startdialog_startbutton);
        /*绑定关闭按键的监听*/
        closebutton = (Button)window.findViewById(R.id.startdialog_closebutton);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ActivityCollector.finishAll();
                System.exit(0);
            }
        });
    }

    private void startTime(){
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mCountFlag){
                    // 1分钟处理一次
                    if ((mCount%60)==0){
                        mHandler.sendEmptyMessage(0x201);
                    }
                    mCount++;
                }
            }
        };
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0x201){
                    is_checkNetwork = isNetworkAvailable(context); //检查网络

                    if(is_checkNetwork == false){  //表示没有网络
                        Toast.makeText(context,"请确认你的网络是否正常连接，3秒后退出应用",Toast.LENGTH_LONG).show();
                        TimerTask tt = new TimerTask() {
                            @Override
                            public void run() {      //关闭dialog
                                ad.dismiss();
                                isLive = false;
                                ActivityCollector.finishAll();
                                System.exit(0);
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(tt, 3000);   //3秒后执行TimeTask的run方法
                    }
                    else if(is_checkNetwork == true) {   //有网络
                        volleyUtil.getLoginInfo(new VolleyUtil.ResultCodeLisenter() {
                            public void onResult(int code) {
                                if (code == 0) {   //访问数据库成功
                                    if (mCountFlag) {
                                        mCountFlag = false;
                                    /*开启5秒倒计时线程*/
                                        CountTimeThread();
                                    /*绑定开始游戏按键监听*/
                                        startbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ad.dismiss();
                                                isLive = false;
                                                is_close = true;
                                                mCloseDialogListener.onClose();
                                            }
                                        });
                                    /*获取并显示挑战人数*/
                                        volleyUtil.getTodayTotal(totalInfo, new VolleyUtil.ResultCodeLisenter() {
                                            @Override
                                            public void onResult(int code) {
                                                if (code == 0) {
                                                    numtext.setText(totalInfo.getTotal() + "");
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(context, "网络超时", Toast.LENGTH_LONG).show();
                                    dismiss();
                                    ActivityCollector.finishAll();
                                    System.exit(0);
                                }
                            }
                        });
                    }
                }
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, 0,1000);

        mCountFlag = true;
        mCount = 0;
    }

    private void stopTime(){
        if (mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
        if (mTimer!=null){
            mTimer.cancel();
            mTimer = null;
        }
        mCountFlag = false;
    }

    private void CountTimeThread() {
        Handler time_hander = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                time = b.getInt("time");
                timetext.setText(time+"");
                if(time==0&&(!is_close)){
                    ad.dismiss();
                    isLive = false;
                    mCloseDialogListener.onClose();
                }
            }
        };
        time_thread_control = new CountDownThread(time, time_hander);
        timethread = new Thread(time_thread_control);
        timethread.start();
    }

    private void dismiss() {
        stopTime ();
        if(time_thread_control!=null){
            time_thread_control.setTag(true);
            CountDownThread.destory = true;
        }
        volleyUtil.cancelAllRequest();      //退出则结束访问服务器
        ad.dismiss();
        isLive = false;
    }

    public void timecountON() {
        time_thread_control.setStartTime(time_temp);
        time_thread_control.setStop(false, 0);
    }
    public void timecountOFF() {
        time_temp = time;
        time_thread_control.setStop(true, time_temp);
    }
    /**
     * SCW
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
}
