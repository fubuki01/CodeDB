package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.customer.CustomerCheckActivity;
import com.hnran.perfmanagesys.utils.BigDecimalUtil;
import com.readboy.ssm.po.DepositAccountFamily;
import com.readboy.ssm.po.DepositAccountPerson;
import com.readboy.ssm.po.DepositCustomer;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tan
 *
 */

public class CustomerProductCunkuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

	public static enum ITEM_TYPE {  
		ITEM_TYPE_Product,  
		ITEM_TYPE_Person 
	}

	private List<DepositAccountFamily> depositAccountFamilys;
	private List<DepositAccountPerson> depositAccountPersons;

	public CustomerProductCunkuanAdapter(DepositCustomer depositCustomer){
		super();
		if(depositCustomer != null){
			this.depositAccountFamilys = depositCustomer.getDepositAccountFamilys();
			this.depositAccountPersons = depositCustomer.getDepositAccountPersons();
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		int size = 0;

		if(depositAccountFamilys != null){
			size += depositAccountFamilys.size();
		}
		if(depositAccountPersons != null){
			size += depositAccountPersons.size();
		}

		return size + 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub


		if (holder instanceof ProductViewHolder){  
			if(depositAccountFamilys != null){

				DepositAccountFamily depositAccountFamily = depositAccountFamilys.get(position);
				((ProductViewHolder)holder).tvCunkuan_mingchen.setText(depositAccountFamily.getCkzh());
				((ProductViewHolder)holder).tvCunkuan_kaihujigou.setText(depositAccountFamily.getZzjc());
				((ProductViewHolder)holder).tvCunkuan_lilv.setText(BigDecimalUtil.formatBigDecimal(depositAccountFamily.getLl()));
				((ProductViewHolder)holder).tvCunkuan_kaihuriqi.setText(depositAccountFamily.getKhrq()); 
				((ProductViewHolder)holder).tvCunkuan_jine.setText(depositAccountFamily.getCkye() + ""); 
				try{
					((ProductViewHolder)holder).tvCunkuan_leixing.setText(CommonContent.YINGXIAO_LEIXING[depositAccountFamily.getYxlx()]); 
				}catch(IndexOutOfBoundsException e){
					e.printStackTrace();
				}
				((ProductViewHolder)holder).tvCunkuan_tuozhanren.setText(depositAccountFamily.getTzr()); 
				((ProductViewHolder)holder).tvCunkuan_bili.setText(depositAccountFamily.getTzbl());

			}
		}else if (holder instanceof PersonViewHolder){  

			if(depositAccountPersons != null){
				position = position - depositAccountFamilys.size() - 1;
				if(position == -1){
					((PersonViewHolder)holder).tvXingming.setText("管户人");
					((PersonViewHolder)holder).tvBili.setText("管户比例");
					((PersonViewHolder)holder).tvKaishi.setText("开始时间");
					((PersonViewHolder)holder).tvJieshu.setText("结束时间");
				}else{
					DepositAccountPerson dap = depositAccountPersons.get(position);
					((PersonViewHolder)holder).tvXingming.setText(dap.getYgxm());
					((PersonViewHolder)holder).tvBili.setText(dap.getGhbl()+"");
					((PersonViewHolder)holder).tvKaishi.setText(dap.getKsrq());
					((PersonViewHolder)holder).tvJieshu.setText(dap.getJsrq());
				}
			}
		}  
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		if (viewType == ITEM_TYPE.ITEM_TYPE_Product.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_cunkuan,parent,false);  

			return new ProductViewHolder(view);  

		}else if(viewType == ITEM_TYPE.ITEM_TYPE_Person.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_cunkuan_user,parent,false);  
			return new PersonViewHolder(view);  

		}  
		return null;
	}


	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(depositAccountFamilys == null){
			return 0;
		}
		return position < depositAccountFamilys.size() ? ITEM_TYPE.ITEM_TYPE_Product.ordinal() : ITEM_TYPE.ITEM_TYPE_Person.ordinal();
	}

	public class ProductViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvCunkuan_mingchen, tvCunkuan_kaihujigou, tvCunkuan_lilv, 
		tvCunkuan_kaihuriqi, tvCunkuan_jine, tvCunkuan_leixing, 
		tvCunkuan_tuozhanren, tvCunkuan_bili;

		public ProductViewHolder(View view)
		{
			super(view);
			tvCunkuan_mingchen = (TextView) view.findViewById(R.id.customer_product_cunkuan_mingchen);
			tvCunkuan_kaihujigou = (TextView) view.findViewById(R.id.customer_product_cunkuan_jigou);
			tvCunkuan_lilv = (TextView) view.findViewById(R.id.customer_product_cunkuan_lilv);
			tvCunkuan_kaihuriqi = (TextView) view.findViewById(R.id.customer_product_cunkuan_riqi);
			tvCunkuan_jine = (TextView) view.findViewById(R.id.customer_product_cunkuan_jine);
			tvCunkuan_leixing = (TextView) view.findViewById(R.id.customer_product_cunkuan_leixing);
			tvCunkuan_tuozhanren = (TextView) view.findViewById(R.id.customer_product_cunkuan_tuozhanren);
			tvCunkuan_bili = (TextView) view.findViewById(R.id.customer_product_cunkuan_bili);
		}
	}


	public class PersonViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvXingming, tvBili, tvKaishi, tvJieshu;
		public PersonViewHolder(View view)
		{
			super(view);
			tvXingming = (TextView) view.findViewById(R.id.item_customer_cunkuan_xingming);
			tvBili = (TextView) view.findViewById(R.id.item_customer_cunkuan_bili);
			tvKaishi = (TextView) view.findViewById(R.id.item_customer_cunkuan_kaishi);
			tvJieshu = (TextView) view.findViewById(R.id.item_customer_cunkuan_jieshu);
		}
	}

	public void setData(DepositCustomer depositCustomer){
		if(depositCustomer != null){
			this.depositAccountFamilys = depositCustomer.getDepositAccountFamilys();
			this.depositAccountPersons = depositCustomer.getDepositAccountPersons();
		}
	}

}