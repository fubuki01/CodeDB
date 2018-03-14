package com.hnran.perfmanagesys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.CallRecord;
import com.readboy.ssm.po.ContactsPhone;
import com.readboy.ssm.po.VisitContactLog;

import java.util.List;


/**
 * Created by Wyd on 2018/1/6.
 * 联系人列表
 */

public class CallRecordAdapter extends BaseAdapter implements View.OnClickListener{

    private static final String TAG = "CallRecordAdapter";
    private List<VisitContactLog> contactLogs;
    private Context mCtx;
    private ViewHolder viewHolder;
    private CallBackRecord mCallBackRecord;



    public CallRecordAdapter(Context ctx, List<VisitContactLog> contactLogs, CallBackRecord callBackRecord) {
        this.mCtx = ctx;
        this.contactLogs = contactLogs;
        this.mCallBackRecord = callBackRecord;
    }

    @Override
    public void onClick(View v) {
        mCallBackRecord.recordClick(v);
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     */
    public interface CallBackRecord{
        public void recordClick(View view);
    }

    @Override
    public int getCount() {
        return contactLogs == null ? 0 : contactLogs.size();
    }

    @Override
    public Object getItem(int position) {
        return contactLogs == null ? 0 : contactLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(mCtx).inflate(R.layout.item_visit_contact_call_log,null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.callTime.setText(contactLogs.get(position).getDate());
        viewHolder.callPhoneNumber.setText(contactLogs.get(position).getPhone());
        viewHolder.callRecord.setOnClickListener(this);
        viewHolder.callRecord.setTag(contactLogs.get(position).getFileName());
        return convertView;
    }

    public static class ViewHolder{
        TextView callTime;
        TextView callPhoneNumber;
        ImageView callRecord;

        public ViewHolder(View view){
            this.callTime = (TextView) view.findViewById(R.id.tv_call_time);
            this.callPhoneNumber = (TextView) view.findViewById(R.id.tv_call_phone_number);
            this.callRecord = (ImageView) view.findViewById(R.id.iv_call_record);
        }

    }
}
