package com.hnran.perfmanagesys.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	private static final String TAG = "FileUtil";

	/**
	 * 检查SD卡是否存在
	 *
	 * @return
	 */
	public static boolean checkSDCard() {
		final String status = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(status)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取安装在用户手机上的应用包路径名下的files目录
	 *
	 * @return files path
	 */
	public static String getAppFilesDir(Context context) {
		return context.getFilesDir().getPath();
	}

	/**
	 * 获取安装在用户手机上的应用包路径名下的cache目录
	 *
	 * @return cache path
	 */
	public static String getAppCacheDir(Context context) {
		return context.getCacheDir().getPath();
	}

	public static String getExternalFileDir(Context context){
		return context.getExternalCacheDir().getPath();
	}

	/**
	 * 保存拍摄的照片
	 * @param context 上下问
	 * @param bitmap 需要保存的照片
	 * @param name 以拍摄时间为名字
	 */
	public static String saveBitmap(Context context , Bitmap bitmap , String name){
		File file = null;
		String path = "";
		if(name != null){
			name = name.replace(" ", "").replace(":", "");
			if(checkSDCard()){
				path = getExternalFileDir(context);
			}else{
				path = getAppFilesDir(context);
			}
			file = new File(path, name+".png");
			FileOutputStream out = null;
			try {

				out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return file.getAbsolutePath();

	}

	public static Uri createVideoUri(Context context , String name){
		File file = null;
		name = name.replace(" ", "").replace(":", "");
		if(checkSDCard()){
			file = new File(getExternalFileDir(context),name+".mp4");
		}else{
			file = new File(getAppFilesDir(context),name+".mp4");
		}

		try {
			if (!file.exists()) {
				file.createNewFile();
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Uri.fromFile(file);
	}

	/**
	 * 照片存储到本地SD卡
	 */

	public static File savePicture(Context context,String name){
		File file = null;
		String path = "";
		if(name != null) {
			name = name.replace(" ", "").replace(":", "");
			if (checkSDCard()) {
				path = Environment.getExternalStorageDirectory().getPath()+"/perfManageSys/";
			} else {
				path = getAppFilesDir(context);
			}
			File f = new File(path);
			if (!f.exists()){
				f.mkdirs();//创建文件夹
			}
			file = new File(path, name + ".png");
			return file;
		}
		return null;
	}


	/**
	 * 存储照片到app应用中
	 * @param context
	 * @param name
	 * @return
	 */
	public static File savePictureInApp(Context context,String name){
		File file = null;
		String path = "";
		if(name != null) {
			path = getAppFilesDir(context);
			file = new File(path, name + ".png");
			return file;
		}
		return null;
	}



}
