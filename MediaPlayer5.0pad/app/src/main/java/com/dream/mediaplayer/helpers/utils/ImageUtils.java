package com.dream.mediaplayer.helpers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.helpers.lastfm.Album;
import com.dream.mediaplayer.helpers.lastfm.Artist;
import com.dream.mediaplayer.helpers.lastfm.Image;
import com.dream.mediaplayer.helpers.lastfm.PaginatedResult;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.widget.ImageView;

import static com.dream.mediaplayer.Constants.*;

public class ImageUtils {
	
    private static final String IMAGE_EXTENSION = ".img";
	
	private static File getFile(Context context, ImageInfo imageInfo){
		return new File(context.getExternalCacheDir(), createShortTag(imageInfo)+IMAGE_EXTENSION);
	}
	
	public static Bitmap getNormalImageFromDisk( Context context, ImageInfo imageInfo ){
		Bitmap bitmap = null;
		File nFile = getFile( context, imageInfo );
		if(nFile.exists()){
			bitmap = BitmapFactory.decodeFile( nFile.getAbsolutePath() );
		}		
		return bitmap;
	}
	
	public static Bitmap getThumbImageFromDisk( Context context, ImageInfo imageInfo, int thumbSize ){
		File nFile = getFile( context, imageInfo );
		return getThumbImageFromDisk( context, nFile, thumbSize );
	}
	
	public static Bitmap getThumbImageFromDisk( Context context, File nFile, int thumbSize ){
		if(!nFile.exists())
			return null;
		final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try{
        	BitmapFactory.decodeFile( nFile.getAbsolutePath() , options);
        }
        catch(Exception e){
        }
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > thumbSize || width > thumbSize) {
             if (width > height) {
                 inSampleSize = Math.round((float)height / (float)thumbSize);
             } else {
                 inSampleSize = Math.round((float)width / (float)thumbSize);
             }
             final float totalPixels = width * height;
             final float totalReqPixelsCap = thumbSize * thumbSize * 2;
             while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                 inSampleSize++;
             }
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
		try{
			bitmap = BitmapFactory.decodeFile( nFile.getAbsolutePath() , options );
		}
		catch (Exception e){
		}
        return  bitmap;
	}

	public static File getImageFromGallery( Context context, ImageInfo imageInfo ){		
		String albumArt = ( imageInfo.type == TYPE_ALBUM ) ? imageInfo.data[3] : imageInfo.data[1];	  
        if(albumArt != null){
        	InputStream in = null;
        	OutputStream out = null;
        	try{
        		File orgFile = new File(albumArt);
        		File newFile = new File(context.getExternalCacheDir(), createShortTag(imageInfo)+IMAGE_EXTENSION);
        		in = new FileInputStream(orgFile);
        		out = new FileOutputStream(newFile);
        		byte[] buf = new byte[1024];
        		int len;
        		while ((len = in.read(buf)) > 0){
        			out.write(buf, 0, len);
        		}
        		
	        	return newFile;
        	}
        	catch( Exception e){
        	} finally {
        		try {
        			if (in != null) {
        				in.close();
        			}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        return null;
	}
	
    public static File getImageFromWeb( Context context, ImageInfo imageInfo ) {
    	String imageUrl = null;
        try {
	        if( imageInfo.type.equals(TYPE_ALBUM) ){  
	        	Album image = Album.getInfo(imageInfo.data[1], imageInfo.data[2], LASTFM_API_KEY);
	            if (image != null) {
	            	imageUrl = image.getLargestImage();
	            }
	        }
	        else if ( imageInfo.type.equals(TYPE_ARTIST) ) {
		        PaginatedResult<Image> images = Artist.getImages(imageInfo.data[0], 2, 1, LASTFM_API_KEY);
	            Iterator<Image> iterator = images.getPageResults().iterator();
	            if (iterator.hasNext()) {
		            Image image = iterator.next();	
			        imageUrl = image.getLargestImage();
	            }
	        }
        } catch ( Exception e ) {
        	return null;
        }
        if ( imageUrl == null || imageUrl.isEmpty() ) {
            return null;
        }
        File newFile = getFile( context, imageInfo );
        ApolloUtils.downloadFile( imageUrl, newFile );
		if (newFile.exists()) {
            return newFile;
        }
        return null;
    }    

    public static File getImageFromMediaStore( Context context, ImageInfo imageInfo ){
    	String mAlbum = imageInfo.data[0];
    	String[] projection = {
                BaseColumns._ID, Audio.Albums._ID, Audio.Albums.ALBUM_ART, Audio.Albums.ALBUM
        };
        Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, projection, BaseColumns._ID+ "=" + DatabaseUtils.sqlEscapeString(mAlbum), null, null);
        if (cursor == null) {
        	return null;
        }
        
        int column_index = cursor.getColumnIndex(Audio.Albums.ALBUM_ART);
        if(cursor.getCount()>0){
	    	cursor.moveToFirst();
	        String albumArt = cursor.getString(column_index);	  
	        if(albumArt != null){
	        	File newFile = null;
	        	InputStream in = null;
	        	OutputStream out = null;
	        	try{
	        		File orgFile = new File(albumArt);
	        		newFile = new File(context.getExternalCacheDir(), createShortTag(imageInfo)+IMAGE_EXTENSION);
	        		in = new FileInputStream(orgFile);
	        		out = new FileOutputStream(newFile);
	        		byte[] buf = new byte[1024];
	        		int len;
	        		while ((len = in.read(buf)) > 0){
	        			out.write(buf, 0, len);
	        		}
	        		
	        		return newFile;
	        	} catch( Exception e){
	        	} finally {
	        		if (in != null) {
	        			try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		
	        		if (out != null) {
	        			try {
							out.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		
	        		cursor.close();
	        	}
	        }
        } else {
        	cursor.close();
        }
        
        return null;
    }
    
    public static void deleteDiskCache(Context context) throws IOException {
    	final File dir = context.getExternalCacheDir();
        final File[] files = dir.listFiles();
        for (final File file : files) {
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }
    
    public static String createShortTag(ImageInfo imageInf){
    	String tag = null;
    	if( imageInf.type.equals( TYPE_ALBUM ) ){
    		//album id + album suffix 
    		tag = imageInf.data[0] + ALBUM_SUFFIX;
    	}
    	else if (imageInf.type.equals( TYPE_ARTIST )){
    		//artist name + album suffix
    		tag = imageInf.data[0] + ARTIST_SUFFIX;
    	}
    	else if (imageInf.type.equals( TYPE_GENRE )){
    		//genre name + genre suffix
    		tag = imageInf.data[0] + GENRE_SUFFIX;
    	}
    	else if (imageInf.type.equals( TYPE_PLAYLIST )){
    		//genre name + genre suffix
    		tag = imageInf.data[0] + PLAYLIST_SUFFIX;
    	}
    	ApolloUtils.escapeForFileSystem(tag);
    	return tag;
    }
    
    public static void setLoadedBitmap(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transition = new TransitionDrawable(new Drawable[]{
                new ColorDrawable(android.R.color.transparent),
                new BitmapDrawable(imageView.getResources(), bitmap)
        });

        imageView.setImageDrawable(transition);
        final int duration = imageView.getResources().getInteger(R.integer.image_fade_in_duration);
        transition.startTransition(duration);
    }
}
