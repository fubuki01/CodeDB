package com.dream.mediaplayer.views;

import com.dream.mediaplayer.R;

import android.app.ActionBar;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;



/**
 * 声音调整poup window
 * Created by terry on 15-2-11.
 */
public class VolumePopupWindow extends PopupWindow {

    private View view;
    private VerticalSeekBar seekBar;
    private AudioManager audioManager;

    /**
     * 因为本身系统的声音数值的最高值相对较小
     * 为了提高seekbar拖动对声音改变的精度
     * 所以对seekbar的最高值 当前值都乘以一个倍率
     * 对seekbar改变的progress除以一个倍率
     */
    private static final int MULTIPLYING_POWER = 16;

    public VolumePopupWindow(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.volume_control_layout , null);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
//				Log.e("VolumePopupWindow --- ", "onKey() --- event.getKeyCode() = "+event.getKeyCode());
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
				
				return false;
			}
		});
        
        seekBar = (VerticalSeekBar) view.findViewById(R.id.volume_seekbar);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar VerticalSeekBar) {
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
        setOutsideTouchable(true);
		setTouchable(true);
		setTouchInterceptor(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.bg_volume));
        
        setContentView(view);
        setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
    }
    
    /**
     * 音量上升
     */
    public void seekUp() {
        if(seekBar.getProgress() < seekBar.getMax()) {
            seekBar.setProgress(seekBar.getProgress() + 1*MULTIPLYING_POWER);
//            seekBar.onSizeChanged();
        }
    }

    /**
     * 音量下降
     */
    public void seekDown(){
        if(seekBar.getProgress() > 0) {
            seekBar.setProgress(seekBar.getProgress() - 1*MULTIPLYING_POWER);
//            seekBar.onSizeChanged();
        }
    }

    public void updateSeekBar() {
//    	seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
    }
    
    
}
