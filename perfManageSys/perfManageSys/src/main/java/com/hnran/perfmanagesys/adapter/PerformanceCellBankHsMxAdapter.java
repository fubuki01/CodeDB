package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.customer.CustomerCheckActivity;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceCellBankHsMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceCellBankHsMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCellBankHsMx> pfmCBHMs;
	
	public PerformanceCellBankHsMxAdapter(){
		this.pfmCBHMs = new ArrayList<PerformanceCellBankHsMx>();
	}
	
//	public PerformanceCellBankHsMxAdapter(List<PerformanceCellBankHsMx> pfmCBHMs){
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_dianzi_hushu, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tv0.setText(pfmCBHMs.get(position).getZzjc());
		viewHolder.tv1.setText(pfmCBHMs.get(position).getOpentime());
		viewHolder.tv2.setText(pfmCBHMs.get(position).getKhname());
		viewHolder.tv3.setText(pfmCBHMs.get(position).getStk_opentime());
		try{
			viewHolder.tv4.setText(CommonContent.YINGXIAO_LEIXING[pfmCBHMs.get(position).getYxlx()]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		viewHolder.tv5.setText(pfmCBHMs.get(position).getStk_active_time());
		
		viewHolder.tv6.setText(pfmCBHMs.get(position).getDj()+"");
		viewHolder.tv7.setText(pfmCBHMs.get(position).getGz()+"" );
		viewHolder.tv8.setText(pfmCBHMs.get(position).getStk_mobile());
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
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_khjg);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_khrq);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_khmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_tprq);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_yxlx);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_daoqi);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_dj);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_wuji);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dianzi_hs_sjhm);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmCBHMs.addAll((List<PerformanceCellBankHsMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmCBHMs.clear();
	}	
	
}
