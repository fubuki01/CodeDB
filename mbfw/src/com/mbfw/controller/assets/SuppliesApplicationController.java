package com.mbfw.controller.assets;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesApplication;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetApproverProcessService;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.assets.SuppliesApplicationService;
import com.mbfw.service.assets.SuppliesStoreService;
import com.mbfw.service.assets.SuppliesUseService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.PageData;



@Controller
@RequestMapping(value = "/asset")
public class SuppliesApplicationController extends BaseController{
	@Resource(name = "SuppliesApplicationService")
	private SuppliesApplicationService suppliesApplicationService;
	@Autowired
	private SuppliesApplicationService ams;
	@Resource(name = "projectApplyService")
	private ProjectApplyService projectApplyService;
	@Autowired
	private AssetApproverProcessService aaps;  //审批流程Service
	@Autowired
	private AssetPurchaserApplyService assetPurchaserApplyService;
	/**
	 * 耗材采购申请页面
	 */
	@RequestMapping(value = "/acm_apply")
	public ModelAndView apply() throws Exception {
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

		PageOption page = new PageOption(8, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
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
					Integer totalnumber = suppliesApplicationService.findTotalSqDataNumber();
					//(4)设置总的数据条数
					page.setPd(pd);
					page.setTotalResult(totalnumber); 
					//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
					//(6)查询数据库，返回对应条数的数据
					listApprover = suppliesApplicationService.listSqGetPageApprover(page);
				}
				//--------进行检索处理
				else{
					//(3)获取传送过来的进行检索的审核人员的姓名
					String searchName = pd.getString("retrieve_content");
					//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
					page.setPd(pd);
					//(5)查询对应审核人员姓名的数据总条数
					Integer totalnumber = suppliesApplicationService.findNumberSqBySearchName(page);
					//(6)设置总的数据条数
					page.setTotalResult(totalnumber);
					//(7)设置需要显示的数据的索引
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
					//(8)查询数据库，返回对应检索姓名的数据
					listApprover = suppliesApplicationService.listSqSearchNameApprover(page);
					}
		mv.addObject("permission",permission);
		mv.addObject("page",page);       
		mv.addObject("listApprover", listApprover);
		mv.setViewName("system/aconsume_material/shenqing");
		return mv;
	}

	
//	新增耗材申请页面
	@RequestMapping(value="/shenqing_add")
	public ModelAndView shenqing_add(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		JSONArray js = JSONArray.fromObject(info);
		mv.addObject("institutionInfo", js);
		
		//显示下拉框中的耗材编码
	    mv.addObject("product_code_used", suppliesApplicationService.find_SuppliesApplication(pd));
		//获取审批流程的内容
		List<PageData> allProcessInfo =  aaps.findAllProcessToProject();
		mv.addObject("processinfo" ,allProcessInfo );  //返回所有的审批流程的信息
		//---------
		mv.setViewName("system/aconsume_material/shenqing_add");
		mv.addObject("msg", "suppliesSq");
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 显示耗材编码对应的信息
	 */
	@RequestMapping(value = "/fill_suppliesApplication")	

	public @ResponseBody PageData fill_suppliesApplication(Page page) throws Exception {
		PageData pd = this.getPageData();
//		//获取入库单的唯一id号
//		String id = pd.getString("id");
//		
		//获取入库单的唯一id号
	   String product_code = pd.getString("product_code");
		
		PageData fill_asset_info = suppliesApplicationService.find_product_to_add_supplies(product_code);
		System.out.println(fill_asset_info);
		return fill_asset_info;
	}
	
	/**
	 * 保存申请信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/saveSq")
	public String  saveSq() throws Exception{
//		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		String product_name = pd.getString("supplies_name").split("@")[1];
		pd.put("supplies_name", product_name);
		//添加耗材申请到数据库
		suppliesApplicationService.saveSq(pd);
		
		
        //生成采购单号
		String dateString ="";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		dateString = formatter.format(currentTime);
		pd.put("purchase_order", dateString);
		
		//----------处理添加审批流程的操作开始----scw--
		// 获取选取审批流程的ID和名字
		PageData newDataProcess = new PageData();
		int processId = Integer.parseInt(pd.getString("apply_procedure_id"));
		String processName = pd.getString("apply_procedure");
		newDataProcess.put("process_ApprovalId", processId); // 流程ID
		newDataProcess.put("process_ApprovalName", processName); // 流程名

		newDataProcess.put("project_Id", pd.get("id")); // 获取添加申请的id
		newDataProcess.put("project_Name", pd.getString("supplies_name")); // 耗材名称
		// 根据流程ID和顺序,查询对应流程表中的第一个流程节点的ID和名字
		String nodeName = assetPurchaserApplyService.findProcessNodeName(processId);
		newDataProcess.put("current_NodeName", nodeName); // 第一个审批节点的名字
		newDataProcess.put("description_Passnumber", 0); // 通过的人数
		newDataProcess.put("description_Refusenumber", 0); // 不通过的人数
		newDataProcess.put("process_FinishStatus", "未完成"); // 该项目流程的完成状态，开始的状态是未完成
		newDataProcess.put("current_NodeOrder", 1); // 刚开始的时候设置的审批节点顺序为第一个，这个不设置也可以，因为数据库中默认了为第一个
		newDataProcess.put("project_Stryle", "需要"); // 设置该项目流程是需要进行审批操作
		newDataProcess.put("process_Type", "耗材申请");// 设置该项目流程进行审批的类型
		// 将数据插入到项目过程表中
		assetPurchaserApplyService.saveOneProjectProcessInfo(newDataProcess);
		//----------处理添加审批流程的操作结束----scw--
		
//		mv.addObject("msg","success");  //标识更新信息成功
//		mv.setViewName("save_result");
		
//		return mv;
		return "redirect:/asset/acm_apply";
	}
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delete_Sq")
	@ResponseBody
	public Object deleteAllSqData() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String ArrayAllot_ids[] = Allot_ids.split(",");
			Integer newIds[] = new Integer[ArrayAllot_ids.length];
			for(int i = 0; i < ArrayAllot_ids.length; i++){
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
			}
			suppliesApplicationService.deleteSqData(newIds);
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
//	点击删除，根据ID进行删除对应的人员（注：只是将该人员在审核表中数据进行清理，而不是删除该用户）
		@RequestMapping(value="/delSq")
		public void delSq(PrintWriter out) throws Exception{
			ModelAndView mav = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			suppliesApplicationService.deleteSq(pd);	
			out.write("success");
			out.close();	

}
		

		/**
		 * 跳转修改页面修改页面
		 */
		@RequestMapping(value = "/EditSq")
		public ModelAndView EditSq() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			PageData eSq = suppliesApplicationService.EditSqData(pd);	
			mv.setViewName("system/aconsume_material/shenqing_edit");
			mv.addObject("eSq", eSq);
			mv.addObject("pd", pd);
			return mv;
		}
		
		/**
		 * 提交修改表
		 */
		@RequestMapping(value = "/edit_Sq")
		public ModelAndView edit_Sq() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			suppliesApplicationService.updateSq(pd);
			
			mv.addObject("msg", "success");
		
			mv.setViewName("save_result");
			return mv;
		}

}
