// Generated code from Butter Knife. Do not modify!
package com.hnran.perfmanagesys.Statistics.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hnran.perfmanagesys.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HandoverSaveActivity_ViewBinding implements Unbinder {
  private HandoverSaveActivity target;

  private View view2131361907;

  private View view2131361915;

  private View view2131362654;

  private View view2131361916;

  @UiThread
  public HandoverSaveActivity_ViewBinding(HandoverSaveActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HandoverSaveActivity_ViewBinding(final HandoverSaveActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_date, "field 'tvDate' and method 'onDateClick'");
    target.tvDate = Utils.castView(view, R.id.tv_date, "field 'tvDate'", TextView.class);
    view2131361907 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick();
      }
    });
    target.tvBankname = Utils.findRequiredViewAsType(source, R.id.tv_bankname, "field 'tvBankname'", TextView.class);
    target.tvStaffNo = Utils.findRequiredViewAsType(source, R.id.tv_staffNo, "field 'tvStaffNo'", TextView.class);
    target.tvStaffName = Utils.findRequiredViewAsType(source, R.id.tv_staffName, "field 'tvStaffName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_position, "field 'tvPosition' and method 'onBankNameClick'");
    target.tvPosition = Utils.castView(view, R.id.tv_position, "field 'tvPosition'", TextView.class);
    view2131361915 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBankNameClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'onBackClick'");
    view2131362654 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBackClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save, "method 'onSaveClick'");
    view2131361916 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSaveClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HandoverSaveActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDate = null;
    target.tvBankname = null;
    target.tvStaffNo = null;
    target.tvStaffName = null;
    target.tvPosition = null;

    view2131361907.setOnClickListener(null);
    view2131361907 = null;
    view2131361915.setOnClickListener(null);
    view2131361915 = null;
    view2131362654.setOnClickListener(null);
    view2131362654 = null;
    view2131361916.setOnClickListener(null);
    view2131361916 = null;
  }
}
