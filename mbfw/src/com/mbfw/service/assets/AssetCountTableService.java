package com.mbfw.service.assets;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetCountTable;
import com.mbfw.util.PageData;

@Service("assetCountTableService")
public class AssetCountTableService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	public List<AssetCountTable> findAssetCountTableByDept(PageData pd) throws Exception{
		return (List<AssetCountTable>) dao.findForList("AssetCountTableMapper.findAssetCountTableByDept", pd);
	}
	
	public List<AssetCountTable> findAssetCountTableByDeptAndCond(PageData pd) throws Exception{
		return (List<AssetCountTable>) dao.findForList("AssetCountTableMapper.findAssetCountTableByDeptAndCond", pd);
	}
	
	public Integer findTotalDataNumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetCountTableMapper.findTotalDataNumber", pd);
	}
	
	public BigDecimal findToTalAssetPriByDept(PageData pd) throws Exception{
		BigDecimal  v =  (BigDecimal) dao.findForObject("AssetCountTableMapper.findToTalAssetPriByDept", pd);
		return v != null ? v : new BigDecimal(0); 
	}
}
