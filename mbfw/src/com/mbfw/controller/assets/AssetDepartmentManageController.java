package com.mbfw.controller.assets;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetDepartmentManage;
import com.mbfw.service.assets.AssetDepartmentManageService;
import com.mbfw.service.system.menu.MenuService;
import com.mbfw.service.system.role.RoleService;
import com.mbfw.util.Const;
import com.mbfw.util.Jurisdiction;
import com.mbfw.util.PageData;

/**
 * @ClassName: DepartmentController
 * @Description: 部门控制类
 * @author scw
 * @date 2017-09-21
 * 
 */
@Controller
@RequestMapping(value = "/asset")
public class AssetDepartmentManageController extends BaseController {

	@Autowired
	private AssetDepartmentManageService adms;
	
	/**
	 * 显示所有的部门信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/asystem_departmentshow")
	public ModelAndView manage() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		mv.addObject("addresult", pd.getString("addresult"));//返回添加部分的结果
		mv.setViewName("system/asystem_set/asystem_department_list");
		return mv;
	}

	
	@RequestMapping(value = "/table")
	public ModelAndView table() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/asystem_set/asystem_department_table");
		return mv;
	}

	/**
	 * 添加部门（弹出添加部门的对话框）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/asystem_departmentsavedialog")
	public ModelAndView addToDialog() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/asystem_set/asystem_department_add");
		return mv;
	}
	/**
	 * 处理添加部门的操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/asystem_departmentaddoption")
	public ModelAndView addDepartment() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//插入数据到数据库中
		adms.saveDepartmentContent(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	// 修改节点（弹出对话框）
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		ModelAndView mv = this.getModelAndView();

		mv.setViewName("system/asystem_set/asystem_department_edit");
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
