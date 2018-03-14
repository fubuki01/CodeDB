package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceCbBmkkhhsMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformanceCbBmkkhhsMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCbBmkkhhsMx> pfmJRMs;
	
	public PerformanceCbBmkkhhsMxAdapter(){
		this.pfmJRMs = new ArrayList<PerformanceCbBmkkhhsMx>();
	}
	
//	public PerformanceCbBmkkhhsMxAdapter(List<PerformanceCbBmkkhhsMx> pfmJRMs){
//		this.pfmJRMs = pfmJRMs;
//	}
	
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_biamingka, null);
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
		viewHolder.tv4.setText(pfmJRMs.get(position).getDj()+"");
		viewHolder.tv5.setText(pfmJRMs.get(position).getGz()+"");
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
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_bianmingka_jgmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_bianmingka_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_bianmingka_khrq);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_bianmingka_zjhm);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_bianmingka_dj);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_bianmingka_gz);
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmJRMs.addAll((List<PerformanceCbBmkkhhsMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmJRMs.clear();
	}	
	
}
