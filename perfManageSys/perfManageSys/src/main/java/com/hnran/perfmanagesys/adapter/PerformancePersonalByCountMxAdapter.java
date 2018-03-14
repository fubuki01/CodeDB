package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformancePersonalByCountMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformancePersonalByCountMxAdapter extends ADataLoadAdapter{

	private List<PerformancePersonalByCountMx> pfmPBCMs;
	
	public PerformancePersonalByCountMxAdapter(){
		this.pfmPBCMs = new ArrayList<PerformancePersonalByCountMx>();
	}
	
//	public PerformancePersonalByCountMxAdapter(List<PerformancePersonalByCountMx> pfmPBCMs){
//		this.pfmPBCMs = pfmPBCMs;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmPBCMs == null ? 0 : pfmPBCMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmPBCMs == null ? 0 : pfmPBCMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_geren, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv0.setText(pfmPBCMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmPBCMs.get(position).getGzrq());
		viewHolder.tv2.setText(pfmPBCMs.get(position).getGwmc());
		viewHolder.tv3.setText(pfmPBCMs.get(position).getYgxm());
		viewHolder.tv4.setText(pfmPBCMs.get(position).getZbid()+"");
		viewHolder.tv5.setText(pfmPBCMs.get(position).getZbmc());
		viewHolder.tv6.setText(pfmPBCMs.get(position).getZbdj()+"");
		viewHolder.tv7.setText(pfmPBCMs.get(position).getZbjg()+"");
		viewHolder.tv8.setText(pfmPBCMs.get(position).getZbgz()+"");
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
		TextView tv8;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zzmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_tjrq);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_gwmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_ygxm);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zbbs);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zbmc);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zbdj);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zbjg);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dianzi_geren_zbgz);
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmPBCMs.addAll((List<PerformancePersonalByCountMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmPBCMs.clear();
	}	
	
}
