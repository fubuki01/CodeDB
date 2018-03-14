package com.hnran.perfmanagesys.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.view.LabelTextView;
import com.readboy.ssm.po.PerformanceDkmnlrgzMx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerformanceDkmnlrgzMxAdapter extends ADataLoadAdapter{

	private List<PerformanceDkmnlrgzMx> pfmDMs;
	private int type;

	public PerformanceDkmnlrgzMxAdapter(int type){
		this.pfmDMs = new ArrayList<PerformanceDkmnlrgzMx>();
		this.type = type;
	}
	
//	public PerformanceDkmnlrgzMxAdapter(List<PerformanceDkmnlrgzMx> pfmDMs, int type){
//		this.pfmDMs = pfmDMs;
//		this.type = type;
//	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pfmDMs == null ? 0 : pfmDMs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return pfmDMs == null ? 0 : pfmDMs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if(type == 1){
			ViewHolder1 viewHolder1;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_cl_moni, null);
				viewHolder1 = new ViewHolder1(convertView);
				convertView.setTag(viewHolder1);
			} else {
				viewHolder1 = (ViewHolder1) convertView.getTag();
			}
			viewHolder1.tv00.setText(position+1 + "");

			viewHolder1.tv0.setText(pfmDMs.get(position).getDkzh());
			viewHolder1.tv1.setText(pfmDMs.get(position).getZhmc());
			viewHolder1.tv2.setText(pfmDMs.get(position).getZzjc());
			viewHolder1.tv3.setText(pfmDMs.get(position).getDkye()+"");
			viewHolder1.tv4.setText(pfmDMs.get(position).getClljgz()+"");
			viewHolder1.tv5.setText(pfmDMs.get(position).getCldj()+"");
			viewHolder1.tv6.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getLlc()));
			viewHolder1.tv7.setText(pfmDMs.get(position).getClgz()+"");
			viewHolder1.tv8.setText(pfmDMs.get(position).getFcbl()+"");
			try{
				viewHolder1.tv9.setText(CommonContent.WUJI_FENLEI[pfmDMs.get(position).getNcwjflbz()]);
				viewHolder1.tv11.setText(CommonContent.WUJI_FENLEI[pfmDMs.get(position).getWjflbz()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			viewHolder1.tv10.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getClmnlr()));
			viewHolder1.tv12.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getQmmnlr()));
		}else{
			ViewHolder2 viewHolder2;
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_daikuan_zl_moni, null);
				viewHolder2 = new ViewHolder2(convertView);
				convertView.setTag(viewHolder2);
			} else {
				viewHolder2 = (ViewHolder2) convertView.getTag();
			}

			viewHolder2.tv00.setText(position+1 + "");
			viewHolder2.tv0.setText(pfmDMs.get(position).getDkzh());
			viewHolder2.tv1.setText(pfmDMs.get(position).getZhmc());
			viewHolder2.tv2.setText(pfmDMs.get(position).getZzjc());
			viewHolder2.tv3.setText(pfmDMs.get(position).getDkye()+"");
			viewHolder2.tv4.setText(pfmDMs.get(position).getZlljgz()+"");
			viewHolder2.tv5.setText(pfmDMs.get(position).getZldj()+"");
			viewHolder2.tv6.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getLlc()));
			viewHolder2.tv7.setText(pfmDMs.get(position).getZlgz()+"");
			viewHolder2.tv8.setText(pfmDMs.get(position).getFcbl()+"");
			viewHolder2.tv10.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getClmnlr()));
			viewHolder2.tv12.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getQmmnlr()));
			viewHolder2.tv13.setText(BigDecimalUtil.formatBigDecimal(pfmDMs.get(position).getZlmnlr()));
		
			try{
				viewHolder2.tv9.setText(CommonContent.WUJI_FENLEI[pfmDMs.get(position).getNcwjflbz()]);
				viewHolder2.tv11.setText(CommonContent.WUJI_FENLEI[pfmDMs.get(position).getWjflbz()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}


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
		TextView tv6;
		TextView tv7, tv8, tv9, tv10, tv11, tv12 ;

		public ViewHolder1(View v) {
			/* 组件初始化 */
			
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_dkzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_lx);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_jgmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_ljgz);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_dj);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_ftplc);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_gz);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_fcbl);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_clwjfl);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_clmnlr);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_qmwjfl);
			this.tv12 = (TextView) v.findViewById(R.id.item_performance_dk_cl_ml_qmmnlr);
		}
	}	

	public class ViewHolder2 {
		LabelTextView tv00;
		TextView tv0;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv6;
		TextView tv7, tv8, tv9, tv10, tv11, tv12, tv13 ;

		public ViewHolder2(View v) {
			/* 组件初始化 */
			
			this.tv00 = (LabelTextView) v.findViewById(R.id.item_performance_lable);
			
			this.tv0 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_dkzh);
			this.tv1 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_lx);
			this.tv2 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_jgmc);
			this.tv3 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_ye);
			this.tv4 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_ljgz);
			this.tv5 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_dj);
			this.tv6 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_ftplc);
			this.tv7 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_gz);
			this.tv8 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_fcbl);
			this.tv9 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_clwjfl);
			this.tv10 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_clmnlr);
			this.tv11 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_qmwjfl);
			this.tv12 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_qmmnlr);
			this.tv13 = (TextView) v.findViewById(R.id.item_performance_dk_zl_ml_zlmnlr);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean addData(List<?> t) {
		// TODO Auto-generated method stub
		return pfmDMs.addAll((List<PerformanceDkmnlrgzMx>)t);
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		pfmDMs.clear();
	}	

}
