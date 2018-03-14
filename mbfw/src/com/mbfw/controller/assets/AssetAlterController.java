package com.mbfw.controller.assets;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetAlter;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetAlterService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.assets.SuppliesStoreService;
import com.mbfw.service.assets.SuppliesUseService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.PageData;
@SuppressWarnings("unused")
@Controller
@RequestMapping(value = "/asset")
public class AssetAlterController extends BaseController{

	@Resource(name = "AssetAlterService")
	private AssetAlterService assetAlterService;
	@Autowired
	private AssetAlterService ams;
	@Resource(name = "projectApplyService")
	private ProjectApplyService projectApplyService;
	
	/**
	 * 配置更改页面
	 */
	@RequestMapping(value = "/acm_change")
	public ModelAndView change() throws Exception {
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
		
		
		
		List<PageData> 	listApprover = new ArrayList<PageData>();	

		PageOption page = new PageOption(8, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
				if(pd.getString("showCount") != null){
					page.setShowCount(Integer.parseInt(pd.getString("showCount")));
				}
		
		//------没有进行检索的处理-----
				if(pd.getString("retrieve_content") == null || pd.getString("retrieve_content") == ""){
					//(3)查询数据库中数据的总条数
					Integer totalnumber = assetAlterService.findTotalPzDataNumber();
					//(4)设置总的数据条数
					page.setPd(pd);
					page.setTotalResult(totalnumber); 
					//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
					//(6)查询数据库，返回对应条数的数据
					listApprover = assetAlterService.listPdGetPageApprover(page);
				}
				//--------进行检索处理
				else{
					//(3)获取传送过来的进行检索的审核人员的姓名
					String searchName = pd.getString("retrieve_content");
					//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
					page.setPd(pd);
					//(5)查询对应审核人员姓名的数据总条数
					Integer totalnumber = assetAlterService.findNumberPzBySearchName(page);
					//(6)设置总的数据条数
					page.setTotalResult(totalnumber);
					//(7)设置需要显示的数据的索引
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
					//(8)查询数据库，返回对应检索姓名的数据
					listApprover = assetAlterService.listPzSearchNameApprover(page);
					}
		mv.addObject("permission",permission);
		mv.addObject("page",page);       
		mv.addObject("listApprover", listApprover);
		
		mv.setViewName("system/aconfig_alter/Perzhi");
		return mv;
	}
//	新增配置更改表
	@RequestMapping(value="/Pz_add")
	public ModelAndView Pz_add(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		JSONArray js = JSONArray.fromObject(info);
		mv.addObject("institutionInfo", js);
		
		//显示下拉框中的耗材编码
		mv.addObject("product_code_used", assetAlterService.find_assetAlter(pd));
		
		mv.setViewName("system/aconfig_alter/Perzhi_add");
		mv.addObject("msg", "suppliesRk");
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 显示资产编码对应的信息
	 */
	@RequestMapping(value = "/fill_assetAlter")	

	public @ResponseBody PageData fill_assetAlter(Page page) throws Exception {
		PageData pd = this.getPageData();
//		//获取入库单的唯一id号
//		String id = pd.getString("id");
		
		
		//获取入库单的唯一id号
				String asset_code = pd.getString("asset_code");
				
		PageData fill_asset_info = assetAlterService.find_product_to_add_supplies(asset_code);
		System.out.println(fill_asset_info);
		return fill_asset_info;
	}

	/**
	 * 添加变更配置信息
	 * @throws Exception 
	 */
	@RequestMapping(value="/savePz")
	public ModelAndView savePz() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		
		//取耗材名称存储
		String asset_name = pd.getString("chan_config").split("@")[1];
		pd.put("chan_config", asset_name);
		
		
		assetAlterService.addPzData(pd);
		mv.addObject("msg","success");  //标识更新信息成功
		mv.setViewName("save_result");
		
		return mv;
//		return "redirect:/asset/aucs_manage";
	}
	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value = "/delete_Pz")
	@ResponseBody
	public Object deleteAllGetData() {
		PageData pd = new PageData();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String Allot_ids = pd.getString("id");
			String [] ArrayAllot_ids = Allot_ids.split(",");
			Integer  [] newIds = new Integer[ArrayAllot_ids.length];
			for(int i = 0; i < ArrayAllot_ids.length; i++){
				newIds[i] = Integer.parseInt(ArrayAllot_ids[i]);
			}
			assetAlterService.deletePzData(newIds);
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
	 * 批量删除
	 * @throws Exception 
	 */
//	@RequestMapping(value = "/delete_Pz")
//	public String delete_Pz(Integer[] ids) throws Exception {
//		PageData pd =this.getPageData();
//		//进行批量删除操作
//		assetAlterService.deletePzData(ids);
//		
//		return "redirect:/asset/acm_change";
//
//	}

//	点击删除，根据ID进行删除对应的人员（注：只是将该人员在审核表中数据进行清理，而不是删除该用户）
		@RequestMapping(value="/delPz")
		public void delPz(PrintWriter out) throws Exception{
			ModelAndView mav = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			assetAlterService.deletePz(pd);	
			out.write("success");
			out.close();	

}
		
		/**
		 * 跳转修改页面修改页面
		 */
		@RequestMapping(value = "/EditPz")
		public ModelAndView EditRuku() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			AssetAlter ePz = assetAlterService.EditPzData(pd);	
			mv.setViewName("system/aconfig_alter/Perzhi_edit");
			mv.addObject("ePz", ePz);
			mv.addObject("pd", pd);
			return mv;
		}
		
		/**
		 * 提交修改表
		 */
		@RequestMapping(value = "/edit_Pz")
		public ModelAndView edit_get() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			assetAlterService.updatePz(pd);
			
			mv.addObject("msg", "success");
		
			mv.setViewName("save_result");
			return mv;
		}
}
