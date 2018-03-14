package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.performance.PerformanceCunkuanCommonActivity;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.readboy.ssm.po.PerformanceCkmnlrgzMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerformanceCkmnlrgzMxAdapter extends ADataLoadAdapter{

	private List<PerformanceCkmnlrgzMx> pfmDHMs;
	private int type;

	public PerformanceCkmnlrgzMxAdapter(int type){
		this.pfmDHMs = new ArrayList<PerformanceCkmnlrgzMx>();
		this.type = type;
	}
	
	public PerformanceCkmnlrgzMxAdapter(List<PerformanceCkmnlrgzMx> pfmDHMs, int type){
		this.pfmDHMs = pfmDHMs;
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmDHMs == null ? 0 : pfmDHMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmDHMs == null ? 0 : pfmDHMs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(type == PerformanceCunkuanCommonActivity.CUNLIAN_MONI || type == PerformanceCunkuanCommonActivity.CUNLIANG_ZONGHE){
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_cl_moni, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv00.setText(position+1 + "");

			viewHolder.tv0.setText(pfmDHMs.get(position).getCkzh());
			viewHolder.tv1.setText(pfmDHMs.get(position).getZzjc());
			viewHolder.tv2.setText(pfmDHMs.get(position).getZhmc());
			viewHolder.tv3.setText(pfmDHMs.get(position).getCkye() + "");
			try{
				viewHolder.tv4.setText(CommonContent.YINGXIAO_LEIXING[pfmDHMs.get(position).getYxlx()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			viewHolder.tv5.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getLlc()));
			viewHolder.tv6.setText(pfmDHMs.get(position).getFcbl()+"");
			viewHolder.tv7.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getClmnlr()));
			viewHolder.tv8.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getQmmnlr()));
			viewHolder.tv9.setText(pfmDHMs.get(position).getCldj() + "");
			viewHolder.tv10.setText(pfmDHMs.get(position).getClgz()+"");
			viewHolder.tv11.setText(pfmDHMs.get(position).getClljgz() + "");
		}else if(type == PerformanceCunkuanCommonActivity.ZENGLIAN_MONI || type == PerformanceCunkuanCommonActivity.ZENGLIANG_ZONGHE){
			ViewHolder2 viewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_cunkuan_zl_moni, null);
				viewHolder = new ViewHolder2(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder2) convertView.getTag();
			}

			viewHolder.tv00.setText(position+1 + "");
			
			viewHolder.tv0.setText(pfmDHMs.get(position).getCkzh());
			viewHolder.tv1.setText(pfmDHMs.get(position).getZzjc());
			viewHolder.tv2.setText(pfmDHMs.get(position).getZhmc());
			viewHolder.tv3.setText(pfmDHMs.get(position).getCkye() + "");
			try{
				viewHolder.tv4.setText(CommonContent.YINGXIAO_LEIXING[pfmDHMs.get(position).getYxlx()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			viewHolder.tv5.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getLlc()));
			viewHolder.tv6.setText(pfmDHMs.get(position).getFcbl()+"");
			viewHolder.tv7.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getClmnlr()));
			viewHolder.tv8.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getQmmnlr()));
			viewHolder.tv9.setText(pfmDHMs.get(position).getZldj() + "");
			viewHolder.tv10.setText(pfmDHMs.get(position).getZlgz()+"");
			viewHolder.tv11.setText(pfmDHMs.get(position).getZlljgz() + "");
			viewHolder.tv12.setText(BigDecimalUtil.formatBigDecimal(pfmDHMs.get(position).getZlmnlr()));
		}


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
		TextView tv6;
		TextView tv7;
		TextView tv8;
		TextView tv9;
		TextView tv10;
		TextView tv11;
		TextView tv00;
		

		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ckzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_jgmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_zhmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_yxlx);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ftplc);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_fcbl);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_clmnlr);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_qmmnlr);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_dj);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_gz);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_ck_cl_ml_ljgz);
			this.tv00 = (TextView) v.findViewById(R.id.item_performance_lable);			
			
		}
	}	


	public class ViewHolder2 {
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv6;
		TextView tv7;
		TextView tv8;
		TextView tv9;
		TextView tv10;
		TextView tv11;
		TextView tv12;
		
		TextView tv00;

		public ViewHolder2(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_ckzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_jgmc);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_zhmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_yxlx);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_ftplc);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_fcbl);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_clmnlr);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_qmmnlr);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_dj);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_gz);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_ljgz);
			this.tv12 = (TextView) v.findViewById(R.id.item_performance_ck_zl_ml_zlmnlr);
			this.tv00 = (TextView) v.findViewById(R.id.item_performance_lable);			
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmDHMs.addAll((List<PerformanceCkmnlrgzMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmDHMs.clear();
	}	

}
