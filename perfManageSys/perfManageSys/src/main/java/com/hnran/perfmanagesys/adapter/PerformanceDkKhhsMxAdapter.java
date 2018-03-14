package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceDkKhhsMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformanceDkKhhsMxAdapter extends ADataLoadAdapter{

	private List<PerformanceDkKhhsMx> pfmJRMs;
	
//	public PerformanceDkKhhsMxAdapter(List<PerformanceDkKhhsMx> pfmJRMs){
//		this.pfmJRMs = pfmJRMs;
//	}
	
	public PerformanceDkKhhsMxAdapter(){
		this.pfmJRMs = new ArrayList<PerformanceDkKhhsMx>();
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_khhs, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmJRMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmJRMs.get(position).getKhbh());
		viewHolder.tv2.setText(pfmJRMs.get(position).getKhmc());
		viewHolder.tv3.setText(pfmJRMs.get(position).getZjhm());
		viewHolder.tv4.setText(pfmJRMs.get(position).getZzkhr());
		viewHolder.tv5.setText(pfmJRMs.get(position).getLx());
		viewHolder.tv6.setText(pfmJRMs.get(position).getDj()+"");
		viewHolder.tv7.setText(pfmJRMs.get(position).getGz()+"");
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
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_jgmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_khrq);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_zjhm);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_zzkhrq);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_lx);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_dj);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_daikuan_khhs_gz);
			
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmJRMs.addAll((List<PerformanceDkKhhsMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmJRMs.clear();
		
	}	
	
}
