package com.readboy.mentalcalculation.game.MyActivity;

import android.app.Activity;
import android.app.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity的收集器
 */

public class ActivityCollector extends Application{
    public static boolean isdestroy=false;
    public static List<String> mylist = new ArrayList<String>();

    //存储Activity的List
    public static List<Activity> activities = new ArrayList<Activity>();
    public static List<Activity> alreadyActivity = new ArrayList<Activity>();
    //添加Activity主要是用来进行判断是否有除了GameActivity之外的一些方法
    public static void addActivity(Activity activity) {
        if(!activities.contains(activity)){
            activities.add(activity);
        }
    }

    //移出Activity
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //销毁所有Activity
    public static void finishAll() {

        for (Activity activity:activities) {
            activity.finish();
        }
        activities.clear();
    }

    //添加Activity,用于判断是否某个Activity已经被开启过
    public static void putActivity(Activity activity) {
        if(!alreadyActivity.contains(activity)){
            alreadyActivity.add(activity);
        }
    }

}
