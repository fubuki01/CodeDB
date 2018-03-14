package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleBSLNFFExpireRecoveryRate;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WarningNianShouhuilvAdapter extends ADataLoadAdapter{

	private List<SimpleBSLNFFExpireRecoveryRate> simpleBSLNFFExpireRecoveryRates;
	
	public WarningNianShouhuilvAdapter(){
		this.simpleBSLNFFExpireRecoveryRates = new ArrayList<SimpleBSLNFFExpireRecoveryRate>();
	}
	
	public WarningNianShouhuilvAdapter(List<SimpleBSLNFFExpireRecoveryRate> simpleBSLNFFExpireRecoveryRates){
		this.simpleBSLNFFExpireRecoveryRates = simpleBSLNFFExpireRecoveryRates;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleBSLNFFExpireRecoveryRates == null ? 0 : simpleBSLNFFExpireRecoveryRates.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleBSLNFFExpireRecoveryRates == null ? 0 : simpleBSLNFFExpireRecoveryRates.get(position);
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
					R.layout.item_warning_linian_shouhuilv, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleBSLNFFExpireRecoveryRate sberr = simpleBSLNFFExpireRecoveryRates.get(position);
		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvNiandu.setText(sberr.getNd());
		viewHolder.tvHuishoulv.setText(BigDecimalUtil.formatBigDecimalTwo(sberr.getHsl())+"%");
		viewHolder.tvDaoqi.setText(sberr.getDqje() != null ? sberr.getDqje()+"" : "");
		viewHolder.tvQimo.setText(sberr.getQmje() != null ? sberr.getQmje()+"" : "");
		
		return convertView;
		
	}
	
	public void setData(List<SimpleBSLNFFExpireRecoveryRate> simpleBSLNFFExpireRecoveryRates){  
	       this.simpleBSLNFFExpireRecoveryRates = simpleBSLNFFExpireRecoveryRates; 
	   } 
	
	
	/* 封装类 */
	public static class ViewHolder {
		LabelTextView tv00;
		
		TextView tvNiandu;
		TextView tvHuishoulv;
		TextView tvDaoqi;
		TextView tvQimo;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);			
			
			this.tvNiandu = (TextView) v.findViewById(R.id.item_warning_lnshl_niandu);
			this.tvHuishoulv = (TextView) v.findViewById(R.id.item_warning_lnshl_huishoulv);
			this.tvDaoqi = (TextView) v.findViewById(R.id.item_warning_lnshl_daoqi);
			this.tvQimo = (TextView) v.findViewById(R.id.item_warning_lnshl_qimo);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleBSLNFFExpireRecoveryRates.addAll((List<SimpleBSLNFFExpireRecoveryRate>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		simpleBSLNFFExpireRecoveryRates.clear();
	}	
	
}
