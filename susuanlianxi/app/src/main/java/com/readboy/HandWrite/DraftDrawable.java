package com.readboy.HandWrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DraftDrawable extends View {
    private Bitmap cacheBitmap;// 画纸
    private int i = 0;

    private Canvas cacheCanvas;// 创建画布、画家
    private Path path;// 绘图的路径
    public Paint paint;// 画笔
    private float preX, preY;// 之前的XY的位置，用于下面的手势移动
    private int view_width, view_height;
    //	private List<Bitmap> resourceBitmap = new ArrayList<Bitmap>();//存放画布id的list
    String sdpath = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+ "/t";;// 获取sdcard的根路径
    private int count = 0;
    public DraftDrawable(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        cacheCanvas = new Canvas();
        // 获取屏幕的高度与宽度
        view_width = context.getResources().getDisplayMetrics().widthPixels;
        view_height = context.getResources().getDisplayMetrics().heightPixels;
        cacheBitmap = Bitmap.createBitmap( view_width, view_height,
                Config.ARGB_8888);// 建立图像缓冲区用来保存图像
        cacheCanvas.setBitmap(cacheBitmap);
        paint.setColor(Color.argb(255, 255, 205, 0));// 设置画笔的默认颜色
        paint.setStyle(Paint.Style.STROKE);// 设置画笔的填充方式为无填充
        paint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {// 获取触摸的各个瞬间
            case MotionEvent.ACTION_DOWN:// 手势按下
                path.moveTo(x, y);// 绘图的起始点
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx > 5 || dy > 5) {// 用户要移动超过5像素才算是画图，免得手滑、手抖现象
                    path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                    cacheCanvas.drawPath(path, paint);// 绘制路径
                }
                break;
            case MotionEvent.ACTION_UP:
                path.reset();
                SaveBitmap t = new SaveBitmap();
                t.start();
                break;
        }
        invalidate();
        return true;
    }


    public void clearFile() {
        count=0;
        i=0;
        ClearBitmap w = new ClearBitmap();
        w.start();
        invalidate();
    }

    public void pickBitmap() throws Exception {
        if (i >0) {
            i=i-1;
            invalidate();
            String pathName = sdpath+File.separator + i+ ".png";
//			Toast.makeText(getContext(),
//					"图像已获取" + sdpath + File.separator + i + ".png",
//					Toast.LENGTH_SHORT).show();
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            cacheCanvas.drawPaint(paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            cacheCanvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }


    public void SaveFirstBitmap() {
        File file = new File(sdpath);
        if (!file.exists()){
            file.mkdirs();
            Log.e("3333333", "hi"+"");
        }
        File file2 = new File(sdpath +"/"+ 0 + ".png");
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// 以100%的品质创建png
            // 人走带门
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    class SaveBitmap extends Thread{

        public SaveBitmap(){

        }
        public void run(){
            i = i + 1;
            count++;
            File file = new File(sdpath + File.separator + i + ".png");
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// 以100%的品质创建png
                // 人走带门
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void pickBitmapRedo() throws Exception {

        if ((i+1) <= count ) {
            i = i + 1;
            String pathName = sdpath + "/"+ i + ".png";
//            Toast.makeText(getContext(),
//                    "图像已获取" + sdpath + File.separator + i + ".png",
//                    Toast.LENGTH_SHORT).show();
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            cacheCanvas.drawPaint(paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
//            cacheCanvas.drawColor(Color.argb(102, 0, 0, 0));
            cacheCanvas.drawBitmap(bitmap, 0, 0, paint);
            invalidate();
        }
    }

    public void ClearBitmap() {
        count=0;
        i=0;
        path.reset();
        paint.reset();
        invalidate();
        cacheCanvas.drawPath(path, new Paint());
        cacheCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        cacheCanvas = new Canvas();
        // 获取屏幕的高度与宽度
        cacheBitmap = Bitmap.createBitmap(view_width, view_height,
                Config.ARGB_8888);// 建立图像缓冲区用来保存图像
        cacheCanvas.setBitmap(cacheBitmap);
        paint.setColor(Color.argb(255, 255, 205, 0));// 设置画笔的默认颜色
        paint.setStyle(Paint.Style.STROKE);// 设置画笔的填充方式为无填充
        paint.setStrokeWidth(10);
    }


    class ClearBitmap extends Thread {

        public ClearBitmap() {

        }

        public void run() {
            count=0;
            i=0;
            File file = new File(sdpath);
            delete(file);

        }
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

}
