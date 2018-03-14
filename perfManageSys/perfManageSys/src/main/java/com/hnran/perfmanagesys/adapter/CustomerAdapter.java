package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.Customer;
import com.readboy.ssm.po.Marketing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerAdapter extends BaseAdapter{

	private List<Customer> customers;
	private Context mCtx;
	
	public CustomerAdapter(Context ctx, List<Customer> customers){
		this.customers = customers;
		this.mCtx = ctx;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customers == null ? 0 : customers.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  customers == null ? 0 : customers.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mCtx).inflate(
					R.layout.item_customer, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvId.setText(customers.get(position).getKhbh());
		viewHolder.tvName.setText(customers.get(position).getKhmc());
		viewHolder.tvTelephone.setText(customers.get(position).getSjhm());
		viewHolder.tvType.setText(customers.get(position).getCpxx());
		viewHolder.tvAddress.setText(customers.get(position).getZz());
		viewHolder.tvLable.setText(position + 1 + "");
		
		return convertView;
		
	}
	
	public void setData(List<Customer> customers){  
	       this.customers = customers; 
	   } 
	
	
	/* 封装类 */
	public static class ViewHolder {
		TextView tvId;
		TextView tvName;
		TextView tvTelephone;
		TextView tvType;
		TextView tvAddress;
		LabelTextView tvLable;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tvId = (TextView) v.findViewById(R.id.tv_customer_id_content);
			this.tvName = (TextView) v.findViewById(R.id.tv_customer_name_content);
			this.tvTelephone = (TextView) v.findViewById(R.id.tv_customer_tele_content);
			this.tvType = (TextView) v.findViewById(R.id.tv_customer_type_content);
			this.tvAddress = (TextView) v.findViewById(R.id.tv_customer_address);
			this.tvLable = (LabelTextView) v.findViewById(R.id.item_performance_lable);
		}
	}	
	
}
