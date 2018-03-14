package com.mbfw.controller.assets;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
 * @ClassName: ProcessController
 * @Description: TODO 部门控制类
 * @author luojie@hnu.edu.cn
 * @date 2017年8月16日 下午5:20:41
 * 
 */
@Controller
@RequestMapping(value = "/process")
public class AssetProcessManageController extends BaseController {
	String menuUrl = "process/manage.do"; // 菜单地址(权限用)
	// @Resource(name = "processService")
	// private AssetProcessManageService processService;

	/**
	 * 显示数据
	 */
	@RequestMapping(value = "/manage")
	public ModelAndView qx() throws Exception {
		ModelAndView mv = this.getModelAndView();
		// PageData pd = new PageData();
		// pd = this.getPageData();

		// AssetDepartmentManage department = departmentService.listAllDepartment(pd);
		// view jsp路径
		mv.setViewName("system/asystem_set/asystem_process_manage");
		// mv.addObject("department", department);
		// System.out.println(department.getDEPARTMENT_NAME());
		return mv;
	}

	// 设置流程节点
	@RequestMapping(value = "/table")
	public ModelAndView setPoint() throws Exception {
		ModelAndView mv = this.getModelAndView();

		mv.setViewName("system/asystem_set/asystem_process_table");
		return mv;
	}

	// 自定义流程
	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		ModelAndView mv = this.getModelAndView();

		mv.setViewName("system/asystem_set/asystem_process_add");
		return mv;
	}
	
	// 自定义流程
		@RequestMapping(value = "/edit")
		public ModelAndView edit() throws Exception {
			ModelAndView mv = this.getModelAndView();

			mv.setViewName("system/asystem_set/asystem_process_edit");
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
