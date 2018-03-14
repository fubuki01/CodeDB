package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetClassify;
import com.mbfw.entity.system.Menu;
import com.mbfw.util.PageData;

/**
 * 固定资产
 * 自定义资产类别Service
 * @author Wyd
 *
 */
@Service("AssetClassify")
public class AssetClassifyService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 增加自定义资产类别节点
	 */
	public void addPoint(PageData pd) throws Exception {
		dao.save("AssetClassifyMapper.addPoint", pd);
	}
	
	/*
	 * 查找父节点对应的所有孩子节点
	 */
	public List<PageData> listChildPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetClassifyMapper.listChildPoint", pd);
	}
	/*
	 * 查询对应的父节点以获取对应的序列号
	 */
	public PageData getParentPoint(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AssetClassifyMapper.getParentPoint", pd);
	}
	
	/*
	 * 增加自定义资产类别子节点
	 */
	public void addChildPoint(PageData pd) throws Exception {
		dao.save("AssetClassifyMapper.addChildPoint", pd);
	}
	
	/*
	 * 列出所有节点
	 */
	public List<PageData> listAllPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetClassifyMapper.listAllPoint", pd);
	}
	
	/*
	 * 列出所有父类节点
	 */
	public List<PageData> listAllParentPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetClassifyMapper.listAllParentPoint", pd);
	}
	
	/*
	 * 删除节点
	 */
	public void DelectPoint(PageData pd) throws Exception {
		dao.delete("AssetClassifyMapper.delectPoint", pd);
	}
	
	/*
	 * 查询的是表products_info
	 * 查询节点是否被使用
	 */
	public Boolean SearchPointZi(PageData pd) throws Exception {
		List<PageData> pds = (List<PageData>) dao.findForList("AssetClassifyMapper.searchPointZi", pd);
		if(pds.size()!=0){
			return true;
		}
		return false;
	}
	
	/*
	 * 查询的是表products_info
	 * 查询节点是否被使用
	 */
	public Boolean SearchPointFu(PageData pd) throws Exception {
		List<PageData> pds = (List<PageData>) dao.findForList("AssetClassifyMapper.searchPointFu", pd);
		if(pds.size()!=0){
			return true;
		}
		return false;
	}
	
	/*
	 * 
	 * 根据number查询
	 */
	public PageData FindByNumber(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AssetClassifyMapper.findByNumber", pd);
	}
	
	/*
	 * 根据类别名称查询是否存在
	 */
	public PageData findByClass(PageData pd) throws Exception {
		PageData pds = (PageData) dao.findForObject("AssetClassifyMapper.findByClass", pd);
		return pds;
	}
	
	/*
	 * 根据资产名称查询是否存在
	 */
	public PageData findByAssetName(PageData pd) throws Exception {
		PageData pds = (PageData) dao.findForObject("AssetClassifyMapper.findByAssetName", pd);
		return pds;
	}

}
