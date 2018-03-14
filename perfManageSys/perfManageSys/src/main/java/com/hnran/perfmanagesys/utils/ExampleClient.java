package com.hnran.perfmanagesys.utils;

import java.net.URI;  
import java.util.List;

import org.java_websocket.client.WebSocketClient;  
import org.java_websocket.drafts.Draft;  
import org.java_websocket.framing.Framedata;  
import org.java_websocket.handshake.ServerHandshake;  
import org.json.JSONException;
import org.json.JSONObject;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.PMSApplication;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * http://blog.csdn.net/chifengxin/article/details/14521093/
 */
/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */  
public class ExampleClient extends WebSocketClient {  
	
	private static int ID = 1;
	
	private Context mContext = PMSApplication.getGlobalContext();

	public ExampleClient( URI serverUri , Draft draft ) {  
		super( serverUri, draft ); 
	}  

	public ExampleClient( URI serverURI ) {  
		super( serverURI );  
	}  

	@Override  
	public void onOpen( ServerHandshake handshakedata ) {  
		System.out.println( "opened connection" );  
		// if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient  
	}  

	@Override  
	public void onMessage( String message ) {  
		System.out.println( ID +", received: " + message ); 
//		ToastUtil.showToast(PMSApplication.getGlobalContext(), message);

		JSONObject jsonObject = null;
		String operation = "", title = "", content = "";
		try {
			jsonObject = new JSONObject(message);
			operation = jsonObject.getString("operation");
			title = jsonObject.getString("title");
			content = jsonObject.getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if("off-line".equals(operation)){
			if(listener != null && PMSApplication.isLogin && getAppliactionState())
				listener.onReLogin();
		}else if("broadcast".equals(operation)){
			showNotification(title,content);
		}
		
	}  
	
	
//	private void showLogin(){
//		/**
//		 * 发送重新登录广播
//		 */
//		Intent intent = new Intent();
//		intent.setAction(UpdateViewReceiver.RE_LOGIN);
//		mContext.sendBroadcast(intent);
//	}
	
	private void showNotification(String title, String content){
		NotificationManager notiManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new NotificationCompat.Builder(mContext)
				.setSmallIcon(R.drawable.ic_launcher)
//				.setLargeIcon(R.drawable.ic_launcher)
				.setContentTitle(title)
				.setContentText(content)
				.setDefaults(Notification.DEFAULT_ALL)
				.setAutoCancel(true)
				.build();
		notiManager.notify((int) ID++, notification);
	}
	

	@Override  
	public void onFragment( Framedata fragment ) {  
		System.out.println( "received fragment: " + new String( fragment.getPayloadData().array() ) );  
	}  

	@Override  
	public void onClose( int code, String reason, boolean remote ) {  
		// The codecodes are documented in class org.java_websocket.framing.CloseFrame  
		System.out.println( "Connection closed by " + ( remote ? "remote peer" : "us" ) );  
	}  

	@Override  
	public void onError( Exception ex ) {  
		ex.printStackTrace();  
		// if the error is fatal then onClose will be called additionally  
	}  
	
	
	private ReLoginListener listener;
	public void setListener(ReLoginListener listener){
		this.listener = listener;
	}
	public interface ReLoginListener{
		public void onReLogin();
	}
	
	private boolean getAppliactionState(){
		//判断应用是否在运行 
		ActivityManager am = (ActivityManager)mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(20);
		boolean isAppRunning = false;
		String MY_PKG_NAME = "com.hnran.perfmanagesys";
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
				isAppRunning = true;
				break;
			}
		}
		return isAppRunning;
	}

}  
