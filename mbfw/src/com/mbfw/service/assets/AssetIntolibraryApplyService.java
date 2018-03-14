package com.mbfw.service.assets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetCount;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetIntolibraryApply;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetIntolibraryApplyService")
public class AssetIntolibraryApplyService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * @param pd 立个标记是在资产添加的时候，知道在什么时候修改入库单状态
	 * @return
	 * @throws Exception
	 */
	public Integer find_product_count_by_class(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetIntolibraryApplyMapper.find_product_count_by_class", pd);
	}
	
	/**
	 * @param pd 查询标记是在资产添加的时候，知道在什么时候修改入库单状态
	 * @return
	 * @throws Exception
	 */
	public PageData find_into_library_apply_by_into_code(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AssetIntolibraryApplyMapper.find_into_library_apply_by_into_code", pd);
	}
	/**
	 * 
	 * @param pd 保存入库单 
	 * @throws Exception
	 */
	public void save_into_library_apply(PageData pd)throws Exception{
		dao.save("AssetIntolibraryApplyMapper.save_intolibrary_bill", pd);
	}
	
	/**
	 * 
	 * @param pd 保存资产入库
	 * @throws Exception
	 */
	public void  save_asset_into_house(AssetInfo pd)throws Exception{
		dao.save("AssetIntolibraryMapper.save_asset_into_house", pd);
	}
	
	
	public List<PageData> find_into_library_apply(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetIntolibraryApplyMapper.find_into_library_apply", page);
	}
	
	public List<PageData> find_into_library_apply_limit(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetIntolibraryApplyMapper.find_into_library_apply_limit", pd);
	}
	
	/**
	 * 
	 * @param pd 根据id查找入库单
	 * @return
	 * @throws Exception
	 */
	public PageData find_into_libraray_bill_by_id(PageData pd) throws Exception{
		return (PageData) dao.findForObject("AssetIntolibraryApplyMapper.find_into_libraray_bill_by_id", pd);
	}
	
	/**
	 * 统计入库单条数
	 * @throws Exception 
	 */
	public Integer find_into_libraray_bill_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetIntolibraryApplyMapper.find_into_libraray_bill_totalnumber", pd);
	}
	
	// 根据关键字查询满足条数
	public Integer find_into_libraray_bill_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetIntolibraryApplyMapper.find_into_libraray_bill_bykey_totalnumber", page);
	}
	
	//根据关键字查询入库单
	public List<PageData> find_into_libraray_bill_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetIntolibraryApplyMapper.find_into_libraray_bill_bykey", page);
	}
	
	/**
	 * @param pd 根据id 更新相应的入库单
	 * @throws Exception
	 */
	public void edit_intolibrary_bill(PageData pd) throws Exception{
		dao.update("AssetIntolibraryApplyMapper.edit_intolibrary_bill", pd);
	}
	
	/**
	 * 
	 * @param pd 根据id删除相应的入库单
	 * @throws Exception
	 */
	public void delete_intolibrary_bill(PageData pd) throws Exception{
		dao.delete("AssetIntolibraryApplyMapper.delete_intolibrary_bill", pd);
	}
	
	/**
	 * 
	 * @param intolibrary_bill 批量删除入库单
	 * @throws Exception
	 */
	public void delete_all_intolibrary_bill(String [] intolibrary_bill) throws Exception{
		dao.delete("AssetIntolibraryApplyMapper.delete_all_intolibrary_bill", intolibrary_bill);
	}
	
	

	/// 入库单完成封装成json
	public String finish_into_bill() throws Exception{
		Map<String,String> js= new HashMap<String,String>();
		PageData pd = new PageData();
		List<PageData> pds = (List<PageData>) dao.findForList("AssetIntolibraryApplyMapper.find_into_library_bill", pd);
		//循环鉴别每一个条目
		for(PageData pageData : pds){
			String into_code = pageData.getString("into_code");
			
		}
		return null;
		
	}
	
	//查找未完成资产入库的入库单，完成资产入库信息
	public PageData find_asset_into_libraray(String id) throws Exception{
		return (PageData) dao.findForObject("AssetIntolibraryMapper.find_asset_into_libraray", id);
	}
	
	// 查询未完成入库单
	
	public List<PageData> find_into_library_for_asset(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetIntolibraryMapper.find_into_library_for_asset", pd);
	}

	/**
	 * 
	 * @param into_code 更新入库单，是该条记录状态完成
	 * @throws Exception 
	 */
	public void update_into_library_for_asset(String into_code) throws Exception{
		dao.update("AssetIntolibraryMapper.update_into_library_for_asset", into_code);
	}
	/**
	 * @param into_code 更新入库单，是该条记录显示入库中
	 * @throws Exception
	 */
	public void updating_into_library_for_asset(String into_code) throws Exception{
		dao.update("AssetIntolibraryMapper.updating_into_library_for_asset", into_code);
	}
	
	public void update_status_purchase(PageData pd) throws Exception{
		dao.update("AssetIntolibraryApplyMapper.update_status_purchase", pd);
	}
}