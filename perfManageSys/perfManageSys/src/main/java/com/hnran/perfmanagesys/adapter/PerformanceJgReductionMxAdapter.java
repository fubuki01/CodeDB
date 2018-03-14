package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceJgReductionMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceJgReductionMxAdapter extends ADataLoadAdapter{

	private List<PerformanceJgReductionMx> pfmJRMs;
	
	public PerformanceJgReductionMxAdapter(){
		this.pfmJRMs = new ArrayList<PerformanceJgReductionMx>();
	}
	
//	public PerformanceJgReductionMxAdapter(List<PerformanceJgReductionMx> pfmJRMs){
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_jigoukoujian, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv0.setText(pfmJRMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmJRMs.get(position).getGzrq());
		viewHolder.tv2.setText(pfmJRMs.get(position).getGwmc());
		viewHolder.tv3.setText(pfmJRMs.get(position).getYgxm());
		viewHolder.tv4.setText(pfmJRMs.get(position).getZbid());
		viewHolder.tv5.setText(pfmJRMs.get(position).getZbmc());
		viewHolder.tv6.setText(pfmJRMs.get(position).getZbgz()+"");
		viewHolder.tv7.setText(pfmJRMs.get(position).getZbjg()+"");
		return convertView;
	}
	
	
	/* 封装类 */
	public class ViewHolder {
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
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_zzmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_tjrq);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_gwmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_ygxm);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_zbbs);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_zbmc);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_zbgz);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgkj_zbjg);
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmJRMs.addAll((List<PerformanceJgReductionMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmJRMs.clear();
	}	
	
}
