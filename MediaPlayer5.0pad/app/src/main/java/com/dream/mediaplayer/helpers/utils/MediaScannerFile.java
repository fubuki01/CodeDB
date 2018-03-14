package com.dream.mediaplayer.helpers.utils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;

public class MediaScannerFile {

	/**
	 * 扫描指定的文件
	 * 
	 * @param context
	 * @param filePath
	 * @param sListener
	 */
	public static MediaScannerConnection scanFile(Context context,
			String[] filePath, String[] mineType,
			OnScanCompletedListener sListener) {

		ClientProxy client = new ClientProxy(filePath, mineType, sListener);

		try {
			MediaScannerConnection connection = new MediaScannerConnection(
					context.getApplicationContext(), client);
			client.mConnection = connection;
			connection.connect();
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static class ClientProxy implements MediaScannerConnectionClient {
		final String[] mPaths;
		final String[] mMimeTypes;
		final OnScanCompletedListener mClient;
		MediaScannerConnection mConnection;
		int mNextPath;

		ClientProxy(String[] paths, String[] mimeTypes,
				OnScanCompletedListener client) {
			mPaths = paths;
			mMimeTypes = mimeTypes;
			mClient = client;
		}

		public void onMediaScannerConnected() {
			scanNextPath();
		}

		public void onScanCompleted(String path, Uri uri) {
			if (mClient != null) {
				mClient.onScanCompleted(path, uri);
			}
			scanNextPath();
		}

		/**
		 * 自动扫描下一个
		 */
		void scanNextPath() {
			if (mNextPath >= mPaths.length) {
				mConnection.disconnect();
				return;
			}
			String mimeType = mMimeTypes != null ? mMimeTypes[mNextPath] : null;
			mConnection.scanFile(mPaths[mNextPath], mimeType);
			mNextPath++;
		}
	}
}
