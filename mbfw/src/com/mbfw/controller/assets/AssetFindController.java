package com.mbfw.controller.assets;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetFindService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;
import com.mbfw.util.ZxingUtil;


/**
 * 类名称：AssetReqAndAllotController 创建人：揭尹  创建时间：2017年8月20日
 * 
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/asset")
public class AssetFindController extends BaseController {
	@Resource(name = "assetFindService")
	private AssetFindService assetFindService;
	@Autowired
	private ProjectApplyService projectApplyService;
	
	/**
	 * 显示库存查询的数据
	 * 
	 */
	@RequestMapping(value = "/afc_assetFind")
	public ModelAndView list() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String asset_code1 = pd.getString("asset_code");
		String asset_name1 = pd.getString("asset_name");
		String asset_class1 = pd.getString("asset_class");
		String bank_name1 = pd.getString("bank_name");
		String department1 = pd.getString("department");
		String asset_user1 = pd.getString("asset_user");
		//String asset_purchase_time1 = pd.getString("asset_purchase_time");
		String asset_into_bill1 = pd.getString("asset_into_bill");
		String asset_status1 = pd.getString("asset_status");
		String  creatuser_Time = pd.getString("creatuser_Time");
		String  creatuser_endTime = pd.getString("creatuser_endTime");
		

		List<PageData> allotList;
		
		
		//Date date = new Date();
		
		
		//JSONArray js;
		// ----------分页操作开始----------
		PageOption page = new PageOption(10, 1); // 默认初始化一进来显示就是每页显示10条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//----获取当前登陆用户的相关信息，从session中获取
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("sessionUser");
		pd.put("userPermission", user.getUser_Permission()); //当前用户的权限
		if(user.getUser_Permission() == 2){
			pd.put("asset_use_company",user.getOrganization_name());//当前用户所在公司
			//pd.put("asset_use_dept",user.getSuperior_organization_name());//当前用户所在部门
		}
		else{
			pd.put("asset_use_company",user.getSuperior_organization_name());//当前用户所在公司
			pd.put("asset_use_dept", user.getOrganization_name());//当前用户所在部门
		}
		
		
		pd.put("currentUser", user.getNAME());//当前用户姓名
		//-------------------
		if((asset_code1==null||asset_code1.equals(""))&&(asset_name1==null||asset_name1.equals(""))
				&&(asset_class1==null||asset_class1.equals(""))&&(bank_name1==null||bank_name1.equals(""))
				&&(department1==null||department1.equals(""))&&(asset_user1==null||asset_user1.equals(""))
				&&(asset_into_bill1==null||asset_into_bill1.equals(""))
				&&(asset_status1==null||asset_status1.equals(""))&&(creatuser_Time==null||creatuser_Time.equals(""))
				&&(creatuser_endTime==null||creatuser_endTime.equals(""))){
			System.out.println("hahaha");
			page.setPd(pd);
			// (3)查询数据库中数据的总条数
			Integer totalnumber = assetFindService.findTotalNumber(pd);		
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			//String info = assetFindService.findAssetInfo();
			
			//allotList = assetFindService.find_asset_find(page);
			allotList = assetFindService.findAllAssetInfo(page);
			//js = JSONArray.fromObject(info);
			
			// ----------分页结束------------------------------
		}else{
			//处理URL带过来数据的乱码问题
			if(asset_name1 != null && !("").equals(asset_name1)){
				pd.put("asset_name", URLDecoder.decode(asset_name1, "utf-8"));
			}
			else if(asset_code1 != null && !("").equals(asset_code1)){
				pd.put("asset_code", URLDecoder.decode(asset_code1, "utf-8"));
			}
			else if(asset_class1 != null && !("").equals(asset_class1)){
				pd.put("asset_class", URLDecoder.decode(asset_class1, "utf-8"));
			}
			else if(bank_name1 != null && !("").equals(bank_name1)){
				pd.put("bank_name", URLDecoder.decode(bank_name1, "utf-8"));
			}
			else if(department1 != null && !("").equals(department1)){
				pd.put("department", URLDecoder.decode(department1, "utf-8"));
			}
			else if(asset_user1 != null && !("").equals(asset_user1)){
				pd.put("asset_user", URLDecoder.decode(asset_user1, "utf-8"));
			}
//			else if(asset_purchase_time1 != null && !("").equals(asset_purchase_time1)){
//				pd.put("asset_purchase_time", URLDecoder.decode(asset_purchase_time1, "utf-8"));
//			}
			else if(asset_into_bill1 != null && !("").equals(asset_into_bill1)){
				pd.put("asset_into_bill", URLDecoder.decode(asset_into_bill1, "utf-8"));
			}
			else if(asset_status1 != null && !("").equals(asset_status1)){
				pd.put("asset_status", URLDecoder.decode(asset_status1, "utf-8"));
			}
			else if(creatuser_Time != null && !("").equals(creatuser_Time)){
				pd.put("creatuser_Time", URLDecoder.decode(creatuser_Time, "utf-8"));
			}
			else if(creatuser_endTime != null && !("").equals(creatuser_endTime)){
				pd.put("creatuser_endTime", URLDecoder.decode(creatuser_endTime, "utf-8"));
			}
			
			//这个要特别处理一下，因为如果不选公司，那么部门这个字段就是空，会导致查询出问题（特别注意）
			if(("").equals(bank_name1) || bank_name1 == null ){
				pd.remove("department");	
			}
					
			//System.out.println(id+"   "+useid);
			//(3)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(4)查询对应审核人员姓名的数据总条数
			Integer totalnumber = assetFindService.findGaoJiNumber(pd);
			
			//(5)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(6)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(7)查询数据库，返回对应检索姓名的数据

			//String info = assetFindService.findAssetInfo();
			allotList = assetFindService.findGaoJi(page);
			//js = JSONArray.fromObject(info);
		}
		
		//mv.addObject("AssetInfo", js);
		mv.addObject("allotList", allotList);
		mv.addObject("page", page);
		
		mv.setViewName("system/asset_find/asset_find");
		
		
		List<String> asset_name = new ArrayList<String>();
		List<String> asset_class = new ArrayList<String>();
		List<String> asset_user = new ArrayList<String>();
		//List<String> asset_purchase_time = new ArrayList<String>();
		List<String> asset_into_bill = new ArrayList<String>();
		List<String> asset_status = new ArrayList<String>();
		
		List<PageData> assetCodeFind = new ArrayList<PageData>();;		
		assetCodeFind=assetFindService.findBetterAssetInfo(page);
	
		
		for (PageData pageData : assetCodeFind) {
			String an = pageData.getString("asset_name");
			String ac = pageData.getString("asset_class");
			String au = pageData.getString("asset_user");
			//String apt = pageData.getString("asset_purchase_time");
			String aib = pageData.getString("asset_into_bill");
			String as = pageData.getString("asset_status");
			if(!asset_name.contains(an)){
				asset_name.add(an);
			}
			if(!asset_class.contains(ac)){
				asset_class.add(ac);
			}
			if(!asset_user.contains(au)){
				asset_user.add(au);
				
			}
			/*if(!asset_purchase_time.contains(apt)){
				asset_purchase_time.add(apt);
			}*/
			if(!asset_into_bill.contains(aib)){
				asset_into_bill.add(aib);
			}
			if(!asset_status.contains(as)){
				asset_status.add(as);
			}
		}
		
		mv.addObject("asset_name2", asset_name);
		mv.addObject("asset_class2", asset_class);
		mv.addObject("asset_user2", asset_user);
		//mv.addObject("asset_purchase_time2", asset_purchase_time);
		mv.addObject("asset_into_bill2", asset_into_bill);
		mv.addObject("asset_status2", asset_status);
		mv.addObject("assetCodeFind2", assetCodeFind);
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		
		JSONArray jsa = JSONArray.fromObject(info);
		
		
		mv.addObject("institutionInfo", jsa);
		
		//mv.addObject("msg", map);
		/*if(asset_name1 != null || asset_name1 != ""){
			
		}*/
		if(user.getUser_Permission() == 2){
			if((bank_name1 != null && department1 !=null)){
				pd.put("asset_use_company",department1);//当前用户所在公司
				pd.put("asset_use_dept",bank_name1);//当前用户所在部门
			}
			else{	
				pd.put("asset_use_company",user.getSuperior_organization_name());//当前用户所在公司
				pd.put("asset_use_dept",user.getOrganization_name());//当前用户所在部门
			}
		}
		mv.addObject("file_img",PathUtil.getPath("1"));
		mv.addObject("pd", pd);
		return mv;
	}
	
/*	*//**
	 * 去高级查询页面
	 *//*
	@RequestMapping(value = "/goFind")
	public ModelAndView goFind() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		PageOption page = new PageOption(10000, 1);
		
		List<String> asset_name = new ArrayList<String>();
		List<String> asset_class = new ArrayList<String>();
		List<String> asset_user = new ArrayList<String>();
		List<String> asset_purchase_time = new ArrayList<String>();
		List<String> asset_into_bill = new ArrayList<String>();
		List<String> asset_status = new ArrayList<String>();
		
		List<PageData> assetCodeFind = new ArrayList<PageData>();;		
		assetCodeFind=assetFindService.findBetterAssetInfo(page);
		
		for (PageData pageData : assetCodeFind) {
			String an = pageData.getString("asset_name");
			String ac = pageData.getString("asset_class");
			String au = pageData.getString("asset_user");
			String apt = pageData.getString("asset_purchase_time");
			String aib = pageData.getString("asset_into_bill");
			String as = pageData.getString("asset_status");
			if(!asset_name.contains(an)){
				asset_name.add(an);
			}
			if(!asset_class.contains(ac)){
				asset_class.add(ac);
			}
			if(!asset_user.contains(au)){
				asset_user.add(au);
				
			}
			if(!asset_purchase_time.contains(apt)){
				asset_purchase_time.add(apt);
			}
			if(!asset_into_bill.contains(aib)){
				asset_into_bill.add(aib);
			}
			if(!asset_status.contains(as)){
				asset_status.add(as);
			}
		}
		
		mv.addObject("asset_name", asset_name);
		mv.addObject("asset_class", asset_class);
		mv.addObject("asset_user", asset_user);
		mv.addObject("asset_purchase_time", asset_purchase_time);
		mv.addObject("asset_into_bill", asset_into_bill);
		mv.addObject("asset_status", asset_status);
		mv.addObject("assetCodeFind", assetCodeFind);
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		//查询审批流程供项目立项的时候进行选择
		
		JSONArray js = JSONArray.fromObject(info);
		
		
		mv.addObject("institutionInfo", js);
		mv.setViewName("system/asset_find/asset_find_add");
		//mv.addObject("msg", map);
		mv.addObject("pd", pd);
	
	
		return mv;
	}*/
	/**
	 * 高级查询
	 */
//	@RequestMapping(value = "/findG")
//	public ModelAndView findG() throws Exception {
//		ModelAndView mv = this.getModelAndView();
//		PageData pd = new PageData();
//		pd = this.getPageData();
//
//		List<PageData> allotList=assetFindService.findGaoJi(pd);
//	
//		mv.addObject("allotList", allotList);
//		mv.addObject("msg", "success");
//		
//		mv.setViewName("save_result");
//		
//		return mv;
//	}
	/**
	 * 去修改申请页面（维修与维护表）
	 */
	@RequestMapping(value = "/goFind")
	public ModelAndView goFind() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String filePath = PathUtil.getPath("2")+File.separator;
		
		AssetInfo allot = assetFindService.listAllotById(pd);
		mv.setViewName("system/asset_find/asset_look");
		mv.addObject("filePath",filePath); 
		mv.addObject("sd", allot);
		mv.addObject("pd", pd);
		return mv;
	}
	/*@RequestMapping(value = "/isAssetCodeExist")
	public @ResponseBody Map<String,String> isAssetCodeExist() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		//System.out.println("参数："+pd.get("asset_code"));
		ZxingUtil.createQRCode(null, null);
		Map<String,String> m = new HashMap<String,String>();
		m.put("code", code);
		//System.out.println(m.get("code"));
		return m;
	}*/
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
