/**
 * 
 */

package com.dream.mediaplayer.views;

import com.dream.mediaplayer.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author Andrew Neal
 */
public class ViewHolderGrid {

    public final ImageView mViewHolderImage;

    public final TextView mViewHolderLineOne;

    public final TextView mViewHolderLineTwo;


    public ViewHolderGrid(View view) {
        mViewHolderImage = (ImageView)view.findViewById(R.id.albumCover);
        mViewHolderLineOne = (TextView)view.findViewById(R.id.albumName);
        mViewHolderLineTwo = (TextView)view.findViewById(R.id.artistName);
    }

}
