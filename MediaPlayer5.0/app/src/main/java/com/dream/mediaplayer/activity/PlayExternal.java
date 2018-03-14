/*
 * Copyright (C) 2012 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dream.mediaplayer.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.MediaScannerFile;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.helpers.utils.permissionCheckPlayExternal;
import com.dream.mediaplayer.service.ServiceToken;

/**
 */
public class PlayExternal extends Activity implements ServiceConnection/*
																		 * ,
																		 * DialogInterface
																		 * .
																		 * OnCancelListener
																		 */,
		MediaScannerConnection.OnScanCompletedListener {

	private static final String TAG = "PlayExternal";

	private Context mContext;

	private ServiceToken mToken;
	private Uri mUri;

	private MediaScannerConnection mScanConnection;
	private ProgressDialog dialog;

	private long mMediaId = -1;

	private String file;

	private MyHandler myHandler;

	private final static int MSG_SCAN_FINISHED = 0x10;
	private Thread scanFileThread;

	public static boolean startFlag = false;
	public permissionCheckPlayExternal permissionCheck1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		permissionCheck1 = new permissionCheckPlayExternal(this);
		if (!permissionCheck1.flag) {
			init();
			startFlag = true;
			permissionCheck1.flag=false;
			permissionCheck1.flagStop=false;
		}
	}

	public void init(){
		ApolloUtils.setStatusBar(this);

		Log.e(TAG, "onCreate() -1- id = ");

		// Get the external file to play
		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}
		mUri = intent.getData();

		if (mUri == null) {
			finish();
			return;
		}

		mContext = this;
		myHandler = new MyHandler();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		permissionCheck1.onActivityResultTest(this, requestCode, resultCode);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		setIntent(intent);
		Log.e(TAG, "onNewIntent() -1- id = ");
		// Get the external file to play
		Intent intentNew = getIntent();
		if (intentNew == null) {
			finish();
			return;
		}
		mUri = intentNew.getData();
		if (mUri == null) {
			finish();
			return;
		}

		if (MusicUtils.mService != null) {
//			Log.e("haaaaa", "onNewIntent: ");
			play(this.mUri);
		}
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder obj) {
		MusicUtils.mService = IApolloService.Stub.asInterface(obj);
		if (!permissionCheck1.flag) {
//			startFlag = true;
//			permissionCheck1.flag = false;
//			permissionCheck1.flagStop = false;
			play(this.mUri);
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		MusicUtils.mService = null;
	}

	@Override
	protected void onStart() {
		// Bind to Service
		if (!permissionCheck1.flagStop&&startFlag==false) {
			init();
			startFlag = true;
			permissionCheck1.flag = false;
			permissionCheck1.flagStop = false;
		}
		mToken = MusicUtils.bindToService(this, this);
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (null != mScanConnection && mScanConnection.isConnected()) {
			mScanConnection.disconnect();
		}

		// Unbind
		if (MusicUtils.mService != null)
			MusicUtils.unbindFromService(mToken);
		super.onStop();
	}

	// @SuppressLint("NewApi")
	private void play(Uri uri) {
		if (MusicUtils.mService == null) {
			finish();
			return;
		}
		file = uri.getPath();
		String scheme = uri.getScheme();
		if ("file".equals(scheme)) {
			long id = -1;
			try {
				id = MusicUtils.mService.getIdFromPath(file);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.e(TAG, "play() -1- id = " + id+", file = "+file);
			if (id == -1) {
				// Open the stream, But we will not have album information
				Log.e(TAG, "play() -2-filePath = " + file);
				scanFileThread = new Thread(new Runnable() {
					@Override
					public void run() {
						scanFile(file);
					}
				});
				scanFileThread.start();
				showProgressDialog();
			} else if (id >= 0) {
				playOrEnqueuFile(id, false);
			}
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
        	AsyncQueryHandler mAsyncQueryHandler = new AsyncQueryHandler(getContentResolver()) {
                @Override
                protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                	dismissProgressDialog();
                	boolean isPlay = false;
                    if (cursor != null && cursor.moveToFirst()) {
                        int idIdx = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                        if (idIdx >=0) {
                            mMediaId = cursor.getLong(idIdx);
                            
                            if (mMediaId != -1) {
                            	isPlay = true;
                            	playOrEnqueuFile(mMediaId, false);
                            }
                        }
                    } else {
                        Log.w(TAG, "empty cursor");
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    
                    if (!isPlay) {
                    	try {
        					openFile(file);
        				} catch (RemoteException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        					
        					finish();
        				}
                    }
                }
            };
            
        	if (uri.getAuthority() == MediaStore.AUTHORITY) {
                // try to get title and artist from the media content provider
        		showProgressDialog();
        		
                mAsyncQueryHandler.startQuery(0, null, uri, null,
                        null, null, null);
            } else {
                final String authority = uri.getAuthority();
                // hide option menu if the uri may not be opened by music app
                if (authority.contains("attachmentprovider") || authority.contains("mms")) {
                    mMediaId = -1;
                    
                    try {
						openFile(file);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						finish();
					}
                } else {
                	showProgressDialog();
                    // Try to get the display name from another content provider.
                    // Don't specifically ask for the display name though, since the
                    // provider might not actually support that column.
                    mAsyncQueryHandler.startQuery(0, null, uri, null, null, null, null);
                }
            }
        }
	}

	// @Override
	// public void onCancel(DialogInterface dialog) {
	// finish();
	// }

	private void playOrEnqueuFile(long id, boolean enqueue) {
//		final long[] list = new long[] { id };
//		if (!enqueue) {
//			// Remove the actual queue
//			MusicUtils.removeAllTracks();
//			MusicUtils.playAll(getApplicationContext(), list, 0);
//		} else {
//			MusicUtils.addToCurrentPlaylist(getApplicationContext(), list);
//		}

		// Show now playing
		Intent intent = new Intent(this, PlayActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("playtype", 1);
		intent.putExtra("id", id);
		intent.putExtra("enqueue", enqueue);
		startActivity(intent);
		finish();
	}

	private void openFile(String file) throws RemoteException {
//		// Stop, load and play
//		MusicUtils.mService.stop();
//		MusicUtils.mService.openFile(file);
//		MusicUtils.mService.play();

		// Show now playing
		Intent nowPlayingIntent = new Intent(this, PlayActivity.class);
		nowPlayingIntent.putExtra("playtype", 2);
		nowPlayingIntent.putExtra("filename", file);
		startActivity(nowPlayingIntent);
		finish();
	}
	
	private void showProgressDialog() {
		if (dialog == null) {
			dialog = ProgressDialog.show(mContext, getString(R.string.app_name), 
						 getString(R.string.play_external_progress_msg), true, false);
			dialog.setCanceledOnTouchOutside(false);
		}
		//
		if(!((Activity) mContext).isFinishing())
		{
			//show dialog
			dialog.show();
		}
	}
	
	private void dismissProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	private void scanFile(String filePath) {
		String[] rootDir = new String[] { filePath };
		mScanConnection = MediaScannerFile.scanFile(this, rootDir,
				new String[] { "audio/*" }, this);
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		// TODO Auto-generated method stub
		dismissProgressDialog();
		if (uri == null) {
			finish();
			
			return;
		}
		
		//Log.e(TAG, "onScanCompleted() --- path = " + path + ", uri = " + uri+", uri.getPath() = "+uri.getPath());
		Message msg = new Message();
		msg.obj = uri;
		msg.what = MSG_SCAN_FINISHED;
		myHandler.sendMessage(msg);
	}
	
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == MSG_SCAN_FINISHED) {
				Uri uri = (Uri)msg.obj;
//				Log.e("haaaaa", "handleMessage: ");
				play(uri);

			}
		}
	}
}
