package com.hnran.perfmanagesys.Statistics.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;
import com.hnran.perfmanagesys.Statistics.presenter.IsalarySeekPresenter;
import com.hnran.perfmanagesys.Statistics.presenter.SalarySeekPresenter;
import com.hnran.perfmanagesys.adapter.ExpandableAdapter;
import com.hnran.perfmanagesys.utils.DatePickerUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;

public class SalarySeekActivity extends Activity implements IsalarySeekView {

    private IsalarySeekPresenter salarySeekPresenter;
    private Unbinder unbinder;

    @BindView(R.id.tv_date) TextView tv_date;
    @BindView(R.id.tv_bankname) TextView tv_bankname;
    @BindView(R.id.et_staffNo) EditText et_staffNo;
    @BindView(R.id.et_staffName) EditText et_staffName;
    @BindView(R.id.lv_staff_info) ListView lv_staff_info;
    @BindView(R.id.btn_search) ImageView btn_search;

    private ExpandableAdapter<StaffSalaryInfo> mAdapter;
    private AlertDialog banklistDialog;
    private Toast toast;
    private View loadmoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(this.getResources().getColor(R.color.bg_title));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_statistics_salary_seek);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy(){
        if(salarySeekPresenter!=null)
            salarySeekPresenter.onDestroy();
        if(banklistDialog!=null)
            banklistDialog.dismiss();
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_back)
    void onBackClick(){
        this.finish();
    }

    @OnClick(R.id.btn_search)
    void onSearchClick(){
        salarySeekPresenter.loadAdapterData();
    }

    @OnClick(R.id.tv_date)
    void onDateClick(){
        DatePickerUtil.showYearMonthDayPicker(this, tv_date, false, null);
    }

    @OnClick(R.id.tv_bankname)
    void onBankNameClick(){
        if(banklistDialog==null){
            salarySeekPresenter.loadBankList();
            showToast("正在获取支行列表，请保持网络通畅。");
            return;
        }
        banklistDialog.show();
    }

    @OnItemClick(R.id.lv_staff_info)
    void onItemClick(AdapterView<?> parent, View view, int position, long id){
        StaffSalaryInfo info = mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(this, StaffSalaryInfoActivity.class);
        intent.putExtra("info", info);
        startActivity(intent);
    }

    private void init(){
        btn_search.setVisibility(View.VISIBLE);

        Calendar ca = Calendar.getInstance();
        final int year = ca.get(Calendar.YEAR);
        final int month = ca.get(Calendar.MONTH);
        final int day = ca.get(Calendar.DAY_OF_MONTH);
        tv_date.setText(year+"-"+(month+1<=9?"0"+(month+1):(month+1))+"-"+(day<=9?"0"+day:day));

        mAdapter = new ExpandableAdapter<StaffSalaryInfo>(new ArrayList<StaffSalaryInfo>()){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                if(convertView==null){
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(R.layout.item_statistics_staff_salary_info,null);
                }
                StaffSalaryInfo info = getItem(position);
                ((TextView)convertView.findViewById(R.id.tv_bankName)).setText(info.getZZMC());
                ((TextView)convertView.findViewById(R.id.tv_staffNo)).setText(info.getYGGH());
                ((TextView)convertView.findViewById(R.id.tv_staffName)).setText(info.getREALNAME());
                ((TextView)convertView.findViewById(R.id.tv_salary)).setText(info.getGZHJ()+"");
                ((TextView)convertView.findViewById(R.id.tv_subbranchRank)).setText(info.getZHGZPM()+"");
                ((TextView)convertView.findViewById(R.id.tv_headquartersRank)).setText(info.getQHGZPM()+"");
                return convertView;
            }
        };
        /*addHeaderView和addFooterView方法必须放在setadapter前面，否则会报错。
        原因是当我们在调用上述两方法时android会判断当前listview是否已经添加adapter，
        如果已经添加则带着我们设置的header和footer连同已经添加adapter生成一个
        HeaderViewListAdapter，这个HeaderViewListAdapter是一个WrapperListAdapter，
        它包裹了原来的那个adapter，如果想访问原来的那个adapter，可以通过listview的
        getView先获取HeaderViewListAdapter，再通过HeaderViewListAdapter的getWrappedAdapter
        去获得原先的adapter*/
        loadmoreView = LayoutInflater.from(this).inflate(R.layout.load_more, null);
        lv_staff_info.addFooterView(loadmoreView,null,false);
        lv_staff_info.setAdapter(mAdapter);
        lv_staff_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int last_index;
            private int total_index;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)){
                    loadmoreView.setVisibility(View.VISIBLE);
                    salarySeekPresenter.loadAdapterData(mAdapter.getCount(),10);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                last_index = firstVisibleItem+visibleItemCount;
                total_index = totalItemCount;
            }
        });
        salarySeekPresenter = new SalarySeekPresenter(this);
        salarySeekPresenter.loadBankList();
        salarySeekPresenter.loadAdapterData();
    }

    @Override
    public void showToast(String str) {
        if(toast==null){
            toast = Toast.makeText(this,str,Toast.LENGTH_SHORT);
        }
        else{
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public String getStaffName() {
        if(et_staffName==null)
            return null;
        return et_staffName.getText().toString().trim();
    }

    @Override
    public String getStaffNo() {
        if(et_staffNo==null)
            return null;
        return et_staffNo.getText().toString().trim();
    }

    @Override
    public String getBankName() {
        if(tv_bankname==null)
            return null;
        String banknamestr = tv_bankname.getText().toString().trim();
        return banknamestr.equals("全行")?"":banknamestr;
    }

    @Override
    public String getDate() {
        if(tv_date==null)
            return null;
        return tv_date.getText().toString().trim();
    }

    @Override
    public void addAdapterData(List<StaffSalaryInfo> staffSalaryInfos,int start) {
        if(start==0)
            mAdapter.clear();
        mAdapter.expand(staffSalaryInfos);
        mAdapter.notifyDataSetChanged();
        if(start==0)
            lv_staff_info.setSelection(0);
        loadmoreView.setVisibility(View.GONE);
    }

    @Override
    public void setBankList(List<String> bankList) {
        if(banklistDialog==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setAdapter(new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1,
                            bankList),null);
            banklistDialog = builder.create();
            banklistDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tv_bankname.setText(((TextView)view).getText());
                    banklistDialog.hide();
                }
            });
        }
        return;
    }
}
