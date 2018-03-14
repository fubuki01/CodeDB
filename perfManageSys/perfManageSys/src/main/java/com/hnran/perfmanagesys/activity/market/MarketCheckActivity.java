package com.hnran.perfmanagesys.activity.market;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.fragment.market.MarketCommonFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MarketCheckActivity extends BaseActivity{
	
	private static String[] CUNKUAN_TYPE = {"","定期","活期"};
	private static String[] KEHU_TYPE = {"","个人","企业"};
	
	private LinearLayout llCunDaiType, llKehuType, llKehuZhanghao;
	private TextView tvCunDai;
	private View vCunDaiType, vKehuType;
	
	private TextView tvYuyuebianhao, tvJigoumingchen, tvKehumingchen, 
	 tvCunDaileixing, tvKehuzonglei, 
	 tvZhengjianhaoma, tvLianxidianhua, tvYuyueriqi,
	 tvYingxiaorengonghao, tvYingxiaobili, tvKehuzhanghao;
	
	
	
	private int mType;
	private String yuyuebh, jigoumc, jigoudm, kehumc, cunkuanlx,
	               kehulx, zhengjianlx, zhengjianhm, shoujihm,
	               yuyueriqi, yingxiaorgh, yingxiaobl, kehuzhanghao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
//		Marketing marketing = getIntent().get
//		mMarketing = getIntent().getParcelableExtra("Extra_market");
		
		Intent intent = getIntent();
		
		mType = intent.getIntExtra("Extra_type", 0);
		
		yuyuebh = intent.getStringExtra("Extra_yybh");
		jigoumc = intent.getStringExtra("Extra_jgmc"); 
		jigoudm = intent.getStringExtra("Extra_jgdm");
 	    kehumc = intent.getStringExtra("Extra_khmc"); 
 	    cunkuanlx = intent.getStringExtra("Extra_ckzl"); 
 	    kehulx = intent.getStringExtra("Extra_khzl"); 
 	    zhengjianlx = intent.getStringExtra("Extra_zjlb"); 
        zhengjianhm = intent.getStringExtra("Extra_zjhm"); 
        shoujihm = intent.getStringExtra("Extra_sjhm");
        yuyueriqi = intent.getStringExtra("Extra_yyrq");
        yingxiaorgh = intent.getStringExtra("Extra_yggh");
        yingxiaobl = intent.getStringExtra("Extra_yxbl");
        kehuzhanghao = intent.getStringExtra("Extra_khzh");
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_market_check);

		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);
		tv.setText("详情");
		
		TextView tv1 = (TextView) findViewById(R.id.tv_common_back_title_sec);
		tv1.setVisibility(View.VISIBLE);
		tv1.setText("（" + MarketCommonFragment.TITILS[mType] + "）");

		LinearLayout llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ImageView ivEdit = (ImageView) findViewById(R.id.common_back_title_iv);
		ivEdit.setVisibility(View.VISIBLE);
		ivEdit.setImageResource(R.drawable.ic_edit);
		ivEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				gotoMarketModifyActivity();
				
				finish();
			}
		});
		
		llCunDaiType = (LinearLayout) findViewById(R.id.market_ll_cun_dai_zhonglei);
		llKehuType = (LinearLayout) findViewById(R.id.market_ll_kehu_zhonglei);
		llKehuZhanghao = (LinearLayout) findViewById(R.id.market_ll_kehu_cunkuan_zhanghao);
		
		tvCunDai = (TextView) findViewById(R.id.market_tv_cun_dai_zhonglei);
		
		vCunDaiType = findViewById(R.id.market_view_cun_dai_zhonglei);
		vKehuType= findViewById(R.id.market_view_kehu_zhonglei);
		
		
		tvYuyuebianhao = (TextView) findViewById(R.id.market_check_tv_yuyuebianhao);
		tvJigoumingchen = (TextView) findViewById(R.id.market_check_tv_jigoumingcheng);
		tvKehumingchen = (TextView) findViewById(R.id.market_check_tv_kehumingchen);
		tvCunDaileixing = (TextView) findViewById(R.id.market_check_tv_cun_dai_zhonglei);
		tvKehuzonglei = (TextView) findViewById(R.id.market_check_tv_kehu_zhonglei);
		tvZhengjianhaoma = (TextView) findViewById(R.id.market_check_tv_zhengjainhaoma);
		tvLianxidianhua = (TextView) findViewById(R.id.market_check_tv_lianxidianhua);
		tvYuyueriqi = (TextView) findViewById(R.id.market_check_tv_yuyueriqi);
		tvYingxiaorengonghao = (TextView) findViewById(R.id.market_check_tv_yingxiaorengonghao);
		tvYingxiaobili = (TextView) findViewById(R.id.market_check_tv_yingxiaobili);
		tvKehuzhanghao = (TextView) findViewById(R.id.market_check_tv_kehu_cunkuan_zhanghao);

		changeDataState();
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		

	}
	
	
	public void changeDataState(){
		switch(mType){
    	case MarketCommonFragment.CUNKUAN:
    		Log.d("tt","1");
    		
    		llCunDaiType.setVisibility(View.VISIBLE);
    		llKehuType.setVisibility(View.VISIBLE);
    		llKehuZhanghao.setVisibility(View.GONE);
    		vCunDaiType.setVisibility(View.VISIBLE);
    		vKehuType.setVisibility(View.VISIBLE);
    		
    		tvCunDai.setText("存款类型：");
    		
    		String cunkuan = "", kehu = "";
    		try{
    			cunkuan = CUNKUAN_TYPE[Integer.valueOf(cunkuanlx)];
    			kehu = KEHU_TYPE[Integer.valueOf(kehulx)];
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    		
    		tvCunDaileixing.setText(cunkuan);
    		tvKehuzonglei.setText(kehu);
    		
    		break;
//    	case MarketCommonFragment.DAIKUAN:
//    		Log.d("tt","2");
//    		
//    		llCunDaiType.setVisibility(View.VISIBLE);
//    		llKehuType.setVisibility(View.VISIBLE);
//    		llKehuZhanghao.setVisibility(View.GONE);
//    		vCunDaiType.setVisibility(View.VISIBLE);
//    		vKehuType.setVisibility(View.VISIBLE);
//    		
//    		tvCunDai.setText("贷款类型：");
//    		
//    		tvCunDaileixing.setText(cunkuanlx);
//
//
//    		String kehu2 = "";
//    		try{
//    			kehu2 = KEHU_TYPE[Integer.valueOf(kehulx)];
//    		}catch(Exception e){
//    			e.printStackTrace();
//    		}
//    		
//    		tvKehuzonglei.setText(kehu2);
//    		
//    		
//    		break;
    	case MarketCommonFragment.SHOUJI:
    		Log.d("tt","3");
    		llCunDaiType.setVisibility(View.GONE);
    		llKehuType.setVisibility(View.GONE);
    		llKehuZhanghao.setVisibility(View.GONE);
    		vCunDaiType.setVisibility(View.GONE);
    		vKehuType.setVisibility(View.GONE);
    		
    		break;
    	case MarketCommonFragment.ETC:
    		Log.d("tt","3");
    		llCunDaiType.setVisibility(View.GONE);
    		llKehuType.setVisibility(View.GONE);
    		llKehuZhanghao.setVisibility(View.GONE);
    		vCunDaiType.setVisibility(View.GONE);
    		vKehuType.setVisibility(View.GONE);
    		
    		break;
    	case MarketCommonFragment.POS:
    		Log.d("tt","3");
    		llCunDaiType.setVisibility(View.GONE);
    		llKehuType.setVisibility(View.GONE);
    		vCunDaiType.setVisibility(View.GONE);
    		vKehuType.setVisibility(View.GONE);
    		llKehuZhanghao.setVisibility(View.VISIBLE);
    		
    		tvKehuzhanghao.setText(kehuzhanghao);
    		
    		break;
    		
    	}
		
		tvYuyuebianhao.setText(yuyuebh);
		tvJigoumingchen.setText(jigoumc);
		tvKehumingchen.setText(kehumc);
		
		tvZhengjianhaoma.setText(zhengjianhm); 
		tvLianxidianhua.setText(shoujihm); 
		tvYuyueriqi.setText(yuyueriqi);
		tvYingxiaorengonghao.setText(yingxiaorgh); 
		tvYingxiaobili.setText(yingxiaobl); 
		
	}
	
	public void gotoMarketModifyActivity(){
		Intent intent = new Intent(MarketCheckActivity.this, MarketModifyActivity.class);
		intent.putExtra("Extra_type", mType);
		
		intent.putExtra("Extra_yybh", yuyuebh);
		intent.putExtra("Extra_jgmc", jigoumc);
		intent.putExtra("Extra_jgdm", jigoudm);
		intent.putExtra("Extra_khmc", kehumc);
		intent.putExtra("Extra_ckzl", cunkuanlx);
		intent.putExtra("Extra_khzl", kehulx);
		intent.putExtra("Extra_zjlb", zhengjianlx);
		intent.putExtra("Extra_zjhm", zhengjianhm);
		intent.putExtra("Extra_sjhm", shoujihm);
		intent.putExtra("Extra_yyrq", yuyueriqi);
		intent.putExtra("Extra_yggh", yingxiaorgh);
		intent.putExtra("Extra_yxbl", yingxiaobl);
		intent.putExtra("Extra_khzh", kehuzhanghao);
		
		startActivity(intent);
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub
		
	}
	
//	public class MarketInside implements Parcelable{
//		
//	}
	
	
}
