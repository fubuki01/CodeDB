package com.hnran.perfmanagesys.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wyd on 2018/1/3.
 */

public class BitmapUtil {
    private static final String TAG = "BitmapUtil";
    private static InputStream inputStream;

    public static Bitmap getBitmap(String path){
        URL url = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            Log.e(TAG, "getBitmap: url路径错误！");
        }

        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            Log.e(TAG, "getBitmap: 创建链接失败！");
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
