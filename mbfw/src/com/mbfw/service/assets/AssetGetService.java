package com.mbfw.service.assets;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetGetService")
public class AssetGetService {

	
		@Resource(name = "daoSupport")
		private DaoSupport dao;
		
		/*public List<PageData> findDataStoreHouse(PageData pd) throws Exception {
			return (List<PageData>) dao.findForList("AssetGetMapper.asset_get", pd);
		}*/
		
		//显示分页所有数据
		public List<PageData> listPdGetPageApprover(PageOption page) throws Exception{	
			return (List<PageData>) dao.findForList("AssetGetMapper.approverGetListPage" ,page);
		}
		
		public AssetGetManage findEditData(PageData pd) throws Exception {
			return (AssetGetManage) dao.findForObject("AssetGetMapper.get_edit", pd);
		}
		
		//保存要插入的数据
		public void saveData(PageData pd) throws Exception {
			dao.save("AssetGetMapper.saveGet", pd);
		}
		
		//把必要的信息插入到资产表
		public void saveToAssetInfo(PageData pd) throws Exception {
			dao.update("AssetGetMapper.saveToAssetInfo", pd);
		}
		
		//改变有效值
		public void changeValid(PageData pd) throws Exception {
			dao.update("AssetGetMapper.changeValid", pd);
		}			
				
		//删除
		public  void deleteGet(PageData pd) throws Exception {
			dao.delete("AssetGetMapper.deleteGet", pd);
		}
		
		//删除时，让资产表的信息变为null
		public void deleteToAssetInfo(PageData pd) throws Exception {
			dao.update("AssetGetMapper.deleteToAssetInfo" , pd);			
		}
		
		//批量删除
		public void deleteAllGetData(int[] Allot_ids) throws Exception {
			dao.delete("AssetGetMapper.deletealldata", Allot_ids);
		}
		
//		//获取id信息
//		public PageData findById(PageData pd) throws Exception {
//			return (PageData) dao.findForObject("AssetGetMapper.findById" , pd);
//		}
	
		//更新
		public void editGetApprover(PageData pd) throws Exception {
			dao.update("AssetGetMapper.editGetApprover" , pd);			
		}
		
		//更新时，保证资产表的信息同时更新
		public void editToAssetInfo(PageData pd) throws Exception {
			dao.update("AssetGetMapper.editToAssetInfo", pd);
		}
		
		//更改资产状态
		public void changeIssueValue(PageData pd) throws Exception {
			dao.update("AssetGetMapper.changeIssueValue" , pd);			
		}
		
		//更改下发表的状态
		public void editPriorStatus(PageData pd) throws Exception {
			dao.update("AssetGetMapper.editPriorStatus" , pd);			
		}
		
		//删除后返回到原来的有效状态(true)状态
		public void returnValid(PageData pd) throws Exception {
			if(pd.getString("orig_status").equals("回收")){
				dao.update("AssetGetMapper.returnValid" , pd);	
			}
		}
		
		//删除前查找领用表的原始状态信息
		public PageData searchStatusInfo(int s) throws Exception {
			return (PageData) dao.findForObject("AssetGetMapper.searchStatusInfo", s);
		}
	
		
		//删除操作后，还原到以前的状态
		public void returnPriorStatus(PageData pd) throws Exception {
			if(pd.getString("orig_status").equals("接收")){
				dao.update("AssetGetMapper.returnIssueStatus" , pd);
			}
			if(pd.getString("orig_status").equals("回收")){
				dao.update("AssetGetMapper.returnRecycleStatus" , pd);	
			}
		}
		
		//分页显示数据条数
		public Integer findTotalGetDataNumber(PageOption page) throws Exception {		
			return (Integer) dao.findForObject("AssetGetMapper.findTotalGetNumber", page);
		}
		
		// 查询检索姓名内容的数据总条数		
		public Integer findNumberGetBySearchName(PageOption page) throws Exception {
			return (Integer) dao.findForObject("AssetGetMapper.findGetSearchNameNumber", page);
		}
		
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<PageData> listGetSearchNameApprover(PageOption page) throws Exception {
			return (List<PageData>) dao.findForList("AssetGetMapper.findGetTotalSearchInfo", page);
		}
		
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<AssetGetManage> findByDefault(PageData page) throws Exception {
			return (List<AssetGetManage>) dao.findForList("AssetGetMapper.findByDefault", page);
		}	
		
		//查询资产数量
		public Integer checkAssetCount(PageData pd) throws Exception {
			return (Integer) dao.findForObject("AssetGetMapper.checkAssetCount", pd);
		}
}
