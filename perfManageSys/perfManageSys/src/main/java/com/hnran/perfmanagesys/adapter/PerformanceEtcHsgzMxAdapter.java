package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceEtcHsgzMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceEtcHsgzMxAdapter extends ADataLoadAdapter{

	private List<PerformanceEtcHsgzMx> pfmEHMs;
	
	public PerformanceEtcHsgzMxAdapter(){
		this.pfmEHMs = new ArrayList<PerformanceEtcHsgzMx>();
	}
	
//	public PerformanceEtcHsgzMxAdapter(List<PerformanceEtcHsgzMx> pfmEHMs){
//		this.pfmEHMs = pfmEHMs;
//	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmEHMs == null ? 0 : pfmEHMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmEHMs == null ? 0 : pfmEHMs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	
			ViewHolder1 viewHolder1;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_etc, null);
				viewHolder1 = new ViewHolder1(convertView);
				convertView.setTag(viewHolder1);
			} else {
				viewHolder1 = (ViewHolder1) convertView.getTag();
			}

			viewHolder1.tv00.setText(position+1 + "");
			viewHolder1.tv0.setText(pfmEHMs.get(position).getXtkkh());
			viewHolder1.tv1.setText(pfmEHMs.get(position).getKhmc());
			viewHolder1.tv2.setText(pfmEHMs.get(position).getTjrq());
			viewHolder1.tv3.setText(pfmEHMs.get(position).getDj()+"");
			viewHolder1.tv4.setText(pfmEHMs.get(position).getDfje()+"");
			viewHolder1.tv5.setText(pfmEHMs.get(position).getGz()+"");
		
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
		TextView tv5;
		public ViewHolder1(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_xtkkh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_khmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_bdrq);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_dj);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_dfje);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dianzi_etc_gz);
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmEHMs.addAll((List<PerformanceEtcHsgzMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmEHMs.clear();
	}	
	
}
