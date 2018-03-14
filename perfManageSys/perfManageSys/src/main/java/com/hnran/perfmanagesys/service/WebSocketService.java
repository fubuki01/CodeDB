package com.hnran.perfmanagesys.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.drafts.Draft_17;

import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.ReLoginActivity;
import com.hnran.perfmanagesys.broadcastreceiver.UpdateViewReceiver;
import com.hnran.perfmanagesys.utils.ExampleClient;
import com.hnran.perfmanagesys.utils.ExampleClient.ReLoginListener;
import com.hnran.perfmanagesys.utils.MakeUrl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class WebSocketService extends Service{

	private ExampleClient c;
	
	//
	private UpdateViewReceiver updateViewReceiver;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);

		try {
			c = new ExampleClient(new URI("ws://" + MakeUrl.IP + "/websocket/socketServer.do?token=" + PMSApplication.gToken ), new Draft_17());   
			c.setListener(new ReLoginListener() {
				
				@Override
				public void onReLogin() {
					// TODO Auto-generated method stub
					Intent reLoginIntent = new Intent(WebSocketService.this, ReLoginActivity.class);
					reLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					WebSocketService.this.startActivity(reLoginIntent);
				}
			});
			c.connectBlocking();
//			c.send("我是消息推送"); 
		}catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		updateViewReceiver = new UpdateViewReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				super.onReceive(context, intent);
				c.close();
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction(UpdateViewReceiver.CLOSE_SOCKET);
		this.registerReceiver(updateViewReceiver, filter);

		return 0;

	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		c.close();
		unregisterReceiver(updateViewReceiver);
	}
	
}
