package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.performance.PerformanceYewuActivity;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceBusinessDetail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerfBusinessDetailAdapter extends ADataLoadAdapter{

	private List<PerformanceBusinessDetail> pfmBDs;

	private int type;

//	public PerfBusinessDetailAdapter(List<PerformanceBusinessDetail> pfmBDs, int type){
//		this.pfmBDs = pfmBDs;
//		this.type = type;
//	}
	public PerfBusinessDetailAdapter(int type){
		this.pfmBDs = new ArrayList<PerformanceBusinessDetail>();
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmBDs == null ? 0 : pfmBDs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmBDs == null ? 0 : pfmBDs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if(type == PerformanceYewuActivity.XIANJIN){
			ViewHolder1 viewHolder1;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_yewu_xianjin, null);
				viewHolder1 = new ViewHolder1(convertView);
				convertView.setTag(viewHolder1);
			} else {
				viewHolder1 = (ViewHolder1) convertView.getTag();
			}
			viewHolder1.tv00.setText(position+1 + "");
			viewHolder1.tv0.setText(pfmBDs.get(position).getYwjgmc());
			viewHolder1.tv1.setText(pfmBDs.get(position).getYwlx());
			viewHolder1.tv2.setText(pfmBDs.get(position).getXjllgz()+"");
			viewHolder1.tv3.setText(pfmBDs.get(position).getXjjyje()+"");
			viewHolder1.tv4.setText(pfmBDs.get(position).getTjrq());

		}
		
		else if(type == PerformanceYewuActivity.YEWULIANG){
			ViewHolder2 viewHolder2;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_yewu, null);
				viewHolder2 = new ViewHolder2(convertView);
				convertView.setTag(viewHolder2);}
			else {
				viewHolder2 = (ViewHolder2) convertView.getTag();
			}
			viewHolder2.tv00.setText(position+1 + "");
			viewHolder2.tv0.setText(pfmBDs.get(position).getYwjgmc());
			viewHolder2.tv1.setText(pfmBDs.get(position).getYwlx());
			viewHolder2.tv2.setText(pfmBDs.get(position).getJybs()+"");
			viewHolder2.tv3.setText(pfmBDs.get(position).getYwlgz()+"");
			viewHolder2.tv4.setText(pfmBDs.get(position).getSjjybs()+"");
			viewHolder2.tv5.setText(pfmBDs.get(position).getTjrq());
		}

		return convertView;

	}


	/* 封装类 */
	public class ViewHolder1 {
		LabelTextView tv00;
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;

		public ViewHolder1(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_yewu_xianjin_ywjg);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_yewu_xianjin_ywlx);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_yewu_xianjin_jj);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_yewu_xianjin_xjjyje);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_yewu_xianjin_jyrq);
		}
	}	

	public class ViewHolder2 {
		TextView tv00;
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;

		public ViewHolder2(View v) {
			/* 组件初始化 */
			this.tv00 = (TextView) v.findViewById(R.id.item_performance_lable);
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_yewu_ywjg);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_yewu_ywlx);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_yewu_sjbs);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_yewu_jj);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_yewu_zsbs);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_yewu_jyrq);
		}
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmBDs.addAll((List<PerformanceBusinessDetail>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmBDs.clear();
	}	

}
