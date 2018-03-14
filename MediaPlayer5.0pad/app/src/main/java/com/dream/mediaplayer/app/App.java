package com.dream.mediaplayer.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.util.Log;

import com.dream.mediaplayer.Constants;
import com.dream.mediaplayer.R;

public class App extends Application {
	private static App INSTANCE;
	private Bitmap thumbDefaultCover;
	
	private Bitmap normalDefaultCover;
	//shuffleAll click interval time
	public static long SHUFFLE_ALL_INTERVAL_TIME = 400;
	//save the shuffleAll Item's click time to avoid fast click
	public static long clickTimes[] = new long[3];

	//this method is to avoid mutiple click on shuffleAll button
	public static boolean isFastClickShuffleAll(){
		System.arraycopy(clickTimes,1,clickTimes,0,clickTimes.length-1);
		clickTimes[clickTimes.length-1] = SystemClock.uptimeMillis();
		if(clickTimes[2]-clickTimes[0] < SHUFFLE_ALL_INTERVAL_TIME){
			return true;
		}
		return false;
	}
	public static synchronized App getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		INSTANCE = this;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		
		if (thumbDefaultCover != null) {
			if (!thumbDefaultCover.isRecycled()) {
				thumbDefaultCover.recycle();
				thumbDefaultCover = null;
			}
		}
		
		if (normalDefaultCover != null) {
			if (!normalDefaultCover.isRecycled()) {
				normalDefaultCover.recycle();
				normalDefaultCover = null;
			}
		}
	}
	
	public Bitmap getThumbDefaultCover(String imageType) {
			if(imageType.equals(Constants.TYPE_ARTIST)){
				thumbDefaultCover = BitmapFactory.decodeResource(getResources(),
						R.drawable.artist_list_item_default_cover);
			}else if(imageType.equals(Constants.TYPE_ALBUM)){
				thumbDefaultCover = BitmapFactory.decodeResource(getResources(),
						R.drawable.track_list_item_default_cover);
			}
		return thumbDefaultCover;
	}
	
	public Bitmap getNormalDefaultCover(String imageType) {
		if (normalDefaultCover == null) {
			if(imageType.equals(Constants.TYPE_ALBUM)){
				normalDefaultCover = BitmapFactory.decodeResource(getResources(),
						R.drawable.track_list_item_default_big_cover);
			}

		}
		
		return normalDefaultCover;
	}
}
