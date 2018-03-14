package com.dream.mediaplayer.fragment;


import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.activity.PlayActivity;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BottomActionBarFragment extends Fragment implements ServiceConnection {
	
	/**
	 * Handler
	 */
	private static final int REFRESH = 1, UPDATEINFO = 2;
	
	private final static int PLAY = 0x100;
	
	private boolean paused = false;
	
	private ServiceToken mToken;
	
//	private BottomActionBar mBottomActionBar;
	private LinearLayout mBottomActionBar;
	
	private TextView mTrackName;
	
	private TextView mArtistName;
	
	/**
	 * 播放按钮
	 */
	private ImageButton mPlay;
	private ImageButton mPre;
	private ImageButton mNext;
	
//	private VisualizerView mVisualizerView;
	private ImageView mVisualizerView;
	
	private ProgressBar mProgress;
	
	/**
	 * Where we are in the track
	 */
	private long mDuration, mPosOverride = -1;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View root = inflater.inflate(R.layout.bottombar, container);
//        mBottomActionBar = new BottomActionBar(getActivity());
		mBottomActionBar = (LinearLayout) root.findViewById(R.id.name_content);
		mBottomActionBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                intent.setClass(v.getContext(), PlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
			}
		});
		
		// Track name
        mTrackName = (TextView) root.findViewById(R.id.bottombar_songname);

        // Artist name
        mArtistName = (TextView)root.findViewById(R.id.bottombar_artistname);
        
        mPlay = (ImageButton) root.findViewById(R.id.bottombar_play);
        mPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.doPauseResume(mPlay);
				
				/*new Thread(new Runnable() {
					public void run() {
						try {
							if (MusicUtils.mService != null) {
								if (MusicUtils.mService.isPlaying()) {
									MusicUtils.mService.pause();
								} else {
									MusicUtils.mService.play();
								}
							}
							
							mHandler.sendMessage(mHandler.obtainMessage(PLAY));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}).start();*/
			}
		});

		mPre = (ImageButton) root.findViewById(R.id.bottombar_pre);
		mPre.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MusicUtils.mPreBtnDeal();
			}
		});

		mNext = (ImageButton) root.findViewById(R.id.bottombar_next);
        mNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MusicUtils.mNextBtnDeal();
			}
		});

//        mVisualizerView = (VisualizerView) root.findViewById(R.id.visualizerView);
        mVisualizerView = (ImageView) root.findViewById(R.id.visualizerView);
        mVisualizerView.setVisibility(View.INVISIBLE);
//        WeakReference<VisualizerView> mView = new WeakReference<VisualizerView>(mVisualizerView);
//        VisualizerUtils.updateVisualizerView(mView);
        
        mProgress = (ProgressBar) root.findViewById(R.id.bottom_progress);
        mProgress.setMax(1000);
        
		return root;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		paused = false;
		// Bind to Service
		mToken = MusicUtils.bindToService(getActivity(), this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ApolloService.PLAYSTATE_CHANGED);
		filter.addAction(ApolloService.QUEUE_CHANGED);
		filter.addAction(ApolloService.META_CHANGED);
		getActivity().registerReceiver(mMediaStatusReceiver, filter);
		
//		updateMusicInfo();
		long next = refreshNow();
		queueNextRefresh(next);
		
		super.onStart();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		paused = true;
		
		getActivity().unregisterReceiver(mMediaStatusReceiver);
		
		// Unbind
		if (MusicUtils.mService != null)
			MusicUtils.unbindFromService(mToken);
		
		mHandler.removeMessages(REFRESH);
		
		super.onStop();
	}
	
	/**
	 * We need to refresh the time via a Handler
	 */
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH:
				long next = refreshNow();
				queueNextRefresh(next);
				break;
			case UPDATEINFO:
				updateMusicInfo();
				break;
			case PLAY:
				MusicUtils.setPauseButtonImage(mPlay);
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * Update the list as needed
	 */
	private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e("BottomActionBarFragment --- ",
//					"onReceive() --- intent.getAction() = "
//							+ intent.getAction());
			
			if (intent.getAction().equals(ApolloService.META_CHANGED)) {
				mHandler.sendMessage(mHandler.obtainMessage(UPDATEINFO));
			}
			
			updateContent();
			
            MusicUtils.setPauseButtonImage(mPlay);
            
            updateVisualizerView();
		}

	};

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		MusicUtils.mService = IApolloService.Stub.asInterface(service);
		
		updateContent();
		
		MusicUtils.setPauseButtonImage(mPlay);
		
		updateVisualizerView();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		MusicUtils.mService = null;
	}
	
	/**
	 * @return current time
	 */
	private long refreshNow() {
		if (MusicUtils.mService == null)
			return 500;

		try {
			long pos = mPosOverride < 0 ? MusicUtils.mService.position()
					: mPosOverride;
			if ((pos >= 0) && (mDuration > 0)) {
//				mCurrentTime.setText(MusicUtils.makeTimeString(mContext,
//						pos / 1000));
				mProgress.setProgress((int) (1000 * pos / mDuration));

				if (MusicUtils.mService.isPlaying()) {
					// mCurrentTime.setVisibility(View.VISIBLE);
					// mCurrentTime.setTextColor(getResources().getColor(R.color.transparent_black));
				} else {
					// blink the counter
					// int col = mCurrentTime.getCurrentTextColor();
					// mCurrentTime.setTextColor(col == getResources().getColor(
					// R.color.transparent_black) ? getResources().getColor(
					// R.color.holo_blue_dark) : getResources().getColor(
					// R.color.transparent_black));
					return 500;
				}
			} else {
//				mCurrentTime.setText("--:--");
				mProgress.setProgress(1000);
			}
			
			// calculate the number of milliseconds until the next full second, so
            // the counter can be updated at just the right time
            long remaining = 1000 - (pos % 1000);

            // approximate how often we would need to refresh the slider to
            // move it smoothly
            int width = mProgress.getWidth();
            if (width == 0) width = 320;
            long smoothrefreshtime = mDuration / width;

            if (smoothrefreshtime > remaining) return remaining;
            if (smoothrefreshtime < 20) return 20;
            return smoothrefreshtime;
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 500;
	}
	
	/**
	 * @param delay
	 */
	private void queueNextRefresh(long delay) {
		if (!paused) {
			Message msg = mHandler.obtainMessage(REFRESH);
			mHandler.removeMessages(REFRESH);
			mHandler.sendMessageDelayed(msg, delay);
		}
	}
	
	/**
	 * Update what's playing
	 */
	private void updateMusicInfo() {
		if (MusicUtils.mService == null) {
			return;
		}
		
		mDuration = MusicUtils.getDuration();
	}
	
	private void updateVisualizerView() {
		try {
			if (MusicUtils.mService != null
				&& MusicUtils.mService.getAudioId() != -1
				&& MusicUtils.mService.getPath() != null) {
				mVisualizerView.setVisibility(View.VISIBLE);
				mVisualizerView.setImageResource(R.drawable.visualizer_anim);
		        AnimationDrawable animationDrawable = (AnimationDrawable)mVisualizerView.getDrawable();
                if (MusicUtils.mService.isPlaying()) {
                	animationDrawable.start();
                } else {
                	animationDrawable.stop();
                }
			} else {
				mVisualizerView.setVisibility(View.INVISIBLE);
                mVisualizerView.setImageResource(0);
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
			mVisualizerView.setVisibility(View.INVISIBLE);
            mVisualizerView.setImageResource(0);
		}
	}
	
	private void updateContent() {
		try {
	        if (MusicUtils.mService != null/* && MusicUtils.getCurrentAudioId() != -1*/) {
	
	            // Track name
	            mTrackName.setText(MusicUtils.mService.getTrackName());
	
	            // Artist name
	            String artistName = MusicUtils.mService.getArtistName();
	            if (MediaStore.UNKNOWN_STRING.equals(artistName)) {
	                artistName = getActivity().getString(R.string.unknown_artist_name);
	            }
	            mArtistName.setText(artistName);
	
	            // Album art
	           /* ImageView mAlbumArt = (ImageView)bottomActionBar
	                    .findViewById(R.id.bottombar_cover);
	            
	            try {
					Bitmap bitmap = MusicUtils.mService.getAlbumBitmap();
					if (bitmap == null) {
						bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.bottom_bar_default_cover);
					} 
					ImageUtils.setLoadedBitmap(mAlbumArt, bitmap);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        }
        } catch (RemoteException e) {
        	
        } catch (NullPointerException e) {
        	
        }
	}
}
