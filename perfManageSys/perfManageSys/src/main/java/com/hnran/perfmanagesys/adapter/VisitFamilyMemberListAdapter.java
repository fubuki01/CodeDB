package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.fragment.visit.VisitDeleteCallBack;
import com.readboy.ssm.po.VisitFamilyInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */

public class VisitFamilyMemberListAdapter extends BaseAdapter{

    private List<VisitFamilyInfo> listViewData;
    private Context mContext;
    private VisitDeleteCallBack callback;

    public VisitFamilyMemberListAdapter(List<VisitFamilyInfo> listViewData, Context mContext, VisitDeleteCallBack callback) {
        this.listViewData = listViewData;
        this.mContext = mContext;
        this.callback = callback;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_visit_family_member, null);
        }

        TextView name = (TextView)view.findViewById(R.id.itme_family_member_name);
        TextView number = (TextView)view.findViewById(R.id.itme_credentials_number);
        TextView relation = (TextView)view.findViewById(R.id.itme_family_member_relationship);
        TextView deleteButton = (TextView)view.findViewById(R.id.itme_family_member_delete);
        //设置文本内容
        VisitFamilyInfo info = listViewData.get(i);
        name.setText(info.getMemberName());
        number.setText(info.getCredentialNum());
        relation.setText(info.getRelationship());
        final int id = info.getId();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deleteCallBack(id);
            }
        });
        return view;
    }

    public void setData(VisitFamilyInfo info){
        listViewData.add(info);
    }

    public void clearData(){
        listViewData.clear();
    }
}
