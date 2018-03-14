package com.hnran.perfmanagesys.activity.market;

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
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.view.ImageDeleteEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 营销申报，修改页面
 * 
 * @author tan
 *
 */
public class MarketModifyActivity extends BaseActivity{

	public final static int OPTION_MARKET = 1000;

	private final int CHOOSE_JIGOU = 20011;

	//切换类型，隐藏或显现相应模块
	private LinearLayout llCunkuanType, llKehuType, llKehuZhanghao;
	private View vCunkuanType, vKehuType;

	//
	private ImageDeleteEditText tvYuyuebianhao, tvJigoumingchen, tvKehumingchen, 
	tvCunDaileixing, tvKehuzonglei,
	tvZhengjianhaoma, tvLianxidianhua, tvYuyueriqi,
	tvYingxiaorengonghao, tvYingxiaobili, tvKehuzhanghao;

	//单选按钮，存款类别，客户类别
	private RadioGroup rgCunkuan, rgKehu;

	private Button btnSave;

	//五种类型，添加为OTHER，
	private int mUpdateType;

	private String yuyuebh, jigoumc, jigoudm, kehumc, cunkuanlx,
	kehulx, zhengjianhm, shoujihm,
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

		Intent intent = getIntent();

		mUpdateType = intent.getIntExtra("Extra_type", 0);

		yuyuebh = intent.getStringExtra("Extra_yybh");
		jigoumc = intent.getStringExtra("Extra_jgmc"); 
		jigoudm = intent.getStringExtra("Extra_jgdm");
		kehumc = intent.getStringExtra("Extra_khmc"); 
		cunkuanlx = intent.getStringExtra("Extra_ckzl"); 
		kehulx = intent.getStringExtra("Extra_khzl"); 
		zhengjianhm = intent.getStringExtra("Extra_zjhm"); 
		shoujihm = intent.getStringExtra("Extra_sjhm");
		yuyueriqi = intent.getStringExtra("Extra_yyrq");
		yingxiaorgh = intent.getStringExtra("Extra_yggh");
		yingxiaobl = intent.getStringExtra("Extra_yxbl");
		if(yingxiaobl == null || "".equals(yingxiaobl)){
			yingxiaobl = "100";
		}
		kehuzhanghao = intent.getStringExtra("Extra_khzh");

		mParams = new HashMap<String, String>(); 

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_market_modify);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("修改");

		TextView tv1 = (TextView) findViewById(R.id.tv_common_back_title_sec);
		tv1.setVisibility(View.VISIBLE);
		tv1.setText("（" + MarketCommonFragment.TITILS[mUpdateType] + "）");

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
		tvYuyuebianhao.setFocusable(false);
		tvJigoumingchen = (ImageDeleteEditText) findViewById(R.id.market_et_jigoumingcheng);
		tvJigoumingchen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MarketModifyActivity.this, MarketChooseJigouActivity.class);
				startActivityForResult(intent, CHOOSE_JIGOU);
			}
		});

		tvCunDaileixing = (ImageDeleteEditText) findViewById(R.id.market_et_cun_kuan_zhonglei);
		tvKehumingchen = (ImageDeleteEditText) findViewById(R.id.market_et_kehumingchen);
		tvKehuzonglei = (ImageDeleteEditText) findViewById(R.id.market_et_kehu_zhonglei);
		tvZhengjianhaoma = (ImageDeleteEditText) findViewById(R.id.market_et_zhengjainhaoma);
		tvZhengjianhaoma.setFocusable(false);
		tvLianxidianhua = (ImageDeleteEditText) findViewById(R.id.market_et_lianxidianhua);
		tvYuyueriqi = (ImageDeleteEditText) findViewById(R.id.market_et_yuyueriqi);
		tvYuyueriqi.setFocusable(false);

		tvYingxiaorengonghao = (ImageDeleteEditText) findViewById(R.id.market_et_yingxiaorengonghao);
		tvYingxiaorengonghao.setFocusable(false);
		
		tvYingxiaobili = (ImageDeleteEditText) findViewById(R.id.market_et_yingxiaobili);
		tvKehuzhanghao = (ImageDeleteEditText) findViewById(R.id.market_et_kehu_cunkuan_zhanghao);

		changeDataState();

		rgCunkuan = (RadioGroup) findViewById(R.id.market_rb_cunkuan_zhonglei);
		rgKehu = (RadioGroup) findViewById(R.id.market_rb_kehu_zhonglei);

		tvYuyuebianhao.setText(yuyuebh);
		tvJigoumingchen.setText(jigoumc);
		tvKehumingchen.setText(kehumc);

		tvZhengjianhaoma.setText(zhengjianhm); 
		tvLianxidianhua.setText(shoujihm); 
		tvYuyueriqi.setText(yuyueriqi);
		tvYingxiaorengonghao.setText(yingxiaorgh); 
		tvYingxiaobili.setText(yingxiaobl); 
		tvKehuzhanghao.setText(kehuzhanghao);

		setCunkuanGroup(cunkuanlx);
		setKehuGroup(kehulx);

	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	private boolean judgeParam(){

		//判断机构名称
		jigoumc = tvJigoumingchen.getText().toString().trim();
		if("".equals(jigoumc)){
			ToastUtil.showToast(MarketModifyActivity.this, "未选择机构");
			return false;
		}

		return true;
	}


	/**
	 * 点击RadioButton，执行相应的操作，显示对应表单
	 * @param id
	 */
	public void changeDataState(){
		switch(mUpdateType){
		case MarketCommonFragment.CUNKUAN:
			Log.d("tt","1");

			llCunkuanType.setVisibility(View.VISIBLE);
			llKehuType.setVisibility(View.VISIBLE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.VISIBLE);
			vKehuType.setVisibility(View.VISIBLE);

			initEditText();

			break;

		case MarketCommonFragment.SHOUJI:
			Log.d("tt","3");
			llCunkuanType.setVisibility(View.GONE);
			llKehuType.setVisibility(View.GONE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.GONE);
			vKehuType.setVisibility(View.GONE);

			initEditText();
			break;
		case MarketCommonFragment.ETC:
			Log.d("tt","3");
			llCunkuanType.setVisibility(View.GONE);
			llKehuType.setVisibility(View.GONE);
			llKehuZhanghao.setVisibility(View.GONE);
			vCunkuanType.setVisibility(View.GONE);
			vKehuType.setVisibility(View.GONE);

			initEditText();
			break;
		case MarketCommonFragment.POS:
			Log.d("tt","3");
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
		jigoumc = tvJigoumingchen.getText().toString().trim();
		kehumc = tvKehumingchen.getText().toString().trim();
		cunkuanlx = tvCunDaileixing.getText().toString().trim();
		kehulx = tvKehuzonglei.getText().toString().trim();
		zhengjianhm = tvZhengjianhaoma.getText().toString().trim(); 
		shoujihm = tvLianxidianhua.getText().toString().trim(); 
		yuyueriqi = tvYuyueriqi.getText().toString().trim();
		yingxiaorgh = tvYingxiaorengonghao.getText().toString().trim(); 
		yingxiaobl = tvYingxiaobili.getText().toString().trim(); 
		kehuzhanghao = tvKehuzhanghao.getText().toString().trim();

		mParams.clear();
		mParams.put("jgdm", jigoudm);
		mParams.put("khmc", kehumc);
		mParams.put("zjlb", "1");
		mParams.put("zjhm", zhengjianhm);
		mParams.put("sjhm", shoujihm);
		mParams.put("yyrq", yuyueriqi);
		mParams.put("yggh", yingxiaorgh);
		mParams.put("yxbl", yingxiaobl);

		switch(mUpdateType){
		case MarketCommonFragment.CUNKUAN:
			mParams.put("ckzl", getCunkuanGroup());
			mParams.put("khzl", getKehuGroup());
			break;

			//		case R.id.btn2:
			//			mParams.put("khzl", getKehuGroup());
			//			break;
		case R.id.btn3:


			break;
		case R.id.btn4:


			break;
		case MarketCommonFragment.POS:
			mParams.put("khckzh", kehuzhanghao);
			break;

		}

		mParams.put("yybh", yuyuebh);

	}

	public void initEditText(){
		String str = "";
		tvYuyuebianhao.setText(str);
		tvJigoumingchen.setText(str);
		tvKehumingchen.setText(str);
		tvCunDaileixing.setText(str);
		tvKehuzonglei.setText(str);
		tvZhengjianhaoma.setText(str);
		tvLianxidianhua.setText(str); 
		tvYuyueriqi.setText(str);
//		tvYingxiaorengonghao.setText(str); 
		tvYingxiaobili.setText(str); 
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
				Bundle bunble = MarketModifyActivity.this.getIntent().getExtras();
				bunble.putInt("Extra_update_index", mUpdateType);
				intent.putExtras(bunble);

				intent.setAction(UpdateViewReceiver.UPDATE_VIEW);
//				MarketModifyActivity.this.sendBroadcast(intent);
				PMSApplication.getGlobalContext().sendBroadcast(intent);

				ToastUtil.showToast(MarketModifyActivity.this, "成功");
				MarketModifyActivity.this.setResult(OPTION_MARKET);
				MarketModifyActivity.this.finish();


			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
				ToastUtil.showToast(MarketModifyActivity.this, "失败");
				MarketModifyActivity.this.finish();
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
