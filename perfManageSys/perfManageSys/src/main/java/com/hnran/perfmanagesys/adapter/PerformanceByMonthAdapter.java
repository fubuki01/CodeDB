package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.PerformanceFindByMonthHome;
import com.readboy.ssm.po.PerformanceGwjxgzMxCustom;
import com.readboy.ssm.po.PerformanceGzhz;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author tan
 *
 */

public class PerformanceByMonthAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private PerformanceGzhz performanceGzhz;
	private List<PerformanceGwjxgzMxCustom> performanceGwjxgzMxCustoms;

	public static enum ITEM_TYPE {  
		ITEM_TYPE_Head,  
		ITEM_TYPE_Content 
	}

	public PerformanceByMonthAdapter(PerformanceFindByMonthHome performanceFindByMonthHome){
		super();
		setData(performanceFindByMonthHome);
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return (performanceGwjxgzMxCustoms != null ? performanceGwjxgzMxCustoms.size() : 0) + 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub

		if (holder instanceof ContentViewHolder){  
			try{
				PerformanceGwjxgzMxCustom performanceGwjxgzMxCustom = performanceGwjxgzMxCustoms.get(position -1); 
				String str = "元";
				((ContentViewHolder)holder).tvZuzhi.setText(performanceGwjxgzMxCustom.getZzjc());
				((ContentViewHolder)holder).tvGangwei.setText(performanceGwjxgzMxCustom.getGwmc());
				((ContentViewHolder)holder).tvHeji.setText(performanceGwjxgzMxCustom.getGzhj() + str);
				((ContentViewHolder)holder).tvJiben.setText(performanceGwjxgzMxCustom.getJbjxgz() + str); 
				((ContentViewHolder)holder).tvTiaozeng.setText(performanceGwjxgzMxCustom.getTzgz() + str); 
				((ContentViewHolder)holder).tvKoujian.setText(performanceGwjxgzMxCustom.getKjgz() + str); 
				((ContentViewHolder)holder).tvPhjfk.setText(performanceGwjxgzMxCustom.getPhjfkgz() + str); 
				((ContentViewHolder)holder).tvGxl.setText(performanceGwjxgzMxCustom.getGxlgz() + str);
				((ContentViewHolder)holder).tvAljc.setText(performanceGwjxgzMxCustom.getAljcgz() + str);
			
				((ContentViewHolder)holder).rlPhjfk.setTag(position-1);
				((ContentViewHolder)holder).rlGxl.setTag(position-1);
				((ContentViewHolder)holder).rlAljc.setTag(position-1);
			
			}catch(Exception e){
				e.printStackTrace();
			}

		}else if (holder instanceof HeadViewHolder){  
			if(performanceGzhz != null){
				((HeadViewHolder)holder).tvRank.setText(performanceGzhz.getQhpm() + "");
				((HeadViewHolder)holder).tvTotal.setText(performanceGzhz.getGzhj() + "");
			}else{
				((HeadViewHolder)holder).tvRank.setText("0");
				((HeadViewHolder)holder).tvTotal.setText("0");
			}
		}  
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		if (viewType == ITEM_TYPE.ITEM_TYPE_Head.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_month_head,parent,false);  

			return new HeadViewHolder(view);  

		}else if(viewType == ITEM_TYPE.ITEM_TYPE_Content.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_performance_month_content,parent,false);  
			return new ContentViewHolder(view);  

		}  
		return null;
	}


	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return position == 0 ? ITEM_TYPE.ITEM_TYPE_Head.ordinal() : ITEM_TYPE.ITEM_TYPE_Content.ordinal();
	}

	public class ContentViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvZuzhi, tvGangwei, tvHeji, tvJiben, tvTiaozeng,
		tvKoujian, tvPhjfk, tvGxl, tvAljc;

		private RelativeLayout rlPhjfk, rlGxl, rlAljc;

		public ContentViewHolder(View view)
		{
			super(view);
			
			tvZuzhi = (TextView) view.findViewById(R.id.performance_month_tv_bumen);
			tvGangwei = (TextView) view.findViewById(R.id.performance_month_tv_gangwei);
			tvHeji = (TextView) view.findViewById(R.id.performance_month_tv_heji);
			tvJiben = (TextView) view.findViewById(R.id.performance_month_tv_jiben);
			tvTiaozeng = (TextView) view.findViewById(R.id.performance_month_tv_tiaozeng);
			tvKoujian = (TextView) view.findViewById(R.id.performance_month_tv_koujian);
			tvPhjfk = (TextView) view.findViewById(R.id.performance_month_tv_phjfk);
			tvGxl = (TextView) view.findViewById(R.id.performance_month_tv_gxl);
			tvAljc = (TextView) view.findViewById(R.id.performance_month_tv_aljc);

			rlPhjfk = (RelativeLayout) view.findViewById(R.id.performance_month_rl_phjfk_title);
			rlPhjfk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(layoutClickListener != null) 
						layoutClickListener.onClick(R.id.performance_month_rl_phjfk_title, (int)rlPhjfk.getTag()); 
				}
			});

			rlGxl = (RelativeLayout) view.findViewById(R.id.performance_month_rl_gxl_title);
			rlGxl.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(layoutClickListener != null) 
						layoutClickListener.onClick(R.id.performance_month_rl_gxl_title, (int)rlPhjfk.getTag()); 
				}
			});

			rlAljc = (RelativeLayout) view.findViewById(R.id.performance_month_rl_aljc_title);
			rlAljc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(layoutClickListener != null) 
						layoutClickListener.onClick(R.id.performance_month_rl_aljc_title, (int)rlPhjfk.getTag()); 
				}
			});

		}
	}


	public class HeadViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvRank, tvTotal;
		public HeadViewHolder(View view)
		{
			super(view);
			tvRank = (TextView) view.findViewById(R.id.performance_month_tv_rank);
			tvTotal = (TextView) view.findViewById(R.id.performance_month_tv_total);
		}
	}
	
	public void setData(PerformanceFindByMonthHome performanceFindByMonthHome){
		if(performanceFindByMonthHome != null){
			this.performanceGzhz = performanceFindByMonthHome.getPerformanceGzhz();
			this.performanceGwjxgzMxCustoms = performanceFindByMonthHome.getPerformanceGwjxgzMxCustomList();
		}else{
			this.performanceGzhz = null;
			this.performanceGwjxgzMxCustoms = null;
		}
	}
	

	/**
	 * 
	 * @author Administrator
	 *
	 */
	public interface LayoutClickListener{
		public void onClick(int positionId, int index);
	};

	private LayoutClickListener layoutClickListener;

	public void setLayoutClickListener(LayoutClickListener layoutClickListener){
		this.layoutClickListener = layoutClickListener;
	}


}