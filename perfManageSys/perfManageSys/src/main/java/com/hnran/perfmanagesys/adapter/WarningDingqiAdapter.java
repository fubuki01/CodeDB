package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleDepositExpireDetail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WarningDingqiAdapter extends ADataLoadAdapter{

	private List<SimpleDepositExpireDetail> simpleDepositExpireDetails;
	
	public WarningDingqiAdapter(){
		this.simpleDepositExpireDetails = new ArrayList<SimpleDepositExpireDetail>();
	}
	
	public WarningDingqiAdapter(List<SimpleDepositExpireDetail> simpleDepositExpireDetails){
		this.simpleDepositExpireDetails = simpleDepositExpireDetails;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleDepositExpireDetails == null ? 0 : simpleDepositExpireDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleDepositExpireDetails == null ? 0 : simpleDepositExpireDetails.get(position);
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
					R.layout.item_warning_dingqi, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleDepositExpireDetail sded = simpleDepositExpireDetails.get(position); 

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvJigou.setText(sded.getZzjc());
		viewHolder.tvKehumc.setText(sded.getKhmc());
		viewHolder.tvKaihurq.setText(sded.getKhrq());
		viewHolder.tvDaoqirq.setText(sded.getDqrq());
		viewHolder.tvCunqi.setText(sded.getCq() != null ? sded.getCq() + "" : "");
		viewHolder.tvLilv.setText(BigDecimalUtil.formatBigDecimal(sded.getLl()));
		viewHolder.tvYue.setText(sded.getYe() != null ? sded.getYe() + "" : "");
		
		return convertView;
		
	}
	
	public void setData(List<SimpleDepositExpireDetail> simpleDepositExpireDetails){  
	       this.simpleDepositExpireDetails = simpleDepositExpireDetails; 
	   } 
	
	
	/* 封装类 */
	public static class ViewHolder {
		LabelTextView tv00;

		TextView tvJigou;
		TextView tvKehumc;
		TextView tvKaihurq;
		TextView tvDaoqirq;
		TextView tvCunqi;
		TextView tvLilv;
		TextView tvYue;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tvJigou = (TextView) v.findViewById(R.id.item_warning_dingqi_jigou);
			this.tvKehumc = (TextView) v.findViewById(R.id.item_warning_dingqi_kehumc);
			this.tvKaihurq = (TextView) v.findViewById(R.id.item_warning_dingqi_kaihu);
			this.tvDaoqirq = (TextView) v.findViewById(R.id.item_warning_dingqi_daoqi);
			this.tvCunqi = (TextView) v.findViewById(R.id.item_warning_dingqi_cunqi);
			this.tvLilv = (TextView) v.findViewById(R.id.item_warning_dingqi_lilv);
			this.tvYue = (TextView) v.findViewById(R.id.item_warning_dingqi_yue);
		}
	}	
	
	
	@Override
	public void clearData(){
		simpleDepositExpireDetails.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleDepositExpireDetails.addAll((List<SimpleDepositExpireDetail>)t);
	}
	
}
