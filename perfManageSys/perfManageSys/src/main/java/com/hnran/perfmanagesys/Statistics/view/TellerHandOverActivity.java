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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.Statistics.model.Bean.HandoverInfo;
import com.hnran.perfmanagesys.Statistics.presenter.ItellerHandoverPresenter;
import com.hnran.perfmanagesys.Statistics.presenter.TellerHandoverPresenter;
import com.hnran.perfmanagesys.adapter.ExpandableAdapter;
import com.hnran.perfmanagesys.utils.DatePickerUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TellerHandOverActivity extends Activity implements ItellerHandOverView{

    private ItellerHandoverPresenter tellerHandoverPresenter;
    private Unbinder unbinder;

    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_handover_position) TextView tvPosition;
    @BindView(R.id.lv_staff_info)ListView lv;

    private ExpandableAdapter<HandoverInfo> mAdapter;
    private Toast toast;
    private AlertDialog positionlistDialog;
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
        setContentView(R.layout.activity_teller_hand_over);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy(){
        if(tellerHandoverPresenter!=null)
            tellerHandoverPresenter.onDestroy();
        if(positionlistDialog!=null)
            positionlistDialog.dismiss();
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_back)
    void onBackClick(){
        this.finish();
    }

    @OnClick(R.id.btn_add)
    void onAddClick(){
        Intent intent = new Intent(TellerHandOverActivity.this,HandoverSaveActivity.class);
        this.startActivity(intent);
    }

    @OnClick(R.id.tv_date)
    void onDateClick(){
        DatePickerUtil.showYearMonthDayPicker(this, tvDate, false, tellerHandoverPresenter.getHandler());
    }

    @OnClick(R.id.tv_handover_position)
    void onBankNameClick(){
        if(positionlistDialog==null){
            tellerHandoverPresenter.loadPositionList();
            showToast("正在获取支行列表，请保持网络通畅。");
            return;
        }
        positionlistDialog.show();
    }

    private void init(){
//        Calendar ca = Calendar.getInstance();
//        final int year = ca.get(Calendar.YEAR);
//        final int month = ca.get(Calendar.MONTH);
//        final int day = ca.get(Calendar.DAY_OF_MONTH);
//        tvDate.setText(year+"-"+(month+1<=9?"0"+(month+1):(month+1))+"-"+(day<=9?"0"+day:day));

        mAdapter = new ExpandableAdapter<HandoverInfo>(new ArrayList<HandoverInfo>()){
            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                ViewHolder vh;
                if(convertView==null){
                    vh = new ViewHolder();
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(R.layout.item_statistics_teller_hand_over,null);
                    vh.date = (TextView)convertView.findViewById(R.id.tv_date);
                    vh.position = (TextView)convertView.findViewById(R.id.tv_handover_position);
                    vh.organization = (TextView)convertView.findViewById(R.id.tv_organization_name);
                    vh.staffNo = (TextView)convertView.findViewById(R.id.tv_staff_no);
                    vh.staffName = (TextView)convertView.findViewById(R.id.tv_staff_name);
                    convertView.setTag(vh);
                }
                else{
                    vh = (ViewHolder) convertView.getTag();
                }
                HandoverInfo info = getItem(position);
                vh.date.setText(info.getStaffJj().getJjrq().split(" ")[0]);
                vh.position.setText(info.getStaffJj().getJjgwmc());
                vh.organization.setText(info.getZzjc());
                vh.staffNo.setText(info.getStaffJj().getYggh());
                vh.staffName.setText(info.getYgxm());
                return convertView;
            }
            class ViewHolder{
                TextView date;
                TextView position;
                TextView organization;
                TextView staffNo;
                TextView staffName;
            }
        };

        loadmoreView = LayoutInflater.from(this).inflate(R.layout.load_more, null);
        lv.addFooterView(loadmoreView,null,false);
        lv.setAdapter(mAdapter);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int last_index;
            private int total_index;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)){
                    loadmoreView.setVisibility(View.VISIBLE);
                    tellerHandoverPresenter.loadAdapterData(mAdapter.getCount(),10);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                last_index = firstVisibleItem+visibleItemCount;
                total_index = totalItemCount;
            }
        });

        tellerHandoverPresenter = new TellerHandoverPresenter(this);
        tellerHandoverPresenter.loadPositionList();
        tellerHandoverPresenter.loadAdapterData();
    }

    @Override
    public void addAdapterData(List<HandoverInfo> handoverInfos,int start) {
        if(start==0)
            mAdapter.clear();
        mAdapter.expand(handoverInfos);
        mAdapter.notifyDataSetChanged();
        if(start==0)
            lv.setSelection(0);
        loadmoreView.setVisibility(View.GONE);
    }

    @Override
    public void setPositionList(List<String> PositionList) {
        if(positionlistDialog==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setAdapter(new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1,
                            PositionList),null);
            positionlistDialog = builder.create();
            positionlistDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    tvPosition.setText(((TextView)view).getText());
                    positionlistDialog.hide();
                    clearAdapterAndReload();
                }
            });
        }
        return;
    }

    @Override
    public void clearAdapterAndReload(){
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
        loadmoreView.setVisibility(View.VISIBLE);
        tellerHandoverPresenter.loadAdapterData();
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
    public String getPosition() {
        if(tvPosition==null)
            return null;
        String position = tvPosition.getText().toString().trim();
        return position.equals("无")?"":position;
    }
    @Override
    public String getDate() {
        if(tvDate==null)
            return null;
        return tvDate.getText().toString().trim();
    }
}
