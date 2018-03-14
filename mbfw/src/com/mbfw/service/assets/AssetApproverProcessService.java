package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
/**
 * 
 * @author scw
 * 2017-09-17
 * function:处理流程管理的后台service
 *
 */

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetApprovalProcess;
import com.mbfw.entity.assets.AssetApprovalProcessDetail;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.QueryVo;
import com.mbfw.util.PageData;
@Service
public class AssetApproverProcessService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 获取所有的审核节点的信息
	 * @return 
	 * @throws Exception 
	 */
	public List<PageData> findListApprvalNode() throws Exception {
		
		return (List<PageData>) dao.findForList("ApprovalProcessManage.findallnodeinfo", null);
	}

	/**
	 * 插入审批流程的数据到对应的流程数据库中
	 * @param pd
	 * @throws Exception 
	 */
	public void saveProcessInfoData(PageData pd) throws Exception {
		dao.save("ApprovalProcessManage.saveprocessinfo", pd);	
	}
	
	/**
	 * 查询一共有多少条审批流程
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalNumber() throws Exception {		
		return (Integer) dao.findForObject("ApprovalProcessManage.findtotalnumber", null);
	}


	/**
	 * 插入数据到流程管理详细表中sys_process_detail
	 * @param aapd
	 * @throws Exception 
	 */
	public void saveApprovalNode(AssetApprovalProcessDetail aapd) throws Exception {
		dao.save("ApprovalProcessManage.saveapprovalnodedetail", aapd);
	}
	/**
	 * 查询所有的流程表信息（多个查询）
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findAllProcessInfo(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ApprovalProcessManage.findtotalprocessinfo", page);
	}
	/**
	 * 删除流程表中对应的流程ID
	 * @param delNumber
	 * @throws Exception 
	 */
	public void deleteProcessNode(Integer delNumber) throws Exception {
		dao.delete("ApprovalProcessManage.deleteprocessnode", delNumber);
		
	}
	
	/**
	 * 删除流程节点表中对应的流程ID
	 * @param delNumber
	 * @throws Exception 
	 */
	public void deleteProcessDetailNode(Integer delNumber) throws Exception {
		dao.delete("ApprovalProcessManage.deleteprocessdetailnode", delNumber);
		
	}
	
	/**
	 * 查询需要编辑的流程信息（单个查询）
	 * @param processId
	 * @return
	 * @throws Exception 
	 */
	public PageData findProcessInfoSimple(Integer processId) throws Exception {
		
		return (PageData) dao.findForObject("ApprovalProcessManage.findprocesssimple", processId);
	}

	/**
	 * 返回对应的流程号的节点信息
	 * @param processId
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findCurrentNode(Integer processId) throws Exception {
		
		return (List<PageData>) dao.findForList("ApprovalProcessManage.findallcurrentnode", processId);
	}
	/**
	 * 更新进行修改的流程号ID的信息
	 * @param pd
	 * @throws Exception 
	 */
	public void updateCurrentProcessInfo(PageData pd) throws Exception {
		dao.update("ApprovalProcessManage.updatecurrentprocess", pd);	
	}

	/**
	 * 根据流程号ID，将流程管理节点中对应的流程号已经存在的信息进行删除
	 * @param nodeNumber
	 * @throws Exception 
	 */
	public void deleteCurrentProcessDetailInfo(Integer nodeNumber) throws Exception {
		dao.delete("ApprovalProcessManage.deletecurrentprocessdetail", nodeNumber);
		
	}
	/**
	 * 根据流程号ID进行删除流程（批量删除）
	 * @param ids
	 * @throws Exception 
	 */
	public void deleteBatchProcess(Integer[] id) throws Exception {
		dao.delete("ApprovalProcessManage.deletebatchprocess", id);	
	}
	
	/**
	 * 根据流程号ID进行删除流程（批量删除）
	 * @param ids
	 * @throws Exception 
	 */
	public void deleteBatchProcessDetail(Integer[] id) throws Exception {
		dao.delete("ApprovalProcessManage.deletebatchprocessdetaile", id);
	}
	
	/**
	 * 显示所有的审核人员的数据条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalDataNumber() throws Exception {		
		return (Integer) dao.findForObject("ApprovalProcessManage.findTotalNumber", null);
	}
	
	/**
	 * 查询检索内容的数据总条数
	 * @param searchName 
	 * @return
	 * @throws Exception 
	 */
	public Integer findNumberBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ApprovalProcessManage.findSearchNameNumber", page);
	}

	/**
	 * 根据检索内容，查询数据库，返回符合的数据信息
	 * @param searchName
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> listSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ApprovalProcessManage.findTotalSearchInfo", page);
	}

	/**
	 * 返回所有的审批流程的信息
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findAllProcessToProject() throws Exception {
		
		return (List<PageData>) dao.findForList("ApprovalProcessManage.findAllProcessIntoToProject", null);
	}

	/**
	 * 根据流程ID号，查询在项目过程表aproject_process中是否有过程在使用中
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public Integer findCurrentProcessStatus(Integer processid) throws Exception {	
		return (Integer) dao.findForObject("ApprovalProcessManage.findCurrentProcessStatus", processid);
	}

	/**
	 * 获取在项目审批过程所有当前使用中的审批流程ID
	 * @return
	 * @throws Exception 
	 */
	public List<Integer> findAllProjectProcessIds() throws Exception {		
		return (List<Integer>) dao.findForList("ApprovalProcessManage.findAllProjectProcessIds", null);
	}
	
	
}
