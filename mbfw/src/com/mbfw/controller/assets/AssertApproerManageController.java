package com.mbfw.controller.assets;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.service.assets.AssetApproverManageService;
import com.mbfw.util.PageData;

/*
 * by scw 2017/09/12
 * function:管理审核人员处理，删除，修改
 * 
 */
@Controller
@RequestMapping(value="/asset")
public class AssertApproerManageController extends BaseController{
	
	@Autowired
	private AssetApproverManageService ams;
	
	//点击审核人员，进行显示所有审核人员信息
	@RequestMapping(value="/asystem_showapprover")
	public ModelAndView showApproverInfo() throws Exception{
		ModelAndView mav  = this.getModelAndView();	
		PageData pd = this.getPageData();
		List<PageData> 	listApprover = new ArrayList<PageData>();	
		
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
			Integer totalnumber = ams.findTotalDataNumber();
			//(4)设置总的数据条数
			page.setTotalResult(totalnumber); 
			//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  	
			//(6)查询数据库，返回对应条数的数据
			listApprover = ams.listPdPageApprover(page);
		}
		//--------进行检索处理
		else{
			//(3)获取传送过来的进行检索的内容(这里要解码，因为如果是中文的话就会发生乱码问题)
			pd.put("retrieve_content", URLDecoder.decode(pd.getString("retrieve_content"), "utf-8"));
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = ams.findNumberBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			listApprover = ams.listSearchNameApprover(page);
		}
		
		mav.addObject("listApprover",listApprover);   //返回对应条数的数据
		mav.addObject("page",page);                  //返回对应的分页的内容
		mav.addObject("deleteresult",pd.getString("delresult"));//返回批量删除操作后的结果	
		mav.addObject("addresult",pd.getString("addresult"));//返回添加审核人员操作后的结果
		mav.addObject("deleresultnumber", pd.getString("deleresultnumber")); //返回批量删除操作后的详细结果
		mav.setViewName("system/asystem_set/asystem_approver_list");
		return mav;
	}
	
	//点击删除，根据ID进行删除对应的人员（注：只是将该人员在审核表中数据进行清理，而不是删除该用户）
	@RequestMapping(value="/asystem_deleteApproverSimple")
	public String deleteApproverById() throws Exception{
		PageData pageData = this.getPageData();
		//进行删除操作，根据userid来进行(主键)
		String nodestr = pageData.getString("user_id");//得到删除的用户的ID,Int类型
		ams.deleteApproverById(nodestr);	
		return "redirect:/asset/asystem_showapprover?delresult=success&deleresultnumber=1@1";
	}
	
	//点击编辑，显示到编辑窗口中
	@RequestMapping(value="/asystem_showeditapprover")
	public ModelAndView showEditApprover() throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据ID返回对应的内容数据
		pd = ams.findByUiId(pd);
		mav.addObject("pd", pd);
		mav.setViewName("system/asystem_set/asystem_approver_edit");
		return mav;
	}
	
	//当点击保存，将对应的审核人的信息进行更新，根据userid
	@RequestMapping(value="/asystem_editapprover")
	public ModelAndView editApprover() throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//根据userid进行信息的更新
		ams.editApprover(pd);
		mav.addObject("msg","success");  //标识更新信息成功
		mav.setViewName("system/asystem_set/updateapproverresult");
		return mav;
	}
	
	/**
	 * 通过审批人员的工号或者表的序号ID都行，这里用工号，进行批量删除审批人员信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/asystem_deletebatchapprover")
	public String deleteBatchApprover(String[] ids) throws Exception{
		PageData pd = this.getPageData();
		//进行批量删除操作
		//(1)获取审批节点中的所有审批人员的ID
		List<String> currentUseId = ams.findAllNodeApproverUserIds();
		//(2)判断是否处于审批节点中
		String[] deleteIds = new String[ids.length];
		int index = 0;
		for(int i = 0 ;i < ids.length ; i++ ){
			if(!currentUseId.contains(ids[i])){ //不在使用中
				deleteIds[index] = ids[i];
				index++;
			}
		}
		//(3)进行审批操作
		ams.deleteBatchApprover(deleteIds);
		return "redirect:/asset/asystem_showapprover?delresult=success&deleresultnumber="+ids.length+"@"+index;
	}
	
	/**
	 * 在主界面点击增加审核人员时，返回所有的用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveapprover")
	public ModelAndView saveApprover() throws Exception{
		ModelAndView mav = new ModelAndView();		
		//查询所有的用户信息，来传送到添加审核人员页面中
		List<PageData> pd = ams.findAllApproverByUser();
		mav.addObject("approverinfo",pd);  //返回所有的用户的信息
		mav.setViewName("system/asystem_set/asystem_approver_add");
		return mav;
	}
	
	/**
	 * 进行添加审核人员的操作处理
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/saveOneApprover")
	public String savaOneApprover() throws Exception{ 
		PageData pd = this.getPageData();
		//判断是否添加的人员已经在审核人员数据库中存在
		Integer judgeResult =  ams.findCurrentApproverStatus(pd);
		String addresult = "";
		if(judgeResult == 0){ //表示没有存在
			//进行添加审核人员操作到数据库中
			ams.saveApproverOne(pd);
			addresult = "success";
		}
		else{
			addresult = "fail";
		}
		return "redirect:/asset/asystem_showapprover?addresult="+addresult;
	}

	/**
	 * 响应添加新审核人员的检索用户的结果
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/asystem_showresearchcontentresult")
	public List<PageData> editReasearchResult() throws Exception{
		PageData pd = this.getPageData();
		//获取进行检索的内容
		String checkStr = pd.getString("researContent");
		//如果检索内容是空，那么就返回所有用户的信息，否则返回模糊匹配到的用户信息
		List<PageData> resultInfo = new ArrayList<PageData>();
		if(checkStr == "" || checkStr == null){
			resultInfo = ams.findAllApproverByUser();
		}
		else{
			//模糊匹配数据库中
			resultInfo = ams.findResearchContentResult(checkStr);
		}		
		return resultInfo;
	}
	
	
	/**
	 * 当点击删除的时候，判断该节点能否被删除（关键看该审核人员是否存在于某个审批节点sys_approvnodepeople表）
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/asystem_currentapproverdeleteStatus")
	public PageData currentApproerDeleteStatus() throws Exception{
		String deleteResult = ""; //标识是否能够被删除
		PageData pd = this.getPageData();
		//获取点击删除的审批人员的ID
		String currentUserId = pd.getString("user_id");
		Integer totalNumber = ams.findCurrenApproverStatus(currentUserId);
		if(totalNumber == 0){  //表示审批流程中没有使用该审批节点
			deleteResult = "YES";
		}
		else{
			deleteResult = "NO";
		}
		pd.put("deleteResult", deleteResult);
		return pd;
	}
	
	/**
	 * 测试打印二维码-----用来跳转到打印二维码的页面
	 * @return
	 */
	@RequestMapping(value="/printpicture")
	public String print2Picture(){
		//return "printpicturetest";
		return "jqrcodepicture";
	}

	
}
