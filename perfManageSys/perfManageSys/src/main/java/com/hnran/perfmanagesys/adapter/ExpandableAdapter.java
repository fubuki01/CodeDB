package com.hnran.perfmanagesys.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hnran.perfmanagesys.Statistics.model.Bean.StaffSalaryInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public abstract class ExpandableAdapter<T> extends BaseAdapter {
    private List<T> list;

    public ExpandableAdapter(List<T> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list==null)
            return 0;
        return list.size();
    }

    @Override
    public T getItem(int position) {
        if(list==null)
            return null;
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void expand(List<T> data){
        list.addAll(data);
    }

    public void clear(){
        list.clear();
    }
}
