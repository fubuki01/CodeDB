package com.mbfw.controller.assets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.DotData;
import com.mbfw.entity.PieData;
import com.mbfw.entity.assets.AssetCount;
import com.mbfw.entity.assets.AssetCountTable;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetCountTableService;
import com.mbfw.service.assets.AssetFindService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.service.assets.InstitutionInfoManageService;
import com.mbfw.service.assets.MertialClassifyService;
import com.mbfw.util.Const;
import com.mbfw.util.DateUtil;
import com.mbfw.util.ObjectExcelView;
import com.mbfw.util.PageData;

/**
 * 资产统计
 * @author:	   	LCL
 * @date: 	   	2017-10-10
 * @description:
 */

@Controller
@RequestMapping(value = "/asset")
public class AssetCountController extends BaseController {
	
	@Resource(name="InstitutionalInfo")
	private InstitutionInfoManageService iims;
	
	@Resource(name="MertialClassify")
	private MertialClassifyService mcfs;
	
	@Resource(name="assetRegisterService")
	private AssetRegisterService  assetRegisterService;
	
	private String zclb[] = {"资产类别A","资产类别B","资产类别C","资产类别S"};
	private List<String> orgAbbrList = new ArrayList<String>();
	
	@Resource(name="assetCountTableService")
	private AssetCountTableService assetCountTableService;
	
	@RequestMapping(value = "/assetCountGraph.do")
	public @ResponseBody Map<String,Object> assetCountGraph(
			@RequestParam(value="asset_use_dept",required=false) String asset_use_dept[],
			@RequestParam(value="asset_class",required=false) String asset_class[],
			@RequestParam(value="asset_status",required=false) String asset_status[],
			@RequestParam(value="ksrq",required=false) String ksrq,
			@RequestParam(value="jsrq",required=false) String jsrq) throws Exception {
		
		PageData pd = new PageData();
		pd.put("ksrq", ksrq);
		pd.put("jsrq", jsrq);
		List<DotData> dotList = new ArrayList<DotData>();
		List<PieData> pieList = new ArrayList<PieData>();

		//---汇总饼图Start---
		pd.put("asset_class", asset_class);
		pd.put("asset_use_dept",asset_use_dept);
		List<BigDecimal> priceList = new ArrayList<BigDecimal>();
		for(int j = 0; j < asset_status.length; j++){
			pd.put("asset_status",asset_status[j]);
			BigDecimal pri = assetRegisterService.findTotalPriceByStatus(pd);
			System.out.println("价格:"+pri);
			pieList.add(new PieData(asset_status[j],pri));
		}
		//---汇总饼图End---

		//---汇总条形图Start---
		priceList.clear();
		pd.put("asset_status", asset_status);
		for(int i = 0; i < asset_class.length; i++){
			pd.put("asset_class", asset_class[i]);
			priceList.add(assetRegisterService.findTotalPriceByClass(pd));
		}
		dotList.add(new DotData("所有",priceList));
		//---汇总条形图End---

		Map<String,Object> m = new HashMap<String,Object>();
		m.put("asset_status", asset_status);
		m.put("asset_class", asset_class);
		m.put("data", dotList);
		m.put("pieData", pieList);
		return m;
	}
	
	//分部门显示报表
	@RequestMapping(value = "/asset_count_table.do")
	public ModelAndView assetCountTable(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//false的意思是获取到的session为空，则返回null,true表示没有就新建一个会话
		HttpSession session =request.getSession(false);
		User u = (User) session.getAttribute("sessionUser");
		//当前用户所属组织
		String orgName = u.getOrganization_name();
		Integer perFlag = u.getUser_Permission();
		if(perFlag == 1){ 		//总行管理员
			orgAbbrList =  iims.findOrgByType(pd);
		}else if(perFlag == 2){ //支行管理员
			orgAbbrList.clear();
			pd.put("orgName", orgName);
			findSuperOrg(pd);
		}
		List<PieData> pieList = new ArrayList<PieData>();
		List<AssetCountTable> list = new ArrayList<AssetCountTable>();
		//List<BigDecimal> priList = new ArrayList<BigDecimal>();
		List<AssetCount> tableList = new ArrayList<AssetCount>();
		Integer get_year = Integer.parseInt(DateUtil.getYear());
		for(String one : orgAbbrList){
			pd.put("asset_use_dept",one);
			pd.put("get_time", null);
			BigDecimal zzc = assetCountTableService.findToTalAssetPriByDept(pd);
			pieList.add(new PieData(one,zzc));
			pd.put("get_time", get_year);
			BigDecimal ndxz = assetCountTableService.findToTalAssetPriByDept(pd);
			tableList.add(new AssetCount(one,zzc,ndxz));
		}
		mv.addObject("orgAbbrList",orgAbbrList);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(pieList);
		mv.addObject("type",pd.getString("type"));
		mv.addObject("jsonStr",jsonStr);
		mv.addObject("tableList",tableList);
		mv.setViewName("system/asset_count/asset_count_table2");
		return mv;
	}
	
	
	@RequestMapping(value = "/dept_asset_list.do")
	public ModelAndView assetList(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		PageOption page = new PageOption(10, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if(pd.getString("showCount") != null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		Integer totalnumber = assetCountTableService.findTotalDataNumber(pd);
		//(4)设置总的数据条数
		page.setTotalResult(totalnumber); 
		page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  
		pd.put("page", page);
		List<AssetCountTable> list = assetCountTableService.findAssetCountTableByDeptAndCond(pd);
		mv.addObject("list",list);
		mv.addObject("page",page);
		mv.addObject("type",pd.getString("type"));
		mv.addObject("asset_use_dept",pd.getString("asset_use_dept"));
		mv.addObject("condition",pd.getString("condition"));
		mv.setViewName("system/asset_count/dept_asset_list");
		return mv;
	}
	//根据部门查询部门资产
	
	@RequestMapping(value = "/dept_search_asset.do")
	public ModelAndView dept_search_asset(HttpServletRequest request) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List<AssetCountTable> list = assetCountTableService.findAssetCountTableByDeptAndCond(pd);
		mv.addObject("list",list);
		mv.addObject("condition",pd.getString("condition"));
		mv.addObject("asset_use_dept",pd.getString("asset_use_dept"));
		mv.setViewName("system/asset_count/dept_asset_list");
		return mv;
	}
	
	@RequestMapping(value = "/table_to_excel.do")
	public ModelAndView tableToExcel(HttpServletRequest request) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		HttpSession session =request.getSession(false);
		User u = (User) session.getAttribute("sessionUser");
		//当前用户所属组织
		String orgName = u.getOrganization_name();
		Integer perFlag = u.getUser_Permission();
		if(perFlag == 1){ 		//总行管理员
			orgAbbrList =  iims.findOrgByType(pd);
		}else if(perFlag == 2){ //支行管理员
			orgAbbrList.clear();
			pd.put("orgName", orgName);
			findSuperOrg(pd);
		}
		List<AssetCount> tableList = new ArrayList<AssetCount>();
		Integer get_year = Integer.parseInt(DateUtil.getYear());
		for(String one : orgAbbrList){
			pd.put("asset_use_dept",one);
			pd.put("get_time", null);
			BigDecimal zzc = assetCountTableService.findToTalAssetPriByDept(pd);
			pd.put("get_time", get_year);
			BigDecimal ndxz = assetCountTableService.findToTalAssetPriByDept(pd);
			tableList.add(new AssetCount(one,zzc,ndxz));
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("序号");		//1
		titles.add("部门/支行名称"); // 2
		titles.add("资产总额"); // 3
		titles.add("年度新增资产总额"); // 4
		dataMap.put("titles", titles);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i = 0; i < tableList.size(); i++){
			PageData pdata = new PageData();
			pdata.put("var1", String.valueOf(i+1));
			pdata.put("var2", tableList.get(i).getAsset_use_dept());
			pdata.put("var3", String.valueOf(tableList.get(i).getZzc()));
			pdata.put("var4", String.valueOf(tableList.get(i).getNdxz()));
			varList.add(pdata);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView(); // 执行excel操作
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	public void findSuperOrg(PageData pd) throws Exception{
		orgAbbrList.add(pd.getString("orgName"));
		List<String> tmp = new ArrayList<String>();
		while((tmp = iims.findBranch(pd)).size() >0){
			for(String org : tmp){
				pd.put("orgName", org);
				findSuperOrg(pd);
			}
		}
	}
	
	public static void main(String args[]){
		Map<String,BigDecimal> m = new HashMap<String,BigDecimal>();
		BigDecimal start = new BigDecimal(0);
		m.put("11", start);
		m.put("22", start);
		m.put("11", new BigDecimal(2));
		
		System.out.println(m.get("11")+"--"+m.get("22"));
	}
}


