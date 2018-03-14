package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.mbfw.entity.assets.AssetIssued;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssertIssuedService;
import com.mbfw.service.assets.AssertReceivedService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.Const;
import com.mbfw.util.FileDownload;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssertIssuedController extends BaseController{
	@Autowired
	private AssertIssuedService assertIssuedService;
	@Autowired
	private AssertReceivedService assertReceivedService;
	@Autowired
	private ProjectApplyService projectApplyService; // 部门
	// 开始页面下发单查询
	@RequestMapping(value = "/arda_assertIssued")
	public ModelAndView issued() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer totalnumber = 0;
		List<PageData> asset_issued_bill = new ArrayList<PageData>();
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
			totalnumber= assertIssuedService.find_asset_issued_bill_totalnumber(pd);

			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			page.setPd(pd);
			asset_issued_bill=assertIssuedService.find_asset_issued_bill(page);
		}else{
			page.setPd(pd);
			totalnumber =  assertIssuedService.find_asset_issued_bill_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			asset_issued_bill=assertIssuedService.find_asset_issued_bill_bykey(page);
		}

		String issued = pd.getString("issued");// 判断下发是否成功 
		if(issued != null && !issued.equals("")){
			mv.addObject("issued", issued);
		}
		String updateIssued = pd.getString("updateIssued");// 判断下发是否成功  
		if(updateIssued != null && !updateIssued.equals("")){
			mv.addObject("updateIssued", updateIssued);
		}
		
		String delResult = pd.getString("delResult");// 判断删除是否成功  
		if(delResult != null && !delResult.equals("")){
			mv.addObject("delResult", delResult);
		}
		
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限

		mv.addObject("asset_issued_bill", asset_issued_bill);
		mv.addObject("page", page);
		mv.setViewName("system/asset_down/assertIssuedBill");
		return mv;
	}

	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	//资产下发页面
	@RequestMapping(value = "/arda_add_assertIssued")
	public ModelAndView arda_addassertIssued() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer totalnumber = 0;
		List<PageData> asset_for_issued = new ArrayList<PageData>();
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

		String aa=pd.getString("retrieve_content");
		System.out.println(aa);
		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			totalnumber= assertIssuedService.find_asset_for_issued_totalnumber(pd);

			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			page.setPd(pd);
			asset_for_issued=assertIssuedService.find_asset_for_issued(page);
		}else{
			page.setPd(pd);
			totalnumber =  assertIssuedService.find_asset_for_issued_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			asset_for_issued=assertIssuedService.find_asset_for_issued_bykey(page);
		}

		mv.addObject("asset_for_issued", asset_for_issued);
		mv.addObject("page", page);
		mv.setViewName("system/asset_down/assertIssued");
		return mv;
	}

	// 新增下发单
	@RequestMapping(value = "/arda_add_issue_bill_index")
	public ModelAndView arda_add_issue_bill_index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

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

		mv.addObject("permission", permission);

		mv.addObject("msg", "save_add_issue_bill");
		mv.addObject("id",pd.getString("id"));
		mv.addObject("asset_code", pd.getString("asset_code"));
		mv.setViewName("system/asset_down/issuedTable");
		return mv;
	} 

	//保存新增资产下发
	@RequestMapping(value="/save_add_issue_bill")
	public String save_add_issue_bill(@RequestParam(value = "file_receive", required = false) MultipartFile file[],
			AssetIssued asset_issued) throws Exception{
		//保存文件
		String filePath="";
		String filePathConst = PathUtil.getPath("0")+File.separator;//.getClasspath() + Const.FILEPATHFILE ;
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		asset_issued.setIssue_time(dateString);
		assertIssuedService.save_asset_issued_bill(asset_issued); // 保存下发单
		asset_issued.setReceive_time(dateString);
		assertReceivedService.save_issued_receive(asset_issued); // 保存接收单
		// 修改资产状态
		Integer id = asset_issued.getId();
		assertIssuedService.update_asset_status(id);
		return "redirect:/asset/arda_assertIssued?issued=success";
	}

	// 编辑下发单
	@RequestMapping(value = "/arda_edit_issued_bill")
	public ModelAndView arda_edit_issued_bill() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

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
			String apply_dept = user.getOrganization_name();// 所在公司名称 receieve
			//如果权限为3申请人为用户的名字 上级名称 所在部门
			mv.addObject("apply_person", apply_person);
			mv.addObject("apply_company", apply_company);
			mv.addObject("apply_dept", apply_dept);
		}

		mv.addObject("permission", permission);

		Integer id = Integer.parseInt(pd.getString("id"));
		PageData issued_bill = assertIssuedService.find_issued_bill_by_id(id);
		
		//显示文件
		String file_address =  issued_bill.getString("receive_proof");// 获得文件上传
		String file_name = "";
		if(file_address==null || file_address.equals("")){
			file_name = "no_file";
		}else{
			file_name = file_address;
		}
		mv.addObject("file_address", file_address);
		mv.addObject("file_name", file_name);
		
		
		String issuing =pd.getString("issuing");
		if(issuing !=null && !issuing.equals("")){
			mv.addObject("issue", "yes");
		}
		String receiving =pd.getString("receiving");
		if(receiving !=null && !receiving.equals("")){
			mv.addObject("receive", "yes");
		}
		mv.addObject("issued_bill", issued_bill);
		mv.addObject("asset_code", issued_bill.getString("asset_code"));
		mv.addObject("msg", "update_issued_bill");
		mv.setViewName("system/asset_down/issuedEditTable");
		return mv;
	}
	
	/**
	 * 
	 * @param file
	 * @param asset_issued 
	 * @return保存编辑下发单
	 * @throws Exception
	 */

	// 保存编辑下发单
	@RequestMapping(value = "/update_issued_bill")
	public String update_issued_bill(@RequestParam(value = "file_receive", required = false) MultipartFile file[],
			AssetIssued asset_issued) throws Exception {
		String receive =asset_issued.getReceive();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		asset_issued.setReceive_time(dateString);
		Integer id = asset_issued.getIdd();
		asset_issued.setId(id);//.put("id", id);
		if(receive.equals("yes")){ // 当下发单处于接收状态，有文件
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
			assertReceivedService.update_issue_bill_receive(asset_issued); // 修改接收单
		}else{
			assertIssuedService.update_asset_issue_bill_status(asset_issued); // 修改下发单状态为下发中的下发单 
		}
		return "redirect:/asset/arda_assertIssued?updateIssued=success";
	}

	//删除下发单 arda_deleteAssetIssueBill
	@RequestMapping(value = "/arda_deleteAssetIssueBill")
	public void arda_deleteAssetIssueBill(PrintWriter out)  {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String down_issuing= pd.getString("down_issuing");
			String issued_status =URLDecoder.decode(pd.getString("issued_status"),"utf-8");
			pd.put("issued_status", issued_status);
			if(down_issuing=="yes" && down_issuing!=null){ // 下发中的下发单，删除时恢复资产表的状态为闲置
				assertIssuedService.update_issue_asset_status(pd);
			}else{//接收中的状态，充值下发表的接收内容
				assertIssuedService.update_issue_status(pd);
				assertIssuedService.update_receive_asset_status(pd);
			}
			 assertIssuedService.delete_asset_issue_bill(pd); //把下发单删除标记变为0
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 批量删除下发单
	 * 
	 */

	@RequestMapping(value = "/delete_all_asset_issue_bill")
	@ResponseBody
	public PageData delete_all_purchase_bill() {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String asset_issue_bill = pd.getString("asset_issue_bill");
			StringBuffer str_id =new StringBuffer();
			if (null != asset_issue_bill && !"".equals(asset_issue_bill)) {
				String array_del_asset_issue_bill[] = asset_issue_bill.split(",");
				for (String string : array_del_asset_issue_bill) {
					String []str1 = string.split("@");
					str_id.append(str1[0]+",");
					pd.put("asset_code", str1[1]);
					if(str1[2].equals("下发中")){
						assertIssuedService.update_issue_asset_status(pd);
					}else{
						assertIssuedService.update_issue_status(pd);
						assertIssuedService.update_receive_asset_status(pd);
					}
				}
				String []str2 = str_id.toString().split(",");// 把下发单删除标记变为0
				assertIssuedService.delete_all_asset_issue_bill(str2);
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
	/**
	 * 下载下发证明
	 */
	@RequestMapping(value = "/arda_downReceiveProof")
	public void arda_downReceiveProof(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		String proof = URLDecoder.decode(pd.getString("proof"),"utf-8");
		String file_name = proof.split("@")[1];
		FileDownload.fileDownload(response, PathUtil.getPath("0")+ File.separator + proof, file_name);
	}
	
}
