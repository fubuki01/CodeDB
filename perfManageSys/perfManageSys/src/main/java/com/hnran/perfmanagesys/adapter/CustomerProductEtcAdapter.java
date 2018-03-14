package com.hnran.perfmanagesys.adapter;

import java.util.List;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.activity.CommonContent;
import com.hnran.perfmanagesys.activity.customer.CustomerCheckActivity;
import com.readboy.ssm.po.EtcCustomerFamily;

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

public class CustomerProductEtcAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

	private List<EtcCustomerFamily> etcCustomerFamilys;

	public CustomerProductEtcAdapter(List<EtcCustomerFamily> etcCustomerFamilys){
		super();
		this.etcCustomerFamilys = etcCustomerFamilys;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		int size = 0;

		if(etcCustomerFamilys != null){
			size += etcCustomerFamilys.size();
		}
		return size;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		if(etcCustomerFamilys == null){
			return ;
		}
		EtcCustomerFamily ecf =  etcCustomerFamilys.get(position);

		((ProductViewHolder)holder).tvEtc_kaihujigou.setText(ecf.getZzjc());
		((ProductViewHolder)holder).tvEtc_bangdingriqi.setText(ecf.getRq());
		try{
			((ProductViewHolder)holder).tvEtc_leixing.setText(CommonContent.YINGXIAO_LEIXING[ecf.getYxlx()]);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		((ProductViewHolder)holder).tvEtc_jine.setText(ecf.getDfje()+""); 
		((ProductViewHolder)holder).tvEtc_kahao.setText(ecf.getXtkkh()); 
		((ProductViewHolder)holder).tvEtc_tuozhanren.setText(ecf.getTzr()); 
		((ProductViewHolder)holder).tvEtc_bili.setText(ecf.getTzbl() != null ? ecf.getTzbl()+"" : ""); 
		((ProductViewHolder)holder).tvEtc_anzhuangren.setText(ecf.getAzr()); 
		((ProductViewHolder)holder).tvEtc_anzhuangbili.setText(ecf.getAzbl() != null ? ecf.getAzbl()+"":"");

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_product_etc,parent,false);  

		return new ProductViewHolder(view);  

	}


	public class ProductViewHolder extends RecyclerView.ViewHolder
	{

		private TextView tvEtc_kaihujigou, tvEtc_bangdingriqi, 
		tvEtc_leixing, tvEtc_jine, tvEtc_kahao, 
		tvEtc_tuozhanren, tvEtc_bili, tvEtc_anzhuangren, tvEtc_anzhuangbili;

		public ProductViewHolder(View view)
		{
			super(view);
			tvEtc_kaihujigou = (TextView) view.findViewById(R.id.customer_product_etc_jigou); 
			tvEtc_bangdingriqi = (TextView) view.findViewById(R.id.customer_product_etc_kaihu_riqi); 
			tvEtc_leixing = (TextView) view.findViewById(R.id.customer_product_etc_leixing); 
			tvEtc_jine = (TextView) view.findViewById(R.id.customer_product_etc_dianfu); 
			tvEtc_kahao = (TextView) view.findViewById(R.id.customer_product_etc_tiepian_kahao); 
			tvEtc_tuozhanren = (TextView) view.findViewById(R.id.customer_product_etc_tuozhanren); 
			tvEtc_bili = (TextView) view.findViewById(R.id.customer_product_etc_bili);
			tvEtc_anzhuangren = (TextView) view.findViewById(R.id.customer_product_etc_anzhuangren);
			tvEtc_anzhuangbili = (TextView) view.findViewById(R.id.customer_product_etc_anzhuang_bili);

		}
	}

	public void setData(List<EtcCustomerFamily> etcCustomerFamilys){
		this.etcCustomerFamilys = etcCustomerFamilys;
	}
}