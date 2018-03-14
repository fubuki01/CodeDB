/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.dream.mediaplayer.helpers.visualizer;


import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;

public class BarGraphRenderer extends Renderer
{
  private Context mContext = null;
  
  private int[] mData = null;
  
  private int num = 3;
  
  private Paint paint;

  public BarGraphRenderer(Context context)
  {
    super();
    mContext = context;
    mData = new int[num];
    for (int i = 0; i < num; i++) {
    	mData[i] = 0;
    }
    
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(mContext.getResources().getColor(R.color.listview_item_textview_select_color));
  }

  @Override
  public void onRender(Canvas canvas, AudioData data, Rect rect)
  {
    // Do nothing, we only display FFT data
  }

  /**
   * Renders a 14 line bar graph/ histogram of the FFT data
   */
  @Override
  public void onRender(Canvas canvas, FFTData data, Rect rect)
  {
	  //space between lines of graph  
//	  float space = 4f;
	  float space = ApolloUtils.dp2px(mContext, 1.4f);

	  Resources resources = mContext.getResources();
//	  NinePatchDrawable bg =  (NinePatchDrawable) resources.getDrawable(R.drawable.bar_graph);
	  DisplayMetrics metrics = resources.getDisplayMetrics();
	  //margin from left/right edges
//	  int margin = (int) ( ( 16 * (metrics.densityDpi/160f) ) + 0.5f );
	  int margin = ApolloUtils.dp2px(mContext, 16);
  
	  //Calculate width of each bar
	  float bar_width = ( ( rect.width() - (((num-1) * space) + (margin * 2)) ) / num );
	  //calculate length between the start of each bar
//	  float next_start = bar_width + space;//show align_center
	  float next_start = rect.width()-((num-1)*space+num*bar_width);//show align_right
	  
	  for (int i = 0; i < num; i++) {
			//set x start of bar
//			float x1 = margin + (i * next_start);//show align_center
		  float x1 = next_start+i*(space+bar_width);//show align_right
		
			//calculate height of bar based on sampling 4 data points
			byte rfk = data.bytes[ (10 * i)];
			byte ifk = data.bytes[ (10 * i + 1)];
			float magnitude = (rfk * rfk + ifk * ifk);
			int dbValue = (int) (10 * Math.log10(magnitude));
			rfk = data.bytes[ (10 * i + 2)];
			ifk = data.bytes[ (10 * i + 3)];
			magnitude = (rfk * rfk + ifk * ifk);
			dbValue = (int) ( (10 * Math.log10(magnitude)) + dbValue) / 2;
		
			//Average with previous bars value(reduce spikes / smoother transitions)
			dbValue =( mData[i] +  ((dbValue < 0) ? 0 : dbValue) ) / 2;
			mData[i] = dbValue;
		
			//only jump height on multiples of 5
			if(dbValue >= 5)
				dbValue = (int) Math.floor(dbValue/5) * 5;
		
			//bottom edge of canvas
			float y1 = rect.height();
//			int blockHeight = 10;
			int blockHeight = ApolloUtils.dp2px(mContext, 4.5f);
			int numBlocks = (int) Math.floor((dbValue * 1.7f) / blockHeight);
			//cycle through and render individual blocks
			for( int j = 0; j < numBlocks; j++ ){
					int yEnd = (int)( y1 - ( blockHeight * j ));
//					Rect nRect = new Rect((int)x1, yEnd - blockHeight, (int)(x1+bar_width), yEnd);
//					bg.setBounds(nRect);
//					bg.draw(canvas);
					
					if (j == numBlocks-1) {
						paint.setAlpha(150);
					} else if (j == numBlocks-2) {
						paint.setAlpha(190);
					} else if (j == numBlocks-3) {
						paint.setAlpha(230);
					} else {
						paint.setAlpha(250);
					}
					
					float cx = x1+bar_width/2;
					float cy = yEnd-blockHeight/2;
					float radius = bar_width/2f; 
					canvas.drawCircle(cx, cy, radius, paint);
					
			}			
	  }
  }
}
