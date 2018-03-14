package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.customer.CustomerProductDaikuanActivity;
import com.hnran.perfmanagesys.adapter.CustomerProductEtcAdapter.ProductViewHolder;
import com.readboy.ssm.po.CellBankCustomer;
import com.readboy.ssm.po.CellBankCustomerPerson;
import com.readboy.ssm.po.CellBankCustomerProduct;
import com.readboy.ssm.po.EtcCustomerFamily;
import com.readboy.ssm.po.LoanCustomerFamily;
import com.readboy.ssm.po.LoanCustomerPerson;

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

public class CustomerProductDaikuanPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public final static String[] GUANHU_LEIXING = {"", "拓展", "管户", "包收", "审批", "调查", "安装"};
	
	private List<LoanCustomerPerson> loanCustomerPersons;

	public CustomerProductDaikuanPersonAdapter(List<LoanCustomerPerson> loanCustomerPersons){
		super();
		this.loanCustomerPersons = loanCustomerPersons;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		int size = 0;

		if(loanCustomerPersons != null){
			size += loanCustomerPersons.size();
		}
		return size;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		if(loanCustomerPersons == null){
			return ;
		}
		LoanCustomerPerson dap =  loanCustomerPersons.get(position);
		
		((PersonViewHolder)holder).tvLeixing.setText(GUANHU_LEIXING[dap.getGhlx()]);
		((PersonViewHolder)holder).tvXingming.setText(dap.getYgxm());
		((PersonViewHolder)holder).tvBili.setText(dap.getGhbl()+"");
		((PersonViewHolder)holder).tvKaishi.setText(dap.getKsrq());
		((PersonViewHolder)holder).tvJieshu.setText(dap.getJsrq());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_daikuan_user,parent,false);  

		return new PersonViewHolder(view);  

	}


	public class PersonViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvLeixing, tvXingming, tvBili, tvKaishi, tvJieshu;
		public PersonViewHolder(View view)
		{
			super(view);
			tvLeixing = (TextView) view.findViewById(R.id.item_customer_cunkuan_leixing);
			tvXingming = (TextView) view.findViewById(R.id.item_customer_cunkuan_xingming);
			tvBili = (TextView) view.findViewById(R.id.item_customer_cunkuan_bili);
			tvKaishi = (TextView) view.findViewById(R.id.item_customer_cunkuan_kaishi);
			tvJieshu = (TextView) view.findViewById(R.id.item_customer_cunkuan_jieshu);
		}
	}

	public void setData(List<LoanCustomerPerson> loanCustomerPersons){
		this.loanCustomerPersons = loanCustomerPersons;
	}
}