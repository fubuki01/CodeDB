package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.util.PageData;

@SuppressWarnings("unused")
@Service("SuppliesStoreService")
public class SuppliesStoreService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
//	插入数据库数据
	@SuppressWarnings("unchecked")
	public List<PageData> findStoreHouse(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SuppliesStoreMapper.listAllSuppliesStore", pd);
	}
	//保存用户
	public void addRkData(PageData pd) throws Exception {
		
		dao.save("SuppliesStoreMapper.addRk", pd);
		int count = Integer.parseInt(pd.getString("actual_amount"));
		PageData pds = (PageData)dao.findForObject("SuppliesStoreMapper.searchSp", pd);
		if(pds!=null){
			int inventory_quantity = (int) pds.get("inventory_quantity");
			inventory_quantity = count+inventory_quantity;
			pd.put("actual_amount", inventory_quantity+"");
			dao.update("SuppliesStoreMapper.updateSp", pd);
		}else{
			dao.save("SuppliesStoreMapper.addSp", pd);
		}
	
	}
	
	//批量删除
	public void deleteRkData(Integer[] Allot_ids) throws Exception {
		dao.delete("SuppliesStoreMapper.deleteRk", Allot_ids);
	}
//	编辑
	public SuppliesStore EditRukuData(PageData pd) throws Exception {
		return (SuppliesStore) dao.findForObject("SuppliesStoreMapper.editRk", pd);
	}
	
	//更新
	public void updateRuku(PageData pd) throws Exception {
				dao.update("SuppliesStoreMapper.updateRk" , pd);			
			}
	
	
	
	/**
	 * 根据userId来进行删除用户信息
	 * @param pd
	 * @throws Exception 
	 */
	public void deleteRuku(PageData pd) throws Exception {
		dao.delete("SuppliesStoreMapper.delRk", pd);		
	}
	
	//显示分页所有数据
			@SuppressWarnings("unchecked")
			public List<PageData> listPdGetPageApprover(PageOption page) throws Exception{	
				return (List<PageData>) dao.findForList("SuppliesStoreMapper.RkListPage" ,page);
			}
	
	//分页显示数据条数
			public Integer findTotalRkDataNumber() throws Exception {		
				return (Integer) dao.findForObject("SuppliesStoreMapper.findTotalRkNumber", null);
			}
			
			// 查询检索姓名内容的数据总条数		
			public Integer findNumberRkBySearchName(PageOption page) throws Exception {
				return (Integer) dao.findForObject("SuppliesStoreMapper.findRkSearchNameNumber", page);
			}
			
			// 根据检索内容，查询数据库，返回所有的的数据信息		 
			@SuppressWarnings("unchecked")
			public List<PageData> listRkSearchNameApprover(PageOption page) throws Exception {
				return (List<PageData>) dao.findForList("SuppliesStoreMapper.findRkTotalSearchInfo", page);
			}
			
			//显示下拉框中的耗材编码
			@SuppressWarnings("unchecked")
			public List<PageData> find_suppliesStore(PageData pd) throws Exception{
				return  (List<PageData>) dao.findForList("SuppliesStoreMapper.find_suppliesStore", pd);
			}
			// 根据耗材编码填写耗材信息
			public PageData find_product_to_add_supplies(String product_code) throws Exception{
				return (PageData) dao.findForObject("SuppliesStoreMapper.find_product_to_add_supplies", product_code);
			}
			
			//更新状态成已经入库
			public PageData edit_stateRuku(String id) throws Exception {
				return (PageData) dao.findForObject("SuppliesStoreMapper.edit_stateRuku" , id);			
					}
}
