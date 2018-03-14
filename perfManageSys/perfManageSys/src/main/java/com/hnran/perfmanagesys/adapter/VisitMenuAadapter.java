package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 拜访功能下拉菜单的适配器
 */

public class VisitMenuAadapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private int res;
    private String[] strs;

    public VisitMenuAadapter(Context context, int resource, String[] strs) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.res = resource;
        this.strs = strs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(res, null);

        TextView text = (TextView) convertView.findViewById(android.R.id.text1);
        text.setText(getItem(position));
        text.setTextColor(Color.BLACK);
        text.setTextSize(15);
        convertView.setBackgroundColor(Color.WHITE);

        return convertView;
    }

    @Override
    public String getItem(int position) {
        return strs[position];
    }

    @Override
    public int getCount() {
        return strs.length;
    }
}


