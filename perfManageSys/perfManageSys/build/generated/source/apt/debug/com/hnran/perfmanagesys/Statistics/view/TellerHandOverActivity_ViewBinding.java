// Generated code from Butter Knife. Do not modify!
package com.hnran.perfmanagesys.Statistics.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hnran.perfmanagesys.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TellerHandOverActivity_ViewBinding implements Unbinder {
  private TellerHandOverActivity target;

  private View view2131361907;

  private View view2131362040;

  private View view2131362654;

  private View view2131362658;

  @UiThread
  public TellerHandOverActivity_ViewBinding(TellerHandOverActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TellerHandOverActivity_ViewBinding(final TellerHandOverActivity target, View source) {
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
    view = Utils.findRequiredView(source, R.id.tv_handover_position, "field 'tvPosition' and method 'onBankNameClick'");
    target.tvPosition = Utils.castView(view, R.id.tv_handover_position, "field 'tvPosition'", TextView.class);
    view2131362040 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBankNameClick();
      }
    });
    target.lv = Utils.findRequiredViewAsType(source, R.id.lv_staff_info, "field 'lv'", ListView.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'onBackClick'");
    view2131362654 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBackClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_add, "method 'onAddClick'");
    view2131362658 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TellerHandOverActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDate = null;
    target.tvPosition = null;
    target.lv = null;

    view2131361907.setOnClickListener(null);
    view2131361907 = null;
    view2131362040.setOnClickListener(null);
    view2131362040 = null;
    view2131362654.setOnClickListener(null);
    view2131362654 = null;
    view2131362658.setOnClickListener(null);
    view2131362658 = null;
  }
}
