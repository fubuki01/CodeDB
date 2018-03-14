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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetCheckService;
import com.mbfw.service.assets.AssetTableManageService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/asset")
public class AssetManageCheckController extends BaseController {
	@Autowired
	private ProjectApplyService projectApplyService;
	@Resource(name = "assetCheckService")
	private AssetCheckService assetCheckService;
	@Resource(name = "assetTableManageService")
	private AssetTableManageService assetTableManageService;

	/**
	 * 显示盘点列表
	 */
	@RequestMapping(value = "/check_list")
	public ModelAndView checklsit() throws Exception {
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
		
		List<PageData> listCheck = new ArrayList<PageData>();
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
			Integer totalnumber = assetCheckService.findTotalOrigCheckCount(page);			
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			// 
			listCheck = assetCheckService.listOrigCheckPageShow(page);
			for(int i = 0 ; i <listCheck.size() ; i++){
				//PageData currentPd = ;
				Integer total_number = assetCheckService.findCheckDataNumber((String) listCheck.get(i).get("check_name"));
				listCheck.get(i).put("totalNumber", total_number);
				// 查询单表已盘点的数量
				Integer checked_number = assetCheckService.findCheckedDataNumber((String) listCheck.get(i).get("check_name"));
				listCheck.get(i).put("checkNumber", checked_number);
			}
	
		}
		// --------进行检索处理
		else {
			// (3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			// (4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			// (5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetCheckService.findOrigNumberCheckBySearchName(page);
			// (6)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (8)查询数据库，返回对应检索姓名的数据
			listCheck = assetCheckService.listChecktOrigSearchNameShow(page);
		}
		mv.addObject("count",this.getPageData().getString("count"));
		mv.addObject("page", page); // 返回对应的分页的内容
		mv.addObject("permission",permission);
		mv.addObject("listCheck", listCheck); // 返回对应条数的数据
		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.setViewName("/system/asset_checkout/check_list");
		return mv;
	}
	
	
	
	/**
	 * 显示单个盘点名称中数据
	 */
	@RequestMapping(value = "/asset_check")
	public ModelAndView manager() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
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
			pd.put("check_name", pd.getString("pm"));
			page.setPd(pd);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = assetCheckService.findTotalCheckDataNumber(pd);			
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			if(pd.getString("check_name") != null)
			{
				listApprover = assetCheckService.listPdCheckPageApprover(page);	
				mv.addObject("searchname", listApprover.get(0).getString("check_name"));	
				
			}			
		}
		// --------进行检索处理
		else {
			pd.getString("check_name");
			// (3)获取传送过来的进行检索的审核人员的姓名
			String searchName = pd.getString("retrieve_content");
			// (4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			// (5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetCheckService.findNumberCheckBySearchName(page);
			// (6)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (8)查询数据库，返回对应检索姓名的数据
			listApprover = assetCheckService.listChecktSearchNameApprover(page);
		}

		//获取增删改查的权限
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送修改项目的结果
		mv.addObject("count",this.getPageData().getString("count"));
		mv.addObject("page", page); // 返回对应的分页的内容
//		mv.addObject("permission",permission);
		mv.addObject("listApprover", listApprover); // 返回对应条数的数据
		mv.setViewName("/system/asset_checkout/asset_check");
		return mv;
	}

	/**
	 * 盘点添加表
	 */
	@RequestMapping(value = "/goAddCheck")
	public ModelAndView goAddCheck() throws Exception {
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
		String username = user.getNAME();
		pd.put("permission", permission);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("organization_name",organization_name);
		pd.put("username", username);
		if(user.getUser_Permission() == 1){
			info = projectApplyService.institutionInfo();		
		}else if(user.getUser_Permission() == 2){
			info =  "[{\""+user.getSuperior_organization_name()+"\":[\""+user.getOrganization_name()+"\"]}]";
		}
		JSONArray js = JSONArray.fromObject(info);
		
		List<String> asset_name = new ArrayList<String>();
		List<String> asset_class = new ArrayList<String>();
		List<String> asset_standard_model = new ArrayList<String>();
		List<String> asset_status = new ArrayList<String>();
		List<PageData> assetCodeFind = new ArrayList<PageData>();;		
		assetCodeFind=assetTableManageService.find_AllAssetCode(pd);	
		//通过查询资产编码，来得到资产表中的所有需要选择的信息	
		for (PageData pageData : assetCodeFind) {
			String an = pageData.getString("asset_name");
			String ac = pageData.getString("asset_class");
			String asm = pageData.getString("asset_standard_model");
			String as = pageData.getString("asset_status");
			if(!asset_name.contains(an)){
				asset_name.add(an);
			}
			if(!asset_class.contains(ac)){
				asset_class.add(ac);
			}
			if(!asset_standard_model.contains(asm)){
				asset_standard_model.add(asm);
			}
			if(!asset_status.contains(as)){
				asset_status.add(as);
			}
		}				
		mv.addObject("asset_name", asset_name);
		mv.addObject("asset_class", asset_class);
		mv.addObject("asset_standard_model", asset_standard_model);
		mv.addObject("asset_status", asset_status);
		mv.addObject("assetCodeFind", assetCodeFind);
		mv.setViewName("system/asset_checkout/checktable");
		mv.addObject("institutionInfo", js);
		mv.addObject("pd", pd);

		return mv;
	}
//	/**
//	 * 资产封装成json，用ajax请求
//	 */
//	@RequestMapping(value = "/find_checkinfo")		
//	public @ResponseBody PageData find_checkinfo(Page page) throws Exception {
//		PageData pd = this.getPageData();
//		//获取入库单的唯一id号
//		String id = pd.getString("id");
//		PageData checkinfo = assetTableManageService.findInfoBy_CheckInfo(id);
//		//将Date类型转化为String
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
//		String str = sdf.format(checkinfo.get("get_time"));
//		checkinfo.put("get_time", str);
//		return checkinfo;
//	}
	
	/**
	 * ajax来判断是否出现重名
	 */
	@RequestMapping(value = "/find_checkname")		
	public @ResponseBody Object find_checkname(Page page) throws Exception {
		HashMap map = new HashMap();
		PageData pd = this.getPageData();
		//获取唯一id号
		String name = pd.getString("check_name");
		//查询总盘点表中已存在的盘点名称
		PageData checkname = assetTableManageService.find_CheckName(name);
		if(checkname != null)
			map.put("result", "error");
		else
			map.put("result", "ok");
		return map;
	}
	
	
	
	/**
	 * 处理添加资产报废表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/save_Check")
	public String save_Check(@RequestParam("asset_use_dept") String asset_use_dept[],
			@RequestParam("asset_standard_model") String asset_standard_model[],
			@RequestParam("asset_name") String asset_name[],
			@RequestParam("asset_status") String asset_status[],
			@RequestParam("asset_class") String asset_class[],
			@RequestParam("asset_use_company") String asset_use_company,
			@RequestParam("start_purchase_time") String start_purchase_time,
			@RequestParam("end_purchase_time") String end_purchase_time,
			@RequestParam("check_name") String check_name,
			@RequestParam("build_name") String build_name,
			@RequestParam("build_time") String build_time) throws Exception{
		PageData pd = new PageData();
		
		//String asset_name = pd.getString("asset_name");
		//String asset_class = pd.getString("asset_class");
		//String asset_use_company = pd.getString("asset_use_company");
		//String asset_use_dept = pd.getString("asset_use_dept");
		//String asset_standard_model = pd.getString("asset_standard_model");
		//String start_purchase_time = pd.getString("start_purchase_time");//获取开始时间
		//String end_purchase_time = pd.getString("end_purchase_time"); //获取截止时间
		//String asset_status = pd.getString("asset_status");
		
		if (asset_name != null && asset_name.length > 0){
			pd.put("asset_name", asset_name); 
		}
		if (asset_class != null && asset_class.length > 0){
			pd.put("asset_class", asset_class);
		}
		if (asset_use_company != null && !("").equals(asset_use_company)){
			pd.put("asset_use_company",asset_use_company);
		}
		if (asset_use_dept != null && asset_use_dept.length > 0){
			pd.put("asset_use_dept", asset_use_dept);
		}
		if (asset_standard_model != null && asset_standard_model.length > 0){
			pd.put("asset_standard_model", asset_standard_model);
		}
		if (asset_status != null && asset_status.length > 0){
			pd.put("asset_status", asset_status);
		}
		if (start_purchase_time != null && !"".equals(start_purchase_time)) {
			start_purchase_time = start_purchase_time + " 00:00:00";
			pd.put("start_purchase_time", start_purchase_time);
		}
		if (end_purchase_time != null && !"".equals(end_purchase_time)) {
			end_purchase_time = end_purchase_time + " 00:00:00";
			pd.put("end_purchase_time", end_purchase_time);
		}
		pd.put("check_name",check_name);
		pd.put("build_name",build_name);
		pd.put("build_time",build_time);
		//多条件多选择查询
		List<PageData> listApprover = assetCheckService.findMultipleSelectResult(pd);
		if(listApprover.isEmpty()){
			return "redirect:/asset/check_list?saveResult=false";
		}else{
			//添加数据到盘点表单中
			assetCheckService.saveCheckList(pd);
			//添加数据到数据库中
			for (PageData pageData : listApprover) {
				pageData.put("check_name", check_name);
				assetCheckService.saveCheckData(pageData);
			}
			return "redirect:/asset/check_list?saveResult=success";			
		}
		
	}

	
	/**
	 * 盘点表删除
	 */
	@RequestMapping(value = "/delete_CheckList")
	@ResponseBody
	public Object deleteCheckListData() {
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
			}
			
			assetCheckService.deletecheckthislistdata(newIds);
			assetCheckService.deletechecklistdata(newIds);			
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
	 * 单表批量删除
	 */
	@RequestMapping(value = "/delete_Check")
	@ResponseBody
	public Object deleteCheckData() {
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
			}
			assetCheckService.deletecheckdata(newIds);
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
}
