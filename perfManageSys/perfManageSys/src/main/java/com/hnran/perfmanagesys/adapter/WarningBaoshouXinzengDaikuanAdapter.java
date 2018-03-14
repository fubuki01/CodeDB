package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleBSNewAddBadLoanDetail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WarningBaoshouXinzengDaikuanAdapter extends ADataLoadAdapter{

	private List<SimpleBSNewAddBadLoanDetail> simpleBSNewAddBadLoanDetails;
	
	public WarningBaoshouXinzengDaikuanAdapter(){
		this.simpleBSNewAddBadLoanDetails = new ArrayList<SimpleBSNewAddBadLoanDetail>();
	}
	
	public WarningBaoshouXinzengDaikuanAdapter(List<SimpleBSNewAddBadLoanDetail> simpleBSNewAddBadLoanDetails){
		this.simpleBSNewAddBadLoanDetails = simpleBSNewAddBadLoanDetails;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleBSNewAddBadLoanDetails == null ? 0 : simpleBSNewAddBadLoanDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleBSNewAddBadLoanDetails == null ? 0 : simpleBSNewAddBadLoanDetails.get(position);
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
					R.layout.item_warning_baoshou_xinzeng_daikuan, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleBSNewAddBadLoanDetail sbnadld = simpleBSNewAddBadLoanDetails.get(position);

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvJigou.setText(sbnadld.getZzjc());
		viewHolder.tvKehumc.setText(sbnadld.getKhmc());
		viewHolder.tvKaihurq.setText(sbnadld.getFfrq());
		viewHolder.tvDaoqirq.setText(sbnadld.getDqrq());
		viewHolder.tvJine.setText(sbnadld.getDkje() != null ? sbnadld.getDkje() + "" : "");
		viewHolder.tvLilv.setText(BigDecimalUtil.formatBigDecimal(sbnadld.getLl()));
		viewHolder.tvYue.setText(sbnadld.getDkye() != null ? sbnadld.getDkye() + "" : "");
		viewHolder.tvNianchuWuji.setText(sbnadld.getNcwjfl());
		
		try{
			viewHolder.tvQimoWuji.setText(CommonContent.WUJI_FENLEI[Integer.valueOf(sbnadld.getQmwjfl())]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		return convertView;
		
	}
	
	public void setData(List<SimpleBSNewAddBadLoanDetail> simpleBSNewAddBadLoanDetails){  
	       this.simpleBSNewAddBadLoanDetails = simpleBSNewAddBadLoanDetails; 
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
		TextView tvNianchuWuji;
		TextView tvQimoWuji;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tvJigou = (TextView) v.findViewById(R.id.item_warning_bsxzdk_jigou);
			this.tvKehumc = (TextView) v.findViewById(R.id.item_warning_bsxzdk_kehumc);
			this.tvKaihurq = (TextView) v.findViewById(R.id.item_warning_bsxzdk_fafang);
			this.tvDaoqirq = (TextView) v.findViewById(R.id.item_warning_bsxzdk_daoqi);
			this.tvJine = (TextView) v.findViewById(R.id.item_warning_bsxzdk_jine);
			this.tvLilv = (TextView) v.findViewById(R.id.item_warning_bsxzdk_lilv);
			this.tvYue = (TextView) v.findViewById(R.id.item_warning_bsxzdk_yue);
			this.tvNianchuWuji = (TextView) v.findViewById(R.id.item_warning_bsxzdk_nianchu_wuji);
			this.tvQimoWuji = (TextView) v.findViewById(R.id.item_warning_bsxzdk_qimo_wuji);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleBSNewAddBadLoanDetails.addAll((List<SimpleBSNewAddBadLoanDetail>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		simpleBSNewAddBadLoanDetails.clear();
	}	
	
}
