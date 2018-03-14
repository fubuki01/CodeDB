package com.readboy.game;
import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;
import com.readboy.susuan.bean.UserInfos;

import java.util.ArrayList;
import java.util.List;


public class finishDialog_w {
    Context context;
    android.app.AlertDialog ad;
    TextView quit_bt;

    ImageView lookoffice;
    ImageView updataMedalPicture;

    ImageView currentGradeIv;
    ImageView currentGradeIv2;
    ImageView currentGradeIv3;
    ImageView currentGradeIv4;

    ImageView currentOfficeIv1;
    ImageView currentOfficeIv2;
    ImageView currentOfficeIv3;
    ImageView currentOfficeIv4;
    ImageView currentOfficeIv5;
    ImageView currentOfficeIv6;
    ImageView currentOfficeIv7;
    ImageView currentOfficeIv8;


    ImageView historyGradeIv;
    ImageView historyGradeIv2;
    ImageView historyGradeIv3;
    ImageView historyGradeIv4;

    ImageView historyOfficeIv;
    ImageView historyOfficeIv2;
    ImageView historyOfficeIv3;
    ImageView historyOfficeIv4;
    ImageView historyOfficeIv5;
    ImageView historyOfficeIv6;
    ImageView historyOfficeIv7;
    ImageView historyOfficeIv8;


    Button btn_quitdialog;
    Button btn_continu;
    //Button bt_stop;
    int topOffice;
    int topGrade;
    int currentOffice;
    int currentGrade;
    int superpff;
    int superhgrade;
    private Destort mDestort;
    UserInfos current;
    List<UserInfos> users;
    rankDialog rankDialog;
    Window window;
    List<ImageView> nowOfficeList=new ArrayList<ImageView>(); //存储当前排名的ID
    List<ImageView> nowGradeList=new ArrayList<ImageView>(); //存储当前分数的ID
    List<ImageView> historyGradeList=new ArrayList<ImageView>(); //存储历史分数的ID
    List<ImageView> historyOfficeList=new ArrayList<ImageView>(); //存储历史排名的ID
    List<Integer> numberPictureID=new ArrayList<Integer>();  //存储显示数字的资源
    public finishDialog_w(Context context,int office,int grade,String intent_type,UserInfos current,List<UserInfos> users) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.current=current;
        this.users=users;
        ad=new android.app.AlertDialog.Builder(context,R.style.finishiDialogStyle).create();
            ad.show();
        WindowManager.LayoutParams lp=ad.getWindow().getAttributes();
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        ad.getWindow().setAttributes(lp);
//        ad.getWindow().setContentView(R.layout.finish_dialog_w);
//        ad.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window = ad.getWindow();
        window.setAttributes(lp);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.finish_dialog_w);
        findById();   //拿到控件ID
        getDrawableNumberId();  //存储数字图片的ID，方便用来显示
        //setClickContinueEvent(final View.OnClickListener listener);
        //setClickQuitEvent();
        //设置点击空白处不可退出
        ad.setCancelable(false);
        currentOffice=current.getRank();  //得到当前排名
        currentGrade=current.getScore();   //得到当前分数
        superhgrade=current.getMaxScore();  //得到历史最高分
        superpff=current.getMaxRank();       //得到历史最高排名

        setCurrentData(currentOffice,currentGrade);
        setTopData(intent_type);
        setOnClickListener();
    }
   //存储数字图片的ID
    private void getDrawableNumberId() {
        numberPictureID.add(R.drawable.number0);
        numberPictureID.add(R.drawable.number1);
        numberPictureID.add(R.drawable.number2);
        numberPictureID.add(R.drawable.number3);
        numberPictureID.add(R.drawable.number4);
        numberPictureID.add(R.drawable.number5);
        numberPictureID.add(R.drawable.number6);
        numberPictureID.add(R.drawable.number7);
        numberPictureID.add(R.drawable.number8);
        numberPictureID.add(R.drawable.number9);

    }

    //得到所有空间的ID
    private void findById() {
        lookoffice=(ImageView)window.findViewById(R.id.iv_lookoffice); //看排行榜
        btn_quitdialog= (Button) window.findViewById(R.id.quit);  //退出
        btn_continu= (Button) window.findViewById(R.id.again);  //继续游戏
       // bt_stop=(Button)window.findViewById(R.id.bt_stop);   //退出
        updataMedalPicture=(ImageView)window.findViewById(R.id.recore_type);  //更新奖牌的图标

        currentGradeIv=(ImageView)window.findViewById(R.id.iv_currentgrade1);  //当前分数ID
        nowGradeList.add(currentGradeIv);
        currentGradeIv2=(ImageView)window.findViewById(R.id.iv_currentgrade2);
        nowGradeList.add(currentGradeIv2);
        currentGradeIv3=(ImageView)window.findViewById(R.id.iv_currentgrade3);
        nowGradeList.add(currentGradeIv3);
        currentGradeIv4=(ImageView)window.findViewById(R.id.iv_currentgrade4);
        nowGradeList.add(currentGradeIv4);

        currentOfficeIv1=(ImageView)window.findViewById(R.id.iv_currentoffice1); //当前排名ID
        nowOfficeList.add(currentOfficeIv1);
        currentOfficeIv2=(ImageView)window.findViewById(R.id.iv_currentoffice2);
        nowOfficeList.add(currentOfficeIv2);
        currentOfficeIv3=(ImageView)window.findViewById(R.id.iv_currentoffice3);
        nowOfficeList.add(currentOfficeIv3);
        currentOfficeIv4=(ImageView)window.findViewById(R.id.iv_currentoffice4);
        nowOfficeList.add(currentOfficeIv4);
        currentOfficeIv5=(ImageView)window.findViewById(R.id.iv_currentoffice5);
        nowOfficeList.add(currentOfficeIv5);
        currentOfficeIv6=(ImageView)window.findViewById(R.id.iv_currentoffice6);
        nowOfficeList.add(currentOfficeIv6);
        currentOfficeIv7=(ImageView)window.findViewById(R.id.iv_currentoffice7);
        nowOfficeList.add(currentOfficeIv7);
        currentOfficeIv8=(ImageView)window.findViewById(R.id.iv_currentoffice8);
        nowOfficeList.add(currentOfficeIv8);

        historyGradeIv=(ImageView)window.findViewById(R.id.iv_historygrade1);  //历史最高分
        historyGradeList.add(historyGradeIv);
        historyGradeIv2=(ImageView)window.findViewById(R.id.iv_historygrade2);
        historyGradeList.add(historyGradeIv2);
        historyGradeIv3=(ImageView)window.findViewById(R.id.iv_historygrade3);
        historyGradeList.add(historyGradeIv3);
        historyGradeIv4=(ImageView)window.findViewById(R.id.iv_historygrade4);
        historyGradeList.add(historyGradeIv4);

        historyOfficeIv=(ImageView)window.findViewById(R.id.iv_historyoffice1); //历史最高排名
        historyOfficeList.add(historyOfficeIv);
        historyOfficeIv2=(ImageView)window.findViewById(R.id.iv_historyoffice2);
        historyOfficeList.add(historyOfficeIv2);
        historyOfficeIv3=(ImageView)window.findViewById(R.id.iv_historyoffice3);
        historyOfficeList.add(historyOfficeIv3);
        historyOfficeIv4=(ImageView)window.findViewById(R.id.iv_historyoffice4);
        historyOfficeList.add(historyOfficeIv4);
        historyOfficeIv5=(ImageView)window.findViewById(R.id.iv_historyoffice5);
        historyOfficeList.add(historyOfficeIv5);
        historyOfficeIv6=(ImageView)window.findViewById(R.id.iv_historyoffice6);
        historyOfficeList.add(historyOfficeIv6);
        historyOfficeIv7=(ImageView)window.findViewById(R.id.iv_historyoffice7);
        historyOfficeList.add(historyOfficeIv7);
        historyOfficeIv8=(ImageView)window.findViewById(R.id.iv_historyoffice8);
        historyOfficeList.add(historyOfficeIv8);

    }

    //所有按钮的监听事件
    private void setOnClickListener() {
        //重新挑战的监听
        btn_continu.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ad.dismiss();    //消失当前的dialog
                GameActivity.homeKeyDown=false;
                if (mDestort!=null){
                    mDestort.onClick();
                }
                ActivityCollector.finishAll();  //如果之前还打开了其他的activity则销毁，比如草稿纸开了，一直没关而时间到了
            }
        });

        //查看排行榜
        lookoffice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rankDialog =new rankDialog(context,current,users);
            }
        });

        //退出当前页面
        btn_quitdialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                ActivityCollector.isdestroy=true;
                if (mDestort!=null){
                   mDestort.onClick();
                    waitdialogActivity.notShowAgain=true;
                }
                ActivityCollector.finishAll();  //销毁之前存储的那些Activity(写了个类的方法)

            }
        });
    }


    public void setDestort (Destort destort){
        mDestort = destort;
    }
    //回调接口
    public interface Destort{
        public void onClick();
    }



    /*设置当前排名和分数*/
    public void setCurrentData(int office ,int grade){
        //得到当前排名的图片并且进行显示
        String nowOffice=office+"";
        int officeLength=nowOffice.length();
        for(int i=0;i<officeLength;i++){
            int index=Integer.parseInt(nowOffice.charAt(i)+""); //得到每一位的数字（或者用nowOffice.charAt(i)-48也可以）
            nowOfficeList.get(i).setBackgroundResource(numberPictureID.get(index));
        }
        //设置当前的分数并且显示
        String nowGrade=grade+"";
        int gradeLength=nowGrade.length();
        for(int i=0;i<gradeLength;i++){
            int index=Integer.parseInt(nowGrade.charAt(i)+""); //得到每一位的数字（或者用nowOffice.charAt(i)-48也可以）
            nowGradeList.get(i).setBackgroundResource(numberPictureID.get(index));
        }
    }



    /*设置最高的局和分数*/
    public void setTopData(String intent_type){
      //设置历史最高的分数并且显示
        String nowGrade=superhgrade+"";
        int gradeLength=nowGrade.length();
        for(int i=0;i<gradeLength;i++){
            int index=Integer.parseInt(nowGrade.charAt(i)+""); //得到每一位的数字（或者用nowOffice.charAt(i)-48也可以）
            historyGradeList.get(i).setBackgroundResource(numberPictureID.get(index));
        }
       //设置厉害最高的排名并且显示
        //得到当前排名的图片并且进行显示
        String nowOffice=superpff+"";
        int officeLength=nowOffice.length();
        for(int i=0;i<officeLength;i++){
            int index=Integer.parseInt(nowOffice.charAt(i)+""); //得到每一位的数字（或者用nowOffice.charAt(i)-48也可以）
            historyOfficeList.get(i).setBackgroundResource(numberPictureID.get(index));
        }

        //设置奖牌的图标，看是否打破历史记录
        if(currentGrade<superhgrade){  //当次成绩小于历史最高
            updataMedalPicture.setBackgroundResource(R.drawable.finish_right_fighting);
        }
        else if(currentGrade==superhgrade){  //当前成绩等于最高成绩
            updataMedalPicture.setBackgroundResource(R.drawable.finish_right_equal);
        }
        else if(currentGrade>superhgrade){                              //当前成绩打破最高成绩
            updataMedalPicture.setBackgroundResource(R.drawable.finish_right_break);
        }
    }

    //重写返回键的监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
               ActivityCollector.finishAll(); //退出所有的activity
            return true;
        }
        return false;
    }

    /*消除按钮*/
    public void dismiss() {
        ad.dismiss();
    }


    public void hide(){
        ad.hide();
    }

    public void show(){
        ad.show();
    }
    /**
     * 更新名次
     */
//    public void updateGradeContent(){
//        if(currentOffice>=topOffice){
//            topOffice=currentOffice;
//        }
//        if(currentGrade>=topGrade){
//            topGrade=currentGrade;
//        }
//    }
    /*
     * 设置保存的数据 排名
     * @param fist_grade 第一名分数
     * @param second_grade 第二名分数
     * @param third_grade 第三名分数
     */
//    public void updateGrade(int fist_grade,int office,String intent_type){
//        //实例化SharedPreferences对象（第一步）
//        String name="test";
//        SharedPreferences mySharedPreferences= context.getSharedPreferences(name,
//                Activity.MODE_PRIVATE);
//        //实例化SharedPreferences.Editor对象（第二步）
//        SharedPreferences.Editor editor = mySharedPreferences.edit();
//        //用putString的方法保存数据
//        editor.putInt("first_grade"+intent_type, topGrade);
//        editor.putInt("office_grade"+intent_type, topOffice);
//        //提交当前数据
//        editor.commit();
//    }


}
