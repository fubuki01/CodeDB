package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceDkkhbsMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerfDkkhbsMxAdapter extends ADataLoadAdapter{

	private List<PerformanceDkkhbsMx> pfmDMs;
	private int type;
	
	public PerfDkkhbsMxAdapter(int type){
		this.pfmDMs = new ArrayList<PerformanceDkkhbsMx>();
		this.type = type;
	}
	
//	public PerfDkkhbsMxAdapter(List<PerformanceDkkhbsMx> pfmDMs, int type){
//		this.pfmDMs = pfmDMs;
//		this.type = type;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmDMs == null ? 0 : pfmDMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmDMs == null ? 0 : pfmDMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_yingxiao, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmDMs.get(position).getDkzh());
		viewHolder.tv1.setText(pfmDMs.get(position).getKhmc());
		viewHolder.tv2.setText(pfmDMs.get(position).getZzjc());
		viewHolder.tv3.setText(pfmDMs.get(position).getDkje()+"");
		viewHolder.tv4.setText(pfmDMs.get(position).getFfrq());
		viewHolder.tv5.setText(type == 1 ? pfmDMs.get(position).getBsdj()+"" : pfmDMs.get(position).getEddj()+"");
		viewHolder.tv6.setText(pfmDMs.get(position).getDqrq());
		viewHolder.tv7.setText(type == 1 ? pfmDMs.get(position).getBsgz()+"":pfmDMs.get(position).getEdgz()+"");
		viewHolder.tv8.setText(pfmDMs.get(position).getZrbl()+"");
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
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dk_yx_dkzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dk_yx_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dk_yx_jgmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dk_yx_dkje);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dk_yx_ffrq);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dk_yx_dj);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dk_yx_dqrq);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dk_yx_gz);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dk_yx_zrbl);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmDMs.addAll((List<PerformanceDkkhbsMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmDMs.clear();
		
	}	
	
}
