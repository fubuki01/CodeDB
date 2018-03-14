package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 *2017-09-21
 *function:项目审批的service
 */
@Service
public class AssertShenpiService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查询所有的项目流程的信息（无检索内容的时候）
	 * @param page 
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findListProjectProcess(PageOption page) throws Exception {	
		return (List<PageData>) dao.findForList("ProjectProcess.approverListPage" ,page);
	}

	/**
	 * 查询数据库中的数据的条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalDataNumber() throws Exception {	
		return (Integer) dao.findForObject("ProjectProcess.findTotalNumber", null);
	}
	
	/**
	 * 查询检索内容的数据的条数 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Integer findNumberBySearchName(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ProjectProcess.findSearchNameNumber", page);
	}
	/**
	 * 根据检索内容进行查询数据条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listSearchNameApprover(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectProcess.findTotalSearchInfo", page);
	}
	/**
	 * 点击审批，返回点击项目进程的详细信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findCurrentProjectDetail(PageData pd) throws Exception {	
		return (PageData) dao.findForObject("ProjectProcess.findCurrentProjectDetail", pd);
	}
	/**
	 * 插入具体的审批详细数据到项目审批细节表中
	 * @param pd 
	 * @throws Exception 
	 */
	public void saveProjectProceeDetail(PageData pd) throws Exception {
		dao.save("ProjectProcess.saveDetailProjectProcess", pd);
		
	}

	/**
	 * 获取当前项目ID是否已经在项目描述表中
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalCurrentProjectNumber(PageData pd) throws Exception {	
		return (Integer) dao.findForObject("ProjectProcess.findCurrentProjectNumber", pd);
	}

	/**
	 * 查询对应的项目流程中通过的人数
	 * @param string 
	 * @param string
	 * @return
	 * @throws Exception 
	 */
	public Integer findProjectDescriptionPassNumber(PageData pd) throws Exception {
	
		return (Integer) dao.findForObject("ProjectProcess.findDescriptionPassNumber", pd);
	}

	/**
	 * 获取当前项目流程，当前审批流程，当前审批节点的所有信息
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findRelativeInfo(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("ProjectProcess.findRelativeInfoProject", pd);
	}

	/**
	 * 添加相应的数据到项目过程描述表中
	 * @param pd
	 * @throws Exception 
	 */
	public void saveProjectProcessDescription(PageData pd) throws Exception {
		dao.save("ProjectProcess.saveProjectDescriptionInfo", pd);
		
	}
	/**
	 * 更新项目过程表中对应的通过的人数字段
	 * @param pd
	 * @throws Exception 
	 */
	public void editProjectProcess(PageData pd) throws Exception {
		dao.update("ProjectProcess.editDescriptionPassnumber", pd);
		
	}
	/**
	 * 通过节点顺序来判断是否在当前的审批流程中还有后续节点
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public PageData findIfNextApprovalNode(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("ProjectProcess.findNextApprovalInfo", pd);
	}
	/**
	 * 修改项目过程中的完成状态和通过人数
	 * @param pd
	 * @throws Exception 
	 */
	public void editProjectProcessStatus(PageData pd) throws Exception {
		dao.update("ProjectProcess.editProjectFishAndPass", pd);
		
	}
	/**
	 * 修改项目过程中的当前节点和通过人数
	 * @param pd
	 * @throws Exception 
	 */
	public void editProjectProcessCurrentNode(PageData pd) throws Exception {
		dao.update("ProjectProcess.editProjectNodeInfo", pd);
	}

	/**
	 * 判断当前的过程是否是处于完成状态
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public String findCurrentProjectIfFinish(PageData pd) throws Exception {
		return (String) dao.findForObject("ProjectProcess.findCurrentIfFinishStatus", pd);
	}

	/**
	 * 根据项目ID和用户ID
	 * 判断在项目审批细节表中，是否有该用户的操作信息，返回非0，则表示已经进行过处理
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public Integer findCurrentUserStatus(PageData pd) throws Exception {
		
		return (Integer) dao.findForObject("ProjectProcess.findCurrentUserIfOption", pd);
	}

	/**
	 * 获取当前项目流程中的不通过的人数
	 * @param parseInt
	 * @return
	 * @throws Exception 
	 */
	public int findProjectDescriptionRefuseNumber(PageData pd) throws Exception {	
		return (int) dao.findForObject("ProjectProcess.findDescriptionRefuseNumber", pd);
	}
	/**
	 * 根据项目流程ID，更新项目过程表中对应的不通过的人数
	 * @param pd
	 * @throws Exception 
	 */
	public void editProjectProcessRefuseNumber(PageData pd) throws Exception {
		dao.update("ProjectProcess.editDescriptionRefusenumber", pd);	
	}
	/**
	 * 根据项目流程ID，更新不通过人数和是否完成状态
	 * @param pd
	 * @throws Exception 
	 */
	public void editProjectProcessStatusFinished(PageData pd) throws Exception {
		dao.update("ProjectProcess.editProjectFishAndRefuse", pd);	
	}

	/**
	 * 根据项目ID，修改项目审批的结果
	 * @param projectId:各自项目的ID ， currentProcessType:项目的类型 ， approResult：审批的结果---审批通过并且是否通过
	 * @throws Exception 
	 */
	public void editAprojectManagerStatus(Integer projectId ,String currentProcessType ,String approResult) throws Exception {
		PageData content = new PageData();		
		if("项目立项".equals(currentProcessType)){	
			content.put("apply_id", projectId);
			content.put("apply_status", approResult);
			dao.update("ProjectProcess.editProjectManageStatus", content);	
		}
		else if("耗材申请".equals(currentProcessType)){
			content.put("id", projectId);
			content.put("state", approResult);
			dao.update("ProjectProcess.editProjectManageSuppliesStatus",content );
		}
		else if("报废申请".equals(currentProcessType)){
			content.put("id", projectId);
			content.put("approve_status", approResult);
			dao.update("ProjectProcess.editProjectManageAbandonStatus",content );
		}
		else if("资产调入与借出".equals(currentProcessType)){
			content.put("id", projectId);
			content.put("status", approResult);
			dao.update("ProjectProcess.editProjectManageAssetStatus",content );
		}
	}

	/**
	 * 根据项目进程ID，更新项目过程表中的通过和不通过的人数信息全部为0，还有审批状态为未完成
	 * @param projectId
	 * @throws Exception 
	 */
	public void editCurrentProjectProcessContent(PageData pd) throws Exception {
		dao.update("ProjectProcess.editProjectContent", pd);
		
	}

	/**
	 * 根据项目进程ID，删除所有的项目明细表中的数据
	 * @param projectId
	 * @throws Exception 
	 */
	public void editDeletProjectProcessDeatil(Integer projectId) throws Exception {
		dao.delete("ProjectProcess.deleteProjectProcessDetail", projectId);		
	}

	/**
	 * 根据审批人员ID，删除对应的项目明细表中之前的操作信息
	 * @param deleteDetail
	 * @throws Exception 
	 */
	public void editProjectProcessDetailByUserID(PageData pd) throws Exception {
		dao.delete("ProjectProcess.deleteDetailByUserId", pd);		
	}

	/**
	 * 根据项目流程ID，更新项目流程的审批顺序的字段和通过人数和不通过人数信息
	 * @param projectId
	 * @throws Exception 
	 */
	public void editProjectProcessLastOrderInfo(Integer projectId) throws Exception {
		dao.update("ProjectProcess.editProjectProcessOrder", projectId);		
	}

	/**
	 * 根据项目流程ID，更新当前节点的名字
	 * @param projectId
	 * @throws Exception 
	 */
	public void editProjectProcessNodeName(PageData pd) throws Exception {
		dao.update("ProjectProcess.editProjectProcessNodeName", pd);		
	}

	/**
	 * 根据项目ID号修改当前的项目的审核状态为“审核中”
	 * @param currentProjecttId
	 * @throws Exception 
	 */
	public void editProjectManagerStatus(Integer currentProjecttId , String currentProcessType) throws Exception {
		if("项目立项".equals(currentProcessType)){
			dao.update("ProjectProcess.editProjectManageStatusOther",currentProjecttId );	
		}
		else if("耗材申请".equals(currentProcessType)){
			dao.update("ProjectProcess.editProjectManageStatusSupplies",currentProjecttId );
		}
		else if("报废申请".equals(currentProcessType)){
			dao.update("ProjectProcess.editProjectManageStatusAbandon",currentProjecttId );
		}
		else if("资产调入与借出".equals(currentProcessType)){
			dao.update("ProjectProcess.editProjectManageStatusAsset",currentProjecttId );
		}
	}
	
	/**
	 * 根据项目ID，更新流程号ID和流程名字（主要是用于项目立项中当审批未开始之前进行项目修改）
	 * @param pd
	 * @throws Exception 
	 */
	public void editFromOthersProjectOption(PageData pd) throws Exception{
		dao.update("ProjectProcess.updateOthersProjectOption", pd);
	}

	/**
	 * 根据审批项目中的ID和类型进行返回对应的数据
	 * @param current
	 * @return
	 * @throws Exception 
	 */
	public PageData findDifferentContent(PageData current) throws Exception {
		PageData backContent = new PageData();
		//判断一下需要进行显示的数据是属于哪个表里面(因为审批项目中可能是在资产，报废和维修表中)
		if("项目立项".equals(current.getString("process_Type"))){
			backContent = (PageData) dao.findForObject("ProjectProcess.findProjectManager", current);
		}
		else if("耗材申请".equals(current.getString("process_Type"))){
			backContent = (PageData) dao.findForObject("ProjectProcess.findProjectSuppliesApplication", current);
		}
		else if("报废申请".equals(current.getString("process_Type"))){
			backContent = (PageData) dao.findForObject("ProjectProcess.findProjectAssetAbandon", current);
		}
		else if("资产调入与借出".equals(current.getString("process_Type"))){
			backContent = (PageData) dao.findForObject("ProjectProcess.findProjectAssetAsset", current);
		}
		return backContent;
	}

	/**
	 * 根据流程号ID和当前审批次序，获取到对应的审批节点中审批人员的信息
	 * @param resultContent
	 * @return
	 * @throws Exception 
	 */
	public List<String> findAllCurrentNodePeopleInfo(PageData pd) throws Exception {
		return (List<String>) dao.findForList("ProjectProcess.findAllNodePeopleInfo",pd);
	}

	/**
	 * 获取当前操作项目流程的当前的审批节点的ID
	 * @param pd
	 * @return
	 * @throws Exception 
	 */
	public Integer findCurrentNodeId(PageData pd) throws Exception {		
		return (Integer) dao.findForObject("ProjectProcess.findCurrentNodeId", pd);
	}

	/**
	 * 当重新返回到第一级流程处理的时候，需要将各个项目中的状态设置为“已提交”，这样能够方便各个项目对内容进行修改
	 * @param currentProjecttId：各个需要审批的项目ID
	 * @param currentProcessType：需要审批的项目的类型，是项目立项还是耗材申请，还是报废申请等
	 * @throws Exception 
	 */
	public void editAgainDifferenProjectStatus(Integer currentProjecttId, String currentProcessType) throws Exception {
		//判断属于的是什么审批项目，这样处理的表也就会不一样
		//判断一下需要进行显示的数据是属于哪个表里面(因为审批项目中可能是在资产，报废和维修表中)
		if ("项目立项".equals(currentProcessType)) {
			dao.update("ProjectProcess.updateProjectManageStatusAgain", currentProjecttId);
		} else if ("耗材申请".equals(currentProcessType)) {
			dao.update("ProjectProcess.updateProjectSuppliesStatusAgain", currentProjecttId);
		} else if ("报废申请".equals(currentProcessType)) {
			dao.update("ProjectProcess.updateProjectAbandonStatusAgain", currentProjecttId);
		}else if ("资产调入与借出".equals(currentProcessType)) {
			dao.update("ProjectProcess.updateProjectAssetStatusAgain", currentProjecttId);
		}
	}

	/**
	 * 根据审批流程ID，查询到所有的审批人员的userID
	 * @param integer
	 * @return
	 * @throws Exception 
	 */
	public List<String> findAllRightProjectByUserId(Integer processId) throws Exception {
		return (List<String>) dao.findForList("ProjectProcess.findCurrentProcessAllApprovUserids", processId);
	}
}
