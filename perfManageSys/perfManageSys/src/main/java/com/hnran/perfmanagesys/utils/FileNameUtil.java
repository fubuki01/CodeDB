package com.hnran.perfmanagesys.utils;

import java.util.Random;

/**
 * 生成文件名称工具类
 * Created by Wyd on 2017/12/9.
 */
public class FileNameUtil {
    /**
     * 生成文件名称工具类
     * 生成方法：当前时间戳+6位随机数
     * synchronized:防止多线程冲突
     * @return 生成的主键
     */
    public synchronized static String genUniqueFileName(){
        /*生成随机数*/
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(randomNumber);
    }
}
