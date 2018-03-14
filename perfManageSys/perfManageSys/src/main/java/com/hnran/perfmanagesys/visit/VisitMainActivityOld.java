package com.hnran.perfmanagesys.visit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.utils.FileUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.MultipartRequest;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;

public class VisitMainActivityOld extends Activity {

	private static final int IMAGE_REQUEST_CODE = 1;
	private static final int VIDEO_REQUEST_CODE = 2;
	private static final int VIDEO_SIZE = 5;
	private static final int VIDEO_LENGTH = 5;
	private LocationClient mLocationClient;//定位SDK的核心类
	private TextView LocationResult;
	private String locationString;
	private TextView TimeResult;
	private String timeString;
	public MyLocationListener mMyLocationListener;//定义监听类
	public Handler mHanlder = null;
	private ImageView ivPhoto;
	private VideoView vvVideo;
	private Uri videoUri;


	private TextView tvEdit;

	private ProgressBar progressBar;

	private Button btnCamera, btnVideo;

	private String mPath = "", mType = "";

	private boolean Upload_Flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_visit_main_old);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("信息采集");

		/**
		 * 标题栏左方返回
		 */
		LinearLayout back = (LinearLayout)findViewById(R.id.ll_common_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tvEdit = (TextView) findViewById(R.id.common_back_title_tv);
		tvEdit.setText("上传");
		//		tvEdit.setVisibility(View.VISIBLE);
		tvEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ToastUtil.showToast(VisitMainActivityOld.this, "正在上传...");
				if(Upload_Flag){
					Upload_Flag = false;
					customerInfoFile();
				}

			}
		});

		progressBar = (ProgressBar) findViewById(R.id.visit_main_progressbar);

		mLocationClient = new LocationClient(this.getApplicationContext());
		InitLocation();
		LocationResult = (TextView)findViewById(R.id.location_textview);
		TimeResult = (TextView)findViewById(R.id.time_textview);
		TimeResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.start();
			}
		});
		LocationResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLocationClient.start();
			}
		});
		btnCamera = (Button)findViewById(R.id.take_photo);
		btnCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePicture();
			}
		});

		btnVideo = (Button)findViewById(R.id.take_video);
		btnVideo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takeVideo();
			}
		});

		ivPhoto = (ImageView)findViewById(R.id.show_image);
		vvVideo = (VideoView)findViewById(R.id.video_view);

		mHanlder = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				progressBar.setVisibility(View.GONE);
				btnCamera.setEnabled(true);
				btnVideo.setEnabled(true);

				Log.v("hnulab", "msg is "+msg.getData().toString());
				locationString = msg.getData().getString("location");
				timeString = msg.getData().getString("time");
				TimeResult.setText("时间:"+timeString);
				LocationResult.setText("地点:"+locationString);
			}

		};
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mLocationClient.start();

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		tvEdit.setVisibility(View.VISIBLE);
		if( data != null){
			if(requestCode==IMAGE_REQUEST_CODE){

				try{
					Bitmap image = (Bitmap)data.getExtras().get("data");
					mPath = FileUtil.saveBitmap(this, image, timeString).toString();
				
					mType = "image";

					vvVideo.setVisibility(View.INVISIBLE);
					ivPhoto.setVisibility(View.VISIBLE);
					ivPhoto.setImageBitmap(image);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}

			if (requestCode==VIDEO_REQUEST_CODE) {

				mPath = videoUri.toString();

				int i = mPath.indexOf("file://");
				if(i != -1){
					mPath = mPath.substring(i+7);
				}

				mType = "video";

				vvVideo.setVisibility(View.VISIBLE);
				ivPhoto.setVisibility(View.INVISIBLE);
				if(videoUri != null) vvVideo.setVideoURI(videoUri);
				vvVideo.start();
				vvVideo.setOnCompletionListener( new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mp.start();
						mp.setLooping(true);
					}
				});
			}
		}
	}


	private void takePicture(){
		/*
		 * <intent-filter> <action
		 * android:name="android.media.action.IMAGE_CAPTURE" /> <category
		 * android:name="android.intent.category.DEFAULT" /> </intent-filter>
		 */
		// 激活系统的照相机进行拍照
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		//        intent.addCategory("android.intent.category.DEFAULT");
		//        String path = null;
		//        if(FileUtil.checkSDCard()){
		//        	path = FileUtil.getExternalFileDir(this);
		//        	
		//        }else{
		//        	path = FileUtil.getAppCacheDir(this);
		//        }
		//        Log.v("hnulab", FileUtil.getExternalFileDir(this));
		//        Log.v("hnulab", path);
		//        //保存照片到指定的路径
		//        path = path+"/"+timeString+".jpg";
		//        File file = new File(path);
		//        Uri uri = Uri.fromFile(file);
		//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		startActivityForResult(intent, IMAGE_REQUEST_CODE);
	}

	private void takeVideo(){
		/*
		 * <intent-filter> <action
		 * android:name="android.media.action.VIDEO_CAPTURE" /> <category
		 * android:name="android.intent.category.DEFAULT" /> </intent-filter>
		 */
		// 激活系统的照相机进行录像
		Intent intent = new Intent();
		intent.setAction("android.media.action.VIDEO_CAPTURE");
		intent.addCategory("android.intent.category.DEFAULT");
		videoUri = FileUtil.createVideoUri(this, timeString);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
		intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, VIDEO_SIZE*1024*1024);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,VIDEO_LENGTH);

		startActivityForResult(intent, VIDEO_REQUEST_CODE);
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置高精度定位定位模式
		option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
		option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
		option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
		mLocationClient.setLocOption(option);
	}


	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		public void onReceiveLocation(BDLocation location) {
			//Receive Location 

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());//获得当前时间
			sb.append("\nerror code : ");
			sb.append(location.getLocType());//获得erro code得知定位现状
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());//获得纬度
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());//获得经度
			sb.append("\nradius : ");
			sb.append(location.getRadius());

			if (location.getLocType() == BDLocation.TypeGpsLocation){//通过GPS定位
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());//获得速度
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());//获得当前地址
				sb.append(location.getDirection());//获得方位
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){//通过网络连接定位
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());//获得当前地址
				//运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());//获得经营商？
			}
			else if (location.getLocType() == BDLocation.TypeNetWorkException) {

				sb.append("\ndescribe : ");
				sb.append("网络不同导致定位失败，请检查网络是否通畅");

			} else if (location.getLocType() == BDLocation.TypeCriteriaException) {

				sb.append("\ndescribe : ");
				sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
			}
			//            TimeResult.setText("时间:"+sb.toString());
			Log.v("hnulab", "时间是"+location.getTime());
			//            LocationResult.setText("地点:"+location.getAddrStr());
			Log.i("hnulab", sb.toString());
			Message msg = mHanlder.obtainMessage();
			Bundle data = new Bundle();
			data.putString("time", location.getTime());
			data.putString("location", location.getAddrStr());
			msg.setData(data);
			mHanlder.sendMessage(msg);
			mLocationClient.stop();
		}

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO Auto-generated method stub
		}
	}


	private void customerInfoFile(){
		//构造参数列表
		List<Part> partList = new ArrayList<Part>();
		partList.add(new StringPart("khmc", getIntent().getStringExtra("Extra_khmc"), "UTF-8"));
		partList.add(new StringPart("khbh", getIntent().getStringExtra("Extra_khbh"), "UTF-8"));
		partList.add(new StringPart("ghrgh", PMSApplication.gUser.getTellId(), "UTF-8"));
		partList.add(new StringPart("type", mType, "UTF-8"));
		partList.add(new StringPart("time", timeString, "UTF-8"));
		partList.add(new StringPart("address", locationString, "UTF-8"));

		try {

			File file = new File(mPath);
			partList.add(new FilePart("fileupload", file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String url = MakeUrl.makeURL(new String[]{"UploadCustomerInfoFile.action"});

		//生成请求
		MultipartRequest profileUpdateRequest = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//处理成功返回信息
				ToastUtil.showToast(getApplication(), "上传成功");
				VisitMainActivityOld.this.finish();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				//处理失败错误信息
				ToastUtil.showToast(getApplication(), "上传失败");
				Upload_Flag = true;
				tvEdit.setFocusable(false);
			}
		});
		//将请求加入队列
		VolleyUtil.getInstanceRequestQueue().add(profileUpdateRequest);
	}

}
