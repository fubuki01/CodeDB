/**
 * 
 */

package com.dream.mediaplayer.views;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.ui.widgets.VisualizerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author Andrew Neal
 */
public class ViewHolderArtistList {

	public FadeImageView imageView;
	public TextView music_artist;
	public TextView music_numOfAlbums;
	public TextView music_numOfTracks;
	public View lineView;
	
	
	public ViewHolderArtistList(View view) {
		imageView = (FadeImageView) view.findViewById(R.id.item_cover);
		music_artist = (TextView) view.findViewById(R.id.music_artist);
		music_numOfAlbums = (TextView) view.findViewById(R.id.music_numOfAlbums);
		music_numOfTracks = (TextView) view.findViewById(R.id.music_numOfTracks);
		lineView = (View) view.findViewById(R.id.view_line);
	}
}
