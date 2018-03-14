package com.mbfw.controller.assets;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetAllot;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.Role;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.AssetRARService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.service.assets.AssetRepAndAllotService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.Jurisdiction;
import com.mbfw.util.PageData;

/**
 * 类名称：AssetReqAndAllotController 创建人：揭尹  创建时间：2017年8月20日
 * 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/asset")
public class AssetRepAndAllotController extends BaseController {
	@Autowired
	//String menuUrl = "asset/listAll.do"; // 菜单地址(权限用)
	@Resource(name = "assetRepAndAllotService")
	private AssetRepAndAllotService assetRepAndAllotService;
	
	@Resource(name = "assetRARService")
	private AssetRARService assetRARService;
	
	@Autowired
	private ProjectApplyService projectApplyService;
	
	@Resource(name="assetRegisterService")
	private AssetRegisterService assetRegisterService;
	@Autowired
	private AssetPurchaserApplyService assetPurchaserApplyService;
	
	/**
	 * 保存申请表
	 */
	@RequestMapping(value = "/saveAllot")
	public ModelAndView saveAllot() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData tmp = new PageData();
		//----获取当前登陆用户的相关信息，从session中获取
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("sessionUser");
		//List<AssetInfo> alist = assetRepAndAllotService.findAssetInfo(pd);
	/*	String asset_use_dept = pd.getString("it_department");
		String asset_user = pd.getString("asset_receiver");
		tmp.put("asset_use_dept", asset_use_dept);
		tmp.put("asset_user", asset_user);*/
		pd.put("user_id", user.getUSERNAME());
		assetRepAndAllotService.saveAllot(pd);
		assetRepAndAllotService.editAssetInfo(pd);
		
		//----start--增加内容到审批项目列表中
		// 获取选取审批流程的ID和名字
		PageData newDataProcess = new PageData();
		Integer processId = Integer.parseInt(pd.getString("apply_procedure_id"));
		String processName = pd.getString("apply_procedure");
		newDataProcess.put("process_ApprovalId", processId); // 流程ID
		newDataProcess.put("process_ApprovalName", processName); // 流程名

		newDataProcess.put("project_Id", pd.get("id")); // 获取添加申请的id
		newDataProcess.put("project_Name", pd.getString("allot_name")); // 资产调入与借出申请名称
		// 根据流程ID和顺序,查询对应流程表中的第一个流程节点的ID和名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(processId);
		newDataProcess.put("current_NodeName", nodeName); // 第一个审批节点的名字
		newDataProcess.put("description_Passnumber", 0); // 通过的人数
		newDataProcess.put("description_Refusenumber", 0); // 不通过的人数
		newDataProcess.put("process_FinishStatus", "未完成"); // 该项目流程的完成状态，开始的状态是未完成
		newDataProcess.put("current_NodeOrder", 1); // 刚开始的时候设置的审批节点顺序为第一个，这个不设置也可以，因为数据库中默认了为第一个
		newDataProcess.put("project_Stryle", "需要"); // 设置该项目流程是需要进行审批操作
		newDataProcess.put("process_Type", "资产调入与借出");// 设置该项目流程进行审批的类型
		// 将数据插入到项目过程表中
		assetPurchaserApplyService.saveOneProjectProcessInfo(newDataProcess);
		//------end----
		
		mv.addObject("msg", "success");
		
		mv.setViewName("save_result");
		
		return mv;
	}
	/**
	 * 保存报修表
	 */
	@RequestMapping(value = "/saveRAR") 
	public ModelAndView saveRAR() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		PageData tmp = new PageData();
		String s="";
		tmp.put("asset_code", pd.getString("asset_code"));
		List<AssetInfo> list=assetRegisterService.findByAssetCode(tmp);
		AssetRAR rar = assetRARService.listBymaintain_result(pd);
		if(list.isEmpty()){
	
			s="报修失败：资产编码错误！";
		}
		else{
			AssetInfo asset = list.get(0);
			if(asset.getAsset_status().equals("报废")){
				s="报修失败：资产已报废！";
			}
			else if(asset.getAsset_status().equals("报修")&&rar.getMaintain_result().equals("未维修")){
				assetRARService.mobile_updateRAR(pd);
				
				s="报修成功：已覆盖之前的报修表！";
			}else if(asset.getAsset_status().equals("报修")&&rar.getMaintain_result().equals("无法维修")){
				s="该设备已经处于无法维修状态！";
			}
			else {
				tmp.put("asset_status", "报修");
				assetRegisterService.editAssetStatus(tmp);
				pd.put("pre_asset_status", asset.getAsset_status());//保存之前的资产状态
				assetRARService.saveRAR(pd);
				s="报修成功！";
			}
			
		}
		
		mv.addObject("msg", s);
		
		mv.setViewName("system/acall_repair/result");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/isAssetCodeExist")
	public @ResponseBody Map<String,String> isAssetCodeExist() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//System.out.println("参数："+pd.get("asset_code"));
		String code = assetRegisterService.findAssetCode(pd);
		Map<String,String> m = new HashMap<String,String>();
		m.put("code", code);
		//System.out.println(m.get("code"));
		return m;
	}
	/**
	 * 保存维修表
	 */
	@RequestMapping(value = "/saveRAR1")
	public ModelAndView saveRAR1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData tmp = new PageData();

		System.out.print(pd.getString("asset_code"));
		
		if(pd.getString("maintain_result").equals("已维修")){
		
			assetRARService.editAssetStatus(pd);
		}
		
		assetRARService.saveRAR1(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 显示维修中数据
	 * 
	 */
	@RequestMapping(value = "/derepair")
	public ModelAndView list3() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> rarList = new ArrayList<PageData>();
		
		PageOption page = new PageOption(10, 1); // 默认初始化一进来显示就是每页显示10条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("sessionUser");
		pd.put("userPermission", user.getUser_Permission()); //当前用户的权限
		
		// ------没有进行检索的处理-----
		if (pd.getString("retrieve_content") == null || ("").equals( pd.getString("retrieve_content"))) {
			page.setPd(pd);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = assetRARService.findTotalNumber();
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			rarList = assetRARService.listAllRAR(page);
		}
		// --------进行检索处理
		else {
			// (3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			// (4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			// (5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetRARService.findNumberGetBySearchName(page);
			// (6)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (8)查询数据库，返回对应检索姓名的数据
			rarList = assetRARService.listGetSearchNameApprover(page);
		}

		mv.addObject("rarList", rarList);
		mv.addObject("page", page);
		mv.addObject("pd",pd);
		mv.setViewName("system/acall_repair/asset_derepair");
		return mv;
	}
	/**
	 * 显示调用与借出表中数据
	 * 
	 */
	@RequestMapping(value = "/borrow")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> allotList = new ArrayList<PageData>();
		String asset_user = pd.getString("asset_user");
		PageOption page = new PageOption(10, 1); // 默认初始化一进来显示就是每页显示10条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//----获取当前登陆用户的相关信息，从session中获取
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("sessionUser");
		pd.put("user_id", user.getUSERNAME());
		pd.put("userPermission", user.getUser_Permission()); //当前用户的权限
		pd.put("bank_name",user.getOrganization_name());//当前用户所在公司
		//pd.put("ot_department",user.getSuperior_organization_name());//当前用户所在部门
		
		// ------没有进行检索的处理-----
		if (pd.getString("retrieve_content") == null || ("").equals( pd.getString("retrieve_content"))) {
			page.setPd(pd);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = assetRepAndAllotService.findTotalNumber(page);
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			allotList = assetRepAndAllotService.listAllAllot(page);
		}
		// --------进行检索处理
		else {
			// (3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			// (4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			// (5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetRepAndAllotService.findNumberGetBySearchName(page);
			// (6)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (8)查询数据库，返回对应检索姓名的数据
			allotList = assetRepAndAllotService.listGetSearchNameApprover(page);
		}
		mv.addObject("allotList", allotList);
		mv.addObject("page", page);
		mv.addObject("pd",pd);
		mv.setViewName("system/adispatch_borrow/asset_allocation_borrow_tab");
		return mv;
	}

	/**
	 * 去新增调拨与借用的申请页面
	 */
	@RequestMapping(value = "/goAddBorrow")
	public ModelAndView goAddB() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//获取审批流程的信息
			List<PageData> approvalProcess = projectApplyService.findApproveProcess();
			mv.addObject("processinfo", approvalProcess);
			
			
			List<PageData> alist = new ArrayList<PageData>();
			alist=assetRARService.listRARById(pd);
			mv.addObject("By_code",alist);
			mv.setViewName("system/adispatch_borrow/addPage");	
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	@RequestMapping(value = "/isAssetAllot")
	public @ResponseBody Map<String,String> isAssetAllot() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String company = assetRepAndAllotService.findAssetGongsi(pd);
		String dept = assetRepAndAllotService.findAssetDept(pd);
		String user = assetRepAndAllotService.findAssetUser(pd);
		Map<String,String> m = new HashMap<String,String>();
		m.put("company",company);
		m.put("dept", dept);
		m.put("user", user);
		return m;
	}
//	/**
//	 * 去新增调拨与借用的审批页面
//	 */
//	@RequestMapping(value = "/goAddBorrow1")
//	public ModelAndView goAddB1() {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//		try {
//			
//			mv.setViewName("system/adispatch_borrow/shenpibiao");
//			mv.addObject("msg", "saveU");
//			mv.addObject("pd", pd);
//	
//		} catch (Exception e) {
//			logger.error(e.toString(), e);
//		}
//		return mv;
//	}
	
	/**
	 * 去新增报修页面
	 */
	@RequestMapping(value = "/goAddRepair")
	public ModelAndView goAddR() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		List<PageData> alist = new ArrayList<PageData>();
		alist=assetRARService.listRARById(pd);
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		
		JSONArray js = JSONArray.fromObject(info);
		
		mv.addObject("By_code",alist);
		mv.addObject("institutionInfo", js);
		mv.setViewName("system/acall_repair/addPage1");
		//mv.addObject("msg", map);
		mv.addObject("pd", pd);
	
	
		return mv;
	}
	@RequestMapping(value = "/isAssetUserExist")
	public @ResponseBody Map<String,String> isAssetUserExist() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		String user = assetRegisterService.findAssetUser(pd);
		Map<String,String> m = new HashMap<String,String>();
		m.put("user", user);
		
		return m;
	}
	
	@RequestMapping(value = "/isAssetNameExist")
	public @ResponseBody Map<String,String> isAssetNameExist() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String name = assetRegisterService.findAssetName(pd);
		Map<String,String> m = new HashMap<String,String>();
		m.put("name", name);
		
		return m;
	}
	@RequestMapping(value = "/isAssetGongsiExist")
	public @ResponseBody Map<String,String> isAssetGongsiExist() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		String company = assetRegisterService.findAssetGongsi(pd);
		String dept = assetRegisterService.findAssetDept(pd);
		Map<String,String> m = new HashMap<String,String>();
		m.put("company",company);
		m.put("dept", dept);

		return m;
	}
	/**
	 * 去新增维修页面
	 * @throws Exception 
	 */
	@RequestMapping(value = "/goAddRepair1")
	public ModelAndView goAddR1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		AssetRAR allot = assetRARService.listAllotById(pd);
		
		mv.setViewName("system/acall_repair/addPage");
		mv.addObject("sd", allot);
		mv.addObject("pd", pd);
	
		
		return mv;
	}
	
/**
 * 删除（调拨与借用表）
 */
	 
	@RequestMapping(value = "/deleteAllot")
	public void deleteU(PrintWriter out)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
			
		assetRepAndAllotService.deleteAllot(pd);
			
	    out.write("success");
		out.close();
		

	}
	/**
	 * 删除（维修与维护表）
	 */
		 
		@RequestMapping(value = "/deleteRAR")
		public void deleteRAR(PrintWriter out)throws Exception{
			PageData pd = new PageData();
			pd = this.getPageData();
				
			assetRARService.deleteRAR(pd);
				
		    out.write("success");
			out.close();
			

		}
    /**
	 * 批量删除（调拨与借用表）
	 */
	@RequestMapping(value = "/deleteAllAllot")
	@ResponseBody
	public Object deleteAllAllot() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String ArrayAllot_ids[] = Allot_ids.split(",");
			int newIds[] = new int[ArrayAllot_ids.length];
			for(int i = 0; i < ArrayAllot_ids.length; i++){
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
			}
			assetRepAndAllotService.deleteAllAllot(newIds);
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	/**
	 * 批量删除（维修与维护表）
	 */
	@RequestMapping(value = "/deleteAllRAR")
	@ResponseBody
	public Object deleteAllRAR() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String ArrayAllot_ids[] = Allot_ids.split(",");
			int newIds[] = new int[ArrayAllot_ids.length];
			for(int i = 0; i < ArrayAllot_ids.length; i++){
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
			}
			assetRARService.deleteAllRAR(newIds);
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 修改审批表
	 */
	@RequestMapping(value = "/editAllot1")
	public ModelAndView editU1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		assetRepAndAllotService.editAllot1(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 修改申请表（调拨与借用表）
	 */
	@RequestMapping(value = "/editAllot")
	public ModelAndView editU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		assetRepAndAllotService.editAllot(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 修改申请表（维修与维护表）
	 */
	@RequestMapping(value = "/editRAR")
	public ModelAndView editR() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		assetRARService.editRAR(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 去修改申请页面（调拨与借用表）
	 */
	@RequestMapping(value = "/goEditAllot")
	public ModelAndView goEditB() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		AssetAllot allot = assetRepAndAllotService.listAllotById(pd);	
		mv.setViewName("system/adispatch_borrow/editPage");
		mv.addObject("allot", allot);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去修改申请页面（维修与维护表）
	 */
	@RequestMapping(value = "/goEditRAR")
	public ModelAndView goEditR() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		AssetRAR allot = assetRARService.listAllotById(pd);
		mv.setViewName("system/acall_repair/editPage");
		mv.addObject("sd", allot);
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 去审批页面
	 */
	@RequestMapping(value = "/goEditAllot1")
	public ModelAndView goEditU1() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		AssetAllot allot = assetRepAndAllotService.listAllotById(pd);	
		mv.setViewName("system/adispatch_borrow/shenpibiao");
		mv.addObject("sp", allot);
		mv.addObject("pd", pd);
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}