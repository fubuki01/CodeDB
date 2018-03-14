package com.mbfw.controller.mobile;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetAbandonManage;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetIntolibraryApply;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.assets.AssetRecycleManage;
import com.mbfw.service.assets.AssertIssuedService;
import com.mbfw.service.assets.AssetAbandonService;
import com.mbfw.service.assets.AssetGetService;
import com.mbfw.service.assets.AssetIntolibraryApplyService;
import com.mbfw.service.assets.AssetRARService;
import com.mbfw.service.assets.AssetRecycleService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.service.assets.SuppliesInquiryService;
import com.mbfw.util.AppUtil;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/mobile/query")
public class QueryController extends BaseController{

	/**
	 * 耗材查询
	 */
	@Resource(name="SuppliesInquiryService")
	private SuppliesInquiryService suppliesInquiryService; //资产领用查询服务
	@RequestMapping(value = "/supplies_Inquiry", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object supplies_Inquiry() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		
		List<PageData> supplies = suppliesInquiryService.findSuppliesByDefault(tmp);
		
		String json = (new Gson()).toJson(supplies);
		map.put("result", json.toString());
		return map;
	}
	/**
	 * 资产信息查询
	 */	
	@Resource(name="assetRegisterService")
	private AssetRegisterService assetRegisterService; //资产信息查询服务
	@RequestMapping(value = "/asset_info", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_info() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		int status = Integer.valueOf(pd.getString("asset_status"));
		String[] str = {"闲置", "下发", "接收", "领用", "报废", "回收", "报修"};
		for(int i=0;i<7;i++){
			String val="";
			if(status%10==1)
				val=str[i];
			tmp.put("asset_status_"+i, val);
			status/=10;
		}
		
		List<AssetInfo> assetinfo=assetRegisterService.findByDefault(tmp);
		
		JSONArray json = JSONArray.fromObject(assetinfo);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产下发查询
	 */
	@Autowired
	private AssertIssuedService assetIssuedService; //资产下发查询服务
	@RequestMapping(value = "/asset_issue", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_issue() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		int status = Integer.valueOf(pd.getString("issued_status"));
		String[] str = {"下发中", "接收", "领用"};
		for(int i=0;i<3;i++){
			String val="";
			if(status%10==1)
				val=str[i];
			tmp.put("issued_status_"+i, val);
			status/=10;
		}
		
		List<PageData> assetrar = assetIssuedService.mobile_issue_query(tmp);
		
		String json = (new Gson()).toJson(assetrar);
//		JSONArray json = JSONArray.fromObject(assetrar);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产领用查询
	 */
	@Resource(name="assetGetService")
	private AssetGetService assetGetService; //资产领用查询服务
	@RequestMapping(value = "/asset_get", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_get() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		
		List<AssetGetManage> asset_get = assetGetService.findByDefault(tmp);
		
		JSONArray json = JSONArray.fromObject(asset_get);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产回收查询
	 */
	@Resource(name="assetRecycleService")
	private AssetRecycleService assetRecycleService; //资产领用查询服务
	@RequestMapping(value = "/asset_recycle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_recycle() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		
		List<AssetRecycleManage> asset_get = assetRecycleService.findByDefault(tmp);
		
		JSONArray json = JSONArray.fromObject(asset_get);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产报废查询
	 */
	@Resource(name="assetAbandonService")
	private AssetAbandonService assetAbandonService; //资产领用查询服务
	@RequestMapping(value = "/asset_abandon", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_abandon() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		
		List<AssetAbandonManage> asset_get = assetAbandonService.findByDefault(tmp);
		
		JSONArray json = JSONArray.fromObject(asset_get);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产维修查询
	 */
	@Resource(name="assetRARService")
	private AssetRARService assetRARService;
	@RequestMapping(value = "/asset_report", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object asset_report() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		int status = Integer.valueOf(pd.getString("maintain_result"));
		String[] str = {"未维修", "已维修", "无法维修"};
		for(int i=0;i<3;i++){
			String val="";
			if(status%10==1)
				val=str[i];
			tmp.put("maintain_result_"+i, val);
			status/=10;
		}
		
		List<AssetRAR> assetrar=assetRARService.findByDefault(tmp);
		
		JSONArray json = JSONArray.fromObject(assetrar);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 入库查询
	 */
	@Resource(name="assetIntolibraryApplyService")
	private AssetIntolibraryApplyService assetIntolibraryApplyService; //入库查询服务
	@RequestMapping(value = "/storage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object storage() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();

		PageData tmp = new PageData();
		Set<String> keyset = pd.keySet();
		for(String key : keyset){
			if(key.equals("offset") || key.equals("num"))
				tmp.put(key, Integer.valueOf(pd.getString(key)));
			else
				tmp.put(key, pd.getString(key));
		}
		
		List<PageData> into_library_apply=assetIntolibraryApplyService.find_into_library_apply_limit(tmp);
		
		String json = (new Gson()).toJson(into_library_apply);
		map.put("result", json.toString());
		return map;
	}
}
