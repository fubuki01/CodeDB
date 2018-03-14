/**
 * Copyright 2011, Felix Palmer
 *
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.dream.mediaplayer.ui.widgets;

import static com.dream.mediaplayer.Constants.VISUALIZATION_TYPE;

import com.dream.mediaplayer.R;
import com.dream.mediaplayer.helpers.visualizer.AudioData;
import com.dream.mediaplayer.helpers.visualizer.BarGraphRenderer;
import com.dream.mediaplayer.helpers.visualizer.FFTData;
import com.dream.mediaplayer.helpers.visualizer.Renderer;
import com.dream.mediaplayer.helpers.visualizer.SolidBarGraphRenderer;
import com.dream.mediaplayer.helpers.visualizer.WaveformRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class VisualizerView extends View {

	private byte[] mBytes;
	private byte[] mFFTBytes;
	private Rect mRect = new Rect();

	private Renderer mRenderer;
	String type = null;
	
	public VisualizerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);

		mBytes = null;
		mFFTBytes = null;
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context.getApplicationContext());
		type = sp.getString(VISUALIZATION_TYPE,
				getResources().getString(R.string.visual_none));

		type = getResources().getString(R.string.visual_bargraph);
		if (type.equals(getResources().getString(R.string.visual_solidbargraph))) {

			mRenderer = new SolidBarGraphRenderer(context);

		} else if (type.equals(getResources().getString(
				R.string.visual_waveform))) {

			mRenderer = new WaveformRenderer(context);

		} else if (type.equals(getResources().getString(
				R.string.visual_bargraph))) {

			mRenderer = new BarGraphRenderer(context);

		}

	}

	public VisualizerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VisualizerView(Context context) {
		this(context, null, 0);
	}

	/**
	 * Pass data to the visualizer. Typically this will be obtained from the
	 * Android Visualizer.OnDataCaptureListener call back. See
	 * {@link Visualizer.OnDataCaptureListener#onWaveFormDataCapture }
	 * 
	 * @param bytes
	 */
	public void updateVisualizer(byte[] bytes) {
		mBytes = bytes;
		invalidate();
	}

	/**
	 * Pass FFT data to the visualizer. Typically this will be obtained from the
	 * Android Visualizer.OnDataCaptureListener call back. See
	 * {@link Visualizer.OnDataCaptureListener#onFftDataCapture }
	 * 
	 * @param bytes
	 */
	public void updateVisualizerFFT(byte[] bytes) {
		mFFTBytes = bytes;
		invalidate();
	}

	Bitmap mCanvasBitmap;
	Canvas mCanvas;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Log.e("Visualizer --- ", "onDraw() --1- type = "+type);
		if (type.equals(getResources().getString(R.string.visual_none))) {
			Log.e("Visualizer --- ", "onDraw() --2- type = " + type);
			return;
		}
		// Create canvas once we're ready to draw
		mRect.set(0, 0, getWidth(), getHeight());

		if (mCanvasBitmap == null) {
			mCanvasBitmap = Bitmap.createBitmap(canvas.getWidth(),
					canvas.getHeight(), Config.ARGB_8888);
		}
		if (mCanvas == null) {
			mCanvas = new Canvas(mCanvasBitmap);
		}

		// Clear canvas
		mCanvas.drawColor(0, Mode.CLEAR);

		if (mBytes != null) {
			// Render all audio renderers
			AudioData audioData = new AudioData(mBytes);
			mRenderer.render(mCanvas, audioData, mRect);
		}

		if (mFFTBytes != null) {
			// Render all FFT renderers
			FFTData fftData = new FFTData(mFFTBytes);
			mRenderer.render(mCanvas, fftData, mRect);
		}

		canvas.drawBitmap(mCanvasBitmap, new Matrix(), null);
	}
}