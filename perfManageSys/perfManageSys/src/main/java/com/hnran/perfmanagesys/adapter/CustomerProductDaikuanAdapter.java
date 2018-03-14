package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.hnran.perfmanagesys.utils.DensityUtil;
import com.readboy.ssm.po.LoanCustomerFamily;
import com.readboy.ssm.po.LoanCustomerPerson;
import com.readboy.ssm.po.LoanCustomerProduct;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tan
 *
 */

public class CustomerProductDaikuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static enum ITEM_TYPE {  
		ITEM_TYPE_Product,  
		ITEM_TYPE_Person,
		ITEM_TYPE_Hth
	}

	private Context mCnt;

	private List<LoanCustomerFamily> loanCustomerFamilys;

	public CustomerProductDaikuanAdapter(Context cnt, List<LoanCustomerFamily> loanCustomerFamilys){
		super();
		this.mCnt = cnt;
		this.loanCustomerFamilys = loanCustomerFamilys;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub

		return loanCustomerFamilys != null ? loanCustomerFamilys.size() * 3 : 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		if(loanCustomerFamilys == null ){
			return ;
		}

		position = position / 3;

		if (holder instanceof ProductViewHolder){  

			LoanCustomerProduct lcp = loanCustomerFamilys.get(position).getLoanCustomerProduct();
			((ProductViewHolder)holder).tvDaikuan_mingchen.setText(lcp.getCpmc());
			((ProductViewHolder)holder).tvDaikuan_kaihujigou.setText(lcp.getZzjc());
			((ProductViewHolder)holder).tvDaikuan_lilv.setText(BigDecimalUtil.formatBigDecimal(lcp.getLl()));
			((ProductViewHolder)holder).tvDaikuan_fafangriqi.setText(lcp.getFfrq()); 
			((ProductViewHolder)holder).tvDaikuan_daoqiriqi.setText(lcp.getDqrq());
			((ProductViewHolder)holder).tvDaikuan_jine.setText(lcp.getDkje() + ""); 
			((ProductViewHolder)holder).tvDaikuan_leixing.setText(lcp.getYxbl()+""); 
			((ProductViewHolder)holder).tvDaikuan_jingli.setText(lcp.getKhjlmc()); 
			((ProductViewHolder)holder).tvDaikuan_yue.setText(lcp.getDkye()+""); 
			try{
				((ProductViewHolder)holder).tvDaikuan_fenlei.setText(CommonContent.WUJI_FENLEI[lcp.getFive_class_type()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}else if (holder instanceof PersonListViewHolder){  

			List<LoanCustomerPerson> loanCustomerPersons = loanCustomerFamilys.get(position).getLoanCustomerPerson();

			ViewGroup.LayoutParams layoutParams = ((PersonListViewHolder)holder).mRecyclerView.getLayoutParams();

			float itemHeight = mCnt.getResources().getDimension(R.dimen.layout_choose_height);

			layoutParams.height = (int) (loanCustomerPersons.size() * itemHeight) + DensityUtil.dip2px(mCnt, 10);

			((PersonListViewHolder)holder).mRecyclerView.setLayoutParams(layoutParams);

			((PersonListViewHolder)holder).mRecyclerView.setLayoutManager(new LinearLayoutManager(mCnt));
			((PersonListViewHolder)holder).mRecyclerView.setAdapter(new CustomerProductDaikuanPersonAdapter(loanCustomerPersons));


		}else if (holder instanceof ProductHthViewHolder){  
			((ProductHthViewHolder)holder).tvHth.setText(loanCustomerFamilys.get(position).getHth());
		}    

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		if (viewType == ITEM_TYPE.ITEM_TYPE_Product.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_daikuan,parent,false);  

			return new ProductViewHolder(view);  

		}else if(viewType == ITEM_TYPE.ITEM_TYPE_Person.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_daikuan_recycleview,parent,false);  
			return new PersonListViewHolder(view);  

		}else if(viewType == ITEM_TYPE.ITEM_TYPE_Hth.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_daikuan_hth,parent,false);  
			return new ProductHthViewHolder(view);  

		}
		return null;
	}


	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub

		int type;

		if(position % 3 == 1){
			type = ITEM_TYPE.ITEM_TYPE_Product.ordinal();
		}else if(position % 3 == 2){
			type = ITEM_TYPE.ITEM_TYPE_Person.ordinal();
		}else {
			type = ITEM_TYPE.ITEM_TYPE_Hth.ordinal();
		}

		return type;
	}

	public class ProductViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvDaikuan_mingchen, tvDaikuan_kaihujigou, tvDaikuan_lilv, 
		tvDaikuan_fafangriqi, tvDaikuan_daoqiriqi, tvDaikuan_jine, tvDaikuan_leixing, 
		tvDaikuan_fenlei, tvDaikuan_yue, tvDaikuan_jingli;

		public ProductViewHolder(View view)
		{
			super(view);
			tvDaikuan_mingchen = (TextView) view.findViewById(R.id.customer_product_daikuan_mingchen); 
			tvDaikuan_kaihujigou = (TextView) view.findViewById(R.id.customer_product_daikuan_jigou); 
			tvDaikuan_lilv = (TextView) view.findViewById(R.id.customer_product_daikuan_lilv);  
			tvDaikuan_fafangriqi = (TextView) view.findViewById(R.id.customer_product_daikuan_riqi);  
			tvDaikuan_daoqiriqi = (TextView) view.findViewById(R.id.customer_product_daikuan_daoqi_riqi);  
			tvDaikuan_jine = (TextView) view.findViewById(R.id.customer_product_daikuan_jine);  
			tvDaikuan_leixing = (TextView) view.findViewById(R.id.customer_product_daikuan_leixing);  
			tvDaikuan_fenlei = (TextView) view.findViewById(R.id.customer_product_daikuan_wuji);  
			tvDaikuan_yue = (TextView) view.findViewById(R.id.customer_product_daikuan_yue);  
			tvDaikuan_jingli = (TextView) view.findViewById(R.id.customer_product_daikuan_jingli); 

		}
	}

	public class PersonListViewHolder extends RecyclerView.ViewHolder{

		private RecyclerView mRecyclerView;
		//		private CustomerProductDaikuanPersonAdapter adapter;

		public PersonListViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			mRecyclerView = (RecyclerView) itemView.findViewById(R.id.costomer_product_daikuan_recycleview);
			//			mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
			//			//			mRecyclerView.setAdapter(new PersonViewHolder());

		}


	}

	public class ProductHthViewHolder extends RecyclerView.ViewHolder{

		private TextView tvHth;

		public ProductHthViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
			tvHth = (TextView) itemView.findViewById(R.id.customer_product_daikuan_hth);
		}
	}


	public void setData(List<LoanCustomerFamily> loanCustomerFamilys){
		this.loanCustomerFamilys = loanCustomerFamilys;
	}

	private int dip2px(float dip) {
		float v = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mCnt.getResources().getDisplayMetrics());
		return (int) (v + 0.5f);
	}
}