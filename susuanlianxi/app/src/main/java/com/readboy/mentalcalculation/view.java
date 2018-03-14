package com.readboy.mentalcalculation;

import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class view extends View{
	private Paint mPaint; 
	String text="减法计算方法";
	Vector<Bitmap> bitmap;
	Bitmap bit_none;
	Bitmap bit_exist;
	boolean is_choose=false;

	public view(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public view(Context context, AttributeSet attrs) {
        super(context, attrs);
	      
		
		mPaint=new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(20);
		
		//新将数组，存放显示的星星
		bitmap=new Vector<Bitmap>(5);
		
		
		//获取星星的数据
		bit_none=BitmapFactory.decodeResource(getResources(), R.drawable.star_off);
		bit_exist=BitmapFactory.decodeResource(getResources(), R.drawable.star_on);
		
		//初始化
		for(int i=0;i<5;i++){
			bitmap.add(bit_none);
		}
		
		invalidate();
		
    }
	
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		
		 // 新建圆形矩阵
        RectF outerRect = new RectF(0, 0, 400,150);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(is_choose==true){
        	paint.setColor(Color.rgb( 251,116,175));
        }
        else{
        	paint.setColor(Color.rgb(193,210,240));
        }
        canvas.drawRoundRect(outerRect, 20, 20, paint);
		
        //矩阵上文字
       // float textWidth = mPaint.measureText(text);
		//canvas.drawText(text,outerRect.width()/2-textWidth/2 ,outerRect.height()/2-20, mPaint);
		
		
		/*通过StaticLayout来绘制文字的换行*/
		TextPaint tp = new TextPaint();
        tp.setColor(Color.BLACK);
        tp.setStyle(Style.FILL);
        tp.setTextSize(27);
		StaticLayout myStaticLayout = new StaticLayout(text, tp, canvas.getWidth(), Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
		canvas.save();
		//mPaint.setTextAlign(Paint.Align.LEFT);
		//文字的位置
		canvas.translate(outerRect.left, outerRect.top+20);
		myStaticLayout.draw(canvas);
		canvas.restore();
		
		
		
		
		//矩阵上的小星星
		for(int i=0;i<5;i++){
			canvas.drawBitmap(bitmap.elementAt(i), 180+(i-2)*40, outerRect.height()-70, new Paint());
		}
		
	}
	
	
	/*设置图标的文字*/
	public void setText(String text){
		this.text=text;
		invalidate();
	}
	
	
	/*显示星星个数*/
	public void setStarNum(int num){
		bitmap.clear();
		if(bitmap.isEmpty()){
			for(int i=0;i<num;i++){
				bitmap.add(bit_exist);
			}
			for(int i=num;i<5;i++){
				bitmap.add(bit_none);
			}
		}
		invalidate();
	}
	
	
	/*显示是否为粉色*/
	public void setColor(boolean is_pick){
		is_choose=is_pick;
		invalidate();
	}
	

}
