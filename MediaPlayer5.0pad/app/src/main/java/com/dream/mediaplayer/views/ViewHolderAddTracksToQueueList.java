/**
 * 
 */

package com.dream.mediaplayer.views;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.ui.widgets.VisualizerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author Andrew Neal
 */
public class ViewHolderAddTracksToQueueList {

	public ImageView checkBox;
	public FadeImageView imageView;
	public TextView music_title;
	public TextView music_Artist;
	public View lineView;
	
	
	public ViewHolderAddTracksToQueueList(View view) {
		checkBox = (ImageView) view.findViewById(R.id.checkbox);
		imageView = (FadeImageView) view.findViewById(R.id.item_cover);
		music_title = (TextView) view.findViewById(R.id.music_title);
		music_Artist = (TextView) view.findViewById(R.id.music_Artist);
		lineView = (View) view.findViewById(R.id.view_line);
	}
}
