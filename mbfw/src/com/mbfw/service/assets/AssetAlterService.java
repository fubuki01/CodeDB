package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetAlter;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.util.PageData;

@Service("AssetAlterService")
public class AssetAlterService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

//	插入数据库数据
	public List<PageData> findPzHouse(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetAlterMapper.listAllAssetAlter", pd);
	}
	//保存用户
		public void addPzData(PageData pd) throws Exception {
			dao.save("AssetAlterMapper.addPz", pd);
		}
		
//		删除数据
		public void deletePz(PageData pd) throws Exception {
			dao.delete("AssetAlterMapper.delPz", pd);		
		}
		
		//批量删除
		public void deletePzData(Integer[] Allot_ids) throws Exception {
			dao.delete("AssetAlterMapper.deletePz", Allot_ids);
		}
		
//		//批量删除
//		public void deletePzData(Integer[] ids) throws Exception {
//			dao.delete("AssetAlterMapper.deletePz", ids);
//		}
		
//		编辑
		public AssetAlter EditPzData(PageData pd) throws Exception {
			return (AssetAlter) dao.findForObject("AssetAlterMapper.editPz", pd);
		}
		
		//更新
		public void updatePz(PageData pd) throws Exception {
					dao.update("AssetAlterMapper.updatePz" , pd);			
				}
		/**
		 * 显示所有的审核人员的数据条数
		 * @return
		 * @throws Exception 
		 */
//		public Integer findTotalDataNumber() throws Exception {		
//			return (Integer) dao.findForObject("AssetAlterMapper.findPzTotalNumber", null);
//		}

	/**
		 * 显示所有的审核人员
		 * @param pd
		 * @return
		 * @throws Exception
		 */
//		public List<PageData> listPdPageApprover(PageOption page) throws Exception{	
//			return (List<PageData>) dao.findForList("AssetAlterMapper.PzListPage" ,page);
//		}
		
		//显示分页所有数据
		public List<PageData> listPdGetPageApprover(PageOption page) throws Exception{	
			return (List<PageData>) dao.findForList("AssetAlterMapper.PzListPage" ,page);
		}

//分页显示数据条数
		public Integer findTotalPzDataNumber() throws Exception {		
			return (Integer) dao.findForObject("AssetAlterMapper.findTotalPzNumber", null);
		}
		
		// 查询检索姓名内容的数据总条数		
		public Integer findNumberPzBySearchName(PageOption page) throws Exception {
			return (Integer) dao.findForObject("AssetAlterMapper.findPzSearchNameNumber", page);
		}
		
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<PageData> listPzSearchNameApprover(PageOption page) throws Exception {
			return (List<PageData>) dao.findForList("AssetAlterMapper.findPzTotalSearchInfo", page);
		}
		//显示资产编码  （下拉框中的）
		public List<PageData> find_assetAlter(PageData pd) throws Exception{
			return  (List<PageData>) dao.findForList("AssetAlterMapper.find_assetAlter", pd);
		}
		// 根据资产编码填写信息
		public PageData find_product_to_add_supplies(String id) throws Exception{
			return (PageData) dao.findForObject("AssetAlterMapper.find_product_to_add_supplies", id);
		}
}
