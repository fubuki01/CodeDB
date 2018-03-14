package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.VisitBaseInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class VisitBasicInfoRecodeAdapter extends BaseAdapter {

    private List<VisitBaseInfo> listViewData;
    private Context mContext;

    public VisitBasicInfoRecodeAdapter(List<VisitBaseInfo> listViewData, Context mContext) {
        this.listViewData = listViewData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return listViewData.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_visit_basic_information_log, null);
        }

        TextView vt = (TextView)view.findViewById(R.id.itme_visit_type);
        TextView date = (TextView)view.findViewById(R.id.itme_date);
        //设置文本内容
        vt.setText(listViewData.get(i).getType());
        date.setText(listViewData.get(i).getDate());
        return view;
    }

    public void clearData(){
        listViewData.clear();
    }

    public void setData(VisitBaseInfo info){
        listViewData.add(info);
    }
}
