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
public class ViewHolderArtistAlbumList {

	public FadeImageView imageView;
	public TextView albumName;
	public TextView tracksCount;
	public View viewLine;
	
	public ViewHolderArtistAlbumList(View view) {
		imageView = (FadeImageView) view.findViewById(R.id.item_cover);
		albumName = (TextView) view.findViewById(R.id.album_name);
		tracksCount = (TextView) view.findViewById(R.id.track_count);
		viewLine = (View) view.findViewById(R.id.view_line);
	}
}
