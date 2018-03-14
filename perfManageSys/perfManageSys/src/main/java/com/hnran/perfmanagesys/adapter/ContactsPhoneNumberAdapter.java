package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.ContactsPhone;
import com.readboy.ssm.po.VisitFamilyInfo;

import java.util.List;


/**
 * Created by Wyd on 2018/1/6.
 * 联系人列表
 */

public class ContactsPhoneNumberAdapter extends BaseAdapter implements View.OnClickListener{

    private static final String TAG = "ContactsPhoneNumberAdapter";
    private List<VisitFamilyInfo> visitFamilyInfos;
    private Context mCtx;
    private ViewHolder viewHolder;
    private CallBack mCallBack;



    public ContactsPhoneNumberAdapter(Context ctx, List<VisitFamilyInfo> visitFamilyInfos, CallBack callBack) {
        this.mCtx = ctx;
        this.visitFamilyInfos = visitFamilyInfos;
        this.mCallBack = callBack;
    }

    @Override
    public void onClick(View v) {
        mCallBack.click(v);
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface  CallBack{
        public void click(View view);
    }

    @Override
    public int getCount() {
        return visitFamilyInfos == null ? 0 : visitFamilyInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return visitFamilyInfos == null ? 0 : visitFamilyInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(mCtx).inflate(R.layout.item_visit_contanct_informaition,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.contactName.setText(visitFamilyInfos.get(position).getMemberName());
        viewHolder.contactNumber.setText(visitFamilyInfos.get(position).getPhone());
        viewHolder.callNumber.setOnClickListener(this);
        viewHolder.callNumber.setTag(position);
        return convertView;
    }

    public static class ViewHolder{
        TextView contactName;
        TextView contactNumber;
        ImageView callNumber;

        public ViewHolder(View view){
            this.contactName = (TextView) view.findViewById(R.id.tv_contact_people);
            this.contactNumber = (TextView) view.findViewById(R.id.tv_contact_number);
            this.callNumber = (ImageView) view.findViewById(R.id.iv_call_phone_number);
        }

    }
}
