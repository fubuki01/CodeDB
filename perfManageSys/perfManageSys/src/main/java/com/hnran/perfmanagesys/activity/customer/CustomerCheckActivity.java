package com.hnran.perfmanagesys.activity.customer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseActivity;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.adapter.CustomerProductDaikuanPersonAdapter;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.hnran.perfmanagesys.view.ScrollSlidingTabStrip;
import com.hnran.perfmanagesys.view.ScrollSlidingTabStrip.TabClickListener;
import com.hnran.perfmanagesys.view.FadingScrollView;
import com.hnran.perfmanagesys.view.FadingScrollView.ScrollChangeChildListener;
import com.hnran.perfmanagesys.view.ScrollerLayout;
import com.hnran.perfmanagesys.visit.VisitMainActivity;
import com.hnran.perfmanagesys.visit.VisitMainActivityOld;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.readboy.ssm.po.CellBankCustomerPerson;
import com.readboy.ssm.po.CellBankCustomerProduct;
import com.readboy.ssm.po.Customer;
import com.readboy.ssm.po.CustomerDetail;
import com.readboy.ssm.po.DepositAccountFamily;
import com.readboy.ssm.po.DepositAccountPerson;
import com.readboy.ssm.po.EtcCustomerFamily;
import com.readboy.ssm.po.LoanCustomerPerson;
import com.readboy.ssm.po.LoanCustomerProduct;
import com.readboy.ssm.po.RelatedPerson;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomerCheckActivity extends BaseActivity{

	public final String[] CUSTOMER_TYPE = {"", "个人客户","对公客户"};

	private final Map<String, String> RelatedPersonMap = new HashMap<String, String>();

	private final int BASE = 0;
	private final int RELATION = 1;
	private final int PRODUCT = 2;

	private LinearLayout llBack;
	private LinearLayout llAddUser;

	private TextView tvEdit;

	private LinearLayout llBaseInfo, llRelationUser, llProduct;

	private ScrollSlidingTabStrip scrollSlidingTabStrip;

	private FadingScrollView scrollView;
	private ScrollerLayout scrollerLayout;

	private ProgressBar progressBar;
	

	/**
	 * 基本信息
	 */
	private TextView tvBumen, tvKehubianhao, tvKehumingchen, 
	tvLianxidianhua, tvChiyouchp, tvDizhi,
	tvKaihuriqi, tvFarendaibiao, tvQiyeguimo, tvJingyingfanwei;


	/**
	 * 持有产品
	 */
	private TextView tvCunkuanMore, tvDaikuanMore, tvShoujiMore, tvEtcMore;

	//存款信息
	private LinearLayout llCunkuanRoot;
	private TextView tvCunkuan_mingchen, tvCunkuan_kaihujigou, tvCunkuan_lilv, 
	tvCunkuan_kaihuriqi, tvCunkuan_jine, tvCunkuan_leixing, 
	tvCunkuan_tuozhanren, tvCunkuan_bili;

	//存款管户人
	private LinearLayout llCunkuanUserAdd;

	//贷款信息
	private LinearLayout llDaikuanRoot;
	private TextView tvDaikuan_mingchen, tvDaikuan_kaihujigou, tvDaikuan_lilv, 
	tvDaikuan_fafangriqi, tvDaikuan_daoqiriqi, tvDaikuan_jine, tvDaikuan_leixing, 
	tvDaikuan_fenlei, tvDaikuan_yue, tvDaikuan_jingli;

	//贷款管户人
	private LinearLayout llDaikuanUserAdd;


	//手机银行信息
	private LinearLayout llShoujiRoot;
	private TextView tvShouji_kaihujigou, tvShouji_kaihuriqi,
	tvShouji_leixing, tvShouji_zhuxiaoriqi, tvShouji_tiepianriqi,
	tvShouji_tuozhanren, tvShouji_bili;

	//手机银行管户人
	private LinearLayout llShoujiUserAdd;


	//ETC信息
	private LinearLayout llEtcRoot;
	private TextView tvEtc_kaihujigou, tvEtc_bangdingriqi, 
	tvEtc_leixing, tvEtc_jine, tvEtc_kahao, 
	tvEtc_tuozhanren, tvEtc_bili, tvEtc_anzhuangren, tvEtc_anzhuangbili;


	private Customer mCustomer;

	private CustomerDetail mCustomerDetail;

	/**
	 * 记录标题位置
	 */
	private int currentPosition;
	private int oldPosition;

	/**
	 * 
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub

		RelatedPersonMap.put("401", "父母与子女");
		RelatedPersonMap.put("002", "配偶");
		RelatedPersonMap.put("403", "兄弟姐妹");
		RelatedPersonMap.put("X", "其他");

		currentPosition = oldPosition = BASE;

		mCustomer = (Customer) getIntent().getSerializableExtra("Extra_customer");

	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_customer_info); 

		/**
		 * 标题栏中间标题
		 */
		TextView tv = (TextView) findViewById(R.id.tv_common_back_title);

		if(mCustomer.getKhlx() == 2){
			tv.setText(CUSTOMER_TYPE[2]);
		}else{

			tv.setText(CUSTOMER_TYPE[1]);
			
//			tv.setText(mCustomer.getKhmc());
//
//			TextView tv1 = (TextView) findViewById(R.id.tv_common_back_title_sec);
//			tv1.setVisibility(View.VISIBLE);
//			tv1.setText("(" + CUSTOMER_TYPE[mCustomer.getKhlx()] + ")");
		}


		/**
		 * 标题栏左方返回
		 */
		llBack = (LinearLayout)findViewById(R.id.ll_common_back);
		llBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		/**
		 * 标题栏右方
		 */
		tvEdit = (TextView) findViewById(R.id.common_back_title_tv);

		//原来的跳转
		/*tvEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CustomerCheckActivity.this, VisitMainActivityOld.class);
				intent.putExtra("Extra_khbh", mCustomer.getKhbh());
				intent.putExtra("Extra_khmc", mCustomer.getKhmc());
				startActivity(intent);
//				//				finish();
//				Toast.makeText(CustomerCheckActivity.this, "拜访", Toast.LENGTH_SHORT).show();

			}
		});*/

		//修改之后的界面跳转
		tvEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CustomerCheckActivity.this, VisitMainActivity.class);
				intent.putExtra("Extra_khbh", mCustomer.getKhbh());
				intent.putExtra("Extra_khmc", mCustomer.getKhmc());
				startActivity(intent);
			}
		});


		progressBar = (ProgressBar) findViewById(R.id.customer_info_progressbar);

		/**
		 * 用户基本信息
		 */
		tvBumen = (TextView) findViewById(R.id.tv_customer_type_content);
		tvKehubianhao = (TextView) findViewById(R.id.tv_customer_id_content);
		tvKehumingchen= (TextView) findViewById(R.id.tv_customer_name_content);
		tvLianxidianhua = (TextView) findViewById(R.id.tv_customer_tele_content);
		tvChiyouchp = (TextView) findViewById(R.id.tv_customer_chiyou_chanpin_content);
		tvDizhi = (TextView) findViewById(R.id.tv_customer_address);
		tvKaihuriqi = (TextView) findViewById(R.id.tv_customer_kaihu_riqi_content);
		tvFarendaibiao = (TextView) findViewById(R.id.tv_customer_faren_daibiao_content);
		tvQiyeguimo = (TextView) findViewById(R.id.tv_customer_jinying_guimo_content);

		/**
		 * 关联人信息，多条
		 */
		llAddUser = (LinearLayout)findViewById(R.id.customer_relation_user_ll_add);

		/**
		 * 三部分基本跟布局
		 */
		llBaseInfo = (LinearLayout) findViewById(R.id.customer_info_base);
		llRelationUser = (LinearLayout) findViewById(R.id.customer_info_relation);
		llProduct = (LinearLayout) findViewById(R.id.customer_info_product);

		/**
		 * 顶部标题，实现点击定位到滑动的相应子控件
		 */
		scrollSlidingTabStrip = (ScrollSlidingTabStrip) findViewById(R.id.customer_info_tabs);
		scrollSlidingTabStrip.setTitle(new String[]{"基本信息","关联人信息","持有产品"});
//		scrollSlidingTabStrip.setAlpha(0);
		scrollSlidingTabStrip.setTabClickListener(new TabClickListener() {
			@Override
			public void onTabClick(int position) {
				// TODO Auto-generated method stub
				Log.d("tt","position:" + position);
				int offY = scrollSlidingTabStrip.getMeasuredHeight();
				switch(position){
				case 0:
					scrollView.scrollTo(0,0);
					break;
				case 1:
					scrollView.scrollTo(0, llRelationUser.getTop() - offY);
					break;
				case 2:
					scrollView.scrollTo(0, llProduct.getTop() - offY);
					break;
				}
			}

		});

		//默认不显示标签栏
		scrollerLayout = (ScrollerLayout) findViewById(R.id.customer_info_scrollerlayout);
//		scrollerLayout.scrollBy(0, -scrollSlidingTabStrip.getHeight());
		
		/**
		 * 整个界面滑动，添加滑动监听，根据子控件位置显示相应选择标题
		 */
		scrollView = (FadingScrollView) findViewById(R.id.customer_info_sv);
		scrollView.setFadingView(scrollerLayout);
//		scrollView.setFadingHeightView(llBaseInfo);
		scrollView.setScrollChangeChildListener(new ScrollChangeChildListener() {

			@Override
			public void onScorllChangeChild(int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub

				int offY = scrollSlidingTabStrip.getMeasuredHeight();

				if(t < llBaseInfo.getBottom() - offY){
					currentPosition = BASE;
				}else if(t < llRelationUser.getBottom() - offY){
					currentPosition = RELATION;
				}else{
					currentPosition = PRODUCT;
				}

				if(currentPosition != oldPosition){
					oldPosition = currentPosition;
					scrollSlidingTabStrip.changePostion(currentPosition);
				}
			}
		});

		tvCunkuanMore = (TextView) findViewById(R.id.customer_pruduct_cunkuan_more);
		tvCunkuanMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoProductMoreActivity(CustomerProductCunkuanActivity.class);
			}
		});

		tvDaikuanMore = (TextView) findViewById(R.id.customer_pruduct_daikuan_more);
		tvDaikuanMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoProductMoreActivity(CustomerProductDaikuanActivity.class);
			}
		});

		tvShoujiMore = (TextView) findViewById(R.id.customer_pruduct_shouji_more);
		tvShoujiMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoProductMoreActivity(CustomerProductShoujiActivity.class);
			}
		});

		tvEtcMore = (TextView) findViewById(R.id.customer_pruduct_etc_more);
		tvEtcMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoProductMoreActivity(CustomerProductEtcActivity.class);
			}
		});

		/**
		 * 产品信息
		 */

		//1-1、存款基本信息
		llCunkuanRoot = (LinearLayout) findViewById(R.id.customer_pruduct_cunkuan_root);

		tvCunkuan_mingchen = (TextView) findViewById(R.id.customer_product_cunkuan_mingchen);
		tvCunkuan_kaihujigou = (TextView) findViewById(R.id.customer_product_cunkuan_jigou);
		tvCunkuan_lilv = (TextView) findViewById(R.id.customer_product_cunkuan_lilv);
		tvCunkuan_kaihuriqi = (TextView) findViewById(R.id.customer_product_cunkuan_riqi);
		tvCunkuan_jine = (TextView) findViewById(R.id.customer_product_cunkuan_jine);
		tvCunkuan_leixing = (TextView) findViewById(R.id.customer_product_cunkuan_leixing);
		tvCunkuan_tuozhanren = (TextView) findViewById(R.id.customer_product_cunkuan_tuozhanren);
		tvCunkuan_bili = (TextView) findViewById(R.id.customer_product_cunkuan_bili);

		//1-2、存款管户人
		llCunkuanUserAdd = (LinearLayout) findViewById(R.id.customer_product_cunkuan_user_ll_add);

		//2-1、贷款信息
		llDaikuanRoot = (LinearLayout) findViewById(R.id.customer_pruduct_daikuan_root);

		tvDaikuan_mingchen = (TextView) findViewById(R.id.customer_product_daikuan_mingchen); 
		tvDaikuan_kaihujigou = (TextView) findViewById(R.id.customer_product_daikuan_jigou); 
		tvDaikuan_lilv = (TextView) findViewById(R.id.customer_product_daikuan_lilv);  
		tvDaikuan_fafangriqi = (TextView) findViewById(R.id.customer_product_daikuan_riqi);  
		tvDaikuan_daoqiriqi = (TextView) findViewById(R.id.customer_product_daikuan_daoqi_riqi);  
		tvDaikuan_jine = (TextView) findViewById(R.id.customer_product_daikuan_jine);  
		tvDaikuan_leixing = (TextView) findViewById(R.id.customer_product_daikuan_leixing);  
		tvDaikuan_fenlei = (TextView) findViewById(R.id.customer_product_daikuan_wuji);  
		tvDaikuan_yue = (TextView) findViewById(R.id.customer_product_daikuan_yue);  
		tvDaikuan_jingli = (TextView) findViewById(R.id.customer_product_daikuan_jingli); 

		//2-2、贷款管户人
		llDaikuanUserAdd = (LinearLayout) findViewById(R.id.customer_product_daikuan_user_ll_add);


		//3-1、手机银行信息
		llShoujiRoot = (LinearLayout) findViewById(R.id.customer_pruduct_shouji_root);

		tvShouji_kaihujigou = (TextView) findViewById(R.id.customer_product_shouji_jigou);
		tvShouji_kaihuriqi = (TextView) findViewById(R.id.customer_product_shouji_kaihu_riqi);
		tvShouji_leixing = (TextView) findViewById(R.id.customer_product_shouji_leixing); 
		tvShouji_zhuxiaoriqi = (TextView) findViewById(R.id.customer_product_shouji_zhuxiao_riqi); 
		tvShouji_tiepianriqi = (TextView) findViewById(R.id.customer_product_shouji_tiepian_riqi);
		tvShouji_tuozhanren = (TextView) findViewById(R.id.customer_product_shouji_tuozhanren); 
		tvShouji_bili = (TextView) findViewById(R.id.customer_product_shouji_bili);

		//3-2、手机银行管户人
		llShoujiUserAdd = (LinearLayout) findViewById(R.id.customer_product_shouji_user_ll_add);

		//4-1、ETC信息
		llEtcRoot = (LinearLayout) findViewById(R.id.customer_pruduct_etc_root);

		tvEtc_kaihujigou = (TextView) findViewById(R.id.customer_product_etc_jigou); 
		tvEtc_bangdingriqi = (TextView) findViewById(R.id.customer_product_etc_kaihu_riqi); 
		tvEtc_leixing = (TextView) findViewById(R.id.customer_product_etc_leixing); 
		tvEtc_jine = (TextView) findViewById(R.id.customer_product_etc_dianfu); 
		tvEtc_kahao = (TextView) findViewById(R.id.customer_product_etc_tiepian_kahao); 
		tvEtc_tuozhanren = (TextView) findViewById(R.id.customer_product_etc_tuozhanren); 
		tvEtc_bili = (TextView) findViewById(R.id.customer_product_etc_bili);
		tvEtc_anzhuangren = (TextView) findViewById(R.id.customer_product_etc_anzhuangren);
		tvEtc_anzhuangbili = (TextView) findViewById(R.id.customer_product_etc_anzhuang_bili);

	}


	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

		String url = MakeUrl.makeURL(new String[]{"responseCustomerDetailJson.action"});

		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  
				try{
					mCustomerDetail = JSON.parseObject(response, CustomerDetail.class);

				}catch(JSONException e){
					e.printStackTrace();
				}
				
//				Log.d("tt", mCustomerDetail.toString());

				scrollView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);

				tvEdit.setVisibility(View.VISIBLE);

				notifyView();
			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error);  
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数

				Map<String, String> params = new HashMap<String, String>();
				params.put("khbh", mCustomer.getKhbh());
				params.put("ghrgh", mCustomer.getGhrgh());

				return params;
			}
		}; 
		
		//2017-10-18 修改：设置网络请求时间
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				CommonContent.NETWORK_TIME_OUT_2, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}

	protected void notifyView(){

		if(mCustomerDetail == null) return;

		notifyViewBaseInfo();
		notifyViewRelatedPerson();
		notifyViewProduct();

	}

	/**
	 * 基本信息部分
	 */
	private void notifyViewBaseInfo(){
		mCustomer = mCustomerDetail.getCustomer();
		if(mCustomer != null){
			tvBumen.setText(mCustomer.getZzjc());
			tvKehubianhao.setText(mCustomer.getKhbh());
			tvKehumingchen.setText(mCustomer.getKhmc());
			tvLianxidianhua.setText(mCustomer.getSjhm());
			tvChiyouchp.setText(mCustomer.getCpxx());
			tvDizhi.setText(mCustomer.getZz());
			tvKaihuriqi.setText(mCustomer.getZjhm()); 
			//			tvFarendaibiao.setText(mCustomer.getf);
			//			tvQiyeguimo.setText(mCustomer.getGhrgh());
		}
	}

	/**
	 * 关联人部分
	 */
	private void notifyViewRelatedPerson(){

		for(int i = 0, size = mCustomerDetail.getRelatedPerson().size(); i < size; i++){
			//动态添加关联人信息
			View view = LayoutInflater.from(this).inflate(
					R.layout.item_customer_relation_user, null);

			RelatedPerson rp = mCustomerDetail.getRelatedPerson().get(i);

			String lx = RelatedPersonMap.get(rp.getGlrlx());

			((TextView)view.findViewById(R.id.item_customer_relation_tv_lxrlx)).setText(lx);
			((TextView)view.findViewById(R.id.item_customer_relation_tv_xm)).setText(rp.getGlrxm());
			((TextView)view.findViewById(R.id.item_customer_relation_tv_dh)).setText(rp.getLxfs());

			llAddUser.addView(view);
		}
		llAddUser.invalidate();
	}

	/**
	 * 持有产品部分
	 */
	private void notifyViewProduct(){

		notifyViewProductCunkuan();
		notifyViewProductDaikuan();
		notifyViewProductShouji();
		notifyViewProductEtc();

	}

	private void notifyViewProductCunkuan(){
		//更新存款产品数量
		//		int length = mCustomerDetail.getDepositAccountFamily().size();
		//		tvCunkuanMore.setText(MORE_LEFT + length + MORE_RIGHT);

		//更新存款产品信息
		DepositAccountFamily depositAccountFamily =  mCustomerDetail.getDepositAccountFamily();
		if(depositAccountFamily != null){
			llCunkuanRoot.setVisibility(View.VISIBLE);

			tvCunkuan_mingchen.setText(depositAccountFamily.getCkzh());
			tvCunkuan_kaihujigou.setText(depositAccountFamily.getZzjc());

			//			String str = depositAccountFamily.getLl() != null ? depositAccountFamily.getLl()+"":"";
			tvCunkuan_lilv.setText(BigDecimalUtil.formatBigDecimal(depositAccountFamily.getLl()));
			tvCunkuan_kaihuriqi.setText(depositAccountFamily.getKhrq()); 
			tvCunkuan_jine.setText(depositAccountFamily.getCkye() + "");
			try{
				tvCunkuan_leixing.setText(CommonContent.YINGXIAO_LEIXING[depositAccountFamily.getYxlx()]); 
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			tvCunkuan_tuozhanren.setText(depositAccountFamily.getTzr()); 
			tvCunkuan_bili.setText(depositAccountFamily.getTzbl());

			List<DepositAccountPerson> list = mCustomerDetail.getDepositAccountPerson();
			for(int i = list.size() - 1; i >= 0; i--){
				//动态添加管户人信息
				View view = LayoutInflater.from(this).inflate(
						R.layout.item_customer_product_cunkuan_user, null);

				DepositAccountPerson dap = list.get(i);
				((TextView)view.findViewById(R.id.item_customer_cunkuan_xingming)).setText(dap.getYgxm());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_bili)).setText(dap.getGhbl()+"");
				((TextView)view.findViewById(R.id.item_customer_cunkuan_kaishi)).setText(dap.getKsrq());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_jieshu)).setText(dap.getJsrq());

				llCunkuanUserAdd.addView(view);
			}
			llCunkuanUserAdd.invalidate();		//不加这一行，只有最后一次setText有效
		}
	}

	//	private void notifyViewProductDaikuan(){
	//		//更新存款产品数量
	//		int length = mCustomerDetail.getLoanCustomerProduct().size();
	//		tvDaikuanMore.setText(MORE_LEFT + length + MORE_RIGHT);
	//
	//		//更新存款产品信息
	//		if(length > 0){
	//			llDaikuanRoot.setVisibility(View.VISIBLE);
	//			LoanCustomerProduct lcp =  mCustomerDetail.getLoanCustomerProduct().get(0);
	//			if(lcp != null){
	//				tvDaikuan_mingchen.setText(lcp.getKhbh());
	//				tvDaikuan_kaihujigou.setText(lcp.getJgdm());
	//				tvDaikuan_lilv.setText(lcp.getLl()+"");
	//				tvDaikuan_fafangriqi.setText(lcp.getFfrq()); 
	//				tvDaikuan_jine.setText(lcp.getDkje() + ""); 
	//				tvDaikuan_leixing.setText(lcp.getYxbl()+""); 
	//				tvDaikuan_jingli.setText(lcp.getKhjlmc()); 
	//				tvDaikuan_fenlei.setText(lcp.getYxr());
	//			}
	//
	//			List<DepositAccountPerson> list = mCustomerDetail.getDepositAccountPerson();
	//			for(int i = list.size() - 1; i >= 0; i--){
	//				//动态添加管户人信息
	//				View view = LayoutInflater.from(this).inflate(
	//						R.layout.item_customer_product_daikuan_user, null);
	//
	//				DepositAccountPerson dap = list.get(i);
	//				((TextView)view.findViewById(R.id.item_customer_cunkuan_xingming)).setText(dap.getKhbh());
	//				((TextView)view.findViewById(R.id.item_customer_cunkuan_bili)).setText(dap.getGhbl()+"");
	//				((TextView)view.findViewById(R.id.item_customer_cunkuan_kaishi)).setText(dap.getKsrq());
	//				((TextView)view.findViewById(R.id.item_customer_cunkuan_jieshu)).setText(dap.getJsrq());
	//
	//				llDaikuanUserAdd.addView(view);
	//			}
	//			llDaikuanUserAdd.invalidate();		//不加这一行，只有最后一次setText有效
	//		}
	//	}


	private void notifyViewProductDaikuan(){
		//更新存款产品数量
		//		int length = mCustomerDetail.getLoanCustomerFamily().size();
		//		tvDaikuanMore.setText(MORE_LEFT + length + MORE_RIGHT);

		//更新存款产品信息
		LoanCustomerProduct lcp = mCustomerDetail.getLoanCustomerProduct();
		if(lcp != null){
			llDaikuanRoot.setVisibility(View.VISIBLE);

			tvDaikuan_mingchen.setText(lcp.getCpmc());
			tvDaikuan_kaihujigou.setText(lcp.getZzjc());
			//			String str = lcp.getLl() != null ? lcp.getLl()+"":"";
			tvDaikuan_lilv.setText(BigDecimalUtil.formatBigDecimal(lcp.getLl()));
			tvDaikuan_fafangriqi.setText(lcp.getFfrq()); 
			tvDaikuan_daoqiriqi.setText(lcp.getDqrq());
			tvDaikuan_jine.setText(lcp.getDkje() + ""); 

			String bl = lcp.getYxbl()!=null ? lcp.getYxbl()+"": "";
			tvDaikuan_leixing.setText(bl); 
			tvDaikuan_yue.setText(lcp.getDkye()+"");
			tvDaikuan_jingli.setText(lcp.getKhjlmc()); 
			tvDaikuan_fenlei.setText(CommonContent.WUJI_FENLEI[lcp.getFive_class_type()]);

			List<LoanCustomerPerson> list = mCustomerDetail.getLoanCustomerPerson();
			for(int i = list.size() - 1; i >= 0; i--){
				//动态添加管户人信息
				View view = LayoutInflater.from(this).inflate(
						R.layout.item_customer_product_daikuan_user, null);

				LoanCustomerPerson dap = list.get(i);
				((TextView)view.findViewById(R.id.item_customer_cunkuan_leixing)).setText(CustomerProductDaikuanPersonAdapter.GUANHU_LEIXING[dap.getGhlx()]);
				((TextView)view.findViewById(R.id.item_customer_cunkuan_xingming)).setText(dap.getYgxm());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_bili)).setText(dap.getGhbl()+"");
				((TextView)view.findViewById(R.id.item_customer_cunkuan_kaishi)).setText(dap.getKsrq());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_jieshu)).setText(dap.getJsrq());

				llDaikuanUserAdd.addView(view);
			}
			llDaikuanUserAdd.invalidate();		//不加这一行，只有最后一次setText有效
		}
	}

	private void notifyViewProductShouji(){
		//更新手机银行产品数量
		//		int length = mCustomerDetail.getCellBankCustomerProduct().size();
		//		tvShoujiMore.setText(MORE_LEFT + length + MORE_RIGHT);

		//更新手机银行产品信息
		CellBankCustomerProduct cbcp =  mCustomerDetail.getCellBankCustomerProduct();
		if(cbcp != null){
			llShoujiRoot.setVisibility(View.VISIBLE);

			tvShouji_kaihujigou.setText(cbcp.getZzjc());
			tvShouji_kaihuriqi.setText(cbcp.getKhrq());
			try{
				tvShouji_leixing.setText(CommonContent.YINGXIAO_LEIXING[cbcp.getYxlx()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			tvShouji_zhuxiaoriqi.setText(cbcp.getZxrq());
			tvShouji_tiepianriqi.setText(cbcp.getTprq());
			tvShouji_tuozhanren.setText(cbcp.getTzr()); 

			String bl = cbcp.getTzbl()!=null ? cbcp.getTzbl()+"": "";
			tvShouji_bili.setText(bl);

			List<CellBankCustomerPerson> list = mCustomerDetail.getCellBankCustomerPerson();
			for(int i = list.size() - 1; i >= 0; i--){
				//动态添加管户人信息
				View view = LayoutInflater.from(this).inflate(
						R.layout.item_customer_product_shouji_user, null);

				CellBankCustomerPerson ccp = list.get(i);
				((TextView)view.findViewById(R.id.item_customer_cunkuan_xingming)).setText(ccp.getYgxm());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_bili)).setText(ccp.getGhbl()+"");
				((TextView)view.findViewById(R.id.item_customer_cunkuan_kaishi)).setText(ccp.getKsrq());
				((TextView)view.findViewById(R.id.item_customer_cunkuan_jieshu)).setText(ccp.getJsrq());

				llShoujiUserAdd.addView(view);
			}
			llShoujiUserAdd.invalidate();
		}
	}

	private void notifyViewProductEtc(){
		//更新ETC产品数量
		//		int length = mCustomerDetail.getEtcCustomerFamily().size();
		//		tvEtcMore.setText(MORE_LEFT + length + MORE_RIGHT);

		//更新ETC产品信息
		EtcCustomerFamily ecf =  mCustomerDetail.getEtcCustomerFamily();
		if(ecf != null){
			llEtcRoot.setVisibility(View.VISIBLE);

			tvEtc_kaihujigou.setText(ecf.getZzjc());
			tvEtc_bangdingriqi.setText(ecf.getRq());
			try{
				tvEtc_leixing.setText(CommonContent.YINGXIAO_LEIXING[ecf.getYxlx()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			tvEtc_jine.setText(ecf.getDfje()+""); 
			tvEtc_kahao.setText(ecf.getXtkkh()); 
			tvEtc_tuozhanren.setText(ecf.getTzr()); 

			String bl = ecf.getTzbl()!=null ? ecf.getTzbl()+"": "";
			tvEtc_bili.setText(bl); 
			tvEtc_anzhuangren.setText(ecf.getAzr()); 
			tvEtc_anzhuangbili.setText(ecf.getAzbl()+"");
		}
	}

	private void gotoProductMoreActivity(@SuppressWarnings("rawtypes") Class cls){
		Intent intent = new Intent(CustomerCheckActivity.this, cls);
		intent.putExtra("Extra_khbh", mCustomerDetail.getCustomer().getKhbh());
		intent.putExtra("Extra_jgdm", mCustomerDetail.getCustomer().getJgdm());
		startActivity(intent);
	}

}
