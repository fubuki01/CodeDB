package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.AssetRecycleManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetRecycleService")
public class AssetRecycleService {

	
		@Resource(name = "daoSupport")
		private DaoSupport dao;
		
//		public List<PageData> findDataStoreHouse(PageData pd) throws Exception {
//			return (List<PageData>) dao.findForList("AssetRecycleMapper.asset_recycle", pd);
//		}
		
		//显示分页所有数据
		public List<PageData> listPdRecyclePageApprover(PageOption page) throws Exception{	
			return (List<PageData>) dao.findForList("AssetRecycleMapper.approverRecycleListPage" ,page);
		}
		
		//分页显示数据条数
		public Integer findTotalRecycleDataNumber(PageOption page) throws Exception {		
			return (Integer) dao.findForObject("AssetRecycleMapper.findTotalRecycleNumber", page);
		}
		
		//获得前一状态的信息
		public AssetGetManage findGetInfoData(PageData pd) throws Exception {
			return (AssetGetManage) dao.findForObject("AssetRecycleMapper.findGetInfoData", pd);
		}
		
		//更改资产状态
		public void editRecycleStatus(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.editRecycleStatus" , pd);			
		}
		
		//在编辑时获取录入信息
		public AssetRecycleManage findRecycleEditData(PageData pd) throws Exception {
			return (AssetRecycleManage) dao.findForObject("AssetRecycleMapper.recycle_edit", pd);
		}
	
		//保存用户
		public void saveRecycleData(PageData pd) throws Exception {
			dao.save("AssetRecycleMapper.saveRecycle", pd);
		}
		
		//保存信息时，将资产表的部门等信息也置为null
		public void deleteToAssetInfo(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.deleteToAssetInfo" , pd);			
		}
		
		//更改有效值
		public void changeValue(PageData pd) throws Exception {
			dao.save("AssetRecycleMapper.changeValue", pd);
		}
		
		//删除前查找领用表的原始状态信息
		public PageData searchStatusInfo(int s) throws Exception {
			return (PageData) dao.findForObject("AssetRecycleMapper.searchStatusInfo", s);
		}
						
		//删除
		public  void deleteRecycle(PageData pd) throws Exception {
			dao.delete("AssetRecycleMapper.deleteRecycle", pd);
		}
		
		//删除时，还原资产表中之前的信息
		public void returnToAssetInfo(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.returnToAssetInfo" , pd);			
		}
		
		//删除后返回到原来的资产状态
		public void returnPriorStatus(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.returnPriorStatus" , pd);			
		}
		
		//删除后还原之前状态的有效值
		public void returnValid(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.returnValid" , pd);			
		}
		
		//批量删除
		public void deleteAllRecycleData(int[] Allot_ids) throws Exception {
			dao.delete("AssetRecycleMapper.deleteallrecycledata", Allot_ids);
		}
		
		//更新
		public void editRecycleApprover(PageData pd) throws Exception {
			dao.update("AssetRecycleMapper.editRecycleApprover" , pd);			
		}
	
		// 查询检索姓名内容的数据总条数		
		public Integer findNumberRecycleBySearchName(PageOption page) throws Exception {
			return (Integer) dao.findForObject("AssetRecycleMapper.findRecycleSearchNameNumber", page);
		}
				
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<PageData> listRecycleSearchNameApprover(PageOption page) throws Exception {
			return (List<PageData>) dao.findForList("AssetRecycleMapper.findRecycleTotalSearchInfo", page);
		}
		
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<AssetRecycleManage> findByDefault(PageData page) throws Exception {
			return (List<AssetRecycleManage>) dao.findForList("AssetRecycleMapper.findByDefault", page);
		}
		
		//查询资产数量
		public Integer checkAssetCount(PageData pd) throws Exception {
			return (Integer) dao.findForObject("AssetRecycleMapper.checkAssetCount", pd);
		}
}