package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetIssued;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service /// 接受服务
public class AssertReceivedService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * @param page 查找闲置资产以用来下发
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_for_received(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetReceivedMapper.find_asset_for_received", page);
	}
	
	/**
	 * @param pd 查找可以下发的总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_for_received_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetReceivedMapper.find_asset_for_received_totalnumber", pd);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_for_received_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetReceivedMapper.find_asset_for_received_bykey_totalnumber", page);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的记录
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_for_received_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetReceivedMapper.find_asset_for_received_bykey", page);
	}
	/**
	 * @param pd 修改下发单状态
	 * @throws Exception
	 */
	public void update_issue_bill_status(AssetIssued pd) throws Exception{
		dao.update("AssetReceivedMapper.update_issue_bill_status", pd);
	}
	
	/**
	 * @param id 修改资产状态
	 * @throws Exception
	 */
	public void update_asset_status(PageData pd) throws Exception{
		dao.update("AssetReceivedMapper.update_asset_status", pd);
	}
	/**
	 * 
	 * @param pd保存接收单
	 * @throws Exception
	 */
	public void save_issued_receive(AssetIssued pd) throws Exception{
		dao.save("AssetReceivedMapper.save_issued_receive", pd);
	}
	
	/**
	 * 
	 * @param pd 当下发单处于接收状态，修改下发单状态
	 * @throws Exception
	 */
	public void update_issue_bill_receive(AssetIssued pd) throws Exception{
		dao.update("AssetReceivedMapper.update_issue_bill_receive", pd);
	}
}
