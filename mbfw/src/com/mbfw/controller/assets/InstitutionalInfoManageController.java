package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.entity.system.User;
import com.mbfw.entity.assets.InstitutionalInfo;
import com.mbfw.service.assets.InstitutionInfoManageService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

/**
 * 机构信息管理Controller
 * 
 * @author Wyd
 *
 */
@Controller
@RequestMapping(value = "/asset")
public class InstitutionalInfoManageController extends BaseController {
	@Resource(name = "InstitutionalInfo")
	@Autowired
	private InstitutionInfoManageService institutionInfoManageService;
	
	/**
	 * 机构信息管理列表界面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/asystem_institutionalinfoshow")
	public ModelAndView showInstitutionalInfo() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）

		List<PageData> institutionList = new ArrayList<PageData>();
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
				Integer totalnumber = institutionInfoManageService.findTotaInstitutionNumber();
				// (4)设置总的数据条数
				page.setTotalResult(totalnumber);
				// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				// (6)查询数据库，返回对应条数的数据
				institutionList = institutionInfoManageService.listAllInstitution(page);
			} else if (permission == 2) {
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("organization_abbreviation", organization_name);// 对应存放组织简称
				page.setPd(pd);
				int total = institutionInfoManageService.listPermission2InstitutionTotal(page);
				page.setTotalResult(total);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				institutionList = institutionInfoManageService.listPermission2Institution(page);
			} else if (permission == 3) {
				institutionList.clear();
			}
		}
		//关键字查询
		else{
			pd.put("key", URLDecoder.decode(pd.getString("key"), "utf-8"));
			String str = (String) pd.get("key");
			if (permission == 1) {
				page.setPd(pd);
				// 获得关键字对应的数据用于计算数量
				int institutionLists = institutionInfoManageService.keySearchInstitutionNumber(str);
				// (3)查询数据库中数据的总条数
				Integer totalnumber = institutionLists;
				// (4)设置总的数据条数
				page.setTotalResult(totalnumber);
				// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				// (6)查询数据库，返回对应条数的数据
				institutionList = institutionInfoManageService.keySearchInstitution(page);
			} else if (permission == 2) {
				String organization_name = user.getOrganization_name();// 所在公司名称
				pd.put("organization_abbreviation", organization_name);// 对应存放组织简称
				page.setPd(pd);
				int totalnumber = institutionInfoManageService.keySearchPermission2InstitutionTotal(page);
				page.setTotalResult(totalnumber);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				institutionList = institutionInfoManageService.keySearchPermission2Institution(page);
			} else if (permission == 3) {
				institutionList.clear();
			}
			mv.addObject("key", str);
		}
		

		// ----------分页结束------------------------------
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.setViewName("system/asystem_set/asystem_institutional_list");
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加的结果
		mv.addObject("delectresult", this.getPageData().getString("delectResult"));// 传送删除的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改的结果
		mv.addObject("institutionList", institutionList);
		mv.addObject("page", page); // 返回对应的分页的内容
		mv.addObject("permission", permission);//返回用户权限
		return mv;
	}

	// 机构增加界面
	@RequestMapping(value = "/asystem_institutional_add")
	public ModelAndView institutionalAddView() throws Exception {
		ModelAndView mv = this.getModelAndView();

		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）

		// 存放获取的机构简称 以String的方式返回到前段界面
		StringBuffer soa = new StringBuffer();
		if (permission == 1) {// 总行
			boolean flag = false;
			// 获取所有的组织简称
			List<String> oa = institutionInfoManageService.institution1Info();
			for (String string : oa) {
				if (flag) {
					soa.append(",");
				} else {
					flag = true;
				}
				soa.append(string);
			}
		} else if (permission == 2) {//支行
			String organization_name = user.getOrganization_name();// 所在公司名称
			//权限为2暂时上级只能选择自己
			soa.append(organization_name);
			/*List<String> oa = institutionInfoManageService.institution2Info(organization_name);
			boolean flag = false;
			for (String string : oa) {
				if (flag) {
					soa.append(",");
				} else {
					flag = true;
				}
				soa.append(string);
			}*/
		}
		mv.addObject("permission", permission);
		mv.addObject("organization_abbreviation", soa);
		mv.setViewName("system/asystem_set/asystem_institutional_add");
		return mv;
	}

	// 机构增加界面提交
	@RequestMapping(value = "/asystem_institutionalinfoadd")
	public String institutionalSave(InstitutionalInfo institutionalInfo) throws Exception {
		ModelAndView mv = this.getModelAndView();
		String son = institutionalInfo.getSuperior_organization_name();
		if (!son.isEmpty()) {
			PageData pd = institutionInfoManageService.searchInstitutionCode(son);
			int oi = (int) pd.get("organizational_identification");
			institutionalInfo.setSuperior_organizational_identification(oi);
		}
		institutionInfoManageService.saveInstitution(institutionalInfo);
//		mv.setViewName("save_result");
//		return mv;
		return "redirect:/asset/asystem_institutionalinfoshow?saveResult=success";
	}

	// 查看选中的机构
	@RequestMapping(value = "/asystem_institutional_check")
	public ModelAndView projectCheck(@RequestParam(value = "info", required = false) int number) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pds = this.getPageData();
		PageData pd = institutionInfoManageService.searchInstitution(number);
		pd.put("key", URLDecoder.decode(pds.getString("key"), "utf-8"));
		mv.setViewName("system/asystem_set/asystem_institutional_check");
		mv.addObject("pd", pd);
		return mv;
	}

	// 修改选中的机构信息
	@RequestMapping(value = "/asystem_institutional_modify")
	public ModelAndView institutionModify(@RequestParam(value = "info", required = false) int number) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pds = this.getPageData();
		
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		
		PageData pd = institutionInfoManageService.searchInstitution(number);
		// 存放获取的机构简称 以String的方式返回到前段界面
		StringBuffer soa = new StringBuffer();
		if (permission == 1) {// 总行
			boolean flag = false;
			// 获取所有的组织简称
			List<String> oa = institutionInfoManageService.institution1Info();
			for (String string : oa) {
				if (flag) {
					soa.append(",");
				} else {
					flag = true;
				}
				soa.append(string);
			}
		} else if (permission == 2) {
			String organization_name = user.getOrganization_name();// 所在公司名称
			String oAbbreviation = pd.getString("organization_abbreviation");
			if(organization_name.equals(oAbbreviation)){
				String son = user.getSuperior_organization_name();
				soa.append(son);
			}else{
				soa.append(organization_name);
			}
			//权限为2暂时上级只能选择自己 所以不用下面的
			/*List<String> oa = institutionInfoManageService.institution2Info(organization_name);
			boolean flag = false;
			for (String string : oa) {
				if (flag) {
					soa.append(",");
				} else {
					flag = true;
				}
				soa.append(string);
			}*/
		}
		mv.addObject("permission", permission);
		pd.put("key", URLDecoder.decode(pds.getString("key"), "utf-8"));
		mv.setViewName("system/asystem_set/asystem_institutional_modify");
		mv.addObject("pd", pd);
		mv.addObject("organization_abbreviation", soa);
		return mv;
	}

	// 更新选中的机构信息
	@RequestMapping(value = "/asystem_institutional_update")
	public String institutionUpdate(InstitutionalInfo institutionalInfo) throws Exception {
		ModelAndView mv = this.getModelAndView();
		institutionInfoManageService.updateInstitution(institutionalInfo);
//		mv.setViewName("save_result");
//		return mv;
		String key = institutionalInfo.getKey();
		key = java.net.URLEncoder.encode(key,"utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		return "redirect:/asset/asystem_institutionalinfoshow?updateResult=success"+"&&key="+key;
	}

	// 删除选中的机构条目
	@RequestMapping(value = "/asystem_institutional_delect")
	public String InstitutionDelect(@RequestParam(value = "info", required = false) String str) throws Exception {
		PageData pd = this.getPageData();
		List<String> oName = new ArrayList<String>(); 
		String msg = "";
		String[] s = str.split("@");
		int[] intArr = new int[s.length];
		for (int i = 0; i < s.length - 1; i++) {
			intArr[i] = Integer.parseInt(s[i + 1]);
		}
		oName = institutionInfoManageService.judgeInstitutionUse(intArr);
		if(oName.size()==0){
			institutionInfoManageService.deleteInstitution(intArr);
			msg = "success";
		}else{
			msg = "fail";
		}
		
		String key = URLDecoder.decode(pd.getString("key"), "utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		key = java.net.URLEncoder.encode(key,"utf-8");
		return "redirect:/asset/asystem_institutionalinfoshow?delectResult="+msg+"&&key="+key;
	}
	
	
	/**
	 * 关键字插叙
	 * 现在不用了
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/asystem_institutional_search")
	public ModelAndView InstitutionSearch() throws Exception {

		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<PageData> institutionList = new ArrayList<PageData>();

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
		
		if (permission == 1) {
			page.setPd(pd);
			// 获得关键字对应的数据用于计算数量
			int institutionLists = institutionInfoManageService.keySearchInstitutionNumber(str);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = institutionLists;
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			institutionList = institutionInfoManageService.keySearchInstitution(page);
		} else if (permission == 2) {
			String organization_name = user.getOrganization_name();// 所在公司名称
			pd.put("organization_abbreviation", organization_name);// 对应存放组织简称
			page.setPd(pd);
			int totalnumber = institutionInfoManageService.keySearchPermission2InstitutionTotal(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			institutionList = institutionInfoManageService.keySearchPermission2Institution(page);
		} else if (permission == 3) {
			institutionList.clear();
		}
		// ----------分页结束------------------------------

		mv.setViewName("system/asystem_set/asystem_institutional_list");
		mv.addObject("key", str);
		mv.addObject("institutionList", institutionList);
		mv.addObject("page", page); // 返回对应的分页的内容
		mv.addObject("permission", permission);//返回用户权限
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
