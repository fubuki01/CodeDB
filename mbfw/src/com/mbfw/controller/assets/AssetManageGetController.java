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
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetGetService;
import com.mbfw.service.assets.AssetTableManageService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssetManageGetController extends BaseController {
	@Autowired
	private ProjectApplyService projectApplyService;
	@Resource(name = "assetGetService")
	private AssetGetService assetGetService;
	@Resource(name = "assetTableManageService")
	private AssetTableManageService assetTableManageService;

	/**
	 * 显示资产领用模块中数据
	 */
	@RequestMapping(value = "/aucs_manage")
	public ModelAndView manager() throws Exception {
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
		
		List<PageData> listApprover = new ArrayList<PageData>();
		

		PageOption page = new PageOption(10, 1); // 默认初始化一进来显示就是每页显示10条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		// ------没有进行检索的处理-----
		if (pd.getString("retrieve_content") == null || ("").equals( pd.getString("retrieve_content"))) {
			page.setPd(pd);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = assetGetService.findTotalGetDataNumber(page);
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			listApprover = assetGetService.listPdGetPageApprover(page);
		}
		// --------进行检索处理
		else {
			// (3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			// (4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			// (5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetGetService.findNumberGetBySearchName(page);
			// (6)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (8)查询数据库，返回对应检索姓名的数据
			listApprover = assetGetService.listGetSearchNameApprover(page);
		}
		// List<PageData> asset_datahouse =
		// assetGetService.findDataStoreHouse(pd);
		mv.addObject("count",this.getPageData().getString("count"));
		mv.addObject("page", page); // 返回对应的分页的内容
		mv.addObject("permission",permission);
		mv.addObject("listApprover", listApprover); // 返回对应条数的数据
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.setViewName("/system/asset_used/asset_get");

		return mv;
	}


	/**
	 * 添加信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/save_get")
	public String saveData() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();		
		assetGetService.saveData(pd);
		//把基本数据保存到资产表
		assetGetService.saveToAssetInfo(pd);
		assetGetService.changeValid(pd);
		pd.put("asset_status", "领用");
		assetTableManageService.changeAssetStatus(pd);
		assetGetService.changeIssueValue(pd);
		return "redirect:/asset/aucs_manage?saveResult=success";
	}
	
	/**
	 * 新增资产领用表
	 */
	@RequestMapping(value = "/goAddA")
	public ModelAndView goAddA() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10000, 1);
		String info = "";

		//查询公司和对应的部门生成json 供二级联动适应
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		Integer permission = user.getUser_Permission(); //部门权限		
		String superior_organization_name = user.getSuperior_organization_name();//上一级部门
		String organization_name = user.getOrganization_name();//当前所属部门
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		if(user.getUser_Permission() == 1){
			info = projectApplyService.institutionInfo();		
		}else if(user.getUser_Permission() == 2){
			info =  "[{\""+user.getSuperior_organization_name()+"\":[\""+user.getOrganization_name()+"\"]}]";
		}
		JSONArray js = JSONArray.fromObject(info);
		
		
		List<PageData> assetCodeFind = new ArrayList<PageData>();;		
		assetCodeFind=assetTableManageService.find_IdleAssetCode(pd);	
	
		mv.addObject("assetCodeFind", assetCodeFind);
		mv.setViewName("system/asset_used/gettable");
		mv.addObject("institutionInfo", js);
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 资产封装成json，用ajax请求
	 */
	@RequestMapping(value = "/find_getinfo")		
	public @ResponseBody PageData find_getinfo(Page page) throws Exception {
		PageData pd = this.getPageData();
		//获取入库单的唯一id号
		String id = pd.getString("id");
		PageData getinfo = assetTableManageService.findAssetInfo_GetInfo(id);
		return getinfo;
	}
	
	
	/**
	 * 增加修改表
	 */
	@RequestMapping(value = "/edit_get")
	public String editGetApprover() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("asset_code", pd.getString("asset_code"));
		assetGetService.editGetApprover(pd);
		assetGetService.editToAssetInfo(pd);
		
		return "redirect:/asset/aucs_manage?updateResult=success";
	}

	/**
	 * 修改页面
	 */
	@RequestMapping(value = "/go_edit_get")
	public ModelAndView goEditGet() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10000, 1);
		String info = "";
		//查询公司和对应的部门生成json 供二级联动适应
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		if(user.getUser_Permission() == 1){
			info = projectApplyService.institutionInfo();		
		}else if(user.getUser_Permission() == 2){
			info =  "[{\""+user.getSuperior_organization_name()+"\":[\""+user.getOrganization_name()+"\"]}]";
		}
		JSONArray js = JSONArray.fromObject(info);
		
		AssetGetManage agm = assetGetService.findEditData(pd);				
		mv.setViewName("system/asset_used/get_edit");
		mv.addObject("institutionInfo", js);
		mv.addObject("agm", agm);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 删除信息
	 * @return 
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete_get")
	public String deleteGet() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		int id = Integer.parseInt(pd.getString("id"));
		PageData search = assetGetService.searchStatusInfo(id);
		String orig_status = search.getString("orig_status");
		String code = search.getString("asset_code");
		assetGetService.deleteGet(pd);
		pd.put("orig_status", orig_status);
		pd.put("asset_code", code);
		assetGetService.deleteToAssetInfo(pd);
		assetGetService.returnPriorStatus(pd);
		assetGetService.returnValid(pd);
		
		return "redirect:/asset/aucs_manage?deleteResult=success";
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delete_All")
	@ResponseBody
	public Object deleteAllGetData() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String ArrayAllot_ids[] = Allot_ids.split(",");
			int newIds[] = new int[ArrayAllot_ids.length];
			for (int i = 0; i < ArrayAllot_ids.length; i++) {
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
				PageData search = assetGetService.searchStatusInfo(newIds[i]);
				String orig_status = search.getString("orig_status");
				String code = search.getString("asset_code");
				pd.put("orig_status", orig_status);
				pd.put("asset_code", code);
				assetGetService.deleteToAssetInfo(pd);
				assetGetService.returnPriorStatus(pd);
				assetGetService.returnValid(pd);
			}
			assetGetService.deleteAllGetData(newIds);				
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
	@RequestMapping(value = "/check_AssetCountG")
	public String checkAssetCountG() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//权限设置
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
		Integer permission = user.getUser_Permission(); //部门权限		
		String superior_organization_name = user.getSuperior_organization_name();//上一级部门
		String organization_name = user.getOrganization_name();//当前所属部门
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		int count = assetGetService.checkAssetCount(pd);
		return  "redirect:/asset/aucs_manage?count="+count;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
