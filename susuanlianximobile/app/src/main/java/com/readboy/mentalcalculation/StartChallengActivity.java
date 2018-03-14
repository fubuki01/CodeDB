package com.readboy.mentalcalculation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.readboy.mentalcalculation.game.MyActivity.ActivityCollector;
import com.readboy.mentalcalculation.other.GetUserChapterName;
import com.readboy.mentalcalculation.other.myIndex;

public class StartChallengActivity extends Activity {

    private String nowChapterName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_startchalleng);
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("chapterName");
        nowChapterName = GetUserChapterName.getChapterName(this);  //主要得到上一次的章节名，方便后处理两种情况
        GetUserChapterName.setChapterName(name, StartChallengActivity.this);
        if (name == null) {
            this.finish();
            return;
        } else if (name.equals(nowChapterName)) {   //如果当前的章节名和之前取的一样，则需要将StartActivity进行finish
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } else {      //当从学习眼又一次进入的时候（选择的是不同的章节的时候）
            if (ActivityCollector.alreadyActivity.size() != 0) {
                for (Activity activity : ActivityCollector.alreadyActivity) {
                    activity.finish();
                }
                ActivityCollector.alreadyActivity.clear();
            }
        }
        super.onStart();
        myIndex index = myIndex.getIndex();
        Intent intent = new Intent();
        intent.setClass(this, index.getActivity(name));
        intent.putExtra("content", name);
        this.startActivity(intent);
        this.finish();
    }

}
