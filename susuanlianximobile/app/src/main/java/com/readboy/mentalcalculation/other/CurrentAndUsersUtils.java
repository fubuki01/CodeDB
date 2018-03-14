package com.readboy.mentalcalculation.other;

/*
  操作当前的用户和服务器中所有用户的信息
 */

import com.readboy.susuan.bean.UserInfos;

import java.util.List;

public class CurrentAndUsersUtils {
    public static UserInfos outcurrent;
    public static List<UserInfos> outusers;
    /*
    设置当前用户的引用
     */
    public static void setCurrent(UserInfos incurrent){
        outcurrent=incurrent;
    }
    public static UserInfos getCurrent(){
        return outcurrent;
    }

    /*
    设置所有用户信息的对象
     */
    public static void setUsers(List<UserInfos> inusers){
        outusers=inusers;
    }
    public static  List<UserInfos> getUsers(){
        return outusers;
    }
}
