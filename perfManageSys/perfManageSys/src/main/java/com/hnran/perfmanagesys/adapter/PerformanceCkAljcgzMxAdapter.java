package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;
import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceCkAljcgzMx;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformanceCkAljcgzMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCkAljcgzMx> pfmDHMs;

	//存量、增量类型
	private int type;
	
	public PerformanceCkAljcgzMxAdapter(int type){
		this.pfmDHMs = new ArrayList<PerformanceCkAljcgzMx>();
		this.type = type;
	}

//	public PerformanceCkAljcgzMxAdapter(List<PerformanceCkAljcgzMx> pfmDHMs, int type){
//		this.pfmDHMs = pfmDHMs;
//		this.type = type;
//	}

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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(type == 1){
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_cl_yrp_yue, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv00.setText(position+1 + "");

			viewHolder.tv0.setText(pfmDHMs.get(position).getCkzh());
			viewHolder.tv1.setText(pfmDHMs.get(position).getZzjc());
			viewHolder.tv2.setText(pfmDHMs.get(position).getZhmc());
			viewHolder.tv3.setText(pfmDHMs.get(position).getClyrp() + "");
			viewHolder.tv4.setText(pfmDHMs.get(position).getQmyrp() + "");
			viewHolder.tv9.setText(pfmDHMs.get(position).getClyrpdj() + "");
			viewHolder.tv10.setText(pfmDHMs.get(position).getClyrpgz()+"");
			viewHolder.tv11.setText(pfmDHMs.get(position).getClyrpljgz() + "");
		}else if(type == 2){
			ViewHolder2 viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_zl_yrp_yue, null);
				viewHolder = new ViewHolder2(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder2) convertView.getTag();
			}

			viewHolder.tv00.setText(position+1 + "");
			
			viewHolder.tv0.setText(pfmDHMs.get(position).getCkzh());
			viewHolder.tv1.setText(pfmDHMs.get(position).getZzjc());
			viewHolder.tv2.setText(pfmDHMs.get(position).getZhmc());
			viewHolder.tv3.setText(pfmDHMs.get(position).getClyrp() + "");
			viewHolder.tv4.setText(pfmDHMs.get(position).getQmyrp() + "");
			viewHolder.tv5.setText(pfmDHMs.get(position).getZlyrp() + "");
			viewHolder.tv9.setText(pfmDHMs.get(position).getZlyrpdj() + "");
			viewHolder.tv10.setText(pfmDHMs.get(position).getZlyrpgz()+"");
			viewHolder.tv11.setText(pfmDHMs.get(position).getZlyrpljgz() + "");
		
			
			
		}
		return convertView;

	}


	/* 封装类 */
	public class ViewHolder {
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv9;
		TextView tv10;
		TextView tv11;
		
		LabelTextView tv00;

		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ckzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_jgmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_zhmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_yxlx);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_dj);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_gz);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ljgz);
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
		}
	}
	
	public class ViewHolder2 {
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv9;
		TextView tv10;
		TextView tv11;
		TextView tv12;
		
		LabelTextView tv00;

		public ViewHolder2(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ckzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_jgmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_zhmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_yxlx);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_y);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_dj);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_gz);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ljgz);

			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmDHMs.addAll((List<PerformanceCkAljcgzMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmDHMs.clear();
	}	

}
