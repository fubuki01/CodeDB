package com.mbfw.service.assets;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetTableManageService")
public class AssetTableManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 查询补齐所需要的信息
	public List<PageData> find_AssetCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetTableManageMapper.find_AssetCode", pd);
	}

	// 查询所有  回收  时可获取的状态信息的资产编码
	public List<PageData> find_GetAssetCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetTableManageMapper.find_GetAssetCode", pd);
	}
	
	// 查询所有  领用  时可获取的状态信息的资产编码
	public List<PageData> find_AllAssetCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetTableManageMapper.find_AllAssetCode", pd);
	}
	
	// 查询所有  领用  时可获取的状态信息的资产编码
	public List<PageData> find_IdleAssetCode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetTableManageMapper.find_IdleAssetCode", pd);
	}
		
	//回显闲置状态的信息
	public PageData findAssetInfo_byIdleStatus(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_byIdleStatus", id);
	}
	
	//回显领用状态的信息
	public PageData findAssetInfo_byGetStatus(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_byGetStatus", id);
	}
	
	//回显回收状态的信息
	public PageData findAssetInfo_byRecycleStatus(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_byRecycleStatus", id);
	}
	
	//回显维修状态的信息
	public PageData findAssetInfo_byMaintainStatus(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_byMaintainStatus", id);
	}

	//填写领用表时，回显下发状态的信息
	public PageData findAssetInfo_GetInfo(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_GetInfo", id);
	}
	
	//填写回收表时，回显回收状态的信息
	public PageData findAssetInfo_RecycleInfo(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findAssetInfo_RecycleInfo", id);
	}
	
	//回显盘点需要的信息
	public PageData findInfoBy_CheckInfo(String id) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.findInfoBy_CheckInfo", id);
	}
	
	//判断盘点名称是否重复
	public PageData find_CheckName(String name) throws Exception{
		return (PageData) dao.findForObject("AssetTableManageMapper.find_CheckName", name);
	}
	
	//改变资产状态
	public void changeAssetStatus(PageData pd) throws Exception {
		dao.save("AssetTableManageMapper.changeAssetStatus", pd);
	}
}
