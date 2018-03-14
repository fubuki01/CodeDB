package com.readboy.mentalcalculation.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    }
    public static String getChapterName(Activity start){   //返回用户选择的章节

        SharedPreferences  sp = start.getSharedPreferences("config", Context.MODE_PRIVATE);
        String StrchapterName = sp.getString("name","");//如果取不到值就取后面的""
        return StrchapterName;
    }



}


