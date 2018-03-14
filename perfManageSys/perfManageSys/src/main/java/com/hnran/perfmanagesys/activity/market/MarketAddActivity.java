package com.hnran.perfmanagesys.activity.market;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.broadcastreceiver.UpdateViewReceiver;
import com.hnran.perfmanagesys.fragment.market.MarketCommonFragment;
import com.hnran.perfmanagesys.utils.DatePickerUtil;
import com.hnran.perfmanagesys.utils.IDNumberValidator;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.view.ImageDeleteEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 营销申报，增加页面
 * 
 * @author tan
 *
 */
public class MarketAddActivity extends BaseActivity{

	public final static int OPTION_MARKET = 1000;

	private final int CHOOSE_JIGOU = 20010;

	//营销申报五种类型，对应的选择按钮
	private final int[] IDS = {R.id.btn1, R.id.btn3, R.id.btn4, R.id.btn5};

	//切换类型，隐藏或显现相应模块
	private LinearLayout llCunkuanType, llKehuType, llKehuZhanghao;
	private View vCunkuanType, vKehuType;

	//
	private RadioGroup rgMarketType;

	private Button btnSave;

	//
	private ImageDeleteEditText tvYuyuebianhao, tvJigoumingchen, tvKehumingchen, 
	tvCunkuanleixing, tvKehuzonglei, tvZhengjianleibie,
	tvZhengjianhaoma, tvLianxidianhua, tvYuyueriqi,
	tvYingxiaorengonghao, tvYingxiaobili, tvKehuzhanghao;

	//单选按钮，存款类别，客户类别，证件类别
	private RadioGroup rgCunkuan, rgKehu;

	//五种类型，添加为OTHER，
	private int mUpdateType;
	private int mInsertType;

	private String yuyuebh, jigoumc, jigoudm, kehumc, zhengjianhm, shoujihm,
	yuyueriqi, yingxiaorgh, yingxiaobl, kehuzhanghao;

	//提交参数列表
	private Map<String, String> mParams;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		mParams = new HashMap<String, String>(); 

		mInsertType = getIntent().getIntExtra("Extra_index", 0);
		mUpdateType = -1;

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_market_add);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("添加");

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		llCunkuanType = (LinearLayout) findViewById(R.id.market_ll_cun_kuan_zhonglei);
		llKehuType = (LinearLayout) findViewById(R.id.market_ll_kehu_zhonglei);
		llKehuZhanghao = (LinearLayout) findViewById(R.id.market_ll_kehu_cunkuan_zhanghao);

		vCunkuanType = findViewById(R.id.market_view_cun_kuan_zhonglei);
		vKehuType= findViewById(R.id.market_view_kehu_zhonglei);


		btnSave = (Button)findViewById(R.id.market_cunkuan_btn_save);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(judgeParam()){
					upLoadData();
					btnSave.setClickable(false);
				}
			}
		});

		tvYuyuebianhao = (ImageDeleteEditText) findViewById(R.id.market_et_yuyuebianhao);
		tvJigoumingchen = (ImageDeleteEditText) findViewById(R.id.market_et_jigoumingcheng);
		tvJigoumingchen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MarketAddActivity.this, MarketChooseJigouActivity.class);
				startActivityForResult(intent, CHOOSE_JIGOU);
			}
		});

		tvCunkuanleixing = (ImageDeleteEditText) findViewById(R.id.market_et_cun_kuan_zhonglei);
		tvKehumingchen = (ImageDeleteEditText) findViewById(R.id.market_et_kehumingchen);
		tvKehuzonglei = (ImageDeleteEditText) findViewById(R.id.market_et_kehu_zhonglei);
		tvZhengjianleibie = (ImageDeleteEditText) findViewById(R.id.market_et_zhengjianleixing);
		tvZhengjianhaoma = (ImageDeleteEditText) findViewById(R.id.market_et_zhengjainhaoma);
		tvLianxidianhua = (ImageDeleteEditText) findViewById(R.id.market_et_lianxidianhua);
		tvYuyueriqi = (ImageDeleteEditText) findViewById(R.id.market_et_yuyueriqi);
		tvYuyueriqi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				DatePickUtil.showDatePickDlg(MarketAddActivity.this, tvYuyueriqi, false, null);
				DatePickerUtil.showYearMonthDayPicker(MarketAddActivity.this, tvYuyueriqi, true, null);
			}
		});

		tvYingxiaorengonghao = (ImageDeleteEditText) findViewById(R.id.market_et_yingxiaorengonghao);
		tvYingxiaorengonghao.setFocusable(false);
		tvYingxiaorengonghao.setText(PMSApplication.gUser.getTellId());

		tvYingxiaobili = (ImageDeleteEditText) findViewById(R.id.market_et_yingxiaobili);
		tvYingxiaobili.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				judgeYingxiaoBili(tvYingxiaobili.getText().toString().trim());
			}
		});
		tvYingxiaobili.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER){
					judgeYingxiaoBili(tvYingxiaobili.getText().toString().trim());
				}
				return false;
			}
		});


		tvKehuzhanghao = (ImageDeleteEditText) findViewById(R.id.market_et_kehu_cunkuan_zhanghao);


		rgMarketType = (RadioGroup) findViewById(R.id.radio_group); 
		rgMarketType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
			@Override  
			public void onCheckedChanged(RadioGroup group, int checkedId) {  
				changeDataState(checkedId);
			}  
		});


		rgMarketType.check(IDS[mInsertType]);
		changeDataState(IDS[mInsertType]);

		rgCunkuan = (RadioGroup) findViewById(R.id.market_rb_cunkuan_zhonglei);
		rgKehu = (RadioGroup) findViewById(R.id.market_rb_kehu_zhonglei);

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	private void judgeYingxiaoBili(String bili){
		if(bili == null || "".equals(bili)) return; 
		int bl = 0;
		try{
			bl = Integer.valueOf(bili);
		}catch(NumberFormatException  e){
			e.printStackTrace();
		}
		if(bl > 100){
			tvYingxiaobili.setText("100");
			ToastUtil.showToast(MarketAddActivity.this, "营销比例不能大于100");

		}else if(bl < 0){
			tvYingxiaobili.setText("0");
			ToastUtil.showToast(MarketAddActivity.this, "营销比例不能小于0");
		}
	}

	private boolean judgeParam(){

		//判断机构名称
		jigoumc = tvJigoumingchen.getText().toString().trim();
		if("".equals(jigoumc)){
			ToastUtil.showToast(MarketAddActivity.this, "未选择机构");
			return false;
		}

		//判断身份证号
		zhengjianhm = tvZhengjianhaoma.getText().toString().trim();

		if("1".equals(getKehuGroup())){
			/**
			 * 判断身份证号码正确性
			 */
			IDNumberValidator iv = new IDNumberValidator();
			if(!iv.isValidatedAllIdcard(zhengjianhm)){
				ToastUtil.showToast(MarketAddActivity.this, "身份证号码不合法");
				tvZhengjianhaoma.requestFocus();
				return false;
			}
		}

		//判断预约日期
		yuyueriqi = tvYuyueriqi.getText().toString().trim();
		if("".equals(yuyueriqi)){
			ToastUtil.showToast(MarketAddActivity.this, "未选择预约日期");
			return false;
		}
		
		yingxiaobl = tvYingxiaobili.getText().toString().trim();
		try{
			BigDecimal bl = new BigDecimal(yingxiaobl);
			judgeYingxiaoBili(yingxiaobl);
		}catch(Exception e){
			e.printStackTrace();
			ToastUtil.showToast(MarketAddActivity.this, "营销比例错误");
			return false;
		}

		return true;
	}

	/**
	 * 点击RadioButton，执行相应的操作，显示对应表单
	 * @param id
	 */
	public void changeDataState(int id){
		switch(id){
		case R.id.btn1:
			Log.d("tt","1");

			mInsertType = MarketCommonFragment.CUNKUAN;

			llCunkuanType.setVisibility(View.VISIBLE);
			llKehuType.setVisibility(View.VISIBLE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.VISIBLE);
			vKehuType.setVisibility(View.VISIBLE);

			initEditText();

			break;
			//		case R.id.btn2:
			//			Log.d("tt","2");
			//
			//			mInsertType = MarketCommonFragment.DAIKUAN;
			//			
			//			llCunkuanType.setVisibility(View.GONE);
			//			llDaikuanType.setVisibility(View.VISIBLE);
			//			llKehuType.setVisibility(View.VISIBLE);
			//			llKehuZhanghao.setVisibility(View.GONE);
			//			vCunkuanType.setVisibility(View.GONE);
			//			vDaikuanType.setVisibility(View.VISIBLE);
			//			vKehuType.setVisibility(View.VISIBLE);
			//
			//			initEditText();
			//
			//			break;
		case R.id.btn3:
			Log.d("tt","3");
			mInsertType = MarketCommonFragment.SHOUJI;
			llCunkuanType.setVisibility(View.GONE);
			llKehuType.setVisibility(View.GONE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.GONE);
			vKehuType.setVisibility(View.GONE);

			initEditText();
			break;
		case R.id.btn4:
			Log.d("tt","3");
			mInsertType = MarketCommonFragment.ETC;
			llCunkuanType.setVisibility(View.GONE);
			llKehuType.setVisibility(View.GONE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.GONE);
			vKehuType.setVisibility(View.GONE);

			initEditText();
			break;
		case R.id.btn5:
			Log.d("tt","3");
			mInsertType = MarketCommonFragment.POS;
			llCunkuanType.setVisibility(View.GONE);
			llKehuType.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.GONE);
			vKehuType.setVisibility(View.GONE);
			llKehuZhanghao.setVisibility(View.VISIBLE);

			initEditText();
			break;

		}

	}

	/**
	 * 点击保存，执行相应的操作，保存对应表单
	 * @param id
	 */
	public void saveDataState(){

		yuyuebh = tvYuyuebianhao.getText().toString().trim();
		//		jigoumc = tvJigoumingchen.getText().toString().trim();
		kehumc = tvKehumingchen.getText().toString().trim();
		zhengjianhm = tvZhengjianhaoma.getText().toString().trim(); 
		shoujihm = tvLianxidianhua.getText().toString().trim(); 
		yuyueriqi = tvYuyueriqi.getText().toString().trim();
		yingxiaorgh = tvYingxiaorengonghao.getText().toString().trim(); 
		yingxiaobl = tvYingxiaobili.getText().toString().trim(); 
		kehuzhanghao = tvKehuzhanghao.getText().toString().trim();

		mParams.clear();
		mParams.put("jgdm", jigoudm != null ? jigoudm:"");
		mParams.put("khmc", kehumc != null ? kehumc:"");
		mParams.put("zjlb", "1");
		mParams.put("zjhm", zhengjianhm != null ? zhengjianhm:"");
		mParams.put("sjhm", shoujihm != null ? shoujihm:"");
		mParams.put("yyrq", yuyueriqi != null ? yuyueriqi :"");
		mParams.put("yggh", yingxiaorgh != null ? yingxiaorgh:"");
		mParams.put("yxbl", yingxiaobl != null ? yingxiaobl:"");

		int id = rgMarketType.getCheckedRadioButtonId();
		switch(id){
		case R.id.btn1:
			mParams.put("ckzl", getCunkuanGroup());
			mParams.put("khzl", getKehuGroup());
			break;

		case R.id.btn3:


			break;
		case R.id.btn4:


			break;
		case R.id.btn5:
			mParams.put("khckzh", kehuzhanghao);
			break;

		}

		if(mUpdateType != -1){	//修改 , 参数包括预约编号
			mParams.put("yybh", yuyuebh);
		}


	}

	public void initEditText(){
		String str = "";
		tvYuyuebianhao.setText(str);
		tvJigoumingchen.setText(str);
		tvKehumingchen.setText(str);
		tvCunkuanleixing.setText(str);
		tvKehuzonglei.setText(str);
		tvZhengjianleibie.setText(str);
		tvZhengjianhaoma.setText(str);
		tvLianxidianhua.setText(str); 
		tvYuyueriqi.setText(str);
		//		tvYingxiaorengonghao.setText(str); 
		tvYingxiaobili.setText("100"); 
		tvKehuzhanghao.setText(str);
	}

	public void upLoadData(){

		saveDataState();

		String str = "";
		switch(mUpdateType){
		case MarketCommonFragment.CUNKUAN:
			str = "mobileUpdateDepositMarketing.action";
			break;
			//		case MarketCommonFragment.DAIKUAN:
			//			str = "mobileUpdateLoanMarketing.action";
			//			break;
		case MarketCommonFragment.SHOUJI:
			str = "mobileUpdateCellBankMarketing.action";
			break;
		case MarketCommonFragment.ETC:
			str = "mobileUpdateEtcMarketing.action";
			break;
		case MarketCommonFragment.POS:
			str = "mobileUpdatePosMarketing.action";
			break;
		case MarketCommonFragment.OTHER:
			//添加，
			switch(mInsertType){
			case MarketCommonFragment.CUNKUAN:
				str = "mobileInsertDepositMarketing.action";  
				break;
				//			case MarketCommonFragment.DAIKUAN:
				//				str = "mobileInsertLoanMarketing.action";  
				//				break;
			case MarketCommonFragment.SHOUJI:
				str = "mobileInsertCellBankMarketing.action";  
				break;
			case MarketCommonFragment.ETC:
				str = "mobileInsertEtcMarketing.action";  
				break;
			case MarketCommonFragment.POS:
				str = "mobileInsertPosMarketing.action";
				break;

			}
			break;

		}

		String url = MakeUrl.makeURL(new String[]{str});

		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  

				/**
				 * 发送更新广播
				 */
				Intent intent = new Intent();

				if(mUpdateType == MarketCommonFragment.OTHER)
					intent.putExtra("Extra_update_index", mInsertType);
				else 
					intent.putExtra("Extra_update_index", mUpdateType);

				intent.setAction(UpdateViewReceiver.UPDATE_VIEW);
				MarketAddActivity.this.sendBroadcast(intent);

				ToastUtil.showToast(MarketAddActivity.this, "成功");
				MarketAddActivity.this.setResult(OPTION_MARKET);
				MarketAddActivity.this.finish();


			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				ToastUtil.showToast(MarketAddActivity.this, "失败");
				MarketAddActivity.this.finish();
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				return mParams;
			}
		}; 


		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}


	//存款种类
	public void setCunkuanGroup(String type){
		if("1".equals(type)){
			rgCunkuan.check(R.id.market_rb_cunkuan_zhonglei_dingqi);
		}else if("2".equals(type)){
			rgCunkuan.check(R.id.market_rb_cunkuan_zhonglei_huoqi);
		}
	}

	public String getCunkuanGroup(){
		String str = "1";
		int id = rgCunkuan.getCheckedRadioButtonId();
		if(id == R.id.market_rb_cunkuan_zhonglei_dingqi){
			str = "1";
		}else if(id == R.id.market_rb_cunkuan_zhonglei_huoqi){
			str = "2";
		}
		return str;
	}

	//客户种类
	public void setKehuGroup(String type){
		if("1".equals(type)){
			rgKehu.check(R.id.market_rb_kehu_zhonglei_geren);
		}else if("2".equals(type)){
			rgKehu.check(R.id.market_rb_kehu_zhonglei_duigong);
		}
	}

	public String getKehuGroup(){
		String str = "1";
		int id = rgKehu.getCheckedRadioButtonId();
		if(id == R.id.market_rb_kehu_zhonglei_geren){
			str = "1";
		}else if(id == R.id.market_rb_kehu_zhonglei_duigong){
			str = "2";
		}
		return str;
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CHOOSE_JIGOU && data != null){
			jigoumc = data.getStringExtra("Extra_jigou_zzjc");
			jigoudm = data.getStringExtra("Extra_jigou_ywdm");
			tvJigoumingchen.setText(jigoumc);
		}
	}


}
