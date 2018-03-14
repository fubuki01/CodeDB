package com.hnran.perfmanagesys.fragment.performance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.performance.PerformanceCbSjyhdhlActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceCunkuanCkAljcgzMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceCunkuanCommonActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceCunkuanYzkhgzMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDKDydklxshMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDKWddkhsMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDaikuanBiaowaiActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDaikuanMonilirunActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDaikuanYingxiaoActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziBmkkhhsMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziEtcActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziGerenActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziHushuActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziJiaoyiActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziJigouActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDianziJigouPingJunActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDiqubuchaActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDkAljcgzMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDkDndqdkshlActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDkKhhsMxActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceDkXzbnbldkzbActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceGljxBmkhActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceGljxWdrjgzActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceGuanliActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceGzzlActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceJgaljcActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceJymbActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceXsxsgzActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceYewuActivity;
import com.hnran.perfmanagesys.activity.performance.PerformanceYwlWdywlActivity;
import com.hnran.perfmanagesys.adapter.PerfMainContentAdapter;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.PerformanceMx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * 绩效工资（按天）指标ID明细界面，查询得到每个指标类别下的指标ID列表
 * @author Tower
 *
 */

@SuppressLint("ValidFragment")
public class PerfCommonFragment extends Fragment{

	private final static int FINISHED = 300001;
	private final static int ERROR = 300002;
	private final static int ADD = 300003;

	public final static int ZBLB_CUNKUAN = 1;
	public final static int ZBLB_DAIKUAN = 2;
	public final static int ZBLB_DIANZI = 3;
	public final static int ZBLB_YEWU = 4;
	public final static int ZBLB_GUANLI = 5;
	public final static int ZBLB_DIQU = 6;
	public final static int ZBLB_QITA = 7;
	public final static int ZBLB_JINGYING = 8;
	public final static int ZBLB_YANQI = 9;

	private LinearLayout llList;
	private ProgressBar progressBar;

	private ListView listview;

	private PerfMainContentAdapter mAdapter;
	protected List<PerformanceMx> mList;

	private TextView tvTotalWages;

	private String mDate;
	protected int zblb;  //指标类别
	
	//区分地区
	private String area;

	private String total;

	public PerfCommonFragment(String date) {
		// TODO Auto-generated constructor stub
		this.mDate = date;
	}

	public PerfCommonFragment(String date, int zblb) {
		// TODO Auto-generated constructor stub
		this.mDate = date;
		this.zblb = zblb;
	}
	
	public PerfCommonFragment(String date, int zblb, String area) {
		// TODO Auto-generated constructor stub
		this.mDate = date;
		this.zblb = zblb;
		this.area = area;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_perf_common, null);

		initVariables();
		initViews(view);
		loadData();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub

		mAdapter = new PerfMainContentAdapter(getActivity(), mList);	
	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub

		TextView date = (TextView) view.findViewById(R.id.perf_common_tv_date);
		date.setText(mDate);

		llList = (LinearLayout) view.findViewById(R.id.perf_common_ll_list);
//		llList.setVisibility(View.VISIBLE);
		progressBar = (ProgressBar) view.findViewById(R.id.perf_common_progressbar);

		tvTotalWages = (TextView) view.findViewById(R.id.perf_common_tv_total);

		listview = (ListView) view.findViewById(R.id.perf_common_lv);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				gotoPerfCommonActivity(position);
			}});

	}


	protected void loadData() {
		// TODO Auto-generated method stub

		String url = MakeUrl.makeURL(new String[]{"responsePerformanceMxJson.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@SuppressWarnings("unchecked")
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", "response：" + response);  

				try{
					mList = (List<PerformanceMx>)JSON.parseArray(response, PerformanceMx.class);
//					@SuppressWarnings("unchecked")
//					HashMap<String,Object> map = (HashMap<String,Object>)JSON.parse(response);
//					if(map != null){
//						if(map.containsKey("performances")){
//							mList = (ArrayList<PerformanceMx>)map.get("performances");
//						}
//
//						if(map.containsKey("area")){
//							mArea = (Area)map.get("area");
//						}
//					}

				}catch(JSONException e){
					e.printStackTrace();
				}

				if(mList != null) handler.sendEmptyMessage(FINISHED);
				else handler.sendEmptyMessage(ERROR);

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 
				handler.sendEmptyMessage(ERROR);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();

				map.put("yggh", PMSApplication.gUser.getTellId());
				map.put("gzrq", mDate);
				map.put("zblb", zblb+"");

				return map;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}

	public void notifyView(){

		mAdapter.setData(mList);
		mAdapter.notifyDataSetChanged();

		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BigDecimal b = new BigDecimal("0");
				for(PerformanceMx list : mList){
					b = b.add(list.getZbgz());
				}
				total = b + "";
				handler.sendEmptyMessage(ADD);
			}
		}).start();
	}


	/**
	 * 分类跳到不同页面，传入不同参数
	 * @param position
	 */
	public void gotoPerfCommonActivity(int position){

		Intent intent = null;// = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);

		switch(zblb){
		case ZBLB_CUNKUAN:			//存款大类	
			switch(mList .get(position).getZbid()){
			case "D90001":			//存量存款自然增长模拟利润
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 2);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNLIAN_MONI);
				break;
			case "D90002":			//存量存款主动揽储模拟利润
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 1);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNLIAN_MONI);
				break;
			case "D90005":			//增量存款自然增长模拟利润
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 2);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.ZENGLIAN_MONI);
				break;
			case "D90006":			//增量存款主动揽储模拟利润
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 1);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.ZENGLIAN_MONI);
				break;
			case "D90007":			//存量存款户数
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 1);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNKUAN_HUSHU);
				break;
			case "D90009":			//增量存款户
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_lx", 2);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNKUAN_HUSHU);
				break;
			case "D90038":			//存量存款自然增长模拟利润(综合户)
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_zzbz", mList.get(position).getZzbz());
				intent.putExtra("Extra_lx", 1);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNLIANG_ZONGHE);
				break;
			case "D90039":			//增量存款自然增长模拟利润(综合户)
				intent = new Intent(getActivity(), PerformanceCunkuanCommonActivity.class);
				intent.putExtra("Extra_zzbz", mList.get(position).getZzbz());
				intent.putExtra("Extra_lx", 2);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.ZENGLIANG_ZONGHE);
				break;
				
			/**
			 * 2017-09-01 新增部分	
			 */
			case "D90043":			//存量存款月日平余额业绩
				intent = new Intent(getActivity(), PerformanceCunkuanCkAljcgzMxActivity.class);
				intent.putExtra("Extra_lx", 1);
				break;
			case "D90044":			//增量存款月日平余额业绩
				intent = new Intent(getActivity(), PerformanceCunkuanCkAljcgzMxActivity.class);
				intent.putExtra("Extra_lx", 2);
				break;
				
			case "D90056":			//存款新增优质客户
				intent = new Intent(getActivity(), PerformanceCunkuanYzkhgzMxActivity.class);
				break;
			
			case "D90063":			//存量存款月日平余额业绩(综合户)
				intent = new Intent(getActivity(), PerformanceCunkuanCkAljcgzMxActivity.class);
				intent.putExtra("Extra_lx", 1);
				//综合
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.CUNLIANG_ZONGHE);
				intent.putExtra("Extra_zzbz", mList.get(position).getZzbz());
				break;
			case "D90064":			//增量存款月日平余额业绩(综合户)
				intent = new Intent(getActivity(), PerformanceCunkuanCkAljcgzMxActivity.class);
				intent.putExtra("Extra_lx", 2);
				intent.putExtra("Extra_type", PerformanceCunkuanCommonActivity.ZENGLIANG_ZONGHE);
				intent.putExtra("Extra_zzbz", mList.get(position).getZzbz());
				break;
				
			case "D90045":			//网点存款月末余额业绩
				intent = new Intent(getActivity(), PerformanceJgaljcActivity.class);
				break;
				
			default :
				showDefault();
				break;

			}
			break;

		case ZBLB_DAIKUAN:			//贷款款大类

			switch(mList .get(position).getZbid()){
			case "D90010":			//营销贷款笔数
				intent = new Intent(getActivity(), PerformanceDaikuanYingxiaoActivity.class);
				intent.putExtra("Extra_type", PerformanceDaikuanYingxiaoActivity.BISHU);
				break;
			case "D90011":			//营销贷款额度
				intent = new Intent(getActivity(), PerformanceDaikuanYingxiaoActivity.class);
				intent.putExtra("Extra_type", PerformanceDaikuanYingxiaoActivity.EDU);
				break;
			case "D90012":			//存量贷款模拟利润
				intent = new Intent(getActivity(), PerformanceDaikuanMonilirunActivity.class);
				intent.putExtra("Extra_type", PerformanceDaikuanMonilirunActivity.CUNLIANG);
				break;
			case "D90014":			//增量贷款模拟利润
				intent = new Intent(getActivity(), PerformanceDaikuanMonilirunActivity.class);
				intent.putExtra("Extra_type", PerformanceDaikuanMonilirunActivity.ZENGLIANG);
				break;
			case "D90037":			//表外贷款清收
				intent = new Intent(getActivity(), PerformanceDaikuanBiaowaiActivity.class);
				break;
			/**
			 * 	2017-09-01
			 */
			case "D90046":			//存量贷款户数
				intent = new Intent(getActivity(), PerformanceDkKhhsMxActivity.class);
				intent.putExtra("Extra_lx", 1);
				break;
			case "D90047":			//增量贷款户数
				intent = new Intent(getActivity(), PerformanceDkKhhsMxActivity.class);
				intent.putExtra("Extra_lx", 2);
				break;
			case "D90048":			//贷款月末余额
				intent = new Intent(getActivity(), PerformanceDkAljcgzMxActivity.class);
				break;
				
			case "D90050":			//新增表内不良贷款占比(比上月)
				intent = new Intent(getActivity(), PerformanceDkXzbnbldkzbActivity.class);
				break;
			case "D90051":			//当年到期贷款收回率
				intent = new Intent(getActivity(), PerformanceDkDndqdkshlActivity.class);
				break;
				
			case "D90052":			//当月贷款利息收回(多条)
				intent = new Intent(getActivity(), PerformanceDKDydklxshMxActivity.class);
				break;
				
			case "D90053":			//网点贷款户数
				intent = new Intent(getActivity(), PerformanceDKWddkhsMxActivity.class);
				intent.putExtra("Extra_zzbz", mList .get(position).getZzbz());
				break;
				
			case "D90054":			//网点贷款月末余额
			case "D90062":			//网点贷款利息收入
				intent = new Intent(getActivity(), PerformanceJgaljcActivity.class);
				break;
				
			case "D90049":	//便民卡户数
				intent = new Intent(getActivity(), PerformanceDianziBmkkhhsMxActivity.class);
				break;
				
			default :
				showDefault();
				break;
			}

			break;
		case ZBLB_DIANZI:
			switch(mList .get(position).getZbid()){
			case "D90015":	//手机银行主动营销户数
				intent = new Intent(getActivity(), PerformanceDianziHushuActivity.class);
				intent.putExtra("Extra_lx", PerformanceDianziHushuActivity.ZHUDONG);
				break;
			case "D90016":	//手机银行自然增长户数
				intent = new Intent(getActivity(), PerformanceDianziHushuActivity.class);
				intent.putExtra("Extra_lx", PerformanceDianziHushuActivity.ZIRAN);
				break;
				
			case "D90017":	//手机银行交易笔数
				intent = new Intent(getActivity(), PerformanceDianziJiaoyiActivity.class);
				break;
				
			case "D90019":	//社保卡激活户数
				intent = new Intent(getActivity(), PerformanceDianziJigouPingJunActivity.class);
				break;
				
			case "D90020":	//ETC营销户数
				if("会同".equals(area)){
					intent = new Intent(getActivity(), PerformanceDianziGerenActivity.class);
				}else{
					intent = new Intent(getActivity(), PerformanceDianziEtcActivity.class);
					intent.putExtra("Extra_lx", 1);
				}
				break;
				
			case "D90021":	//ETC安装户数
				intent = new Intent(getActivity(), PerformanceDianziEtcActivity.class);
				intent.putExtra("Extra_lx", 6);
				break;
				
			case "D90022":	//个人网银户数
			case "D90023":	//企业网银户数
			case "D90024":	//POS机营销户数
			case "D90025":	//POS机安装户数
			case "D90026":	//POS机刷卡笔数
				intent = new Intent(getActivity(), PerformanceDianziGerenActivity.class);
				break;
					
			case "D90035":	//支行社保卡激活率
			case "D90036":	//支行电子银行替代率
				//2017-10-17 修改：地区为“会同”
				if("会同".equals(area)){
					intent = new Intent(getActivity(), PerformanceCbSjyhdhlActivity.class);
				}else{
					intent = new Intent(getActivity(), PerformanceDianziJigouActivity.class);
				}
				
				break;
				
			//
			case "D90018":	//手机银行动户率
				intent = new Intent(getActivity(), PerformanceCbSjyhdhlActivity.class);
				break;
				
			case "D90042":	//电子银行业务
			case "D90065":	//手机银行户数
			case "D90066":	//网上银行户数
			case "D90067":	//贷记卡户数
			case "D90074":	//便民卡
				intent = new Intent(getActivity(), PerformanceDianziGerenActivity.class);
				break;
				
			default :
				showDefault();
				break;
				
			}
			break;
		case ZBLB_YEWU:
			switch(mList .get(position).getZbid()){
			case "D90031":			//本人经办业务量
				intent = new Intent(getActivity(), PerformanceYewuActivity.class);
				intent.putExtra("Extra_type", PerformanceYewuActivity.YEWULIANG);
				break;
			case "D90040":			//本人经办现金流量
				intent = new Intent(getActivity(), PerformanceYewuActivity.class);
				intent.putExtra("Extra_type", PerformanceYewuActivity.XIANJIN);
				break;
				
			//
			case "D90032":			//支行总业务量
			case "D90041":			//网点现金流量
				intent = new Intent(getActivity(), PerformanceYwlWdywlActivity.class);
				break;
				
			case "D90057":			//ATM机业务量
			case "D90078":			//调整业务量
				intent = new Intent(getActivity(), PerformanceDianziGerenActivity.class);
				break;
				
			default :
				showDefault();
				break;
			}
			break;
			
		case ZBLB_GUANLI:			//管理绩效
			switch(mList .get(position).getZbid()){
			case "D90033":	//管理绩效
				intent = new Intent(getActivity(), PerformanceGuanliActivity.class);
				break;
				
			case "D90058":	//本部室管理目标考核
			case "D90059":	//部门挂片考核
				intent = new Intent(getActivity(), PerformanceGljxBmkhActivity.class);
				break;
				
			case "D99998":	//网点人均工资
				intent = new Intent(getActivity(), PerformanceGljxWdrjgzActivity.class);
				break;
				
			default :
				showDefault();
				break;
			}
			break;
			
		case ZBLB_DIQU:				//地区补差
			switch(mList .get(position).getZbid()){
			case "D90034":	//地区补差
				intent = new Intent(getActivity(), PerformanceDiqubuchaActivity.class);
				break;
			
			default :
				showDefault();
				break;
			}
			break;
			
		case ZBLB_QITA:				//其它
			switch(mList .get(position).getZbid()){
			case "D90055":	//工作质量
				intent = new Intent(getActivity(), PerformanceGzzlActivity.class);
				break;
			case "D99999":	//享受系数工资
				intent = new Intent(getActivity(), PerformanceXsxsgzActivity.class);
				break;
			default :
				showDefault();
				break;
			}	
			break;
			
		case ZBLB_JINGYING:				//经营目标
			switch(mList .get(position).getZbid()){
			case "D90060":	//机构经营目标完成情况计酬
				intent = new Intent(getActivity(), PerformanceJymbActivity.class);
				break;
			
			default :
				showDefault();
				break;
			}	
			break;
			
		case ZBLB_YANQI:				//延期兑换
			break;
		default:
			break;

		}

		if(intent != null){
			intent.putExtra("Extra_title", mList.get(position).getZbmc());
			intent.putExtra("Extra_zbid", mList .get(position).getZbid());
			intent.putExtra("Extra_date", mDate);
			intent.putExtra("Extra_total", mList.get(position).getZbgz()+"");
			startActivity(intent);
		}
		
	}

	private Handler handler = new Handler(){
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){

			case FINISHED:

				progressBar.setVisibility(View.GONE);
				llList.setVisibility(View.VISIBLE);

				notifyView();

				break;

			case ERROR:

				progressBar.setVisibility(View.GONE);

				break;
			
			case ADD:
				tvTotalWages.setText(total);
				break;
			}
		};		
	};

	private void showDefault(){
		ToastUtil.showToast(getActivity(), "没有当前指标的明细信息");
	}
}
