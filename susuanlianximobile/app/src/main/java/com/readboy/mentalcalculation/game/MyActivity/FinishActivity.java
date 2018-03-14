package com.readboy.mentalcalculation.game.MyActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.readboy.mentalcalculation.R;
import com.readboy.mentalcalculation.other.CurrentAndUsersUtils;
import com.readboy.susuan.bean.UserInfos;
import com.readboy.susuan.util.VolleyUtil;

import java.util.ArrayList;
import java.util.List;


public class FinishActivity extends Activity{

    private Button lookRankButton;
    private Button restartGameButton;
    private ImageView showGradeNumberIV0;
    private ImageView showGradeNumberIV1;
    private ImageView showGradeNumberIV2;
    private ImageView showGradeNumberIV3;
    private ImageView showGradeNumberIV4;
    private TextView showCurrentRightQuestion;
    private TextView showCurrentRank;
    private TextView showHistoryGrade;
    private TextView showHistoryRank;
    private ImageView backgroundLight;
    private int current_grade;  //用户当前的分数
    private int current_office;  //用户当前的题目数
    private int current_rank;
    private int history_Rank;
    private int history_grade;
    private List<Integer> numberInfoID=new ArrayList<Integer>();  //存储数字图片的ID
    private List<ImageView> ivShowID=new ArrayList<ImageView>();  //存储显示对应数字的ImageView的ID
    private ImageView exitGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        getAllDataInfomation();          //获取从服务器之前传过来所有需要显示的数据
        getDrawableNumberId();          //存储数字图片的ID，方便用来显示
        findView();                  //获取组件的ID
        setShowImageViewGrade();  //设置显示用户当前分数的Imageview
        setButtonListener();   //设置按钮的监听
        setImageViewAnimotion();  //设置图片迅速旋转

    }


    /*
      让背景光环匀速旋转
     */
    private void setImageViewAnimotion() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.imagerota);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        backgroundLight.startAnimation(animation);
    }

    /*
     设置显示用户当前分数的Imageview
     */
    private void setShowImageViewGrade() {
        //设置当前的分数并且显示(ImageView显示的)
        String nowGrade=current_grade+"";
        int gradeLength=nowGrade.length();
        for(int i=0;i<gradeLength;i++) {
            int index = Integer.parseInt(nowGrade.charAt(i) + ""); //得到每一位的数字（或者用nowGrade.charAt(i)-48也可以）
            ivShowID.get(i).setBackgroundResource(numberInfoID.get(index));
        }
        //显示用户当前的题目数，排名和历史成绩和排名(TextView)
        showCurrentRank.setText(current_rank+"");
        showCurrentRightQuestion.setText(current_office+"");
        showHistoryGrade.setText(history_grade+"");
        showHistoryRank.setText(history_Rank+"");

    }

    /*
    设置按钮监听
     */
    private void setButtonListener() {
        //设置查看排行榜的监听
        lookRankButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(FinishActivity.this,RankActivity.class);
                    intent.putExtra("current_grade",current_grade);  //传送分数
                    intent.putExtra("current_rank",current_rank);    //传送排名
                    startActivity(intent);
            }
        });
        //设置重新挑战的监听
        restartGameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseGameActivity.restartGameFromFishAC=true;
                finish();
            }
        });
        //设置退出游戏的监听
        exitGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseGameActivity.exitGameFromFishAC=true;
                finish();
            }
        });
    }

    /*
      得到布局组件的ID
     */
    private void findView() {
        //显示当前分数的背景，后面要进行旋转
        backgroundLight = (ImageView) findViewById(R.id.medallight);
        //查看排行榜的按钮
        lookRankButton = (Button) findViewById(R.id.medallookrank);
        //重新挑战的按钮
        restartGameButton = (Button) findViewById(R.id.medalrestartgame);
        ///图显示用户当前得分的第一个ImageView
        showGradeNumberIV0 = (ImageView) findViewById(R.id.medalnumber0);
        ivShowID.add(showGradeNumberIV0);
        //图显示用户当前得分的ImageView
        showGradeNumberIV1 = (ImageView) findViewById(R.id.medalnumber1);
        ivShowID.add(showGradeNumberIV1);
        //图显示用户当前得分的ImageView
        showGradeNumberIV2 = (ImageView) findViewById(R.id.medalnumber2);
        ivShowID.add(showGradeNumberIV2);
        //图显示用户当前得分的ImageView
        showGradeNumberIV3 = (ImageView) findViewById(R.id.medalnumber3);
        ivShowID.add(showGradeNumberIV3);
        //图显示用户当前得分的ImageView
        showGradeNumberIV4 = (ImageView) findViewById(R.id.medalnumber4);
        ivShowID.add(showGradeNumberIV4);
        //答对题数
        showCurrentRightQuestion = (TextView) findViewById(R.id.tv_rightnumber);
        //当前排名
        showCurrentRank = (TextView) findViewById(R.id.tv_currentrank);
        //历史分数
        showHistoryGrade = (TextView) findViewById(R.id.tv_historygrade);
        //历史排名
        showHistoryRank = (TextView) findViewById(R.id.tv_historyrank);
        //退出按钮
        exitGame =(ImageView)findViewById(R.id.iv_exit);
    }

    /*
    获取所有需要显示的数据
     */
    public void getAllDataInfomation() {
        Intent intent=getIntent();
        current_grade=intent.getExtras().getInt ("current_grade");  //得到当前用户的分数
        current_office=intent.getExtras().getInt("current_office");  //得到当前用户的分数
        current_rank =intent.getExtras().getInt ("current_rank");  //得到当前用户的排名
        history_Rank =intent.getExtras().getInt ("history_rank");  //得到当前用户的历史最高排名
        history_grade =intent.getExtras().getInt("history_grade");  //得到当前用户的历史最高分数
    }

    /*
     存储显示数字的图片资源ID,方便用来后面的显示
     */
    public void getDrawableNumberId() {
        numberInfoID.add(R.drawable.number0);
        numberInfoID.add(R.drawable.number1);
        numberInfoID.add(R.drawable.number2);
        numberInfoID.add(R.drawable.number3);
        numberInfoID.add(R.drawable.number4);
        numberInfoID.add(R.drawable.number5);
        numberInfoID.add(R.drawable.number6);
        numberInfoID.add(R.drawable.number7);
        numberInfoID.add(R.drawable.number8);
        numberInfoID.add(R.drawable.number9);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            BaseGameActivity.exitGameFromFishAC=true;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
