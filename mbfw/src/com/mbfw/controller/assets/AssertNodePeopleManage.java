package com.mbfw.controller.assets;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetApproNodePeople;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.service.assets.AssetApproverNodeManageService;
import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 * 2017-09-14
 * function：处理关于审批节点的操作
 *
 */

@Controller
@RequestMapping(value="/asset")
public class AssertNodePeopleManage extends BaseController{
	
	@Autowired
	private AssetApproverNodeManageService aanms ; //处理审核节点的service
	
	/**
	 * 显示所有的审批节点
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value ="/asystem_showallnode")	
	public ModelAndView showAllNode( ) throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> 	listPage = new ArrayList<PageData>();	

		//----------分页操作开始----------
		PageOption page = new PageOption(5, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if(pd.getString("showCount") != null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//------没有进行检索的处理-----
				if(pd.getString("retrieve_content") == null || pd.getString("retrieve_content") == ""){
		//(3)查询数据库中数据的总条数
		Integer totalnumber = aanms.findTotalDataNumber();
		//(4)设置总的数据条数
		page.setTotalResult(totalnumber); 
		//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
		page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
		//(6)查询数据库，返回对应条数的数据
		 listPage = aanms.listApproNode(page);
		//----------分页结束------------------------------
		}
		else{
			//(3)获取传送过来的进行检索的内容(这里要解码，因为如果是中文的话就会发生乱码问题)
			pd.put("retrieve_content", URLDecoder.decode(pd.getString("retrieve_content"), "utf-8"));
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = aanms.findNumberBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			listPage = aanms.listSearchNameApprover(page);
		}	
				
				
		//查询审批人的数据，为了在增加的时候进行下拉列表显示
		List<PageData> approverlistPage = aanms.listApprover();
		mav.addObject("listPage", listPage); //返回审核节点数据
		mav.addObject("page",page);      //返回对应的分页的内容
		mav.addObject("approverlistPage", approverlistPage);//返回审核人列表数据
		mav.addObject("editresult" , this.getPageData().getString("editresult"));//传送查看当前审核节点的结果
		mav.addObject("deleteresult" , this.getPageData().getString("deleresult"));//传送批量删除审核节点的结果
		mav.addObject("deleresultnumber",this.getPageData().getString("deleresultnumber"));//返回删除操作中所处理的详细内容
		mav.setViewName("system/asystem_set/asystem_appnode_list");
		return mav;
	}
	/**
	 * 处理添加节点的操作
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value ="/asystem_saveapprovernode")
	public String editApproverNode() {
		try{
			ModelAndView mav = this.getModelAndView();
			PageData pageData = new PageData();
			pageData = this.getPageData();
			//首先需要需要到提交过来的userid的内容，因为在jsp之前用"-"进行的分割
			String users = pageData.getString("saveapproverid");
			String[] useridOne =  users.split("-");//得到单个的审核人员的ID
			
			//进行插入数据的处理，分别要插入到两张表中，sys_approvnodepeople和sys_approvnode
			aanms.saveApproNode(pageData);  //插入到审核节点表中
			
			//查询数据库中节点数据的最大ID的数字（因为这样才知道当前的节点ID，方便节点管理表中得到该新增条目的序号）
			Integer number =  aanms.findNumberNode();
			
			//循环遍历插入（下标从1开始，因为分割后第一个是为空的，之前这样处理了）
			for(int i =1 ; i <useridOne.length ; i++){
				//AssetApproNodePeople aanp = new AssetApproNodePeople();
				pageData.put("approvnode_UserId", useridOne[i]);//用户ID
				pageData.put("approvnode_Order", i);//因为级别顺序从1开始，级别越大越在后面
				pageData.put("approvnode_nodeId", number);//节点序号，即为新增的那条的ID
				aanms.saveApproNodeToPeople(pageData); //插入数据到审核节点对应的顺序表中
			}
		}
		catch (Exception e) {	
		}
		return "redirect:/asset/asystem_showallnode?editresult=success";
	}
	/**
	 * 删除审批节点（单个）
	 * @throws Exception 
	 */
	@RequestMapping(value ="/asystem_deleteapprovernode")
	public String deleteApproNode() throws Exception{
		PageData pageData = this.getPageData();
		Integer nodestr = Integer.parseInt(pageData.getString("nodeid"));//得到删除的nodeId,Int类型
		//进行删除操作(这里需要管理两张表，一张审核人员管理，一张审批节点)
		aanms.deleteSimpleNode(nodestr);  //删除审批节点
		aanms.deleteSimpleNodePeople(nodestr);  //删除审批节点管理
		return "redirect:/asset/asystem_showallnode?deleresult=success&deleresultnumber=1@1";
	}
	
	/**
	 * 显示对应点击的查看按钮所点击的审批节点详细信息
	 * @return
	 * @throws Exception 
	 */
	
	@RequestMapping(value ="/asystem_lookcurrentnode")
	public ModelAndView findCurrentNodeDetail() throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pageData = new PageData();
		pageData = this.getPageData();
		//因为需要设置两个表中的ID都保持一样（需要联表查询），先取出传送过来的并记录到审核节点管理表中的对应内容
		pageData.put("approvnode_nodeId", pageData.getString("node_Id"));//记录审核节点管理中的ID
		
		//查询对应审批ID的信息
		PageData pa = aanms.findDetailNodeInfo(pageData);
		
		//查询所有符合审核资格的人员名单
		//查询审批人的数据，为了在修改人员的时候进行下拉列表显示
		List<PageData> approverpeople = aanms.listApprover();
		
		//查询对应节点中，对应的审核人员的所有信息，因为需要显示之前编辑好的的审核人员的名字（通过整型的nodeid来查询）
		
		List<PageData> currentNodeApprvoverInfo = aanms.findApproverInfo(pageData);
		
		mav.addObject("nodecurrentinfo",pa);  //返回当前审核节点的详细信息内容
		mav.addObject("approverpeople", approverpeople);//返回所有审核人员的名单
		mav.addObject("currentNodeApprvoverInfo", currentNodeApprvoverInfo);//返回当前审核人员的userid和姓名
		
		mav.setViewName("system/asystem_set/asystem_currentnode");
		return mav;
	}
	
	/**
	 * 进行处理编辑审批节点详细信息的更新处理
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_editapprovalnodeinfo")
	public String editApprovalNode() throws Exception{
			ModelAndView mav = this.getModelAndView();
			PageData pageData = new PageData();
			pageData = this.getPageData();
			//首先需要需要到提交过来的userid的内容，因为在jsp之前用"-"进行的分割
			String users = pageData.getString("saveapproverid");
			String[] useridOne =  users.split("-");//得到单个的审核人员的ID
			
			//（1）进行跟新数据的处理，分别要插入到两张表中，sys_approvnodepeople和sys_approvnode
			aanms.editApproNode(pageData);  //根据NodeId更新数据到审核节点表中
			
			//（2）进行删除审核人管理节点中，对应该审批节点的原始数据
			Integer currentNodeId = Integer.parseInt(pageData.getString("node_Id"));//获取这次的更新的节点ID
			aanms.deleteLastApprover(currentNodeId);
			
			//（3）进行插入新的审核人员的信息数据到审核节点表中，
			//循环遍历插入（下标从1开始，因为分割后第一个是为空的，之前这样处理了）		
			for(int i =1 ; i <useridOne.length ; i++){
				//AssetApproNodePeople aanp = new AssetApproNodePeople();
				pageData.put("approvnode_UserId", useridOne[i]);//用户ID
				pageData.put("approvnode_nodeId", currentNodeId);//节点序号，即为新增的那条的ID
				aanms.saveApproNodeToPeople(pageData); //插入数据到审核节点对应的顺序表中
			}
		return "redirect:/asset/asystem_showallnode?editresult=success";
	}
	
	/**
	 * 通过多个审批节点ID，进行批量删除审批节点
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_deletebatchnode")
	public String deleteBatchApprovalNode(Integer[] ids) throws Exception{
		PageData pd = this.getPageData();
		//进行批量删除，这里需要操作两张表，一张审批节点表sys_approvnode，一张审批节点对应的审批节点详情表sys_approvnodepeople
		//(1)获取正在使用中的审批流程有哪些审批节点的ID
		List<Integer> currentUseId = aanms.findAllProcessNodeIds();
		//(2)判断选择的ID是否是在使用中
		Integer[] deleteIds = new Integer[ids.length];
		int index = 0;
		for(int i = 0 ;i < ids.length ; i++ ){
			if(!currentUseId.contains(ids[i])){ //不在使用中
				deleteIds[index] = ids[i];
				index++;
			}
		}
		//(3)进行删除
		aanms.deleteBatchApprovalNode(deleteIds);
		aanms.deleteBatchApprovalNodePeople(deleteIds);
		return "redirect:/asset/asystem_showallnode?deleresult=success&deleresultnumber="+ids.length+"@"+index;
	}
	
	/**
	 * 当点击删除或者处于点击修改节点内容的时候，判断该节点能否被删除（关键看该节点是否存在于某个审批流程sys_process_detail表）
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/asystem_currentnodedeleteStatus")
	public PageData currentNodeDeleteStatus() throws Exception{
		String deleteResult = ""; //标识是否能够被删除
		PageData pd = this.getPageData();
		//获取点击删除的审批节点ID
		Integer currentNodeId = Integer.parseInt(pd.getString("nodeid"));
		Integer totalNumber = aanms.findCurrenNodeStatus(currentNodeId);
		if(totalNumber == 0){  //表示审批流程中没有使用该审批节点
			deleteResult = "YES";
		}
		else{
			deleteResult = "NO";
		}
		pd.put("deleteResult", deleteResult);
		return pd;
	}
	
}
