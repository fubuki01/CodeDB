package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceDKDydklxshMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceDKDydklxshMxAdapter extends ADataLoadAdapter{

	private List<PerformanceDKDydklxshMx> pfmJRMs;
	
//	public PerformanceDKDydklxshMxAdapter(List<PerformanceDKDydklxshMx> pfmJRMs){
//		this.pfmJRMs = pfmJRMs;
//	}
	public PerformanceDKDydklxshMxAdapter(){
		this.pfmJRMs = new ArrayList<PerformanceDKDydklxshMx>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmJRMs == null ? 0 : pfmJRMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmJRMs == null ? 0 : pfmJRMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_dydkshlx, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmJRMs.get(position).getDkzh());
		viewHolder.tv1.setText(pfmJRMs.get(position).getZzjc());
		viewHolder.tv2.setText(pfmJRMs.get(position).getZhmc());
		viewHolder.tv3.setText(pfmJRMs.get(position).getShlx()+"");
		viewHolder.tv4.setText(pfmJRMs.get(position).getSxr());
		viewHolder.tv5.setText(pfmJRMs.get(position).getFcbl()+"");
		viewHolder.tv6.setText(pfmJRMs.get(position).getZbdj()+"");
		viewHolder.tv7.setText(pfmJRMs.get(position).getZbgz()+"");
		return convertView;
	}
	
	
	/* 封装类 */
	public class ViewHolder {
		LabelTextView tv00;
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv6;
		TextView tv7;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_dkzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_qsjg);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_shlx);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_shrq);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_shje);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_dj);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dk_dydklx_gz);
			
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmJRMs.addAll((List<PerformanceDKDydklxshMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmJRMs.clear();
	}	
	
}
