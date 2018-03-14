package com.mbfw.controller.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetAbandonManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetAbandonService;
import com.mbfw.service.assets.AssetApproverProcessService;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.AssetTableManageService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssetManageAbandonController extends BaseController{

	@Autowired
	private ProjectApplyService projectApplyService;
	@Resource(name = "assetAbandonService")
	private AssetAbandonService assetAbandonService;
	@Resource(name = "assetTableManageService")
	private AssetTableManageService assetTableManageService;
	@Autowired
	private AssetApproverProcessService aaps;  //审批流程Service
	@Autowired
	private AssetPurchaserApplyService assetPurchaserApplyService;
	
	
	/**
	 * 显示资产报废模块中数据
	 */
	@RequestMapping(value = "/aucs_abandon")
	public ModelAndView abandon() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//权限设置
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
		Integer permission = user.getUser_Permission(); //部门权限		
		String superior_organization_name = user.getSuperior_organization_name();//上一级部门
		String organization_name = user.getOrganization_name();//当前所属部门
		String username = user.getNAME();
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		pd.put("username", username);
		
		List<PageData> 	listApprover = new ArrayList<PageData>();	
		
		PageOption page = new PageOption(10, 1); //默认初始化一进来显示就是每页显示10条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if(pd.getString("showCount") != null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//------没有进行检索的处理-----
		if(pd.getString("retrieve_content") == null || ("").equals( pd.getString("retrieve_content"))){
			page.setPd(pd);
			//(3)查询数据库中数据的总条数
			Integer totalnumber = assetAbandonService.findTotalAbandonDataNumber(page);
			//(4)设置总的数据条数
			page.setTotalResult(totalnumber); 
			//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
			//(6)查询数据库，返回对应条数的数据
			listApprover = assetAbandonService.listPdAbandonPageApprover(page);
		}
		//--------进行检索处理
		else{
			//(3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetAbandonService.findNumberAbandonBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			listApprover = assetAbandonService.listAbandonSearchNameApprover(page);
			}
		mv.addObject("page",page);     //返回对应的分页的内容
		mv.addObject("listApprover", listApprover);		//返回对应条数的数据
		mv.addObject("permission",permission);
		mv.addObject("count",this.getPageData().getString("count"));
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.setViewName("/system/asset_scrap/asset_abandon");

		return mv;
	}
	

	/**
	 * 新增资产报废表
	 */
	@RequestMapping(value = "/goAddC")
	public ModelAndView goAddC() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10, 1);
		//权限设置
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
		Integer permission = user.getUser_Permission(); //部门权限		
		String superior_organization_name = user.getSuperior_organization_name();//上一级部门
		String organization_name = user.getOrganization_name();//当前所属部门
		String username = user.getNAME();
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		pd.put("username", username);
		
		List<PageData> assetCodeFind = null;
		assetCodeFind=assetTableManageService.find_AssetCode(pd);

		//获取审批流程的信息
		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
		mv.addObject("approvalProcess", approvalProcess);		
		
		mv.addObject("assetCodeFind", assetCodeFind);
		mv.setViewName("system/asset_scrap/abandontable");
		mv.addObject("msg", "save_Abandon");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 资产封装成json，用ajax请求
	 */
	@RequestMapping(value = "/find_assetinfo")		
	public @ResponseBody  PageData find_assetinfo() throws Exception {
		PageData pd = this.getPageData();
		//获取入库单的唯一id号
//		Map<String,PageData> rm = new HashMap<String,PageData>();
		String id = pd.getString("id");
		PageData idleStatus = assetTableManageService.findAssetInfo_byIdleStatus(id);
		PageData getStatus = assetTableManageService.findAssetInfo_byGetStatus(id);
		PageData recycleStatus = assetTableManageService.findAssetInfo_byRecycleStatus(id);
		PageData maintainStatus = assetTableManageService.findAssetInfo_byMaintainStatus(id);
//		rm.put("getStatus", getStatus);
//		rm.put("recycleStatus", recycleStatus);
		if(idleStatus != null){
			if(idleStatus.getString("asset_status").equals("闲置")){
				return idleStatus;
			}
		}
		if(getStatus != null){
			if(getStatus.getString("asset_status").equals("领用")){
				return getStatus;
			}
		}
		if(recycleStatus != null){
			if(recycleStatus.getString("asset_status").equals("回收")){
				return recycleStatus;
			}
		}
		if(maintainStatus != null){
			if(maintainStatus.getString("asset_status").equals("报修")){
				return maintainStatus;
			}
		}
		return null;
	}
	
	
	
	/**
	 * 处理添加资产报废表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/save_abandon")
	public String save_abandon() throws Exception{
		PageData pd = this.getPageData();
		//添加数据到数据库中
		assetAbandonService.saveAbandonData(pd);
		assetAbandonService.changeValue(pd);
		pd.put("asset_status", "报废");
		assetTableManageService.changeAssetStatus(pd);
		
			
		//添加数据信息到项目审批过程表中		
		// 获取选取审批流程的ID和名字
		PageData newDataProcess = new PageData();
		Integer processId = Integer.parseInt(pd.getString("apply_procedure_id"));
		String processName = pd.getString("abandon_approve");
		newDataProcess.put("process_ApprovalId", processId); // 流程ID
		newDataProcess.put("process_ApprovalName", processName); // 流程名

		newDataProcess.put("project_Id", pd.get("id")); // 获取添加申请的id
		newDataProcess.put("project_Name", pd.getString("asset_name")); // 耗材名称
		// 根据流程ID和顺序,查询对应流程表中的第一个流程节点的ID和名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(processId);
		newDataProcess.put("current_NodeName", nodeName); // 第一个审批节点的名字
		newDataProcess.put("description_Passnumber", 0); // 通过的人数
		newDataProcess.put("description_Refusenumber", 0); // 不通过的人数
		newDataProcess.put("process_FinishStatus", "未完成"); // 该项目流程的完成状态，开始的状态是未完成
		newDataProcess.put("current_NodeOrder", 1); // 刚开始的时候设置的审批节点顺序为第一个，这个不设置也可以，因为数据库中默认了为第一个
		newDataProcess.put("project_Stryle", "需要"); // 设置该项目流程是需要进行审批操作
		newDataProcess.put("process_Type", "报废申请");// 设置该项目流程进行审批的类型
		// 将数据插入到项目过程表中
		assetPurchaserApplyService.saveOneProjectProcessInfo(newDataProcess);
		
		//----------处理添加审批流程的操作结束----scw--
		return "redirect:/asset/aucs_abandon?saveResult=success";
	}
	
	/**
	 * 增加修改表
	 */
	@RequestMapping(value = "/edit_abandon")
	public String editAbandonApprover() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		assetAbandonService.editAbandonApprover(pd);
		return "redirect:/asset/aucs_abandon?updateResult=success";
	}
	
	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/go_edit_abandon")
	public ModelAndView goEditAbandon() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10000, 1);
		AssetAbandonManage aam = assetAbandonService.findAbandonEditData(pd);	
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
		JSONArray js = JSONArray.fromObject(info);				
		//获取审批流程的信息
		List<PageData> approvalProcessInfo = projectApplyService.findApproveProcess();
		mv.addObject("approvalProcess", approvalProcessInfo);
		
//		List<PageData> assetCompanyFind = null;
//		assetCompanyFind=assetTableManageService.find_GetCompany(page);		
//		mv.addObject("assetCompanyFind", assetCompanyFind);
		
//		List<PageData> assetDepartmentFind = null;
//		assetDepartmentFind=assetTableManageService.find_GetDepartment(page);		
//		mv.addObject("assetDepartmentFind", assetDepartmentFind);
		
		mv.setViewName("system/asset_scrap/abandon_edit");
		mv.addObject("institutionInfo", js);
		mv.addObject("aam", aam);
		mv.addObject("pd", pd);
		return mv;
	}

	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delete_AllAbandon")
	@ResponseBody
	public String deleteAllAbandonData() {
		PageData pd = new PageData();
///		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String ArrayAllot_ids[] = Allot_ids.split(",");
			int newIds[] = new int[ArrayAllot_ids.length];
			for(int i = 0; i < ArrayAllot_ids.length; i++){
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
				PageData search = assetAbandonService.searchStatusInfo(newIds[i]);
				String orig_status = search.getString("orig_status");
				String code = search.getString("asset_code");
				pd.put("orig_status", orig_status);
				pd.put("asset_code", code);	
				//删除后退回到之前的资产状态
				assetAbandonService.returnPriorStatus(pd);
				//删除后退回为原来的有效值
				assetAbandonService.returnValid(pd);
				//删除后同时对审批的数据进行删除
				assetAbandonService.deleteApproveProcess(pd);
			}
			assetAbandonService.deleteAllAbandonData(newIds);
			pdList.add(pd);
//			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
//		return "OK";
		return "redirect:/asset/aucs_abandon?deleteResult=success";
	}
	
	
	
//	/**
//	 * 修改审批表
//	 */
//	@RequestMapping(value = "/approval_abandon")
//	public ModelAndView editApprovalAbandon() throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		
//		assetAbandonService.approvalAbandon(pd);		
//		mv.addObject("msg", "success");
//		mv.setViewName("save_result");
//		return mv;
//	}
//	
//	
//	/**
//	 * 审批页面
//	 */
//	@RequestMapping(value = "/go_approval_abandon")
//	public ModelAndView goApprovalAbandon() throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		AssetAbandonManage sp = assetAbandonService.findAbandonEditData(pd);	
//		mv.setViewName("system/asset_scrap/abandon_approval");
//		mv.addObject("sp", sp);
//		mv.addObject("pd", pd);
//		return mv;
//	}
	
	/**
	 * 查询资产数量
	 */
	@RequestMapping(value = "/check_AssetCountA")
	public String checkAssetCountA() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
		Integer permission = user.getUser_Permission(); //部门权限		
		String superior_organization_name = user.getSuperior_organization_name();//上一级部门
		String organization_name = user.getOrganization_name();//当前所属部门
		String username = user.getNAME();
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		pd.put("username", username);
		int count = assetAbandonService.checkAssetCount(pd);
		return  "redirect:/asset/aucs_abandon?count="+count;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
