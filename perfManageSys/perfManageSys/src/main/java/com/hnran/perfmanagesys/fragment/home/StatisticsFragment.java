package com.hnran.perfmanagesys.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.BaseFragment;
import com.hnran.perfmanagesys.activity.warning.WarningCommonActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */

public class StatisticsFragment extends BaseFragment {

    public ListView listview;
    private ArrayAdapter<String> adapter;

    private List<String> contents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
//		super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_warning, null);

        initVariables();
        initViews(view);
        loadData();

        return view;

    }

    protected void initVariables() {
        // TODO Auto-generated method stub

        contents = new ArrayList<String>();
        contents.add("员工工资查询");
        contents.add("全行工资排名前十");
        contents.add("全行工资排名后十");
        contents.add("支行工资排名前十");
        contents.add("支行工资排名后十");
        contents.add("柜员交接");

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_warning_main, R.id.warning_tv_text, contents);

    }


    protected void initViews(View view) {
        // TODO Auto-generated method stub
        TextView tv_title = (TextView) view.findViewById(R.id.tv_common_top_title);
        tv_title.setText(title);

        listview = (ListView) view.findViewById(R.id.warning_lv_customer);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Log.d("cick", position+"");
                Intent intent = new Intent();
                if(0<position&&position<5){
                    intent.putExtra("type",position);
                }
                intent.setAction("com.hnran.perfmanagesys.Statistics.view."+position);
                StatisticsFragment.this.startActivity(intent);
            }
        });

    }

    protected void loadData() {
        // TODO Auto-generated method stub

    }
}
