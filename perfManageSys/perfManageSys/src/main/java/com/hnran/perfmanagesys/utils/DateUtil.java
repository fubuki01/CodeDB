package com.hnran.perfmanagesys.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
	/**
	 * 根据跟定格式得到当前时间 yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @return 
	 */
	public static String getCurrentTime(String format){
		//使用默认时区和语言环境获得一个日历
		Calendar cale = Calendar.getInstance();
		//将Calendar类型转换成Date类型
		Date tasktime=cale.getTime();
		//设置日期输出的格式
		SimpleDateFormat df=new SimpleDateFormat(format);
		//格式化输出
		return df.format(tasktime);
	}
	
	/**
	 * 根据跟定格式得到当前时间 yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @return 
	 */
	public static String getCurrentTime(){
		//使用默认时区和语言环境获得一个日历
		Calendar cale = Calendar.getInstance();
		//将Calendar类型转换成Date类型
		Date tasktime=cale.getTime();
		//设置日期输出的格式
		SimpleDateFormat df=new SimpleDateFormat("HH:mm:ss");
		//格式化输出
		return df.format(tasktime);
	}
	
	
	/**
	 * 根据跟定格式得到当前时间 yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @return 
	 */
	public static String format(Date date,String format){
		//设置日期输出的格式
		SimpleDateFormat df=new SimpleDateFormat(format);
		//格式化输出
		return df.format(date);
	}
	
	/**
	 * 根据跟定格式得到当前时间 yyyy-MM-dd HH:mm:ss
	 * @param format
	 * @return 
	 */
	public static String format(Date date){
		if(date == null) return "";
		String format = "yyyy-MM-dd";
		//设置日期输出的格式
		SimpleDateFormat df=new SimpleDateFormat(format);
		//格式化输出
		return df.format(date);
	}
	
	/**
	 * 比较时间大小
	 * @param str
	 * @return
	 */
	public static boolean compareDate(String str){
		
		String crt = getCurrentTime("yyyy-MM-dd");
		int r = str.compareTo(crt);
		
		return r > 0 ? true : false;
	}
	
	/**
	 * 得到当前月份的第一天
	 * @param str
	 * @return
	 */
	public static String getCurrentMonthFirstDay(String str){
		String firstday = "";

		Calendar cale = Calendar.getInstance();
		cale.setTime(format2date(str, "yyyy-MM-dd"));
		cale.add(Calendar.MONTH, 0);  
		cale.set(Calendar.DAY_OF_MONTH, 1);  
		firstday = format(cale.getTime());

		return firstday;
	}
	
	/**
	 * 得到当前月份的最后一天
	 * @param str
	 * @return
	 */
	public static String getCurrentMonthLastDay(String str){
		String lastday = "";

		Calendar cale = Calendar.getInstance();
		cale.setTime(format2date(str, "yyyy-MM-dd"));
		cale.add(Calendar.MONTH, 1);  
		cale.set(Calendar.DAY_OF_MONTH, 0);  
		lastday = format(cale.getTime());

		return lastday;
	}
	
	
	
	/**
	 * 得到当年的第一天
	 * @param str
	 * @return
	 */
	public static String getCurrentYearFirstDay(String str){
		String lastday = "";

		Calendar cale = Calendar.getInstance();
		cale.setTime(format2date(str, "yyyy-MM-dd"));
		cale.add(Calendar.YEAR, 0);  
		cale.set(Calendar.DAY_OF_YEAR, 1);  
		lastday = format(cale.getTime());

		return lastday;
	}
	
	/**
	 * 得到当年的最后一天
	 * @param str
	 * @return
	 */
	public static String getCurrentYearLastDay(String str){
		String lastday = "";

		Calendar cale = Calendar.getInstance();
		cale.setTime(format2date(str, "yyyy-MM-dd"));
		cale.add(Calendar.YEAR, 1);  
		cale.set(Calendar.DAY_OF_YEAR, 0);  
		lastday = format(cale.getTime());

		return lastday;
	}
	
	
	/**
	 * 将字符形式的时间转换为Date类型
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date format2date(String str, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try{
			date = sdf.parse(str);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return date;
	}
	
}
