package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.Marketing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MarketCommonAdapter extends BaseAdapter{

	private List<? extends Marketing> customers;
	private Context mCtx;
	
	public MarketCommonAdapter(Context ctx, List<? extends Marketing> customers){
		this.customers = customers;
		this.mCtx = ctx;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customers != null ? customers.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customers.get(position);
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
					R.layout.item_market, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvId.setText(customers.get(position).getYybh() + "");
		viewHolder.tvName.setText(customers.get(position).getKhmc());
		viewHolder.tvTelephone.setText(customers.get(position).getSjhm());
		viewHolder.tvType.setText(customers.get(position).getZzjc());
//		String str = customers.get(position).getYyrq() != null ? customers.get(position).getYyrq().toString():"2017-05-20";
		viewHolder.tvDate.setText(customers.get(position).getYyrq());
		viewHolder.tvLable.setText(position+1+"");
		
		return convertView;
		
	}
	
	
	/* 封装类 */
	public static class ViewHolder {
		TextView tvId;
		TextView tvName;
		TextView tvTelephone;
		TextView tvType;
		TextView tvDate;
		LabelTextView tvLable;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tvId = (TextView) v.findViewById(R.id.tv_market_id_content);
			this.tvName = (TextView) v.findViewById(R.id.tv_market_name_content);
			this.tvTelephone = (TextView) v.findViewById(R.id.tv_market_tele_content);
			this.tvType = (TextView) v.findViewById(R.id.tv_market_type_content);
			this.tvDate = (TextView) v.findViewById(R.id.tv_market_address);
			this.tvLable = (LabelTextView) v.findViewById(R.id.item_performance_lable);
		}
	}	
	
	
	public void setData(List<? extends Marketing> marker){  
	       this.customers = marker; 
	   } 
	
}
