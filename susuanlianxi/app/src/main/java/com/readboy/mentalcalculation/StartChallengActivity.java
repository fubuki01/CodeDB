package com.readboy.mentalcalculation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.readboy.game.ActivityCollector;
import com.readboy.game.CountDownThread;
import com.readboy.game.GameActivity;
import com.readboy.game.Grade_1.Grade_1_down;
import com.readboy.game.Grade_1.Grade_1_top;
import com.readboy.game.Grade_2.Grade_2_down;
import com.readboy.game.Grade_2.Grade_2_down_yu;
import com.readboy.game.Grade_2.Grade_2_top;
import com.readboy.game.Grade_3.Grade_3_down;
import com.readboy.game.Grade_3.Grade_3_down_yu;
import com.readboy.game.Grade_3.Grade_3_top;
import com.readboy.game.Grade_3.Grade_3_top_fen;
import com.readboy.game.Grade_4.Grade_4_down;
import com.readboy.game.Grade_4.Grade_4_down_xiao;
import com.readboy.game.Grade_4.Grade_4_top;
import com.readboy.game.Grade_5.Grade_5_down;
import com.readboy.game.Grade_5.Grade_5_down_fen;
import com.readboy.game.Grade_5.Grade_5_down_fen_xiao;
import com.readboy.game.Grade_5.Grade_5_top;
import com.readboy.game.Grade_6.Grade_6_down;
import com.readboy.game.Grade_6.Grade_6_down3;
import com.readboy.game.Grade_6.Grade_6_down_fen;
import com.readboy.game.Grade_6.Grade_6_top;
import com.readboy.game.Grade_6.Grade_6_top_fen;
import com.readboy.game.costomAnimation;
import com.readboy.susuan.bean.TotalInfo;
import com.readboy.susuan.util.VolleyUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.readboy.game.BaseGameActivity.homeKeyDown;


/**
 * Created by Wyd on 2017/3/9.
 */

public class StartChallengActivity extends Activity {
    protected ImageView back_bt;
    protected ImageView time;
    protected ImageView begain;
    public int time1;
    protected int time_temp;
    protected int timetotal=5;
    protected int numbertotal =90;
    protected boolean is_quit = false;
    protected boolean is_pause = false;
    public String nowChapterName;
    costomAnimation costom_animation;
    protected CountDownThread count_down_thread;
    protected ImageView number_1;
    protected ImageView number_2;
    protected ImageView number_3;
    protected ImageView number_4;
    protected ImageView number_5;
    public String name;
    private myIndex index;
    private Handler handler;

    private TotalInfo totalInfo = new TotalInfo();
    private VolleyUtil volleyUtil;

    protected BroadcastReceiver mHomeKeyEventReceiver = null;  //设置个广播

    private List<Integer> resourceTime = new ArrayList<Integer>();//存放时间图片的id的list
    private List<Integer> resourceNumber = new ArrayList<Integer>();//存放人数图片id的list
    private TimerTask mTimerTask;
    private Timer mTimer;
    private int mCount;
    private boolean mCountFlag;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.comein);
        volleyUtil = new VolleyUtil(this);
        Bundle bundle=this.getIntent().getExtras();
        name = bundle.getString("chapterName");
        Log.e("111111111", "111111111111111");

        nowChapterName=GetUserChapterName.getChapterName(this);  //主要得到上一次的章节名，方便后处理两种情况

        GetUserChapterName.setChapterName(name,StartChallengActivity.this);

        if(name.equals(nowChapterName)) {   //如果当前的章节名和之前取的一样，则需要将StartActivity进行finish
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }
        else{      //当从学习眼又一次进入的时候（选择的是不同的章节的时候）
            if(ActivityCollector.alreadyActivity.size()!=0){
                for (Activity activity:ActivityCollector.alreadyActivity) {
                    activity.finish();
                }
                ActivityCollector.alreadyActivity.clear();
            }
        }
        findView();//获取游戏界面
        registerHomeKey();
        if(fistTimeInGame()==true){
            inputThread();
        }
        addResource();
        //initnumber();

        startTime ();
        index = new myIndex();

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
//                    volleyUtil.cancelAllRequest();
                    volleyUtil.getLoginInfo(new VolleyUtil.ResultCodeLisenter(){
                        public void onResult(int code) {
                            if(code==0){   //访问数据库成功
                                if (mCountFlag) {
                                    mCountFlag = false;
                                    CountTimeThread();
                                    StartButtonView();
                                }
                            }
                            else{
                                Toast.makeText(StartChallengActivity.this,"网络超时",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
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


    private void addResource() {
        costom_animation = new costomAnimation();
        resourceTime.add(R.drawable.time1);
        resourceTime.add(R.drawable.time2);
        resourceTime.add(R.drawable.time3);
        resourceTime.add(R.drawable.time4);
        resourceTime.add(R.drawable.time5);


        resourceNumber.add(R.drawable.number0);
        resourceNumber.add(R.drawable.number1);
        resourceNumber.add(R.drawable.number2);
        resourceNumber.add(R.drawable.number3);
        resourceNumber.add(R.drawable.number4);
        resourceNumber.add(R.drawable.number5);
        resourceNumber.add(R.drawable.number6);
        resourceNumber.add(R.drawable.number7);
        resourceNumber.add(R.drawable.number8);
        resourceNumber.add(R.drawable.number9);
    }

    private void CountTimeThread() {
        Handler time_hander = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();//获取传递过来的Message中的数据集合 wyd
                time1 = b.getInt("time");//从数据集合中获取key为＂time＂的值，这个值是整型 wyd
//                Log.e("6666666666666", time1+"");
                if (time1==5&&is_quit==false){
                    time.setVisibility(View.VISIBLE);
                    time.setImageResource(R.drawable.time5);
                }
                if (time1==4&&is_quit==false){
                    time.setVisibility(View.VISIBLE);
                    time.setImageResource(R.drawable.time4);
                }
                if (time1==3&&is_quit==false){
                    time.setVisibility(View.VISIBLE);
                    time.setImageResource(R.drawable.time3);
                }
                if (time1==2&&is_quit==false){
                    time.setVisibility(View.VISIBLE);
                    time.setImageResource(R.drawable.time2);
                }
                if (time1==1&&is_quit==false){
                    time.setVisibility(View.VISIBLE);
                    time.setImageResource(R.drawable.time1);
                }
                if (time1==0&&is_quit==false) {
                    is_quit=true;
                    Intent intent = new Intent();
                    intent.setClass(StartChallengActivity.this,index.getclass(name));
                    intent.putExtra("content",name);
                    intent.putExtra("type", index.gettype(name));
                    StartChallengActivity.this.startActivity(intent);
                    StartChallengActivity.this.finish();
                }
            }
        };
        count_down_thread = new CountDownThread(timetotal, time_hander);
        new Thread(count_down_thread).start();
    }

    protected void findView() {
        back_bt= (ImageView) findViewById(R.id.back_bt);
        time= (ImageView) findViewById(R.id.time);
        begain = (ImageView) findViewById(R.id.begain);
        number_1= (ImageView) findViewById(R.id.number_1);
        number_2= (ImageView) findViewById(R.id.number_2);
        number_3= (ImageView) findViewById(R.id.number_3);
        number_4= (ImageView) findViewById(R.id.number_4);
        number_5= (ImageView) findViewById(R.id.number_5);
    }

    //点击按钮响应事件
    private void StartButtonView() {
        begain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                is_quit = true;
                Intent intent = new Intent();
                intent.setClass(StartChallengActivity.this,index.getclass(name));
                Log.e("wwwwwwwwww", index.getclass(name)+"");
                intent.putExtra("content",name);
                intent.putExtra("type", index.gettype(name));
                StartChallengActivity.this.startActivity(intent);
                StartChallengActivity.this.finish();
            }
        });
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_quit = true;
                StartChallengActivity.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        is_pause = true;
        time_temp = time1;              //将计时器的时间暂停不走动
        count_down_thread.setStop(true, time_temp);
    }

    protected void onDestroy() {
        is_quit = true;
        homeKeyDown=false;
        if (count_down_thread!=null) {
            count_down_thread.setStartTime(1);
            count_down_thread.setTag(true);
            count_down_thread.setStartTime(0);
        }
        stopTime ();
        volleyUtil.cancelAllRequest();      //退出则结束访问服务器
        if(number_5!=null) {
            number_1.setImageResource(0);
            number_2.setImageResource(0);
            number_3.setImageResource(0);
            number_4.setImageResource(0);
            number_5.setImageResource(0);
        }
        unRegisterHomeKey ();
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (is_pause){
            count_down_thread.setStartTime(time1);
            count_down_thread.setStop(false, 0);
        }

    }

    protected void onStart() {
        super.onStart();
        Log.e("start","onstart");
        volleyUtil.getTodayTotal(totalInfo, new VolleyUtil.ResultCodeLisenter() {
            @Override
            public void onResult(int code) {
                if(code == 0){
                    numbertotal = totalInfo.getTotal();
                    Date data=new Date();
                    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Log.e("time",df.format(data));

                    int n9 = numbertotal/10000;//万位
                    if (n9!=0) {
                        number_1.setImageResource(resourceNumber.get(n9));
                    }
                    int n8 = numbertotal%10000;
                    int n7 = n8/1000;//千位
                    if (n7!=0) {
                        number_2.setImageResource(resourceNumber.get(n7));
                    }
                    int n6 = n8%1000;
                    int n5 = n6/100; //百位
                    if (n5!=0) {
                        number_3.setImageResource(resourceNumber.get(n5));
                    }
                    int n4 = n6%100;
                    int n3 = n4/10; //十位
                    if (n3!=0) {
                        number_4.setImageResource(resourceNumber.get(n3));
                    }
                    int n2 = n4%10;//个位
                    number_5.setImageResource(resourceNumber.get(n2));
                }
            }
        });

    }


    /**
     * 判断是否是第一次进入游戏
     * @return  返回是否是第一次，true表示是，false表示错误
     */
    public boolean fistTimeInGame(){
        String name="fist_in_game";
        /**
         * wyd
         * getSharedPreferences(name,mode)方法的第一个参数用于指定该文件的名称，名称不用带后缀，
         * 后缀会由Android自动加上。方法的第二个参数指定文件的操作模式，共有四种操作模式，
         */
        SharedPreferences sharedPreferences=StartChallengActivity.this.getSharedPreferences(name,
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值

        //是否是第一次
        boolean is_show=sharedPreferences.getBoolean("is_in_first", true);

        if(is_show==true){
            SharedPreferences.Editor editor = sharedPreferences.edit();////获取编辑器
            editor.putBoolean("is_in_first", false);//修改默认值
            editor.commit();//提交修改
        }

        return is_show;
    }

    void inputThread(){
        final AlertDialog alertdialog = new  AlertDialog.Builder(StartChallengActivity.this)
                .setTitle("注意事项" )
                .setMessage("数据正在copy，请等待！" ).create();


        handler=new Handler(){
            public void handleMessage(Message msg) {
                if(msg.arg1==101){
                    Log.i("mentalcalculation", "main_message_101");
                    alertdialog.dismiss();
                }
                if(msg.arg1==110){
                    Log.i("mentalcalculation", "main_message_110");
                    alertdialog.show();
                }
            }
        };
        outPutToSD outputToSdThread=new outPutToSD(this,handler);
        new Thread(outputToSdThread).start();
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
                            finish();
                        } else if (TextUtils.equals(reason,
                                SYSTEM_HOME_KEY_LONG)) {
                            finish();
                        }
                    }

                }
            };
            registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                    Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }
    private void unRegisterHomeKey (){
        if (mHomeKeyEventReceiver!=null){
            unregisterReceiver(mHomeKeyEventReceiver);
            mHomeKeyEventReceiver=null;
        }
    }
}



class myIndex {
    class value {
        Class c;
        int type;
        value(Class c,int type)
        {
            this.c = c;
            this.type = type;
        }
    }
    HashMap<String,value> map = new HashMap<String,value>();
    public myIndex()
    {
        map.put("1~5的加法", new value(Grade_1_top.class,1));
        map.put("1~5的减法", new value(Grade_1_top.class,2));
        map.put("6、7的加减法", new value(Grade_1_top.class,3));
        map.put("8、9的加减法", new value(Grade_1_top.class,4));
        map.put("10的加减法", new value(Grade_1_top.class,5));
        map.put("10以内的连加", new value(Grade_1_top.class,6));
        map.put("10以内的连减", new value(Grade_1_top.class,7));
        map.put("10以内的加减混合运算", new value(Grade_1_top.class,8));
        map.put("2、3、4、5加几的进位加法", new value(Grade_1_top.class,9));
        map.put("6、7、8加几的进位加法", new value(Grade_1_top.class,10));
        map.put("9加几的进位加法", new value(Grade_1_top.class,11));
        map.put("十几减9的退位减法", new value(Grade_1_down.class,1));
        map.put("十几减几的退位减法", new value(Grade_1_down.class,2));
        map.put("20以内的加减混合运算", new value(Grade_1_down.class,3));
        map.put("整十数加整十数", new value(Grade_1_down.class,4));
        map.put("整十数减整十数", new value(Grade_1_down.class,5));
        map.put("两位数与一位数的不进位加法", new value(Grade_1_down.class,6));
        map.put("两位数与一位数的进位加法", new value(Grade_1_down.class,7));
        map.put("两位数与一位数的不退位减法", new value(Grade_1_down.class,8));
        map.put("两位数与一位数的退位减法", new value(Grade_1_down.class,9));
        map.put("两位数加整十数", new value(Grade_1_down.class,10));
        map.put("两位数与两位数的不进位加法", new value(Grade_1_down.class,11));
        map.put("两位数与两位数的不退位减法", new value(Grade_1_down.class,12));
        map.put("两位数与两位数的进位加法", new value(Grade_2_top.class,1));
        map.put("两位数与两位数的退位减法", new value(Grade_2_top.class,2));
        map.put("100以内的连加", new value(Grade_2_top.class,3));
        map.put("100以内的连减", new value(Grade_2_top.class,4));
        map.put("100以内的加减混合运算", new value(Grade_2_top.class,5));
        map.put("2的乘法口诀", new value(Grade_2_top.class,6));
        map.put("3的乘法口诀", new value(Grade_2_top.class,7));
        map.put("4的乘法口诀", new value(Grade_2_top.class,8));
        map.put("5的乘法口诀", new value(Grade_2_top.class,9));
        map.put("乘加和乘减的计算", new value(Grade_2_top.class,10));
        map.put("6的乘法口诀", new value(Grade_2_top.class,11));
        map.put("7的乘法口诀", new value(Grade_2_top.class,12));
        map.put("8的乘法口诀", new value(Grade_2_top.class,13));
        map.put("9的乘法口诀", new value(Grade_2_top.class,14));
        map.put("用2~6的乘法口诀求商", new value(Grade_2_down.class,1));
        map.put("用7~8的乘法口诀求商", new value(Grade_2_down.class,2));
        map.put("用9的乘法口诀求商", new value(Grade_2_down.class,3));
        map.put("乘除混合运算", new value(Grade_2_down.class,4));
        map.put("有余数的除法", new value(Grade_2_down_yu.class,5));
        map.put("两位数加两位数", new value(Grade_2_down.class,6));
        map.put("两位数减两位数", new value(Grade_2_down.class,7));
        map.put("三位数减两位数", new value(Grade_2_down.class,8));
        map.put("三位数加两位数", new value(Grade_2_down.class,9));
        map.put("两位数加两位数的连续进位加法", new value(Grade_3_top.class,1));
        map.put("三位数减三位数的连续退位减法", new value(Grade_3_top.class,2));
        map.put("三位数连续进位加", new value(Grade_3_top.class,3));
        map.put("三位数加三位数", new value(Grade_3_top.class,4));
        map.put("三位数减三位数", new value(Grade_3_top.class,5));
        map.put("两位数乘一位数的不进位乘法", new value(Grade_3_top.class,6));
        map.put("两位数乘一位数的进位乘法", new value(Grade_3_top.class,7));
        map.put("三位数乘一位数", new value(Grade_3_top.class,8));
        map.put("末尾有0的三位数乘以一位数", new value(Grade_3_top.class,9));
        map.put("中间带0的三位数乘以一位数", new value(Grade_3_top.class,10));
        map.put("同分母分数加法", new value(Grade_3_top_fen.class,11));
        map.put("同分母分数减法", new value(Grade_3_top_fen.class,12));
        map.put("整十、整百、整千除以一位数", new value(Grade_3_down.class,1));
        map.put("几百几十、几千几百除以一位数", new value(Grade_3_down.class,2));
        map.put("两位数除以一位数", new value(Grade_3_down.class,3));
        map.put("三位数除以一位数", new value(Grade_3_down.class,4));
        map.put("商中间有0的除法（商有余数）", new value(Grade_3_down_yu.class,5));
        map.put("商末尾有0的除法（商有余数）", new value(Grade_3_down_yu.class,6));
        map.put("商中间有0的除法（商没有余数）", new value(Grade_3_down.class,7));
        map.put("商末尾有0的除法（商没有余数）", new value(Grade_3_down.class,8));
        map.put("两位数乘整十数", new value(Grade_3_down.class,9));
        map.put("两位数乘两位数", new value(Grade_3_down.class,10));
        map.put("三位数乘以两位数", new value(Grade_4_top.class,1));
        map.put("整十数除以整十数", new value(Grade_4_top.class,2));
        map.put("几百几十除以整十数", new value(Grade_4_top.class,3));
        map.put("两位数除以两位数", new value(Grade_4_top.class,4));
        map.put("三位数除以两位数", new value(Grade_4_top.class,5));
        map.put("整数四则混合运算", new value(Grade_4_top.class,6));
        map.put("加法结合律", new value(Grade_4_down.class,1));
        map.put("乘法结合律", new value(Grade_4_down.class,2));
        map.put("乘法分配律", new value(Grade_4_down.class,3));
        map.put("连减的简便运算", new value(Grade_4_down.class,4));
        map.put("小数加法", new value(Grade_4_down_xiao.class,5));
        map.put("小数减法", new value(Grade_4_down_xiao.class,6));
        map.put("小数的加减混合", new value(Grade_4_down_xiao.class,7));
        map.put("小数乘整数", new value(Grade_5_top.class,1));
        map.put("小数乘小数", new value(Grade_5_top.class,2));
        map.put("小数乘加", new value(Grade_5_top.class,3));
        map.put("小数乘减", new value(Grade_5_top.class,4));
        map.put("小数除以整数", new value(Grade_5_top.class,5));
        map.put("小数除以小数", new value(Grade_5_top.class,6));
        map.put("整数除以小数", new value(Grade_5_top.class,7));
        map.put("整数除以整数，商是小数", new value(Grade_5_top.class,8));
        map.put("求两个数的最大公因数", new value(Grade_5_down.class,1));
        map.put("求两个数的最小公倍数", new value(Grade_5_down.class,2));
        map.put("分数与小数互化", new value(Grade_5_down_fen_xiao.class,3));
        map.put("同分母分数加法", new value(Grade_5_down_fen.class,4));
        map.put("同分母分数减法", new value(Grade_5_down_fen.class,5));
        map.put("同分母分数连加、连减", new value(Grade_5_down_fen.class,6));
        map.put("异分母分数加减", new value(Grade_5_down_fen.class,7));
        map.put("分数加减混合运算", new value(Grade_5_down_fen.class,8));
        map.put("分数乘以整数", new value(Grade_6_top_fen.class,1));
        map.put("分数乘以分数", new value(Grade_6_top_fen.class,2));
        map.put("分数除以整数", new value(Grade_6_top_fen.class,3));
        map.put("一个数除以分数", new value(Grade_6_top_fen.class,4));
        map.put("小数与百分数互化", new value(Grade_6_down.class,1));
        map.put("分数与百分数互化", new value(Grade_6_down_fen.class,2));
        map.put("百分数的计算", new value(Grade_6_down3.class,3));
    }
    public Class getclass(String name)
    {
        return map.get(name).c;
    }
    public int gettype(String type)
    {
        return map.get(type).type;
    }


}