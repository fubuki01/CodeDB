package com.mbfw.controller.assets;


import java.io.File;
import java.io.FileOutputStream;
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

//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetIssued;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssertIssuedService;
import com.mbfw.service.assets.AssertReceivedService;
import com.mbfw.service.assets.AssetGetService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

import net.sf.json.JSONArray;


@Controller
@RequestMapping(value = "/asset")
public class AssertReceivedController extends BaseController{
	@Autowired
	private AssertIssuedService assertIssuedService;

	@Autowired
	private AssertReceivedService assertReceivedService;
	@Autowired
	private ProjectApplyService projectApplyService; // 部门
	/**
	 * 
	 * @param page 可以接受的下发单 /mbfw/WebRoot/WEB-INF/jsp/system/asset_accept/assertReceived.jsp
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/arda_assertReceived")
	public ModelAndView arda_assertReceived() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer totalnumber = 0;
		List<PageData> asset_for_received = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();

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

		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}

		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			totalnumber= assertReceivedService.find_asset_for_received_totalnumber(pd);

			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			page.setPd(pd);
			asset_for_received=assertReceivedService.find_asset_for_received(page);
		}else{
			page.setPd(pd);
			totalnumber =  assertReceivedService.find_asset_for_received_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			asset_for_received=assertReceivedService.find_asset_for_received_bykey(page);
		}

		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("asset_for_received", asset_for_received);
		mv.addObject("page", page);
		mv.setViewName("system/asset_accept/assertReceived");
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */


	// 新增接收单
	@RequestMapping(value = "/arda_add_received_bill_index")
	public ModelAndView arda_add_received_bill_index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询公司和对应的部门生成json 供二级联动适应
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String register_person = user.getNAME();
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
		mv.addObject("permission", permission);
		mv.addObject("msg", "save_add_received_bill");
		mv.addObject("asset_code", pd.getString("asset_code"));
		mv.setViewName("system/asset_accept/receivedTable");
		return mv;
	} 

	//保存新增资产下发
	@RequestMapping(value="/save_add_received_bill")
	public ModelAndView save_add_received_bill(@RequestParam(value = "file_receive", required = false) MultipartFile file[],
			AssetIssued asset_issued) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//保存文件
		String filePath="";
		String filePathConst = PathUtil.getPath("0")+File.separator;
		StringBuffer receive_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.getOriginalFilename().equals("")) {
				String time = System.currentTimeMillis()+"@"+multipartFile.getOriginalFilename();
				filePath = filePathConst+time; // 文件上传路径
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(filePath));
				receive_file.append(time+"#");
			}
		}
		if(!receive_file.equals("")){
			asset_issued.setReceive_proof(receive_file.toString());
		}
		//结束
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		asset_issued.setReceive_time(dateString);
		assertReceivedService.update_issue_bill_status(asset_issued); // 修改下发单

		// 修改资产状态
		pd.put("asset_code", asset_issued.getAsset_code());
		pd.put("receive_branch", asset_issued.getReceive_branch());
		pd.put("receive_department", asset_issued.getReceive_department());
		assertReceivedService.update_asset_status(pd);

		mv.setViewName("save_result");
		return mv;
	}
}
