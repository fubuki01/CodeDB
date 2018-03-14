package com.mbfw.controller.assets;

import java.io.PrintWriter;
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
import com.mbfw.entity.assets.AssetRecycleManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetGetService;
import com.mbfw.service.assets.AssetRecycleService;
import com.mbfw.service.assets.AssetTableManageService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssetManageRecycleController extends BaseController{
	@Autowired
	private ProjectApplyService projectApplyService;
	@Resource(name = "assetRecycleService")
	private AssetRecycleService assetRecycleService;
	@Resource(name = "assetGetService")
	private AssetGetService assetGetService;
	@Resource(name = "assetTableManageService")
	private AssetTableManageService assetTableManageService;
	
	/**
	 * 显示资产回收模块中数据
	 */
	@RequestMapping(value = "/aucs_recycle")
	public ModelAndView recycle() throws Exception {
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
		if(pd.getString("retrieve_content") == null ||  ("").equals( pd.getString("retrieve_content"))){
			page.setPd(pd);
			//(3)查询数据库中数据的总条数
			Integer totalnumber = assetRecycleService.findTotalRecycleDataNumber(page);
			//(4)设置总的数据条数
			page.setTotalResult(totalnumber); 
			//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
			//(6)查询数据库，返回对应条数的数据
			listApprover = assetRecycleService.listPdRecyclePageApprover(page);
			}
		//--------进行检索处理
		else{
			//(3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetRecycleService.findNumberRecycleBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			listApprover = assetRecycleService.listRecycleSearchNameApprover(page);
			}		
		
		//List<PageData> asset_datahouse = assetRecycleService.findDataStoreHouse(pd);
		mv.addObject("count",this.getPageData().getString("count"));
		mv.addObject("page",page);     //返回对应的分页的内容
		mv.addObject("listApprover", listApprover);		//返回对应条数的数据
		mv.addObject("permission",permission);
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.setViewName("/system/asset_callback/asset_recycle");

		return mv;
	}
	
		
	/**
	 * 添加信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/save_recycle")
	public String saveRecycleData() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();	
		//保存提交信息
		assetRecycleService.saveRecycleData(pd);
		//更改资产表的资产状态
		pd.put("asset_status", "回收");
		pd.put("asset_code", pd.getString("asset_code"));
		//当资产回收时，保证资产表中最新的信息中部门等信息的值应当为空
		assetRecycleService.deleteToAssetInfo(pd);
		assetTableManageService.changeAssetStatus(pd);
		//改变领用表的有效值
		assetRecycleService.changeValue(pd);		
		return "redirect:/asset/aucs_recycle?saveResult=success";
	}
	

	/**
	 * 新增资产回收表
	 */
	@RequestMapping(value = "/goAddB")
	public ModelAndView goAddB() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd =this.getPageData();
		PageOption page = new PageOption(100000, 1);
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
		List<PageData> assetCodeFind = new ArrayList<PageData>();
		assetCodeFind=assetTableManageService.find_GetAssetCode(pd);	
		mv.addObject("assetCodeFind", assetCodeFind);
		
//		//查询公司和对应的部门生成json 供二级联动适应
//		String info = projectApplyService.institutionInfo();
//		//查询审批流程供项目立项的时候进行选择
//		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
//		JSONArray js = JSONArray.fromObject(info);

		mv.addObject("msg", "save_recycle");
//		mv.addObject("institutionInfo", js);
		mv.setViewName("system/asset_callback/recycletable");
		return mv;
	}
	
	/**
	 * 资产封装成json，用ajax请求
	 */
	@RequestMapping(value = "/find_recycleinfo")		
	public @ResponseBody PageData find_recycleinfo(Page page) throws Exception {
		PageData pd = this.getPageData();
		//获取入库单的唯一id号
		String id = pd.getString("id");
		PageData recycleinfo = assetTableManageService.findAssetInfo_RecycleInfo(id);
		return recycleinfo;
	}
	
	
	/**
	 * 增加修改表
	 */
	@RequestMapping(value = "/edit_recycle")
	public String editGetApprover() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		assetRecycleService.editRecycleApprover(pd);

		return "redirect:/asset/aucs_recycle?updateResult=success";
	}
	
	
	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/go_edit_recycle")
	public ModelAndView goEditRecycle() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10000, 1);
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
		
		AssetRecycleManage arm = assetRecycleService.findRecycleEditData(pd);
		System.out.println(arm.getAsset_name());
		List<PageData> assetCodeFind = new ArrayList<PageData>();
		assetCodeFind=assetTableManageService.find_GetAssetCode(pd);	
		mv.addObject("assetCodeFind", assetCodeFind);
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		List<PageData> approvalProcess = projectApplyService.findApproveProcess();
		JSONArray js = JSONArray.fromObject(info);	
		
		mv.setViewName("system/asset_callback/recycle_edit");
		mv.addObject("institutionInfo", js);
		mv.addObject("arm", arm);
		mv.addObject("pd", pd);
		return mv;
	}

	
	/**
	 * 删除信息
	 * @throws Exception 
	 */
	@RequestMapping(value = "/delete_recycle")
	public String deleteRecycle() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int id = Integer.parseInt(pd.getString("id"));
		PageData search = assetRecycleService.searchStatusInfo(id);
		String orig_status = search.getString("orig_status");
		String code = search.getString("asset_code");
		String orig_company = search.getString("orig_company");
		String orig_department = search.getString("orig_department");
		String orig_user = search.getString("orig_user");
		String asset_use = search.getString("asset_use");
	
		pd.put("orig_status", orig_status);
		pd.put("asset_code", code);
		pd.put("orig_company", orig_company);
		pd.put("orig_department", orig_department);
		pd.put("orig_user", orig_user);
		pd.put("asset_use", asset_use);
		
		assetRecycleService.returnToAssetInfo(pd);
		
		assetRecycleService.deleteRecycle(pd);
		assetRecycleService.returnPriorStatus(pd);
		assetRecycleService.returnValid(pd);
		return "redirect:/asset/aucs_recycle?deleteResult=success";
	}

	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delete_AllRecycle")
	@ResponseBody
	public Object deleteAllRecycleData() {
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
				PageData search = assetRecycleService.searchStatusInfo(newIds[i]);
				String orig_status = search.getString("orig_status");
				String code = search.getString("asset_code");
				pd.put("orig_status", orig_status);
				pd.put("asset_code", code);
				assetRecycleService.returnPriorStatus(pd);
				assetRecycleService.returnValid(pd);
			}
			assetRecycleService.deleteAllRecycleData(newIds);
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
	 * 查询资产数量
	 */
	@RequestMapping(value = "/check_AssetCountR")
	public String checkAssetCountR() throws Exception {
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
		int count = assetRecycleService.checkAssetCount(pd);
		return  "redirect:/asset/aucs_recycle?count="+count;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
