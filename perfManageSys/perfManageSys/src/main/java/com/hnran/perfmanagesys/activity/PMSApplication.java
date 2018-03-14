package com.hnran.perfmanagesys.activity;

import com.baidu.mapapi.SDKInitializer;
import com.readboy.ssm.po.User;

import android.app.Application;
import android.content.Context;

public class PMSApplication extends Application{

	private static PMSApplication mPMSApplication;
	
	public static User gUser;
	public static String gToken = "";
	
	public static boolean isLogin = false;

	public void onCreate() {
		super.onCreate();
		mPMSApplication = this;
		SDKInitializer.initialize(getApplicationContext());
	}

	public static PMSApplication getApplication() {

		return mPMSApplication;
	}

	public static Context getGlobalContext() {

		return mPMSApplication;
	}
	
}
