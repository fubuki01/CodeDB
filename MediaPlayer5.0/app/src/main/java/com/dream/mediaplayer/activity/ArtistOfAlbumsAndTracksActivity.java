package com.dream.mediaplayer.activity;

import static com.dream.mediaplayer.Constants.ALBUM_ID_KEY;
import static com.dream.mediaplayer.Constants.ALBUM_KEY;
import static com.dream.mediaplayer.Constants.ARTIST_ID;
import static com.dream.mediaplayer.Constants.ARTIST_KEY;
import static com.dream.mediaplayer.Constants.GENRE_KEY;
import static com.dream.mediaplayer.Constants.INTENT_ACTION;
import static com.dream.mediaplayer.Constants.MIME_TYPE;
import static com.dream.mediaplayer.Constants.NUMALBUMS;
import static com.dream.mediaplayer.Constants.PLAYLIST_NAME;
import static com.dream.mediaplayer.Constants.SIZE_NORMAL;
import static com.dream.mediaplayer.Constants.SRC_FIRST_AVAILABLE;
import static com.dream.mediaplayer.Constants.TYPE_ALBUM;
import static com.dream.mediaplayer.Constants.TYPE_ARTIST;
import static com.dream.mediaplayer.Constants.TYPE_GENRE;
import static com.dream.mediaplayer.Constants.TYPE_PLAYLIST;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.cache.ImageInfo;
import com.dream.mediaplayer.fragment.AlbumsAndTracksOfBundleFragment;
import com.dream.mediaplayer.fragment.ArtistAlbumsFragment;
import com.dream.mediaplayer.fragment.TracksOfBundleFragment;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;
import com.dream.mediaplayer.views.MarqueeTextView;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.ArtistColumns;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArtistOfAlbumsAndTracksActivity extends Activity  implements ServiceConnection {
	
private String tag = "ArtistOfAlbumsAndTracksActivity --- ";
	
	private Context mContext;
	
	private ServiceToken mToken;
	
	/** 管理fragment */
	private FragmentManager fragmentManager;
	
	/**
	 * 根布局
	 */
	private RelativeLayout rootboot;
	
	private ImageView mRetrunBtn;
	
	private TextView mTextAlbumName;
	
	// Bundle
    private Bundle bundle;

    private Intent intent;
    
    private String mimeType;
    
    private ImageView mSearchTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ApolloUtils.setStatusBar(this);
		
		getWindow().setAllowEnterTransitionOverlap(true);
		
		mContext = this;
		
		// Control Media volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        // fragment管理者
     	fragmentManager = getFragmentManager();
        
        // Layout
        setContentView(R.layout.activity_artist_of_albums_and_tracks);
        
        //ImageCache
//    	mImageProvider = ImageProvider.getInstance( this );
        
        getElements();
        
        initListener();
        
        whatBundle(savedInstanceState);
        
        initAlbumInfo();
        
//        addFragment(new AlbumsAndTracksOfBundleFragment(bundle));
        addFragment(new ArtistAlbumsFragment(bundle));
//        addFragment(new TracksOfBundleFragment(bundle));
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		MusicUtils.finishTranstion((Activity)mContext, true);
	}
	
	@Override
    public void onSaveInstanceState(Bundle outcicle) {
        outcicle.putAll(bundle);
        super.onSaveInstanceState(outcicle);
    }
	
	/**
	 * 获取界面元素
	 */
	private void getElements() {
		rootboot = (RelativeLayout) findViewById(R.id.rootboot);
//		bottomActionBar = (RelativeLayout) findViewById(R.id.bottom_bar);
//		bottombarCover = (ImageView) findViewById(R.id.bottombar_cover);
//		bottombar_songname = (MarqueeTextView) findViewById(R.id.bottombar_songname);
//		bottombar_artistname = (MarqueeTextView) findViewById(R.id.bottombar_artistname);
//		mPrev = (ImageButton) findViewById(R.id.bottombar_pre);
//		mPlay = (ImageButton) findViewById(R.id.bottombar_play);
//		mNext = (ImageButton) findViewById(R.id.bottombar_next);
		
		mRetrunBtn = (ImageView) findViewById(R.id.return_btn);
		mTextAlbumName = (TextView) findViewById(R.id.album_name);
		
		mSearchTextView = (ImageView) findViewById(R.id.search_textview);
	}
	
	/**
	 * 初始化监听器
	 */
	private void initListener() {
		mSearchTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.changeToSearchActivity(mContext);
			}
		});

		mRetrunBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.finishTranstion((Activity)mContext, false);
			}
		});
	}
	
	private void addFragment(Fragment fragment) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.framelayout, fragment);
		transaction.commit();
	}
	
	/**
     * @param icicle
     * @return what Bundle we're dealing with
     */
    public void whatBundle(Bundle icicle) {
        intent = getIntent();
        bundle = icicle != null ? icicle : intent.getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (bundle.getString(INTENT_ACTION) == null) {
            bundle.putString(INTENT_ACTION, intent.getAction());
        }
        if (bundle.getString(MIME_TYPE) == null) {
            bundle.putString(MIME_TYPE, intent.getType());
        }
        mimeType = bundle.getString(MIME_TYPE);
    }
	
	private void initAlbumInfo() {
    	ImageInfo mInfo = new ImageInfo();
    	mInfo.source = SRC_FIRST_AVAILABLE;
        mInfo.size = SIZE_NORMAL;
    	String lineOne = "";
    	String lineTwo = "";

        if (ApolloUtils.isArtist(mimeType)) {
        	String mArtist = getArtist();
            mInfo.type = TYPE_ARTIST;
            mInfo.data = new String[]{ mArtist };  
            lineOne = mArtist;
            lineTwo = MusicUtils.makeAlbumsLabel(this, Integer.parseInt(getNumAlbums()), 0, false);
        }else if (ApolloUtils.isAlbum(mimeType)) {
        	String mAlbum = getAlbum(), mArtist = getArtist();
            mInfo.type = TYPE_ALBUM;
            mInfo.data = new String[]{ getAlbumId(), mAlbum, mArtist };                
            lineOne = mAlbum;
            lineTwo = mArtist;
        } else if (Audio.Playlists.CONTENT_TYPE.equals(mimeType)) {
        	String plyName = bundle.getString(PLAYLIST_NAME);
        	mInfo.type = TYPE_PLAYLIST;
            mInfo.data = new String[]{ plyName };               
            lineOne = plyName;
        }
        else{ 
        	String genName = MusicUtils.parseGenreName(this,
        			MusicUtils.getGenreName(this, bundle.getLong(BaseColumns._ID), true));
        	mInfo.type = TYPE_GENRE;
            mInfo.size = SIZE_NORMAL;
            mInfo.data = new String[]{ genName };             
            lineOne = genName;
        }

        mTextAlbumName.setText(getArtist());
    }
	
	/**
     * @return artist name from Bundle
     */
    public String getArtist() {
        if (bundle.getString(ARTIST_KEY) != null)
            return bundle.getString(ARTIST_KEY);
        return getResources().getString(R.string.app_name);
    }

    /**
     * @return album name from Bundle
     */
    public String getAlbum() {
        if (bundle.getString(ALBUM_KEY) != null)
            return bundle.getString(ALBUM_KEY);
        return getResources().getString(R.string.app_name);
    }

    /**
     * @return album name from Bundle
     */
    public String getAlbumId() {
        if (bundle.getString(ALBUM_ID_KEY) != null)
            return bundle.getString(ALBUM_ID_KEY);
        return getResources().getString(R.string.app_name);
    }

    /**
     * @return number of albums from Bundle
     */
    public String getNumAlbums() {
        if (bundle.getString(NUMALBUMS) != null)
            return bundle.getString(NUMALBUMS);
        String[] projection = {
                BaseColumns._ID, ArtistColumns.ARTIST, ArtistColumns.NUMBER_OF_ALBUMS
        };
        Uri uri = Audio.Artists.EXTERNAL_CONTENT_URI;        
        Long id = ApolloUtils.getArtistId(getArtist(), ARTIST_ID, this);
        Cursor cursor = null;
        try{
        	cursor = this.getContentResolver().query(uri, projection, BaseColumns._ID+ "=" + DatabaseUtils.sqlEscapeString(String.valueOf(id)), null, null);
        }
        catch(Exception e){
        	e.printStackTrace();        	
        }
        if(cursor == null)
        	return String.valueOf(0);
        
        String numAlbums = String.valueOf(0);
        int mArtistNumAlbumsIndex = cursor.getColumnIndexOrThrow(ArtistColumns.NUMBER_OF_ALBUMS);
        if(cursor.getCount()>0){
	    	cursor.moveToFirst();
	        numAlbums = cursor.getString(mArtistNumAlbumsIndex);	  
        }    
        
        cursor.close();
        
        return numAlbums;
    }

    /**
     * @return genre name from Bundle
     */
    public String getGenre() {
        if (bundle.getString(GENRE_KEY) != null)
            return bundle.getString(GENRE_KEY);
        return getResources().getString(R.string.app_name);
    }

    /**
     * @return playlist name from Bundle
     */
    public String getPlaylist() {
        if (bundle.getString(PLAYLIST_NAME) != null)
            return bundle.getString(PLAYLIST_NAME);
        return getResources().getString(R.string.app_name);
    }

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		MusicUtils.mService = IApolloService.Stub.asInterface(service);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		MusicUtils.mService = null;
	}
	
	@Override
	protected void onStart() {

		// Bind to Service
		mToken = MusicUtils.bindToService(this, this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ApolloService.PLAYSTATE_CHANGED);
		filter.addAction(ApolloService.QUEUE_CHANGED);
		filter.addAction(ApolloService.META_CHANGED);
		registerReceiver(mMediaStatusReceiver, filter);

		super.onStart();
	}

	@Override
	public void onStop() {
		unregisterReceiver(mMediaStatusReceiver);

		// Unbind
		if (MusicUtils.mService != null)
			MusicUtils.unbindFromService(mToken);

		// TODO: clear image cache

		super.onStop();
	}
	
	/**
	 * Update the list as needed
	 */
	private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e(tag,
//					"onReceive() --- intent.getAction() = "
//							+ intent.getAction());
			
			if (intent.getAction().equals(ApolloService.META_CHANGED)) {
				//显示跟布局的背景
//				MusicUtils.setRootbootBK(mContext, rootboot);
	            
//	            MusicUtils.updateBottomActionBar(mContext, bottomActionBar, bottombar_songname, bottombar_artistname, bottombarCover);
			}
			
//			MusicUtils.setPauseButtonImage(mPlay);
		}

	};
}
