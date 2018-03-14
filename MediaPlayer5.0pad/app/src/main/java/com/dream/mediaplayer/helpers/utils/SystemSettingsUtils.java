package com.dream.mediaplayer.helpers.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by LCL on 2017/5/9.
 */

public class SystemSettingsUtils{

    private final static  String SYSMODE_PARENTMANAGE = "sysmode_parentmanage";

    /**
     * 获取系统当前模式，需要权限：<uses-permission android:name="android.permission.READ_SETTINGS"/>
     * @param context
     * @param defaultMode
     * @return 0（普通模式）；1（孩子禁用模式，C3 手机上通过物理开关开启）；2（晚睡禁用模式）；3（上课禁用模式）
     * 1）获取到的当前模式的值是 1 和 3 时，不能播放歌曲;
     */

    // this method is to get current system mode
    public static int getCurSysMode(Context context, int defaultMode){
        return Settings.System.getInt(context.getContentResolver(), SYSMODE_PARENTMANAGE, defaultMode);
    }

}
