package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleBSOverdueLoanDetail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WarningYuqiDaikuanAdapter extends ADataLoadAdapter{

	private List<SimpleBSOverdueLoanDetail> simpleBSOverdueLoanDetails;
	
	public WarningYuqiDaikuanAdapter(){
		this.simpleBSOverdueLoanDetails = new ArrayList<SimpleBSOverdueLoanDetail>();
	}
	
	public WarningYuqiDaikuanAdapter(List<SimpleBSOverdueLoanDetail> simpleBSOverdueLoanDetails){
		this.simpleBSOverdueLoanDetails = simpleBSOverdueLoanDetails;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleBSOverdueLoanDetails == null ? 0 : simpleBSOverdueLoanDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleBSOverdueLoanDetails == null ? 0 : simpleBSOverdueLoanDetails.get(position);
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
					R.layout.item_warning_yuqi_daikuan, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleBSOverdueLoanDetail sbld = simpleBSOverdueLoanDetails.get(position);
		
		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvJigou.setText(sbld.getZzjc());
		viewHolder.tvKehumc.setText(sbld.getKhmc());
		viewHolder.tvKaihurq.setText(sbld.getFfrq());
		viewHolder.tvDaoqirq.setText(sbld.getDqrq());
		viewHolder.tvJine.setText(sbld.getDkje() != null ? sbld.getDkje()+"" : "");
		viewHolder.tvLilv.setText(BigDecimalUtil.formatBigDecimal(sbld.getLl())); 
		viewHolder.tvYue.setText(sbld.getDkye() != null ? sbld.getDkye()+"" : "");
		
		try{
			viewHolder.tvWuji.setText(CommonContent.WUJI_FENLEI[Integer.valueOf(sbld.getWjfl())]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		return convertView;
		
	}
	
	public void setData(List<SimpleBSOverdueLoanDetail> simpleBSOverdueLoanDetails){  
	       this.simpleBSOverdueLoanDetails = simpleBSOverdueLoanDetails; 
	   } 
	
	
	/* 封装类 */
	public static class ViewHolder {
		LabelTextView tv00;
		
		TextView tvJigou;
		TextView tvKehumc;
		TextView tvKaihurq;
		TextView tvDaoqirq;
		TextView tvJine;
		TextView tvLilv;
		TextView tvYue;
		TextView tvWuji;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);			
			
			this.tvJigou = (TextView) v.findViewById(R.id.item_warning_yqdk_jigou);
			this.tvKehumc = (TextView) v.findViewById(R.id.item_warning_yqdk_kehumc);
			this.tvKaihurq = (TextView) v.findViewById(R.id.item_warning_yqdk_fafang);
			this.tvDaoqirq = (TextView) v.findViewById(R.id.item_warning_yqdk_daoqi);
			this.tvJine = (TextView) v.findViewById(R.id.item_warning_yqdk_jine);
			this.tvLilv = (TextView) v.findViewById(R.id.item_warning_yqdk_lilv);
			this.tvYue = (TextView) v.findViewById(R.id.item_warning_yqdk_yue);
			this.tvWuji = (TextView) v.findViewById(R.id.item_warning_yqdk_wuji);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleBSOverdueLoanDetails.addAll((List<SimpleBSOverdueLoanDetail>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		simpleBSOverdueLoanDetails.clear();
	}	
	
}
