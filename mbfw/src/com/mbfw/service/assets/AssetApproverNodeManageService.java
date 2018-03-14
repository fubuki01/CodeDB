package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetApproNodePeople;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 *2017-09-14
 *function：处理审核节点的操作
 */
@Service
public class AssetApproverNodeManageService {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 插入数据到审核节点表中
	 * @param pageData
	 * @throws Exception 
	 */
	public void saveApproNode(PageData pd) throws Exception {
		dao.save("ApproverNode.saveapprovnode", pd);	
	}

	/**
	 * 插入数据到节点管理表中
	 * @throws Exception 
	 */
	public void saveApproNodeToPeople(PageData pd) throws Exception {
		dao.save("ApproverNode.savenodetopeople", pd);
	}
	/**
	 * 查询节点表中一共有多少条数据，
	 * @throws Exception 
	 * @return：返回数据条数
	 */
	public Integer findNumberNode() throws Exception {
		Integer number = (Integer) dao.findForObject("ApproverNode.findnodenumber", null);
		return number;
	}

	/**
	 * 查询数据库，显示所有的节点信息
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listApproNode(PageOption page) throws Exception {
		
		return (List<PageData>) dao.findForList("ApproverNode.listapprovnode", page);
	}
	/**
	 * 查询数据库，返回所有的审核人信息
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listApprover() throws Exception{
		return (List<PageData>) dao.findForList("ApproverNode.listapprover", null);
	}

	/**
	 * 根据审批节点的ID，进行删除操作
	 * @param nodestr：删除审核节点ID
	 * @throws Exception 
	 */
	public void deleteSimpleNode(Integer nodestr) throws Exception {
		dao.delete("ApproverNode.deletesimplenode", nodestr);	
	}
	
	/**
	 * 根据审批节点的ID，进行删除操作
	 * @param nodestr：删除审核节点管理员ID
	 * @throws Exception 
	 */
	public void deleteSimpleNodePeople(Integer nodestr) throws Exception {
		dao.delete("ApproverNode.deletesimplenodepeople", nodestr);	
	}

	/**
	 * 根据审核节点Id，返回详细的信息,联表查询
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	public PageData findDetailNodeInfo(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("ApproverNode.findnodeinfo", pd);
	}
	
	/**
	 * 查询对应的节点中的userid中的审核人员表中的人的姓名
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findApproverInfo(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ApproverNode.findApproverNodePeopleInfo", pd);	
	}

	/**
	 * 进行更新查看节点内容修改后的操作
	 * @param pageData
	 * @throws Exception 
	 */
	public void editApproNode(PageData pd) throws Exception {
		dao.update("ApproverNode.updateEditNodeInfo",pd);	
	}

	/**
	 * 根据审核节点ID进行删除之前存在的审核人员数据，达到更新效果
	 * @param currentNodeId
	 * @throws Exception 
	 */
	public void deleteLastApprover(Integer currentNodeId) throws Exception {
		dao.delete("ApproverNode.deleteApproverNodePeople", currentNodeId);
		
	}

	
	/**
	 * 根据节点ID进行批量删除审批节点数据
	 * @param ids
	 * @throws Exception 
	 */
	public void deleteBatchApprovalNode(Integer[] ids) throws Exception {
		dao.delete("ApproverNode.deleteBatchApprovalNode", ids);	
	}
	
	/**
	 * 根据节点ID进行批量删除审批节点数据
	 * @param ids
	 * @throws Exception 
	 */
	public void deleteBatchApprovalNodePeople(Integer[] ids) throws Exception {
		dao.delete("ApproverNode.deleteBatchApprovalNodePeople", ids);	
	}
	
	/**
	 * 显示所有的审批的数据条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalDataNumber() throws Exception {		
		return (Integer) dao.findForObject("ApproverNode.findTotalNumber", null);
	}

	/**
	 * 查询检索内容的数据总条数
	 * @param searchName 
	 * @return
	 * @throws Exception 
	 */
	public Integer findNumberBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ApproverNode.findSearchNameNumber", page);
	}
	
	/**
	 * 根据检索内容，查询数据库，返回所有的审核人员的数据信息
	 * @param searchName
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ApproverNode.findTotalSearchInfo", page);
	}
		
	/**
	 * 根据节点ID，返回所有的审核人员的userid
	 * @param nodeid
	 * @return
	 * @throws Exception
	 */
	public List<String> findAllUserIdByNodeId(Integer nodeid) throws Exception{
		return (List<String>) dao.findForList("ApproverNode.findAllApproverUserId", nodeid);
	}

	/**
	 * 根据节点ID，判断是否存在于某个审批流程中
	 * @param currentNodeId 当前审批节点ID
	 * @return 返回查找审批流程中包含该审批节点的条目数，如果为0，则表示没有审批流程使用该审批节点
	 * @throws Exception 
	 */
	public Integer findCurrenNodeStatus(Integer currentNodeId) throws Exception {
		return (Integer) dao.findForObject("ApproverNode.findCurrentNodeStatus", currentNodeId);
	}

	/**
	 * 获取所有的审批流程中的审批节点的ID
	 * @return
	 * @throws Exception 
	 */
	public List<Integer> findAllProcessNodeIds() throws Exception {	
		return (List<Integer>) dao.findForList("ApproverNode.findAllProcessNodeIds", null);
	}
}
		