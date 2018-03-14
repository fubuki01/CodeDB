package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetPurchaseBill;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetApproverProcessService;
import com.mbfw.service.assets.AssetPurchaseBillService;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.ProductsInfoService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.assets.ProviderEditService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssetCaigouController extends BaseController {
	@Resource(name = "assetPurchaserApplyService")
	private AssetPurchaserApplyService assetPurchaserApplyService; // 采购申请服务
	@Resource(name = "assetPurchaseBillService")
	private AssetPurchaseBillService assetPurchaseBillService;//保存采购单服务

	@Autowired
	private ProviderEditService providerEditService;  // 供应商
	@Autowired
	private AssetApproverProcessService aaps;  //审批流程Service
	@Autowired
	private ProjectApplyService projectApplyService; // 部门
	@Autowired
	private ProductsInfoService productsInfoService; // 产品

	/**
	 * 采购申请页面
	 */
	@RequestMapping(value = "/apl_Caigou_apply")
	public ModelAndView apl_Caigou_apply() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Integer totalnumber = 0;
		List<PageData> purchase_bill = new ArrayList<PageData>();
		pd = this.getPageData();
		// ----------分页操作开始----------
		PageOption page = new PageOption(8, 1); // 默认初始化一进来显示就是每页显示5条，当前页面为1

		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字


		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		pd.put("apply_person", apply_person);

		page.setPd(pd);
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){

			// (3)查询数据库中数据的总条数
			totalnumber = assetPurchaseBillService.find_puchase_bill_totalnumber(page);

			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));

			// (6)查询数据库，返回对应条数的数据
			purchase_bill = assetPurchaseBillService.find_puchase_bill(page);	
			// ----------分页结束------------------------------
		}else{
			totalnumber = assetPurchaseBillService.find_puchase_bill_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			purchase_bill = assetPurchaseBillService.find_puchase_bill_bykey(page);	
		}

		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("purchase_bill", purchase_bill);
		mv.addObject("page", page);
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送增加项目的结果
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("saveApplyResult", this.getPageData().getString("saveResultApply"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		//mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.setViewName("system/apurchase_manager/apurchase_apply");

		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

	/**
	 * 
	 * 填写采购申请单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/apl_insert")
	public ModelAndView apl_insert() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//--------scw------------
		List<PageData> allProcessInfo =  aaps.findAllProcessToProject();
		mv.addObject("processinfo" ,allProcessInfo );  //返回所有的审批流程的信息
		//------------

		// --------lss 
		//查询公司和对应的部门生成json 供二级联动适应
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

		mv.addObject("approvalProcess", approvalProcess);
		List<PageData> product_find = productsInfoService.findProductByClass(pd); // 查找产品
		mv.addObject("product_find", product_find);
		//---------

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		mv.addObject("date",df.format(new Date()));
		mv.addObject("permission", permission);
		mv.setViewName("system/apurchase_manager/apurchase_insert");
		mv.addObject("msg", "save_purcahse_apply");
		mv.addObject("pd", pd);
		return mv;
	}
	/**
	 * 
	 * 保存填写采购申请单
	 */
	@RequestMapping(value = "/save_purcahse_apply")
	public String save_purcahse_apply(@RequestParam(value = "address", required = false) MultipartFile file,
			ProjectApply projectApply) throws Exception {

		//---------lss
		//通过session获取登录用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_name = user.getUSERNAME();

		//String filePath = "";
		String filePath = "";
		String name="";
		String fp ="";
		if (!file.getOriginalFilename().equals("")) {
			long time = System.currentTimeMillis();
			int number = (int)(Math.random()*900)+100;
			name= time+""+number+""+user_name+"@"+file.getOriginalFilename();
			fp = PathUtil.getPath("0")+ File.separator + name;
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fp));
		}
		
		projectApply.setApply_report_address(name);
		assetPurchaserApplyService.save_apurchase_apply(projectApply);
		//----------------结束

		//----------------scw

		//获取选取审批流程的ID和名字
		PageData newDataProcess = new PageData();
		int processId = projectApply.getApply_procedure_id();
		String processName = projectApply.getApply_procedure();
		newDataProcess.put("process_ApprovalId", processId);  //流程ID
		newDataProcess.put("process_ApprovalName", processName); //流程名

		newDataProcess.put("project_Id", projectApply.getApply_id()); //项目Id
		newDataProcess.put("project_Name", projectApply.getApply_name()); //项目名
		//根据流程ID和顺序,查询对应流程表中的第一个流程节点的ID和名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(processId);
		newDataProcess.put("current_NodeName", nodeName); //第一个审批节点的名字
		newDataProcess.put("description_Passnumber", 0); //通过的人数
		newDataProcess.put("description_Refusenumber", 0); //不通过的人数
		newDataProcess.put("process_FinishStatus", "未完成"); //该项目流程的完成状态，开始的状态是未完成
		newDataProcess.put("current_NodeOrder", 1); //刚开始的时候设置的审批节点顺序为第一个，这个不设置也可以，因为数据库中默认了为第一个
		newDataProcess.put("project_Stryle", "需要"); //设置该项目流程是需要进行审批操作
		newDataProcess.put("process_Type", "项目立项");//设置该项目流程进行审批的类型
		//将数据插入到项目过程表中
		assetPurchaserApplyService.saveOneProjectProcessInfo(newDataProcess);
		//---------------------	
		return "redirect:/asset/apl_Caigou_apply?saveResultApply=success";
	}

	/**
	 * 
	 * 填写采购单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/apl_insert_apply")
	public ModelAndView apl_insert_apply() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageOption page = new PageOption(100000, 1);

		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();

		String apply_person = user.getNAME();//获取用户名字

		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		pd.put("apply_person", apply_person);

		//------查询供应商
		List<PageData> providerFind = providerEditService.find_provider_insert(page);
		mv.addObject("providerFind",providerFind);
		//---- 结束

		// ---- 查询采购申请通过的表 多级联动
		//page.setPd(pd);
		String project_manager_info = assetPurchaserApplyService.pass_approval_info(pd);
		mv.addObject("project_manager_info",project_manager_info); /// 

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("delivery_time", formatter.format(currentTime));

		//--------
		mv.setViewName("system/apurchase_manager/apurchase_insert_apply");
		mv.addObject("msg", "save_purchase_bill");


		mv.addObject("flag","saveP");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 保存采购单 
	 */
	@RequestMapping(value = "/save_purchase_bill")
	public String save_purcahse_bill(@RequestParam(value = "file_upload", required = false) MultipartFile file[],
			AssetPurchaseBill purchaseBill) throws Exception {
		//---------lss
		//通过session获取登录用户名
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String user_name = user.getUSERNAME();

		String fp ="";
		StringBuffer upload_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.getOriginalFilename().equals("")) {
				long time = System.currentTimeMillis();
				int number = (int)(Math.random()*900)+100;
				String name = time+""+number+""+user_name+"@"+multipartFile.getOriginalFilename(); // 文件名
				fp = PathUtil.getPath("0")+ File.separator + name;
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(fp));
				upload_file.append(name+"#");
			}
		}
		if(!upload_file.equals("")){
			purchaseBill.setPurchase_upload(upload_file.toString());
		}
		//----------------结束

		String flag = purchaseBill.getFlag();//.getString("flag");

		String[] provider = purchaseBill.getProvider().split(":");
		String provider_code = provider[0];
		String provider_name = provider[1];

		purchaseBill.setProvider_code(provider_code );
		purchaseBill.setProvider_name(provider_name);

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);

		//添加编码
		purchaseBill.setDevice_code(purchaseBill.getDevice_code());

		String Result="";
		int saveRet=0;
		Integer apply_id =purchaseBill.getApply_id();
		purchaseBill.setApply_id(purchaseBill.getApply_id());
		if(flag.equals("saveP")){
			purchaseBill.setPurchase_code(dateString);
			assetPurchaseBillService.save_purchase_bill(purchaseBill);
			Result="redirect:/asset/apl_Caigou_apply?saveResult=success";
			saveRet =1;

		}else{
			if(purchaseBill.getFile_hidden().equals("former")){
				purchaseBill.setPurchase_upload(purchaseBill.getFile_address());
			}
			assetPurchaseBillService.edit_purchase_bill(purchaseBill);
			Result="redirect:/asset/apl_Caigou_apply?updateResult=success";
		}
		if(saveRet==1){

			assetPurchaserApplyService.update_finish_purchase_bill(apply_id);
		}
		return Result;
	}

	/**
	 * 修改采购单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/edit_purchase_bill")
	public ModelAndView edit_purchase_bill() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd=this.getPageData();

		PageOption page = new PageOption(100000, 1);
		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字
		pd.put("apply_person", apply_person);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		page.setPd(pd);
		pd = assetPurchaseBillService.find_puchase_bill_byid(pd); // 传入选定的id
		String apply_name = pd.getString("project_name");
		mv.addObject("apply_name",apply_name);

		// ---- 查询采购申请通过的表 多级联动
		String project_manager_info = assetPurchaserApplyService.pass_approval_info(pd);
		mv.addObject("project_manager_info",project_manager_info); /// 

		String file_address = (String) pd.get("purchase_upload");// 获得文件上传
		String file_name = "";
		if(file_address.isEmpty()){
			file_name = "无申请报告";
		}else{
			//String file_names = file_address.split("/")[file_address.split("/").length - 1];
			file_name = file_address;
		}
		mv.addObject("file_address", file_address);
		mv.addObject("file_name", file_name);
		//------查询供应商
		List<PageData> providerFind = providerEditService.find_provider_insert(page);
		mv.addObject("providerFind",providerFind);
		//---- 结束

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("delivery_time", formatter.format(currentTime));

		mv.setViewName("system/apurchase_manager/apurchase_insert_apply");
		mv.addObject("msg", "save_purchase_bill");
		mv.addObject("flag","editP");
		mv.addObject("pd",pd);
		return mv;

	}

	/**
	 * 删除采购单
	 * @param out
	 */
	@RequestMapping(value = "/delete_purchase_bill")
	public void delete_purchase_bill(PrintWriter out){
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			assetPurchaseBillService.delete_purchase_bill(pd);
			// 修改项目立项状态
			assetPurchaseBillService.update_status_project(pd);
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 批量删除采购单
	 * 
	 */

	@RequestMapping(value = "/delete_all_purchase_bill")
	@ResponseBody
	public PageData delete_all_purchase_bill() {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String del_purchase_bill = pd.getString("del_purchase_bill");
			StringBuffer str_id =new StringBuffer();
			if (null != del_purchase_bill && !"".equals(del_purchase_bill)) {
				String array_del_purchase_bill[] = del_purchase_bill.split(",");
				for (String string : array_del_purchase_bill) {
					String []str1 = string.split("@");
					str_id.append(str1[0]+",");
					pd.put("apply_id", str1[1]);
					assetPurchaseBillService.update_status_project(pd); // 恢复项目立项状态
				}
				String []str2 = str_id.toString().split(",");
				assetPurchaseBillService.delete_all_purchase_bill(str2); //删除
				pd.put("msg", "success");
			} else {
				pd.put("msg", "no");
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return pd;
	}



}
