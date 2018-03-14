package com.mbfw.controller.assets;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetApprovalProcess;
import com.mbfw.entity.assets.AssetApprovalProcessDetail;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.QueryVo;
import com.mbfw.service.assets.AssetApproverProcessService;
import com.mbfw.util.PageData;

import sun.awt.ModalExclude;

/**
 * 
 * @author scw
 *2017-09-17
 *function：管理审批流程表的Controller后台操作
 */
@Controller
@RequestMapping(value ="/asset")
public class AssetApprovalProcessController extends BaseController{
	
	@Autowired
	private AssetApproverProcessService aaps;
	
	/**
	 * 返回所有的审批流程的数据
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_showapprovalprocess")
	public ModelAndView showAllProcess() throws Exception{
		ModelAndView mav = this.getModelAndView();		
		PageData pd = this.getPageData();
		List<PageData> 	pageDatas = new ArrayList<PageData>();	

		//------分页处理开始---------------
		PageOption page = new PageOption(5, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if(pd.getString("showCount") != null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//-----没有进行检索的处理-----
		if(pd.getString("retrieve_content") == null || pd.getString("retrieve_content") == ""){
			//(3)查询数据库中数据的总条数
			Integer totalnumber = aaps.findTotalDataNumber();
			//(4)设置总的数据条数
			page.setTotalResult(totalnumber); 
			//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
			//(6)查询数据库，返回对应条数的数据
			 pageDatas = aaps.findAllProcessInfo(page);
		}
		//--------进行检索处理
		else{
			//(3)获取传送过来的进行检索的内容(这里要解码，因为如果是中文的话就会发生乱码问题)
			pd.put("retrieve_content", URLDecoder.decode(pd.getString("retrieve_content"), "utf-8"));
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = aaps.findNumberBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));		
			//(8)查询数据库，返回对应检索姓名的数据
			pageDatas = aaps.listSearchNameApprover(page);
		}
		
		mav.addObject("pageDatas", pageDatas);  //返回所有的流程表信息
		mav.addObject("page",page);             //返回对应的分页的内容
		mav.addObject("editresult" ,pd.getString("editresult"));  //返回编辑节点操作的结果
		mav.addObject("deleresult" ,pd.getString("deleresult"));  //返回删除操作的结果
		mav.addObject("deleresultnumber",pd.getString("deleresultnumber"));//批量删除流程节点操作的结果(包含选择删除的个数和实际删除的个数)
		mav.setViewName("system/asystem_set/asystem_approvalprocess_list");
		return mav;
	}
	/**
	 * 处理审批流程增加的操作(只是相当于跳转的功能)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_addprocessnode")
	public ModelAndView editAddProcess() throws Exception{
		ModelAndView mav = this.getModelAndView();
		//查询获取所有的审批节点的内容
		List<PageData> approvalNodeInfos =  aaps.findListApprvalNode();
		mav.addObject("nodeinfo" ,approvalNodeInfos) ; //返回所有的审核节点的信息
		mav.setViewName("system/asystem_set/asystem_approvalprocess_add");
		return mav;
	}
	
	
	/**
	 * 处理添加审批流程的数据（真正的处理添加流程的数据）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping( value ="/asystem_editprocessdetail")
	public String editProvessDedail( ) throws Exception{
		//(1)将流程数据进行插入到数据库中
		PageData pd= this.getPageData();
		aaps.saveProcessInfoData(pd); //插入数据
		//(2)查询得到审批流程表中最大的流程ID号，从而得到该增加的审批节点对应的审批流程ID
		Integer totalProcessNumber = aaps.findTotalNumber();
		//(3)得到一共添加 的审批节点个数，这样就能够得到每个节点的顺序
		int nodeNumber = Integer.parseInt(pd.getString("savenodenumber"));
		//(4)循环遍历每个节点的信息，然后插入到审批流程详细数据表中
		AssetApprovalProcessDetail aapd = new AssetApprovalProcessDetail();
		for (int i = 0 ; i <nodeNumber ;i++){
			aapd.setProcessdetail_Id(totalProcessNumber); //设置节点对应的审批流程ID，其实就是总条数
			aapd.setProcessdetail_NodeId(Integer.parseInt(pd.getString("processdetail_NodeId" + i)));//设置节点ID
			aapd.setProcessdetail_Back(pd.getString("processdetail_Back" + i)); //设置是否该节点可以返回
			aapd.setProcessdetail_Nodeorder(i+1); //设置节点处理顺序，因为从0开始，但是数据库设置为1开始，所以要+1
			//插入数据到数据库
			aaps.saveApprovalNode(aapd);
		}	
		return "redirect:/asset/asystem_showapprovalprocess?editresult=success";//处理完后返回到显示所有流程界面
	}
	
	/**
	 * 进行删除流程操作（单个）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_deleteproceess")
	public String deletProcess() throws Exception{
		PageData pd = this.getPageData();
		//获取要进行删除的流程号ID
		Integer delNumber  = Integer.parseInt(pd.getString("processid"));
		//进行删除操作，需要删除两张表中的内容，分别是sys_process_approval和sys_process_detail
		aaps.deleteProcessNode(delNumber);
		aaps.deleteProcessDetailNode(delNumber);
		return "redirect:/asset/asystem_showapprovalprocess?deleresult=success&deleresultnumber="+1+"@"+1;
	}
	/**
	 * 点击编辑按钮，跳转到修改审批流程页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/asystem_editprocesscontent")
	public ModelAndView editProcessNode() throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pd = this.getPageData();
		//获取点击需要编辑修改的流程号ID
		Integer processId = Integer.parseInt(pd.getString("processid"));
		//查询该流程号的信息
		PageData pageData = aaps.findProcessInfoSimple(processId);
		//查询该流程号对应的审批节点的信息
		List<PageData> nodes = aaps.findCurrentNode(processId);
		//查询所有的审核人员的信息
		List<PageData> approvalPeople = aaps.findListApprvalNode();
		mav.addObject("processcontent", pageData); //返回流程信息
		mav.addObject("currentnode", nodes);  //返回对应的审批节点信息
		mav.addObject("allappropeople", approvalPeople);  //返回所有的审核人员信息
		mav.setViewName("system/asystem_set/asystem_approvalprocess_edit");
		return mav;
	}
	
	/**
	 * 点击编辑按钮，跳转到修改审批流程页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/asystem_updateprocessdetail")
	public String updateCurrentProcessInfo() throws Exception{
		PageData pd= this.getPageData();
		//(1)将当前的流程号的信息进行更新
		aaps.updateCurrentProcessInfo(pd); //插入数据
		//(2)得到当前进行修改的流程号ID，从而得到该增加的审批节点对应的审批流程ID
		//获取到当前修改的流程号的ID
		Integer currentProcessNumber =Integer.parseInt( pd.getString("process_Id"));
		//(3)得到一共添加 的审批节点个数，这样就能够得到每个节点的顺序
		Integer nodeNumber = Integer.parseInt(pd.getString("savenodenumber"));
		//(4)将之前流程详细表中存在的信息进行删除，之后再进行插入操作
		aaps.deleteCurrentProcessDetailInfo(currentProcessNumber);
		//(5)循环遍历每个节点的信息，然后插入到审批流程详细数据表中
		AssetApprovalProcessDetail aapd = new AssetApprovalProcessDetail();
		for (int i = 0 ; i <nodeNumber ;i++){
			aapd.setProcessdetail_Id(currentProcessNumber); //设置节点对应的审批流程ID，其实就是当前操作的流程号ID（已经保存得到了的）
			aapd.setProcessdetail_NodeId(Integer.parseInt(pd.getString("processdetail_NodeId" + i)));//设置节点ID
			aapd.setProcessdetail_Back(pd.getString("processdetail_Back" + i)); //设置是否该节点可以返回
			aapd.setProcessdetail_Nodeorder(i+1); //设置节点处理顺序，因为从0开始，但是数据库设置为1开始，所以要+1
			//插入数据到数据库
			aaps.saveApprovalNode(aapd);
		}	
		return "redirect:/asset/asystem_showapprovalprocess?editresult=success";
	}
	
	/**
	 * 审批流程主界面中的(批量删除)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/asystem_deletebatchprocessnumber")
	public String delBatchProcessNode(Integer[] ids) throws Exception{
		PageData pd = this.getPageData();
		//进行批量删除的操作，分别操作两张表流程表和流程表细节
		//(1)首先获取在审批过程表中的所有使用的审批流程的ID
		List<Integer> currentUseId = aaps.findAllProjectProcessIds();
		//(2)判断批量删除所选择的删除ID是否是处于使用中，如果是使用中则不允许删除，则不添加到删除id的数组中
		Integer[] deleteIds = new Integer[ids.length];
		int index = 0;
		for(int i = 0 ;i < ids.length ; i++ ){
			if(!currentUseId.contains(ids[i])){ //不在使用中
				deleteIds[index] = ids[i];
				index++;
			}
		}
		//(3)进行删除操作
		aaps.deleteBatchProcess(deleteIds);
		aaps.deleteBatchProcessDetail(deleteIds);
		return "redirect:/asset/asystem_showapprovalprocess?deleresult=success&deleresultnumber="+ids.length+"@"+index;
	}
	
	/**
	 * 当进行删除或者编辑操作的时候，判断所选择的审批流程是否处于使用当中（即在审批过程表中）
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@ResponseBody
	@RequestMapping(value="/asystem_processstatus")
	public PageData findCurrentProcessStatus() throws Exception{
		String status = "";
		PageData pd =this.getPageData();
		//根据流程ID号，查询在项目过程表aproject_process中是否有过程在使用中
		Integer totalNumber = aaps.findCurrentProcessStatus(Integer.parseInt(pd.getString("processid")));
		if(totalNumber == 0){  //表示没有在使用中的项目过程
			status = "success";
		}
		else{
			status = "fail";
		}
		pd.put("status", status);
		return pd;
	}
}
