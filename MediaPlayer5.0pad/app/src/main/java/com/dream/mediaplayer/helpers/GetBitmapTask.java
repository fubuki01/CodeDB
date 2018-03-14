package com.dream.mediaplayer.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.dream.mediaplayer.app.App;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.helpers.utils.Blur;
import com.dream.mediaplayer.helpers.utils.ImageUtils;

import java.io.File;
import java.lang.ref.WeakReference;

import static com.dream.mediaplayer.Constants.SIZE_NORMAL;
import static com.dream.mediaplayer.Constants.SIZE_THUMB;
import static com.dream.mediaplayer.Constants.SRC_FILE;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.SRC_GALLERY;
import static com.dream.mediaplayer.Constants.SRC_LASTFM;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;
import static com.dream.mediaplayer.Constants.TYPE_ARTIST;

public class GetBitmapTask extends AsyncTask<String, Integer, Bitmap> {

	private String tag = "GetBitmapTask --- ";
    
    private WeakReference<OnBitmapReadyListener> mListenerReference;

    private WeakReference<Context> mContextReference;

    private ImageInfo mImageInfo;

    private int mThumbSize;


    public GetBitmapTask( int thumbSize, ImageInfo imageInfo, OnBitmapReadyListener listener, Context context) {
        mListenerReference = new WeakReference<OnBitmapReadyListener>(listener);
        mContextReference = new WeakReference<Context>(context);
        mImageInfo = imageInfo;
    	mThumbSize = thumbSize;
    }

    @Override
    protected Bitmap doInBackground(String... ignored) {
        Context context = mContextReference.get();
        if (context == null) {
            return null;
        }
        //Get bitmap from proper source
        File nFile = null;
        
        if( mImageInfo.source.equals(SRC_FILE)  && !isCancelled()){
        	nFile = ImageUtils.getImageFromMediaStore( context, mImageInfo );
        }
        else if ( mImageInfo.source.equals(SRC_LASTFM)  && !isCancelled()){
        	nFile = ImageUtils.getImageFromWeb( context, mImageInfo );
        }
        else if ( mImageInfo.source.equals(SRC_GALLERY)  && !isCancelled()){
        	nFile = ImageUtils.getImageFromGallery( context, mImageInfo );
        }        	
        else if ( mImageInfo.source.equals(SRC_FIRST_AVAILABLE)  && !isCancelled()){
        	Bitmap bitmap = null;
        	if( mImageInfo.size.equals( SIZE_NORMAL ) ){
        		bitmap = ImageUtils.getNormalImageFromDisk( context, mImageInfo );
        	}
        	else if( mImageInfo.size.equals( SIZE_THUMB ) ){
        		bitmap = ImageUtils.getThumbImageFromDisk( context, mImageInfo, mThumbSize );
        	}
        	//if we have a bitmap here then its already properly sized
        	if( bitmap != null ){
        		if (mImageInfo.isBlur == true) {
	        		Bitmap bitmapBlur = Blur.fastblur(context, bitmap, 25);
	        		mImageInfo.bitampBlur = bitmapBlur;
        		}
        		return bitmap;
        	}
        	
        	if( mImageInfo.type.equals( TYPE_ALBUM ) ){
        		nFile = ImageUtils.getImageFromMediaStore( context, mImageInfo );
        	}
//        	if( nFile == null && ( mImageInfo.type.equals( TYPE_ALBUM ) || mImageInfo.type.equals( TYPE_ARTIST ) ) )
//        		nFile = ImageUtils.getImageFromWeb( context, mImageInfo );
        	
        	if( nFile == null && ( /*mImageInfo.type.equals( TYPE_ALBUM ) || */mImageInfo.type.equals( TYPE_ARTIST ) ) ) {
//        		nFile = ImageUtils.getImageFromWeb( context, mImageInfo );
//        		Log.e(tag, "doInBackground() --- nFile = "+nFile);
        	}
        }
        if( nFile != null ){        	
        	// if requested size is normal return it
        	if( mImageInfo.size.equals( SIZE_NORMAL ) && mImageInfo.isBlur == false) {
        		return BitmapFactory.decodeFile(nFile.getAbsolutePath());
        	} else if (mImageInfo.size.equals( SIZE_NORMAL ) && mImageInfo.isBlur == true) {
        		Bitmap bitmapTemp = BitmapFactory.decodeFile(nFile.getAbsolutePath());
        		if (bitmapTemp != null) {
	        		Bitmap bitmapBlur = Blur.fastblur(context, bitmapTemp, 25);
	        		mImageInfo.bitampBlur = bitmapBlur;
        		}
        		return bitmapTemp;
        	}
        	//if it makes it here we want a thumbnail image
        	if (mImageInfo.isBlur == false) {
        		return ImageUtils.getThumbImageFromDisk( context, nFile, mThumbSize );
        	} else if (mImageInfo.isBlur == true) {
        		Bitmap bitmapTemp = ImageUtils.getThumbImageFromDisk( context, nFile, mThumbSize );
        		if (bitmapTemp != null) {
	        		Bitmap bitmapBlur = Blur.fastblur(context, bitmapTemp, 25);
	        		mImageInfo.bitampBlur = bitmapBlur;
        		}
        		return bitmapTemp;
        	}
        }
        return null;
    }
    
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        OnBitmapReadyListener listener = mListenerReference.get();
        boolean isDefault = false;
        if(bitmap == null && !isCancelled()){
        	isDefault = true;
        	
        	Context context = mContextReference.get();
        	if (context != null) {
	        	if(mImageInfo.size.equals(SIZE_THUMB)) {
//	        		bitmap = BitmapFactory.decodeResource(context.getResources(),
//	        													R.drawable.track_list_item_default_cover);
					bitmap = App.getInstance().getThumbDefaultCover(mImageInfo.type);
	        	} else if(mImageInfo.size.equals(SIZE_NORMAL)) {
//	        		bitmap = BitmapFactory.decodeResource(context.getResources(),
//	        													R.drawable.track_list_item_default_big_cover);
	        		bitmap = App.getInstance().getNormalDefaultCover(mImageInfo.type);
	        	}
        	}
        }
        if (/*bitmap != null && */!isCancelled()) {
            if (listener != null) {
                listener.bitmapReady(bitmap,  ImageUtils.createShortTag(mImageInfo) + mImageInfo.size, isDefault, mImageInfo.bitampBlur);
            }
        }
    }

    public static interface OnBitmapReadyListener {
        public void bitmapReady(Bitmap bitmap, String tag, boolean isDefault, Bitmap bitmapBlur);
    }
}
