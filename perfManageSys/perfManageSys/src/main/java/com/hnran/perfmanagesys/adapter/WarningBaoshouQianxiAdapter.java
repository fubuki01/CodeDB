package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleBSLoanOweInterestDetail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WarningBaoshouQianxiAdapter extends ADataLoadAdapter{

	private List<SimpleBSLoanOweInterestDetail> simpleBSLoanOweInterestDetails;
	
	public WarningBaoshouQianxiAdapter(){
		this.simpleBSLoanOweInterestDetails = new ArrayList<SimpleBSLoanOweInterestDetail>();
	}
	
	public WarningBaoshouQianxiAdapter(List<SimpleBSLoanOweInterestDetail> simpleBSLoanOweInterestDetails){
		this.simpleBSLoanOweInterestDetails = simpleBSLoanOweInterestDetails;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleBSLoanOweInterestDetails == null ? 0 : simpleBSLoanOweInterestDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleBSLoanOweInterestDetails == null ? 0 : simpleBSLoanOweInterestDetails.get(position);
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
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_warning_daikuan_qianxi, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleBSLoanOweInterestDetail sbloid = simpleBSLoanOweInterestDetails.get(position);

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvJigou.setText(sbloid.getZzjc());
		viewHolder.tvKehumc.setText(sbloid.getKhmc());
		viewHolder.tvKaihurq.setText(sbloid.getQxr());
		viewHolder.tvDaoqirq.setText(sbloid.getJxr());
		viewHolder.tvCunqi.setText(sbloid.getQx()+"");
		
		
		return convertView;
		
	}
	
	public void setData(List<SimpleBSLoanOweInterestDetail> simpleBSLoanOweInterestDetails){  
	       this.simpleBSLoanOweInterestDetails = simpleBSLoanOweInterestDetails; 
	   } 
	
	
	/* 封装类 */
	public static class ViewHolder {
		LabelTextView tv00;

		TextView tvJigou;
		TextView tvKehumc;
		TextView tvKaihurq;
		TextView tvDaoqirq;
		TextView tvCunqi;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tvJigou = (TextView) v.findViewById(R.id.item_warning_dkqx_jigou);
			this.tvKehumc = (TextView) v.findViewById(R.id.item_warning_dkqx_kehumc);
			this.tvKaihurq = (TextView) v.findViewById(R.id.item_warning_dkqx_qixiri);
			this.tvDaoqirq = (TextView) v.findViewById(R.id.item_warning_dkqx_jiexiri);
			this.tvCunqi = (TextView) v.findViewById(R.id.item_warning_dkqx_qianxi);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleBSLoanOweInterestDetails.addAll((List<SimpleBSLoanOweInterestDetail>) t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		simpleBSLoanOweInterestDetails.clear();
	}	
	
}
