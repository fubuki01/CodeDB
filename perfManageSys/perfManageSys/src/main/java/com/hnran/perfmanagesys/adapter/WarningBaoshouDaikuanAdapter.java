package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.SimpleBSLoanExpireDetail;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WarningBaoshouDaikuanAdapter extends ADataLoadAdapter{

	private List<SimpleBSLoanExpireDetail> simpleBSLoanExpireDetails;

	public WarningBaoshouDaikuanAdapter(){
		this.simpleBSLoanExpireDetails = new ArrayList<SimpleBSLoanExpireDetail>();
	}
	
	public WarningBaoshouDaikuanAdapter(List<SimpleBSLoanExpireDetail> simpleBSLoanExpireDetails){
		this.simpleBSLoanExpireDetails = simpleBSLoanExpireDetails;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return simpleBSLoanExpireDetails == null ? 0 : simpleBSLoanExpireDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return  simpleBSLoanExpireDetails == null ? 0 : simpleBSLoanExpireDetails.get(position);
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
					R.layout.item_warning_baoshou_daikuan, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		SimpleBSLoanExpireDetail sbled = simpleBSLoanExpireDetails.get(position);
		

		viewHolder.tv00.setText(position+1 + "");
		viewHolder.tvJigou.setText(sbled.getZzjc());
		viewHolder.tvKehumc.setText(sbled.getKhmc());
		viewHolder.tvKaihurq.setText(sbled.getFfrq());
		viewHolder.tvDaoqirq.setText(sbled.getDqrq());
		viewHolder.tvJine.setText(sbled.getDkje() != null ? sbled.getDkje() + "" : "");
		viewHolder.tvLilv.setText(BigDecimalUtil.formatBigDecimal(sbled.getLl()));
		viewHolder.tvYue.setText(sbled.getDkye() != null ? sbled.getDkye() + "" : "");
		try{
			viewHolder.tvWuji.setText(CommonContent.WUJI_FENLEI[Integer.valueOf(sbled.getWjfl())]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		return convertView;

	}

	public void setData(List<SimpleBSLoanExpireDetail> simpleBSLoanExpireDetails){  
		this.simpleBSLoanExpireDetails = simpleBSLoanExpireDetails; 
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
			
			this.tvJigou = (TextView) v.findViewById(R.id.item_warning_bsdk_jigou);
			this.tvKehumc = (TextView) v.findViewById(R.id.item_warning_bsdk_kehumc);
			this.tvKaihurq = (TextView) v.findViewById(R.id.item_warning_bsdk_fafang);
			this.tvDaoqirq = (TextView) v.findViewById(R.id.item_warning_bsdk_daoqi);
			this.tvJine = (TextView) v.findViewById(R.id.item_warning_bsdk_jine);
			this.tvLilv = (TextView) v.findViewById(R.id.item_warning_bsdk_lilv);
			this.tvYue = (TextView) v.findViewById(R.id.item_warning_bsdk_yue);
			this.tvWuji = (TextView) v.findViewById(R.id.item_warning_bsdk_wuji);
		}
	}	
	
	public void clearData(){
		simpleBSLoanExpireDetails.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return simpleBSLoanExpireDetails.addAll((List<SimpleBSLoanExpireDetail>)t);
	}

}
