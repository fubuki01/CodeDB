package com.dream.mediaplayer.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.dream.mediaplayer.R;

/**
 * 设置bitmap时会显示淡入淡出的动画的ImageView
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 14-2-27
 * Time: 下午5:43
 */
public class FadeImageView extends ImageView{
    private AlphaAnimation alphaOut;
    private AlphaAnimation alphaIn;

    private Paint paint;
    private int roundWidth = 5;
    private int roundHeight = 5;
    private Paint paint2;

    private boolean isAnimEnd = true;

    private Context context;
    public FadeImageView(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public FadeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public FadeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(context, attrs);
    }

    private AlphaAnimation getAlphaOut(){
        if(null != alphaOut){
            return alphaOut;
        }
        alphaOut = new AlphaAnimation(1.0f, 0f);
        alphaOut.setFillAfter(true);
        alphaOut.setDuration(200);
        return alphaOut;
    }

    private AlphaAnimation getAlphaIn(){
        if(null != alphaIn){
            return alphaIn;
        }
        alphaIn = new AlphaAnimation(0f, 1.0f);
        alphaIn.setFillAfter(true);
        alphaIn.setDuration(200);
        return alphaIn;
    }

    public void setImageBitmapAnim(final Bitmap bm) {
//        super.setImageBitmap(bm);
        getAlphaOut().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setImageDrawable(new BitmapDrawable(context.getResources(), bm));
                startAnimation(getAlphaIn());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.startAnimation(getAlphaOut());

    }
    
    public void setVisiblityVisible() {
    	if (!isAnimEnd) {
//    		return;
    	}
    	
    	isAnimEnd = false;
    	
    	setVisibility(View.VISIBLE);
//    	setVisibilityOfElements(View.VISIBLE);
    	
    	getAlphaIn().setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				isAnimEnd = true;
		    	
		    	invalidate();
			}
		});
    	this.startAnimation(getAlphaIn());
    }
    
    public void setVisiblityGone() {
    	if (!isAnimEnd) {
//    		return;
    	}
    	
    	isAnimEnd = false;
    	
    	setVisibility(View.INVISIBLE);
//    	setVisibilityOfElements(View.INVISIBLE);
    	
    	getAlphaOut().setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				isAnimEnd = true;
//				setVisibilityOfElements(View.INVISIBLE);
		    	
		    	invalidate();
			}
		});
    	this.startAnimation(getAlphaOut());
    }

    /**
     * 以下代码用于绘制矩形框的圆角
     */

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView);
            roundWidth = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundWidth, roundWidth);
            roundHeight = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_roundHeight, roundHeight);
        } else {
            float density = context.getResources().getDisplayMetrics().density;
            roundWidth = (int) (roundWidth * density);
            roundHeight = (int) (roundHeight * density);
        }

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLiftUp(canvas2);
        drawLiftDown(canvas2);
        drawRightUp(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, roundHeight);
        path.lineTo(0, 0);
        path.lineTo(roundWidth, 0);
        path.arcTo(new RectF(0, 0, roundWidth * 2, roundHeight * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - roundHeight);
        path.lineTo(0, getHeight());
        path.lineTo(roundWidth, getHeight());
        path.arcTo(new RectF(0, getHeight() - roundHeight * 2, roundWidth * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - roundWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - roundHeight);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, getHeight() - roundHeight * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), roundHeight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundWidth, 0);
        path.arcTo(new RectF(getWidth() - roundWidth * 2, 0, getWidth(), 0 + roundHeight * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }
}
