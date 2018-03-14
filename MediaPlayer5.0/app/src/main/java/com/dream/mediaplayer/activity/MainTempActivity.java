package com.dream.mediaplayer.activity;

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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.fragment.AlbumsFragment;
import com.dream.mediaplayer.fragment.ArtistsFragment;
import com.dream.mediaplayer.fragment.PlaylistsFragment;
import com.dream.mediaplayer.fragment.TracksFragment;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.preferences.SharedPreferencesCompat;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;

public class MainTempActivity extends Activity implements ServiceConnection {
	
	private Context mContext;
	
	private RadioGroup mRadioGroup;
	
	private RelativeLayout isPlayingLayout;
	
	private TextView fragmentName;
	
	private ImageView search;
	
	/** 管理fragment */
	private FragmentManager fragmentManager;
	
	private SharedPreferences mPreferences;
	
	/**
	 * spinner当前选中的条目索引
	 */
	private int spinnerCurSeleIndex = 0;
	
	private ServiceToken mToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ApolloUtils.setStatusBar(this);
		
		mContext = this;
		
		// fragment管理者
		fragmentManager = getFragmentManager();
		
		mPreferences = getSharedPreferences("Music", MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
		
		// Control Media volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		setContentView(R.layout.activity_main_temp);
		
		getElement();
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
		// Unbind
		if (MusicUtils.mService != null)
			MusicUtils.unbindFromService(mToken);
		
		unregisterReceiver(mMediaStatusReceiver);

		// TODO: clear image cache

		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		Editor ed = mPreferences.edit();
		ed.putInt("spinnerCurIndex", spinnerCurSeleIndex);
		SharedPreferencesCompat.apply(ed);
	}
	
	private void getElement() {
		fragmentName = (TextView) findViewById(R.id.fragment_name);
		search = (ImageView) findViewById(R.id.search);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.changeToSearchActivity(mContext);
			}
		});
		
		mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
		mRadioGroup.getBackground().setAlpha(192);
		for (int i = 0; i < 4; i++) {
			mRadioGroup.getChildAt(i).setId(i);
		}
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (!((RadioButton) mRadioGroup.getChildAt(checkedId)).isChecked()) {
					((RadioButton) mRadioGroup.getChildAt(checkedId)).setChecked(true);
				}
				changeFragment(getFragment(checkedId));
				
				updateFragmentName(checkedId);
			}
		});
		
		spinnerCurSeleIndex = mPreferences.getInt("spinnerCurIndex", 0);
		((RadioButton)(mRadioGroup.getChildAt(spinnerCurSeleIndex))).setChecked(true);
		
		isPlayingLayout = (RelativeLayout) findViewById(R.id.is_playing_content);
		isPlayingLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainTempActivity.this, PlayActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void updateFragmentName(int index) {
		String[] fragmentNameArray = new String[] {
			mContext.getResources().getString(R.string.play_list),
			mContext.getResources().getString(R.string.performer), 
			mContext.getResources().getString(R.string.song), 
			mContext.getResources().getString(R.string.album), 
		};
		
		fragmentName.setText(fragmentNameArray[index]);
	}
	
	/**
	 * 切换fragment页面显示
	 * 
	 * @param fragment
	 */
	private void changeFragment(Fragment fragment) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.content_framelayout, fragment);
		transaction.commit();
	}
	
	private Fragment getFragment(int index) {
		switch (index) {
		case 0:
			spinnerCurSeleIndex = 0;
			return new PlaylistsFragment();
		case 1:
			spinnerCurSeleIndex = 1;
			return new ArtistsFragment();
		case 2:
			spinnerCurSeleIndex = 2;
			return new TracksFragment();
		case 3:
			spinnerCurSeleIndex = 3;
			return new AlbumsFragment();
		default:
			spinnerCurSeleIndex = 2;
			return new TracksFragment();
		}
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		MusicUtils.mService = IApolloService.Stub.asInterface(service);
		
		if (MusicUtils.mService != null 
			&& MusicUtils.getCurrentAudioId() != -1) {
			isPlayingLayout.setVisibility(View.VISIBLE);
		} else {
			isPlayingLayout.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		MusicUtils.mService = null;
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
			
//			if (intent.getAction().equals(ApolloService.META_CHANGED)) {
				//显示跟布局的背景
//				MusicUtils.setRootbootBK(mContext, rootboot);
	            
//	            MusicUtils.updateBottomActionBar(mContext, bottomActionBar, bottombar_songname, bottombar_artistname, bottombarCover);
//			}
			
//            MusicUtils.setPauseButtonImage(mPlay);
			
			if (isPlayingLayout.getVisibility() == View.INVISIBLE) {
				isPlayingLayout.setVisibility(View.VISIBLE);
			}
		}

	};
}
