package com.mbfw.controller.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.mbfw.controller.base.BaseController;
import com.mbfw.service.assets.AssetCheckService;
import com.mbfw.util.PageData;

@Controller
@RequestMapping(value="/mobile/countAndCheck")
public class CountAndCheckController extends BaseController{
	/**
	 * 按资产编码获取资产相关信息（用于盘点）
	 */
	@Resource(name="assetCheckService")
	private AssetCheckService assetCheckService;
	@RequestMapping(value = "/check/get_assetinfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object get_assetinfo() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		PageData info = assetCheckService.mobile_getAssetinfo(pd);
		
		if(info==null){
			map.put("result", "该资产在盘点清单中未找到！");
		}
		else{
			if(info.getString("check_status").equals("已盘点"))
				map.put("result", "该资产已经盘点！");
			else{
				map.put("result", "ok");
				map.put("asset_name", info.getString("asset_name"));
				map.put("asset_use_company", info.getString("asset_use_company"));
				map.put("asset_use_dept", info.getString("asset_use_dept"));
				map.put("asset_user", info.getString("asset_user"));
				map.put("asset_status", info.getString("asset_status"));
			}
		}
		
		return map;
	}
	
	/**
	 * 获取某盘点表单内的资产相关信息（用于盘点）
	 */
	@RequestMapping(value = "/check/get_checklist", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object get_checklist() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		List<PageData> checklist = assetCheckService.mobile_getChecklist(pd);
		
		String json = (new Gson()).toJson(checklist);
		map.put("result", json.toString());
		return map;
	}
	
	/**
	 * 资产盘点（用于盘点）
	 */
	@RequestMapping(value = "/check/check", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object check() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		PageData info = assetCheckService.mobile_getAssetinfo(pd);
		if(info==null){
			map.put("result", "该资产在盘点清单中未找到！");
		}
		else{
			if(info.getString("check_status").equals("已盘点"))
				map.put("result", "该资产已经盘点！");
			else{
				assetCheckService.mobile_check(pd);
				map.put("result", "盘点成功！");
			}
		}

		return map;
	}
}
