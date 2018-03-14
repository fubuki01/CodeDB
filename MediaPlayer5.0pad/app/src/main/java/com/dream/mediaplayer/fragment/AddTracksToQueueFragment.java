
package com.dream.mediaplayer.fragment;




import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.SideBarView;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.helpers.utils.SortCursor.SortEntry;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.ui.adapters.AddTracksToQueueAdapter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;

import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Artists;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.Genres;
import android.provider.MediaStore.Audio.Playlists;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import static com.dream.mediaplayer.Constants.EXTERNAL;
import static com.dream.mediaplayer.Constants.INTENT_ADD_TO_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.MIME_TYPE;
import static com.dream.mediaplayer.Constants.PLAYLIST_FAVORITES;
import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;
import static com.dream.mediaplayer.Constants.ADD_TO_PALYLIST;

/**
 */
public class AddTracksToQueueFragment extends Fragment implements LoaderCallbacks<Cursor>,
        OnItemClickListener, MusicUtils.Defs {
	
	private String tag = "AddTracksToQueueFragment --- ";
	
    // Adapter
    private AddTracksToQueueAdapter mTrackAdapter;

    // ListView
    private ListView mListView;
    
    /**
     */
    private TextView mTextViewCount;

    // Cursor
    private Cursor mCursor;

    // Playlist ID
    private long mPlaylistId = -1;

    // Selected position
    private int mSelectedPosition;

    // Used to set ringtone
    private long mSelectedId;

    private boolean mEditMode = false;

    // Audio columns
    public static int mTitleIndex, mAlbumIndex, mArtistIndex, mMediaIdIndex, mAlbumId;
    
    private SideBarView mSideBar;
    
    /**
	 * 根布局
	 */
//	private RelativeLayout rootboot;
	
	private TextView finishTextView;
	
	private ImageView checkBoxAll;
	
	private boolean isCheckBoxALLSelect;
	
	private TextView header_textview;
	

    // Bundle
    public AddTracksToQueueFragment() {
    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public AddTracksToQueueFragment(Bundle args) {
        setArguments(args);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isEditMode();

        // Adapter
        mTrackAdapter = new AddTracksToQueueAdapter(getActivity(), R.layout.add_tracks_to_queue_list_item_layout, null,
                new String[] {}, new int[] {}, 0);
        mListView.setOnCreateContextMenuListener(this);
        mListView.setOnItemClickListener(this); 
        mListView.setAdapter(mTrackAdapter);

        // Important!
        getLoaderManager().initLoader(0, null, this);
    }

//    @Override
    public void refresh() {
        // The data need to be refreshed
        if( mListView != null ) {
            getLoaderManager().restartLoader(0, null, this);
        }
    }
    
    private void updateUI() {
    	if (mTrackAdapter.getSortList() != null
    			&& mTrackAdapter.getSortList().size() > 0) {
    		int totalNum = 0;
    		ArrayList<SortEntry> sortList = mTrackAdapter.getSortList();
			if (sortList != null) {
				for (SortEntry sortEntry:sortList) {
					if (sortEntry.isSelect) {
						totalNum++;
					}
				}
				
				if (totalNum == sortList.size()) {
//					checkBoxAll.setChecked(true);
					isCheckBoxALLSelect = true;
					checkBoxAll.setBackgroundResource(R.drawable.select_image_select);
				} else if (totalNum < sortList.size()) {
//					checkBoxAll.setChecked(false);
					isCheckBoxALLSelect = false;
					checkBoxAll.setBackgroundResource(R.drawable.select_image_unselect);
				}
				
				if (totalNum > 0) {
					header_textview.setText("已选择 ("+totalNum+")");
					//finishTextView.setVisibility(View.VISIBLE);
				} else {
					header_textview.setText(getActivity().getResources().getString(R.string.add_tracks_to_queue_select_all_textview_text));
					//finishTextView.setVisibility(View.INVISIBLE);
				}
			}
    	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_tracks_to_queue_fragment_layout, container, false);
        
//        rootboot = (RelativeLayout) root.findViewById(R.id.rootboot);
        
        finishTextView = (TextView) root.findViewById(R.id.finish_textview);
        finishTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<Long> arrayList = new ArrayList<Long>();
				ArrayList<SortEntry> sortList = mTrackAdapter.getSortList();

                if (sortList != null) {
					for (SortEntry sortEntry:sortList) {
						if (sortEntry.isSelect) {
                                arrayList.add(sortEntry.baseColumns_ID);
						}
					}
//					Long list[] = (Long[])arrayList.toArray(new Long[sortList.size()]);
					long list[] = new long[arrayList.size()];
					for (int i = 0; i < arrayList.size(); i++) {
						list[i] = arrayList.get(i);
					}
					
					if (getArguments() != null) {
						long playListId = getArguments().getLong(ADD_TO_PALYLIST);
						if (playListId > 0 ) {
							MusicUtils.addToPlaylist(getActivity(), list, playListId);
						}
					} else {
						MusicUtils.addToCurrentPlaylist(getActivity(), list);
					}
					
				}
				
				getActivity().finish();
			}
		});
        
    	checkBoxAll = (ImageView) root.findViewById(R.id.checkbox_all);
    	checkBoxAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				boolean isChecked = false;
//				if (checkBoxAll.isChecked()) {
//					isChecked = true;
//				} else {
//					isChecked = false;
//				}
				isCheckBoxALLSelect = !isCheckBoxALLSelect;
				if (isCheckBoxALLSelect) {
					checkBoxAll.setBackgroundResource(R.drawable.select_image_select);
				} else {
					checkBoxAll.setBackgroundResource(R.drawable.select_image_unselect);
				}
				
				ArrayList<SortEntry> sortList = mTrackAdapter.getSortList();
				if (sortList != null) {
					for (SortEntry sortEntry:sortList) {
						sortEntry.isSelect = isCheckBoxALLSelect;
					}
					
					mTrackAdapter.notifyDataSetChanged();
					
					updateUI();
				}
			}
		});
    	
    	header_textview = (TextView) root.findViewById(R.id.header_textview);
    	
        mListView = (ListView)root.findViewById(R.id.list);
        /*View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.tracks_list_header_layout, null);
        mTextViewCount = (TextView) view.findViewById(R.id.song_count_tv);
        mListView.addHeaderView(view, null, true);*/
        
        mSideBar = (SideBarView) root.findViewById(R.id.sidebar);
		mSideBar.setOnItemClickListener(new SideBarView.OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mTrackAdapter.getIndexer().get(s) != null) {
					mListView.setSelection(mTrackAdapter.getIndexer().get(s)
							+ mListView.getHeaderViewsCount());
				}
			}
		});

        // Align the track list with the header, in other words,OCD.
//        TextView mHeader = (TextView)root.findViewById(R.id.title);
//        int eight = (int)getActivity().getResources().getDimension(
//                R.dimen.list_separator_padding_left_right);
//        mHeader.setPadding(eight, 0, 0, 0);

        // Set the header while in @TracksBrowser
//        String header = getActivity().getResources().getString(R.string.track_header);
//        int left = getActivity().getResources().getInteger(R.integer.listview_padding_left);
//        int right = getActivity().getResources().getInteger(R.integer.listview_padding_right);
//        ApolloUtils.listHeader(this, root, header);
//        ApolloUtils.setListPadding(this, mListView, left, 0, right, 0);

        // Hide the extra spacing from the Bottom ActionBar in the queue
        // Fragment in @AudioPlayerHolder
        if (getArguments() != null) {
            mPlaylistId = getArguments().getLong(BaseColumns._ID);
            String mimeType = getArguments().getString(MIME_TYPE);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimeType)) {
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
//                        LinearLayout emptyness = (LinearLayout)root.findViewById(R.id.empty_view);
//                        emptyness.setVisibility(View.GONE);
                }
            }
        }
        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {
                BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST, AudioColumns.ALBUM_ID
        };
        StringBuilder where = new StringBuilder();
        String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
        where.append(AudioColumns.IS_MUSIC + "=1").append(" AND " + MediaColumns.TITLE + " != ''");
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        if (getArguments() != null) {
            mPlaylistId = getArguments().getLong(BaseColumns._ID);
            String mimeType = getArguments().getString(MIME_TYPE);
            //Log.e(tag, "onCreateLoader() --- 1 mimeType = "+mimeType+", mPlaylistId = "+mPlaylistId);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimeType)) {
                where = new StringBuilder();
                where.append(AudioColumns.IS_MUSIC + "=1");
                where.append(" AND " + MediaColumns.TITLE + " != ''");
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
                        uri = Audio.Media.EXTERNAL_CONTENT_URI;
                        long[] mNowPlaying = MusicUtils.getQueue();
                        if (mNowPlaying.length == 0)
                            return null;
                        where = new StringBuilder();
                        where.append(BaseColumns._ID + " IN (");
                        if (mNowPlaying == null || mNowPlaying.length <= 0)
                            return null;
                        for (long queue_id : mNowPlaying) {
                            where.append(queue_id + ",");
                        }
                        where.deleteCharAt(where.length() - 1);
                        where.append(")");
                        break;
                    case (int)PLAYLIST_FAVORITES:
                        long favorites_id = MusicUtils.getFavoritesId(getActivity());
                        projection = new String[] {
                                Playlists.Members._ID, Playlists.Members.AUDIO_ID,
                                MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST, AudioColumns.ALBUM_ID
                        };
                        uri = Playlists.Members.getContentUri(EXTERNAL, favorites_id);
                        sortOrder = Playlists.Members.DEFAULT_SORT_ORDER;
                        break;
                    default:
                        if (id < 0)
                            return null;
                        projection = new String[] {
                                Playlists.Members._ID, Playlists.Members.AUDIO_ID,
                                MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST,
                                AudioColumns.DURATION, AudioColumns.ALBUM_ID
                        };

                        uri = Playlists.Members.getContentUri(EXTERNAL, mPlaylistId);
                        sortOrder = Playlists.Members.DEFAULT_SORT_ORDER;
                        break;
                }
            } else if (Audio.Genres.CONTENT_TYPE.equals(mimeType)) {
                long genreId = getArguments().getLong(BaseColumns._ID);
                uri = Genres.Members.getContentUri(EXTERNAL, genreId);
                projection = new String[] {
                        BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM,
                        AudioColumns.ARTIST
                };
                where = new StringBuilder();
                where.append(AudioColumns.IS_MUSIC + "=1").append(
                        " AND " + MediaColumns.TITLE + " != ''");
                sortOrder = Genres.Members.DEFAULT_SORT_ORDER;
            } else {
                if (Audio.Albums.CONTENT_TYPE.equals(mimeType)) {
                    long albumId = getArguments().getLong(BaseColumns._ID);
                    where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
                    sortOrder = Audio.Media.TRACK + ", " + sortOrder;
                } else if (Audio.Artists.CONTENT_TYPE.equals(mimeType)) {
                    sortOrder = MediaColumns.TITLE;
                    long artist_id = getArguments().getLong(BaseColumns._ID);
                    where.append(" AND " + AudioColumns.ARTIST_ID + "=" + artist_id);
                }
            }
        }
        return new CursorLoader(getActivity(), uri, projection, where.toString(), null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
    	
        if (data == null) { 
            return;
        }
       
        if (getArguments() != null
                && Playlists.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))
                && (getArguments().getLong(BaseColumns._ID) >= 0 || getArguments().getLong(
                        BaseColumns._ID) == PLAYLIST_FAVORITES)) {
            mMediaIdIndex = data.getColumnIndexOrThrow(Playlists.Members.AUDIO_ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            
//            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
//            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
//            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            mAlbumId = data.getColumnIndexOrThrow(Playlists.Members.ALBUM_ID);
           
            // FIXME
//             mArtistIndex =
//             data.getColumnIndexOrThrow(Playlists.Members.ARTIST);
        } else if (getArguments() != null
                && Genres.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
            mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
            
            
        } else if (getArguments() != null
                && Artists.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            // mArtistIndex is "line2" of the ListView
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
        } else if (getArguments() != null
                && Albums.CONTENT_TYPE.equals(getArguments().getString(MIME_TYPE))) {
            mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
            mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
            mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
           
        } else {
        	 mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
             mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
             mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
             mAlbumIndex = data.getColumnIndexOrThrow(AudioColumns.ALBUM);
             mAlbumId = data.getColumnIndexOrThrow(AudioColumns.ALBUM_ID);
        }
        
        SortCursor sortCursor = new SortCursor(data, MediaColumns.TITLE, BaseColumns._ID);
        
        mTrackAdapter.swapCursor(sortCursor);
//        updateCountTextView(sortCursor.getSortList().size());
        mListView.invalidateViews();
        mCursor = sortCursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mTrackAdapter != null)
            mTrackAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(getArguments() != null ? getArguments() : new Bundle());
        super.onSaveInstanceState(outState);
    }

    /*@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	 AdapterContextMenuInfo mi = (AdapterContextMenuInfo)menuInfo;
         if (mi.position == 0) {
         	super.onCreateContextMenu(menu, v, menuInfo);
         	
         	return;
         }
         
        menu.add(0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
        menu.add(0, ADD_TO_PLAYLIST, 0, getResources().getString(R.string.add_to_playlist));
        menu.add(0, USE_AS_RINGTONE, 0, getResources().getString(R.string.use_as_ringtone));
        menu.add(0, REAL_REMOVE, 0, getResources().getString(R.string.real_remove));
        if (mEditMode) {
            menu.add(0, REMOVE, 0, getResources().getString(R.string.remove));
        }
        menu.add(0, SEARCH, 0, getResources().getString(R.string.search));

        mSelectedPosition = mi.position-mListView.getHeaderViewsCount();
        mCursor.moveToPosition(mSelectedPosition);

        try {
            mSelectedId = mCursor.getLong(mMediaIdIndex);
        } catch (IllegalArgumentException ex) {
            mSelectedId = mi.id;
        }

        String title = mCursor.getString(mTitleIndex);
        menu.setHeaderTitle(title);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    public static  Cursor tomCursor;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PLAY_SELECTION:
                int position = mSelectedPosition;
                MusicUtils.playAll(getActivity(), mCursor, position);
                break;
            case ADD_TO_PLAYLIST: {
                Intent intent = new Intent(INTENT_ADD_TO_PLAYLIST);
                long[] list = new long[] {
                    mSelectedId
                };
                intent.putExtra(INTENT_PLAYLIST_LIST, list);
                getActivity().startActivity(intent);
                break;
            }
            case USE_AS_RINGTONE:
                MusicUtils.setRingtone(getActivity(), Integer.toString((int)mSelectedId));
                break;
            case REAL_REMOVE:
            	 MusicUtils.RealdeletSong(getActivity(), mSelectedId,mSelectedPosition);
            	
                break;
            case REMOVE: {
                removePlaylistItem(mSelectedPosition);
                break;
            }
            case SEARCH: {
                MusicUtils.doSearch(getActivity(), mCursor, mTitleIndex);
                break;
            }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }*/
  
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//    	Log.e(tag, "onItemClick() -1- position = "+position);
    	if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return;
        }
    	
    	ArrayList<SortEntry> sortList = mTrackAdapter.getSortList();
    	if (sortList != null) {
    		sortList.get(position).isSelect = !sortList.get(position).isSelect;
    		mTrackAdapter.notifyDataSetChanged();
    		
    		updateUI();
    	}
    	
    	/*if (position == 0) {
    		MusicUtils.shuffleAll(getActivity(), mCursor);
            tomCursor= mCursor;
    		
    		return;
    	}*/
    	
        /*if (mCursor instanceof NowPlayingCursor) {
            if (MusicUtils.mService != null) {
                MusicUtils.setQueuePosition(position-mListView.getHeaderViewsCount());
                MusicUtils.getSongPathName(getActivity(),id,position-mListView.getHeaderViewsCount());
                return;
            }
        }
        MusicUtils.playAll(getActivity(), mCursor, position-mListView.getHeaderViewsCount());
        tomCursor= mCursor;*/
    }

    /**
     * Update the list as needed
     */
    private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
//        	Log.e(tag, "onReceive() --- intent.getAction() = "+intent.getAction()+", mPlaylistId = "+mPlaylistId);
        	if (intent.getAction().equals(ApolloService.META_CHANGED)) {
        		//显示跟布局的背景
//				MusicUtils.setRootbootBK(getActivity(), rootboot);
        	}
        	
            if (mListView != null) {
                mTrackAdapter.notifyDataSetChanged();
                // Scroll to the currently playing track in the queue
                if (mPlaylistId == PLAYLIST_QUEUE)
                    mListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mListView.setSelection(MusicUtils.getQueuePosition());
                        }
                    }, 100);
            }
        }

    };

    
	
    @Override
    public void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ApolloService.META_CHANGED);
        filter.addAction(ApolloService.QUEUE_CHANGED);
        filter.addAction(ApolloService.PLAYSTATE_CHANGED);
        getActivity().registerReceiver(mMediaStatusReceiver, filter);

    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mMediaStatusReceiver);
        super.onStop();
    }
    
    /**
	 */
	private void updateCountTextView(int songsCount) {
		String template = getString(R.string.tracks_list_header_text);
        String text = String.format(template, songsCount);
		mTextViewCount.setText(text);
	}

    /**
     */
    private void removePlaylistItem(int which) {

        mCursor.moveToPosition(which);
        long id = mCursor.getLong(mMediaIdIndex);
        if (mPlaylistId >= 0) {
            Uri uri = Playlists.Members.getContentUri(EXTERNAL, mPlaylistId);
            getActivity().getContentResolver().delete(uri, Playlists.Members.AUDIO_ID + "=" + id,
                    null);
        } else if (mPlaylistId == PLAYLIST_QUEUE) {
            MusicUtils.removeTrack(id);
            reloadQueueCursor();
        } else if (mPlaylistId == PLAYLIST_FAVORITES) {
            MusicUtils.removeFromFavorites(getActivity(), id);
        }
//        updateCountTextView(((SortCursor)mCursor).getSortList().size());
        mListView.invalidateViews();
    }

    /**
     * Reload the queue after we remove a track
     */
    private void reloadQueueCursor() {
        if (mPlaylistId == PLAYLIST_QUEUE) {
            String[] cols = new String[] {
                    BaseColumns._ID, MediaColumns.TITLE, MediaColumns.DATA, AudioColumns.ALBUM,
                    AudioColumns.ARTIST, AudioColumns.ARTIST_ID
            };
            StringBuilder selection = new StringBuilder();
            selection.append(AudioColumns.IS_MUSIC + "=1");
            selection.append(" AND " + MediaColumns.TITLE + " != ''");
            Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
            long[] mNowPlaying = MusicUtils.getQueue();
            if (mNowPlaying.length == 0) {
            }
            selection = new StringBuilder();
            selection.append(BaseColumns._ID + " IN (");
            for (int i = 0; i < mNowPlaying.length; i++) {
                selection.append(mNowPlaying[i]);
                if (i < mNowPlaying.length - 1) {
                    selection.append(",");
                }
            }
            selection.append(")");
            mCursor = MusicUtils.query(getActivity(), uri, cols, selection.toString(), null, null);
            
            SortCursor sortCursor = new SortCursor(mCursor, MediaColumns.TITLE);
            mTrackAdapter.swapCursor(sortCursor);
//            updateCountTextView(sortCursor.getSortList().size());
            mListView.invalidateViews();
            mCursor = sortCursor;
        }
    }

    /**
     * Check if we're viewing the contents of a playlist
     */
    public void isEditMode() {
        if (getArguments() != null) {
            String mimetype = getArguments().getString(MIME_TYPE);
            if (Audio.Playlists.CONTENT_TYPE.equals(mimetype)) {
                mPlaylistId = getArguments().getLong(BaseColumns._ID);
                switch ((int)mPlaylistId) {
                    case (int)PLAYLIST_QUEUE:
                        mEditMode = true;
                        break;
                    case (int)PLAYLIST_FAVORITES:
                        mEditMode = true;
                        break;
                    default:
                        if (mPlaylistId > 0) {
                            mEditMode = true;
                        }
                        break;
                }
            }
        }
    }
}
