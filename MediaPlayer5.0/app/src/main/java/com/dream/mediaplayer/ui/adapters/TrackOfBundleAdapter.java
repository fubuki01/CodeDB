package com.dream.mediaplayer.ui.adapters;




import java.lang.ref.WeakReference;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.fragment.TracksOfBundleFragment;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.VisualizerUtils;
import com.dream.mediaplayer.ui.widgets.VisualizerView;
import com.dream.mediaplayer.views.TracksOfBunleListHolder;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;



/**
 * @author Andrew Neal
 */
@SuppressLint("NewApi")
public class TrackOfBundleAdapter extends SimpleCursorAdapter {

	private Context mContext;
	
    public TrackOfBundleAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        
        mContext = context;
    }
  
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);

        Cursor mCursor = (Cursor) getItem(position);
        mCursor.moveToPosition(position);
        // ViewHolderList
        TracksOfBunleListHolder viewholder;

        if (view != null) {
            viewholder = new TracksOfBunleListHolder(view);
            view.setTag(viewholder);
        } else {
            viewholder = (TracksOfBunleListHolder)convertView.getTag();
        }

        // Track name
        String trackName = mCursor.getString(TracksOfBundleFragment.mTitleIndex);
        String template = mContext.getString(R.string.tracks_of_bundle_title_text);
        String text = String.format(template,trackName);
        viewholder.music_title.setText("     "+text);
        
        // Duration
        int duration = mCursor.getInt(TracksOfBundleFragment.mDurationIndex);
        viewholder.music_duration.setText(MusicUtils.makeTimeString(mContext,
        		duration / 1000));

        // Now playing indicator
        long currentaudioid = MusicUtils.getCurrentAudioId();
        long audioid = mCursor.getLong(TracksOfBundleFragment.mMediaIdIndex);
        if (currentaudioid == audioid) {
        	viewholder.music_title.setTextColor(mContext.getResources().getColor(R.color.listview_item_textview_select_color));
        	viewholder.visualizerView.setVisibility(View.VISIBLE);
//			WeakReference<VisualizerView> mView = new WeakReference<VisualizerView>(viewholder.visualizerView);
//	        VisualizerUtils.updateVisualizerView(mView);
        	
        	viewholder.visualizerView.setImageResource(R.anim.visualizer_anim);
	        AnimationDrawable animationDrawable = (AnimationDrawable)viewholder.visualizerView.getDrawable();
	        try {
                if (MusicUtils.mService.isPlaying()) {
                	animationDrawable.start();
                } else {
                	animationDrawable.stop();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                viewholder.visualizerView.setVisibility(View.GONE);
    			viewholder.visualizerView.setImageResource(0);
            }
		} else {
			viewholder.music_title.setTextColor(mContext.getResources().getColor(R.color.listview_item_textview_unselect_color));
			
			viewholder.visualizerView.setVisibility(View.GONE);
			viewholder.visualizerView.setImageResource(0);
		}
        
        return view;
    }
}
