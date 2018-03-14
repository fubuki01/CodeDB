/**
 * 
 */

package com.dream.mediaplayer.fragment;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.MainActivity;
import com.dream.mediaplayer.activity.PlaylistOfTracksActivity;
import com.dream.mediaplayer.helpers.utils.MenuColorFix;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.SortCursor;
import com.dream.mediaplayer.ui.adapters.PlaylistAdapter;

import se.emilsjolander.stickylistheaders.SideBarView;

import static com.dream.mediaplayer.Constants.INTENT_CREATE_PLAYLIST;
import static com.dream.mediaplayer.Constants.INTENT_KEY_RENAME;
import static com.dream.mediaplayer.Constants.INTENT_PLAYLIST_LIST;
import static com.dream.mediaplayer.Constants.INTENT_RENAME_PLAYLIST;
import static com.dream.mediaplayer.Constants.MIME_TYPE;
import static com.dream.mediaplayer.Constants.PLAYLIST_NAME;

//import com.dream.mediaplayer.activity.PlaylistDialog;

/**
 * @author Andrew Neal
 */
public class PlaylistsFragment extends Fragment implements LoaderCallbacks<Cursor>,
        OnItemClickListener {

    // Adapter
    private PlaylistAdapter mPlaylistAdapter;

    // ListView
    private ListView mListView;

    // Cursor
    private Cursor mCursor;

    // Current playlist Id
    private String mCurrentPlaylistId;

    // Options
    private static final int PLAY_SELECTION = 11;

    private static final int DELETE_PLAYLIST = 12;

    private static final int RENAME_PLAYLIST = 13;
    
    private static final int ADD_TRACKS_TO_PLAYLIST = 14;

    // Aduio columns
    public static int mPlaylistNameIndex, mPlaylistIdIndex;
    
    private SideBarView mSideBar;
    private String title;

    private Intent intent;

    // Bundle
    public PlaylistsFragment() {
    }

    public PlaylistsFragment(Bundle args) {
        setArguments(args);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Adapter
        getActivity().setTheme(R.style.TranslucentTheme);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mPlaylistAdapter = new PlaylistAdapter(getActivity(), R.layout.playlists_list_item_layout, null,
                new String[] {}, new int[] {}, 0);
        mListView.setOnCreateContextMenuListener(this);
        mListView.setAdapter(mPlaylistAdapter);
        mListView.setOnItemClickListener(this);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.playlists_fragment_layout, container, false);
        mListView = (ListView)root.findViewById(R.id.list);
        View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.playlists_list_header_layout, null);
        mListView.addHeaderView(view, null, true);
        
        mSideBar = (SideBarView) root.findViewById(R.id.sidebar);
		mSideBar.setOnItemClickListener(new SideBarView.OnItemClickListener() {

			@Override
			public void onItemClick(String s) {
				if (mPlaylistAdapter.getIndexer().get(s) != null) {
					mListView.setSelection(mPlaylistAdapter.getIndexer().get(s)
							+ mListView.getHeaderViewsCount());
				}
			}
		});
        return root;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (requestCode == MusicUtils.REQUEST_CODE_CREATE_PALYLIST
    		&& resultCode == MusicUtils.RERULT_CODE_CREATE_PALYLIST) {
    		if (data != null) {
    			boolean isRestartLoader = data.getBooleanExtra("isRestartLoader", false);
    			if (isRestartLoader) {
    				getLoaderManager().restartLoader(0, null, this);
    			}
    		}
    	}
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {
                BaseColumns._ID, PlaylistsColumns.NAME, PlaylistsColumns.DATA
        };
        Uri uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Playlists.DEFAULT_SORT_ORDER;
        return new CursorLoader(getActivity(), uri, projection, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

        mPlaylistIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        mPlaylistNameIndex = data.getColumnIndexOrThrow(PlaylistsColumns.NAME);
        
        SortCursor sortCursor = new SortCursor(data, PlaylistsColumns.NAME);
        mPlaylistAdapter.swapCursor(sortCursor);
        mListView.invalidateViews();
        mCursor = sortCursor;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mPlaylistAdapter != null)
            mPlaylistAdapter.swapCursor(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putAll(getArguments() != null ? getArguments() : new Bundle());
        super.onSaveInstanceState(outState);
    }

    /**
     * ListView的创建上下文菜单
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

        AdapterContextMenuInfo mi = (AdapterContextMenuInfo)menuInfo;
         if (mi.position == 0) {
         	super.onCreateContextMenu(menu, v, menuInfo);
         	
         	return;
         }
//        menu.add(0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
        MenuColorFix.addMenu (menu, 0, PLAY_SELECTION, 0, getResources().getString(R.string.play_all));
        if (mi.id >= 0) {
//            menu.add(0, RENAME_PLAYLIST, 0, getResources().getString(R.string.rename_playlist));
        	MenuColorFix.addMenu (menu, 0, RENAME_PLAYLIST, 0, getResources().getString(R.string.rename_playlist));
//            menu.add(0, DELETE_PLAYLIST, 0, getResources().getString(R.string.delete_playlist));
        	MenuColorFix.addMenu (menu, 0, DELETE_PLAYLIST, 0, getResources().getString(R.string.delete_playlist));
        }
//        menu.add(0, ADD_TRACKS_TO_PLAYLIST, 0, getResources().getString(R.string.add_tracks_to_playlist));
        MenuColorFix.addMenu (menu, 0, ADD_TRACKS_TO_PLAYLIST, 0, getResources().getString(R.string.add_tracks_to_playlist));
        //当前播放列表的ID值
        mCurrentPlaylistId = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseColumns._ID));
        //当前播放列表的名字
        title = mCursor.getString(mPlaylistNameIndex);
        menu.setHeaderTitle(title);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     * 上下文菜单的点击监听处理
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo mi = (AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case PLAY_SELECTION: {    //全部播放
                long[] list = MusicUtils.getSongListForPlaylist(getActivity(),
                        Long.parseLong(mCurrentPlaylistId));
                MusicUtils.playAll(getActivity(), list, 0);
                break;
            }
            case RENAME_PLAYLIST: {     //重命名播放列表名字
                Intent intent = new Intent(INTENT_RENAME_PLAYLIST);
                intent.putExtra(INTENT_KEY_RENAME, mi.id);
                getActivity().startActivity(intent);
                break;
            }
            case DELETE_PLAYLIST: {    //删除对应的播放列表
                //判断当前删除的播放列表是否是“最喜欢的”，使其不能进行删除
                if("最喜爱的".equals(title)){
                    //弹个土司进行提醒，不能够进行删除
                    Toast.makeText(getActivity() , "当前列表是系统级别，不能进行删除哦！",Toast.LENGTH_SHORT).show();
                }
                else {    //否则能够进行正常的删除
                    Uri uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, mi.id);
                    getActivity().getContentResolver().delete(uri, null, null);
                }
                break;
            }
            case ADD_TRACKS_TO_PLAYLIST: {      //添加歌曲到对应的播放列表
            	MusicUtils.addTracksToPlaylist(getActivity(), Long.parseLong(mCurrentPlaylistId));
            	break;
            }
            
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * ListView的点击监听
     * @param parent
     * @param v
     * @param position  ：点击的位置
     * @param id：播放列表的ID
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //当点击的是新建播放列表选项
        if (position == 0) {
            long[] mList = new long[] {};
            if (intent==null){
                if(!MainActivity.exitManiActivity2Playlst){   //判断点击了新建的时候，是否同时点击了返回按钮，否则会在主界面出现Dialog
                    //弹出Dialog主题的Activity
                    intent = new Intent(INTENT_CREATE_PLAYLIST);
                    intent.putExtra(INTENT_PLAYLIST_LIST, mList);
//            startActivity(intent);
                    startActivityForResult(intent, MusicUtils.REQUEST_CODE_CREATE_PALYLIST);
                    return;
                }
                else{
                    MainActivity.exitManiActivity2Playlst = false ; //标志复位，直接退出
                    return;
                }
            }else {
                return;
            }

        }
        tracksBrowser(id);
    }

    @Override
    public void onResume() {
        super.onResume();
        intent = null;
    }

    /**
     * 播放列表选项的点击操作处理
     * @param id
     */
    private void tracksBrowser(long id) {

        String playlistName = mCursor.getString(mPlaylistNameIndex);

        Bundle bundle = new Bundle();
        bundle.putString(MIME_TYPE, Audio.Playlists.CONTENT_TYPE);
        bundle.putString(PLAYLIST_NAME, playlistName);
        bundle.putLong(BaseColumns._ID, id);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getActivity(), PlaylistOfTracksActivity.class);
        intent.putExtras(bundle);
//        MusicUtils.screenTransitAnimByPair(getActivity(), 
//				intent, null);
        getActivity().startActivity(intent);
    }
}
