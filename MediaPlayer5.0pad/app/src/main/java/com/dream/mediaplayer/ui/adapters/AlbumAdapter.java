
package com.dream.mediaplayer.ui.adapters;

import static com.dream.mediaplayer.Constants.SIZE_NORMAL;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import java.lang.ref.WeakReference;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.cache.ImageProvider;
import com.dream.mediaplayer.fragment.AlbumsFragment;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.views.ViewHolderAddTracksToQueueList;
import com.dream.mediaplayer.views.ViewHolderGrid;

/**
 * @author Andrew Neal
 */
public class AlbumAdapter extends SimpleCursorAdapter {

//    private AnimationDrawable mPeakOneAnimation, mPeakTwoAnimation;
	
	private WeakReference<ViewHolderGrid> holderReference;

    private Context mContext;
    
    private ImageProvider mImageProvider;

    public AlbumAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    	mContext = context;
    	mImageProvider = ImageProvider.getInstance( (Activity) mContext );
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//    	Log.e("AlbumAdapter --- ", "position = "+position);
        final View view = super.getView(position, convertView, parent);

        Cursor mCursor = (Cursor) getItem(position);
        // ViewHolderGrid
        final ViewHolderGrid viewholder;

        if (view != null) {
            viewholder = new ViewHolderGrid(view);
//            view.setTag(viewholder);
            
            holderReference = new WeakReference<ViewHolderGrid>(viewholder);
            view.setTag(holderReference.get());
        } else {
            viewholder = (ViewHolderGrid)convertView.getTag();
        }
        

        // Album name
        String albumName = mCursor.getString(AlbumsFragment.mAlbumNameIndex);
        viewholder.mViewHolderLineOne.setText(albumName);

        // Artist name
        String artistName = mCursor.getString(AlbumsFragment.mArtistNameIndex);
        
        //NumberOfSongs
        int numberOfSongs = mCursor.getInt(AlbumsFragment.mNumberOfSongsIndex);
        String template = mContext.getString(R.string.gridview_artist_text);
        String text = String.format(template, artistName, numberOfSongs);
        viewholder.mViewHolderLineTwo.setText(text);
        
        // Album ID
        String albumId = mCursor.getString(AlbumsFragment.mAlbumIdIndex);

        ImageInfo mInfo = new ImageInfo();
        mInfo.type = TYPE_ALBUM;
        mInfo.size = SIZE_NORMAL;
        mInfo.source = SRC_FIRST_AVAILABLE;
        mInfo.data = new String[]{ albumId , artistName, albumName };
        
        mImageProvider.loadImage( holderReference.get().mViewHolderImage, mInfo );
//        mImageProvider.loadImage( viewholder.mViewHolderImage, mInfo );

        // Now playing indicator
        long currentalbumid = MusicUtils.getCurrentAlbumId();
        long albumid = mCursor.getLong(AlbumsFragment.mAlbumIdIndex);
        if (currentalbumid == albumid) {
//            holderReference.get().mPeakOne.setImageResource(R.anim.peak_meter_1);
//            holderReference.get().mPeakTwo.setImageResource(R.anim.peak_meter_2);
//            mPeakOneAnimation = (AnimationDrawable)holderReference.get().mPeakOne.getDrawable();
//            mPeakTwoAnimation = (AnimationDrawable)holderReference.get().mPeakTwo.getDrawable();
            try {
                if (MusicUtils.mService.isPlaying()) {
//                    mPeakOneAnimation.start();
//                    mPeakTwoAnimation.start();
                } else {
//                    mPeakOneAnimation.stop();
//                    mPeakTwoAnimation.stop();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
//            holderReference.get().mPeakOne.setImageResource(0);
//            holderReference.get().mPeakTwo.setImageResource(0);
        }
        return view;
    }
}
