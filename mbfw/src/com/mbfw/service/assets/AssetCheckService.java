package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetCheckService")
public class AssetCheckService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 分页盘点总页显示的数据条数
	public Integer findTotalOrigCheckCount(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findTotalOrigCheckCount", page);
	}
	
	// 显示盘点总页中分页的所有数据
	public List<PageData> listOrigCheckPageShow(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetCheckMapper.listOrigCheckPageShow", page);
	}
	
	// 查询检索姓名内容的数据总条数(总)
	public Integer findOrigNumberCheckBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findOrigNumberCheckBySearchName", page);
	}

	// 根据检索内容，查询数据库，返回所有的的数据信息(总)
	public List<PageData> listChecktOrigSearchNameShow(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetCheckMapper.listChecktOrigSearchNameShow", page);
	}
	
	
	
	// 分页单个盘点名称显示的数据条数
	public Integer findTotalCheckDataNumber(PageData pd) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findTotalCheckDataNumber", pd);
	}

	// 显示单个盘点名称中分页的所有数据
	public List<PageData> listPdCheckPageApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetCheckMapper.listPdCheckPageApprover", page);
	}

	// 查询检索姓名内容的数据总条数(支)
	public Integer findNumberCheckBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findNumberCheckBySearchName", page);
	}

	// 根据检索内容，查询数据库，返回所有的的数据信息(支)
	public List<PageData> listChecktSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetCheckMapper.listChecktSearchNameApprover", page);
	}

	
	// 查询所有的数量，显示盘点的完成进度
	public Integer findCheckDataNumber(String checkName) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findCheckDataNumber", checkName);
	}
	// 查询已盘点的数量，显示盘点的完成进度
	public Integer findCheckedDataNumber(String checkName) throws Exception {
		return (Integer) dao.findForObject("AssetCheckMapper.findCheckedDataNumber", checkName);
	}
	
	
	// 多条件多选择查询
	public List<PageData> findMultipleSelectResult(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetCheckMapper.findMultipleSelectResult", pd);
	}
	
	//保存数据到盘点表单
	public void saveCheckList(PageData pd) throws Exception {
		dao.save("AssetCheckMapper.saveCheckList", pd);
	}
		
	// 保存信息
	public void saveCheckData(PageData pd) throws Exception {
		dao.save("AssetCheckMapper.saveCheckData", pd);
	}
	
	// 删除盘点单数据
	public void deletechecklistdata(int[] newIds) throws Exception {
		dao.delete("AssetCheckMapper.deletechecklistdata", newIds);
	}
	
	// 在删除盘点单数据的同时，也删除盘点单的单表对应的数据
	public void deletecheckthislistdata(int[] newIds) throws Exception {
		dao.delete("AssetCheckMapper.deletecheckthislistdata", newIds);
	}
	
	// 删除单表数据
	public void deletecheckdata(int[] newIds) throws Exception {
		dao.delete("AssetCheckMapper.deletecheckdata", newIds);
	}
	
	//移动端获取盘点表单名称
	public List<String> mobile_getCheckName(PageData pd) throws Exception {
		return (List<String>) dao.findForList("AssetCheckMapper.mobile_getCheckName", pd);
	}
	
	//移动端获取资产信息
	public PageData mobile_getAssetinfo(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AssetCheckMapper.mobile_getAssetinfo", pd);
	}
	
	//移动端获取盘点表单内容
	public List<PageData> mobile_getChecklist(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AssetCheckMapper.mobile_getChecklist", pd);
	}
	
	//移动端盘点
	public void mobile_check(PageData pd) throws Exception {
		dao.update("AssetCheckMapper.mobile_check", pd);
		return;
	}
}
