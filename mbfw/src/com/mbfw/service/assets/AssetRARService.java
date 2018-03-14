package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetAllot;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetRARService")
public class AssetRARService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询显示数据
	public List<PageData> listAllRAR(PageOption page) throws Exception {
		return (List<PageData>)dao.findForList("AssetRepairAndMaintainMapper.listAllAssetRAR",page);
	}
	/**
	 * 查询总条数
	 * @throws Exception 
	 */
	public Integer findTotalNumber() throws Exception{
		
		return (Integer) dao.findForObject("AssetRepairAndMaintainMapper.findTotalNumber", null);
	}

	// 查询检索姓名内容的数据总条数		
	public Integer findNumberGetBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetRepairAndMaintainMapper.findPzSearchNameNumber", page);
	}
	
	// 根据检索内容，查询数据库，返回所有的的数据信息		 
	public List<PageData> listGetSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetRepairAndMaintainMapper.findPzTotalSearchInfo", page);
	}

	//单独查询显示数据
	public AssetRAR listAllotById(PageData pd) throws Exception {
		return (AssetRAR) dao.findForObject("AssetRepairAndMaintainMapper.listAssetRAR",pd);
	}
	//单独查询显示数据(有另外作用)
	public List<PageData> listRARById(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetRepairAndMaintainMapper.findCode", pd);
	}
	/*
	 * 保存保修表
	 */
	public void saveRAR(PageData pd) throws Exception {
		dao.save("AssetRepairAndMaintainMapper.saveRAR", pd);
	}
	/*
	 * 修改用户
	 */
	public void editRAR(PageData pd) throws Exception {
		dao.update("AssetRepairAndMaintainMapper.editRAR", pd);
	}
	/*
	 * 删除用户
	 */
	public void deleteRAR(PageData pd) throws Exception {
		dao.delete("AssetRepairAndMaintainMapper.deleteRAR", pd);
	}
	
	/*
	 * 保存维修表
	 */
	public void saveRAR1(PageData pd) throws Exception {
		dao.update("AssetRepairAndMaintainMapper.saveRAR1", pd);
	}

	/*
	 * 批量删除
	 */
	public void deleteAllRAR(int[] RAR_ids) throws Exception {
		dao.delete("AssetRepairAndMaintainMapper.deleteAllRAR", RAR_ids);
	}
	
	/*
	 * 移动端缺省查询
	 */	 
	public List<AssetRAR> findByDefault(PageData page) throws Exception {
		return (List<AssetRAR>) dao.findForList("AssetRepairAndMaintainMapper.findByDefault", page);
	}
	
	/*
	 * 移动端覆盖报修表
	 */
	public void mobile_updateRAR(PageData pd) throws Exception {
		dao.update("AssetRepairAndMaintainMapper.mobile_updateRAR", pd);
	}
	
	/*
	 * 移动端维护
	 */
	public void mobile_maintain(PageData pd) throws Exception {
		dao.update("AssetRepairAndMaintainMapper.mobile_maintain", pd);
	}
	
	/*
	 * 移动端维修查询 
	 */	 
	public PageData mobile_maintain_query(PageData page) throws Exception {
		return (PageData)dao.findForObject("AssetRepairAndMaintainMapper.mobile_maintain_query", page);
	}
	
	/*
	 * 移动端维护
	 */
	public void  editAssetStatus(PageData pd) throws Exception {
		dao.update("AssetRepairAndMaintainMapper.editAssetStatus", pd);
	}
	/*
	 * 单独查询维修结果
	 */

	public AssetRAR listBymaintain_result(PageData pd) throws Exception {
		return (AssetRAR) dao.findForObject("AssetRepairAndMaintainMapper.listBymaintain_result",pd);
	}
}
