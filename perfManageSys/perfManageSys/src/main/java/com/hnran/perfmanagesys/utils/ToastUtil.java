package com.hnran.perfmanagesys.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast = null;
	/**
	 * 
	 * @param str
	 */
	public static void showToast(Context cnt, String str){
		if(mToast == null){
			mToast = Toast.makeText(cnt, str, Toast.LENGTH_SHORT);

		}else{
			mToast.setText(str);
		}
		mToast.show();
	}

	public static void cancelToast(Context cnt){
		if(mToast != null){
			mToast.cancel();
		}
	}
}
