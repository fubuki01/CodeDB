package com.readboy.mentalcalculation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.R.attr.name;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/3/15.
 */

public class GetUserChapterName {
    public static String username;
    public static void setChapterName(String name, Activity start){  //得到用户选择的章节
        username=name;
        SharedPreferences sharedPreferences = start.getSharedPreferences("config", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("name", username);
        editor.commit();//提交修改
        Log.e("ggggggggggggggg", username+"");
    }
    public static String getChapterName(Activity start){   //返回用户选择的章节

        SharedPreferences  sp = start.getSharedPreferences("config", Context.MODE_PRIVATE);
        String StrchapterName = sp.getString("name","");//如果取不到值就取后面的""
        Log.e("hhhhhhhhhhhhhhhh", StrchapterName);
        return StrchapterName;
    }



}


