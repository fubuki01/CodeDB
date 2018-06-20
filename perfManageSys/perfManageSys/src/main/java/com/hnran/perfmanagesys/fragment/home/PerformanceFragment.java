package com.hnran.perfmanagesys.fragment.home;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.PerformanceActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceGljxWdrjgzActivity;
import com.hnran.perfmanagesys.utils.DatePickerUtil;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.Area;
import com.readboy.ssm.po.Performance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 绩效工资（按天查询）首页， 共有三级界面，PerformanceFragment-> PerformanceActivity -> 根据指标ID具体明细
 * @author Tower
 *
 */
public class PerformanceFragment extends BaseFragment{

	private final static int FINISHED = 3001;
	private final static int ERROR = 3002;

	private final String ZONGHANG = "全行：";
	private final String QUHANG = "  支行：";

	private TextView tvText;

	private TextView tvDate, tvMonth;
	private TextView tvQhRank, tvZhRank, tvCunkuan, tvDaikuan, tvDianzi, tvYewu, tvGuanli, tvDiqu, tvQita, tvYx, tvYq;
	private LinearLayout llCunkuan, llDaikuan, llDianzi, llYewu, llGuanli, llDiqu, llQita, llYx, llYq;

	private LinearLayout llRoot;
	private ProgressBar progressBar;

	private Performance performance;

	private boolean isFirst = true;
	
	//2017-10-18 改：区分地区
	private Area mArea = new Area();

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.fragment_performance_day, null);

		initVariables();
		initViews(view, savedInstanceState);
		loadData();
		//		notifyView();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

	}


	protected void initViews(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
//		tv_title.setText(title);

		llRoot = (LinearLayout) view.findViewById(R.id.perf_root);
		llRoot.setVisibility(View.VISIBLE);

		tvText = (TextView) view.findViewById(R.id.perf_tv_text);

		progressBar = (ProgressBar) view.findViewById(R.id.perf_progressbar);

		tvDate = (TextView) view.findViewById(R.id.perf_tv_date);

		if(savedInstanceState == null )	{	//只有Activity第一次进来时才设置默认当前日期
			tvDate.setText(DateUtil.getCurrentTime("yyyy-MM-dd"));
		}
		else{
			tvDate.setText(savedInstanceState.getString("Extra_date"));
		}

		RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.perf_rl_date);
		rl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//	DatePickUtil.showDatePickDlg(getActivity(), tvDate, true, null);
				DatePickerUtil.showYearMonthDayPicker(getActivity(), tvDate, false, handler);

			}
		});

		tvMonth = (TextView) view.findViewById(R.id.perf_tv_month);

		llCunkuan = (LinearLayout) view.findViewById(R.id.perf_ll_cunkuan);
		llCunkuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(0);
			}
		});

		llDaikuan = (LinearLayout) view.findViewById(R.id.perf_ll_daikuan);
		llDaikuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(1);
			}
		});

		llDianzi = (LinearLayout) view.findViewById(R.id.perf_ll_dianzi);
		llDianzi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(2);
			}
		});

		llYewu = (LinearLayout) view.findViewById(R.id.perf_ll_yewu);
		llYewu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(3);
			}
		});

		llGuanli = (LinearLayout) view.findViewById(R.id.perf_ll_guanli);
		llGuanli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(4);
			}
		});

		llDiqu = (LinearLayout) view.findViewById(R.id.perf_ll_diqu);
		llDiqu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(5);
			}
		});

		llQita = (LinearLayout) view.findViewById(R.id.perf_ll_qita);
		llQita.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(6);
			}
		});

		llYx = (LinearLayout) view.findViewById(R.id.perf_ll_jingying);
		llYx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(7);
			}
		});

		llYq = (LinearLayout) view.findViewById(R.id.perf_ll_yanqi);
		llYq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoPerformanceActivity(8);
			}
		});


		tvQhRank  = (TextView) view.findViewById(R.id.perf_rank_fenhang);
		tvZhRank  = (TextView) view.findViewById(R.id.perf_rank_quanhang);
		tvCunkuan = (TextView) view.findViewById(R.id.perf_tv_cunkuan);
		tvDaikuan = (TextView) view.findViewById(R.id.perf_tv_daikuan);
		tvDianzi  = (TextView) view.findViewById(R.id.perf_tv_dianzi);
		tvYewu    = (TextView) view.findViewById(R.id.perf_tv_yewu);
		tvGuanli  = (TextView) view.findViewById(R.id.perf_tv_guanli);
		tvDiqu    = (TextView) view.findViewById(R.id.perf_tv_diqu);
		tvQita    = (TextView) view.findViewById(R.id.perf_tv_qita);
		tvYx    = (TextView) view.findViewById(R.id.perf_tv_jingying);
		tvYq    = (TextView) view.findViewById(R.id.perf_tv_yanqi);

		//		setRankText(ZONGHANG+112+QUHANG+22);
	}


	protected void loadData() {
		// TODO Auto-generated method stub

		performance = null;

		String url = MakeUrl.makeURL(new String[]{"responsePerformanceJson.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", "response：" + response);  
				System.out.println(response);
				try{
//					performance = JSON.parseObject(response, Performance.class);
					
					JSONObject jsonObj = JSON.parseObject(response);  
					JSONObject p = jsonObj.getJSONObject("performance"); 
					JSONObject a = jsonObj.getJSONObject("area");
					performance = JSON.parseObject(p.toString(), Performance.class);
					mArea = JSON.parseObject(a.toString(), Area.class);
					
//					HashMap<String,Object> map = (HashMap<String,Object>)JSON.parseObject(response, HashMap.class);
//					Map map = (Map) JSONObject.pa;
//					if(map != null){
//						if(map.containsKey("performance")){
//							performance = (Performance)map.get("performance");
//						}
//
//						if(map.containsKey("area")){
//							mArea = (Area)map.get("area");
//						}
//					}

				}catch(JSONException e){
					e.printStackTrace();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				handler.sendEmptyMessage(FINISHED);

				//				if(performance == null) 
				//				else{
				//					handler.sendEmptyMessage(ERROR);
				////					if(!isFirst) ToastUtil.showToast(getActivity(), "没有数据");
				//				}

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 
				handler.sendEmptyMessage(ERROR);
				ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();

				map.put("yggh", PMSApplication.gUser.getTellId());
				map.put("gzrq", tvDate.getText().toString().trim());
				//				map.put("gzrq", "2017-06-01");

				return map;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);
	}

	public void notifyView(){

		//		String strMonth =  + "元";
		//		int lengthMonth = strMonth.length();
		//		SpannableString month = new SpannableString(strMonth);
		//		month.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, lengthMonth-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		//		month.setSpan(new RelativeSizeSpan(0.7f), lengthMonth - 1, lengthMonth, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		if(performance != null){

			llRoot.setVisibility(View.VISIBLE);

			setRankText(ZONGHANG+performance.getQhgzpm()+QUHANG+performance.getZhgzpm());

			tvMonth.setText(performance.getGzhj() + "");
			//			tvQhRank.setText(performance.getZhgzpm() + "");
			//			tvZhRank.setText(performance.getQhgzpm() + "");
			tvCunkuan.setText(performance.getCkgz() + "");
			tvDaikuan.setText(performance.getDkgz() + "");
			tvDianzi.setText(performance.getDzyhgz() + "");
			tvYewu.setText(performance.getYwlgz() + "");
			tvGuanli.setText(performance.getGljxgz() + "");
			tvDiqu.setText(performance.getDqbcgz() + "");
			tvQita.setText(performance.getQt() + "");
			tvYx.setText(performance.getJymbgz() + "");
			tvYq.setText(performance.getYqdfgz() + "");

		}else{
			//			String str = "0";
			//			tvMonth.setText(str);
			//			tvQhRank.setText( str);
			//			tvZhRank.setText( str);
			//			tvCunkuan.setText(str);
			//			tvDaikuan.setText(str);
			//			tvDianzi.setText(str);
			//			tvYewu.setText(str);
			//			tvGuanli.setText(str);
			//			tvDiqu.setText(str);


			tvText.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 跳转到绩效工资（按天）二级界面
	 * @param position
	 */

	public void gotoPerformanceActivity(int position){

		Intent intent = new Intent(getActivity(), PerformanceActivity.class);
		intent.putExtra("Extra_position", position);
		intent.putExtra("Extra_date", tvDate.getText().toString().trim());
		//		intent.putExtra("Extra_date", "2017-06-01");
		intent.putExtra("Extra_area", mArea == null ? "" : mArea.getBz());
		startActivity(intent);

	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case DatePickerUtil.CHANGE_YEAR_MONTH_DAY:

				progressBar.setVisibility(View.VISIBLE);
				llRoot.setVisibility(View.GONE);
				tvText.setVisibility(View.GONE);

				isFirst = false;
				loadData();

				//				test();

				break;

			case FINISHED:

				progressBar.setVisibility(View.GONE);


				notifyView();

				break;

			case ERROR:

				progressBar.setVisibility(View.GONE);
				tvText.setVisibility(View.VISIBLE);

				break;
			}

		};		
	};


	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//保存已选择的时间
		outState.putString("Extra_date", tvDate.getText().toString().trim());
	};

	/**
	 * 设置全行排名，支行排名字体
	 * @param str
	 */
	private void setRankText(String str){

		//缩小全行： 支行： 字体
		SpannableStringBuilder style = new SpannableStringBuilder(str); 

		int i1 = str.indexOf("：");
		int i2 = str.lastIndexOf("：")+1;

		// 
		style.setSpan(new RelativeSizeSpan(0.6f),0,i1+1,Spannable.SPAN_INCLUSIVE_INCLUSIVE); 
		style.setSpan(new RelativeSizeSpan(0.6f),i2 - QUHANG.length(),i2,Spannable.SPAN_INCLUSIVE_INCLUSIVE); 

		tvZhRank.setText(style);
	}


	private void test(){
		String date = tvDate.getText().toString().trim();
		Intent intent = new Intent(getActivity(), PerformanceGljxWdrjgzActivity.class);
		intent.putExtra("Extra_date", date);
		intent.putExtra("Extra_title", "测试");
		intent.putExtra("Extra_zbid", "D99998");
		intent.putExtra("Extra_zbbz", "D90051");
		startActivity(intent);
	}
	
}
