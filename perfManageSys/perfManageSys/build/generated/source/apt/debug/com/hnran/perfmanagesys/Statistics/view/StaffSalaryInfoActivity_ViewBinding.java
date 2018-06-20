// Generated code from Butter Knife. Do not modify!
package com.hnran.perfmanagesys.Statistics.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hnran.perfmanagesys.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StaffSalaryInfoActivity_ViewBinding implements Unbinder {
  private StaffSalaryInfoActivity target;

  private View view2131362654;

  @UiThread
  public StaffSalaryInfoActivity_ViewBinding(StaffSalaryInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StaffSalaryInfoActivity_ViewBinding(final StaffSalaryInfoActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_back, "field 'btnBack' and method 'onBackClick'");
    target.btnBack = Utils.castView(view, R.id.btn_back, "field 'btnBack'", ImageView.class);
    view2131362654 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBackClick();
      }
    });
    target.tvCommonSalaryTitle = Utils.findRequiredViewAsType(source, R.id.tv_common_salary_title, "field 'tvCommonSalaryTitle'", TextView.class);
    target.tvSalaryDate = Utils.findRequiredViewAsType(source, R.id.tv_salary_date, "field 'tvSalaryDate'", TextView.class);
    target.tvBankName = Utils.findRequiredViewAsType(source, R.id.tv_bank_name, "field 'tvBankName'", TextView.class);
    target.tvStaffNo = Utils.findRequiredViewAsType(source, R.id.tv_staff_no, "field 'tvStaffNo'", TextView.class);
    target.tvStaffName = Utils.findRequiredViewAsType(source, R.id.tv_staff_name, "field 'tvStaffName'", TextView.class);
    target.tvHeadquartersRank = Utils.findRequiredViewAsType(source, R.id.tv_headquarters_rank, "field 'tvHeadquartersRank'", TextView.class);
    target.tvBranchRank = Utils.findRequiredViewAsType(source, R.id.tv_branch_rank, "field 'tvBranchRank'", TextView.class);
    target.tvSaveSalary = Utils.findRequiredViewAsType(source, R.id.tv_save_salary, "field 'tvSaveSalary'", TextView.class);
    target.tvBorrowSalary = Utils.findRequiredViewAsType(source, R.id.tv_borrow_salary, "field 'tvBorrowSalary'", TextView.class);
    target.tvBankSalary = Utils.findRequiredViewAsType(source, R.id.tv_bank_salary, "field 'tvBankSalary'", TextView.class);
    target.tvTransactionSalary = Utils.findRequiredViewAsType(source, R.id.tv_transaction_salary, "field 'tvTransactionSalary'", TextView.class);
    target.tvManageSalary = Utils.findRequiredViewAsType(source, R.id.tv_manage_salary, "field 'tvManageSalary'", TextView.class);
    target.tvLocalSalary = Utils.findRequiredViewAsType(source, R.id.tv_local_salary, "field 'tvLocalSalary'", TextView.class);
    target.salaryTotal = Utils.findRequiredViewAsType(source, R.id.salary_total, "field 'salaryTotal'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StaffSalaryInfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnBack = null;
    target.tvCommonSalaryTitle = null;
    target.tvSalaryDate = null;
    target.tvBankName = null;
    target.tvStaffNo = null;
    target.tvStaffName = null;
    target.tvHeadquartersRank = null;
    target.tvBranchRank = null;
    target.tvSaveSalary = null;
    target.tvBorrowSalary = null;
    target.tvBankSalary = null;
    target.tvTransactionSalary = null;
    target.tvManageSalary = null;
    target.tvLocalSalary = null;
    target.salaryTotal = null;

    view2131362654.setOnClickListener(null);
    view2131362654 = null;
  }
}
