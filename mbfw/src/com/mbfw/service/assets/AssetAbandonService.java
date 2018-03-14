package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetAbandonManage;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetAbandonService")
public class AssetAbandonService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 显示分页所有数据
	public List<PageData> listPdAbandonPageApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetAbandonMapper.approverAbandonListPage", page);
	}

	public AssetAbandonManage findAbandonEditData(PageData pd) throws Exception {
		return (AssetAbandonManage) dao.findForObject("AssetAbandonMapper.abandon_edit", pd);
	}

	// 保存用户
	public void saveAbandonData(PageData pd) throws Exception {
		dao.save("AssetAbandonMapper.saveAbandonData", pd);
	}

	// 更改有效值
	public void changeValue(PageData pd) throws Exception {
		if (pd.getString("asset_status").equals("领用")) {
			dao.update("AssetAbandonMapper.changeGetValue", pd);
		}
		if (pd.getString("asset_status").equals("回收")) {
			dao.update("AssetAbandonMapper.changeRecycleValue", pd);
		}
	}

	// 批量删除
	public void deleteAllAbandonData(int[] Allot_ids) throws Exception {
		dao.delete("AssetAbandonMapper.deleteallabandondata", Allot_ids);
	}

	// 删除后返回到原来的有效状态(true)状态
	public void returnValid(PageData pd) throws Exception {
		if (pd.getString("orig_status").equals("领用")) {
			dao.update("AssetAbandonMapper.returnGetValid", pd);
		}
		if (pd.getString("orig_status").equals("回收")) {
			dao.update("AssetAbandonMapper.returnRecycleValid", pd);
		}
	}

	// 删除前查找领用表的原始状态信息
	public PageData searchStatusInfo(int s) throws Exception {
		return (PageData) dao.findForObject("AssetAbandonMapper.searchStatusInfo", s);
	}

	// 删除操作后，还原到以前的状态
	public void returnPriorStatus(PageData pd) throws Exception {
		if (pd.getString("orig_status").equals("领用")) {
			dao.update("AssetAbandonMapper.returnGetStatus", pd);
		}
		if (pd.getString("orig_status").equals("回收")) {
			dao.update("AssetAbandonMapper.returnRecycleStatus", pd);
		}
	}

	//删除操作后，把审批流程也删除
	public  void deleteApproveProcess(PageData pd) throws Exception {
		dao.delete("AssetAbandonMapper.deleteApproveProcess", pd);
	}

	// 更新
	public void editAbandonApprover(PageData pd) throws Exception {
		dao.update("AssetAbandonMapper.editAbandonApprover", pd);
	}

	// 分页显示数据条数
	public Integer findTotalAbandonDataNumber(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetAbandonMapper.findTotalAbandonNumber", page);
	}

	// 查询检索姓名内容的数据总条数
	public Integer findNumberAbandonBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("AssetAbandonMapper.findAbandonSearchNameNumber", page);
	}

	// 根据检索内容，查询数据库，返回所有的的数据信息
	public List<PageData> listAbandonSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("AssetAbandonMapper.findAbandonTotalSearchInfo", page);
	}

	// 移动端缺省查询
	public List<AssetAbandonManage> findByDefault(PageData page) throws Exception {
		return (List<AssetAbandonManage>) dao.findForList("AssetAbandonMapper.findByDefault", page);
	}

	// 查询资产数量
	public Integer checkAssetCount(PageData pd) throws Exception {
		return (Integer) dao.findForObject("AssetAbandonMapper.checkAssetCount", pd);
	}
}
