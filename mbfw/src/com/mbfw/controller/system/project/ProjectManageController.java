package com.mbfw.controller.system.project;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.system.Role;
import com.mbfw.service.system.appuser.AppuserService;
import com.mbfw.service.system.project.ProjectManageService;
import com.mbfw.service.system.role.RoleService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.Jurisdiction;
import com.mbfw.util.MD5;
import com.mbfw.util.ObjectExcelView;
import com.mbfw.util.PageData;

@Controller
@RequestMapping(value = "/project")
public class ProjectManageController extends BaseController {

	@Resource(name = "projectManageService")
	private ProjectManageService projectManageService;
	/**
	 * 显示项目列表
	 */
	@RequestMapping(value = "/listAllProjectManage")
	public ModelAndView listUsers(Page page) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			page.setPd(pd);
			List<PageData> list = projectManageService.listAllProjectManage(pd);
			mv.setViewName("system/aproject_manager/my_apjm_ApprovalList");
			mv.addObject("list", list);
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

}
