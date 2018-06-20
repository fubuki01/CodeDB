package com.hnran.perfmanagesys.Statistics.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StaffSalaryInfoActivity extends Activity {

    @BindView(R.id.btn_back) ImageView btnBack;
    @BindView(R.id.tv_common_salary_title) TextView tvCommonSalaryTitle;
    @BindView(R.id.tv_salary_date) TextView tvSalaryDate;
    @BindView(R.id.tv_bank_name) TextView tvBankName;
    @BindView(R.id.tv_staff_no) TextView tvStaffNo;
    @BindView(R.id.tv_staff_name) TextView tvStaffName;
    @BindView(R.id.tv_headquarters_rank) TextView tvHeadquartersRank;
    @BindView(R.id.tv_branch_rank) TextView tvBranchRank;
    @BindView(R.id.tv_save_salary) TextView tvSaveSalary;
    @BindView(R.id.tv_borrow_salary) TextView tvBorrowSalary;
    @BindView(R.id.tv_bank_salary) TextView tvBankSalary;
    @BindView(R.id.tv_transaction_salary) TextView tvTransactionSalary;
    @BindView(R.id.tv_manage_salary) TextView tvManageSalary;
    @BindView(R.id.tv_local_salary) TextView tvLocalSalary;
    @BindView(R.id.salary_total) TextView salaryTotal;

    private Unbinder unbinder;

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
        setContentView(R.layout.activity_staff_salary_info);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.btn_back)
    void onBackClick(){
        this.finish();
    }

    private void init(){
        Intent intent = getIntent();
        StaffSalaryInfo info = intent.getParcelableExtra("info");
        tvSalaryDate.setText(info.getGZRQ().substring(0,10));
        tvBankName.setText(info.getZZMC());
        tvStaffNo.setText(info.getYGGH());
        tvStaffName.setText(info.getREALNAME());
        tvHeadquartersRank.setText(info.getQHGZPM()+"");
        tvBranchRank.setText(info.getZHGZPM()+"");
        tvSaveSalary.setText(info.getCKGZ()+"");
        tvBorrowSalary.setText(info.getDKGZ()+"");
        tvBankSalary.setText(info.getDZYHGZ()+"");
        tvTransactionSalary.setText(info.getYWLGZ()+"");
        tvManageSalary.setText(info.getGLJXGZ()+"");
        tvLocalSalary.setText(info.getDQBCGZ()+"");
        salaryTotal.setText(info.getGZHJ()+"");
    }
}
