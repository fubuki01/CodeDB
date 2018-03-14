package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetDepartmentManage;
import com.mbfw.entity.assets.AssetAllot;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetReportAndRepair;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetRepAndAllotService")
public class AssetRepAndAllotService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//查询显示数据
	public List<PageData> listAllAllot(PageOption page) throws Exception {
		return (List<PageData>)dao.findForList("AssetAllotMapper.listAllAssetAllot",page);
	}
	
	/**
	 * 查询总条数
	 * @throws Exception 
	 */
	public Integer findTotalNumber(PageOption page) throws Exception{
		
		return (Integer) dao.findForObject("AssetAllotMapper.findTotalNumber", page);
	}
	
	// 查询检索姓名内容的数据总条数		
	public Integer findNumberGetBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetAllotMapper.findPzSearchNameNumber", page);
	}
	
	// 根据检索内容，查询数据库，返回所有的的数据信息		 
	public List<PageData> listGetSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetAllotMapper.findPzTotalSearchInfo", page);
	}
	
	//单独查询显示数据
	public AssetAllot listAllotById(PageData pd) throws Exception {
		return (AssetAllot) dao.findForObject("AssetAllotMapper.listAssetAllot",pd);
	}

	/*
	 * 通过id获取数据
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AssetAllotMapper.findByUiId", pd);
	}

	/*
	 * 保存用户
	 */
	public void saveAllot(PageData pd) throws Exception {
		dao.save("AssetAllotMapper.saveAllot", pd);
	}

	/*
	 * 修改用户
	 */
	public void editAllot(PageData pd) throws Exception {
		dao.update("AssetAllotMapper.editAllot", pd);
	}
	/*
	 * 删除用户
	 */
	public void deleteAllot(PageData pd) throws Exception {
		dao.delete("AssetAllotMapper.deleteAllot", pd);
	}
	
	/*
	 * 审批
	 */
	public void editAllot1(PageData pd) throws Exception {
		dao.update("AssetAllotMapper.editAllot1", pd);
	}

	/*
	 * 批量删除调拨申请
	 */
	public void deleteAllAllot(int[] Allot_ids) throws Exception {
		dao.delete("AssetAllotMapper.deleteAllAllot", Allot_ids);
	}
	/**
	 * 公司名称查找（JY，勿删）
	 */
	public String findAssetGongsi(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetAllotMapper.findAssetGongsi", pd);
	}
	/**
	 * 部门名称查找（JY，勿删）
	 */
	public String findAssetDept(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetAllotMapper.findAssetDept", pd);
	}
	/**
	 * 原资产使用人查找（JY，勿删）
	 */
	public String findAssetUser(PageData pd) throws Exception{
		return (String) dao.findForObject("AssetAllotMapper.findAssetUser", pd);
	}

	/*
	 *修改资产使用人以及新的部门
	 */
	public void editAssetInfo(PageData pd) throws Exception {
		dao.update("AssetAllotMapper.editAssetInfo", pd);
	}
	/**
	 * 资产使用人以及新的部门
	 */
	public List<AssetInfo> findAssetInfo(PageData pd) throws Exception{
		return (List<AssetInfo>) dao.findForList("AssetAllotMapper.findAssetInfo", pd);
	}
}
