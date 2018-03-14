package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.customer.CustomerCheckActivity;
import com.readboy.ssm.po.CellBankCustomer;
import com.readboy.ssm.po.CellBankCustomerPerson;
import com.readboy.ssm.po.CellBankCustomerProduct;

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

public class CustomerProductShoujiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

	public static enum ITEM_TYPE {  
		ITEM_TYPE_Product,  
		ITEM_TYPE_Person 
	}

	private List<CellBankCustomerPerson> cellBankCustomerPersons;
	private List<CellBankCustomerProduct> cellBankCustomerProducts;

	public CustomerProductShoujiAdapter(CellBankCustomer cellBankCustomers){
		super();
		if(cellBankCustomers != null){
			this.cellBankCustomerPersons = cellBankCustomers.getCellBankCustomerPersons();
			this.cellBankCustomerProducts = cellBankCustomers.getCellBankCustomerProducts();
		}
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		int size = 0;

		if(cellBankCustomerProducts != null){
			size += cellBankCustomerProducts.size();
		}
		if(cellBankCustomerPersons != null){
			size += cellBankCustomerPersons.size();
		}

		return size + 1;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		if(cellBankCustomerProducts == null || cellBankCustomerPersons == null){
			return ;
		}
		if (holder instanceof ProductViewHolder){  
			CellBankCustomerProduct cbcp = cellBankCustomerProducts.get(position);
			((ProductViewHolder)holder).tvShouji_kaihujigou.setText(cbcp.getZzjc());
			((ProductViewHolder)holder).tvShouji_kaihuriqi.setText(cbcp.getKhrq());
			try{
			((ProductViewHolder)holder).tvShouji_leixing.setText(CommonContent.YINGXIAO_LEIXING[cbcp.getYxlx()]);
			}catch(IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			((ProductViewHolder)holder).tvShouji_zhuxiaoriqi.setText(cbcp.getZxrq());
			((ProductViewHolder)holder).tvShouji_tiepianriqi.setText(cbcp.getTprq());
			((ProductViewHolder)holder).tvShouji_tuozhanren.setText(cbcp.getTzr()); 
			((ProductViewHolder)holder).tvShouji_bili.setText(cbcp.getTzbl() != null ? cbcp.getTzbl()+"" : "");

		}else if (holder instanceof PersonViewHolder){  
			position = position - cellBankCustomerProducts.size() - 1;
			if(position == -1){
				((PersonViewHolder)holder).tvXingming.setText("管户人姓名");
				((PersonViewHolder)holder).tvBili.setText("管户比例(%)");
				((PersonViewHolder)holder).tvKaishi.setText("开始时间");
				((PersonViewHolder)holder).tvJieshu.setText("结束时间");

			}else{
				CellBankCustomerPerson cbcp = cellBankCustomerPersons.get(position);
				((PersonViewHolder)holder).tvXingming.setText(cbcp.getYgxm());
				((PersonViewHolder)holder).tvBili.setText(cbcp.getGhbl()+"");
				((PersonViewHolder)holder).tvKaishi.setText(cbcp.getKsrq());
				((PersonViewHolder)holder).tvJieshu.setText(cbcp.getJsrq());
			}
		}  
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		if (viewType == ITEM_TYPE.ITEM_TYPE_Product.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_shouji,parent,false);  

			return new ProductViewHolder(view);  

		}else if(viewType == ITEM_TYPE.ITEM_TYPE_Person.ordinal()){  

			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_shouji_user,parent,false);  
			return new PersonViewHolder(view);  

		}  
		return null;
	}


	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if(cellBankCustomerProducts == null){
			return 0;
		}
		return position < cellBankCustomerProducts.size() ? ITEM_TYPE.ITEM_TYPE_Product.ordinal() : ITEM_TYPE.ITEM_TYPE_Person.ordinal();
	}

	public class ProductViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvShouji_kaihujigou, tvShouji_kaihuriqi,
		tvShouji_leixing, tvShouji_zhuxiaoriqi, tvShouji_tiepianriqi,
		tvShouji_tuozhanren, tvShouji_bili;

		public ProductViewHolder(View view)
		{
			super(view);
			tvShouji_kaihujigou = (TextView) view.findViewById(R.id.customer_product_shouji_jigou);
			tvShouji_kaihuriqi = (TextView) view.findViewById(R.id.customer_product_shouji_kaihu_riqi);
			tvShouji_leixing = (TextView) view.findViewById(R.id.customer_product_shouji_leixing); 
			tvShouji_zhuxiaoriqi = (TextView) view.findViewById(R.id.customer_product_shouji_zhuxiao_riqi); 
			tvShouji_tiepianriqi = (TextView) view.findViewById(R.id.customer_product_shouji_tiepian_riqi);
			tvShouji_tuozhanren = (TextView) view.findViewById(R.id.customer_product_shouji_tuozhanren); 
			tvShouji_bili = (TextView) view.findViewById(R.id.customer_product_shouji_bili);

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

	public void setData(CellBankCustomer cellBankCustomers){
		if(cellBankCustomers != null){
			this.cellBankCustomerPersons = cellBankCustomers.getCellBankCustomerPersons();
			this.cellBankCustomerProducts = cellBankCustomers.getCellBankCustomerProducts();
		}
	}
}