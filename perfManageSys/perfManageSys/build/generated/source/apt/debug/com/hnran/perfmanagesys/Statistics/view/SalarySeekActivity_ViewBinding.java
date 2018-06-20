// Generated code from Butter Knife. Do not modify!
package com.hnran.perfmanagesys.Statistics.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hnran.perfmanagesys.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SalarySeekActivity_ViewBinding implements Unbinder {
  private SalarySeekActivity target;

  private View view2131361907;

  private View view2131361908;

  private View view2131362020;

  private View view2131362656;

  private View view2131362654;

  @UiThread
  public SalarySeekActivity_ViewBinding(SalarySeekActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SalarySeekActivity_ViewBinding(final SalarySeekActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tv_date, "field 'tv_date' and method 'onDateClick'");
    target.tv_date = Utils.castView(view, R.id.tv_date, "field 'tv_date'", TextView.class);
    view2131361907 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_bankname, "field 'tv_bankname' and method 'onBankNameClick'");
    target.tv_bankname = Utils.castView(view, R.id.tv_bankname, "field 'tv_bankname'", TextView.class);
    view2131361908 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBankNameClick();
      }
    });
    target.et_staffNo = Utils.findRequiredViewAsType(source, R.id.et_staffNo, "field 'et_staffNo'", EditText.class);
    target.et_staffName = Utils.findRequiredViewAsType(source, R.id.et_staffName, "field 'et_staffName'", EditText.class);
    view = Utils.findRequiredView(source, R.id.lv_staff_info, "field 'lv_staff_info' and method 'onItemClick'");
    target.lv_staff_info = Utils.castView(view, R.id.lv_staff_info, "field 'lv_staff_info'", ListView.class);
    view2131362020 = view;
    ((AdapterView<?>) view).setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> p0, View p1, int p2, long p3) {
        target.onItemClick(p0, p1, p2, p3);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_search, "field 'btn_search' and method 'onSearchClick'");
    target.btn_search = Utils.castView(view, R.id.btn_search, "field 'btn_search'", ImageView.class);
    view2131362656 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSearchClick();
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
  }

  @Override
  @CallSuper
  public void unbind() {
    SalarySeekActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_date = null;
    target.tv_bankname = null;
    target.et_staffNo = null;
    target.et_staffName = null;
    target.lv_staff_info = null;
    target.btn_search = null;

    view2131361907.setOnClickListener(null);
    view2131361907 = null;
    view2131361908.setOnClickListener(null);
    view2131361908 = null;
    ((AdapterView<?>) view2131362020).setOnItemClickListener(null);
    view2131362020 = null;
    view2131362656.setOnClickListener(null);
    view2131362656 = null;
    view2131362654.setOnClickListener(null);
    view2131362654 = null;
  }
}
