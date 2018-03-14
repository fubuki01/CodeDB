package com.readboy.mentalcalculation.game.MyActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.readboy.mentalcalculation.game.MyDialog.Start_dialog;
import com.readboy.mentalcalculation.other.CountDownThread;
import com.readboy.mentalcalculation.other.PlaySound;
import com.readboy.mentalcalculation.other.costomAnimation;
import com.readboy.mentalcalculation.other.myIndex;
import com.readboy.mentalcalculation.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


abstract public class BaseGameActivity extends Activity implements View.OnClickListener {

    private Thread supply_project_thread;
    private Start_dialog start_dialog;
    private android.app.AlertDialog ad;
    private Button closedialog_exit;
    private Button closedialog_cancel;
    private Button skipdialog_skip;
    private Button skipdialog_cancel;
    private boolean keyswitch = false;

    private ImageButton key_delete, game_back;
    private ImageView animation_correct_wrong, cue_item_icon;
    protected TextView subject_number, timeview, animation_score, cue_item_text;

    private costomAnimation costom_animation;
    protected List<Integer> resourceIdListSuccess = new ArrayList<Integer>();//存放成功图片id的list
    protected List<Integer> resourceIdListFail = new ArrayList<Integer>();//存放失败图片id的list
    protected int stayAnimationSuccessTime;//判断正确动画播放时长
    protected int stayAnimationFailTime;//错误动画播放时长


    protected int answer_count;

    int successOrFail = -1;
    protected int durationTime;
    public PlaySound playSound;
    protected int problem_number_correct_count = 0;
    protected int stayAnimationUpScore;
    protected int student_grade;
    protected int time = 180;
    protected int s = 0;

    public CountDownThread count_down_thread;
    final protected int STARTNUM = 180;
    protected int time_temp;
    protected boolean is_pause_time = false;
    public boolean is_start_time = false;
    private Thread mythread;

    private boolean is_Start_Dialog = false;//判断是否进入startDialog

    protected String problem;
    protected String answer;
    protected Boolean correct;
    protected Boolean endAnimation = false;
    protected int subject_count = 0;
    protected Object Alock;
    protected BroadcastReceiver mHomeKeyEventReceiver = null;  //设置Home键监听的广播
    public static boolean homeKeyDown = false;   //Home键监听的标识
    private Button next_problem_button, key_dian, key_0, key_1, key_2, key_3, key_4, key_5, key_6, key_7, key_8, key_9;
    public static boolean exitGameFromFishAC = false;   //退出游戏
    public static boolean restartGameFromFishAC = false; //重新开始
    public static String chapterName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myIndex index = myIndex.getIndex();
        chapterName = this.getIntent().getExtras().getString("content");
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(index.getLayout(chapterName));
        findView();
        init();
        setListener();
        addResource();

        GetProAndAns(index.getThread(chapterName), index.gettype(chapterName));
        ActivityCollector.addActivity(this);
        ActivityCollector.alreadyActivity.add(this);

        if (restartGameFromFishAC == false) {
            ActivityCollector.addActivity(this);
            start_dialog = new Start_dialog(this);
            is_Start_Dialog = true;

            /**
             * 回调开启activity时间线程
             */
            start_dialog.setCloseDialogListener(new Start_dialog.ICloseDialogListener() {
                @Override
                public void onClose() {
                    timeThread();
                    is_start_time = true;
                }
            });
        } else {
            restartGameFromFishAC = false;
            is_Start_Dialog = false;
            is_start_time = true;
            timeThread();
        }
    }

    /**
     * 获取游戏界面
     */
    protected void findView() {
        cue_item_icon = (ImageView) findViewById(R.id.cue_item_icon);
        cue_item_text = (TextView) findViewById(R.id.cue_item_text);
        game_back = (ImageButton) findViewById(R.id.game_back);
        key_delete = (ImageButton) findViewById(R.id.key_delete);
        next_problem_button = (Button) findViewById(R.id.next_problem_button);
        animation_correct_wrong = (ImageView) findViewById(R.id.animation_correct_wrong);
        animation_score = (TextView) findViewById(R.id.animation_score);
        subject_number = (TextView) findViewById(R.id.subject_number);
        timeview = (TextView) findViewById(R.id.timeview);
        key_dian = (Button) findViewById(R.id.key_dian);
        key_0 = (Button) findViewById(R.id.key_0);
        key_1 = (Button) findViewById(R.id.key_1);
        key_2 = (Button) findViewById(R.id.key_2);
        key_3 = (Button) findViewById(R.id.key_3);
        key_4 = (Button) findViewById(R.id.key_4);
        key_5 = (Button) findViewById(R.id.key_5);
        key_6 = (Button) findViewById(R.id.key_6);
        key_7 = (Button) findViewById(R.id.key_7);
        key_8 = (Button) findViewById(R.id.key_8);
        key_9 = (Button) findViewById(R.id.key_9);
        key_delete.setOnClickListener(this);
        key_dian.setOnClickListener(this);
        key_0.setOnClickListener(this);
        key_1.setOnClickListener(this);
        key_2.setOnClickListener(this);
        key_3.setOnClickListener(this);
        key_4.setOnClickListener(this);
        key_5.setOnClickListener(this);
        key_6.setOnClickListener(this);
        key_7.setOnClickListener(this);
        key_8.setOnClickListener(this);
        key_9.setOnClickListener(this);

        findChildView();
    }

    abstract protected void findChildView(); //获取子类View组件

    /**
     * 初始化变量
     */
    private void init() {
        student_grade = 0;
        playSound = new PlaySound(this);
        Alock = new Object();
    }


    /**
     * 隐藏输入法 显示光标
     */
    public void hintInput(EditText editText) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT <= 10) {
            // 点击EditText，屏蔽默认输入法
            editText.setInputType(InputType.TYPE_NULL); // editText是声明的输入文本框。
        } else {
            // 点击EditText，隐藏系统输入法
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method method = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);// 4.0的是setShowSoftInputOnFocus，4.2的是setSoftInputShownOnFocus
                method.setAccessible(false);
                method.invoke(editText, false); // editText是声明的输入文本框。
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 统一设置监听
     */
    private void setListener() {


        game_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBackDialog();
            }
        });

        setChiledListener();

        next_problem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (endAnimation == false) {
                    if (InputisEmpty() == 0) {
                        if(keyswitch)
                            return;
                        keyswitch=true;
                        ad = new android.app.AlertDialog.Builder(BaseGameActivity.this).create();
                        Window window = ad.getWindow();
                        ad.show();
                        window.setContentView(R.layout.dialog_skip);
                        skipdialog_cancel = (Button) window.findViewById(R.id.skipdialog_cancel);
                        skipdialog_skip = (Button) window.findViewById(R.id.skipdialog_skip);
                        ad.setCancelable(false);
                        /**
                         * 点击取消按钮
                         */
                        skipdialog_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                keyswitch=false;
                                ad.dismiss();
                            }
                        });

                        /**
                         * 点击确定按钮
                         */
                        skipdialog_skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 * 点击跳过 显示下一题
                                 */
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        synchronized (Alock) {
                                            Alock.notifyAll();
                                        }
                                    }
                                }, 100);
                                keyswitch=false;
                                ad.dismiss();
                            }
                        });
                    } else if (InputisEmpty() == 1) {
                        Toast.makeText(BaseGameActivity.this, "答案未填写完整", Toast.LENGTH_SHORT).show();
                    } else
                        judgeAns();
                }
            }
        });
    }


    /**
     * 点击返回键或按钮弹出对话框
     */
    private void gameBackDialog() {
        if(keyswitch)
            return;
        keyswitch=true;
        ad = new android.app.AlertDialog.Builder(BaseGameActivity.this, R.style.startdialog).create();
        ad.show();
        WindowManager.LayoutParams lp = ad.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Window window = ad.getWindow();
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.dialog_close);
        ad.setCancelable(false);
        closedialog_cancel = (Button) window.findViewById(R.id.closedialog_cancel);
        closedialog_exit = (Button) window.findViewById(R.id.closedialog_exit);
        pauseTime();//暂停当前时间
        /**
         * 点击取消按钮
         */
        closedialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyswitch=false;
                ad.dismiss();
                canclePauseTime();
            }
        });
        /**
         * 点击确定按钮
         */
        closedialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyswitch=false;
                BaseGameActivity.this.finish();
                System.exit(0);
            }
        });
    }


    /**
     * 判断点击的是哪一个Edittext
     */
    abstract protected void setChiledListener();


    /**
     * 用于下一题点击按键判断和相关操作
     * @return
     */
    abstract protected int InputisEmpty();


    /**
     * 计时线程
     */
    private void timeThread() {
        Handler time_hander = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();//获取传递过来的Message中的数据集合 wyd
                if(time!=180)
                    time = b.getInt("time");//从数据集合中获取key为＂time＂的值，这个值是整型 wyd
                else
                    time=179;
                timeview.setText(time + "秒");
                if (time <= 10)
                    timeview.setTextColor(Color.argb(255, 255, 0, 0));
                if (time <= 0) {
                    showFinishDialog();
                }
            }
        };
        count_down_thread = new CountDownThread(STARTNUM, time_hander);
        mythread = new Thread(count_down_thread);
        mythread.start();
    }

    /**
     * 获取题目和答案；
     * @param chapterclass
     * @param chaptertype
     */
    protected void GetProAndAns(Class chapterclass, int chaptertype) {

        Handler problem_hander = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                setProAndAns(b.getString("problem"), b.getString("answer"));
                subject_count++;
                subject_number.setText("第" + subject_count + "题");
            }
        };
        Class[] classes = new Class[]{Handler.class, Object.class, int.class};
        try {
            supply_project_thread = new Thread((Runnable) chapterclass.getConstructor(classes).newInstance(problem_hander, Alock, chaptertype));
        } catch (Exception e) {
            Log.e("myException", e.getMessage());
        }
        supply_project_thread.start();
    }

    abstract protected void setProAndAns(String pro, String ans); //用于设置题目和答案

    /**
     * 判断答案对错
     */
    abstract protected void judgeAns();

    /**
     * 判断对错动画和分数动画
     *
     * @param choosing
     */
    protected void Animal(boolean choosing) {
        endAnimation = true;
        animation_correct_wrong.setVisibility(View.VISIBLE);
        if (choosing == true) {
            successOrFail = 1;
            durationTime = 1000;
            costom_animation.setAnimation(animation_correct_wrong, resourceIdListSuccess);
            UpSuccessAndGrade();
        } else {
            successOrFail = 2;
            durationTime = 1000;
            s = 0;
            costom_animation.setAnimation(animation_correct_wrong, resourceIdListFail);
        }

        costom_animation.start(false, durationTime);   //启动动画
        if (time != 0) {
            playSound.PlaySoundSuccessOrFail(successOrFail);
        }


        Handler handler = new Handler();
        /**
         * 延迟动画结束时间
         */
        handler.postDelayed(new Runnable() {
            public void run() {
                costom_animation.stop();
                animation_correct_wrong.setVisibility(View.INVISIBLE);
                synchronized (Alock) {
                    Alock.notifyAll();
                }
                endAnimation = false;
            }
        }, stayAnimationSuccessTime);
    }

    /**
     * 分数动画、更新分数
     */
    protected void UpSuccessAndGrade() {
        s = s + 1;
        if (s > 10) {
            s = 10;
        }
        animation_score.setVisibility(View.VISIBLE);
        animation_score.setText("+" + s);
        durationTime = 1500;
        costom_animation.start(false, durationTime);   //启动动画
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                costom_animation.stop();
                animation_score.setVisibility(View.INVISIBLE);
            }
        }, stayAnimationUpScore);
        student_grade = student_grade + s;
    }

    /**
     * 加载动画资源
     */
    private void addResource() {

        costom_animation = new costomAnimation();
        resourceIdListSuccess.add(R.drawable.correct);
        stayAnimationSuccessTime = 1000;//1s
        resourceIdListFail.add(R.drawable.wrong);
        stayAnimationFailTime = 1000;//1s
        stayAnimationUpScore = 1000;
    }

    /**
     * 数字键盘点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        setKeyBoard(view);
    }

    /**
     * 数字键盘点击事件抽象方法
     * @param view
     */
    abstract protected void setKeyBoard(View view);

    @Override
    protected void onResume() {
        super.onResume();
        if (is_pause_time) {
            canclePauseTime();
        }
        if (exitGameFromFishAC) { //如果在排名界面点击退出游戏
            finish();
            exitGameFromFishAC=false;
            System.exit(0);
        }
        if (restartGameFromFishAC) { //如果在排名界面点击重新挑战
            time=180;
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            ActivityCollector.alreadyActivity.clear();
            finish();
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
        if(time==0)
            showFinishDialog();



        if(is_Start_Dialog) {
            if (start_dialog.isLive && start_dialog.isScreenAndHomeKeydown) {
                start_dialog.timecountON();
                start_dialog.isScreenAndHomeKeydown = false;
            }
        }

    }

    /**
     * 暂停
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (is_start_time){
            pauseTime();
        }

        if(start_dialog!=null&&is_Start_Dialog&&start_dialog.isLive && !start_dialog.isScreenAndHomeKeydown)
        {
            start_dialog.timecountOFF();
            start_dialog.isScreenAndHomeKeydown=true;
        }

    }


    /**
     * 暂停当前时间
     */
    private void pauseTime(){
        time_temp = time;              //将计时器的时间暂停不走动
        if(count_down_thread!=null)
            count_down_thread.setStop(true, time_temp);
        is_pause_time = true;
    }

    /**
     * 取消暂停当前时间
     */
    private void canclePauseTime(){
        count_down_thread.setStartTime(time);
        count_down_thread.setStop(false, 0);
        is_pause_time = false;
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 停止并重置音乐
         */
        playSound.mp_fail.stop();
        playSound.mp_fail.release();
        playSound.mp_fail = null;
        playSound.mp_success.stop();
        playSound.mp_success.release();
        playSound.mp_success = null;

        if (is_start_time){
            count_down_thread.setTag(true);
            is_start_time = false;
        }
    }


    /**
     * 计时结束，弹出结束对话框；
     */
    private void showFinishDialog() {
        Intent intent = new Intent();   //跳转到等待页面
        intent.setClass(BaseGameActivity.this, waitdialogActivity.class);
        //传当前用户的分数和做的题目数
        intent.putExtra("guanka", problem_number_correct_count);
        intent.putExtra("current_grade", student_grade);
        Log.e("grade", student_grade + "@@");
        startActivity(intent);

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
                            homeKeyDown = true;
                        } else if (TextUtils.equals(reason,
                                SYSTEM_HOME_KEY_LONG)) {
                            homeKeyDown = true;
                            // 表示长按home键,显示最近使用的程序列表
                        }
                    }

                }
            };
            registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                    Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }
    }
   /*
    不改变
    */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

    /**
     * 返回键监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            gameBackDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

}

