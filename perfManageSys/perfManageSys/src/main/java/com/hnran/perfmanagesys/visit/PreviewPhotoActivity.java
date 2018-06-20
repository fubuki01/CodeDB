package com.hnran.perfmanagesys.visit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.platform.comapi.map.B;
import com.hnran.perfmanagesys.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Description：进行图片浏览,可删除
 * <p>
 * Created by Mjj on 2016/12/2.
 */

public class PreviewPhotoActivity extends Activity {

    private static final String TAG = "PreviewPhotoActivity";
    private FileInputStream fis = null;
    private static final int DELETE_PHOPTO_CODE = 3;
    // 返回图标
    private Button btBack;
    //删除图标
    private ImageView ivDelete;


    private ImageView imageView;
    private int itemId;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
    };
    private String type;
    private String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preview_photo);// 切屏到主界面
        btBack = (Button) findViewById(R.id.bt_back);
        ivDelete = (ImageView) findViewById(R.id.iv_photo_delete);
        btBack.setOnClickListener(new Backlistener());
        ivDelete.setOnClickListener(new DeleteListener());

        imageView = (ImageView) findViewById(R.id.iv_view_photo);
        /**
         * 显示图片
         */
        showPicture();

    }

    private void showPicture() {
        /**
         * 获取点击的图片并显示
         */
        Intent intent = getIntent();
//        byte[] fileImgs = intent.getByteArrayExtra("fileImg");
//        Bitmap bitmap = BitmapFactory.decodeByteArray(fileImgs, 0, fileImgs.length);
//        imageView.setImageBitmap(bitmap);
        path = intent.getStringExtra("path");
        type = intent.getStringExtra("type");
        if (type.equals("old")){
            itemId = intent.getIntExtra("itemId",-2);
        }

        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        Message msg = new Message();
        msg.what = 1;
        msg.obj = bitmap;
        handler.sendMessage(msg);

    }


    // 返回按钮的监听
    private class Backlistener implements OnClickListener {
        public void onClick(View v) {
            finish();
        }
    }

    /**
     * 点击删除按钮监听事件
     */
    private class DeleteListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            if (type.equals("old")){
                //列表中的
                intent.putExtra("itemId",itemId);
            }else {
                //新拍摄的
                intent.putExtra("path",path);
            }
            setResult(DELETE_PHOPTO_CODE,intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
