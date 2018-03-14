package com.hnran.perfmanagesys.visit;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.adapter.ViewPagerAdapter;
import com.hnran.perfmanagesys.fragment.visit.BasicInformationFragment;
import com.hnran.perfmanagesys.fragment.visit.ContactInformationFragment;
import com.hnran.perfmanagesys.fragment.visit.FamilyInformationFragment;
import com.hnran.perfmanagesys.fragment.visit.OtherInformationFragment;
import com.hnran.perfmanagesys.fragment.visit.VisitJudgeAndSaveCallBack;
import com.hnran.perfmanagesys.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wyd on 2018/1/2.
 */

public class VisitMainActivity extends FragmentActivity {

    private static final String TAG = "VisitMainActivity";
    String[] visitMenu = {"基本信息","家庭信息","联系方式","其他信息"};

    private List<LinearLayout> llTabs;
    private List<TextView> tvTabs;
    private List<ImageView> ivTabs;

    private int[] ivIds = {R.drawable.basicinformation, R.drawable.familyinformation, R.drawable.contactinformation, R.drawable.otherinformation};
    private int[] ivSltIds = {R.drawable.basicinformation_selected, R.drawable.familyinformation_selected, R.drawable.contactinformation_selected, R.drawable.otherinformation_selected};

    private int[] clickPosition = {0, 0, 0, 0};    //菜单项的实际位置，未包含隐藏项

    private NoScrollViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private List<Fragment> fragmentLists;
    private int currentPosition;
    private int oldPosition;

    private Resources mRresources;

    //右边操作按钮
    private TextView common_visit_title_tv;
    //保存对话框
    private AlertDialog mAlertDialogRecord = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        initVariables();
        initViews(savedInstanceState);
        updateMenus();
    }


    private void initVariables() {
        mRresources = this.getResources();

        currentPosition = oldPosition = 0;

        fragmentLists = new ArrayList<Fragment>();

        vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentLists);

        llTabs = new ArrayList<LinearLayout>();

        tvTabs = new ArrayList<TextView>();

        ivTabs = new ArrayList<ImageView>();

    }


    protected void initViews(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_visit_main);
        /**
         * 拜访客户标题栏右边的按钮
         */
        common_visit_title_tv = (TextView) findViewById(R.id.common_visit_title_tv);
        common_visit_title_tv.setVisibility(View.VISIBLE);
        common_visit_title_tv.setText("保存");
        common_visit_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否执行保存操作
                boolean isjudge = ((VisitJudgeAndSaveCallBack)fragmentLists.get(currentPosition)).judgeCallBack();
                if(!isjudge)
                    return;
                //弹出保存对话框
                if(mAlertDialogRecord!=null){
                    mAlertDialogRecord.show();
                    return;
                }
                mAlertDialogRecord = new AlertDialog.Builder(VisitMainActivity.this).create();
                mAlertDialogRecord.setCanceledOnTouchOutside(false);
                mAlertDialogRecord.show();
                mAlertDialogRecord.getWindow().setContentView(R.layout.dialog_pop);
                TextView msg = (TextView) mAlertDialogRecord.getWindow().findViewById(R.id.tv_sjd_id);
                msg.setText("是否保存当前界面的信息？");
                Button cancle = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_right);
                Button confirm = (Button) mAlertDialogRecord.getWindow().findViewById(R.id.alarm_btn_left);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialogRecord.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((VisitJudgeAndSaveCallBack)fragmentLists.get(currentPosition)).saveCallBack();
                        mAlertDialogRecord.dismiss();
                    }
                });
            }
        });

        /**
         * 拜访客户左边的返回按钮
         */
        LinearLayout mBack = (LinearLayout) findViewById(R.id.ll_common_visit);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisitMainActivity.this.finish();
            }
        });

        /**
         * 拜访客户菜单按钮
         */

        //基本信息
        LinearLayout mBasic = (LinearLayout) findViewById(R.id.visit_ll_basic);
        llTabs.add(mBasic);
        mBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = currentPosition;
                currentPosition = 0;
                changeTabState();
            }
        });

        //家庭信息
        LinearLayout mFamily = (LinearLayout) findViewById(R.id.visit_ll_family);
        llTabs.add(mFamily);
        mFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = currentPosition;
                currentPosition = 1;
                changeTabState();
            }
        });

        //联系方式
        LinearLayout mContact = (LinearLayout) findViewById(R.id.visit_ll_contact);
        llTabs.add(mContact);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = currentPosition;
                currentPosition = 2;
                changeTabState();
            }
        });

        //其他信息
        LinearLayout mOther = (LinearLayout) findViewById(R.id.visit_ll_other);
        llTabs.add(mOther);
        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPosition = currentPosition;
                currentPosition = 3;
                changeTabState();
            }
        });

        /**
         * 菜单按钮文字
         */
        tvTabs.add((TextView) findViewById(R.id.visit_tv_basic));
        tvTabs.add((TextView) findViewById(R.id.visit_tv_family));
        tvTabs.add((TextView) findViewById(R.id.visit_tv_contact));
        tvTabs.add((TextView) findViewById(R.id.visit_tv_other));

        /**
         * 菜单按钮图标
         */
        ivTabs.add((ImageView) findViewById(R.id.visit_iv_basic));
        ivTabs.add((ImageView) findViewById(R.id.visit_iv_family));
        ivTabs.add((ImageView) findViewById(R.id.visit_iv_contact));
        ivTabs.add((ImageView) findViewById(R.id.visit_iv_other));

        viewPager = (NoScrollViewPager) findViewById(R.id.visit_viewpager);
        viewPager.setNoScroll(true);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                oldPosition = currentPosition;
                currentPosition = arg0;
                changeTabState();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }



    private void changeTabState() {

        tvTabs.get(oldPosition).setTextColor(mRresources.getColor(R.color.font_black_2));

        tvTabs.get(currentPosition).setTextColor(mRresources.getColor(R.color.font_tab_selected));

        ivTabs.get(oldPosition).setImageResource(ivIds[oldPosition]);

        ivTabs.get(currentPosition).setImageResource(ivSltIds[currentPosition]);


        if (viewPager.getCurrentItem() != clickPosition[currentPosition]) {
            viewPager.setCurrentItem(clickPosition[currentPosition]);
        }
    }

    /**
     * 拜访客户的菜单选项
     */
    private void updateMenus() {

        BaseFragment[] fragments = {new BasicInformationFragment(), new FamilyInformationFragment(), new ContactInformationFragment(), new OtherInformationFragment()};

        fragmentLists.clear();

        int first = -1;

        for (int i = 0, j = 0, size = tvTabs.size(); i < size; i++) {
            try {
                if (first == -1)
                    first = i;        //记录第一个不隐藏的菜单项
                clickPosition[i] = j++;

                fragments[i].setTitle(visitMenu[i]);
                fragmentLists.add(fragments[i]);        //同步修改二级界面标题

                llTabs.get(i).setVisibility(View.VISIBLE);
                if (visitMenu[i] != null) {
                    tvTabs.get(i).setText(visitMenu[i]);
                }

            }catch(IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }

        vpAdapter.notifyDataSetChanged();

        currentPosition = oldPosition = first;
        changeTabState();
        viewPager.setCurrentItem(clickPosition[first]);
    }



}
