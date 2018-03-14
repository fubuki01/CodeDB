package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceDepositHushuMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerfCunkuanHushuAdapter extends ADataLoadAdapter{

	private final String[] CUNKUAN_LEIXING = {"","存量","增量"};
	
	private List<PerformanceDepositHushuMx> pfmDHMs;
	
	public PerfCunkuanHushuAdapter(){
		this.pfmDHMs = new ArrayList<PerformanceDepositHushuMx>();
	}
	
	public PerfCunkuanHushuAdapter(List<PerformanceDepositHushuMx> pfmDHMs){
		this.pfmDHMs = pfmDHMs;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmDHMs == null ? 0 : pfmDHMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmDHMs == null ? 0 : pfmDHMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_hushu, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		
		viewHolder.tv0.setText(pfmDHMs.get(position).getKhmc());
		viewHolder.tv1.setText(pfmDHMs.get(position).getLx());
		try{
			int index = Integer.valueOf(pfmDHMs.get(position).getLx());
			viewHolder.tv1.setText(CUNKUAN_LEIXING[index]);
		}catch(NumberFormatException  e){
			e.printStackTrace();
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		viewHolder.tv2.setText(pfmDHMs.get(position).getZzjc());
		viewHolder.tv3.setText(pfmDHMs.get(position).getDj() + "");
		viewHolder.tv4.setText(pfmDHMs.get(position).getZzkhrq());
		viewHolder.tv5.setText(pfmDHMs.get(position).getGz() + "");
		viewHolder.tv6.setText(pfmDHMs.get(position).getGhjsrq());
		viewHolder.tv7.setText(pfmDHMs.get(position).getLjgz() + "");
		
		return convertView;
		
	}
	
	
	/* 封装类 */
	public class ViewHolder {
		TextView tv00;
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
			this.tv00 = (TextView) v.findViewById(R.id.item_performance_lable);			
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_ck_hs_khmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_ck_hs_lx);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_ck_hs_jgmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_ck_hs_dj);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_ck_hs_zzkhrq);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_ck_hs_gz);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_ck_hs_ghjsrq);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_ck_hs_ljgz);
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmDHMs.addAll((List<PerformanceDepositHushuMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmDHMs.clear();
	}	
	
}
