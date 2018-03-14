package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceBlqsMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceBlqsMxAdapter extends ADataLoadAdapter{

	private final String[] SHOUHUI_LEIXING = {"","本金","利息"};

	private List<PerformanceBlqsMx> pfmBMs;

	public PerformanceBlqsMxAdapter(){
		this.pfmBMs = new ArrayList<PerformanceBlqsMx>();
	}
	
//	public PerformanceBlqsMxAdapter(List<PerformanceBlqsMx> pfmBMs){
//		this.pfmBMs = pfmBMs;
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmBMs == null ? 0 : pfmBMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmBMs == null ? 0 : pfmBMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_biaowai, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv00.setText(position+1 + "");

		viewHolder.tv0.setText(pfmBMs.get(position).getDkzh());
		viewHolder.tv1.setText(pfmBMs.get(position).getKhmc());
		viewHolder.tv2.setText(pfmBMs.get(position).getJgmc());
		try{
			viewHolder.tv3.setText(SHOUHUI_LEIXING[pfmBMs.get(position).getShlx()]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		viewHolder.tv4.setText(pfmBMs.get(position).getShrq());
		viewHolder.tv5.setText(pfmBMs.get(position).getShje()+"");
		viewHolder.tv6.setText(pfmBMs.get(position).getZbdj()+"");
		viewHolder.tv7.setText(pfmBMs.get(position).getZbgz()+"");
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
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dk_bw_dkzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dk_bw_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dk_bw_qsjg);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dk_bw_shlx);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dk_bw_shrq);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dk_bw_shje);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dk_bw_dj);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dk_bw_gz);
		
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmBMs.addAll((List<PerformanceBlqsMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmBMs.clear();
	}	

}
