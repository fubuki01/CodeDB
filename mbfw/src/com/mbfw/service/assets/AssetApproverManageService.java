package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 * 2017.09.13
 * function：管理审核人员信息Service
 *
 */
@Service
public class AssetApproverManageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 显示所有的审核人员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPdPageApprover(PageOption page) throws Exception{	
		return (List<PageData>) dao.findForList("ApproverManage.approverListPage" ,page);
	}
	/**
	 * 根据userId来进行删除用户信息
	 * @param nodestr
	 * @throws Exception 
	 */
	public void deleteApproverById(String nodestr) throws Exception {
		dao.delete("ApproverManage.deleteApprover", nodestr);		
	}
	/**
	 * 根据ID来进行显示对应的审核人信息，进行编辑
	 * @param pd
	 * @throws Exception 
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("ApproverManage.findByUiId" , pd);
		
	}
	
	/**
	 * 根据userid进行更新审核表
	 * @param pd
	 * @throws Exception 
	 */
	public void editApprover(PageData pd) throws Exception {
		dao.update("ApproverManage.editApprover" , pd);
		
	}
	
	/**
	 * 根据审批人员表中的用户ID进行批量删除审批人员信息
	 * @param ids
	 * @throws Exception 
	 */
	public void deleteBatchApprover(String[] ids) throws Exception {
		dao.delete("ApproverManage.deleteBatchApprover", ids);
	}
	
	/**
	 * 从所有的用户中添加审核人员
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findAllApproverByUser() throws Exception {
		return (List<PageData>) dao.findForList("ApproverManage.addApproverByUser", null);
	}
	
	/**
	 * 通过jsp传送过来的数据，添加一个用户信息到审核人员表中
	 * @param pd
	 * @throws Exception 
	 */
	public void saveApproverOne(PageData pd) throws Exception {
		dao.save("ApproverManage.addApproverOne", pd);
	}
	
	/**
	 * 根据用户ID，判断是否已经存在在审核人员数据库中
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public Integer findCurrentApproverStatus(PageData pd) throws Exception {
	
		return (Integer) dao.findForObject("ApproverManage.findCurrentInfo", pd);
	}
	
	/**
	 * 显示所有的审核人员的数据条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalDataNumber() throws Exception {		
		return (Integer) dao.findForObject("ApproverManage.findTotalNumber", null);
	}
	
	/**
	 * 查询检索姓名内容的数据总条数
	 * @param searchName 
	 * @return
	 * @throws Exception 
	 */
	public Integer findNumberBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ApproverManage.findSearchNameNumber", page);
	}
	
	/**
	 * 根据检索内容，查询数据库，返回所有的审核人员的数据信息
	 * @param searchName
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ApproverManage.findTotalSearchInfo", page);
	}
	
	/**
	 * 返回所有的审核人员的信息（不分页的显示）
	 * @return
	 * @throws Exception 
	 */
	public List<String> findAllApproverInfoToUser() throws Exception{
		return (List<String>) dao.findForList("ApproverManage.findAllApproverInfoToUser", null);
	}
	/**
	 * 模糊匹配用户姓名，返回所有的检索用户的信息
	 * @param checkStr
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findResearchContentResult(String checkStr) throws Exception {	
		return (List<PageData>) dao.findForList("ApproverManage.findResearchResultName", checkStr);
	}
	
	/**
	 * 当点击删除的时候，判断该节点能否被删除（关键看该审核人员是否存在于某个审批节点sys_approvnodepeople表）
	 * @param currentNodeId
	 * @return
	 * @throws Exception 
	 */
	public Integer findCurrenApproverStatus(String currentUserId) throws Exception {
		return (Integer) dao.findForObject("ApproverManage.findCurrentApproverDeleteStatus", currentUserId);
	}
	
	/**
	 * 获取审批节点中所有的审批人员的ID，用于判断是否能够进行批量删除的操作
	 * @return
	 * @throws Exception 
	 */
	public List<String> findAllNodeApproverUserIds() throws Exception {	
		return (List<String>) dao.findForList("ApproverManage.findAllNodeApproverUserIds", null);
	}
}
