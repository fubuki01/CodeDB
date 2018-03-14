/**
 * 
 */

package com.dream.mediaplayer.ui.widgets;


import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.PlayActivity;
import com.dream.mediaplayer.helpers.utils.ImageUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Andrew Neal
 */
public class BottomActionBar extends RelativeLayout implements OnClickListener {
	
	private String tag = "BottomActionBar --- ";
	 
    public BottomActionBar(Context context) {
        super(context);
    }

    public BottomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public BottomActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Updates the bottom ActionBar's info
     * 
     * @param activity
     * @throws RemoteException
     */
    public void updateBottomActionBar(Activity activity) {
    	if (activity == null) {
    		return;
    	}
    	
        View bottomActionBar =activity.findViewById(R.id.name_content);
        if (bottomActionBar == null) {
            return;
        }
        
        bottomActionBar.setOnClickListener(this);

        try {
	        if (MusicUtils.mService != null/* && MusicUtils.getCurrentAudioId() != -1*/) {
	
	            // Track name
	            TextView mTrackName = (TextView)bottomActionBar
	                    .findViewById(R.id.bottombar_songname);
	            mTrackName.setText(MusicUtils.mService.getTrackName());
	
	            // Artist name
	            TextView mArtistName = (TextView)bottomActionBar
	                    .findViewById(R.id.bottombar_artistname);
	            String artistName = MusicUtils.mService.getArtistName();
	            if (MediaStore.UNKNOWN_STRING.equals(artistName)) {
	                artistName = activity.getString(R.string.unknown_artist_name);
	            }
	            mArtistName.setText(artistName);
	
	            // Album art
	           /* ImageView mAlbumArt = (ImageView)bottomActionBar
	                    .findViewById(R.id.bottombar_cover);
	            
	            try {
					Bitmap bitmap = MusicUtils.mService.getAlbumBitmap();
					if (bitmap == null) {
						bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bottom_bar_default_cover);
					} 
					ImageUtils.setLoadedBitmap(mAlbumArt, bitmap);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        }
        } catch (RemoteException e) {
        	
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.name_content:
            	
                Intent intent = new Intent();
                intent.setClass(v.getContext(), PlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }
}
