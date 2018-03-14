package com.dream.mediaplayer.activity;

import static com.dream.mediaplayer.Constants.PLAYLIST_QUEUE;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.fragment.AddTracksToQueueFragment;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;

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
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class AddTracksToQueueActivity extends Activity implements ServiceConnection {
	
	private String tag = "AddTracksToQueueActivity --- ";
	
	private Context mContext;
	
	/**
	 * 根布局
	 */
	private FrameLayout rootboot;
	
	private ServiceToken mToken;
	
	/** 管理fragment */
	private FragmentManager fragmentManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ApolloUtils.setStatusBar(this);
		
		mContext = this;
		
		// Control Media volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        // fragment管理者
     	fragmentManager = getFragmentManager();
        
        // Layout
        setContentView(R.layout.activity_add_song_to_queue);
        
        rootboot = (FrameLayout) findViewById(R.id.framelayout);
        
        Intent intent = getIntent();
        if (intent != null) {
        	Bundle bundle = intent.getExtras();
        	if (bundle != null) {
        		addFragment(new AddTracksToQueueFragment(bundle));
        		return;
        	}
        }
        
        addFragment(new AddTracksToQueueFragment());
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
//				MusicUtils.setRootbootBK(mContext, rootboot);
        	}
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        // Bind to Service
     	mToken = MusicUtils.bindToService(this, this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ApolloService.META_CHANGED);
        filter.addAction(ApolloService.QUEUE_CHANGED);
        filter.addAction(ApolloService.PLAYSTATE_CHANGED);
        registerReceiver(mMediaStatusReceiver, filter);

    }

    @Override
    public void onStop() {
    	
        unregisterReceiver(mMediaStatusReceiver);
        
        // Unbind
 		if (MusicUtils.mService != null)
 			MusicUtils.unbindFromService(mToken);
     		
        super.onStop();
    }
    
    @Override
	public void onServiceConnected(ComponentName name, IBinder obj) {
		MusicUtils.mService = IApolloService.Stub.asInterface(obj);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		MusicUtils.mService = null;
	}
	
	private void addFragment(Fragment fragment) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.framelayout, fragment);
		transaction.commit();
	}
}
