package com.mbfw.service.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetPurchaseBill;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Service("assetPurchaseBillService")
public class AssetPurchaseBillService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Autowired
	private AssetPurchaseBillService assetPurchaseBillService; // 采购单服务
	/**
	 * 保存采购单
	 * @param pd
	 * @throws Exception
	 */
	public void save_purchase_bill(AssetPurchaseBill pd)throws Exception{
		dao.save("AssetPurchaseBillMapper.save_purchase_bill", pd);
	}
	
	/**
	 * 查询全部采购单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_puchase_bill(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetPurchaseBillMapper.find_puchase_bill", page);
	}
	
	// 查询关键字总条
	public List<PageData> find_puchase_bill_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetPurchaseBillMapper.find_puchase_bill_bykey", page);
	}
	// 查询关键字
	public Integer find_puchase_bill_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetPurchaseBillMapper.find_puchase_bill_bykey_totalnumber", page);
	}
	
	// 查找已完成的采购单在进行入库操作逻辑
	public List<PageData> find_finish_purchase_bill(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetPurchaseBillMapper.find_finish_purchase_bill", pd);
	}
	
	// 查找已完成的采购单在进行入库操作逻辑 ，封装成json
	
	public String finish_purchase_bill_info(PageData pd) throws Exception{
		Map<String,String> js = new HashMap<String,String>();
        //PageData pd = new PageData();
        List<PageData> pds =  assetPurchaseBillService.find_finish_purchase_bill(pd);
        //循环鉴别每一个条目
        for(PageData pageData : pds){
        	String apply_name = pageData.get("purchase_code").toString();
        	String splits = pageData.getString("purchase_asset_name")+"@"+pageData.get("purchase_asset_count").toString()
        	+"@"+pageData.get("purchase_price").toString()+"@"+pageData.getString("device_code");
        	js.put(apply_name, splits);
        	
        }
        
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		return json.toString();
	}
	
	/**
	 * 通过id查询采购单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData find_puchase_bill_byid(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AssetPurchaseBillMapper.find_puchase_bill_byid", pd);
	}
	
	/**
	 * 修改采购单
	 * @param pd
	 * @throws Exception
	 */
	public void edit_purchase_bill(AssetPurchaseBill pd) throws Exception{
		dao.update("AssetPurchaseBillMapper.edit_purchase_bill", pd);
	}
	
	/**
	 * 
	 * @param pd 删除采购单
	 * @throws Exception
	 */
	public void delete_purchase_bill(PageData pd) throws Exception{
		dao.delete("AssetPurchaseBillMapper.delete_purchase_bill", pd);
	}
	
	/**
	 * 批量删除采购单
	 */
	public void delete_all_purchase_bill(String[] del_purchase_bill) throws Exception {
		dao.delete("AssetPurchaseBillMapper.delete_all_purchase_bill", del_purchase_bill);
	}
	
	/**
	 * 查询总条数
	 * @throws Exception 
	 */
	public Integer find_puchase_bill_totalnumber(PageOption pd) throws Exception{
		
		return (Integer) dao.findForObject("AssetPurchaseBillMapper.find_puchase_bill_totalnumber", pd);
	}
	/**
	 * 
	 * @param purchase_code修改采购单状态
	 * @throws Exception 
	 */
	public void update_finish_purchase_bill(String purchase_code) throws Exception{
		dao.update("AssetPurchaseBillMapper.update_finish_purchase_bill", purchase_code);
	}
	
	/**
	 * 
	 * @param into_code 修改采购单状态 ,使之完成
	 * @throws Exception
	 */
	public void update_finish_purchase_bill_after_registerasset(String into_code) throws Exception{
		dao.update("AssetPurchaseBillMapper.update_finish_purchase_bill_after_registerasset", into_code);
	}
	
	/**
	 * 
	 * @param into_code 修改采购单状态 ,使之采购中
	 * @throws Exception
	 */
	public void updating_finish_purchase_bill_after_registerasset(String into_code) throws Exception{
		dao.update("AssetPurchaseBillMapper.updating_finish_purchase_bill_after_registerasset", into_code);
	}
	/**
	 * 
	 * @param pd在删除采购单状态时，恢复项目立项采购单状态 
	 * @throws Exception
	 */
	public void update_status_project(PageData pd) throws Exception{
		dao.update("AssetAProjectManager.update_status_project", pd);
	}
}
