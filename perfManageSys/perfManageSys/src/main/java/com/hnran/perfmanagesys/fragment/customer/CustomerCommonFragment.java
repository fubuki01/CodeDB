package com.hnran.perfmanagesys.fragment.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.LoginActivity;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.activity.ReLoginActivity;
import com.hnran.perfmanagesys.activity.customer.CustomerCheckActivity;
import com.hnran.perfmanagesys.adapter.CustomerAdapter;
import com.hnran.perfmanagesys.utils.DateUtil;
import com.hnran.perfmanagesys.utils.MakeUrl;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.utils.VolleyUtil;
import com.readboy.ssm.po.Customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomerCommonFragment extends Fragment{

	//	public static final String[] CUSTOMER_TYPE = {"", "个人客户","对公客户"};

	public final static int CUSTOMER_PORSONAL = 1;
	public final static int CUSTOMER_PUBLIC = 2;

	protected final int NOTIFY_LISTVIEW = 10002;

	protected final int REQUEST_ERROR = 10003;

	protected int mType;

	private PullToRefreshListView lvCustomer;

	private TextView tvText;

	private ProgressBar progressBar;

	private CustomerAdapter mCustomerAdapter;
	protected List<Customer> mCustomers;
	
	protected int currentResult = 0;
	
	private String condition = "";
	
	//显示条目总数
	private Integer mNum;
	private TextView tvNumberLable;

	public CustomerCommonFragment(){

	}

	@SuppressLint("ValidFragment")
	public CustomerCommonFragment(int type){
		this.mType = type;
	}

	@SuppressLint("HandlerLeak")
	protected Handler UIHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case NOTIFY_LISTVIEW:

				notifyListView();

				break;

			case REQUEST_ERROR:

				notifyListView();

				ToastUtil.showToast(getActivity(), CommonContent.ERROR_NETWORK);

				break;
			}

		};
	};


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_customer_common , null);

		initVariables();
		initViews(view);
		loadData();

		return view;

	}

	protected void initVariables() {
		// TODO Auto-generated method stub
		//		mCustomers = new ArrayList<Customer>();
		//		
		//		if(mType == CUSTOMER_PORSONAL){
		//			mCustomers.add(new Customer("70065", "刘夏","13198901872","存款、贷款"));
		//			mCustomers.add(new Customer("70010", "腾丫丫","15089721251","贷款"));
		//			mCustomers.add(new Customer("70109", "王小年","18998791982","手机银行"));
		//		}else if(mType == CUSTOMER_PUBLIC){
		//			mCustomers.add(new Customer("456", "李四","12312312312","存款客户"));
		//			mCustomers.add(new Customer("456", "李四","12312312312","存款客户"));
		//			mCustomers.add(new Customer("789", "李四","12312312312","存款客户"));
		//			mCustomers.add(new Customer("123", "李四","12312312312","存款客户"));
		//			mCustomers.add(new Customer("123", "李四","12312312312","存款客户"));
		//			mCustomers.add(new Customer("456", "李四","12312312312","存款客户"));
		//		}
		mCustomers = new ArrayList<Customer>();
		
		mCustomerAdapter = new CustomerAdapter(getActivity(), mCustomers);
	}


	protected void initViews(View view) {
		// TODO Auto-generated method stub	

		lvCustomer = (PullToRefreshListView) view.findViewById(R.id.customer_common_lv_customer);
		lvCustomer.setAdapter(mCustomerAdapter);
		lvCustomer.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//				Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
				gotoCustomerActivity(position - 1);

			}});

		lvCustomer.setMode(Mode.BOTH);		//下拉刷新，上拉加载
		lvCustomer.getLoadingLayoutProxy(true,false).setRefreshingLabel("正在刷新");
		lvCustomer.getLoadingLayoutProxy(true,false).setPullLabel("下拉刷新");
		lvCustomer.getLoadingLayoutProxy(true,false).setReleaseLabel("释放开始刷新");
		lvCustomer.getLoadingLayoutProxy(false,true).setRefreshingLabel("正在加载");
		lvCustomer.getLoadingLayoutProxy(false,true).setPullLabel("上拉加载更多");
		lvCustomer.getLoadingLayoutProxy(false,true).setReleaseLabel("释放开始加载");

		String label = "上次更新：" + DateUtil.getCurrentTime();  

		// Update the LastUpdatedLabel  
		lvCustomer.getLoadingLayoutProxy()  
		.setLastUpdatedLabel(label); 


		lvCustomer.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullDownToRefresh");

				String label = "上次更新：" + DateUtil.getCurrentTime();  

				// Update the LastUpdatedLabel  
				refreshView.getLoadingLayoutProxy()  
				.setLastUpdatedLabel(label); 

				clearCustomer();
				loadData();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.d("tt","onPullUpToRefresh");

				//				new GetDataTask().execute();

				loadData();

			}
		});

		progressBar = (ProgressBar) view.findViewById(R.id.customer_progressbar);

		tvText = (TextView) view.findViewById(R.id.customer_tv_text);
		
		tvNumberLable = (TextView) view.findViewById(R.id.performance_tv_lable);
	}

	public void loadDataByCondition(String condition){
		this.condition = condition;
		loadData();
	}
	
	public void loadData() {
		// TODO Auto-generated method stub

//		if(mCustomers != null)  mCustomers.clear();		//刷新才清空
		
//		progressBar.setVisibility(View.VISIBLE);
//		lvCustomer.setVisibility(View.GONE);
		tvText.setVisibility(View.GONE);
		tvNumberLable.setVisibility(View.GONE);

		String url = MakeUrl.makeURL(new String[]{"responseCustomerJson.action"});
		StringRequest stringRequest = new StringRequest(Method.POST, url,  
				new Response.Listener<String>() {  
			@Override  
			public void onResponse(String response) {  
				Log.d("TAG", response);  

				try{
					
					JSONObject jsonObj = JSON.parseObject(response);  
					JSONArray list = jsonObj.getJSONArray("list"); 
					
					mNum = jsonObj.getIntValue("num");

					List<Customer> tmp = JSON.parseArray(list.toString(), Customer.class);
					mCustomers.addAll(tmp);
					
				}catch(JSONException e){
					e.printStackTrace();
				}catch(Exception e){
					e.printStackTrace();
				}

				UIHandler.sendEmptyMessage(NOTIFY_LISTVIEW);

			}  
		}, new Response.ErrorListener() {  
			@Override  
			public void onErrorResponse(VolleyError error) {  
				Log.e("TAG", error.getMessage(), error); 

				UIHandler.sendEmptyMessage(REQUEST_ERROR);
			}  
		}){
			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> params = new HashMap<String, String>();
				
				//
				String gyh = "";
				try{
					gyh = PMSApplication.gUser.getTellId();
				}catch(Exception e){
					e.printStackTrace();
					//跳转到登录界面
					Intent intent = new Intent(CustomerCommonFragment.this.getActivity(), LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );
					startActivity(intent);
				}
				
				params.put("gyh", gyh);
				params.put("khlx", mType +"");
				params.put("condition", condition);
				int size = mCustomers != null ? mCustomers.size() : 0;
				params.put("currentResult", size+"");
				return params;
			}
		}; 

		VolleyUtil.getInstanceRequestQueue().add(stringRequest);

	}


	public void gotoCustomerActivity(int position){

		Intent intent = new Intent(getActivity(), CustomerCheckActivity.class);
		intent.putExtra("Extra_title", mCustomers.get(position).getKhmc());
		//		intent.putExtra("Extra_title_sec", "(" + CUSTOMER_TYPE[mType] + ")");


		intent.putExtra("Extra_customer", mCustomers.get(position));

		startActivity(intent);
	} 


	protected void notifyListView(){
		
		progressBar.setVisibility(View.GONE);

		lvCustomer.onRefreshComplete();
		
		if(mCustomers == null || mCustomers.size() == 0){
			tvText.setVisibility(View.VISIBLE);
			
		}else{
			mCustomerAdapter.setData(mCustomers);
			tvNumberLable.setVisibility(View.VISIBLE);
			tvNumberLable.setText(mNum+"");
		}
		mCustomerAdapter.notifyDataSetChanged();
	}
	
	public void clearCustomer(){
		if(mCustomers != null){
			mCustomers.clear();
			currentResult = 0;
			condition = "";
		}
	}

}
