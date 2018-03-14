package com.dream.mediaplayer.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.fragment.TrackBrowserFragment;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;
import com.dream.mediaplayer.ui.widgets.RepeatingImageButton;
import com.dream.mediaplayer.views.FadeFrameLayout;
import com.dream.mediaplayer.views.FadeFrameLayoutImageView;
import com.dream.mediaplayer.views.FadeImageView;
import com.dream.mediaplayer.views.VolumePopupWindow;
import com.lg.lrcview_master.DefaultLrcParser;
import com.lg.lrcview_master.LrcRow;
import com.lg.lrcview_master.LrcView;
import com.lg.lrcview_master.LrcView.OnLrcClickListener;
import com.lg.lrcview_master.LrcView.OnSeekToListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PlayActivity extends Activity implements ServiceConnection {

	private String tag = "PlayActivity --- ";
	private Context mContext;
	private RelativeLayout rootboot;
	private ServiceToken mToken;

	/**
	 * Track, album, and artist name
	 */
	private TextView mTrackName, mArtistName;

	/**
	 * Total and current time
	 */
	private TextView mTotalTime, mCurrentTime;

	/**
	 */
//	private TextView mRepeatTextView, mShuffleTextView;

	/**
	 * Album art
	 */
	private FadeImageView mAlbumArt;

	/**
	 * Controls
	 */
	private ImageButton mRepeat, mShuffle, mPlay, mFavorite/*, mVolume*/;

	/**
	 * Controls
	 */
	private RepeatingImageButton mPrev, mNext;

	/**
	 * Progress
	 */
	private SeekBar mProgress;

	/**
	 * Where we are in the track
	 */
	private long mDuration, mLastSeekEventTime, mPosOverride = -1,
			mStartSeekPos = 0;

	private boolean mFromTouch, paused = false;

	/**
	 * Handler
	 */
	private static final int REFRESH = 1, UPDATEINFO = 2;

	private final static int PLAY = 0x100;

	private VolumePopupWindow volumePopupWindow;

//	private FrameLayout mFrameLayout;

//	private RelativeLayout mRelativeLayout;

//	private TextView mTextView;

	private ImageButton btnList;

	FragmentManager fragmentManager;

	TrackBrowserFragment trackBrowserFragment;

	private FrameLayout nPlay_album_layout;

	private FadeFrameLayout nPlay_big_lyric_layout;

	private FadeFrameLayoutImageView imageFadeFrameLayout;

	private LrcView lrcView;

	private boolean next = true;

	private SeekBar mProgressVolume;

	private AudioManager audioManager;
	private Intent mIntent;

	/**
	 * 因为本身系统的声音数值的最高值相对较小
	 * 为了提高seekbar拖动对声音改变的精度
	 * 所以对seekbar的最高值 当前值都乘以一个倍率
	 * 对seekbar改变的progress除以一个倍率
	 */
	private static final int MULTIPLYING_POWER = 16;

	private Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ApolloUtils.setPlayActivityStatusBar(this);

		getWindow().setAllowEnterTransitionOverlap(true);

		mContext = this;

		setContentView(R.layout.activity_play);

		// Control Media volume
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

		fragmentManager = getFragmentManager();

		init();

		mIntent = getIntent();
//		playIntentMedia (mIntent);
// 		updateMusicInfo();
//		setPauseButtonImage();
//		MusicUtils.setRootbootBK(mContext, rootboot);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		mIntent = intent;
//		playIntentMedia (intent);			
	}
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (fragmentManager.getBackStackEntryCount() > 0) {
//			mTextView.setTextColor(getResources().getColor(R.color.white));
			fragmentManager.popBackStack();
		} else if (fragmentManager.getBackStackEntryCount() == 0) {
			MusicUtils.finishTranstion((Activity)mContext, true);
		}
	}

	@Override
	protected void onStart() {
		paused = false;

		// Bind to Service
		mToken = MusicUtils.bindToService(this, this);
		IntentFilter filter = new IntentFilter();
		filter.addAction("Broadcast one!");
		filter.addAction(ApolloService.META_CHANGED);
		filter.addAction(ApolloService.PLAYSTATE_CHANGED);
		registerReceiver(mMediaStatusReceiver, filter);
		updateMusicInfo();
		long next = refreshNow();
		queueNextRefresh(next);

		//每次返回前台前都更新音量条
		if (mProgressVolume != null) {
			mProgressVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
		}

		super.onStart();
	}

	@Override
	protected void onStop() {
		paused = true;
		// Unbind
		if (MusicUtils.mService != null) {
			MusicUtils.unbindFromService(mToken);
			mToken = null;
		}

		unregisterReceiver(mMediaStatusReceiver);

		mHandler.removeMessages(REFRESH);

		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateMusicInfo();
		setPauseButtonImage();
		if (mIntent!=null){
			playIntentMedia (mIntent);
			mIntent=null;
		}
//        long next = refreshNow();
//		queueNextRefresh(next);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		MusicUtils.mService = IApolloService.Stub.asInterface(service);

		MusicUtils.setRootbootBK(mContext, rootboot);

		updateMusicInfo();

		lrcView.setLrcRows(getLrcRows());

		setPauseButtonImage();
		setShuffleButtonImage();
		setRepeatButtonImage();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		MusicUtils.mService = null;
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
					refreshNow();
					setPauseButtonImage();
					break;
				default:
					break;
			}
		}
	};

	/**
	 * Update the MenuItems in the ActionBar
	 */
	private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals("Broadcast one!")) {
				Toast.makeText(PlayActivity.this,getResources().getString(R.string.please_select_a_track),Toast.LENGTH_SHORT).show();
				finish();
			}
			if (intent.getAction().equals(ApolloService.META_CHANGED)) {
				mHandler.sendMessage(mHandler.obtainMessage(UPDATEINFO));
				MusicUtils.setRootbootBK(mContext, rootboot);
				lrcView.setLrcRows(getLrcRows());
				updateFavoriteBtn();
			}

			setPauseButtonImage();
			setShuffleButtonImage();
			setRepeatButtonImage();
		}

	};

	private void scanBackward(int repcnt, long delta) {
		if (MusicUtils.mService == null)
			return;
		try {
			if (repcnt == 0) {
				mStartSeekPos = MusicUtils.mService.position();
				mLastSeekEventTime = 0;
			} else {
				if (delta < 5000) {
					// seek at 10x speed for the first 5 seconds
					delta = delta * 10;
				} else {
					// seek at 40x after that
					delta = 50000 + (delta - 5000) * 40;
				}
				long newpos = mStartSeekPos - delta;
				if (newpos < 0) {
					// move to previous track
					MusicUtils.mService.prev();
					long duration = MusicUtils.mService.duration();
					mStartSeekPos += duration;
					newpos += duration;
				}
				if (((delta - mLastSeekEventTime) > 250) || repcnt < 0) {
					MusicUtils.mService.seek(newpos);
					mLastSeekEventTime = delta;
				}
				if (repcnt >= 0) {
					mPosOverride = newpos;
				} else {
					mPosOverride = -1;
				}
				refreshNow();
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	private void scanForward(int repcnt, long delta) {
		if (MusicUtils.mService == null)
			return;
		try {
			if (repcnt == 0) {
				mStartSeekPos = MusicUtils.mService.position();
				mLastSeekEventTime = 0;
			} else {
				if (delta < 5000) {
					// seek at 10x speed for the first 5 seconds
					delta = delta * 10;
				} else {
					// seek at 40x after that
					delta = 50000 + (delta - 5000) * 40;
				}
				long newpos = mStartSeekPos + delta;
				long duration = MusicUtils.mService.duration();
				if (newpos >= duration) {
					// move to next track
					MusicUtils.mService.next();
					mStartSeekPos -= duration; // is OK to go negative
					newpos -= duration;
				}
				if (((delta - mLastSeekEventTime) > 250) || repcnt < 0) {
					MusicUtils.mService.seek(newpos);
					mLastSeekEventTime = delta;
				}
				if (repcnt >= 0) {
					mPosOverride = newpos;
				} else {
					mPosOverride = -1;
				}
				refreshNow();
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Scan backwards
	 */
	private final RepeatingImageButton.RepeatListener mRewListener = new RepeatingImageButton.RepeatListener() {
		@Override
		public void onRepeat(View v, long howlong, int repcnt) {
			scanBackward(repcnt, howlong);
		}
	};

	/**
	 * Scan forwards
	 */
	private final RepeatingImageButton.RepeatListener mFfwdListener = new RepeatingImageButton.RepeatListener() {
		@Override
		public void onRepeat(View v, long howlong, int repcnt) {
			scanForward(repcnt, howlong);
		}
	};

	/**
	 * Update what's playing
	 */
	
//    public String getRealPathFromURI(Uri contentUri) {
//        String res = null;
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        if(cursor.moveToFirst()){;
//           int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//           res = cursor.getString(column_index);
//        }
//        cursor.close();
//        return res;
//    }  	
	private void updateMusicInfo() {
		if (MusicUtils.mService == null) {
			return;
		}
		try {
//			String path = MusicUtils.mService.getPath();
//			String testPath = getRealPathFromURI(Uri.parse(path));		
//	        if (path == null) {
//	        	MusicUtils.finishTranstion((Activity)mContext, false);
//	            return;
//	        }

			String artistName = MusicUtils.getArtistName();
			String trackName = MusicUtils.getTrackName();
			mTrackName.setText(trackName);
			mArtistName.setText(artistName);
			mDuration = MusicUtils.getDuration();
			mTotalTime.setText(MusicUtils
					.makeTimeString(mContext, mDuration / 1000));
			Bitmap bitmap = MusicUtils.mService.getAlbumBitmap();
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.play_activity_default_cover);
			}
			mAlbumArt.setImageBitmap(bitmap);
//			mAlbumArt.setImageBitmapAnim(bitmap);
//			ImageUtils.setLoadedBitmap(mAlbumArt, bitmap);
		} catch (RemoteException e) {
			MusicUtils.finishTranstion((Activity)mContext, false);
		} catch (NullPointerException e) {
			// we might not actually have the service yet
			e.printStackTrace();
		}
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
	 */
	private void updateFavoriteBtn() {
		if (MusicUtils.mService != null && MusicUtils.getCurrentAudioId() != -1) {
			if (MusicUtils.isFavorite(this, MusicUtils.getCurrentAudioId())) {
				mFavorite.setImageResource(R.drawable.btn_play_favorite_select_selector);
			} else {
				mFavorite.setImageResource(R.drawable.btn_play_favorite_unselect_selector);
			}
		}
	}

	/**
	 */
	private void init() {
//		rootboot = (RelativeLayout) findViewById(R.id.top_content);
		rootboot = (RelativeLayout) findViewById(R.id.rootboot);

		mTrackName = (TextView) findViewById(R.id.play_songname);
		mArtistName = (TextView) findViewById(R.id.play_artistname);

		mTotalTime = (TextView) findViewById(R.id.play_total_time);
		mCurrentTime = (TextView) findViewById(R.id.play_current_time);

		mAlbumArt = (FadeImageView) findViewById(R.id.imageCover);

		mFavorite = (ImageButton) findViewById(R.id.btn_favorite);
//		mVolume = (ImageButton) findViewById(R.id.btn_volume);

		mRepeat = (ImageButton) findViewById(R.id.btn_repeat);
		mPlay = (ImageButton) findViewById(R.id.nPlay_play_playPause);
		mShuffle = (ImageButton) findViewById(R.id.btn_shuffle);
		mPrev = (RepeatingImageButton) findViewById(R.id.nPlay_play_pre);
		mNext = (RepeatingImageButton) findViewById(R.id.nPlay_play_next);

//		mRepeatTextView = (TextView) findViewById(R.id.text_repeat);
//		mShuffleTextView = (TextView) findViewById(R.id.text_shuffle);

		updateFavoriteBtn();
		mFavorite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.toggleFavorite();
				updateFavoriteBtn();

				if (MusicUtils.mService != null && MusicUtils.getCurrentAudioId() != -1) {
					if (MusicUtils.isFavorite(mContext, MusicUtils.getCurrentAudioId())) {
						showToast(R.string.add_to_favorite);
					} else {
						showToast(R.string.delete_to_favorite);
					}
				}
			}
		});

//		mVolume.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				showVolume();
//			}
//		});
//		mVolume.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				Log.e(tag, "mVolume --- onKey() --- event.getKeyCode() = "+event.getKeyCode());
//				return false;
//			}
//		});

		mRepeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cycleRepeat();
			}
		});

		mPrev.setRepeatListener(mRewListener, 260);
		mPrev.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				if (MusicUtils.mService == null)
//					return;
//				try {
//					if (MusicUtils.mService.position() > 0) {
//						MusicUtils.mService.prev();
//					} else {
//						MusicUtils.mService.seek(0);
//						MusicUtils.mService.play();
//					}
//				} catch (RemoteException ex) {
//					ex.printStackTrace();
//				}
				if (MusicUtils.mService == null)
					return;
				try {
					if (next){
						int shuffle = MusicUtils.mService.getShuffleMode();
						int histSize = MusicUtils.mService.getHistSize();
						long pos = MusicUtils.mService.position();
						if (pos < 2000L) {
							if ((shuffle == ApolloService.SHUFFLE_NORMAL) && (histSize == 0 || histSize == 1)) {
								Toast.makeText(PlayActivity.this, getResources().getString(R.string.first_track_of_random_trackist)
										, Toast.LENGTH_SHORT).show();
								MusicUtils.mService.seek(0);
								MusicUtils.mService.play();
							} else {
								MusicUtils.mService.prev();
							}
						} else {
							MusicUtils.mService.seek(0);
							MusicUtils.mService.play();
						}

						next = false;
						new Thread() {
							public void run() {
								try{
									sleep(200);
									next = true;
								}catch(Exception e) {
									next = true;
								}
							}
						}.start();
					}
				} catch (RemoteException ex) {
					next = true;
				}
			}
		});

		mPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doPauseResume();
			}
		});

		mNext.setRepeatListener(mFfwdListener, 260);
		mNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (MusicUtils.mService == null){
					return;
				}
				try {
					if (next) {
						MusicUtils.mService.next();
						next = false;
						new Thread() {
							public void run() {
								try {
									sleep(200);
									next = true;
								}catch(Exception e) {
									next = true;
								}
							}
						}.start();
					}
				} catch (RemoteException ex) {
					next = true;
					ex.printStackTrace();
				}
			}
		});

		mShuffle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggleShuffle();
			}
		});

		mProgress = (SeekBar) findViewById(R.id.seekbar);
		if (mProgress instanceof SeekBar) {
			SeekBar seeker = mProgress;
			seeker.setOnSeekBarChangeListener(mSeekListener);
		}
		mProgress.setMax(1000);
		mProgressVolume = (SeekBar) findViewById(R.id.volume_seekbar);
		mProgressVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
		mProgressVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
		mProgressVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar VerticalSeekBar){
				// TODO Auto-generated method stub

			}
			@Override
			public void onStartTrackingTouch(SeekBar VerticalSeekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar VerticalSeekBar,
										  int progress, boolean fromUser) {
				// TODO Auto-generated method stub
//				Log.e("11", "aaaaaaaa fromUser = "+fromUser);
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress / MULTIPLYING_POWER , 0);
				int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)*MULTIPLYING_POWER;  //获取当前值
			}
		});

//		mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_page);
		btnList = (ImageButton) findViewById(R.id.btn_list);
		btnList.setOnClickListener(new OnClickListener() {

			@Override
//		mFrameLayout = (FrameLayout) findViewById(R.id.frameLayout);
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fragmentManager.getBackStackEntryCount() == 0) {
//					mTextView.setTextColor(getResources().getColor(R.color.listview_item_textview_select_color));

					if (trackBrowserFragment == null) {
						Bundle bundle = new Bundle();
						bundle.putString("action", Intent.ACTION_EDIT);
						bundle.putString("playlist", "nowplaying");
						trackBrowserFragment = new TrackBrowserFragment(bundle);
					}

					FragmentTransaction transaction = fragmentManager.beginTransaction();
					transaction.setCustomAnimations(
							R.animator.fragment_slide_top_enter,
							R.animator.fragment_slide_top_exit,
							R.animator.fragment_slide_bottom_enter,
							R.animator.fragment_slide_bottom_exit);
					FrameLayout showcontent=(FrameLayout) findViewById(R.id.curlist_fragment);
					showcontent.setVisibility(View.VISIBLE);
					transaction.add(R.id.curlist_fragment, trackBrowserFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				} else if (fragmentManager.getBackStackEntryCount() > 0) {
//					mTextView.setTextColor(getResources().getColor(R.color.white));

					fragmentManager.popBackStack();
				}
			}
		});

		nPlay_album_layout = (FrameLayout) findViewById(R.id.nPlay_album_layout);
		nPlay_album_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setLrcLayoutAndImageCoverVisibility();
			}
		});

		imageFadeFrameLayout = (FadeFrameLayoutImageView) findViewById(R.id.imageFadeFrameLayout);

		nPlay_big_lyric_layout = (FadeFrameLayout) findViewById(R.id.nPlay_big_lyric_layout);
		lrcView = (LrcView) findViewById(R.id.lrcView);
		lrcView.setOnSeekToListener(onSeekToListener);
		lrcView.setOnLrcClickListener(onLrcClickListener);
		lrcView.setLrcScalingFactor(LrcView.MAX_SCALING_FACTOR);
	}

	private void setLrcLayoutAndImageCoverVisibility() {
		if (nPlay_big_lyric_layout.getVisibility() == View.INVISIBLE) {
			nPlay_big_lyric_layout.setVisiblityVisible();
			imageFadeFrameLayout.setVisiblityGone();
		} else if (nPlay_big_lyric_layout.getVisibility() == View.VISIBLE) {
			nPlay_big_lyric_layout.setVisiblityGone();
			imageFadeFrameLayout.setVisiblityVisible();
		}
	}

	/**
	 *
	 * @return
	 */
	private List<LrcRow> getLrcRows() {
		List<LrcRow> rows = null;
		String url = "";
		if (MusicUtils.mService != null) {
			try {
				url = MusicUtils.mService.getData();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (url != null && !url.equals("")) {
			int indexPoint = url.lastIndexOf(".");
			String tempString = url.substring(0, indexPoint);
			String lrcPath = tempString + ".lrc";
			File lrcFile = new File(lrcPath);
			if (lrcFile.isFile()) {
				try {
					/*
					 * InputStream in = new FileInputStream(lrcFile); byte[] b =
					 * new byte[3]; in.read(b); in.close();
					 *
					 * boolean isUtf8Type = false; if (b[0] == -17 && b[1] ==
					 * -69 && b[2] == -65) { isUtf8Type = true; } else {
					 * isUtf8Type = false; }
					 *
					 * File lrcFileCopy = new File(lrcPath); FileInputStream
					 * stream = new FileInputStream(lrcFileCopy);//
					 * context.openFileInput(file); BufferedReader br = null; if
					 * (isUtf8Type) { br = new BufferedReader( new
					 * InputStreamReader(stream, "UTF-8")); } else { br = new
					 * BufferedReader( new InputStreamReader(stream, "GB2312"));
					 * }
					 */

					/*
					 * InputStreamReader isr =
					 * Utils.getInputStreamReader(lrcPath); BufferedReader br =
					 * new BufferedReader(isr);
					 */

					/*
					 * File lrcFileCopy = new File(lrcPath); FileInputStream in
					 * = new FileInputStream(lrcFileCopy); String code =
					 * Utils.getTxtEncode(in); Log.e(TAG,
					 * "getLrcRows() -- code = "+code); InputStream stream = new
					 * FileInputStream(lrcFile); BufferedReader br = new
					 * BufferedReader( new InputStreamReader(stream, code));
					 */

					InputStream stream = new FileInputStream(lrcFile);
					byte b[] = new byte[3];
					stream.read(b);
					BufferedReader br;
					//  if the lrc file's encoding is utf-8
					if(b[0] == -17 && b[1] == -69 && b[2] == -65){
							br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
					}else{
						// otherwise,read the lrc file in GBK Encoding
						br = new BufferedReader(new InputStreamReader(stream,"GBK"));
					}
					String line;
					StringBuffer sb = new StringBuffer();
					while ((line = br.readLine()) != null) {
						sb.append(line + "\n");
					}

					// isr.close();
					stream.close();

					// System.out.println(sb.toString());

					rows = DefaultLrcParser.getIstance().getLrcRows(
							sb.toString());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return rows;
	}

	/**
	 * Set the play and pause image
	 */
	private void setPauseButtonImage() {
		/*try {
			if (MusicUtils.mService != null && MusicUtils.mService.isPlaying()) {
				mPlay.setImageResource(R.drawable.btn_play_pause_selector);
			} else {
				mPlay.setImageResource(R.drawable.btn_play_play_selector);
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}*/

		try {
			if (MusicUtils.mService != null
					&& MusicUtils.mService.getAudioId() != -1
					&& MusicUtils.mService.getPath() != null) {
				if (MusicUtils.mService.isPlaying()) {
					mPlay.setImageResource(R.drawable.btn_play_pause_selector);
				} else {
					mPlay.setImageResource(R.drawable.btn_play_play_selector);
				}
			} else {
				mPlay.setImageResource(R.drawable.btn_play_play_selector);
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Play and pause music
	 */
	private void doPauseResume() {
		try {
			if (MusicUtils.mService != null) {
				if (MusicUtils.mService.isPlaying()) {
					MusicUtils.mService.pause();
				} else {
					MusicUtils.mService.play();
				}
			}
			refreshNow();
			setPauseButtonImage();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}

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

	/**
	 * Cycle repeat states
	 */
	private void cycleRepeat() {
		if (MusicUtils.mService == null) {
			return;
		}
		try {
			int mode = MusicUtils.mService.getRepeatMode();
			if (mode == ApolloService.REPEAT_NONE) {
				MusicUtils.mService.setRepeatMode(ApolloService.REPEAT_ALL);
				MusicUtils.mService.setShuffleMode(ApolloService.SHUFFLE_NONE);
				setShuffleButtonImage();
				showToast(R.string.repeat_all_notif);
			} else if (mode == ApolloService.REPEAT_ALL) {
				MusicUtils.mService.setRepeatMode(ApolloService.REPEAT_CURRENT);
				if (MusicUtils.mService.getShuffleMode() != ApolloService.SHUFFLE_NONE) {
					MusicUtils.mService
							.setShuffleMode(ApolloService.SHUFFLE_NONE);
					setShuffleButtonImage();
				}
				showToast(R.string.repeat_current_notif);
			} else {
				MusicUtils.mService.setRepeatMode(ApolloService.REPEAT_NONE);
				showToast(R.string.repeat_off_notif);
			}
			setRepeatButtonImage();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Set the repeat images
	 */
	private void setRepeatButtonImage() {
		if (MusicUtils.mService == null)
			return;
		try {
			switch (MusicUtils.mService.getRepeatMode()) {
				case ApolloService.REPEAT_ALL:
					mRepeat.setImageResource(R.drawable.btn_play_repeat_all_selector);
//				mRepeatTextView.setText(R.string.play_repeat_all);
					break;
				case ApolloService.REPEAT_CURRENT:
					mRepeat.setImageResource(R.drawable.btn_play_repeat_single_selector);
//				mRepeatTextView.setText(R.string.play_repeat_single);
					break;
				default:
					mRepeat.setImageResource(R.drawable.btn_play_repeat_close_selector);
//				mRepeatTextView.setText(R.string.play_repeat_close);
					break;
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Set the shuffle images
	 */
	private void setShuffleButtonImage() {
		if (MusicUtils.mService == null)
			return;
		try {
			switch (MusicUtils.mService.getShuffleMode()) {
				case ApolloService.SHUFFLE_NONE:
					mShuffle.setImageResource(R.drawable.btn_play_shuffle_close_selector);
//				mShuffleTextView.setText(R.string.play_shuffle_close);
					break;
				case ApolloService.SHUFFLE_AUTO:
					mShuffle.setImageResource(R.drawable.btn_play_shuffle_open_selector);
					mRepeat.setImageResource(R.drawable.btn_play_repeat_close_selector);
//				mShuffleTextView.setText(R.string.play_shuffle_open);
					break;
				default:
					mShuffle.setImageResource(R.drawable.btn_play_shuffle_open_selector);
//				mShuffleTextView.setText(R.string.play_shuffle_open);
					break;
			}
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Set the shuffle mode
	 */
	private void toggleShuffle() {
		if (MusicUtils.mService == null) {
			return;
		}
		try {
			int shuffle = MusicUtils.mService.getShuffleMode();
			if (shuffle == ApolloService.SHUFFLE_NONE) {
				MusicUtils.mService
						.setShuffleMode(ApolloService.SHUFFLE_NORMAL);
//				if (MusicUtils.mService.getRepeatMode() == ApolloService.REPEAT_CURRENT) {
//					MusicUtils.mService.setRepeatMode(ApolloService.REPEAT_ALL);
//					setRepeatButtonImage();
//				}
				MusicUtils.mService.setRepeatMode(ApolloService.REPEAT_NONE);
				setRepeatButtonImage();
				showToast(R.string.shuffle_on_notif);
			} else if (shuffle == ApolloService.SHUFFLE_NORMAL
					|| shuffle == ApolloService.SHUFFLE_AUTO) {
				MusicUtils.mService.setShuffleMode(ApolloService.SHUFFLE_NONE);
				showToast(R.string.shuffle_off_notif);
			}
			setShuffleButtonImage();
		} catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return current time
	 */
	private long refreshNow() {
		if (MusicUtils.mService == null){
			return 500;
		}
		try {
			long pos = mPosOverride < 0 ? MusicUtils.mService.position()
					: mPosOverride;
			if ((pos >= 0) && (mDuration > 0)) {
				mCurrentTime.setText(MusicUtils.makeTimeString(mContext,
						pos / 1000));
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
				mCurrentTime.setText("--:--");
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

	private void showVolume() {
//		if (volumePopupWindow != null) {
//			volumePopupWindow = null;
//		}
//
//		volumePopupWindow = new VolumePopupWindow(mContext);
//		volumePopupWindow.showAsDropDown(mVolume, 0, ApolloUtils.dp2px(mContext, -270));
	}

	/**
	 * 显示toast
	 * @param resid
	 */
	private void showToast(int resid) {
		if (mToast == null) {
			mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(resid);
		mToastHandler.postDelayed(mRun, 800);
		mToast.show();
	}

	private Handler mToastHandler = new Handler();
	private Runnable mRun = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
				case KeyEvent.KEYCODE_VOLUME_DOWN:
					seekDown();

					return true;
				case KeyEvent.KEYCODE_VOLUME_UP:
					seekUp();

					return true;
			}
		}

		return super.dispatchKeyEvent(event);
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		Log.e(tag, "onKeyDown() --- event.getKeyCode() = "+event.getKeyCode());
//		switch (event.getKeyCode()) {
//        case KeyEvent.KEYCODE_VOLUME_DOWN:
//        	if (volumePopupWindow != null) {
//	        	if (volumePopupWindow.isShowing()) {
//		        	if (volumePopupWindow != null) {
//		        		volumePopupWindow.seekDown();
//		        	}
//	        	} else {
//	        		showVolume();
//	        	}
//        	} else {
//        		showVolume();
//        	}
//        	return false;
//        case KeyEvent.KEYCODE_VOLUME_UP:
//        	if (volumePopupWindow != null) {
//	        	if (volumePopupWindow.isShowing()) {
//		        	if (volumePopupWindow != null) {
//		        		volumePopupWindow.seekUp();
//		        	}
//	        	} else {
//	        		showVolume();
//	        	}
//        	} else {
//        		showVolume();
//        	}
//            return false;
//        }
//
//		return super.onKeyDown(keyCode, event);
//	}

	/**
	 * Drag to a specfic duration
	 */
	private final OnSeekBarChangeListener mSeekListener = new OnSeekBarChangeListener() {
		@Override
		public void onStartTrackingTouch(SeekBar bar) {
			mLastSeekEventTime = 0;
			mFromTouch = true;
		}

		@Override
		public void onProgressChanged(SeekBar bar, int progress,
									  boolean fromuser) {

			if (MusicUtils.mService != null) {
				if (lrcView != null) {
					long lrcProgress = mDuration * progress / 1000;
					lrcView.seekTo((int)lrcProgress, true, fromuser);
				}
			}

			if (!fromuser || (MusicUtils.mService == null))
				return;

			long now = SystemClock.elapsedRealtime();
			mPosOverride = mDuration * progress / 1000;
			if ((now - mLastSeekEventTime) > 10) {
				mLastSeekEventTime = now;
//				mPosOverride = mDuration * progress / 1000;
//				try {
//					MusicUtils.mService.seek(mPosOverride);
//				} catch (RemoteException ex) {
//					ex.printStackTrace();
//				}

				if (!mFromTouch) {
					refreshNow();
					mPosOverride = -1;
				}
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar bar) {
			try {
				if (null != MusicUtils.mService) {
					MusicUtils.mService.seek(mPosOverride);
				}
			} catch (RemoteException ex) {
			}

			mPosOverride = -1;
			mFromTouch = false;
		}
	};

	OnSeekToListener onSeekToListener = new OnSeekToListener() {

		@Override
		public void onSeekTo(int progress) {
			if (MusicUtils.mService == null)
				return;

			try {
				MusicUtils.mService.seek(progress);
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
		}
	};

	OnLrcClickListener onLrcClickListener = new OnLrcClickListener() {

		@Override
		public void onClick() {
			setLrcLayoutAndImageCoverVisibility();
		}
	};

	/**
	 * 音量上升
	 */
	public void seekUp() {
		if(mProgressVolume.getProgress() < mProgressVolume.getMax()) {
			mProgressVolume.setProgress(mProgressVolume.getProgress() + 1*MULTIPLYING_POWER);
//            seekBar.onSizeChanged();
		}
	}

	/**
	 * 音量下降
	 */
	public void seekDown(){
		if(mProgressVolume.getProgress() > 0) {
			mProgressVolume.setProgress(mProgressVolume.getProgress() - 1*MULTIPLYING_POWER);
//            seekBar.onSizeChanged();
		}
	}
	
	public void playIntentMedia(Intent intent){
		int playType =intent.getIntExtra("playtype", 0); 
		if (playType==1){
			long id = intent.getLongExtra("id", -1);
			boolean enqueue = intent.getBooleanExtra("enqueue", false);
			if (id!=-1){
				long[] list = new long[] { id };
				if (!enqueue) {
					// Remove the actual queue
					MusicUtils.removeAllTracks();
					MusicUtils.playAll(getApplicationContext(), list, 0);
				} else {
					MusicUtils.addToCurrentPlaylist(getApplicationContext(), list);
				}			
			}
		}else if (playType==2){
			String file = intent.getStringExtra("filename");
			try {
				MusicUtils.mService.stop();
				MusicUtils.mService.openFile(file);
				MusicUtils.mService.play();	
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}			
	}
}
