package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceMx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PerfMainContentAdapter extends BaseAdapter{

	private List<PerformanceMx> list;
	private Context mCtx;
	
	
	public PerfMainContentAdapter(Context ctx, List<PerformanceMx> list){
		this.list = list;
		this.mCtx = ctx;
	}
	
	public void setData(List<PerformanceMx> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.get(position);
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
			convertView = LayoutInflater.from(mCtx).inflate(R.layout.item_perf_common_fragment, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv0.setText(list.get(position).getZbmc());
		viewHolder.tv1.setText(list.get(position).getZbgz()+"");
//		
		return convertView;
		
	}
	
	
	/* 封装类 */
	public class ViewHolder {
		TextView tv0;
		TextView tv1;
		
		public ViewHolder(View v) {
			/* 组件初始化 */
			this.tv0 = (TextView) v.findViewById(R.id.perf_tv_mc);
			this.tv1 = (TextView) v.findViewById(R.id.perf_tv_gz);
		}
	}	
	
}
