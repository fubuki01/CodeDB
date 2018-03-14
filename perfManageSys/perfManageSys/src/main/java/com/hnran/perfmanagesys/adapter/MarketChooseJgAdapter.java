package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.readboy.ssm.po.SimpleOrganization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 营销申报添加页面，选择机构名称
 * @author tan
 *
 */

public class MarketChooseJgAdapter extends RecyclerView.Adapter<MarketChooseJgAdapter.MyViewHolder>
{

	private Context mCnt;
	private List<SimpleOrganization> mDatas;

	private int current;
	public void setCurrent(int current) {
		this.current = current;
	}

	private Drawable defaultDrawable, choosedDrawable;


	public MarketChooseJgAdapter(Context cnt, List<SimpleOrganization> datas){
		this.mCnt = cnt;
		this.mDatas = datas;
		current = -1;
		choosedDrawable = mCnt.getResources().getDrawable(R.drawable.radiobutton_background_checked);
		defaultDrawable = mCnt.getResources().getDrawable(R.drawable.radiobutton_background_unchecked);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
				mCnt).inflate(R.layout.item_market_choose_jg, parent,
						false));
		return holder;
	}

	@SuppressLint("NewApi")
	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		holder.tv.setText(mDatas.get(position).getZzjc());

		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//                    int pos = holder.getLayoutPosition();
					current = position;
					mOnItemClickLitener.onItemClick(holder.itemView, position);
				}
			});
		}


		if(position == current){
			holder.tv.setBackground(choosedDrawable);
		}else{
			holder.tv.setBackground(defaultDrawable);
		}

	}

	@Override
	public int getItemCount()
	{
		return mDatas != null ? mDatas.size() : 0;
	}

	class MyViewHolder extends ViewHolder
	{

		TextView tv;

		public MyViewHolder(View view)
		{
			super(view);
			tv = (TextView) view.findViewById(R.id.market_choose_jg_tv);
		}
	}

	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	
	public void setData(List<SimpleOrganization> datas){
		this.mDatas = datas;
	}
}