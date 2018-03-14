package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceAreaMakeUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerfAreaMakeUpAdapter extends ADataLoadAdapter{
	
	private final String[] XIAOYI_DANGCI = {"","一档","二挡","三挡","四挡","五档"};

	private List<PerformanceAreaMakeUp> pfmAMUs;
	
	
	public PerfAreaMakeUpAdapter(){
		this.pfmAMUs = new ArrayList<PerformanceAreaMakeUp>();
	}
//	public PerfAreaMakeUpAdapter(List<PerformanceAreaMakeUp> pfmAMUs){
//		this.pfmAMUs = pfmAMUs;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmAMUs == null ? 0 : pfmAMUs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmAMUs == null ? 0 : pfmAMUs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_diqu, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmAMUs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmAMUs.get(position).getZgts() + "");
		try{
			viewHolder.tv2.setText(XIAOYI_DANGCI[pfmAMUs.get(position).getXydc()]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		viewHolder.tv3.setText(pfmAMUs.get(position).getGz() + "");
		viewHolder.tv4.setText(pfmAMUs.get(position).getBtbz()+"");
		
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
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_diqu_zzmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_diqu_zgts);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_diqu_dybzjx);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_diqu_gz);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_diqu_btbz);
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmAMUs.addAll((List<PerformanceAreaMakeUp>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmAMUs.clear();
	}	
	
}
