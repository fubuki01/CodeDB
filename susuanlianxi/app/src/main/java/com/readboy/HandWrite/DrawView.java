package com.readboy.HandWrite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.readboy.game.Watched;
import com.readboy.game.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.x;


/**
 * point：记录点的坐标的集合，作为识别的参数
 * path：记录了画图的路径
 *
 * @author wbc
 */

public class DrawView extends View implements Watched {
    float preX;
    float preY;
    private Path path;
    public Paint paint = null;
    final int VIEW_WIDTH = 1;
    final int VIEW_HEIGHT = 1;
    public Timer timer;
    Context context_this;
    private List<Watcher> list = new ArrayList<Watcher>();
    public List<Short> points = new ArrayList<Short>();
    // 瀹氫箟涓?涓唴瀛樹腑鐨勫浘鐗囷紝璇ュ浘鐗囧皢浣滀负缂撳啿鍖?
    Bitmap cacheBitmap = null;
    // 瀹氫箟cacheBitmap涓婄殑Canvas瀵硅薄
    Canvas cacheCanvas = null;

    public DrawView(Context context, AttributeSet set) {
        super(context, set);
        context_this = context;
        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT,
                Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.argb(255, 0, 149, 255));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setDither(true);
//		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));


    }


    /**
     * 监听点击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 鑾峰彇鎷栧姩浜嬩欢鐨勫彂鐢熶綅缃?
        float x = event.getX();
        float y = event.getY();

        //改变touch的值
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                points.add((short) x);
                points.add((short) y);
                if (timer != null)
                    timer.cancel();
                Log.i("myinfo", x + "ddddddddd" + y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;

                points.add((short) x);
                points.add((short) y);
                Log.i("myinfo", x + "mmmmmmmm" + y + ":" + preX + ":" + preY);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint); // 鈶?
                points.add((short) -1);
                points.add((short) -1);
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        notifyWatcher();
                    }
                }, 2000);
                Log.i("myinfo", x + "uuuuuuuuu" + y);
                break;
        }
        invalidate();//重新绘制图片
        return true;
    }


    /**
     * 绘制图像
     */
    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
//		Log.i("mentalwubingchao", "on draw(drawview)")
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap, 0, 0, bmpPaint);
        canvas.drawPath(path, paint);
    }


    /**
     * 閲嶇疆鐢荤瑪
     */
    public void reSetPath() {
        path.reset();
        points.clear();
    }

    final Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //handler澶勭悊娑堟伅
            if (msg.what > 0) {
                // tv1.setText("" + msg.what);   
            } else {
                //鍦╤andler閲屽彲浠ユ洿鏀筓I缁勪欢
//                 tv1.setText("鐐圭伀锛?");   
                //timer.cancel();
            }
        }
    };

    public void clearScreen() {
        invalidate();
    }

    @Override
    public void add(Watcher watcher) {
        // TODO Auto-generated method stub
        list.add(watcher);
    }

    @Override
    public void remove(Watcher watcher) {
        // TODO Auto-generated method stub
        list.remove(watcher);
    }

    @Override
    public void notifyWatcher() {
        for (Watcher watcher : list) {
            watcher.updateNotify(this);
        }
    }

    public void timeCancel() {
        timer.cancel();
        timer = null;
    }
}
