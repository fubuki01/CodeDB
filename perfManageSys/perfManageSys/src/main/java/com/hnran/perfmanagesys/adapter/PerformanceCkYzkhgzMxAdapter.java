package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceCkYzkhgzMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformanceCkYzkhgzMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCkYzkhgzMx> pfmCBHMs;
	
	public PerformanceCkYzkhgzMxAdapter(){
		this.pfmCBHMs = new ArrayList<PerformanceCkYzkhgzMx>();
	}
	
//	public PerformanceCkYzkhgzMxAdapter(List<PerformanceCkYzkhgzMx> pfmCBHMs){
//		this.pfmCBHMs = pfmCBHMs;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmCBHMs == null ? 0 : pfmCBHMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmCBHMs == null ? 0 : pfmCBHMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_yzkhgz, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		
		viewHolder.tv0.setText(pfmCBHMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmCBHMs.get(position).getKhbh());
		viewHolder.tv2.setText(pfmCBHMs.get(position).getKhmc());
		viewHolder.tv3.setText(pfmCBHMs.get(position).getQmye()+"");
		viewHolder.tv4.setText(pfmCBHMs.get(position).getZjhm());
		viewHolder.tv5.setText(pfmCBHMs.get(position).getClyrp()+"");
		viewHolder.tv6.setText(pfmCBHMs.get(position).getQmyrp()+"");
		viewHolder.tv7.setText(pfmCBHMs.get(position).getClnrp()+"" );
		viewHolder.tv8.setText(pfmCBHMs.get(position).getQmnrp()+"" );
		viewHolder.tv9.setText(pfmCBHMs.get(position).getYzkhdj()+"" );
		viewHolder.tv10.setText(pfmCBHMs.get(position).getYzkhgz()+"" );
		viewHolder.tv11.setText(pfmCBHMs.get(position).getYzkhljgz()+"" );
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
		TextView tv8;
		TextView tv9;
		TextView tv10;
		TextView tv11;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_jgmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_khbh);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_khmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_qmye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_zjhm);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_clyrp);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_qmyrp);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_clnrp);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_qmnrp);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_dj);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_gz);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_yzkhgz_ljgz);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmCBHMs.addAll((List<PerformanceCkYzkhgzMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmCBHMs.clear();
		
	}	
	
}
