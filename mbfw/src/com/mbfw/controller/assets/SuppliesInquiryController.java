package com.mbfw.controller.assets;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesInquiry;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.assets.SuppliesInquiryService;
import com.mbfw.service.assets.SuppliesStoreService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.PageData;

@Controller
@RequestMapping(value = "/asset")
public class SuppliesInquiryController extends BaseController{
	@Resource(name = "SuppliesInquiryService")
	private SuppliesInquiryService suppliesInquiryService;
	@Resource(name = "projectApplyService")
	private ProjectApplyService projectApplyService;
	@Resource(name = "SuppliesStoreService")
	private SuppliesStoreService suppliesStoreService;
	@Autowired
	private SuppliesInquiryService ams;
	
	
	/**
	 * 耗材查询页面
	 */
	@RequestMapping(value = "/acm_inquiry")
	public ModelAndView inquiry() throws Exception {

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
		
		
		
		String supplies_model = pd.getString("supplies_model");
		String supplies_name = pd.getString("supplies_name");
		String company_apply = pd.getString("company_apply");
		String applicant_sector = pd.getString("applicant_sector");
		String purchase_time = pd.getString("purchase_time");
		
		
		String product_code = pd.getString("product_code");
		String product_name = pd.getString("product_name");
		
		//查询公司和对应的部门生成json 供二级联动适应
		String info = projectApplyService.institutionInfo();
		JSONArray js = JSONArray.fromObject(info);
		mv.addObject("institutionInfo", js);
		
		//走进来传的null  一进来都是传的null
		mv.addObject("product_name", product_name);
		mv.addObject("product_code", product_code);
		mv.addObject("applicant_sector",applicant_sector);
		
			//显示下拉框中的耗材编码   
		mv.addObject("product_code_used", suppliesInquiryService.find_suppliesStore(pd));
		
		
//		//显示下拉框中的名称   因为和耗材入库添加页面查的是同一个表 product_info
//		mv.addObject("product_name_used", suppliesStoreService.find_suppliesStore(pd));
//		
		//显示下拉框中的日期   因为和耗材入库添加页面查的是同一个表 product_info
	   mv.addObject("product_time_used",suppliesInquiryService.find_suppliesinquiry_time(pd));
		
		
		List<PageData> 	listApprover = new ArrayList<PageData>();	
       //分页操作开始
		PageOption page = new PageOption(5, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
				if(pd.getString("showCount") != null){
					page.setShowCount(Integer.parseInt(pd.getString("showCount")));
				}
		
				
				
				
				
		//------没有进行检索的处理-----
				if((company_apply == null || company_apply.equals(""))&&(supplies_model == null || supplies_model.equals(""))
						&&(supplies_name == null || supplies_name.equals(""))&&(applicant_sector == null || applicant_sector.equals(""))
						&&(purchase_time == null || purchase_time.equals(""))){
					//(3)查询数据库中数据的总条数
					page.setPd(pd);
					Integer totalnumber = suppliesInquiryService.findTotalInDataNumber();
					//(4)设置总的数据条数
					page.setTotalResult(totalnumber); 
					//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
					//(6)查询数据库，返回对应条数的数据
					listApprover = suppliesInquiryService.listPdGetPageApprover(page);
				}
				//--------进行检索处理
				else{
					
					//处理URL带过来数据的乱码问题
					if(supplies_model != null && !("").equals(supplies_model)){
						pd.put("supplies_model", URLDecoder.decode(supplies_model, "utf-8"));
					}
					else if(supplies_name != null && !("").equals(supplies_name)){
						pd.put("supplies_name", URLDecoder.decode(supplies_name, "utf-8"));
					}
					else if(company_apply != null && !("").equals(company_apply)){
						pd.put("company_apply", URLDecoder.decode(company_apply, "utf-8"));
					}
					else if(applicant_sector != null && !("").equals(applicant_sector)){
						pd.put("applicant_sector", URLDecoder.decode(applicant_sector, "utf-8"));
					}
					else if(purchase_time != null && !("").equals(purchase_time)){
						pd.put("purchase_time", URLDecoder.decode(purchase_time, "utf-8"));
					}
					
					//这个要特别处理一下，因为如果不选公司，那么部门这个字段就是空，会导致查询出问题（特别注意）
					if(("").equals(company_apply) || company_apply == null ){
						pd.remove("applicant_sector");	
					}
							
					//System.out.println(id+"   "+useid);
					//(3)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
					
					//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
					page.setPd(pd);
					//(5)查询对应审核人员姓名的数据总条数
					Integer totalnumber = suppliesInquiryService.findNumberbyItemNumber(pd);
					//(6)设置总的数据条数
					page.setTotalResult(totalnumber);
					//(7)设置需要显示的数据的索引
					page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
					//(8)查询数据库，返回对应检索姓名的数据
					listApprover = suppliesInquiryService.findNumberbyItem(page);
					}
		mv.addObject("permission",permission);
		
		mv.addObject("page",page); 
		mv.addObject("pd",pd);
		mv.addObject("listApprover", listApprover);
		mv.setViewName("system/aconsume_material/inquiry");
		return mv;
	}
}



