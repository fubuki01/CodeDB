package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceJgpjMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceJgpjMxAdapter extends ADataLoadAdapter{

	private List<PerformanceJgpjMx> pfmJMs;

	public PerformanceJgpjMxAdapter(){
		this.pfmJMs = new ArrayList<PerformanceJgpjMx>();
	}
	
//	public PerformanceJgpjMxAdapter(List<PerformanceJgpjMx> pfmJMs){
//		this.pfmJMs = pfmJMs;
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmJMs == null ? 0 : pfmJMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmJMs == null ? 0 : pfmJMs.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_jigoupingjun, null);
			viewHolder1 = new ViewHolder1(convertView);
			convertView.setTag(viewHolder1);
		} else {
			viewHolder1 = (ViewHolder1) convertView.getTag();
		}

		viewHolder1.tv0.setText(pfmJMs.get(position).getZzjc());
		viewHolder1.tv1.setText(pfmJMs.get(position).getGzrq());
		viewHolder1.tv2.setText(pfmJMs.get(position).getGwmc());
		viewHolder1.tv3.setText(pfmJMs.get(position).getYgxm());
		viewHolder1.tv4.setText(pfmJMs.get(position).getZzbz());
		viewHolder1.tv5.setText(pfmJMs.get(position).getZbmc());
		viewHolder1.tv6.setText(pfmJMs.get(position).getZbjg()+"");
		viewHolder1.tv7.setText(pfmJMs.get(position).getZbdj()+"");
		viewHolder1.tv8.setText(pfmJMs.get(position).getZbgz()+"");


		return convertView;

	}


	/* 封装类 */
	public class ViewHolder1 {
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv6;
		TextView tv7;
		TextView tv8;

		public ViewHolder1(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zzmc);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_tjrq);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_gwmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_ygxm);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zbbs);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zbmc);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zbjg);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zbdj);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dianzi_jgpj_zbgz);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmJMs.addAll((List<PerformanceJgpjMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmJMs.clear();
	}	



}
