
package com.hnran.perfmanagesys.utils;

import java.util.Map;

import com.hnran.perfmanagesys.activity.PMSApplication;

import android.util.Log;

public class MakeUrl {
	
//	public final static String IP = "39.108.81.73:8745/bank_pm"; //会同农商行2017-09-18 版本号1.0
//	public final static String IP = "39.106.49.154:8080/bank_pm"; //桂东农商行 2017-10-18 版本号1.0.2
//	public final static String IP = "120.79.26.50:8080/bank_pm"; //2017-11-24
//	public final static String IP = "222.244.144.166:38080/bank_pm";//测试环境

//	public final static String IP = "39.108.81.73:8080/bank_pm";//本地电脑
//	public final static String IPNUMBER = "39.108.81.73:8080";//本地电脑

	public final static String IP = "218.77.105.245:38080/bank_pm";//本地电脑
	public final static String IPNUMBER = "218.77.105.245:38080";//本地电脑

//	public final static String IP = "175.6.62.8:8081/bank_pm";//本地电脑
//	public final static String IPNUMBER = "175.6.62.8:8081";//本地电脑

//	public final static String IP = "123.207.87.47:8080/bank_pm";//本地电脑
//	public final static String IPNUMBER = "123.207.87.47:8080";//本地电脑
	public final static String URL = "http://" + IP ;
	public final static String HEAD = URL + "/mobile";
	
//	public final static String HEAD = "https://www.baidu.com";
	
	public static String makeURL(String[] indexs){
		String url = HEAD;
		for(String s : indexs){
			url = url + "/" + s;
		}	
		
		url += "?token="+PMSApplication.gToken;
		
		Log.d("MakeUrl", url);
		
		return url;
	}
	
	public static String makeURL(String[] indexs, Map<String, Object> paras){
		String header = HEAD;
		for(String s : indexs){
			header = header + "/" + s;
		}
		
		
//		int i = 0;
		String para = "?token="+PMSApplication.gToken;
		for (Map.Entry<String, Object> entry : paras.entrySet()) {  
			  
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		    
//			String str = "";
//			try {
//				if(entry.getValue() != null) str = entry.getValue().toString();
////				str = java.net.URLEncoder.encode(str, "UTF-8");
////				Log.d("tt",str);
////				String str2 = java.net.URLDecoder.decode(str);
////				Log.d("tt",str2);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			
//			if(i == 0){
//		    	para = para + "?" + entry.getKey() + "=" + entry.getValue();
//			}else{
//				para = para + "&" + entry.getKey() + "=" + entry.getValue();
//			}
		    
			para = para + "&" + entry.getKey() + "=" + entry.getValue();
			
//		    i++;  
		}  
		
		String url = header + para;
		Log.d("MakeUrl", url);
		
		return url;
	}
	
	

}
