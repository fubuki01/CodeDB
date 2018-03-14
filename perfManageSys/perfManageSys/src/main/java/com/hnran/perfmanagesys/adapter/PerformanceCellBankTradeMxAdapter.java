package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceCellBankTradeMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceCellBankTradeMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCellBankTradeMx> pfmCBTMs;
	
	public PerformanceCellBankTradeMxAdapter(){
		this.pfmCBTMs = new ArrayList<PerformanceCellBankTradeMx>();
	}
	
//	public PerformanceCellBankTradeMxAdapter(List<PerformanceCellBankTradeMx> pfmCBTMs){
//		this.pfmCBTMs = pfmCBTMs;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmCBTMs == null ? 0 : pfmCBTMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmCBTMs == null ? 0 : pfmCBTMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_jiaoyi_bishu, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmCBTMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmCBTMs.get(position).getMobile());
		viewHolder.tv2.setText(pfmCBTMs.get(position).getKhname());
		viewHolder.tv3.setText(pfmCBTMs.get(position).getSubtime());
		viewHolder.tv4.setText(pfmCBTMs.get(position).getDj()+"");
		viewHolder.tv5.setText(pfmCBTMs.get(position).getTranamt()+"");
		viewHolder.tv6.setText(pfmCBTMs.get(position).getGz()+"");
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
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_jgmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_khrq);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_khmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_tprq);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_dj);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_jyje);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_jiaoyi_bs_gz);
		}
	}

	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmCBTMs.addAll((List<PerformanceCellBankTradeMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmCBTMs.clear();
	}	
	
}
