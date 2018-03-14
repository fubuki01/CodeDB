package com.hnran.perfmanagesys.activity;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.utils.MD5;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.view.ImageDeleteEditText;
import com.hnran.perfmanagesys.view.ImageDeleteEditText.TextChangedListener;
import com.loveplusplus.update.AutoUpdFeedBackInterface;
import com.loveplusplus.update.AutoUpdateConfig;
import com.loveplusplus.update.UpdateChecker;
import com.readboy.ssm.po.LoginStatusMessage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 登录页面
 * @author tan
 * 2017-05-25
 *
 */
public class LoginActivity extends BaseActivity implements AutoUpdFeedBackInterface{

	private ImageDeleteEditText etAccount, etPassword;
	private Button btnLogin;
	private CheckBox cbRemember;	//记住账号和密码的CheckBox
	private SharedPreferences sp;	//账号和密码的SharedPreference

	private LinearLayout llWarm;

	private boolean btnLoginEnable = false;
	private int accountTextLength = 0, passwordTextLength = 0;

	private long lastBackKeyTouchTime = 0;
	private long lastBackKeyTouchTimeCOUNT = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * 应用自动更新
		 */
		AutoUpdateConfig.configAppUpdateURL(MakeUrl.HEAD);
		UpdateChecker.setCheckTimeDistance(0);// 以小时为单位、0表示次次都检测更新
		UpdateChecker.setPopInstallActivity(true);
		UpdateChecker.checkForNotification(this);

	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setContentView(R.layout.activity_login); 

		etAccount = (ImageDeleteEditText) findViewById(R.id.login_et_user);
		etAccount.setTextChangedListener(new TextChangedListener() {

			@Override
			public void addTextNumber(int length) {
				// TODO Auto-generated method stub
				accountTextLength = length;
				changeBtnLoginEnable();
			}
		});

		etPassword= (ImageDeleteEditText) findViewById(R.id.login_et_password);
		etPassword.setPasswordType();
		etPassword.setTextChangedListener(new TextChangedListener() {

			@Override
			public void addTextNumber(int length) {
				// TODO Auto-generated method stub
				passwordTextLength = length;
				changeBtnLoginEnable();
			}
		});

		etPassword.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER ){
					if(btnLoginEnable) 
						login();
				}
				return false;
			}
		});

		btnLogin = (Button) findViewById(R.id.login_btn_go);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});

		llWarm = (LinearLayout) findViewById(R.id.login_ll_warm);

		ImageView iv = (ImageView)findViewById(R.id.login_iv_logo);
		//测试
//		iv.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (System.currentTimeMillis() - lastBackKeyTouchTime < 2000) {
//					lastBackKeyTouchTimeCOUNT++;
//					if(lastBackKeyTouchTimeCOUNT > 5){
//
//						PMSApplication.gUser = new User();
//						PMSApplication.gUser.setTellId("8071");
//						PMSApplication.gToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOjB9.Hp82g3Ozq6QlGEgTmsZnbQLpNbuaItusAyZt-B9y_Cg";
//						PMSApplication.isLogin = true;
//						
//						Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//						startActivity(intent);
//						LoginActivity.this.finish();
//					}
//				} else {
//					lastBackKeyTouchTime = System.currentTimeMillis();
//					lastBackKeyTouchTimeCOUNT = 0;
//				}
//			}
//		});
		//回忆账号和密码
		cbRemember = (CheckBox) findViewById(R.id.cb_remember);
		sp = this.getSharedPreferences("AccountAndPassword", Context.MODE_PRIVATE);
		if(sp.getBoolean("IS_REMEMBER",false)){
			cbRemember.setChecked(true);
			etAccount.setText(sp.getString("USER_NAME",""));
			etPassword.setText(sp.getString("PASSWORD",""));
		}
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	private void login(){
		llWarm.setVisibility(View.INVISIBLE);

		//		ToastUtil.showToast(LoginActivity.this, "登录中...");

		requestLoginResult();
	}

	public void changeBtnLoginEnable(){
		if(accountTextLength > 0 && passwordTextLength > 0){
			if(!btnLoginEnable){
				btnLoginEnable = true;
				btnLogin.setEnabled(true);
			}
		}else{
			if(btnLoginEnable){
				btnLoginEnable = false;
				btnLogin.setEnabled(false);
			}
		}
	}

	public void requestLoginResult(){
		btnLogin.setEnabled(false);
		btnLogin.setText("登录中...");
		String url = MakeUrl.makeURL(new String[]{"login.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				ToastUtil.cancelToast(LoginActivity.this);

				LoginStatusMessage user = null;
				try{

					user = JSON.parseObject(response, LoginStatusMessage.class);

				}catch(JSONException e){
					e.printStackTrace();
				}


				/**
				 * 处理服务器返回的登录结果信息
				 */
				if(user != null){

					/**
					 * 记住用户名和密码
					 */
					SharedPreferences.Editor editor = sp.edit();
					if(cbRemember.isChecked())
					{
						editor.putString("USER_NAME", etAccount.getText().toString().trim());
						editor.putString("PASSWORD", etPassword.getText().toString().trim());
					}
					editor.putBoolean("IS_REMEMBER", cbRemember.isChecked());
					editor.commit();

					PMSApplication.gUser = user.getUser();
					PMSApplication.gToken = user.getToken();
					PMSApplication.isLogin = true;

					//成功则进入首页
					Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();

				}else{
					//失败则提示错误
					llWarm.setVisibility(View.VISIBLE);
					btnLogin.setEnabled(true);
					btnLogin.setText("登录");
				}

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				//				ToastUtil.cancelToast(LoginActivity.this);
				ToastUtil.showToast(LoginActivity.this, CommonContent.ERROR_NETWORK);
				btnLogin.setEnabled(true);
				btnLogin.setText("登录");
				
//				if (!CheckNewWorkConnection.isNetworkConnected(getApplicationContext())) {
//					ToastUtil.showToast(LoginActivity.this, "网络连接失败,请检查您的网络");
//				}
				
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();

				map.put("username", etAccount.getText().toString().trim());

				String password = MD5.createMD5(etPassword.getText().toString().trim());
				password = password.toUpperCase();
				map.put("password", password);
				//map.put("password", etPassword.getText().toString().trim());
				
				return map;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
//		VolleyUtil.getInstanceRequestQueue().cancelAll(null);
		ToastUtil.cancelToast(LoginActivity.this);

	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishUpd(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		//检测更新完成回调
		
	}

	@Override
	public void forceUpd() {
		// TODO Auto-generated method stub
		//强制更新回调
	}
}
