package com.mbfw.controller.system.user;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.system.Role;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetApproverManageService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.system.menu.MenuService;
import com.mbfw.service.system.role.RoleService;
import com.mbfw.service.system.user.UserService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.FileDownload;
import com.mbfw.util.FileUpload;
import com.mbfw.util.GetPinyin;
import com.mbfw.util.Jurisdiction;
import com.mbfw.util.ObjectExcelRead;
import com.mbfw.util.PageData;
import com.mbfw.util.ObjectExcelView;
import com.mbfw.util.PathUtil;
import com.mbfw.util.Tools;

import net.sf.json.JSONArray;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

/**
 * scw
 * 类名称：UserController 
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	String menuUrl = "user/listUsers.do"; // 菜单地址(权限用)
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "roleService")
	private RoleService roleService;
	@Resource(name = "menuService")
	private MenuService menuService;
	@Autowired
	private AssetApproverManageService amsService; //处理审核人员的service
	@Autowired
	private ProjectApplyService projectApplyService;//二级联动
	
	
	/**
	 * 保存用户
	 */
	@RequestMapping(value = "/saveOneUser")
	public ModelAndView saveU( ) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID()); // ID
		pd.put("RIGHTS", ""); // 权限
		pd.put("LAST_LOGIN", ""); // 最后登录时间
		pd.put("IP", ""); // IP
		pd.put("STATUS", "0"); // 状态
		pd.put("SKIN", "default"); // 默认皮肤
		pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		if (null == userService.findByUId(pd)) {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
				//添加创建用户的时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String creatUserTime = sdf.format(date);
				pd.put("creatuser_Time", creatUserTime);
				userService.saveU(pd);
			} // 判断新增权限
			mv.addObject("msg", "success");
			mv.addObject("result" , "添加用户成功");
		} else {
			mv.addObject("msg", "failed");			
		}
		mv.setViewName("system/user/addApproverResult");
		return mv;
	}

	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value = "/hasU")
	@ResponseBody
	public Object hasU() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUId(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断邮箱是否存在
	 */
	@RequestMapping(value = "/hasE")
	@ResponseBody
	public Object hasE() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			if (userService.findByUE(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value = "/hasN")
	@ResponseBody
	public Object hasN() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (userService.findByUN(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo); // 返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 修改用户
	 */
	@RequestMapping(value = "/editU")
	public ModelAndView editU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
			pd.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());
		}
		if (Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			userService.editU(pd);
		}
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value = "/goEditU")
	public ModelAndView goEditU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 顶部修改个人资料
		String fx = pd.getString("fx");

		// System.out.println(fx);

		if ("head".equals(fx)) {
			mv.addObject("fx", "head");
		} else {
			mv.addObject("fx", "user");
		}
		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
		pd = userService.findByUiId(pd); // 根据ID读取
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		JSONArray js = JSONArray.fromObject(info);
		mv.addObject("institutionInfo", js); //用于部门选择的二级联动
		
		//查询用户部门的权限信息
		List<PageData> userDepartmentAuthoritys = userService.findAllDepartmentAuthority();
		
		//获取所有的部门信息institutional_information(为了在权限选择的时候有限制)
		List<String> superInstitutional = userService.findAllInstitutionalInformation("总行");//获取总行的名称
		List<String> branchInstitutional = userService.findAllInstitutionalInformation("支行");//获取支行的名称
		mv.addObject("superinstitutional",superInstitutional);
		mv.addObject("branchinstitutional",branchInstitutional);
		
		
		
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("userDepartmentAuthoritys" , userDepartmentAuthoritys); //返回用户部门的权限信息

		return mv;
	}

	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU() throws Exception {
		ModelAndView mv = this.getModelAndView();		
		PageData pd = this.getPageData();
		List<Role> roleList;

		roleList = roleService.listAllERRoles(); // 列出所有二级角色
        
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		JSONArray js = JSONArray.fromObject(info);
		mv.addObject("institutionInfo", js); //用于部门选择的二级联动
		
		//查询用户部门权限的信息
		List<PageData> userDepartmentAuthoritys = userService.findAllDepartmentAuthority();
		
		//获取所有的部门信息institutional_information(为了在权限选择的时候有限制)
		List<String> superInstitutional = userService.findAllInstitutionalInformation("总行");//获取总行的名称
		List<String> branchInstitutional = userService.findAllInstitutionalInformation("支行");//获取支行的名称
		mv.addObject("superinstitutional",superInstitutional);
		mv.addObject("branchinstitutional",branchInstitutional);
		
		/* 不用用json转了
		 * JSONArray arr = JSONArray.fromObject(superInstitutional);
		String jsonInstitutionalInf = arr.toString();*/
		
		
		
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveOneUser");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("userDepartmentAuthoritys" , userDepartmentAuthoritys); //返回用户部门的权限信息
		return mv;
	}

	/**
	 * 显示用户列表(用户组)
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String USERNAME = pd.getString("USERNAME");

		if (null != USERNAME && !"".equals(USERNAME)) {
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}

		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");

		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			lastLoginStart = lastLoginStart + " 00:00:00";
			pd.put("lastLoginStart", lastLoginStart);
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			lastLoginEnd = lastLoginEnd + " 00:00:00";
			pd.put("lastLoginEnd", lastLoginEnd);
		}
		page.setPd(pd);
				
		List<PageData> userList = userService.listPdPageUser(page); // 列出用户列表
		List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色
		
		//查询所有的审批人员的名单信息
		List<String> allApproverUser = amsService.findAllApproverInfoToUser();
		//判断对应的用户是否在审核人员中
		for(int i=0 ; i < userList.size() ; i++){
			if(allApproverUser.contains(userList.get(i).getString("USER_ID"))){//表示该用户已经是审核人员
				userList.get(i).put("approverinfo", "YES"); //加一个字段标识该用户是审核人员了，这样的话在界面就不能重复添加为审核人员				
			}
			else{
				userList.get(i).put("approverinfo", "NO");
			}
		}		
		
		//查询用户部门的权限信息
		List<PageData> userDepartmentAuthoritys = userService.findAllDepartmentAuthority();
		//返回部门权限信息，主要是为了在table中显示对应的部门权限文字，因为user数据库中只保存了编码字段
		mv.addObject("userDepartmentAuthoritys",userDepartmentAuthoritys);
		
		//获取当前登陆用户的UserID，主要是为了当权限不是系统管理员的时候，只让用户管理显示自己的信息就可以了
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String currentUserId = user.getUSER_ID(); //获取当前用户的用户ID
		mv.addObject("currentUserInfo" ,currentUserId );
					
		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);  //其中有功能用于判断是否已经是审核人
		mv.addObject("roleList", roleList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 显示用户列表(tab方式)
	 */
	@RequestMapping(value = "/listtabUsers")
	public ModelAndView listtabUsers(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> userList = userService.listAllUser(pd); // 列出用户列表
		mv.setViewName("system/user/user_tb_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
		return mv;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value = "/deleteU")
	public void deleteU(PrintWriter out) {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
				userService.deleteU(pd);
			}
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/deleteAllU")
	@ResponseBody
	public Object deleteAllU() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String USER_IDS = pd.getString("USER_IDS");

			if (null != USER_IDS && !"".equals(USER_IDS)) {
				String ArrayUSER_IDS[] = USER_IDS.split(",");
				if (Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
					userService.deleteAllU(ArrayUSER_IDS);
				}
				pd.put("msg", "ok");
			} else {
				pd.put("msg", "no");
			}

			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	// ===================================================================================================

	/*
	 * 导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
				// 检索条件===
				String checkContent = pd.getString("checkContent");
				if (null != checkContent && !"".equals(checkContent)) {//存在检索内容
					checkContent = checkContent.trim();   //去掉末尾空字符
					pd.put("checkContent", checkContent);
				}
				String creatuser_Time = pd.getString("creatuser_Time");//获取检索用户创建时间
				String creatuser_endTime = pd.getString("creatuser_endTime"); //获取检索用户截止时间
				if (creatuser_Time != null && !"".equals(creatuser_Time)) {
					creatuser_Time = creatuser_Time + " 00:00:00";
					pd.put("creatuser_Time", creatuser_Time);
				}
				if (creatuser_endTime != null && !"".equals(creatuser_endTime)) {
					creatuser_endTime = creatuser_endTime + " 00:00:00";
					pd.put("creatuser_endTime", creatuser_endTime);
				}
				String roleContent = pd.getString("ROLE_ID"); //检索的系统角色
				if (roleContent != null && !"".equals(roleContent)) {
					pd.put("roleContent", roleContent);
				}
				String department = pd.getString("department"); //检索的用户权限内容
				if (department != null && !"".equals(department)) {
					pd.put("department", department);
				}
				// 检索条件===

				Map<String, Object> dataMap = new HashMap<String, Object>();
				List<String> titles = new ArrayList<String>();
				
				//设置Excel标题显示格式
				titles.add("编号"); // 1
				titles.add("用户名"); // 2
				titles.add("姓名"); // 3
				titles.add("系统角色"); // 4				
				titles.add("邮箱"); // 5
				titles.add("电话号码"); //6
				titles.add("上一级部门"); // 7
				titles.add("所属部门"); // 8
				titles.add("部门权限"); // 9
				titles.add("用户创建时间"); // 10
				titles.add("备注"); // 11

				dataMap.put("titles", titles);

				List<PageData> userList = userService.listAllUser(pd);//获取符合条件数据信息
				List<PageData> varList = new ArrayList<PageData>();
				for (int i = 0; i < userList.size(); i++) {  //添加相对应的获取到的数据到每一列中存储(这里就是10列数据)
					PageData vpd = new PageData();
					vpd.put("var1", userList.get(i).getString("NUMBER")); // 1
					vpd.put("var2", userList.get(i).getString("USERNAME")); // 2
					vpd.put("var3", userList.get(i).getString("NAME")); // 3
					vpd.put("var4", userList.get(i).getString("ROLE_NAME")); // 4
					vpd.put("var5", userList.get(i).getString("EMAIL")); // 5
					vpd.put("var6", userList.get(i).getString("PHONE")); // 6
					vpd.put("var7", userList.get(i).getString("superior_organization_name")); // 7
					vpd.put("var8", userList.get(i).getString("organization_name")); // 8
					//对部门权限的显示这里要处理一下
					if((Integer)(userList.get(i).get("user_Permission")) == 1){
						vpd.put("var9", "总行管理员"); // 9	
					}else if((Integer)(userList.get(i).get("user_Permission")) == 2){
						vpd.put("var9", "支行管理员"); // 9
					}else if((Integer)(userList.get(i).get("user_Permission")) == 3){
						vpd.put("var9", "普通员工"); // 9
					}								
					vpd.put("var10", userList.get(i).getString("creatuser_Time")); // 10
					vpd.put("var11", userList.get(i).getString("BZ")); // 11
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
				mv = new ModelAndView(erv, dataMap);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/user/uploadexcel");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Users.xls", "Users.xls");

	}

	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel"); // 执行上传

			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0); // 执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			
			//判断Excl是否符合规范传过来了内容
			if(listPd == null){
				mv.addObject("msg", "error");
				mv.setViewName("system/user/uploadexcelresult");
				return mv;
			}
			
			//符合规范则进行后面的操作
			
			/* 存入数据库操作====================================== */
			pd.put("RIGHTS", ""); // 权限
			pd.put("LAST_LOGIN", ""); // 最后登录时间
			pd.put("IP", ""); // IP
			pd.put("STATUS", "0"); // 状态
			pd.put("SKIN", "default"); // 默认皮肤

			List<Role> roleList = roleService.listAllERRoles(); // 列出所有二级角色

			pd.put("ROLE_ID", roleList.get(0).getROLE_ID()); // 设置角色ID为随便第一个
			/**
			 * var0 :编号(柜台号) var1 :姓名 var2 :所属部门 var3 :上一级部门 var4 :用户名(工号)......等等
			 * 这个var就是readExcel方法返回对象中在方法中进行封装好的信息，也就是对应的信息
			 */
			for (int i = 0; i < listPd.size(); i++) {
				//判断是否有工号为空，如果为空，后面的就不进行添加，直接提示用户
				if(listPd.get(i).getString("var4") == null || listPd.get(i).getString("var4") == ""){					
					mv.addObject("msg", "error");
					mv.setViewName("system/user/uploadexcelresult");
					return mv;
				}
				//pd.put("USER_ID", this.get32UUID()); // 产生唯一的ID，默认就用工号了，所以这里不用这个方法产生了
				pd.put("USER_ID", listPd.get(i).getString("var4"));//第五列，用户名（工号）作为user_id
				pd.put("NAME", listPd.get(i).getString("var1")); // 第二列，姓名
				pd.put("USERNAME", listPd.get(i).getString("var4"));//第五列，用户名，也就用工号代替
				
				if (userService.findByUId(pd) != null) { // 判断用户名是否重复,如果重复了就不添加这个用户
					continue;
				}
				
				/* 用户名也直接用工号了，就不用姓名的拼音了
				 *
				String USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1")); // 根据姓名汉字生成全拼，为了密码加密				
				pd.put("USERNAME", USERNAME);
				if (userService.findByUId(pd) != null) { // 判断用户名是否重复,如果重复了就再后面添加随机生成的六位数
					USERNAME = GetPinyin.getPingYin(listPd.get(i).getString("var1")) + Tools.getRandomNum();
					pd.put("USERNAME", USERNAME);
				}*/
				pd.put("organization_name", listPd.get(i).getString("var2")); //第三列，所属部门
				pd.put("superior_organization_name", listPd.get(i).getString("var3")); //第四列，上级部门
				//如果上一级部门是总行，就分配权限2，否则分配3
				if("慈利农商银行".equals(listPd.get(i).getString("var3")) ){
					pd.put("user_Permission", 2);
				}
				else{
					pd.put("user_Permission", 3);
				}
				
				/*if (Tools.checkEmail(listPd.get(i).getString("var3"))) { // 邮箱格式不对就跳过(Excel没有邮箱就这个不用了)
					pd.put("EMAIL", listPd.get(i).getString("var3"));
					if (userService.findByUE(pd) != null) { // 邮箱已存在就跳过
						continue;
					}
				} else {
					continue;
				}*/

				pd.put("NUMBER", listPd.get(i).getString("var0")); // 编号已存在就跳过（柜台号）
				//pd.put("PHONE", listPd.get(i).getString("var2")); // 手机号(默认添加的时候没有)

				pd.put("PASSWORD", new SimpleHash("SHA-1", listPd.get(i).getString("var4"), "123").toString()); // 设置初始默认密码为123，并且将这个密码加密到数据库中（用工号和SHA-1来加密）
				if (userService.findByUN(pd) != null) {
					continue;
				}
				
				//添加创建用户的时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String creatUserTime = sdf.format(date);
				pd.put("creatuser_Time", creatUserTime);
				
				userService.saveU(pd);//保存数据到数据库中
			}
			/* 存入数据库操作====================================== */
			mv.addObject("msg", "success");
		}
		mv.setViewName("system/user/uploadexcelresult");
		return mv;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	
	/*
	 * 去添加审核人员页面
	 * scw
	 */
	@RequestMapping(value = "/addapprover")
	public ModelAndView addApprover() throws Exception{
		ModelAndView andView = new ModelAndView();
		//查询相关该ID用户的信息操作
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userService.findByUiId(pd); // 根据ID读取，返回数据
		andView.addObject("pd", pd);
		andView.setViewName("system/user/user_toapprover");
		return andView;
	}
	
	/*
	 * 处理添加审核人员的处理
	 * scw
	 */
	@RequestMapping(value = "/dealAddApprover")
	public ModelAndView dealAddApprover() throws Exception{
		ModelAndView andView = this.getModelAndView();
		//进行添加操作
		PageData pd = new PageData();
		pd = this.getPageData();
		String user_Id = pd.getString("user_Id"); //获取user_id
		//判断该ID是否已经在数据库中了，如果没有则进行添加，否则提示不能重复添加
		PageData findUser = userService.findByUIdApprover(user_Id);
		
		if(findUser == null){  //如果是空，则表示不存在的，则能进行添加
			userService.saveApproverUser(pd);  //这里先都添加先	
			//跳转到添加成功的页面
			andView.addObject("msg", "success");
			andView.addObject("result" , "添加审批人员成功");
			andView.setViewName("system/user/addApproverResult");
			return andView;
		}	
		else{
			//跳转到原来的失败页面
			pd = userService.findByUIdOne(user_Id);
			andView.addObject("pd", pd);
			andView.addObject("editresult", "resultfail");
			andView.setViewName("system/user/user_toapprover");
			return andView;
		}
		
	}
}
