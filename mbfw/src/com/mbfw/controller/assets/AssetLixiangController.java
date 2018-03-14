package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.controller.system.user.UserController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetProjectProcess;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssertShenpiService;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.ProductsInfoService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

import net.sf.json.JSONArray;

/**
 * 项目立项Controller
 * 
 * @author Wyd
 *
 */
@Controller
@RequestMapping(value = "/asset")
public class AssetLixiangController extends BaseController {

	// @Resource(name = "projectApply")
	@Autowired
	private ProjectApplyService projectApplyService;
	@Resource(name = "assetPurchaserApplyService")
	private AssetPurchaserApplyService assetPurchaserApplyService; // 采购申请服务
	@Autowired
	private AssertShenpiService assertShenpiService ;

	@Autowired
	private ProductsInfoService productsInfoService; // 产品
	
	
	/**
	 * 项目立项管理
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_showForm")
	public ModelAndView showForm() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> projectList = new ArrayList<PageData>();
		
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		
		// ----------分页操作开始----------
		PageOption page = new PageOption(5, 1); // 默认初始化一进来显示就是每页显示5条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//没有关键字查询
		if(pd.getString("key") == null || pd.getString("key") == ""){
			// 根据不同权限显示不同内容
			if (permission == 1) {
				// (3)查询数据库中数据的总条数
				Integer totalnumber = projectApplyService.findTotalProjectNumber();
				// (4)设置总的数据条数
				page.setTotalResult(totalnumber);
				// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				// (6)查询数据库，返回对应条数的数据
				projectList = projectApplyService.listAllProject(page);
			}else if(permission==2){
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("apply_dept", organization_name);// 对应存放组织简称
				page.setPd(pd);
				int total = projectApplyService.listPermission2ProjectTotal(page);
				page.setTotalResult(total);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				projectList = projectApplyService.listPermission2Project(page);
			}else if(permission==3){//普通员工
				String user_name = user.getNAME();//用户名
				String apply_company = user.getSuperior_organization_name();//上级组织简称
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("apply_person", user_name);// 对应存放申请人
				pd.put("apply_company", apply_company);// 对应存放申请公司
				pd.put("apply_dept", organization_name);// 对应存放申请部门
				page.setPd(pd);
				int total = projectApplyService.listPermission3ProjectTotal(page);
				page.setTotalResult(total);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				projectList = projectApplyService.listPermission3Project(page);
			}
		}
		//关键字查询
		else{
			pd.put("key", URLDecoder.decode(pd.getString("key"), "utf-8"));
			String str = (String) pd.get("key");
			if(permission==1){
				page.setPd(pd);
				//获得关键字对应的数据 计算总数量
				int projectLists = projectApplyService.findNumberBySearch(str);
				// (3)设置总的数据条数
				page.setTotalResult(projectLists);
				// (4)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				// (5)查询数据库，返回对应条数的数据
				projectList = projectApplyService.keySearchProject(page);
			}else if(permission==2){
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("apply_dept", organization_name);// 对应存放所在部门
				page.setPd(pd);
				int total = projectApplyService.keySearchPermission2ProjectTotal(page);
				page.setTotalResult(total);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				projectList = projectApplyService.keySearchPermission2Project(page);
			}else if(permission==3){
				String user_name = user.getNAME();//用户名
				String apply_company = user.getSuperior_organization_name();//上级组织简称
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("apply_person", user_name);// 对应存放申请人
				pd.put("apply_company", apply_company);// 对应存放申请公司
				pd.put("apply_dept", organization_name);// 对应存放申请部门
				page.setPd(pd);
				int total = projectApplyService.keySearchPermission3ProjectTotal(page);
				page.setTotalResult(total);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				projectList = projectApplyService.keySearchPermission3Project(page);
			}
			mv.addObject("key", str);
		}
		
		// ----------分页结束------------------------------
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("delectResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.addObject("returnresult", this.getPageData().getString("returnResult"));// 传送请求打回的结果
		mv.addObject("projectList", projectList);
		mv.addObject("page", page); // 返回对应的分页的内容
		mv.setViewName("system/aproject_manager/apjm_ProjectApply");
		return mv;
	}
	/* ===============================权限================================== */

	/**
	 * 项目立项增加界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_add")
	public ModelAndView projectAddView() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		if(permission==1){
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfo();
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==2){
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfoPermission2(apply_company,apply_dept);
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==3){
			//获取用户名字
			String apply_person = user.getNAME();
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//如果权限为3申请人为用户的名字 上级名称 所在部门
			mv.addObject("apply_person", apply_person);
			mv.addObject("apply_company", apply_company);
			mv.addObject("apply_dept", apply_dept);
		}
		
		
		//查询审批流程供项目立项的时候进行选择
		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
		

		//--------lss
		PageData pd = new PageData();
		pd =this.getPageData();
		List<PageData> product_find = productsInfoService.findProductByClass(pd); // 查找产品
		mv.addObject("product_find", product_find);
		//-------------

//		System.out.println(js);
		
		mv.addObject("approvalProcess", approvalProcess);
		mv.addObject("permission", permission);
		mv.setViewName("system/aproject_manager/apjm_ProjectApplyAdd");
		return mv;
	}
	
	/**
	 * 项目立项增加界面提交
	 * @param file
	 * @param projectApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectApplySave")
	public String projectApplySave(@RequestParam(value = "address", required = false) MultipartFile file,
			ProjectApply projectApply) throws Exception {
		ModelAndView mv = this.getModelAndView();
		//通过session获取登录用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_name = user.getUSERNAME();
		
		String filePath = "";//以前的文件存放路径
		String fp = "";//文件存放路径
		String name = "";//文件存放名字
		if (!file.getOriginalFilename().equals("")) {
			long time = System.currentTimeMillis();
			int number = (int)(Math.random()*900)+100;
			name = time+""+number+""+user_name+"@"+file.getOriginalFilename();
//			filePath = PathUtil.getClasspath() + Const.FILEPATHFILE + name; // 文件上传路径
			fp = PathUtil.getPath("0")+ File.separator + name;
			System.out.println("flag"+fp);
//			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath));
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fp));
		}
//		projectApply.setApply_report_address(filePath);
		projectApply.setApply_report_address(name);
		projectApplyService.saveProjectApply(projectApply);
		
		//----------------scw
		//查询项目工程表中最大的序号ID，这个是项目表中的主键
		Integer maxNumberId = assetPurchaserApplyService.findMaxProjectId();
		//获取选取审批流程的ID和名字
		PageData newDataProcess = new PageData();
		Integer processId = projectApply.getApply_procedure_id();
		String processName = projectApply.getApply_procedure();
		newDataProcess.put("process_ApprovalId", processId);  //流程ID
		newDataProcess.put("process_ApprovalName", processName); //流程名
		
		newDataProcess.put("project_Id", maxNumberId); //项目Id
		newDataProcess.put("project_Name", projectApply.getApply_name()); //项目名
		//根据流程ID和顺序,查询对应流程表中的第一个流程节点的ID和名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(processId);
		newDataProcess.put("current_NodeName", nodeName); //第一个审批节点的名字
		newDataProcess.put("description_Passnumber", 0); //通过的人数
		newDataProcess.put("description_Refusenumber", 0); //不通过的人数
		newDataProcess.put("process_FinishStatus", "未完成"); //该项目流程的完成状态，开始的状态是未完成
		newDataProcess.put("current_NodeOrder", 1); //刚开始的时候设置的审批节点顺序为第一个，这个不设置也可以，因为数据库中默认了为第一个
		newDataProcess.put("project_Stryle", "需要"); //设置该项目流程是需要进行审批操作
		newDataProcess.put("process_Type", "项目立项");
		//将数据插入到项目过程表中
		assetPurchaserApplyService.saveOneProjectProcessInfo(newDataProcess);
		//---------------------			
//		mv.setViewName("save_result");
//		return mv;
		return "redirect:/asset/atp_showForm?saveResult=success";
	}

	
	/**
	 * 删除选中的项目
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_delect")
	public String projectDelect(@RequestParam(value = "info", required = false) String str) throws Exception {
		PageData pd = this.getPageData();
		String[] s = str.split("@");
		int[] intArr = new int[s.length];
		for (int i = 0; i < s.length - 1; i++) {
			intArr[i] = Integer.parseInt(s[i + 1]);
		}
		List<PageData> pds = projectApplyService.searchProject(intArr);
		for (PageData pageData : pds) {
			String path = (String) pageData.get("apply_report_address");
			path = PathUtil.getPath("0")+File.separator+path;
			File file = new File(path);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}
		projectApplyService.deleteProject(intArr);
		//删除流程表对应的数据
		projectApplyService.deleteProcessProject(intArr);
		//获得搜素的关键字
		
		String key = URLDecoder.decode(pd.getString("key"), "utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		return "redirect:/asset/atp_showForm?delectResult=success"+"&&key="+key;
	}

	
	/**
	 * 修改选中的项目
	 * @param number
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_modify")
	public ModelAndView projectModify(@RequestParam(value = "info", required = false) int number) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pds = this.getPageData();
		
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		
		if(permission==3){
			//获取用户名字
			String apply_person = user.getNAME();
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//如果权限为3申请人为用户的名字 上级名称 所在部门
			mv.addObject("apply_person", apply_person);
			mv.addObject("apply_company", apply_company);
			mv.addObject("apply_dept", apply_dept);
		}
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
		JSONArray js = JSONArray.fromObject(info);
		
		// 查找产品修改时进行选择
		PageOption page = new PageOption(100000, 1);//瑕疵
		List<PageData> product_find = productsInfoService.findProduct(page); 
		
		PageData pd = projectApplyService.searchModifyProject(number);
		String file_address = (String) pd.get("apply_report_address");
		String file_name = "";
		if(file_address.isEmpty()){
			file_name = "无申请报告";
		}else{
//			String file_names = file_address.split("/")[file_address.split("/").length - 1];
//			file_name = file_names.split("@")[1];
			file_name = file_address.split("@")[1];
		}
		
		pd.put("key", URLDecoder.decode(pds.getString("key"), "utf-8"));
		mv.addObject("pd", pd);
		mv.addObject("file_name", file_name);
		mv.addObject("institutionInfo", js);
		mv.addObject("product_find", product_find);
		mv.addObject("approvalProcess", approvalProcess);
		mv.addObject("permission", permission);
		mv.setViewName("system/aproject_manager/apjm_ProjectApplyModify");
		return mv;
	}

	
	/**
	 * 更新选中的项目
	 * @param file
	 * @param projectApply
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_update")
	public String projectUpdate(@RequestParam(value = "address", required = false) MultipartFile file,
			ProjectApply projectApply) throws Exception {
		ModelAndView mv = this.getModelAndView();
		String file_address = projectApply.getApply_report_address();
//		String file_name = file_address.split("/")[file_address.split("/").length - 1];
		//通过session获取登录用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_name = user.getUSERNAME();
		String filePath = "";
		file_address = PathUtil.getPath("0")+File.separator+file_address;//改后新增的语句
//		null != file && !(file).isEmpty()
		if (!file.getOriginalFilename().equals("")) {
			File old_file = new File(file_address);
			if (old_file.isFile() && old_file.exists()) {
				old_file.delete();
			}
			long time = System.currentTimeMillis();
			int number = (int)(Math.random()*900)+100;
			String name = time+""+number+""+user_name+"@"+file.getOriginalFilename();
//			filePath = PathUtil.getClasspath() + Const.FILEPATHFILE + name; // 文件上传路径
			filePath = PathUtil.getPath("0") + File.separator + name; // 文件上传路径
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(filePath));
//			projectApply.setApply_report_address(filePath);
			projectApply.setApply_report_address(name);
		}
		projectApplyService.updateProject(projectApply);
		//创建PageData存放信息 更新流程信息表
		PageData pdInfo = new PageData();
		String apply_id = projectApply.getApply_id();//项目ID
		String apply_name = projectApply.getApply_name();//项目名
		int process_id = projectApply.getApply_procedure_id();//流程ID
		String process_name = projectApply.getApply_procedure();//流程名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(process_id);//节点ID
		pdInfo.put("project_Id", apply_id);
		pdInfo.put("project_Name", apply_name);
		pdInfo.put("process_ApprovalId", process_id);
		pdInfo.put("process_ApprovalName", process_name);
		pdInfo.put("current_NodeName", nodeName);
		pdInfo.put("process_Type", "项目立项");
		assertShenpiService.editFromOthersProjectOption(pdInfo);
//		mv.setViewName("save_result");
//		return mv;
		//页面上的搜索关键字
		String key = projectApply.getKey();
		key = java.net.URLEncoder.encode(key,"utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		return "redirect:/asset/atp_showForm?updateResult=success"+"&&key="+key;
	}
	
	/**
	 * 查看选中的项目
	 * @param number
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_check")
	public ModelAndView projectCheck(@RequestParam(value = "info", required = false) int number) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pds = this.getPageData();
		PageData pd = projectApplyService.searchModifyProject(number);
		String file_address = (String) pd.get("apply_report_address");
//		String file_name = file_address.split("/")[file_address.split("/").length - 1];
		String file_name = "";
		pd.put("key", URLDecoder.decode(pds.getString("key"), "utf-8"));
		mv.addObject("pd", pd);
		if (file_address.isEmpty()) {
			file_name = "无申请报告";
		}else{
			file_name = file_address.split("@")[1];
		}
		mv.addObject("file_name", file_name);
		mv.setViewName("system/aproject_manager/apjm_ProjectApplyCheck");
		return mv;
	}
	
	/**
	 * 查看项目的审批详情
	 * @param number
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_check_approval")
	public ModelAndView projectApprovalCheck(@RequestParam(value = "info", required = false) int number) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pds = this.getPageData();
		List<PageData> checkApprovalProject = projectApplyService.checkApprovalProject(number);
		mv.addObject("checkApprovalProject", checkApprovalProject);
		
		mv.addObject("key", URLDecoder.decode(pds.getString("key"), "utf-8"));
		mv.setViewName("system/aproject_manager/apjm_ProjectApprovalCheck");
		return mv;
	}
	
	/**
	 * 请求打回选中的项目 
	 * 此功能暂时不要
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_return")
	public String projectReturn(@RequestParam(value = "info", required = false) String str) throws Exception {
		String[] s = str.split("@");
		int[] intArr = new int[s.length];
		for (int i = 0; i < s.length - 1; i++) {
			intArr[i] = Integer.parseInt(s[i + 1]);
		}
		projectApplyService.updateReturnProject(intArr);
		return "redirect:/asset/atp_showForm?returnResult=success";
	}

	/**
	 * 关键字查询
	 * 现在不用了 查询直接整合到了页面加载列表的后台atp_showForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/atp_project_search")
	public ModelAndView projectSearch() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> projectList = new ArrayList<PageData>();
		int totalnumber = 0;
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		
		pd.put("key", URLDecoder.decode(pd.getString("key"), "utf-8"));
		String str = (String) pd.get("key");
		
		
		// ----------分页操作开始----------
		PageOption page = new PageOption(5, 1); // 默认初始化一进来显示就是每页显示5条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		if(permission==1){
			page.setPd(pd);
			//获得关键字对应的数据 计算总数量
			int projectLists = projectApplyService.findNumberBySearch(str);
			// (3)设置总的数据条数
			page.setTotalResult(projectLists);
			// (4)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (5)查询数据库，返回对应条数的数据
			projectList = projectApplyService.keySearchProject(page);
		}else if(permission==2){
			String organization_name = user.getOrganization_name();// 所在公司名称
			pd.put("apply_dept", organization_name);// 对应存放所在部门
			page.setPd(pd);
			int total = projectApplyService.keySearchPermission2ProjectTotal(page);
			page.setTotalResult(total);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			projectList = projectApplyService.keySearchPermission2Project(page);
		}else if(permission==3){
			String user_name = user.getNAME();//用户名
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String organization_name = user.getOrganization_name();// 所在公司名称
			pd.put("apply_person", user_name);// 对应存放申请人
			pd.put("apply_company", apply_company);// 对应存放申请公司
			pd.put("apply_dept", organization_name);// 对应存放申请部门
			page.setPd(pd);
			int total = projectApplyService.keySearchPermission3ProjectTotal(page);
			page.setTotalResult(total);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			projectList = projectApplyService.keySearchPermission3Project(page);
		}
		
		// ----------分页结束------------------------------
		totalnumber = projectList.size();
		String searchresult="";
		if(totalnumber!=0){
			searchresult = "success";
		}
		mv.addObject("key", str);
		mv.setViewName("system/aproject_manager/apjm_ProjectApply");
		mv.addObject("searchresult", searchresult);// 传送增加项目的结果
		mv.addObject("projectList", projectList);
		mv.addObject("page", page); // 返回对应的分页的内容
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
