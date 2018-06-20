package com.hnran.perfmanagesys.Statistics.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.Statistics.presenter.HandoverSavePresenter;
import com.hnran.perfmanagesys.Statistics.presenter.IhandoverSavePresenter;
import com.hnran.perfmanagesys.activity.PMSApplication;
import com.hnran.perfmanagesys.utils.DatePickerUtil;
import com.readboy.ssm.po.User;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HandoverSaveActivity extends Activity implements IhandoverSaveView{

    private IhandoverSavePresenter handoverSavePresenter;
    private Unbinder unbinder;

    @BindView(R.id.tv_date)TextView tvDate;
    @BindView(R.id.tv_bankname)TextView tvBankname;
    @BindView(R.id.tv_staffNo)TextView tvStaffNo;
    @BindView(R.id.tv_staffName)TextView tvStaffName;
    @BindView(R.id.tv_position)TextView tvPosition;

    private Toast toast;
    private AlertDialog positionlistDialog;

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
        setContentView(R.layout.activity_handover_save);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy(){
        if(handoverSavePresenter!=null)
            handoverSavePresenter.onDestroy();
        if(positionlistDialog!=null)
            positionlistDialog.dismiss();
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_back)
    void onBackClick(){
        this.finish();
    }

    @OnClick(R.id.btn_save)
    void onSaveClick(){
        handoverSavePresenter.save();
    }

    @OnClick(R.id.tv_date)
    void onDateClick(){
        DatePickerUtil.showYearMonthDayPicker(this, tvDate, false, null);
    }

    @OnClick(R.id.tv_position)
    void onBankNameClick(){
        if(positionlistDialog==null){
            handoverSavePresenter.loadPositionList();
            showToast("正在获取支行列表，请保持网络通畅。");
            return;
        }
        positionlistDialog.show();
    }

    private void init(){
        findViewById(R.id.btn_add).setVisibility(View.GONE);

        Calendar ca = Calendar.getInstance();
        final int year = ca.get(Calendar.YEAR);
        final int month = ca.get(Calendar.MONTH);
        final int day = ca.get(Calendar.DAY_OF_MONTH);
        tvDate.setText(year+"-"+(month+1<=9?"0"+(month+1):(month+1))+"-"+(day<=9?"0"+day:day));

        handoverSavePresenter = new HandoverSavePresenter(this);
        handoverSavePresenter.loadPositionList();

        User user = PMSApplication.gUser;
        tvStaffNo.setText(user.getTellId());
        tvStaffName.setText(user.getRealname());
        handoverSavePresenter.loadOrganizationName();
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
                }
            });
        }
        return;
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

    @Override
    public String getStaffName() {
        if(tvStaffName==null)
            return null;
        return tvStaffName.getText().toString().trim();
    }

    @Override
    public String getStaffNo() {
        if(tvStaffNo==null)
            return null;
        return tvStaffNo.getText().toString().trim();
    }

    @Override
    public String getOrganizationName() {
        if(tvBankname==null)
            return null;
        return tvBankname.getText().toString().trim();
    }

    @Override
    public void setOrganizationName(String organizationName) {
        if(tvBankname==null)
            return;
        tvBankname.setText(organizationName.trim());
    }
}
