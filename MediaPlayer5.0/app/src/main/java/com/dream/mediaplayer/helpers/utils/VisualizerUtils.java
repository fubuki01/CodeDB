package com.dream.mediaplayer.helpers.utils;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

import com.dream.mediaplayer.ui.widgets.VisualizerView;


import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.util.Log;

public class VisualizerUtils {
		
	  private static Visualizer mVisualizer = null;
	  private static Equalizer mEqualizer = null;
//	  private static WeakReference<VisualizerView> mView = null;
//	  private static VisualizerView mVisualizerView = null;
//	  private static ArrayList<VisualizerView> arrayListVisualizerView = new ArrayList<VisualizerView>();
	  
	  private static ArrayList<WeakReference<VisualizerView>> arrayList = new ArrayList<WeakReference<VisualizerView>>();
	  
	  /*public static void updateVisualizerView(WeakReference<VisualizerView> visualizerView){
		  mView = visualizerView;
	  }*/
	  
	  /*public static void updateVisualizerView(VisualizerView visualizerView){
		  arrayListVisualizerView.add(visualizerView);
	  }*/
	  
	  public static void updateVisualizerView(WeakReference<VisualizerView> visualizerView) {
		  arrayList.add(visualizerView);
	  }
	  
	  /*public static void updateVisualizerFFT(byte[] bytes) {
		  if(mView==null)
			  return;
		  VisualizerView view = mView.get();
	      if (view == null) {
	      	return;
	      }
		  view.updateVisualizerFFT(bytes);
	  }*/
	  
	  /*public static void updateVisualizerFFT(byte[] bytes) {
		  if (arrayListVisualizerView != null) {
			  for (VisualizerView visualizerView:arrayListVisualizerView) {
				  if (visualizerView != null) {
					  visualizerView.updateVisualizerFFT(bytes);
				  }
			  }
		  }
	  }*/
	  
	  public static void updateVisualizerFFT(byte[] bytes) {
		  if (arrayList != null) {
			  for (WeakReference<VisualizerView> weakVisualizerView:arrayList) {
				  VisualizerView visualizerView = weakVisualizerView.get();
				  if (visualizerView != null) {
					  visualizerView.updateVisualizerFFT(bytes);
				  }
			  }
		  }
	  }

	  /*public static void updateVisualizer(byte[] bytes) {
		  if(mView==null)
			  return;
		  VisualizerView view = mView.get();
	      if (view == null) {
	      	return;
	      }
		  view.updateVisualizer(bytes);
	  }*/
	  
	  /*public static void updateVisualizer(byte[] bytes) {
		  if (arrayListVisualizerView != null) {
			  for (VisualizerView visualizerView:arrayListVisualizerView) {
				  visualizerView.updateVisualizer(bytes);
			  }
		  }
	  }*/
	  
	  public static void updateVisualizer(byte[] bytes) {
		  if (arrayList != null) {
			  for (WeakReference<VisualizerView> weakVisualizerView:arrayList) {
				  VisualizerView visualizerView = weakVisualizerView.get();
				  if (visualizerView != null) {
					  visualizerView.updateVisualizer(bytes);
				  }
			  }
		  }
	  }
	  
	  public static void releaseVisualizer(){
		  if(mVisualizer != null) {
			  mVisualizer.setEnabled(false);
			  mVisualizer.release();
			  mVisualizer = null;
		  }
		  
		  if (mEqualizer != null) {
			  mEqualizer.setEnabled(false);
			  mEqualizer.release();
			  mEqualizer = null;
		  }
	  }
	  
	  public static void initVisualizer( MediaPlayer player ){
		  VisualizerUtils.releaseVisualizer();
		  try{
			  mVisualizer =  new Visualizer(player.getAudioSessionId());
			  mEqualizer = new Equalizer(0, player.getAudioSessionId());  
		  }
		  catch(Exception e){
			  Log.e("VisualizerUtils --- ", "initVisualizer() ---- error ! e = "+e.toString());
			  mVisualizer = null;
			  mEqualizer = null;
			  
			  return;
		  }

//		  mEqualizer.setEnabled(false); 
//		  mVisualizer.setEnabled(false);		  
		  mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

		  Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener()
		  {
			  @Override
			  public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
			      int samplingRate)
			  {
//				  VisualizerUtils.updateVisualizer(bytes);
			  }
			
			  @Override
			  public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
			      int samplingRate)
			  {
				  VisualizerUtils.updateVisualizerFFT(bytes);
			  }
		  };
//		  mVisualizer.setDataCaptureListener(captureListener,20000 , true, true);
		  mVisualizer.setDataCaptureListener(captureListener,(int)(Visualizer.getMaxCaptureRate() / 2.8f ), false, true);	

		  mEqualizer.setEnabled(true); 
		  mVisualizer.setEnabled(true);		
	  }
}
