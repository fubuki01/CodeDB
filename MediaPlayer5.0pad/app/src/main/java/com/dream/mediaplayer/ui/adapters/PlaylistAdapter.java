/**
 * 
 */

package com.dream.mediaplayer.ui.adapters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dream.mediaplayer.fragment.PlaylistsFragment;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.helpers.utils.SortCursor.SortEntry;
import com.dream.mediaplayer.views.ViewHolderList;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;

/**
 * @author Andrew Neal
 */
public class PlaylistAdapter extends SimpleCursorAdapter implements SectionIndexer {

    private Context mContext;
    
	private int[] mSectionIndices;
	private Character[] mSectionLetters;

	/**
	 */
	private Map<String, Integer> mIndexer = new HashMap<String, Integer>();

	private ArrayList<SortEntry> sortList;
    public PlaylistAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        mContext = context;
    }

	@Override
	public void changeCursor(Cursor cursor) {
		// TODO Auto-generated method stub
		super.changeCursor(cursor);

		if (cursor != null) {
			sortList = ((SortCursor) cursor).getSortList();
			mIndexer.clear();

			getSectionIndices();
		}
	}
	
	@Override
	public Cursor swapCursor(Cursor c) {
		// TODO Auto-generated method stub
		if (c != null) {
			sortList = ((SortCursor) c).getSortList();
			mIndexer.clear();

			getSectionIndices();
		}
		
		return super.swapCursor(c);
	}

	public Map<String, Integer> getIndexer() {
		return mIndexer;
	}

	private void getSectionIndices() {
		if (sortList.size() == 0) {
			return;
		}

		ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
		if (sortList.size() > 0) {
			String[] alphatableb = { "#", "A", "B", "C", "D", "E", "F", "G",
					"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
					"T", "U", "V", "W", "X", "Y", "Z" };

			ArrayList<String> formatList = new ArrayList<String>();
			for (int i = 0; i < sortList.size(); i++) {
				formatList.add(sortList.get(i).key);
			}

			for (int i = 0; i < alphatableb.length; i++) {
				int firstIndex = formatList.indexOf(alphatableb[i]);
				if (firstIndex != -1) {
					sectionIndices.add(firstIndex);
					mIndexer.put(alphatableb[i], firstIndex);
				}
			}

			mSectionLetters = new Character[mIndexer.size()];
			for (int i = 0; i < mSectionLetters.length; i++) {
				mSectionLetters[i] = formatList.get(sectionIndices.get(i))
						.charAt(0);
			}

			mSectionIndices = new int[sectionIndices.size()];
			for (int i = 0; i < sectionIndices.size(); i++) {
				mSectionIndices[i] = sectionIndices.get(i);
			}
		}
	}


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = super.getView(position, convertView, parent);

        Cursor mCursor = (Cursor) getItem(position);
        // ViewHolderList
        final ViewHolderList viewholder;

        if (view != null) {
            viewholder = new ViewHolderList(view);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolderList)convertView.getTag();
        }

        String playlist_name = mCursor.getString(PlaylistsFragment.mPlaylistNameIndex);
        viewholder.music_title.setText(playlist_name);

//        int playList_count = mCursor.getInt(PlaylistsFragment.mCountIndex);
        
        return view;
    }
    
	@Override
	public int getPositionForSection(int section) {
		if (mSectionIndices != null) {
			if (section >= mSectionIndices.length) {
				section = mSectionIndices.length - 1;
			} else if (section < 0) {
				section = 0;
			}
			return mSectionIndices[section];
		}

		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		if (mSectionIndices != null) {
			for (int i = 0; i < mSectionIndices.length; i++) {
				if (position < mSectionIndices[i]) {
					return i - 1;
				}
			}
			return mSectionIndices.length - 1;
		}

		return 0;
	}

	@Override
	public Object[] getSections() {
		if (mSectionLetters != null) {
			return mSectionLetters;
		}

		return new String[] { " " };
	}
}
