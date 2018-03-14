package com.dream.mediaplayer.helpers.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class VaulePreference {

	SharedPreferences sharedPreferences;

	public VaulePreference(Context context) {
		sharedPreferences = context.getSharedPreferences("value_preference",
				Context.MODE_PRIVATE);
	}

	
	/**
	 * 
	 * @param context
	 * @param lrc_size
	 */
	public void savaLrcSize(Context context, int lrc_size) {
		sharedPreferences.edit().putInt("lrc_size", lrc_size).commit();
	}

	/**
	 * 
	 * @param context
	 */
	public int getLrcSize(Context context) {
		return sharedPreferences.getInt("lrc_size", 22);
	}

	/**
	 * 
	 * @param context
	 */
	public void savaLrcColor(Context context, int lrc_color) {
		sharedPreferences.edit().putInt("lrc_color", lrc_color).commit();
	}

	/**
	 * 
	 * @param context
	 */
	public int getLrcColor(Context context) {
		return sharedPreferences.getInt("lrc_color", Color.rgb(51, 181, 229));
	}
	
	/**
	 * 
	 * @param context
	 * @param pos
	 */
	public void savaGuidePosition(Context context, boolean pos) {
		sharedPreferences.edit().putBoolean("isStart", pos).commit();
	}
	public boolean getGuidePosition(Context context) {
		return sharedPreferences.getBoolean("isStart",true);
	}
	
	
	public void savaPlayState(Context context, boolean yes) {
		sharedPreferences.edit().putBoolean("is_play", yes).commit();
	}
	public boolean getPlayState(Context context) {
		return sharedPreferences.getBoolean("is_play",true);
	}
	
	
	public void savaShakePosition(Context context, boolean yes) {
		sharedPreferences.edit().putBoolean("is_shanksong", yes).commit();
	}
	public boolean getShakePosition(Context context) {
		return sharedPreferences.getBoolean("is_shanksong",false);
	}
	
	

	
}
