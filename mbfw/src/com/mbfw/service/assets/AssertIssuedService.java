package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetIssued;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

/**
 * 
 * @author lss
 *2017-09-21
 *function:资产下发的service
 */
@Service
public class AssertIssuedService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * @param page 查找闲置资产以用来下发
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_for_issued(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetIssueMapper.find_asset_for_issued", page);
	}
	
	/**
	 * @param pd 查找可以下发的总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_for_issued_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetIssueMapper.find_asset_for_issued_totalnumber", pd);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_for_issued_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetIssueMapper.find_asset_for_issued_bykey_totalnumber", page);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的记录
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_for_issued_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetIssueMapper.find_asset_for_issued_bykey", page);
	}
	
	/**
	 * @param page 查询下发单，分页显示
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_issued_bill(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetIssueMapper.find_asset_issued_bill", page);
	}
	
	/**
	 * @param page 查询下发单总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_issued_bill_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetIssueMapper.find_asset_issued_bill_totalnumber", pd);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_issued_bill_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetIssueMapper.find_asset_issued_bill_bykey_totalnumber", page);
	}
	
	/**
	 * @param pd 根据关键字查找可以下发的记录
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_issued_bill_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetIssueMapper.find_asset_issued_bill_bykey", page);
	}
	
	/**
	 * @param pd 查询下发机构
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllInfo(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo", pd);
	}
	
	/**
	 * @param pd 保存下发单
	 * @throws Exception
	 */
	public void save_asset_issued_bill(AssetIssued pd) throws Exception{
		dao.save("AssetIssueMapper.save_asset_issued_bill", pd);
	}
	
	/**
	 * @param id 修改资产状态
	 * @throws Exception
	 */
	public void update_asset_status(Integer id) throws Exception{
		dao.update("AssetIssueMapper.update_asset_status", id);
	}
	
	/**
	 * @param id 修改下发单状态
	 * @throws Exception
	 */
	public void update_asset_issue_bill_status(AssetIssued pd) throws Exception{
		dao.update("AssetIssueMapper.update_asset_issue_bill_status", pd);
	}
	
	/**
	 * @param pd 根据id查找下发单
	 * @return
	 * @throws Exception
	 */
	public PageData find_issued_bill_by_id(Integer id) throws Exception{
		return (PageData) dao.findForObject("AssetIssueMapper.find_issued_bill_by_id", id);
	}
	
	/**
	 * @param pd 删除单个下发单
	 * @throws Exception
	 */
	public void delete_asset_issue_bill(PageData pd) throws Exception{
		dao.delete("AssetIssueMapper.delete_asset_issue_bill", pd);
	}
	
	/**
	 * 批量删除下发单
	 */
	public void delete_all_asset_issue_bill(String[] delassetissuebill) throws Exception {
		dao.delete("AssetIssueMapper.delete_all_asset_issue_bill", delassetissuebill);
	}
	
	/**
	 * 移动端下发查询
	 */
	public List<PageData> mobile_issue_query(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetIssueMapper.mobile_issue_query", pd);
	}
	
	/**
	 * 
	 * @param pd 下发中的下发单，删除时恢复资产表的状态为闲置
	 * @throws Exception 
	 */
	public void update_issue_asset_status(PageData pd) throws Exception{
		dao.update("AssetIssueMapper.update_issue_asset_status", pd);
	}
	
	/**
	 * 
	 * @param pd接收中的状态，充值下发表的接收内容
	 * @throws Exception 
	 */
	public void update_issue_status(PageData pd) throws Exception{
		dao.update("AssetIssueMapper.update_issue_status", pd);
	}
	
	/**
	 * 
	 * @param pd 下发中的下发单，删除时恢复资产表的状态为下发
	 * @throws Exception
	 */
	public void update_receive_asset_status(PageData pd) throws Exception{
		dao.update("AssetIssueMapper.update_receive_asset_status", pd);
	}
}
