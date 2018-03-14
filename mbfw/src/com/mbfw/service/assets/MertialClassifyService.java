package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.MertialClassify;
import com.mbfw.util.PageData;

/**
 * 自定义耗材类别Service
 * @author Wyd
 *
 */
@Service("MertialClassify")
public class MertialClassifyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	 * 增加自定义耗材类别节点
	 */
	public void addPoint(PageData pd) throws Exception {
		dao.save("MertialClassifyMapper.addPoint", pd);
	}
	
	/*
	 * 查找父节点对应的所有孩子节点
	 */
	public List<PageData> listChildPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MertialClassifyMapper.listChildPoint", pd);
	}
	
	/*
	 * 查询对应的父节点以获取对应的序列号
	 */
	public PageData getParentPoint(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MertialClassifyMapper.getParentPoint", pd);
	}
	
	/*
	 * 列出所有父类节点
	 */
	public List<PageData> listAllParentPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MertialClassifyMapper.listAllParentPoint", pd);
	}
	
	/*
	 * 增加自定义资产类别子节点
	 */
	public void addChildPoint(PageData pd) throws Exception {
		dao.save("MertialClassifyMapper.addChildPoint", pd);
	}
	
	/*
	 * 列出所有节点
	 */
	public List<PageData> listAllPoint(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MertialClassifyMapper.listAllPoint", pd);
	}
	
	/*
	 * 删除节点
	 */
	public void DelectPoint(PageData pd) throws Exception {
		dao.delete("MertialClassifyMapper.delectPoint", pd);
	}
	
	
	/*
	 * 查询节点是否被使用
	 */
	public Boolean SearchPointByZi(PageData pd) throws Exception {
		List<PageData> pds = (List<PageData>) dao.findForList("MertialClassifyMapper.searchPointByZi", pd);
		if(pds.size()!=0){
			return true;
		}
		return false;
	}
	
	/*
	 * 查询节点是否被使用
	 */
	public Boolean SearchPointByFu(PageData pd) throws Exception {
		List<PageData> pds = (List<PageData>) dao.findForList("MertialClassifyMapper.searchPointByFu", pd);
		if(pds.size()!=0){
			return true;
		}
		return false;
	}
	
	// 耗材类别查询
	public List<PageData> find_used_class(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("MertialClassifyMapper.find_used_class", pd);
	}
	
	/*
	 * 根据类别名称查询是否存在
	 */
	public PageData findByClass(PageData pd) throws Exception {
		PageData pds = (PageData) dao.findForObject("MertialClassifyMapper.findByClass", pd);
		return pds;
	}
	
	/*
	 * 根据耗材名称查询是否存在
	 */
	public PageData findByMertialName(PageData pd) throws Exception {
		PageData pds = (PageData) dao.findForObject("MertialClassifyMapper.findByMertialName", pd);
		return pds;
	}
}
