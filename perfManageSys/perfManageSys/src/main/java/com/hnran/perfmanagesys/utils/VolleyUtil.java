package com.hnran.perfmanagesys.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hnran.perfmanagesys.activity.PMSApplication;

public class VolleyUtil{

    private static RequestQueue mRequestQueue ;

    public static void initialize(){
        if (mRequestQueue == null){
            synchronized (VolleyUtil.class){
                if (mRequestQueue == null){
                    mRequestQueue = Volley.newRequestQueue(PMSApplication.getApplication());
                }
            }
        }
    }

    public static RequestQueue getRequestQueue(){
        if (mRequestQueue == null)
            throw new RuntimeException("请先初始化mRequestQueue");
        return mRequestQueue ;
    }
    
    
    public static RequestQueue getInstanceRequestQueue(){
    	if (mRequestQueue == null){
            synchronized (VolleyUtil.class){
                if (mRequestQueue == null){
                    mRequestQueue = Volley.newRequestQueue(PMSApplication.getApplication());
                }
            }
        }
        return mRequestQueue ;
    }
    
    
    
    
    
}
