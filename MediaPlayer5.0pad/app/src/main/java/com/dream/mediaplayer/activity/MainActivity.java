package com.dream.mediaplayer.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.mediaplayer.IApolloService;
import com.dream.mediaplayer.R;
import com.dream.mediaplayer.fragment.AlbumsFragment;
import com.dream.mediaplayer.fragment.ArtistsFragment;
import com.dream.mediaplayer.fragment.PlaylistsFragment;
import com.dream.mediaplayer.fragment.TracksFragment;
import com.dream.mediaplayer.helpers.utils.ApolloUtils;
import com.dream.mediaplayer.helpers.utils.permissionCheck;
import com.dream.mediaplayer.helpers.utils.MediaScannerFile;
import com.dream.mediaplayer.helpers.utils.MusicUtils;
import com.dream.mediaplayer.permission.PermissionsChecker;
import com.dream.mediaplayer.preferences.SharedPreferencesCompat;
import com.dream.mediaplayer.service.ApolloService;
import com.dream.mediaplayer.service.ServiceToken;
import com.dream.mediaplayer.ui.adapters.SpinnerItemBean;

import java.util.ArrayList;


public class MainActivity extends Activity implements ServiceConnection, MediaScannerConnection.OnScanCompletedListener {

    public static boolean havePermission = false;

	private String tag = "MainActivity --- ";

	private Context mContext;

	private ServiceToken mToken;

	private ArrayList<SpinnerItemBean> spinnerDataList = new ArrayList<SpinnerItemBean>();

//	private SpinnerAdapter spinnerAdapter;

	/**
	 * 根布局
	 */
	private RelativeLayout rootboot;

	/**
	 * 下拉选择框
	 */
//	private Spinner mSpinner;
	
	private TextView topbarTitle;
	
	private ImageView mSearchTextView;
	
	private TextView mUpdateTextView;

	/** 管理fragment */
	private FragmentManager fragmentManager;
	
	private SharedPreferences mPreferences;

	/**
	 * spinner当前选中的条目索引
	 */
	private int spinnerCurSeleIndex = 0;

	private MediaScannerConnection mScanConnection;

    public permissionCheck permissionCheck;


    /**
	 * 点击topbarTitle弹出的对话框
	 */
	private AlertDialog dialog;
	
	private ImageView selectIcon0;
	private ImageView selectIcon1;
	private ImageView selectIcon2;
	private ImageView selectIcon3;

	private static final int PERMISSIONS_REQUEST_CODE = 201702; // 请求码

	// 所需的全部权限,依据应用使用权限
	static final String[] PERMISSIONS = new String[] {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};
	private PermissionsChecker mPermissionsChecker; // 权限检测器
	private static final int REQUEST_CODE = 0; // 请求码
	public  static  boolean exitManiActivity2Playlst = false;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
        mPermissionsChecker = new PermissionsChecker(this);
//        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//            havePermission=false;
//            PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
//        }
		super.onCreate(arg0);
        permissionCheck = new permissionCheck(this);
        if (!permissionCheck.flag) {
            havePermission = true;
			permissionCheck.flagStop=false;
			permissionCheck.flag=false;
            init();
        }


//        ApolloUtils.setStatusBar(this);
//
//
//		mContext = this;
//
//		// fragment管理者
//		fragmentManager = getFragmentManager();
//
//		mPreferences = getSharedPreferences("Music", MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
//
//		// Control Media volume
//		setVolumeControlStream(AudioManager.STREAM_MUSIC);
//
//		initData();
//
//		setContentView(R.layout.activity_main);
//
//		getElements();
//
//		initListener();
//
//		showFragment();
	}

    private void init() {
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ApolloUtils.setStatusBar(this);
        mContext = this;

        // fragment管理者
        fragmentManager = getFragmentManager();

        mPreferences = getSharedPreferences("Music", MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);

        // Control Media volume
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        initData();

        setContentView(R.layout.activity_main);

        getElements();

        initListener();

        showFragment();
    }




    @Override
	public void onResume() {
		super.onResume();
		// 缺少权限时, 进入权限配置页面
	}


	// 用于返回权限检测的结果,没有权限则退出
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        permissionCheck.onActivityResultTest(this, requestCode, resultCode);
	}

	/**
	 * Update the list as needed
	 */
	private final BroadcastReceiver mMediaStatusReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e(tag,
//					"onReceive() --- intent.getAction() = "
//							+ intent.getAction());
			
			if (intent.getAction().equals(ApolloService.META_CHANGED)) {
				//显示跟布局的背景
//				MusicUtils.setRootbootBK(mContext, rootboot);
	            
//	            MusicUtils.updateBottomActionBar(mContext, bottomActionBar, bottombar_songname, bottombar_artistname, bottombarCover);
			}
			
//            MusicUtils.setPauseButtonImage(mPlay);
		}

	};

	/**
	 * 初始化数据topbar_page_title_0
	 */
	private void initData() {
		spinnerCurSeleIndex = mPreferences.getInt("spinnerCurIndex", 0);
		
		spinnerDataList.add(new SpinnerItemBean(mContext.getResources().getString(R.string.topbar_page_title_0), false));
		spinnerDataList.add(new SpinnerItemBean(mContext.getResources().getString(R.string.topbar_page_title_1), false));
		spinnerDataList.add(new SpinnerItemBean(mContext.getResources().getString(R.string.topbar_page_title_2), false));
		spinnerDataList.add(new SpinnerItemBean(mContext.getResources().getString(R.string.topbar_page_title_3), false));
		
		for (int i = 0; i < spinnerDataList.size(); i++) {
			if (spinnerCurSeleIndex == i) {
				spinnerDataList.get(i).setIsSelected(true);
			}
		}
	}

	/**
	 * 获取界面元素
	 */
	private void getElements() {
		rootboot = (RelativeLayout) findViewById(R.id.rootboot);
//		mSpinner = (Spinner) findViewById(R.id.top_bar_spinner);
		topbarTitle = (TextView) findViewById(R.id.topbar_title);
		
//		bottomActionBar = (RelativeLayout) findViewById(R.id.bottom_bar);
//		bottombarCover = (ImageView) findViewById(R.id.bottombar_cover);
//		bottombar_songname = (MarqueeTextView) findViewById(R.id.bottombar_songname);
//		bottombar_artistname = (MarqueeTextView) findViewById(R.id.bottombar_artistname);
//		mPrev = (ImageButton) findViewById(R.id.bottombar_pre);
//		mPlay = (ImageButton) findViewById(R.id.bottombar_play);
//		mNext = (ImageButton) findViewById(R.id.bottombar_next);
		
		mSearchTextView = (ImageView) findViewById(R.id.search_textview);
		mUpdateTextView = (TextView) findViewById(R.id.update_textview);
	}

	/**
	 * 切换fragment页面显示
	 * 
	 * @param fragment
	 */
	private void changeFragment(Fragment fragment) {
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragmentPager, fragment);
		transaction.commit();
	}

	/**
	 * 刷新spinner
	 * @param spinnerCurSeleIndex
	 */
	private void updateSpinner(int spinnerCurSeleIndex) {
		for (int i = 0; i < spinnerDataList.size(); i++) {
			if (i == spinnerCurSeleIndex) {
				spinnerDataList.get(i).setIsSelected(true);
			} else {
				spinnerDataList.get(i).setIsSelected(false);	
			}
		}
		
//		spinnerAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		mSearchTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MusicUtils.changeToSearchActivity(mContext);
			}
		});
		
		mUpdateTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ScanSDCard();
			}
		});
		
//		spinnerAdapter = new SpinnerAdapter(mContext, spinnerDataList);
//		mSpinner.setAdapter(spinnerAdapter);
//		mSpinner.setSelection(spinnerCurSeleIndex);
//		mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				Log.e(tag, "onItemSelected() --- position = " + position);
//				switch (position) {
//				case 0:
//					updateSpinner(position);
//					changeFragment(new TracksFragment());
//					break;
//
//				case 1:
//					updateSpinner(position);
//					changeFragment(new AlbumsFragment());
//					break;
//
//				case 2:
//					updateSpinner(position);
//					changeFragment(new ArtistsFragment());
//					break;
//
//				case 3:
//					updateSpinner(position);
//					changeFragment(new PlaylistsFragment());
//					break;
//
//				default:
//					updateSpinner(0);
//					changeFragment(new TracksFragment());
//					break;
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		RelativeLayout topbarTitleContent = (RelativeLayout) findViewById(R.id.topbar_title_content);
		topbarTitleContent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFragmentDialog();
			}
		});
	}
	
	@Override
	public void onServiceConnected(ComponentName name, IBinder obj) {
		MusicUtils.mService = IApolloService.Stub.asInterface(obj);
		
		//显示跟布局的背景
//		MusicUtils.setRootbootBK(mContext, rootboot);
        
//        MusicUtils.updateBottomActionBar(mContext, bottomActionBar, bottombar_songname, bottombar_artistname, bottombarCover);
        
//        MusicUtils.setPauseButtonImage(mPlay);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		MusicUtils.mService = null;
	}

	@Override
	protected void onStart() {
		Log.i("MainActivity","onStart");
        if (!permissionCheck.flagStop&&havePermission==false) {
            init();
            havePermission = true;
			permissionCheck.flagStop=false;
			permissionCheck.flag=false;
        }

		// Bind to Service
		mToken = MusicUtils.bindToService(this, this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ApolloService.PLAYSTATE_CHANGED);
		filter.addAction(ApolloService.QUEUE_CHANGED);
		filter.addAction(ApolloService.META_CHANGED);
		registerReceiver(mMediaStatusReceiver, filter);

		super.onStart();
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	public void onStop() {
		Log.i("MainActivity","onStop");
		if (null != mScanConnection && mScanConnection.isConnected()) {
			mScanConnection.disconnect();
		}
		
		unregisterReceiver(mMediaStatusReceiver);

		// Unbind
		if (MusicUtils.mService != null)
			MusicUtils.unbindFromService(mToken);

		// TODO: clear image cache

		super.onStop();
	}
	
	public void scanAllFile() {
		  String[] rootDir = new String[] { Environment.getExternalStorageDirectory().getPath()};
		  mScanConnection = MediaScannerFile.scanFile(mContext, rootDir, null, this);
	}
	
    private void ScanSDCard() {
		/*IntentFilter intentfilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentfilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentfilter.addDataScheme("file");
		ScanSdReceiver receiver = new ScanSdReceiver(this);
		this.registerReceiver(receiver, intentfilter);*/
		
		scanAllFile();
		
/*//		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
		String path = Environment.getExternalStorageDirectory().getPath();
		path = path+"/";
		Log.e(tag, "ScanSDCard() -- -------- path = "+path);
		File file = new File(path);
		if (file.exists()) {
			Log.e(tag, "ScanSDCard() -- --------");
			Uri contentUri = Uri.fromFile(file); //out is your output file
			
			Intent mediaScanIntent = new Intent(
	                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
	        mediaScanIntent.setData(contentUri);
	        this.sendBroadcast(mediaScanIntent);
		}*/
		
//		this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//		Uri.parse("file://"+ Environment.getExternalStorageDirectory().getAbsolutePath())));
		
	}

	@Override
	public void onScanCompleted(String path, Uri uri) {
		// TODO Auto-generated method stub
		Log.e(tag, "onScanCompleted() --- path = "+path+", uri.getPath() = "+uri.getPath());
	}
	
	/**
	 * 点击重复播放textview后弹出的对话框
	 */
	private void showFragmentDialog() {
		dialog = new AlertDialog.Builder(mContext, R.style.fragmentDialogStyle).create();
		Window localWindow = dialog.getWindow();
//		WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
		localWindow.setGravity(Gravity.TOP);
//		localWindow.setAttributes(localLayoutParams);
		//localWindow.setWindowAnimations(R.style.PopupAnimation);
		if(!dialog.isShowing()) {
			dialog.show();
		}
		dialog.setContentView(R.layout.alertdialog_changefragment_layout);
		
		selectIcon0 = (ImageView) dialog.findViewById(R.id.select_icon_0);
		selectIcon1 = (ImageView) dialog.findViewById(R.id.select_icon_1);
		selectIcon2 = (ImageView) dialog.findViewById(R.id.select_icon_2);
		selectIcon3 = (ImageView) dialog.findViewById(R.id.select_icon_3);

		RepeatDialogListener localRepeatDialogListener = new RepeatDialogListener();
		dialog.findViewById(R.id.tableRow0).setOnClickListener(
				localRepeatDialogListener);
		dialog.findViewById(R.id.tableRow1).setOnClickListener(
				localRepeatDialogListener);
		dialog.findViewById(R.id.tableRow2).setOnClickListener(
				localRepeatDialogListener);
		dialog.findViewById(R.id.tableRow3).setOnClickListener(
				localRepeatDialogListener);
//		dialog.findViewById(R.id.tableRow4).setOnClickListener(
//				localRepeatDialogListener);
		
		updateDialog(spinnerDataList.get(spinnerCurSeleIndex).getName());
	}
	
	private void showFragment() {
		String[] topbarTitleArray = new String[] {
				mContext.getResources().getString(R.string.topbar_page_title_0), 
				mContext.getResources().getString(R.string.topbar_page_title_1), 
				mContext.getResources().getString(R.string.topbar_page_title_2), 
				mContext.getResources().getString(R.string.topbar_page_title_3)
		};
		
		switch (spinnerCurSeleIndex) {
		case 0:
			topbarTitle.setText(topbarTitleArray[spinnerCurSeleIndex]);
			changeFragment(new TracksFragment());
			break;
		case 1:
			topbarTitle.setText(topbarTitleArray[spinnerCurSeleIndex]);
			changeFragment(new AlbumsFragment());
			break;
		case 2:
			topbarTitle.setText(topbarTitleArray[spinnerCurSeleIndex]);
			changeFragment(new ArtistsFragment());
			break;
		case 3:
			topbarTitle.setText(topbarTitleArray[spinnerCurSeleIndex]);
			changeFragment(new PlaylistsFragment());
			break;
		}
	}
	
	private void updateDialog(String rowName) {
		ImageView[] imageViewArray = new ImageView[]{
				selectIcon0, selectIcon1, selectIcon2, selectIcon3
		};
		
		for (int i = 0; i < spinnerDataList.size(); i++) {
			SpinnerItemBean spinnerItemBean = spinnerDataList.get(i);
			if (spinnerItemBean.getName().equals(rowName)) {
				spinnerItemBean.setIsSelected(true);
				imageViewArray[i].setVisibility(View.VISIBLE);
			} else {
				spinnerItemBean.setIsSelected(false);
				imageViewArray[i].setVisibility(View.INVISIBLE);
			}
		}
	}
	
	private int getCurrentIndex() {
		int currentIndex = 0;
		for (int i = 0; i < spinnerDataList.size(); i++) {
			if (spinnerDataList.get(i).getIsSelected()) {
				currentIndex = i;
				break;
			}
		}
		
		return currentIndex;
	}
	
	private void editCache(int index) {
		Editor ed = mPreferences.edit();
		ed.putInt("spinnerCurIndex", index);
		SharedPreferencesCompat.apply(ed);
	}

	/**
	 * 重写返回按钮的监听
	 */
	@Override
	public  void  onBackPressed(){
		exitManiActivity2Playlst = true ;  //设置退出标记
		super.onBackPressed(); //调用父类
	}
	
	private class RepeatDialogListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			switch (id) {
			case R.id.tableRow0:
				updateDialog(mContext.getResources().getString(R.string.topbar_page_title_0));
				if (getCurrentIndex() == spinnerCurSeleIndex) {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					
					break;
				}
				
				spinnerCurSeleIndex = 0;
				editCache(spinnerCurSeleIndex);
				
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				
				showFragment();

				break;
				
			case R.id.tableRow1:
				updateDialog(mContext.getResources().getString(R.string.topbar_page_title_1));
				if (getCurrentIndex() == spinnerCurSeleIndex) {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					
					break;
				}
				
				spinnerCurSeleIndex = 1;
				editCache(spinnerCurSeleIndex);
				
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				
				showFragment();

				break;
				
			case R.id.tableRow2:
				updateDialog(mContext.getResources().getString(R.string.topbar_page_title_2));
				if (getCurrentIndex() == spinnerCurSeleIndex) {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					
					break;
				}
				
				spinnerCurSeleIndex = 2;
				editCache(spinnerCurSeleIndex);
				
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				
				showFragment();

				break;
				
			case R.id.tableRow3:
				updateDialog(mContext.getResources().getString(R.string.topbar_page_title_3));
				if (getCurrentIndex() == spinnerCurSeleIndex) {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					
					break;
				}
				
				spinnerCurSeleIndex = 3;
				editCache(spinnerCurSeleIndex);
				
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				
				showFragment();
				break;
				
//			case R.id.tableRow4:
//				if (dialog != null && dialog.isShowing()) {
//					dialog.dismiss();
//				}
//				break;

			}
		}

	}
}
