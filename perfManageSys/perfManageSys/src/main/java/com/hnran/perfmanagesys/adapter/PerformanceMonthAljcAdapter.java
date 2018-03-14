package com.hnran.perfmanagesys.adapter;

import java.util.List;
import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceAljcgzMxCustom;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceMonthAljcAdapter extends BaseAdapter{

	private List<PerformanceAljcgzMxCustom> pfmCBTMs;
	
	public PerformanceMonthAljcAdapter(List<PerformanceAljcgzMxCustom> pfmCBTMs){
		this.pfmCBTMs = pfmCBTMs;
	}
	
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_month_aljc, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv0.setText(pfmCBTMs.get(position).getZbmc());
		viewHolder.tv1.setText(pfmCBTMs.get(position).getZbrw() + "");
		viewHolder.tv2.setText(pfmCBTMs.get(position).getZbjg() + "");
		viewHolder.tv3.setText(pfmCBTMs.get(position).getZbdj() + "");
		viewHolder.tv4.setText(pfmCBTMs.get(position).getZbdw()+ "");
		viewHolder.tv5.setText(pfmCBTMs.get(position).getZbgz()+ "");
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
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_1_1);
			this.tv1 = (TextView) v.findViewById(R.id.item_1_2);
			this.tv2 = (TextView) v.findViewById(R.id.item_2_1);
			this.tv3 = (TextView) v.findViewById(R.id.item_2_2);
			this.tv4 = (TextView) v.findViewById(R.id.item_3_1);
			this.tv5 = (TextView) v.findViewById(R.id.item_3_2);
		}
	}	
	
}
